// generated with ast extension for cup
// version 0.8
// 12/0/2020 19:54:18


package rs.ac.bg.etf.pp1.ast;

public class SingleSameTypeConst extends SameTypeConstList {

    private SameTypeConst SameTypeConst;

    public SingleSameTypeConst (SameTypeConst SameTypeConst) {
        this.SameTypeConst=SameTypeConst;
        if(SameTypeConst!=null) SameTypeConst.setParent(this);
    }

    public SameTypeConst getSameTypeConst() {
        return SameTypeConst;
    }

    public void setSameTypeConst(SameTypeConst SameTypeConst) {
        this.SameTypeConst=SameTypeConst;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SameTypeConst!=null) SameTypeConst.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SameTypeConst!=null) SameTypeConst.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SameTypeConst!=null) SameTypeConst.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleSameTypeConst(\n");

        if(SameTypeConst!=null)
            buffer.append(SameTypeConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleSameTypeConst]");
        return buffer.toString();
    }
}
