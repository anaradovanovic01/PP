// generated with ast extension for cup
// version 0.8
// 6/1/2023 14:35:22


package rs.ac.bg.etf.pp1.ast;

public class GlobalDeclListDerived2 extends GlobalDeclList {

    public GlobalDeclListDerived2 () {
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
        buffer.append("GlobalDeclListDerived2(\n");

        buffer.append(tab);
        buffer.append(") [GlobalDeclListDerived2]");
        return buffer.toString();
    }
}
