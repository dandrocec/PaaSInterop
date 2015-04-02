package hr.org.foi.generated.aifiles;
import JSHOP2.*;

class Operator0 extends Operator
{
	public Operator0()
	{
		super(new Predicate(0, 3, new TermList(TermVariable.getVariable(0), TermList.NIL)), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[0];

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[1];
		addIn[0] = new DelAddAtomic(new Predicate(2, 3, new TermList(TermVariable.getVariable(0), TermList.NIL)));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new PreconditionForAll(new PreconditionAtomic(new Predicate(0, 3, new TermList(TermVariable.getVariable(1), TermList.NIL)), unifier), new PreconditionAtomic(new Predicate(1, 3, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), unifier), 3)).setComparator(null);
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
		addIn[0] = new DelAddAtomic(new Predicate(5, 1, new TermList(TermVariable.getVariable(0), TermList.NIL)));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new PreconditionAtomic(new Predicate(4, 1, new TermList(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(3), TermList.NIL))), unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Operator2 extends Operator
{
	public Operator2()
	{
		super(new Predicate(2, 1, new TermList(TermVariable.getVariable(0), TermList.NIL)), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[0];

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[1];
		addIn[0] = new DelAddAtomic(new Predicate(7, 1, new TermList(TermVariable.getVariable(0), TermList.NIL)));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new PreconditionAtomic(new Predicate(4, 1, new TermList(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(6), TermList.NIL))), unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Precondition0 extends Precondition
{
	Precondition[] p;
	Term[][] b;

	public Precondition0(Term[] unifier)
	{
		p = new Precondition[7];
		p[1] = new PreconditionAtomic(new Predicate(9, 8, new TermList(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(8), new TermList(TermVariable.getVariable(2), TermList.NIL)))), unifier);
		p[2] = new PreconditionAtomic(new Predicate(11, 8, new TermList(TermVariable.getVariable(1), new TermList(TermConstant.getConstant(10), new TermList(TermVariable.getVariable(3), TermList.NIL)))), unifier);
		p[3] = new PreconditionAtomic(new Predicate(11, 8, new TermList(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(12), new TermList(TermVariable.getVariable(4), TermList.NIL)))), unifier);
		p[4] = new PreconditionAtomic(new Predicate(14, 8, new TermList(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(13), new TermList(TermVariable.getVariable(5), TermList.NIL)))), unifier);
		p[5] = new PreconditionAtomic(new Predicate(16, 8, new TermList(TermVariable.getVariable(1), new TermList(TermConstant.getConstant(15), new TermList(TermVariable.getVariable(6), TermList.NIL)))), unifier);
		p[6] = new PreconditionAtomic(new Predicate(16, 8, new TermList(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(17), new TermList(TermVariable.getVariable(7), TermList.NIL)))), unifier);
		b = new Term[7][];
		b[0] = unifier;
		b[0] = Term.merge( b, 1 );

		setFirst(false);
	}

	public void bind(Term[] binding)
	{
		b[0] = binding;
		b[0] = Term.merge( b, 1 );
		p[1].bind(binding);
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
	}

	protected Term[] nextBindingHelper()
	{
		while (b[6] == null)
		{
			boolean b5changed = false;
			while (b[5] == null)
			{
				boolean b4changed = false;
				while (b[4] == null)
				{
					boolean b3changed = false;
					while (b[3] == null)
					{
						boolean b2changed = false;
						while (b[2] == null)
						{
							boolean b1changed = false;
							while (b[1] == null)
							{
								b[1] = p[1].nextBinding();
								if (b[1] == null)
									return null;
								b1changed = true;
							}
							if ( b1changed ) {
								p[2].reset();
								p[2].bind(Term.merge(b, 2));
							}
							b[2] = p[2].nextBinding();
							if (b[2] == null) b[1] = null;
							b2changed = true;
						}
						if ( b2changed ) {
							p[3].reset();
							p[3].bind(Term.merge(b, 3));
						}
						b[3] = p[3].nextBinding();
						if (b[3] == null) b[2] = null;
						b3changed = true;
					}
					if ( b3changed ) {
						p[4].reset();
						p[4].bind(Term.merge(b, 4));
					}
					b[4] = p[4].nextBinding();
					if (b[4] == null) b[3] = null;
					b4changed = true;
				}
				if ( b4changed ) {
					p[5].reset();
					p[5].bind(Term.merge(b, 5));
				}
				b[5] = p[5].nextBinding();
				if (b[5] == null) b[4] = null;
				b5changed = true;
			}
			if ( b5changed ) {
				p[6].reset();
				p[6].bind(Term.merge(b, 6));
			}
			b[6] = p[6].nextBinding();
			if (b[6] == null) b[5] = null;
		}

		Term[] retVal = Term.merge(b, 7);
		b[6] = null;
		return retVal;
	}

	protected void resetHelper()
	{
		p[1].reset();
		p[2].reset();
		p[3].reset();
		p[4].reset();
		p[5].reset();
		p[6].reset();
		b[1] = null;
		b[2] = null;
		b[3] = null;
		b[4] = null;
		b[5] = null;
		b[6] = null;
	}
}

class Operator3 extends Operator
{
	public Operator3()
	{
		super(new Predicate(3, 8, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), TermList.NIL))), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[0];

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[3];
		addIn[0] = new DelAddAtomic(new Predicate(18, 8, new TermList(TermConstant.getConstant(8), new TermList(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(13), new TermList(TermVariable.getVariable(5), TermList.NIL))))));
		addIn[1] = new DelAddAtomic(new Predicate(18, 8, new TermList(TermConstant.getConstant(10), new TermList(TermVariable.getVariable(1), new TermList(TermConstant.getConstant(15), new TermList(TermVariable.getVariable(6), TermList.NIL))))));
		addIn[2] = new DelAddAtomic(new Predicate(18, 8, new TermList(TermConstant.getConstant(12), new TermList(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(17), new TermList(TermVariable.getVariable(7), TermList.NIL))))));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new Precondition0(unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Operator4 extends Operator
{
	public Operator4()
	{
		super(new Predicate(4, 1, new TermList(TermVariable.getVariable(0), TermList.NIL)), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[0];

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[1];
		addIn[0] = new DelAddAtomic(new Predicate(20, 1, new TermList(TermVariable.getVariable(0), TermList.NIL)));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new PreconditionAtomic(new Predicate(4, 1, new TermList(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(19), TermList.NIL))), unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Operator5 extends Operator
{
	public Operator5()
	{
		super(new Predicate(5, 1, new TermList(TermVariable.getVariable(0), TermList.NIL)), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[0];

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[1];
		addIn[0] = new DelAddAtomic(new Predicate(21, 1, new TermList(TermVariable.getVariable(0), TermList.NIL)));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new PreconditionAtomic(new Predicate(4, 1, new TermList(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(8), TermList.NIL))), unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Operator6 extends Operator
{
	public Operator6()
	{
		super(new Predicate(6, 2, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), TermList.NIL))), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[0];

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[1];
		addIn[0] = new DelAddAtomic(new Predicate(22, 2, new TermList(TermVariable.getVariable(0), TermList.NIL)));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new PreconditionAtomic(new Predicate(4, 2, new TermList(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(10), TermList.NIL))), unifier)).setComparator(null);
		p.reset();

		return p;
	}
}

class Operator7 extends Operator
{
	public Operator7()
	{
		super(new Predicate(7, 1, new TermList(TermVariable.getVariable(0), TermList.NIL)), -1, -1, new TermNumber(1.0));


		DelAddElement[] delIn = new DelAddElement[0];

		setDel(delIn);

		DelAddElement[] addIn = new DelAddElement[1];
		addIn[0] = new DelAddAtomic(new Predicate(23, 1, new TermList(TermVariable.getVariable(0), TermList.NIL)));

		setAdd(addIn);
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		p = (new PreconditionAtomic(new Predicate(4, 1, new TermList(TermVariable.getVariable(0), new TermList(TermConstant.getConstant(12), TermList.NIL))), unifier)).setComparator(null);
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

		retVal = new TaskList(3, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(0, 2, new TermList(TermVariable.getVariable(0), TermList.NIL)), false, true));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(1, 2, new TermList(TermVariable.getVariable(0), TermList.NIL)), false, true));
		retVal.subtasks[2] = new TaskList(new TaskAtom(new Predicate(2, 2, new TermList(TermVariable.getVariable(1), TermList.NIL)), false, true));

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

class Method1 extends Method
{
	public Method1()
	{
		super(new Predicate(1, 3, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL)))));
		TaskList[] subsIn = new TaskList[1];

		subsIn[0] = createTaskList0();

		setSubs(subsIn);
	}

	TaskList createTaskList0()
	{
		TaskList retVal;

		retVal = new TaskList(5, true);
		retVal.subtasks[0] = new TaskList(new TaskAtom(new Predicate(3, 3, new TermList(TermVariable.getVariable(0), new TermList(TermVariable.getVariable(1), TermList.NIL))), false, true));
		retVal.subtasks[1] = new TaskList(new TaskAtom(new Predicate(4, 3, new TermList(TermVariable.getVariable(0), TermList.NIL)), false, true));
		retVal.subtasks[2] = new TaskList(new TaskAtom(new Predicate(5, 3, new TermList(TermVariable.getVariable(0), TermList.NIL)), false, true));
		retVal.subtasks[3] = new TaskList(new TaskAtom(new Predicate(6, 3, new TermList(TermVariable.getVariable(1), new TermList(TermVariable.getVariable(2), TermList.NIL))), false, true));
		retVal.subtasks[4] = new TaskList(new TaskAtom(new Predicate(7, 3, new TermList(TermVariable.getVariable(0), TermList.NIL)), false, true));

		return retVal;
	}

	public Precondition getIterator(Term[] unifier, int which)
	{
		Precondition p;

		switch (which)
		{
			case 0:
				p = (new PreconditionNil(3)).setComparator(null);
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
			case 0: return "Method1Branch0";
			default: return null;
		}
	}
}

public class cloud extends Domain
{
	public cloud()
	{
		TermVariable.initialize(8);

		constants = new String[24];
		constants[0] = "typeincurrentdata";
		constants[1] = "datatypemappingexists";
		constants[2] = "hasalldatatypemappings";
		constants[3] = "createdatamodelontologyoperation";
		constants[4] = "hasapioperation";
		constants[5] = "havedataontology";
		constants[6] = "createdataelementsfromontologyoperation";
		constants[7] = "datamigrationsuccessfulto";
		constants[8] = "getuserinfooperation";
		constants[9] = "operationhasoutput";
		constants[10] = "createdataoperation";
		constants[11] = "operationhasinput";
		constants[12] = "sendemailoperation";
		constants[13] = "userinfotype";
		constants[14] = "typehasliftingschema";
		constants[15] = "nosqldataobjecttype";
		constants[16] = "typehasloweringschema";
		constants[17] = "emailmessagetype";
		constants[18] = "transformationduringexecution";
		constants[19] = "loginoperation";
		constants[20] = "userisloggedin";
		constants[21] = "userinfoisobtained";
		constants[22] = "dataobjectiscreated";
		constants[23] = "emailissent";

		compoundTasks = new String[2];
		compoundTasks[0] = "migratedata";
		compoundTasks[1] = "addusertoanotherpaas";

		primitiveTasks = new String[8];
		primitiveTasks[0] = "!checkdatatypemappings";
		primitiveTasks[1] = "!createdatamodelontology";
		primitiveTasks[2] = "!createdataelementsfromontology";
		primitiveTasks[3] = "!checkadduserservicedatatypemappings";
		primitiveTasks[4] = "!login";
		primitiveTasks[5] = "!getuserinfo";
		primitiveTasks[6] = "!createdata";
		primitiveTasks[7] = "!sendemail";

		methods = new Method[2][];

		methods[0] = new Method[1];
		methods[0][0] = new Method0();

		methods[1] = new Method[1];
		methods[1][0] = new Method1();


		ops = new Operator[8][];

		ops[0] = new Operator[1];
		ops[0][0] = new Operator0();

		ops[1] = new Operator[1];
		ops[1][0] = new Operator1();

		ops[2] = new Operator[1];
		ops[2][0] = new Operator2();

		ops[3] = new Operator[1];
		ops[3][0] = new Operator3();

		ops[4] = new Operator[1];
		ops[4][0] = new Operator4();

		ops[5] = new Operator[1];
		ops[5][0] = new Operator5();

		ops[6] = new Operator[1];
		ops[6][0] = new Operator6();

		ops[7] = new Operator[1];
		ops[7][0] = new Operator7();

		axioms = new Axiom[24][];

		axioms[0] = new Axiom[0];

		axioms[1] = new Axiom[0];

		axioms[2] = new Axiom[0];

		axioms[3] = new Axiom[0];

		axioms[4] = new Axiom[0];

		axioms[5] = new Axiom[0];

		axioms[6] = new Axiom[0];

		axioms[7] = new Axiom[0];

		axioms[8] = new Axiom[0];

		axioms[9] = new Axiom[0];

		axioms[10] = new Axiom[0];

		axioms[11] = new Axiom[0];

		axioms[12] = new Axiom[0];

		axioms[13] = new Axiom[0];

		axioms[14] = new Axiom[0];

		axioms[15] = new Axiom[0];

		axioms[16] = new Axiom[0];

		axioms[17] = new Axiom[0];

		axioms[18] = new Axiom[0];

		axioms[19] = new Axiom[0];

		axioms[20] = new Axiom[0];

		axioms[21] = new Axiom[0];

		axioms[22] = new Axiom[0];

		axioms[23] = new Axiom[0];

	}
}