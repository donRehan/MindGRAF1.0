package context;

import network.Network;
import nodes.Node;
import nodes.PropositionNode;
import set.PropositionNodeSet;
import java.util.*;

public class Controller{
    private static String currContext = "default";
    private static ContextSet contextSet = new ContextSet(currContext);
    //private static ArrayList<BitSet> minimalNoGoods = new ArrayList<>();
    private static String conflictingContext = null;
    private static PropositionNodeSet conflictingHyps;
	private static Hashtable<Integer, String > attitudeNames = new Hashtable<Integer, String>();
    private static Hashtable<String, ArrayList<String>> consistencies = new Hashtable<String , ArrayList<String>>();
    private static boolean automaticBR = false;
    
    //Consistencies list dictatation
    //Add an attitude to the consistencies list
    public void addAttitude(String attitudeName) {
      // If it doesn't exist then add it
      if (!(consistencies.containsKey(attitudeName))) {
        consistencies.put(attitudeName, new ArrayList<String>());
    }
    }

	public void setAutomaticBR(boolean automaticBR) {
		Controller.automaticBR = automaticBR;
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
	
	public String getPropositionAttitudename(Integer prop,Context c){
		//get the key value from the  attitudeNames hashtable
		System.out.println(c.getPropositionAttitude(prop));
		return attitudeNames.get(c.getPropositionAttitude(prop));
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

	public Context createContext(String contextName) {
        // if a context already exists with that name then throw  an error
		if (contextSet.getContext(contextName) != null){
			throw new RuntimeException("Context with name '" + contextName + "' already exists !");
                 }
		Context c = new Context(contextName);
		return contextSet.add(c);
	}

//	public static Context createContext() {
//		return new Context();
//	}
//
//
	
	public static Context getContext(String contextName) {
		return contextSet.getContext(contextName);
	}
	
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

	public Context createContext(Hashtable<Integer, PropositionNodeSet> attitudes, String name){
		if (contextSet.getContext(name) != null) {
			throw new RuntimeException("Context with name '" + name + "' already exists !");
		}

		Context newContext = new Context(attitudes,name);
		return contextSet.add(newContext);
	}

	public void printContexts() {
    System.out.println("Contexts:");
    for (Context context : contextSet.getContexts().values()) {
        System.out.println(context.getName());
    }
	}

	public static void clearRevision() {
    contextSet = new ContextSet();
    //minimalNoGoods = new Hashtable<>();
    currContext = "default";
}


	public static boolean isAutomaticBR() {
		return automaticBR;
	}

	public static Context getContextByName(String contextName) {
		return contextSet.getContext(contextName);
	}

	public boolean removeContext(String contextName) {
		return contextSet.remove(contextName);
	}

	public String contextToString(String contextName) {
    Context context = contextSet.getContext(contextName);
    if (context == null) {
        return "Context not found: " + contextName;
    }
    
    StringBuilder sb = new StringBuilder();
    sb.append("Context: ").append(context.getName()).append("\n");
    
    // Iterate over the attitudes in the context
    for (Integer attitude : context.getAttitudes()) {
        sb.append("Attitude ").append(attitude).append(": ");
        
        // Retrieve the PropositionNodeSet for the attitude
        PropositionNodeSet propositionNodeSet = context.getAttitude_propositions(attitude);
        
        // Convert the PropositionNodeSet to a string representation
        String propositions = propositionNodeSet != null ? propositionNodeSet.toString() : "Empty";
        
        sb.append(propositions).append("\n");
    }
    
    return sb.toString();
}

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
    // add some consistencies
    //controller.addConsistency("attitude1", "consistency1");
    //controller.addConsistency("attitude1", "consistency2");
    //controller.addConsistency("attitude2", "consistency3");

    //// check if the consistencies were added correctly
    //ArrayList<String> attitude1Consistencies = controller.getConsistenciesForAttitude("attitude1");
    //ArrayList<String> attitude2Consistencies = controller.getConsistenciesForAttitude("attitude2");

    //System.out.println("Attitude 1 consistencies: " + attitude1Consistencies);
    //System.out.println("Attitude 2 consistencies: " + attitude2Consistencies);
    //// try to add a consistency that already exists
    //controller.addConsistency("attitude1", "consistency1");

    //// check if the consistency was added despite already existing
    //System.out.println("Attitude 1 consistencies: " + attitude1Consistencies);
	//
	
    // create a new Controller instance
    Controller controller = new Controller();
	Network network = new Network();
	// create a new context
    PropositionNodeSet pns1 = new PropositionNodeSet();
    Node base1 = Network.createNode("base1", "propositionnode");
    Node base2 = Network.createNode("base2", "propositionnode");
    pns1.add((PropositionNode)base1);
    pns1.add((PropositionNode)base2);
    PropositionNodeSet pns2 = new PropositionNodeSet();
    Node base3 = Network.createNode("base3", "propositionnode");
    Node base4 = Network.createNode("base4", "propositionnode");
    pns2.add((PropositionNode)base3);
    pns2.add((PropositionNode)base4);
	Hashtable<Integer, PropositionNodeSet> attitudes = new Hashtable<Integer, PropositionNodeSet>();
    attitudes.put(1, pns1);
    attitudes.put(2, pns2);
	Context context = controller.createContext(attitudes,"testContext");
	attitudeNames.put(1, "imaginary world");
	attitudeNames.put(2, "real world");
	//works !
	System.out.println(attitudeNames.get(1));

	// Print the initial list of contexts
	System.out.println("Initial list of contexts:");
	controller.printContexts();

	// Remove the context with the specified name
	//fails as its wrong and doesn't exist
	//boolean removed = controller.removeContext("myContext");
	//boolean removed = controller.removeContext("testContext");

	//if (removed) {
    //System.out.println("Context 'myContext' removed successfully.");
	//} else {
    //System.out.println("Failed to remove context 'myContext'.");
	//}
	//System.out.println("Updated list of contexts:");
	//controller.printContexts();

	//Testing Context to String method
	
	System.out.println("=======================");
	System.out.println("=======================");

	String contextString = controller.contextToString("testContext");
	System.out.println(contextString);



    // Test the getAttitudeName method
//    try {
//        Integer attitudeName = context.getPropositionAttitude(base2.getId());
//        System.out.println("Attitude Id: " + attitudeName);
//    } catch (RuntimeException e) {
//        System.out.println(e.getMessage());
//    }
//
//	String attitudename = controller.getPropositionAttitudename(base2.getId(), context);
//	// Check if the output matches the expected value
//	if (attitudename.equals("imaginary world")) {
//	    System.out.println("Output is correct: " + attitudename);
//	} else {
//	    System.out.println("Output is incorrect. Expected: imaginary world, Actual: " + attitudename);
//	}

	// Testing SetAutomatic BR method :: Works !
//	System.out.println("Initial value of automaticBR: " + Controller.isAutomaticBR());
//	// Call the setAutomaticBR method to change the value to true
//	controller.setAutomaticBR(true);
//	// Print the updated value of automaticBR
//	System.out.println("Updated value of automaticBR: " + Controller.isAutomaticBR());

    }
}
