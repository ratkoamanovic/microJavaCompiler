// generated with ast extension for cup
// version 0.8
// 12/0/2020 19:54:19


package rs.ac.bg.etf.pp1.ast;

public class WithOptionalAbstractMethodDeclClass extends OptionalAbstractMethodDeclClass {

    private AbstractAndBasicMethodDeclList AbstractAndBasicMethodDeclList;

    public WithOptionalAbstractMethodDeclClass (AbstractAndBasicMethodDeclList AbstractAndBasicMethodDeclList) {
        this.AbstractAndBasicMethodDeclList=AbstractAndBasicMethodDeclList;
        if(AbstractAndBasicMethodDeclList!=null) AbstractAndBasicMethodDeclList.setParent(this);
    }

    public AbstractAndBasicMethodDeclList getAbstractAndBasicMethodDeclList() {
        return AbstractAndBasicMethodDeclList;
    }

    public void setAbstractAndBasicMethodDeclList(AbstractAndBasicMethodDeclList AbstractAndBasicMethodDeclList) {
        this.AbstractAndBasicMethodDeclList=AbstractAndBasicMethodDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractAndBasicMethodDeclList!=null) AbstractAndBasicMethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractAndBasicMethodDeclList!=null) AbstractAndBasicMethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractAndBasicMethodDeclList!=null) AbstractAndBasicMethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("WithOptionalAbstractMethodDeclClass(\n");

        if(AbstractAndBasicMethodDeclList!=null)
            buffer.append(AbstractAndBasicMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [WithOptionalAbstractMethodDeclClass]");
        return buffer.toString();
    }
}
