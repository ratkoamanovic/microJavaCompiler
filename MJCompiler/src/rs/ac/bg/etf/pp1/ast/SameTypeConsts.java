// generated with ast extension for cup
// version 0.8
// 12/0/2020 19:54:18


package rs.ac.bg.etf.pp1.ast;

public class SameTypeConsts extends SameTypeConstList {

    private SameTypeConstList SameTypeConstList;
    private SameTypeConst SameTypeConst;

    public SameTypeConsts (SameTypeConstList SameTypeConstList, SameTypeConst SameTypeConst) {
        this.SameTypeConstList=SameTypeConstList;
        if(SameTypeConstList!=null) SameTypeConstList.setParent(this);
        this.SameTypeConst=SameTypeConst;
        if(SameTypeConst!=null) SameTypeConst.setParent(this);
    }

    public SameTypeConstList getSameTypeConstList() {
        return SameTypeConstList;
    }

    public void setSameTypeConstList(SameTypeConstList SameTypeConstList) {
        this.SameTypeConstList=SameTypeConstList;
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
        if(SameTypeConstList!=null) SameTypeConstList.accept(visitor);
        if(SameTypeConst!=null) SameTypeConst.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SameTypeConstList!=null) SameTypeConstList.traverseTopDown(visitor);
        if(SameTypeConst!=null) SameTypeConst.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SameTypeConstList!=null) SameTypeConstList.traverseBottomUp(visitor);
        if(SameTypeConst!=null) SameTypeConst.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SameTypeConsts(\n");

        if(SameTypeConstList!=null)
            buffer.append(SameTypeConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SameTypeConst!=null)
            buffer.append(SameTypeConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SameTypeConsts]");
        return buffer.toString();
    }
}
