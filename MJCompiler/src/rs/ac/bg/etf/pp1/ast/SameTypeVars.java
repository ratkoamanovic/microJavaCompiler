// generated with ast extension for cup
// version 0.8
// 12/0/2020 19:54:19


package rs.ac.bg.etf.pp1.ast;

public class SameTypeVars extends SameTypeVarList {

    private SameTypeVarList SameTypeVarList;
    private SameTypeVar SameTypeVar;

    public SameTypeVars (SameTypeVarList SameTypeVarList, SameTypeVar SameTypeVar) {
        this.SameTypeVarList=SameTypeVarList;
        if(SameTypeVarList!=null) SameTypeVarList.setParent(this);
        this.SameTypeVar=SameTypeVar;
        if(SameTypeVar!=null) SameTypeVar.setParent(this);
    }

    public SameTypeVarList getSameTypeVarList() {
        return SameTypeVarList;
    }

    public void setSameTypeVarList(SameTypeVarList SameTypeVarList) {
        this.SameTypeVarList=SameTypeVarList;
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
        if(SameTypeVarList!=null) SameTypeVarList.accept(visitor);
        if(SameTypeVar!=null) SameTypeVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SameTypeVarList!=null) SameTypeVarList.traverseTopDown(visitor);
        if(SameTypeVar!=null) SameTypeVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SameTypeVarList!=null) SameTypeVarList.traverseBottomUp(visitor);
        if(SameTypeVar!=null) SameTypeVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SameTypeVars(\n");

        if(SameTypeVarList!=null)
            buffer.append(SameTypeVarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SameTypeVar!=null)
            buffer.append(SameTypeVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SameTypeVars]");
        return buffer.toString();
    }
}
