package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.*;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc;
	
	public int getMainPc(){
		return mainPc;
	}
	
	public void visit(PrintStmt printStmt){
		if(printStmt.getExpr().struct == Tab.intType || printStmt.getExpr().struct == DerivedTab.boolType){
			Code.loadConst(5);
			Code.put(Code.print);
		} else if(printStmt.getExpr().struct == Tab.charType) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	
	public void visit(PrintStmtWithConst printStmt){
		Code.loadConst(printStmt.getValue());
		if(printStmt.getExpr().struct == Tab.intType || printStmt.getExpr().struct == DerivedTab.boolType) 
			Code.put(Code.print);
		else if(printStmt.getExpr().struct == Tab.charType) 
			Code.put(Code.bprint);
	}
	
	public void visit(ReadStmt readStmt) {
		if (readStmt.getDesignator().obj.getType() == Tab.intType || readStmt.getDesignator().obj.getType() == DerivedTab.boolType)
			Code.put(Code.read);
		else if (readStmt.getDesignator().obj.getType() == Tab.charType) 
			Code.put(Code.bread);
		Code.store(readStmt.getDesignator().obj);
    }

	
	public void visit(NumConst cnst){
		Obj con = Tab.insert(Obj.Con, "$", Tab.intType);
		con.setLevel(0);
		con.setAdr(cnst.getValue());
		Code.load(con);
	}
	
	public void visit(CharConst cnst){
		Obj con = Tab.insert(Obj.Con, "$", Tab.charType);
		con.setLevel(0);
		con.setAdr(cnst.getValue());
		Code.load(con);
	}

	public void visit(BoolConstant cnst){
		Obj con = Tab.insert(Obj.Con, "$", DerivedTab.boolType);
		con.setLevel(0);
		con.setAdr(cnst.getValue() ? 1:0);
		Code.load(con);
	}
	
	public void visit(MethodVoid methodTypeName) {
		if("main".equalsIgnoreCase(methodTypeName.getMethName())){
			mainPc = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		SyntaxNode methodNode = methodTypeName.getParent();
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		Code.put(Code.enter);
		Code.put(0);
		Code.put(varCnt.getCount());
	}

	public void visit(MethodDecl methodDecl){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(DesigStmtExpr designStmt){
		Code.store(designStmt.getDesignator().obj);
	}
	
	public void visit(DesignatorSimple designator) {
		SyntaxNode parent = designator.getParent();
		if(Var.class == parent.getClass() || DesigStmtInc.class == parent.getClass() || DesigStmtDec.class == parent.getClass() || DesignatorArr.class == parent.getClass()){
			Code.load(designator.obj);
		}
	}
	
	public void visit(DesignatorArr designator) {
		SyntaxNode parent = designator.getParent();
		if(Var.class == parent.getClass()){
			Code.put(designator.obj.getType() == Tab.charType ? Code.baload : Code.aload);
		}
		else if(DesigStmtInc.class == parent.getClass() || DesigStmtDec.class == parent.getClass()) {
			Code.put(Code.dup2);
			Code.put(designator.obj.getType() == Tab.charType ? Code.baload : Code.aload);
		}
	}
	
	public void visit(FactorAllocArr factor) {
		Code.put(Code.newarray);
		if (factor.struct.getElemType() == Tab.charType) Code.put(0);
		else Code.put(1);
	}
	
	public void visit(ReturnExpr returnExpr){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(ReturnNoExpr returnNoExpr){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(AddExpr addExpr){
		Addop op = addExpr.getAddop();
		if(op instanceof AddopPlus)	Code.put(Code.add);
		else Code.put(Code.sub);
	}
	
	public void visit(TermExprMinus expr) {
		Code.put(Code.neg);
	}
	
	public void visit(DesigStmtInc designStmt) {
        Code.put(Code.const_1);
        Code.put(Code.add);
        Code.store(designStmt.getDesignator().obj);
    }
	
	public void visit(DesigStmtDec designStmt) {
        Code.put(Code.const_1);
        Code.put(Code.sub);
        Code.store(designStmt.getDesignator().obj);
    }
	
	public void visit(DesigStmtMultiple designStmt) {
		
		//pronalazenje broja elemenata u nizu
		DesigStmtList designList = designStmt.getDesigStmtList();
		int cnt = 0;
		while(!(designList instanceof DesigStmtListOne)) {
			cnt++;
			designList = ((DesignatorStmtList) designList).getDesigStmtList();
		}
		
		//provera da li ima previse parametara
		Code.load(designator);
		Code.put(Code.arraylength);
		Code.loadConst(cnt+1);
		Code.putFalseJump(Code.lt, Code.pc+5);
		Code.put(Code.trap);
		Code.loadConst(1);
		
		//dodela vrednosti od poslednjeg ka prvom
		Obj designator = designStmt.getDesignator().obj;
		designList = designStmt.getDesigStmtList();
		while(true) {
			if(designList instanceof DesignatorStmtList) {
				DesigOrNothing desigOrNothing = ((DesignatorStmtList) designList).getDesigOrNothing();
				if(desigOrNothing instanceof Desig) {
					Code.load(designator);
					Code.loadConst(cnt);
					Code.put(designator.getType().getElemType() == Tab.charType ? Code.baload : Code.aload);
					Code.store(desigOrNothing.obj);
				}
				cnt--;
				designList = ((DesignatorStmtList) designList).getDesigStmtList();
			}
			else {
				DesigOrNothing desigOrNothing = ((DesigStmtListOne) designList).getDesigOrNothing();
				if(desigOrNothing instanceof Desig) {
					Code.load(designator);
					Code.loadConst(cnt);
					Code.put(designator.getType().getElemType() == Tab.charType ? Code.baload : Code.aload);
					Code.store(desigOrNothing.obj);
				}
				break;
			}
		}
	}
	
	public void visit(MulTerm mulopFactor) {
        Mulop op = mulopFactor.getMulop();
        if (op instanceof MulopMult) Code.put(Code.mul);
        if (op instanceof MulopDiv) Code.put(Code.div);
        if (op instanceof MulopMod) Code.put(Code.rem);
    }
	
}