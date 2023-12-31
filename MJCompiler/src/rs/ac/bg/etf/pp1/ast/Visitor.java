// generated with ast extension for cup
// version 0.8
// 6/1/2023 14:35:22


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Mulop Mulop);
    public void visit(Constant Constant);
    public void visit(Relop Relop);
    public void visit(FormalParamDecl FormalParamDecl);
    public void visit(StatementList StatementList);
    public void visit(Extends Extends);
    public void visit(Addop Addop);
    public void visit(Factor Factor);
    public void visit(CondTerm CondTerm);
    public void visit(VarList VarList);
    public void visit(ConstList ConstList);
    public void visit(Designator Designator);
    public void visit(Term Term);
    public void visit(Condition Condition);
    public void visit(DesigOrNothing DesigOrNothing);
    public void visit(DesigStmtList DesigStmtList);
    public void visit(ConstructorDeclList ConstructorDeclList);
    public void visit(GlobalDeclList GlobalDeclList);
    public void visit(VarDeclList VarDeclList);
    public void visit(FormalParamList FormalParamList);
    public void visit(Expr Expr);
    public void visit(MethodTypeName MethodTypeName);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(ActualPars ActualPars);
    public void visit(Statement Statement);
    public void visit(ClassDecl ClassDecl);
    public void visit(CondFact CondFact);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(Program Program);
    public void visit(SingleVarDecl SingleVarDecl);
    public void visit(FormPars FormPars);
    public void visit(MulopMod MulopMod);
    public void visit(MulopDiv MulopDiv);
    public void visit(MulopMult MulopMult);
    public void visit(AddopMinus AddopMinus);
    public void visit(AddopPlus AddopPlus);
    public void visit(RelopIn RelopIn);
    public void visit(RelopLte RelopLte);
    public void visit(RelopLt RelopLt);
    public void visit(RelopGte RelopGte);
    public void visit(RelopGt RelopGt);
    public void visit(RelopIneq RelopIneq);
    public void visit(RelopEq RelopEq);
    public void visit(Assignop Assignop);
    public void visit(Label Label);
    public void visit(DesignatorArr DesignatorArr);
    public void visit(DesignatorMethod DesignatorMethod);
    public void visit(DesignatorSimple DesignatorSimple);
    public void visit(ActualParam ActualParam);
    public void visit(ActualParams ActualParams);
    public void visit(FactorExpr FactorExpr);
    public void visit(FactorAllocWithPars FactorAllocWithPars);
    public void visit(FactorAlloc FactorAlloc);
    public void visit(FactorAllocArr FactorAllocArr);
    public void visit(FuncCallWithPars FuncCallWithPars);
    public void visit(FuncCallWithoutPars FuncCallWithoutPars);
    public void visit(Var Var);
    public void visit(FactorConst FactorConst);
    public void visit(FactorTerm FactorTerm);
    public void visit(MulTerm MulTerm);
    public void visit(TermExprMinus TermExprMinus);
    public void visit(TermExpr TermExpr);
    public void visit(AddExpr AddExpr);
    public void visit(CondExpr CondExpr);
    public void visit(CondRelop CondRelop);
    public void visit(CondTermFact CondTermFact);
    public void visit(AndCond AndCond);
    public void visit(ConditionTerm ConditionTerm);
    public void visit(OrCond OrCond);
    public void visit(NoDesig NoDesig);
    public void visit(Desig Desig);
    public void visit(DesigStmtListOne DesigStmtListOne);
    public void visit(DesignatorStmtList DesignatorStmtList);
    public void visit(DesigStmtExprError DesigStmtExprError);
    public void visit(DesigStmtMultiple DesigStmtMultiple);
    public void visit(DesigStmtPars DesigStmtPars);
    public void visit(DesigStmtNoPars DesigStmtNoPars);
    public void visit(DesigStmtDec DesigStmtDec);
    public void visit(DesigStmtInc DesigStmtInc);
    public void visit(DesigStmtExprWithError DesigStmtExprWithError);
    public void visit(DesigStmtExpr DesigStmtExpr);
    public void visit(Statements Statements);
    public void visit(Foreach Foreach);
    public void visit(IfStmt IfStmt);
    public void visit(IfElseStmt IfElseStmt);
    public void visit(PrintStmtWithConst PrintStmtWithConst);
    public void visit(PrintStmt PrintStmt);
    public void visit(ReadStmt ReadStmt);
    public void visit(Continue Continue);
    public void visit(Break Break);
    public void visit(While While);
    public void visit(ReturnNoExpr ReturnNoExpr);
    public void visit(ReturnExpr ReturnExpr);
    public void visit(Assignment Assignment);
    public void visit(NoStmt NoStmt);
    public void visit(StmtList StmtList);
    public void visit(FormalParDeclArr FormalParDeclArr);
    public void visit(FormalParDecl FormalParDecl);
    public void visit(SingleFormalParamDecl SingleFormalParamDecl);
    public void visit(FormalParamDecls FormalParamDecls);
    public void visit(NoFormParam NoFormParam);
    public void visit(FormParams FormParams);
    public void visit(MethodVoid MethodVoid);
    public void visit(MethodNotVoid MethodNotVoid);
    public void visit(MethodDecl MethodDecl);
    public void visit(OneMethodDecl OneMethodDecl);
    public void visit(MethodDeclarations MethodDeclarations);
    public void visit(ConstructorDecl ConstructorDecl);
    public void visit(NoConstrDeclList NoConstrDeclList);
    public void visit(ConstrDeclList ConstrDeclList);
    public void visit(NoVarDeclList NoVarDeclList);
    public void visit(VarDeclL VarDeclL);
    public void visit(WithoutExtends WithoutExtends);
    public void visit(WithExtends WithExtends);
    public void visit(ClassDeclWithoutAnything ClassDeclWithoutAnything);
    public void visit(ClassDeclWithoutMethods ClassDeclWithoutMethods);
    public void visit(ClassDeclWithMethods ClassDeclWithMethods);
    public void visit(BoolConstant BoolConstant);
    public void visit(CharConst CharConst);
    public void visit(NumConst NumConst);
    public void visit(MultipleConst MultipleConst);
    public void visit(SingleConst SingleConst);
    public void visit(ConstDecl ConstDecl);
    public void visit(ArrVariable ArrVariable);
    public void visit(Variable Variable);
    public void visit(MultipleVars MultipleVars);
    public void visit(SingleVar SingleVar);
    public void visit(Type Type);
    public void visit(VarDecl VarDecl);
    public void visit(NoVarDecl NoVarDecl);
    public void visit(GlobalDeclListDerived2 GlobalDeclListDerived2);
    public void visit(GlobalDeclListDerived1 GlobalDeclListDerived1);
    public void visit(ClassDeclarations ClassDeclarations);
    public void visit(ConstDeclarations ConstDeclarations);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(ProgName ProgName);
    public void visit(ProgramWithoutMethods ProgramWithoutMethods);
    public void visit(ProgramWithMethods ProgramWithMethods);

}
