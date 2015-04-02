import java.util.LinkedList;
import JSHOP2.*;

public class problem
{
	private static String[] defineConstants()
	{
		String[] problemConstants = new String[26];

		problemConstants[0] = "azure";
		problemConstants[1] = "createdataoperation";
		problemConstants[2] = "deletedataoperation";
		problemConstants[3] = "querydataoperation";
		problemConstants[4] = "googleappengine";
		problemConstants[5] = "retrievedataoperation";
		problemConstants[6] = "salesforce";
		problemConstants[7] = "searchdataoperation";
		problemConstants[8] = "getuserinfooperation";
		problemConstants[9] = "mergedataoperation";
		problemConstants[10] = "updatedataoperation";
		problemConstants[11] = "setpasswordoperation";
		problemConstants[12] = "loginoperation";
		problemConstants[13] = "getupdateddataoperation";
		problemConstants[14] = "logoutoperation";
		problemConstants[15] = "emptyrecyclebinoperation";
		problemConstants[16] = "getdeleteddataoperation";
		problemConstants[17] = "invalidatesessionoperation";
		problemConstants[18] = "undeletefromrecyclebindataoperation";
		problemConstants[19] = "upsertdataoperation";
		problemConstants[20] = "listmetadataoperation";
		problemConstants[21] = "changepasswordoperation";
		problemConstants[22] = "sendemailoperation";
		problemConstants[23] = "createmetadataoperation";
		problemConstants[24] = "deletemetadataoperation";
		problemConstants[25] = "updatemetadataoperation";

		return problemConstants;
	}

	private static void createState0(State s)	{
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(5), new TermList(TermConstant.getConstant(6), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(5), new TermList(TermConstant.getConstant(7), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(5), new TermList(TermConstant.getConstant(8), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(5), new TermList(TermConstant.getConstant(0), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(5), new TermList(TermConstant.getConstant(3), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(9), new TermList(TermConstant.getConstant(8), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(9), new TermList(TermConstant.getConstant(6), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(9), new TermList(TermConstant.getConstant(7), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(9), new TermList(TermConstant.getConstant(10), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(9), new TermList(TermConstant.getConstant(3), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(9), new TermList(TermConstant.getConstant(0), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(7), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(12), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(6), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(13), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(8), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(14), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(15), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(10), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(16), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(17), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(18), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(19), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(20), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(21), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(22), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(23), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(24), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(25), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(26), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(27), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(28), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(29), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(30), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(3), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(0), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(5), new TermList(TermConstant.getConstant(6), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(5), new TermList(TermConstant.getConstant(7), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(5), new TermList(TermConstant.getConstant(8), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(5), new TermList(TermConstant.getConstant(0), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(5), new TermList(TermConstant.getConstant(3), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(9), new TermList(TermConstant.getConstant(8), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(9), new TermList(TermConstant.getConstant(6), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(9), new TermList(TermConstant.getConstant(7), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(9), new TermList(TermConstant.getConstant(10), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(9), new TermList(TermConstant.getConstant(3), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(9), new TermList(TermConstant.getConstant(0), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(7), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(12), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(6), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(13), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(8), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(14), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(15), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(10), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(16), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(17), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(18), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(19), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(20), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(21), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(22), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(23), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(24), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(25), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(26), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(27), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(28), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(29), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(30), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(3), TermList.NIL))));
		s.add(new Predicate(1, 0, new TermList(TermConstant.getConstant(11), new TermList(TermConstant.getConstant(0), TermList.NIL))));
	}

	public static LinkedList<Plan> getPlans()
	{
		LinkedList<Plan> returnedPlans = new LinkedList<Plan>();
		TermConstant.initialize(31);

		Domain d = new cloud();

		d.setProblemConstants(defineConstants());

		State s = new State(5, d.getAxioms());

		JSHOP2.initialize(d, s);

		TaskList tl;
		SolverThread thread;

		createState0(s);

		tl = new TaskList(1, true);
		tl.subtasks[0] = new TaskList(new TaskAtom(new Predicate(0, 0, new TermList(TermConstant.getConstant(5), new TermList(TermConstant.getConstant(9), TermList.NIL))), false, false));

		thread = new SolverThread(tl, 2147483647);
		thread.start();

		try {
			while (thread.isAlive())
				Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		returnedPlans.addAll( thread.getPlans() );

		return returnedPlans;
	}

	public static LinkedList<Predicate> getFirstPlanOps() {
		return getPlans().getFirst().getOps();
	}
}