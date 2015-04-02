import JSHOP2.*;

class Operator0 extends Operator
{
	public Operator0()
	{
		super(new Predicate(0, 1, new TermList(TermVariable.getVariable(0), TermList.NIL)), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[0];

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[1];
		addIn[0] = new DelAddAtomic(new Predicate(2, 1, new TermList(TermVariable.getVariable(0), TermList.NIL)));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new PreconditionAtomic(new Predicate(1, 1, new TermList(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(0), TermList.NIL))), unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Operator1 extends Operator
{
	public Operator1()
	{
		super(new Predicate(1, 1, new TermList(TermVariable.getVariable(0), TermList.NIL)), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[0];

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[1];
		addIn[0] = new DelAddAtomic(new Predicate(4, 1, new TermList(TermVariable.getVariable(0), TermList.NIL)));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new PreconditionAtomic(new Predicate(1, 1, new TermList(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(3), TermList.NIL))), unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Method0 extends Method
{
	public Method0()
	{
		super(new Predicate(0, 2, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), TermList.NIL))));
		TaskList[] subsIn = new TaskList[1];

		subsIn[0] = createTaskList0();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(2, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(0, 2, new TermList(TermVariable.getVariable(0), TermList.NIL)), false, true));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(1, 2, new TermList(TermVariable.getVariable(1), TermList.NIL)), false, true));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new PreconditionNil(2)).setComparator(null);
			break;
			default:
				return null;
		}

		p.reset();

		return p;
	}

	public String getLabel(int which)
	{
		switch (which)
		{
			case 0: return "Method0Branch0";
			default: return null;
		}
	}
}

public class cloud extends Domain
{
	public cloud()
	{
		TermVariable.initialize(2);

		constants = new String[5];
		constants[0] = "createdatamodelontologyoperation";
		constants[1] = "hasapioperation";
		constants[2] = "havedataontology";
		constants[3] = "createdataelementsfromontologyoperation";
		constants[4] = "datamigrationsuccessfulto";

		compoundTasks = new String[1];
		compoundTasks[0] = "migratedata";

		primitiveTasks = new String[2];
		primitiveTasks[0] = "!createdatamodelontology";
		primitiveTasks[1] = "!createdataelementsfromontology";

		methods = new Method[1][];

		methods[0] = new Method[1];
		methods[0][0] = new Method0();


		ops = new Operator[2][];

		ops[0] = new Operator[1];
		ops[0][0] = new Operator0();

		ops[1] = new Operator[1];
		ops[1][0] = new Operator1();

		axioms = new Axiom[5][];

		axioms[0] = new Axiom[0];

		axioms[1] = new Axiom[0];

		axioms[2] = new Axiom[0];

		axioms[3] = new Axiom[0];

		axioms[4] = new Axiom[0];

	}
}