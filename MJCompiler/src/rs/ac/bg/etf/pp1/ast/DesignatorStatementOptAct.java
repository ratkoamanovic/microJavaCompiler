// generated with ast extension for cup
// version 0.8
// 12/0/2020 19:54:19


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementOptAct extends DesignatorStatement {

    private Designator Designator;
    private ActParsStartLeftParen ActParsStartLeftParen;
    private OptionalActPars OptionalActPars;

    public DesignatorStatementOptAct (Designator Designator, ActParsStartLeftParen ActParsStartLeftParen, OptionalActPars OptionalActPars) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.ActParsStartLeftParen=ActParsStartLeftParen;
        if(ActParsStartLeftParen!=null) ActParsStartLeftParen.setParent(this);
        this.OptionalActPars=OptionalActPars;
        if(OptionalActPars!=null) OptionalActPars.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public ActParsStartLeftParen getActParsStartLeftParen() {
        return ActParsStartLeftParen;
    }

    public void setActParsStartLeftParen(ActParsStartLeftParen ActParsStartLeftParen) {
        this.ActParsStartLeftParen=ActParsStartLeftParen;
    }

    public OptionalActPars getOptionalActPars() {
        return OptionalActPars;
    }

    public void setOptionalActPars(OptionalActPars OptionalActPars) {
        this.OptionalActPars=OptionalActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(ActParsStartLeftParen!=null) ActParsStartLeftParen.accept(visitor);
        if(OptionalActPars!=null) OptionalActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(ActParsStartLeftParen!=null) ActParsStartLeftParen.traverseTopDown(visitor);
        if(OptionalActPars!=null) OptionalActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(ActParsStartLeftParen!=null) ActParsStartLeftParen.traverseBottomUp(visitor);
        if(OptionalActPars!=null) OptionalActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementOptAct(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParsStartLeftParen!=null)
            buffer.append(ActParsStartLeftParen.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalActPars!=null)
            buffer.append(OptionalActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementOptAct]");
        return buffer.toString();
    }
}
