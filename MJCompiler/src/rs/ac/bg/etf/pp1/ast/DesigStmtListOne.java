// generated with ast extension for cup
// version 0.8
// 6/1/2023 14:35:22


package rs.ac.bg.etf.pp1.ast;

public class DesigStmtListOne extends DesigStmtList {

    private DesigOrNothing DesigOrNothing;

    public DesigStmtListOne (DesigOrNothing DesigOrNothing) {
        this.DesigOrNothing=DesigOrNothing;
        if(DesigOrNothing!=null) DesigOrNothing.setParent(this);
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
        if(DesigOrNothing!=null) DesigOrNothing.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesigOrNothing!=null) DesigOrNothing.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesigOrNothing!=null) DesigOrNothing.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesigStmtListOne(\n");

        if(DesigOrNothing!=null)
            buffer.append(DesigOrNothing.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesigStmtListOne]");
        return buffer.toString();
    }
}
