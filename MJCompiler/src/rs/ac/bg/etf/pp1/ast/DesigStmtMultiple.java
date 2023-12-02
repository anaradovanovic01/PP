// generated with ast extension for cup
// version 0.8
// 6/1/2023 14:35:22


package rs.ac.bg.etf.pp1.ast;

public class DesigStmtMultiple extends DesignatorStatement {

    private DesigStmtList DesigStmtList;
    private Designator Designator;

    public DesigStmtMultiple (DesigStmtList DesigStmtList, Designator Designator) {
        this.DesigStmtList=DesigStmtList;
        if(DesigStmtList!=null) DesigStmtList.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
    }

    public DesigStmtList getDesigStmtList() {
        return DesigStmtList;
    }

    public void setDesigStmtList(DesigStmtList DesigStmtList) {
        this.DesigStmtList=DesigStmtList;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesigStmtList!=null) DesigStmtList.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesigStmtList!=null) DesigStmtList.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesigStmtList!=null) DesigStmtList.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesigStmtMultiple(\n");

        if(DesigStmtList!=null)
            buffer.append(DesigStmtList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesigStmtMultiple]");
        return buffer.toString();
    }
}
