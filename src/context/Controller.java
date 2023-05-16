package context;

import nodes.*;
import  set.PropositionNodeSet;
import java.util.*;

public class Controller{
    private static String currContext = "default";
    private static ContextSet contextSet = new ContextSet(currContext);
    private static ArrayList<BitSet> minimalNoGoods = new ArrayList<>();
    private static String conflictingContext = null;
    private static PropositionNodeSet conflictingHyps;
	private static Hashtable<Integer, String > attitudeNames = new Hashtable<Integer, String>();
    private static Hashtable<String, ArrayList<String>> consistencies = new Hashtable<String , ArrayList<String>>();
    
    //Consistencies list dictatation
    //Add an attitude to the consistencies list
    public void addAttitude(String attitudeName) {
      // If it doesn't exist then add it
      if (!(consistencies.containsKey(attitudeName))) {
        consistencies.put(attitudeName, new ArrayList<String>());
    }
    }

    //Adding a consistency to an attitude
    public void addConsistency(String attitudeName, String consistency) {
        //If the atttiude doesn't exist then create a new one and add it there
        if (!(consistencies.containsKey(attitudeName))) {
            addAttitude(attitudeName);
        }
        //Get the arraylist of consistencies for the attitude
        ArrayList<String> attitudeConsistencies = consistencies.get(attitudeName);
        //If the attitude exists then add the consistency to the list
        if (attitudeConsistencies != null && !attitudeConsistencies.contains(consistency)) {
            attitudeConsistencies.add(consistency);
        }
    }

    /*
     *
public void addConsistency(String attitudeName, String consistency) {
    consistencies.computeIfAbsent(attitudeName, k -> new ArrayList<>()).add(consistency);
}
     */

    //Method that returns all consistencies for a partiuclar attitude
  public ArrayList<String> getConsistenciesForAttitude(String attitudeName) {
    if (consistencies.containsKey(attitudeName)) {
        return consistencies.get(attitudeName);
    }
    return new ArrayList<String>();
}

    //Checking if a consistency exists for an attitude
    public boolean consistencyExists(String attitudeName, String consistency) {
        ArrayList<String> attitudeConsistencies = consistencies.get(attitudeName);
        if (attitudeConsistencies != null) {
            return attitudeConsistencies.contains(consistency);
        }
        return false;
    }

//	/**
//	 * Creates a new Context given its name and adds it to SNeBR's ContextSet.
//	 * @param contextName the name of the Context to be created
//	 * @return the newly Created Context object
//	 * @throws DuplicateContextNameException If a context with the same name exists
//	 */
//	public static Context createContext(String contextName) {
//                // if a context already exists with that name then throw  an error
//		if (contextSet.getContext(contextName) != null){
//			//throw new DuplicateContextNameException(contextName);
//                 }
//
//		Context c = new Context(contextName);
//		return contextSet.add(c);
//	}
//
//	/**
//	 * Creates a new empty Context
//	 *
//	 * @return new Context object
//	 */
//	public static Context createContext() {
//		return new Context();
//	}
//
//	/**
//	 * Clears the global data strctures of SNeBR
//	 */
//	public static void clearSNeBR() {
//		contextSet.clear();
//		minimalNoGoods.clear();
//		currContext = "default";
//		contextSet.add(new Context(currContext));
//	}
//
//
//	/**
//	 * Removes a context from SNeBR's ContextSet.
//	 *
//	 * @param contextName name of the context desired to be removed
//	 * @return <code>true</code> if a context with this name exists,
//	 *         <code>false</code> otherwise
//	 */
//	public static boolean removeContext(String contextName) {
//		Context c = contextSet.getContext(contextName);
//		if (c == null)
//			return false;
//
//		boolean bool = c.removeName(contextName);
//		return contextSet.remove(contextName) && bool;
//	}
//
//
	public void setCurrentContext(String contextName)
	{
		Context context = contextSet.getContext(contextName);
		if(context == null)
			throw new RuntimeException("Context with name '" + contextName + "' doesn't exist !");
		currContext = contextName; 
	}

	public Context getCurrentContext()
	{
		return contextSet.getContext(currContext);
	}

	public static Context createContext(Hashtable<Integer, PropositionNodeSet> attitudes, String name){
		if (contextSet.getContext(name) != null) {
			throw new RuntimeException("Context with name '" + name + "' already exists !");
		}

		Context newContext = new Context(attitudes,name);
		contextSet.add(newContext);
	}

//
//
//	/**
//	 * Asserts a hyp in an existing Context
//	 *
//	 * @param contextName the name of the context to assert the hyp in
//	 * @param hyp         the hyp to be asserted
//	 * @return a new Context object containing the old Context with the hyp asserted
//	 *         in it
//	 * @throws ContextNameDoesntExistException if no Context with this name exists
//	 *                                         in SNeBr's ContextSet
//	 * @throws DuplicatePropositionException   if the hyp to be asserted in the
//	 *                                         Context is already asserted
//	 * @throws NodeNotFoundInNetworkException throws ContextNameDoesntExistException, NotAPropositionNodeException, DuplicatePropositionException,
//			NodeNotFoundInNetworkException, ContradictionFoundException 
//	 */
	public static Context addPropToContext(String contextName,Integer att, int hyp)
			{
		Context context = contextSet.getContext(contextName);
		if (context == null)
			throw new RuntimeException("Context with name '" + contextName + "' doesn't exist !");

		if (!(context.getAttitudes().contains(att)))
        	throw new RuntimeException("attitude doesn't exist in the context !");

		ArrayList<NodeSet> contradictions = checkForContradiction((PropositionNode) Network.getNodeById(hyp), temp,
				false);

		if (contradictions != null) {
			conflictingContext = contextName;
			conflictingHyps = new PropositionSet(new int[] { hyp });
			throw new ContradictionFoundException(contradictions);
		}

		PropositionNode node = (PropositionNode) Network.getNodeById(hyp);
		node.setHyp(true);
		PropositionSet hypSet = oldContext.getHypothesisSet().add(hyp);

		Context newContext = new Context(contextName, hypSet);

		return contextSet.add(newContext);
	}
//
//
//	/**
//	 * Checks if some node's addition to a context c introduces a contradiction.
//	 *
//	 * @param node
//	 * @param c
//	 * @param skipCache This is a boolean flag to allow skipping the cache checking
//	 *                  stage. It is useful for testing purposes only, for now.
//	 * @return
//	 * @throws NodeNotFoundInNetworkException
//	 * @throws DuplicatePropositionException
//	 * @throws NotAPropositionNodeException
//	 */
//	//Not finished.
//	public static ArrayList<NodeSet> checkForContradiction(PropositionNode node, Context c, boolean skipCache)
//			throws NodeNotFoundInNetworkException, DuplicatePropositionException, NotAPropositionNodeException {
//
//		if (c.getNames().contains(conflictingContext)) {
//			checkForContradictionCore(node, c, true);
//			return checkForContradictionCore(node, c, false);
//		}
//
//		return checkForContradictionCore(node, c, skipCache);
//	}
    //Create a main method for testing purposes
    public static void main(String[] args) {
    // create a new Controller instance
    Controller controller = new Controller();
    // add some consistencies
    controller.addConsistency("attitude1", "consistency1");
    controller.addConsistency("attitude1", "consistency2");
    controller.addConsistency("attitude2", "consistency3");

    // check if the consistencies were added correctly
    ArrayList<String> attitude1Consistencies = controller.getConsistenciesForAttitude("attitude1");
    ArrayList<String> attitude2Consistencies = controller.getConsistenciesForAttitude("attitude2");

    System.out.println("Attitude 1 consistencies: " + attitude1Consistencies);
    System.out.println("Attitude 2 consistencies: " + attitude2Consistencies);
    // try to add a consistency that already exists
    controller.addConsistency("attitude1", "consistency1");

    // check if the consistency was added despite already existing
    System.out.println("Attitude 1 consistencies: " + attitude1Consistencies);
    }
}
