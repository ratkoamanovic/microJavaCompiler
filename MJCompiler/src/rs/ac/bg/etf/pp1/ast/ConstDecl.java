// generated with ast extension for cup
// version 0.8
// 12/0/2020 19:54:18


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private SameTypeConstList SameTypeConstList;

    public ConstDecl (Type Type, SameTypeConstList SameTypeConstList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.SameTypeConstList=SameTypeConstList;
        if(SameTypeConstList!=null) SameTypeConstList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public SameTypeConstList getSameTypeConstList() {
        return SameTypeConstList;
    }

    public void setSameTypeConstList(SameTypeConstList SameTypeConstList) {
        this.SameTypeConstList=SameTypeConstList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(SameTypeConstList!=null) SameTypeConstList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(SameTypeConstList!=null) SameTypeConstList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(SameTypeConstList!=null) SameTypeConstList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SameTypeConstList!=null)
            buffer.append(SameTypeConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl]");
        return buffer.toString();
    }
}
