package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class SemanticAnalyzer extends VisitorAdaptor {
	
	Obj currentMethod = null;
	boolean returnFound = false;
	boolean errorDetected = false;
	int nVars;
	Struct currentType = null;
	int level = 0;
	int globalConstants = 0;
	int globalVariables = 0;
	int globalArrays = 0;
	int localVariables = 0;
	
	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
		System.err.println(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public void visit(ProgName progName){
    	progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
    	Tab.openScope();
    }
    
    public void visit(ProgramWithMethods program){
    	nVars = Tab.currentScope.getnVars();
    	Obj mainObj = Tab.find("main");
    	if(mainObj == Tab.noObj) {
    		report_error("Greska: funkcija main nije definisana", null);
    	}
    	Tab.chainLocalSymbols(program.getProgName().obj);
    	Tab.closeScope();
    }
    
    public void visit(ProgramWithoutMethods program) {
    	report_error("Greska: funkcija main nije definisana", null);
    }
    
    public void visit(Type type){
    	Obj typeNode = Tab.find(type.getTypeName());
    	if(typeNode == Tab.noObj){
    		report_error("Greska na " + type.getLine() + ": nije pronadjen tip \"" + type.getTypeName() + "\" u tabeli simbola", null);
    		type.struct = Tab.noType;
    		currentType = Tab.noType;
    	}else{
    		if(Obj.Type == typeNode.getKind()){
    			type.struct = typeNode.getType();
    			currentType = typeNode.getType();
    		}else{
    			report_error("Greska na " + type.getLine() + ": ime " + type.getTypeName() + " ne predstavlja tip", null);
    			type.struct = Tab.noType;
    		}
    	}
    }
    
    public void visit(Variable var) {
    	Obj existingVar= Tab.find(var.getVarName());
        if (existingVar != Tab.noObj) { 
            report_error("Greska na " + var.getLine() + ": promenljiva " + var.getVarName() + " je vec deklarisana", null);
        } else {
	        if(currentType == Tab.noType) return;
	        Obj varObj = Tab.insert(Obj.Var, var.getVarName(), currentType);
	        varObj.setLevel(level);
	        if (level == 0) globalVariables++;
	        else localVariables++;
	    }
    }

    public void visit(ArrVariable var) {
    	Obj existingVar= Tab.find(var.getVarName());
        if (existingVar != Tab.noObj) {
            report_error("Greska na " + var.getLine() + ": promenljiva " + var.getVarName() + " je vec deklarisana", null);
        } else {
	        Struct varType = new Struct(Struct.Array, currentType);
	        Obj varObj = Tab.insert(Obj.Var, var.getVarName(), varType);
	        varObj.setLevel(level);
	        if (level == 0) globalArrays++;
	        else localVariables++;
        }
    }
    
    public void visit(SingleConst cnst) {
    	Constant value = cnst.getConstant();
        if (value.obj.getType() != currentType) {
            report_error("Greska na " + cnst.getLine() + ": deklarisani tip i tip dodeljene konstante se ne poklapaju", null);
            return;
        }
        Obj existingConst = Tab.find(cnst.getConstName());
        if (existingConst != Tab.noObj) {
            report_error("Greska na " + cnst.getLine() + ": konstanta " + cnst.getConstName() + " je vec deklarisana", null);
            return;
        }
        Obj constObj = Tab.insert(Obj.Con, cnst.getConstName(), currentType);
        constObj.setAdr(value.obj.getAdr());
        constObj.setLevel(level);
        globalConstants++;
    }
    
    public void visit(MultipleConst cnst) {
    	Constant value = cnst.getConstant();
        if (value.obj.getType() != currentType) {
        	report_error("Greska na " + cnst.getLine() + ": deklarisani tip i tip dodeljene konstante se ne poklapaju", null);
            return;
        }
        Obj existingConst = Tab.find(cnst.getConstName());
        if (existingConst != Tab.noObj) {
            report_error("Greska na " + cnst.getLine() + ": konstanta " + cnst.getConstName() + " je vec deklarisana", null);
            return;
        }
        Obj constObj = Tab.insert(Obj.Con, cnst.getConstName(), currentType);
        constObj.setAdr(value.obj.getAdr());
        constObj.setLevel(level);
        globalConstants++;
    }
    
    public void visit(NumConst numConst) {
        numConst.obj = new Obj(Obj.Con, "", Tab.intType);
        numConst.obj.setAdr(numConst.getValue());
    }

    public void visit(CharConst charConst) {
        charConst.obj = new Obj(Obj.Con, "", Tab.charType);
        charConst.obj.setAdr(charConst.getValue());
    }

    public void visit(BoolConstant boolConst) {
        boolConst.obj = new Obj(Obj.Con, "", DerivedTab.boolType);
        boolConst.obj.setAdr(boolConst.getValue() ? 1 : 0);
    }
    
    public void visit(MethodNotVoid method) {
    	currentMethod = Tab.insert(Obj.Meth, method.getMethName(), method.getType().struct);
    	method.obj = currentMethod;
    	Tab.openScope();
    	level = 1;
    }
    
    public void visit(MethodVoid method) {
    	currentMethod = Tab.insert(Obj.Meth, method.getMethName(), Tab.noType);
    	method.obj = currentMethod;
    	Tab.openScope();
    	level = 1;
    }
    
    public void visit(FormParams formParams) {
    	if("main".equalsIgnoreCase(currentMethod.getName())) {
    		report_error("Greska na " + formParams.getLine() + ": funkcija main ne sme imati parametre", null);
    	}
    }
    
    public void visit(MethodDecl methodDecl){
    	if("main".equalsIgnoreCase(currentMethod.getName()) && currentMethod.getType() != Tab.noType) {
    		report_error("Greska na " + methodDecl.getLine() + ": funkcija main mora biti tipa void", null);
    	}
    	if(!returnFound && currentMethod.getType() != Tab.noType) {
    		report_error("Greska na " + methodDecl.getLine() + ": funkcija " + currentMethod.getName() + " nema return iskaz", null);
    	}
    	currentMethod.setLevel(0);
    	Tab.chainLocalSymbols(currentMethod);
    	Tab.closeScope();
    	returnFound = false;
    	currentMethod = null;
    	level = 0;
    }
    
    public void visit(ReturnExpr returnExpr){
    	returnFound = true;
    	Struct currMethType = currentMethod.getType();
    	if(!currMethType.compatibleWith(returnExpr.getExpr().struct)){
			report_error("Greska na " + returnExpr.getLine() + ": tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije " + currentMethod.getName(), null);
    	}
    }
    
    public void visit(ReturnNoExpr returnExpr){
    	Struct currMethType = currentMethod.getType();
    	if(currMethType != Tab.noType){
			report_error("Greska na " + returnExpr.getLine() + ": tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije " + currentMethod.getName(), null);
    	}
    }
    
    
    
    
    //-----------------EXPR------------------------
    public void visit(TermExpr expr){
    	expr.struct = expr.getTerm().struct;
    }
    
    public void visit(TermExprMinus expr){
    	if(expr.getTerm().struct == Tab.noType) {
    		expr.struct = Tab.noType;
    		return;
    	}
    	if(expr.getTerm().struct.getKind() != Struct.Int) {
    		report_error("Greska na " + expr.getLine() + ": izraz nije tipa int", null);
    		expr.struct = Tab.noType;
    	}
    	else expr.struct = expr.getTerm().struct;
    }
    
    public void visit(AddExpr expr){
    	if(expr.getTerm().struct == Tab.noType || expr.getExpr().struct == Tab.noType) {
    		expr.struct = Tab.noType;
    		return;
    	}
    	if(expr.getTerm().struct.getKind() != Struct.Int || expr.getExpr().struct.getKind() != Struct.Int) {
    		report_error("Greska na " + expr.getLine() + ": operator nije tipa int", null);
    		expr.struct = Tab.noType;
    		return;
    	}
    	else expr.struct = Tab.intType;
    }

    
    
    //-----------------TERM------------------------
    public void visit(FactorTerm term){
    	term.struct = term.getFactor().struct;
    }
    
    public void visit(MulTerm term){
    	if(term.getFactor().struct == Tab.noType) {
    		term.struct = Tab.noType;
    		return;
    	}
    	if(term.getTerm().struct.getKind() != Struct.Int || term.getFactor().struct.getKind() != Struct.Int) {
    		report_error("Greska na " + term.getLine() + ": operator nije tipa int", null);
    		term.struct = Tab.noType;
    	}
    	else term.struct = Tab.intType;
    }
    
    
    
    //-----------------FACTOR----------------------
    public void visit(FactorConst factor){
    	factor.struct = factor.getConstant().obj.getType();
    }
    
    public void visit(Var var){
    	var.struct = var.getDesignator().obj.getType();
    }
    
    public void visit(FuncCallWithoutPars funcCall){
    	Obj func = funcCall.getDesignator().obj;
    	if(func == Tab.noObj) {
    		funcCall.struct = Tab.noType;
    		return;
    	}
    	if(Obj.Meth == func.getKind()){
			funcCall.struct = func.getType();
    	}else{
    		report_error("Greska na " + funcCall.getLine() + ": ime " + func.getName() + " nije funkcija", null);
			funcCall.struct = Tab.noType;
    	}
    }
    
    public void visit(FuncCallWithPars funcCall){
    	Obj func = funcCall.getDesignator().obj;
    	if(func == Tab.noObj) {
    		funcCall.struct = Tab.noType;
    		return;
    	}
    	if(Obj.Meth == func.getKind()){
			funcCall.struct = func.getType();
    	}else{
			report_error("Greska na " + funcCall.getLine() + ": ime " + func.getName() + " nije funkcija", null);
			funcCall.struct = Tab.noType;
    	}
    }
    
    public void visit(FactorAllocArr factor) {
    	if(factor.getExpr().struct == Tab.noType) {
    		factor.struct = Tab.noType;
    		return;
    	}
    	if(factor.getExpr().struct.getKind() != Struct.Int) {
    		report_error("Greska na " + factor.getLine() + ": izraz nije tipa int", null);
    		factor.struct = Tab.noType;
    	}
    	else factor.struct = new Struct(Struct.Array, factor.getType().struct);
    }
    
    public void visit(FactorAlloc factor) {
    	if(factor.getType().struct == Tab.noType) {
    		factor.struct = Tab.noType;
    		return;
    	}
    	if(factor.getType().struct.getKind() != Struct.Class) {
    		report_error("Greska na " + factor.getLine() + ": instanciranje operatorom new promenljive koja nije tipa klase", null);
    		factor.struct = Tab.noType;
    	}
    	else factor.struct = factor.getType().struct;
    }
    
    public void visit(FactorAllocWithPars factor) {
    	if(factor.getType().struct == Tab.noType) {
    		factor.struct = Tab.noType;
    		return;
    	}
    	if(factor.getType().struct.getKind() != Struct.Class) {
    		report_error("Greska na " + factor.getLine() + ": instanciranje operatorom new promenljive koja nije tipa klase", null);
    		factor.struct = Tab.noType;
    	}
    	else factor.struct = factor.getType().struct;
    }
    
    public void visit(FactorExpr factor) {
    	factor.struct = factor.getExpr().struct;
    }
    
    
    
    //-----------------DESIGNATOR----------------------
    public void visit(DesignatorSimple design) {
    	Obj var = Tab.find(design.getName());
    	if(var == Tab.noObj) {
    		report_error("Greska na " + design.getLine() + ": promenljiva \"" + design.getName() + "\" nije defininsana pre koriscenja", null);
    		design.obj = Tab.noObj;
    	} 
    	else {
    		design.obj = var;
    		DumpSymbolTableVisitor visitor = new DumpSymbolTableVisitor();
    		visitor.visitObjNode(var);
    		System.out.println("Pretraga na " + design.getLine() + "(" + design.getName() + "), nadjeno " + visitor.getOutput());
    	}
    }
    
    public void visit(DesignatorArr design) {
    	if(design.getDesignator().obj == Tab.noObj) {
    		design.obj = Tab.noObj;
    		return;
    	}
    	if(design.getDesignator().obj.getType().getKind() != Struct.Array) {
    		report_error("Greska na " + design.getLine() + ": promenljiva nije niz", null);
    		design.obj = Tab.noObj;
    		return;
    	} 
    	if(design.getExpr().struct.getKind() != Struct.Int) {
    		report_error("Greska na " + design.getLine() + ": izraz nije tipa int", null);
    		design.obj = Tab.noObj;
    		return;
    	}
    	design.obj = new Obj(Obj.Elem, design.getDesignator().obj.getName(), design.getDesignator().obj.getType().getElemType());
    }
    
    public void visit(DesignatorMethod design) {
    	if(design.getDesignator().obj == Tab.noObj) {
    		design.obj = Tab.noObj;
    		return;
    	}
    	if(design.getDesignator().obj.getType().getKind() != Struct.Class) {
    		report_error("Greska na " + design.getLine() + ": promenljiva nije klasa", null);
    		design.obj = Tab.noObj;
    		return;
    	} 
    	Obj var = Tab.find(design.getName());
    	if(var.getKind() != Obj.Meth && var.getKind() != Obj.Fld) {
    		report_error("Greska na " + design.getLine() + ": izraz nije tipa int", null);
    		design.obj = Tab.noObj;
    		return;
    	}
    	design.obj = var;
    } 
    
    
    //-----------------DESIGNATOR STATEMENT----------------------
    public void visit(DesigStmtExpr desigStmt) {
    	Obj designator = desigStmt.getDesignator().obj;
    	Struct expr = desigStmt.getExpr().struct;
    	if(designator == Tab.noObj || expr == Tab.noType) return;
    	if(designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem && designator.getKind() != Obj.Fld) {
    		report_error("Greska na " + desigStmt.getLine() + ": identifikator \"" + designator.getName() + "\" mora biti promenljiva, element niza ili polje klase", null);
    	}
    	else if(designator.getType() != expr  && !(expr == Tab.nullType && designator.getType().getKind() == Struct.Class) 
    			&& !(expr.getKind() == designator.getType().getKind() && expr.getElemType() == designator.getType().getElemType())) {
    		report_error("Greska na " + desigStmt.getLine() + ": tipovi nisu kopatibilni", null);
    	}
    }
    
    public void visit(DesigStmtInc desigStmt) {
    	Obj designator = desigStmt.getDesignator().obj;
    	if(designator == Tab.noObj) return;
    	if(designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem && designator.getKind() != Obj.Fld) {
    		report_error("Greska na " + desigStmt.getLine() + ": identifikator \"" + designator.getName() + "\" mora biti promenljiva, element niza ili polje klase", null);
    	}
    	else if(designator.getType() != Tab.intType) {
    		report_error("Greska na " + desigStmt.getLine() + ": identifikator \"" + designator.getName() + "\" mora biti tipa int", null);
    	}
    }
    
    public void visit(DesigStmtDec desigStmt) {
    	Obj designator = desigStmt.getDesignator().obj;
    	if(designator == Tab.noObj) return;
    	if(designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem && designator.getKind() != Obj.Fld) {
    		report_error("Greska na " + desigStmt.getLine() + ": identifikator \"" + designator.getName() + "\" mora biti promenljiva, element niza ili polje klase", null);
    	}
    	else if(designator.getType() != Tab.intType) {
    		report_error("Greska na " + desigStmt.getLine() + ": identifikator \"" + designator.getName() + "\" mora biti tipa int", null);
    	}
    }
    
    public void visit(DesigStmtNoPars desigStmt) {
    	Obj designator = desigStmt.getDesignator().obj;
    	if(designator == Tab.noObj) return;
    	if(designator.getKind() != Obj.Meth) {
    		report_error("Greska na " + desigStmt.getLine() + ": identifikator \"" + designator.getName() + "\" mora biti metoda klase ili globalna funkcija", null);
    	}
    }
    
    public void visit(DesigStmtPars desigStmt) {
    	Obj designator = desigStmt.getDesignator().obj;
    	if(designator == Tab.noObj) return;
    	if(designator.getKind() != Obj.Meth) {
    		report_error("Greska na " + desigStmt.getLine() + ": identifikator \"" + designator.getName() + "\" mora biti metoda klase ili globalna funkcija", null);
    	}
    }
    
    public void visit(DesigStmtMultiple desigStmt) {
    	Obj designator = desigStmt.getDesignator().obj;
    	Struct desigStmtList = desigStmt.getDesigStmtList().struct;
    	if(desigStmtList == Tab.noType || desigStmtList == Tab.nullType) return;
    	if(designator.getType().getKind() != Struct.Array) {
    		report_error("Greska na " + desigStmt.getLine() + ": identifikator \"" + designator.getName() + "\" sa desne strane jednakosti mora biti niz", null);
        	return;
    	}
    	if(designator.getType().getElemType() != desigStmtList) {
    		report_error("Greska na " + desigStmt.getLine() + ": tipovi nisu kompatibilni", null);
    	}
    }
    
    
    
    //-----------------DESIGNATOR STATEMENT LIST----------------------
    public void visit(DesigStmtListOne desigStmtList) {
    	Obj designator = desigStmtList.getDesigOrNothing().obj;
    	if(designator.getType() == Tab.noType) {
    		desigStmtList.struct = Tab.noType;
    		return;
    	}
    	if(designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem && designator.getKind() != Obj.Fld) {
    		desigStmtList.struct = Tab.noType;
    		report_error("Greska na " + desigStmtList.getLine() + ": identifikator \"" + designator.getName() + "\" mora biti promenljiva, element niza ili polje klase", null);
    	}
    	else desigStmtList.struct = designator.getType();
    }
    
    public void visit(DesignatorStmtList desigStmtList) {
    	Obj designator = desigStmtList.getDesigOrNothing().obj;
    	Struct designatorList = desigStmtList.getDesigStmtList().struct;
    	if(designatorList == Tab.noType || designator.getType() == Tab.noType) {
    		desigStmtList.struct = Tab.noType;
    		return;
    	}
    	if(designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem && designator.getKind() != Obj.Fld) {
        	desigStmtList.struct = Tab.noType;
        	report_error("Greska na " + desigStmtList.getLine() + ": identifikator \"" + designator.getName() + "\" mora biti promenljiva, element niza ili polje klase", null);
        	return;
    	}
    	if(designator.getType() == Tab.nullType) {
    		desigStmtList.struct = designatorList;
    		return;
    	}
    	if(designatorList == Tab.nullType) {
    		desigStmtList.struct = designator.getType();
    		return;
    	}
    	if(designator.getType() != desigStmtList.getDesigStmtList().struct) {
    		report_error("Greska na " + desigStmtList.getLine() + ": tipovi nisu kompatibilni", null);
    		desigStmtList.struct = Tab.noType;
    		return;
    	}
    	desigStmtList.struct = designator.getType();
    }
    
    public void visit(Desig desig) {
    	desig.obj = desig.getDesignator().obj;
    }
    
    public void visit(NoDesig desig) {
    	desig.obj = new Obj(Obj.Var, "", Tab.nullType);
    }
    
    
    //------------------STATEMENT----------------------
    public void visit(PrintStmt stmt) {
    	Struct type = stmt.getExpr().struct;
    	if(type != Tab.intType && type != Tab.charType && type != DerivedTab.boolType) {
    		report_error("Greska na " + stmt.getLine() + ": izraz mora biti tipa int, char ili bool", null);
    	}
    }
    
    public void visit(PrintStmtWithConst stmt) {
    	Struct type = stmt.getExpr().struct;
    	if(type != Tab.intType && type != Tab.charType && type != DerivedTab.boolType) {
    		report_error("Greska na " + stmt.getLine() + ": izraz mora biti tipa int, char ili bool", null);
    	}
    }
 
    public void visit(ReadStmt stmt) {
    	Obj designator = stmt.getDesignator().obj;
    	if(designator.getKind() != Obj.Var && designator.getKind() != Obj.Elem && designator.getKind() != Obj.Fld) {
    		report_error("Greska na " + stmt.getLine() + ": identifikator \"" + designator.getName() + "\" mora biti promenljiva, element niza ili polje klase", null);
        	return;
    	}
    	if(designator.getType() != Tab.intType && designator.getType() != Tab.charType && designator.getType() != DerivedTab.boolType) {
    		report_error("Greska na " + stmt.getLine() + ": izraz mora biti tipa int, char ili bool", null);
    	}
    }
    
    
}
