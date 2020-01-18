// generated with ast extension for cup
// version 0.8
// 12/0/2020 19:54:19


package rs.ac.bg.etf.pp1.ast;

public class DesignatorArray extends Designator {

    private OnlyArrayDesignator OnlyArrayDesignator;
    private Expr Expr;

    public DesignatorArray (OnlyArrayDesignator OnlyArrayDesignator, Expr Expr) {
        this.OnlyArrayDesignator=OnlyArrayDesignator;
        if(OnlyArrayDesignator!=null) OnlyArrayDesignator.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public OnlyArrayDesignator getOnlyArrayDesignator() {
        return OnlyArrayDesignator;
    }

    public void setOnlyArrayDesignator(OnlyArrayDesignator OnlyArrayDesignator) {
        this.OnlyArrayDesignator=OnlyArrayDesignator;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OnlyArrayDesignator!=null) OnlyArrayDesignator.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OnlyArrayDesignator!=null) OnlyArrayDesignator.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OnlyArrayDesignator!=null) OnlyArrayDesignator.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorArray(\n");

        if(OnlyArrayDesignator!=null)
            buffer.append(OnlyArrayDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorArray]");
        return buffer.toString();
    }
}
