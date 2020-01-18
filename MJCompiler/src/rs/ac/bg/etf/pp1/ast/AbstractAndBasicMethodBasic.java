// generated with ast extension for cup
// version 0.8
// 12/0/2020 19:54:19


package rs.ac.bg.etf.pp1.ast;

public class AbstractAndBasicMethodBasic extends AbstractAndBasicMethodDeclList {

    private AbstractAndBasicMethodDeclList AbstractAndBasicMethodDeclList;
    private MethodDecl MethodDecl;

    public AbstractAndBasicMethodBasic (AbstractAndBasicMethodDeclList AbstractAndBasicMethodDeclList, MethodDecl MethodDecl) {
        this.AbstractAndBasicMethodDeclList=AbstractAndBasicMethodDeclList;
        if(AbstractAndBasicMethodDeclList!=null) AbstractAndBasicMethodDeclList.setParent(this);
        this.MethodDecl=MethodDecl;
        if(MethodDecl!=null) MethodDecl.setParent(this);
    }

    public AbstractAndBasicMethodDeclList getAbstractAndBasicMethodDeclList() {
        return AbstractAndBasicMethodDeclList;
    }

    public void setAbstractAndBasicMethodDeclList(AbstractAndBasicMethodDeclList AbstractAndBasicMethodDeclList) {
        this.AbstractAndBasicMethodDeclList=AbstractAndBasicMethodDeclList;
    }

    public MethodDecl getMethodDecl() {
        return MethodDecl;
    }

    public void setMethodDecl(MethodDecl MethodDecl) {
        this.MethodDecl=MethodDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractAndBasicMethodDeclList!=null) AbstractAndBasicMethodDeclList.accept(visitor);
        if(MethodDecl!=null) MethodDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractAndBasicMethodDeclList!=null) AbstractAndBasicMethodDeclList.traverseTopDown(visitor);
        if(MethodDecl!=null) MethodDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractAndBasicMethodDeclList!=null) AbstractAndBasicMethodDeclList.traverseBottomUp(visitor);
        if(MethodDecl!=null) MethodDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractAndBasicMethodBasic(\n");

        if(AbstractAndBasicMethodDeclList!=null)
            buffer.append(AbstractAndBasicMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDecl!=null)
            buffer.append(MethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractAndBasicMethodBasic]");
        return buffer.toString();
    }
}
