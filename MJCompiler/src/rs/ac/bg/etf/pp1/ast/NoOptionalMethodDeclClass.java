// generated with ast extension for cup
// version 0.8
// 12/0/2020 19:54:19


package rs.ac.bg.etf.pp1.ast;

public class NoOptionalMethodDeclClass extends OptionalMethodDeclClass {

    public NoOptionalMethodDeclClass () {
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
        buffer.append("NoOptionalMethodDeclClass(\n");

        buffer.append(tab);
        buffer.append(") [NoOptionalMethodDeclClass]");
        return buffer.toString();
    }
}
