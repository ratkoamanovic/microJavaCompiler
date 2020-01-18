package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class SemanticAnalyzer extends VisitorAdaptor {

	boolean errorDetected = false;
	int printCallCount = 0;
	Obj currentMethod = null;
	Obj currentClass = null;
	boolean returnFound = false;
	int nVars;
	int currentForCount = 0;
	public static final Struct boolType = new Struct(Struct.Bool);
	Struct lastVisitedType;
	Stack<ArrayList<Struct>> actualParams = new Stack<>();

	Logger log = Logger.getLogger(getClass());
	private int numOfFormalParams = 0;

	public SemanticAnalyzer() {
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	public void visit(Program program) {
		Obj main = Tab.currentScope.findSymbol("main");
		if (main != null) {
			if (main.getKind() != Obj.Meth) {
				report_error("Main postoji u programu ali nije metoda " + program.getProgName().getPName(), program);
			} else {
				if (main.getType() != Tab.noType) {
					report_error("Main nije void " + program.getProgName().getPName(), program);
				}
				if (main.getLevel() > 0) {
					report_error("Main ima parametre " + program.getProgName().getPName(), program);
				}
			}
		} else {
			report_error("Main ne postoji u programu " + program.getProgName().getPName(), program);
		}
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}

	public void visit(ProgName progName) {
		if (Tab.currentScope.findSymbol(progName.getPName()) == null) {
			progName.obj = Tab.insert(Obj.Prog, progName.getPName(), Tab.noType);
		} else {
			progName.obj = new Obj(Obj.Prog, progName.getPName(), Tab.noType);
			report_error("Ovo ime vec postoji " + progName.getPName(), progName);
		}
		Tab.openScope();
	}

	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola", type);
			type.struct = Tab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip ", type);
				type.struct = Tab.noType;
			}
		}
		lastVisitedType = type.struct;
	}

	public void visit(StatementReturn statementReturn) {
		OptionalExpr optionalExpr = statementReturn.getOptionalExpr();
		Struct currMethType = currentMethod.getType();
		if (optionalExpr instanceof WithExpr) {
			WithExpr returnExpr = (WithExpr) optionalExpr;
			returnFound = true;
			if (!currMethType.compatibleWith(returnExpr.getExpr().struct)) {
				report_error("Greska na liniji " + returnExpr.getLine() + " : "
						+ "tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije "
						+ currentMethod.getName(), statementReturn);
			}
		} else {
			if (currMethType != Tab.noType) {
				report_error("Funkcija mora biti void ", statementReturn);
			}
		}
	}

	public void visit(MethodDeclNoError methodDecl) {
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Funkcija " + currentMethod.getName() + " nema return iskaz!", methodDecl);
		}

		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		currentMethod.setLevel(numOfFormalParams);

		numOfFormalParams = 0;
		returnFound = false;
		currentMethod = null;
	}

	public void visit(TypeMethodTypeName methodTypeName) {
		if (Tab.currentScope.findSymbol(methodTypeName.getMethName()) == null) {
			currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), methodTypeName.getType().struct);
			methodTypeName.obj = currentMethod;
		} else {
			currentMethod = new Obj(Obj.Meth, methodTypeName.getMethName(), methodTypeName.getType().struct);
			report_error("Ovo ime vec postoji " + methodTypeName.getMethName(), methodTypeName);
		}
		if (currentClass != null) {
			Tab.chainLocalSymbols(currentClass.getType());
		}
		Tab.openScope();
		if (currentClass != null) {
			Tab.insert(Obj.Var, "this", currentClass.getType());
		}
	}

	public void visit(VoidMethodTypeName methodTypeName) {
		if (Tab.currentScope.findSymbol(methodTypeName.getMethName()) == null) {
			currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), Tab.noType);
			methodTypeName.obj = currentMethod;
		} else {
			currentMethod = new Obj(Obj.Meth, methodTypeName.getMethName(), Tab.noType);
			report_error("Ovo ime vec postoji " + methodTypeName.getMethName(), methodTypeName);
		}
		if (currentClass != null) {
			Tab.chainLocalSymbols(currentClass.getType());
		}
		Tab.openScope();
		if (currentClass != null) {
			Tab.insert(Obj.Var, "this", currentClass.getType());
		}
	}

	public void visit(ClassDecl classDecl) {

		Tab.chainLocalSymbols(currentClass.getType());
		Tab.closeScope();

		currentClass = null;
	}

	public void visit(AbstractClassDecl classDecl) {

		Tab.chainLocalSymbols(currentClass.getType());
		Tab.closeScope();

		currentClass = null;
	}

	public void visit(ClassName className) {
		if (Tab.currentScope.findSymbol(className.getName()) == null) {
			currentClass = Tab.insert(Obj.Type, className.getName(), new Struct(Struct.Class));
			className.obj = currentClass;
		} else {
			className.obj = new Obj(Obj.Type, className.getName(), new Struct(Struct.Class));
			report_error("Ovo ime za klasu vec postoji " + className.getName(), className);
		}
		Tab.openScope();
	}

	public void visit(SameTypeConstNum constNum) {
		if (Tab.currentScope.findSymbol(constNum.getName()) == null) {
			Tab.insert(Obj.Con, constNum.getName(), lastVisitedType).setAdr(constNum.getValue());
		} else {
			report_error("Ovo ime za numericku konstantu vec postoji " + constNum.getName(), constNum);
		}
	}

	public void visit(SameTypeConstChar constChar) {
		if (Tab.currentScope.findSymbol(constChar.getName()) == null) {
			Tab.insert(Obj.Con, constChar.getName(), lastVisitedType).setAdr(constChar.getValue());
		} else {
			report_error("Ovo ime za string konstantu vec postoji " + constChar.getName(), constChar);
		}
	}

	public void visit(SameTypeConstBool constBool) {
		if (Tab.currentScope.findSymbol(constBool.getName()) == null) {
			Tab.insert(Obj.Con, constBool.getName(), lastVisitedType).setAdr(constBool.getValue() ? 1 : 0);
		} else {
			report_error("Ovo ime za numericku konstantu vec postoji " + constBool.getName(), constBool);
		}
	}

	public void visit(SameTypeVarArray varArray) {
		if (Tab.currentScope.findSymbol(varArray.getName()) == null) {
			if (currentMethod == null && currentClass != null)
				Tab.insert(Obj.Fld, varArray.getName(), new Struct(Struct.Array, lastVisitedType));
			else
				Tab.insert(Obj.Var, varArray.getName(), new Struct(Struct.Array, lastVisitedType));
		} else {
			report_error("Ovo ime za varijablu vec postoji " + varArray.getName(), varArray);
		}
	}

	public void visit(SameTypeVarOne oneVar) {
		if (Tab.currentScope.findSymbol(oneVar.getName()) == null) {
			if (currentMethod == null && currentClass != null)
				Tab.insert(Obj.Fld, oneVar.getName(), lastVisitedType);
			else
				Tab.insert(Obj.Var, oneVar.getName(), lastVisitedType);
		} else {
			report_error("Ovo ime za varijablu vec postoji " + oneVar.getName(), oneVar);
		}
	}

	public void visit(FormalParamDeclArray arrayParam) {
		if (Tab.currentScope.findSymbol(arrayParam.getName()) == null) {
			Tab.insert(Obj.Var, arrayParam.getName(), new Struct(Struct.Array, lastVisitedType))
					.setFpPos(++numOfFormalParams);
		} else {
			report_error("Ovo ime za niz u deklaraciji metode vec postoji " + arrayParam.getName(), arrayParam);
		}
	}

	public void visit(FormalParamDeclSingle singleVar) {
		if (Tab.currentScope.findSymbol(singleVar.getName()) == null) {
			Tab.insert(Obj.Var, singleVar.getName(), lastVisitedType).setFpPos(++numOfFormalParams);
		} else {
			report_error("Ovo ime za varijablu u deklaraciji metode vec postoji " + singleVar.getName(), singleVar);
		}

	}

	public void visit(DesignatorStatementAssignop assignment) {
		Obj obj = assignment.getDesignator().obj;
		int kind = obj.getKind();
		if (kind == Obj.Var || kind == Obj.Fld || kind == Obj.Elem) {
			if (!assignment.getExpr().struct.assignableTo(obj.getType()))
				report_error("Greska na liniji " + assignment.getLine() + " : "
						+ " nekompatibilni tipovi u dodeli vrednosti ", null);
		}
	}

	public void visit(DesignatorStatementOptAct function) {
		Obj functionObj = function.getDesignator().obj;
		processFunction(function, functionObj);
	}

	private void processFunction(SyntaxNode function, Obj functionObj) {
		int kind = functionObj.getKind();
		if (kind != Obj.Meth) {
			report_error("Greska na liniji " + function.getLine() + " : " + " ovo nije ime funkcije ", function);
		}
		ArrayList<Struct> currentFormal = new ArrayList<>();
		ArrayList<Struct> currentActual = actualParams.pop();
		int cnt = 0;
		for (Obj obj : functionObj.getLocalSymbols()) {
			if (cnt == functionObj.getLevel())
				break;
			currentFormal.add(obj.getType());
			cnt++;

		}
		if (currentActual.size() != currentFormal.size()) {
			report_error("Greska na liniji " + function.getLine() + " : "
					+ " formalni i stvarni parametri nisu iste duzine ", function);
		} else {
			for (int i = 0; i < currentActual.size(); i++) {
				if (!currentActual.get(i).assignableTo(currentFormal.get(i))) {
					report_error("Greska na liniji " + function.getLine() + " : " + " stvarni parametar broj " + (i + 1)
							+ " nije istog tipa kao formalni ", function);
				}
			}
		}
	}

	public void visit(ActParsStartLeftParen leftParen) {
		actualParams.push(new ArrayList<>());
	}

	public void visit(ActPar actPar) {
		actualParams.peek().add(actPar.getExpr().struct);
	}

	public void visit(DesignatorStatementPlusPlus assignment) {
		Obj obj = assignment.getDesignator().obj;
		int kind = obj.getKind();
		if (!(kind == Obj.Var || kind == Obj.Fld || kind == Obj.Elem)) {
			report_error("Greska na liniji " + assignment.getLine() + " : "
					+ " nije odgovarajuci tip designatora za dodelu vrednosti ", null);
		}
		if (!Tab.intType.assignableTo(obj.getType()))
			report_error(
					"Greska na liniji " + assignment.getLine() + " : " + " nekompatibilni tipovi u dodeli vrednosti ",
					null);
	}

	public void visit(DesignatorStatementMinusMinus assignment) {
		Obj obj = assignment.getDesignator().obj;
		int kind = obj.getKind();
		if (!(kind == Obj.Var || kind == Obj.Fld || kind == Obj.Elem)) {
			report_error("Greska na liniji " + assignment.getLine() + " : "
					+ " nije odgovarajuci tip designatora za dodelu vrednosti ", null);
		}
		if (!Tab.intType.assignableTo(obj.getType()))
			report_error(
					"Greska na liniji " + assignment.getLine() + " : " + " nekompatibilni tipovi u dodeli vrednosti ",
					null);
	}

	public void visit(StatementRead assignment) {
		Obj obj = assignment.getDesignator().obj;
		int kind = obj.getKind();
		if (!(kind == Obj.Var || kind == Obj.Fld || kind == Obj.Elem)) {
			report_error("Greska na liniji " + assignment.getLine() + " : "
					+ " nije odgovarajuci tip designatora za dodelu vrednosti ", null);
		}
		if (!(Tab.intType.assignableTo(obj.getType()) || Tab.charType.assignableTo(obj.getType())
				|| boolType.assignableTo(obj.getType())))
			report_error("Greska na liniji " + assignment.getLine() + " : "
					+ " nekompatibilni tipovi u dodeli vrednosti pri uctiavanju varijable ", null);
	}

	public void visit(StatementPrintNoConst print) {
		Struct currType = print.getExpr().struct;
		if (!(Tab.intType.assignableTo(currType) || Tab.charType.assignableTo(currType)
				|| boolType.assignableTo(currType)))
			report_error("Greska na liniji " + print.getLine() + " : "
					+ " nekompatibilni tipovi u dodeli vrednosti pri ispisu varijable ", null);

	}

	public void visit(ForRightParen forRightParen) {
		currentForCount++;
	}

	public void visit(StatementFor statementFor) {
		report_info("Detektovano koriscenje for petlje", statementFor);
		currentForCount--;
	}

	public void visit(StatementBreak statementBreak) {
		if (currentForCount < 1) {
			report_error("Greska na liniji " + statementBreak.getLine() + " : " + " break van for petlje", null);
		}
	}

	public void visit(StatementContinue statementContinue) {
		if (currentForCount < 1) {
			report_error("Greska na liniji " + statementContinue.getLine() + " : " + " continue van for petlje", null);
		}
	}

	public void visit(ExprCondFact exprCondFact) {
		Struct t = exprCondFact.getExpr().struct;
		if (!boolType.assignableTo(t))
			report_error("Greska na liniji " + exprCondFact.getLine() + " : " + " nije bool tip u contition izrazu ",
					null);
	}

	public void visit(RelopCondFact relopCondFact) {
		Struct t = relopCondFact.getExpr().struct;
		Struct tt = relopCondFact.getExpr1().struct;
		if (!t.compatibleWith(tt)) {
			report_error(
					"Greska na liniji " + relopCondFact.getLine() + " : " + " nekompatibilni tipovi za poredjenje ",
					null);
		} else if (!(relopCondFact.getRelop() instanceof RelopEqualEqual
				|| relopCondFact.getRelop() instanceof RelopNotEqual)
				&& (t.getKind() == Struct.Class || t.getKind() == Struct.Array)) {
			report_error("Greska na liniji " + relopCondFact.getLine() + " : "
					+ " na nizove i klase se mogu primeniti samo == i != ", null);
		}
	}

	public void visit(MultyTerms addExpr) {
		Struct te = addExpr.getExpr().struct;
		Struct t = addExpr.getTerm().struct;
		if (te.equals(t) && te == Tab.intType)
			addExpr.struct = te;
		else {
			report_error("Greska na liniji " + addExpr.getLine() + " : nekompatibilni tipovi u izrazu za sabiranje."
					+ t.getKind(), null);
			addExpr.struct = Tab.noType;
		}
	}

	public void visit(PlusTerm addExpr) {
		addExpr.struct = addExpr.getTerm().struct;
	}

	public void visit(MinusTerm addExpr) {
		Struct t = addExpr.getTerm().struct;
		if (t == Tab.intType)
			addExpr.struct = t;
		else {
			report_error("Greska na liniji " + addExpr.getLine() + " : nekompatibilni tipovi u izrazu za sabiranje."
					+ t.getKind(), null);
			addExpr.struct = Tab.noType;
		}
	}

	public void visit(MulopFactorTerm mulFact) {
		Struct te = mulFact.getFactor().struct;
		Struct t = mulFact.getTerm().struct;
		if (te.equals(t) && te == Tab.intType)
			mulFact.struct = te;
		else {
			report_error("Greska na liniji " + mulFact.getLine() + " : nekompatibilni tipovi u izrazu za mnozenje."
					+ t.getKind(), null);
			mulFact.struct = Tab.noType;
		}
	}

	public void visit(SingleFactorTerm mulFact) {
		mulFact.struct = mulFact.getFactor().struct;
	}

	public void visit(DesignatorWithActFactor function) {
		Obj functionObj = function.getDesignator().obj;
		processFunction(function, functionObj);
		function.struct = functionObj.getType();
	}

	public void visit(DesignatorWithoutActFactor designator) {
		designator.struct = designator.getDesignator().obj.getType();
	}

	public void visit(NumconstFactor numConst) {
		numConst.struct = Tab.intType;
	}

	public void visit(CharconstFactor charConst) {
		charConst.struct = Tab.charType;
	}

	public void visit(BoolconstFactor boolConst) {
		boolConst.struct = boolType;
	}

	public void visit(NewTypeExprFactor typeExpr) {
		Struct t = typeExpr.getType().struct;
		typeExpr.struct = new Struct(Struct.Array, t);
		Struct tt = typeExpr.getExpr().struct;
		if (!tt.assignableTo(Tab.intType)) {
			report_error("Greska na liniji " + typeExpr.getLine() + " : dimenzija niza nije tipa int ", null);
		}
	}

	public void visit(NewTypeFactor typeExpr) {
		typeExpr.struct = typeExpr.getType().struct;
	}

	public void visit(ParenthesesFactor typeExpr) {
		typeExpr.struct = typeExpr.getExpr().struct;
	}

	public void visit(DesignatorClass designatorClass) {
		int kind = designatorClass.getDesignator().obj.getKind();
		if (kind == Obj.Var || kind == Obj.Fld || kind == Obj.Elem) {
			designatorClass.obj = designatorClass.getDesignator().obj.getType().getMembersTable()
					.searchKey(designatorClass.getName());
			if (designatorClass.obj == null) {
				report_error("Greska na liniji " + designatorClass.getLine()
						+ " : nije ubaceno nista u designator class " + kind, null);
				designatorClass.obj = Tab.noObj;
			}
		}
	}

	public void visit(DesignatorArray designatorArray) {
		Struct t = designatorArray.getOnlyArrayDesignator().getDesignator().obj.getType();
		if (t.getKind() != Struct.Array) {
			report_error("Greska na liniji " + designatorArray.getLine() + " : designator nije tipa niza ", null);
		} else {
			DumpSymbolTableVisitor printer = new DumpSymbolTableVisitor();
			printer.visitObjNode(designatorArray.getOnlyArrayDesignator().getDesignator().obj);
			report_info("Detektovan pristup elementu niza : " + printer.getOutput(), designatorArray);
		}
		designatorArray.obj = new Obj(Obj.Elem, "Elem", t.getElemType());
		Struct tt = designatorArray.getExpr().struct;
		if (!tt.assignableTo(Tab.intType)) {
			report_error("Greska na liniji " + designatorArray.getLine() + " : indeks niza nije tipa int ", null);
		}
	}

	public void visit(SimpleDesignator designator) {
		Obj obj = Tab.find(designator.getName());
		if (obj == Tab.noObj) {
			report_error("Greska na liniji " + designator.getLine() + " : ime " + designator.getName()
					+ " nije deklarisano! ", null);
		} else {
			DumpSymbolTableVisitor printer = new DumpSymbolTableVisitor();
			printer.visitObjNode(obj);
			if (obj.getKind() == Obj.Con)
				report_info("Detektovano koriscenje konstante : " + printer.getOutput(), designator);
			else if (obj.getKind() == Obj.Meth)
				report_info("Detektovano koriscenje globalne metode : " + printer.getOutput(), designator);
			else if (obj.getKind() == Obj.Var)
				if (obj.getLevel() == 0)
					report_info("Detektovano koriscenje globalne promenljive : " + printer.getOutput(), designator);
				else if (obj.getFpPos() > 0)
					report_info("Detektovano koriscenje formalnog parametra : " + printer.getOutput(), designator);
		}
		designator.obj = obj;
	}

	public boolean passed() {
		return !errorDetected;
	}

	public void visit(StatementForError error) {
		report_info("Uspesan oporavak od sintaksne greske u uslovu za for do ;", error);
	}

	public void visit(VarDeclError error) {
		report_info("Uspesan oporavak od sintaksne greske pri deklarisanju promenljive do ;", error);
	}
	
	public void visit(SameTypeVarsError error) {
		report_info("Uspesan oporavak od sintaksne greske pri deklarisanju promenljive do ,", error);
	}
	
	public void visit(AbstractMethodDeclError error) {
		report_info("Uspesan oporavak od sintaksne greske pri deklarisanju formalnih parametara abstraktne metode do )", error);
	}
	
	public void visit(MethodDeclError error) {
		report_info("Uspesan oporavak od sintaksne greske pri deklarisanju formalnih parametara metode do )", error);
	}

	public void visit(FormalParamDeclsError error) {
		report_info("Uspesan oporavak od sintaksne greske pri deklarisanju formalnih parametara metode do ,", error);
	}
	
	public void visit(DesignatorStatementAssignopError error) {
		report_info("Uspesan oporavak od sintaksne greske pri dodeli vrednosti do ;", error);
	}

}
