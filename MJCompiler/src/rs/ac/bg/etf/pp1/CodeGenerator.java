package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;
	private int op;

	private Stack<Integer> jumpIfFalseAdrs = new Stack<>();
	private Stack<Integer> jumpIfTrueAdrs = new Stack<>();

	private Stack<Integer> jumpForFalseAdrs = new Stack<>();
	private Stack<Integer> jumpForTrueAdrs = new Stack<>();

	private Stack<Integer> forConditionAdress = new Stack<>();
	private Stack<Integer> forNextIter = new Stack<>();

	private Stack<ArrayList<Integer>> breakAdress = new Stack<>();

	private Stack<ArrayList<Integer>> andAdress = new Stack<>();
	private Stack<ArrayList<Integer>> orAdress = new Stack<>();

	public int getMainPc() {
		return mainPc;
	}

	public CodeGenerator() {
		
		Tab.ordObj.setAdr(Code.pc);
		// Generate the entry.
		Code.put(Code.enter);
		Code.put(Tab.ordObj.getLevel());
		Code.put(Tab.ordObj.getLocalSymbols().size());
		
		Code.put(Code.load_n);
		
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		Tab.chrObj.setAdr(Code.pc);
		// Generate the entry.
		Code.put(Code.enter);
		Code.put(Tab.chrObj.getLevel());
		Code.put(Tab.chrObj.getLocalSymbols().size());
		
		Code.put(Code.load_n);
		
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		Tab.lenObj.setAdr(Code.pc);
		// Generate the entry.
		Code.put(Code.enter);
		Code.put(Tab.lenObj.getLevel());
		Code.put(Tab.lenObj.getLocalSymbols().size());
		
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(VoidMethodTypeName methodTypeName) {
		// push bp
		// mov bp, sp
		methodTypeName.obj.setAdr(Code.pc);
		if ("main".equalsIgnoreCase(methodTypeName.getMethName())) {
			mainPc = Code.pc;
		}

		// Generate the entry.
		Code.put(Code.enter);
		Code.put(methodTypeName.obj.getLevel());
		Code.put(methodTypeName.obj.getLocalSymbols().size());
	}

	@Override
	public void visit(TypeMethodTypeName methodTypeName) {
		methodTypeName.obj.setAdr(Code.pc);
		// Generate the entry.
		Code.put(Code.enter);
		Code.put(methodTypeName.obj.getLevel());
		Code.put(methodTypeName.obj.getLocalSymbols().size());
	}

	@Override
	public void visit(MethodDeclNoError MethodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(StatementReturn ReturnExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(DesignatorStatementAssignop assignment) {
		Code.store(assignment.getDesignator().obj);
	}

	@Override
	public void visit(NumconstFactor Const) {
		Code.loadConst(Const.getN1());
	}

	@Override
	public void visit(CharconstFactor Const) {
		Code.loadConst(Const.getC1());
	}

	@Override
	public void visit(BoolconstFactor Const) {
		Code.loadConst(Const.getB1() ? 1 : 0);
	}

	@Override
	public void visit(DesignatorWithoutActFactor designatorWithoutActFactor) {
		Code.load(designatorWithoutActFactor.getDesignator().obj);
	}

	@Override
	public void visit(DesignatorWithActFactor funcCall) {
		Obj functionObj = funcCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}

	@Override
	public void visit(DesignatorStatementOptAct funcCall) {
		Obj functionObj = funcCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if (functionObj.getType() != Tab.noType)
			Code.put(Code.pop);
	}

	@Override
	public void visit(NewTypeExprFactor array) {
		Code.put(Code.newarray);
		if (array.getType().struct == Tab.charType) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}

	@Override
	public void visit(OnlyArrayDesignator designator) {
		Code.load(designator.getDesignator().obj);
	}

	@Override
	public void visit(MultyTerms addExpr) {
		if (addExpr.getAddop() instanceof AddopPlus)
			Code.put(Code.add);
		else
			Code.put(Code.sub);
	}

	@Override
	public void visit(MinusTerm minusTerm) {
		Code.put(Code.neg);
	}

	@Override
	public void visit(MulopFactorTerm mulFact) {
		if (mulFact.getMulop() instanceof MulopMultiply)
			Code.put(Code.mul);
		else if (mulFact.getMulop() instanceof MulopDivide)
			Code.put(Code.div);
		else
			Code.put(Code.rem);
	}

	@Override
	public void visit(DesignatorStatementMinusMinus designator) {
		if (designator.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designator.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designator.getDesignator().obj);
	}

	@Override
	public void visit(DesignatorStatementPlusPlus designator) {
		if (designator.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designator.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designator.getDesignator().obj);
	}

	@Override
	public void visit(StatementRead read) {
		if (read.getDesignator().obj.getType() == Tab.charType) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		Code.store(read.getDesignator().obj);
	}

	@Override
	public void visit(StatementPrintNoConst print) {
		Code.loadConst(1);
		if (print.getExpr().struct == Tab.charType) {
			Code.put(Code.bprint);
		} else {
			Code.put(Code.print);
		}
	}

	@Override
	public void visit(StatementPrintWithConst print) {
		Code.loadConst(print.getN2());
		if (print.getExpr().struct == Tab.charType) {
			Code.put(Code.bprint);
		} else {
			Code.put(Code.print);
		}
	}

	@Override
	public void visit(IfCondition ifCondition) {
		Code.putFalseJump(op, 0);
		jumpIfFalseAdrs.push(Code.pc - 2);
		for (int adress : orAdress.pop()) {
			Code.fixup(adress);
		}
	}

	@Override
	public void visit(StatementIf statementIf) {
		Code.fixup(jumpIfFalseAdrs.pop());
		for (int adress : andAdress.pop()) {
			Code.fixup(adress);
		}
	}

	@Override
	public void visit(Else elseSt) {
		Code.putJump(0);
		jumpIfTrueAdrs.push(Code.pc - 2);
		Code.fixup(jumpIfFalseAdrs.pop());
		for (int adress : andAdress.pop()) {
			Code.fixup(adress);
		}
	}

	@Override
	public void visit(StatementIfElse statementIfElse) {
		Code.fixup(jumpIfTrueAdrs.pop());
	}

	@Override
	public void visit(RelopCondFact relopCondFact) {
		if (relopCondFact.getRelop() instanceof RelopNotEqual)
			op = Code.ne;
		if (relopCondFact.getRelop() instanceof RelopEqualEqual)
			op = Code.eq;
		if (relopCondFact.getRelop() instanceof RelopGreater)
			op = Code.gt;
		if (relopCondFact.getRelop() instanceof RelopGreaterEqual)
			op = Code.ge;
		if (relopCondFact.getRelop() instanceof RelopLess)
			op = Code.lt;
		if (relopCondFact.getRelop() instanceof RelopLessEqual)
			op = Code.le;
	}

	@Override
	public void visit(ExprCondFact exprCondFact) {
		Code.loadConst(1);
		op = Code.eq;
	}

	@Override
	public void visit(WithCondition condition) {
		Code.putFalseJump(op, 0);
		jumpForFalseAdrs.push(Code.pc - 2);
		Code.putJump(0);
		jumpForTrueAdrs.push(Code.pc - 2);
		forNextIter.push(Code.pc);
	}

	@Override
	public void visit(NoCondition condition) {
		Code.putJump(0);
		jumpForTrueAdrs.push(Code.pc - 2);
		forNextIter.push(Code.pc);
	}

	@Override
	public void visit(ForRightParen rparen) {
		Code.putJump(forConditionAdress.pop());
		Code.fixup(jumpForTrueAdrs.pop());
		for (int adress : orAdress.pop()) {
			Code.fixup(adress);
		}
	}

	@Override
	public void visit(StatementFor fors) {
		Code.putJump(forNextIter.pop());
		Code.fixup(jumpForFalseAdrs.pop());
		for (int adress : breakAdress.pop()) {
			Code.fixup(adress);
		}
		for (int adress : andAdress.pop()) {
			Code.fixup(adress);
		}
	}

	@Override
	public void visit(ForSemi semi) {
		forConditionAdress.push(Code.pc);
		breakAdress.push(new ArrayList<>());
		andAdress.push(new ArrayList<>());
		orAdress.push(new ArrayList<>());
	}

	@Override
	public void visit(StatementContinue cont) {
		Code.putJump(forNextIter.peek());
	}

	@Override
	public void visit(StatementBreak breaks) {
		Code.putJump(0);
		breakAdress.peek().add(Code.pc - 2);
	}

	@Override
	public void visit(And and) {
		Code.putFalseJump(op, 0);
		andAdress.peek().add(Code.pc - 2);
	}

	@Override
	public void visit(Or or) {
		putTrueJump(op, 0);
		orAdress.peek().add(Code.pc - 2);
		for (int adress : andAdress.pop()) {
			Code.fixup(adress);
		}
		andAdress.push(new ArrayList<>());
	}

	@Override
	public void visit(IfLeftParen lparen) {
		andAdress.push(new ArrayList<>());
		orAdress.push(new ArrayList<>());
	}

	public static void putTrueJump(int op, int adr) {
		Code.put(Code.jcc + op);
		Code.put2(adr - Code.pc + 1);
	}
	
	

}
