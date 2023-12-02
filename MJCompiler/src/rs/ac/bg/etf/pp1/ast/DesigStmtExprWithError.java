// generated with ast extension for cup
// version 0.8
// 6/1/2023 14:35:22


package rs.ac.bg.etf.pp1.ast;

public class DesigStmtExprWithError extends DesignatorStatement {

    private Designator Designator;
    private Assignop Assignop;
    private DesigStmtExprError DesigStmtExprError;

    public DesigStmtExprWithError (Designator Designator, Assignop Assignop, DesigStmtExprError DesigStmtExprError) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.DesigStmtExprError=DesigStmtExprError;
        if(DesigStmtExprError!=null) DesigStmtExprError.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public Assignop getAssignop() {
        return Assignop;
    }

    public void setAssignop(Assignop Assignop) {
        this.Assignop=Assignop;
    }

    public DesigStmtExprError getDesigStmtExprError() {
        return DesigStmtExprError;
    }

    public void setDesigStmtExprError(DesigStmtExprError DesigStmtExprError) {
        this.DesigStmtExprError=DesigStmtExprError;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(Assignop!=null) Assignop.accept(visitor);
        if(DesigStmtExprError!=null) DesigStmtExprError.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(Assignop!=null) Assignop.traverseTopDown(visitor);
        if(DesigStmtExprError!=null) DesigStmtExprError.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(Assignop!=null) Assignop.traverseBottomUp(visitor);
        if(DesigStmtExprError!=null) DesigStmtExprError.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesigStmtExprWithError(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesigStmtExprError!=null)
            buffer.append(DesigStmtExprError.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesigStmtExprWithError]");
        return buffer.toString();
    }
}
