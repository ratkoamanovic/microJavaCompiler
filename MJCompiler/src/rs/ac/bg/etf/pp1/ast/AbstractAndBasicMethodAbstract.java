// generated with ast extension for cup
// version 0.8
// 12/0/2020 19:54:19


package rs.ac.bg.etf.pp1.ast;

public class AbstractAndBasicMethodAbstract extends AbstractAndBasicMethodDeclList {

    private AbstractAndBasicMethodDeclList AbstractAndBasicMethodDeclList;
    private AbstractMethodDecl AbstractMethodDecl;

    public AbstractAndBasicMethodAbstract (AbstractAndBasicMethodDeclList AbstractAndBasicMethodDeclList, AbstractMethodDecl AbstractMethodDecl) {
        this.AbstractAndBasicMethodDeclList=AbstractAndBasicMethodDeclList;
        if(AbstractAndBasicMethodDeclList!=null) AbstractAndBasicMethodDeclList.setParent(this);
        this.AbstractMethodDecl=AbstractMethodDecl;
        if(AbstractMethodDecl!=null) AbstractMethodDecl.setParent(this);
    }

    public AbstractAndBasicMethodDeclList getAbstractAndBasicMethodDeclList() {
        return AbstractAndBasicMethodDeclList;
    }

    public void setAbstractAndBasicMethodDeclList(AbstractAndBasicMethodDeclList AbstractAndBasicMethodDeclList) {
        this.AbstractAndBasicMethodDeclList=AbstractAndBasicMethodDeclList;
    }

    public AbstractMethodDecl getAbstractMethodDecl() {
        return AbstractMethodDecl;
    }

    public void setAbstractMethodDecl(AbstractMethodDecl AbstractMethodDecl) {
        this.AbstractMethodDecl=AbstractMethodDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractAndBasicMethodDeclList!=null) AbstractAndBasicMethodDeclList.accept(visitor);
        if(AbstractMethodDecl!=null) AbstractMethodDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractAndBasicMethodDeclList!=null) AbstractAndBasicMethodDeclList.traverseTopDown(visitor);
        if(AbstractMethodDecl!=null) AbstractMethodDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractAndBasicMethodDeclList!=null) AbstractAndBasicMethodDeclList.traverseBottomUp(visitor);
        if(AbstractMethodDecl!=null) AbstractMethodDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractAndBasicMethodAbstract(\n");

        if(AbstractAndBasicMethodDeclList!=null)
            buffer.append(AbstractAndBasicMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AbstractMethodDecl!=null)
            buffer.append(AbstractMethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractAndBasicMethodAbstract]");
        return buffer.toString();
    }
}
