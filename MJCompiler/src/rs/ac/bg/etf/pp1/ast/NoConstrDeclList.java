// generated with ast extension for cup
// version 0.8
// 6/1/2023 14:35:22


package rs.ac.bg.etf.pp1.ast;

public class NoConstrDeclList extends ConstructorDeclList {

    public NoConstrDeclList () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoConstrDeclList(\n");

        buffer.append(tab);
        buffer.append(") [NoConstrDeclList]");
        return buffer.toString();
    }
}
