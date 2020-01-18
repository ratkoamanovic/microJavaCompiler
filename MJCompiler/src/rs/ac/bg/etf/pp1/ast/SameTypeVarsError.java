// generated with ast extension for cup
// version 0.8
// 12/0/2020 19:54:19


package rs.ac.bg.etf.pp1.ast;

public class SameTypeVarsError extends SameTypeVarList {

    private SameTypeVar SameTypeVar;

    public SameTypeVarsError (SameTypeVar SameTypeVar) {
        this.SameTypeVar=SameTypeVar;
        if(SameTypeVar!=null) SameTypeVar.setParent(this);
    }

    public SameTypeVar getSameTypeVar() {
        return SameTypeVar;
    }

    public void setSameTypeVar(SameTypeVar SameTypeVar) {
        this.SameTypeVar=SameTypeVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SameTypeVar!=null) SameTypeVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SameTypeVar!=null) SameTypeVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SameTypeVar!=null) SameTypeVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SameTypeVarsError(\n");

        if(SameTypeVar!=null)
            buffer.append(SameTypeVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SameTypeVarsError]");
        return buffer.toString();
    }
}
