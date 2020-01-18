// generated with ast extension for cup
// version 0.8
// 12/0/2020 19:54:19


package rs.ac.bg.etf.pp1.ast;

public class VarDeclNoError extends VarDecl {

    private Type Type;
    private SameTypeVarList SameTypeVarList;

    public VarDeclNoError (Type Type, SameTypeVarList SameTypeVarList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.SameTypeVarList=SameTypeVarList;
        if(SameTypeVarList!=null) SameTypeVarList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public SameTypeVarList getSameTypeVarList() {
        return SameTypeVarList;
    }

    public void setSameTypeVarList(SameTypeVarList SameTypeVarList) {
        this.SameTypeVarList=SameTypeVarList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(SameTypeVarList!=null) SameTypeVarList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(SameTypeVarList!=null) SameTypeVarList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(SameTypeVarList!=null) SameTypeVarList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclNoError(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SameTypeVarList!=null)
            buffer.append(SameTypeVarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclNoError]");
        return buffer.toString();
    }
}
