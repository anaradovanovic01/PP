// generated with ast extension for cup
// version 0.8
// 6/1/2023 14:35:22


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStmtList extends DesigStmtList {

    private DesigStmtList DesigStmtList;
    private DesigOrNothing DesigOrNothing;

    public DesignatorStmtList (DesigStmtList DesigStmtList, DesigOrNothing DesigOrNothing) {
        this.DesigStmtList=DesigStmtList;
        if(DesigStmtList!=null) DesigStmtList.setParent(this);
        this.DesigOrNothing=DesigOrNothing;
        if(DesigOrNothing!=null) DesigOrNothing.setParent(this);
    }

    public DesigStmtList getDesigStmtList() {
        return DesigStmtList;
    }

    public void setDesigStmtList(DesigStmtList DesigStmtList) {
        this.DesigStmtList=DesigStmtList;
    }

    public DesigOrNothing getDesigOrNothing() {
        return DesigOrNothing;
    }

    public void setDesigOrNothing(DesigOrNothing DesigOrNothing) {
        this.DesigOrNothing=DesigOrNothing;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesigStmtList!=null) DesigStmtList.accept(visitor);
        if(DesigOrNothing!=null) DesigOrNothing.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesigStmtList!=null) DesigStmtList.traverseTopDown(visitor);
        if(DesigOrNothing!=null) DesigOrNothing.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesigStmtList!=null) DesigStmtList.traverseBottomUp(visitor);
        if(DesigOrNothing!=null) DesigOrNothing.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStmtList(\n");

        if(DesigStmtList!=null)
            buffer.append(DesigStmtList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesigOrNothing!=null)
            buffer.append(DesigOrNothing.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStmtList]");
        return buffer.toString();
    }
}
