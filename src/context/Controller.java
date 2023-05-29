// Some attitudes don't have to be consistent like likes etc so when adding a prop check first this. L , C
// Need  two flat sets of indicies
// Not all atts we  can do logical implecations what not this indicates that these atts have these properties
// Final
package context;

import network.Network;
import nodes.Node;
import nodes.PropositionNode;
import set.NodeSet;
import set.PropositionNodeSet;
import java.util.*;
import paths.AndPath;
import paths.ComposePath;
import paths.FUnitPath;
import paths.KPlusPath;
import paths.Path;
import paths.PathTrace;
import relations.Relation;
import set.NodeSet;
import cables.DownCable;
import cables.DownCableSet;
import caseFrames.Adjustability;
import components.MyClassCreator;
import context.Context;
import nodes.ActNode;
import nodes.IndividualNode;
import nodes.MolecularType;
import nodes.Node;
import nodes.PropositionNode;

import cables.DownCable;

public class Controller{
    private static String currContext = "default";
    private static ContextSet contextSet = new ContextSet(currContext);
    //private static ArrayList<BitSet> minimalNoGoods = new ArrayList<>();
    private static String conflictingContext = null;
    private static PropositionNodeSet conflictingHyps;
	//Make it  final variable 
	private static Hashtable<Integer, String > attitudeNames = new Hashtable<Integer, String>();
	// set of  set  of ids of the attitudes and changed to final
    private static Hashtable<String, ArrayList<String>> consistencies = new Hashtable<String , ArrayList<String>>();
    private static boolean automaticBR = false;
    
    //Consistencies list dictatation
    //Add an attitude to the consistencies list
	// Attitude names are created only once in the start and cannot be changed any time else
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

	public static Context getContext(String contextName) {
		//if no context with this name exists then throw an error
		if (contextSet.getContext(contextName) == null){
			throw new RuntimeException("Context with name '" + contextName + "' doesn't exist !");
		}
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
		//Check if  current Context  is not default
		if (currContext == "default"){
			throw new RuntimeException("No current Context is set");
		}
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

	// Checks if a contradiction exists if we introduce a proposition in a context's attitude
	public boolean ContradictionExists(Integer att, Context c, PropositionNode p){
	// Get the propositionNodeSet for the attitude
	PropositionNodeSet propositionNodeSet = c.getAttitude_propositions(att);
	/*
	Add consistencies list next step
	//Get the consistency for the attitude if it exists
	ArrayList<String> consistencies = getConsistenciesForAttitude(attitudeNames.get(att));
	//if Consistencies is not null then set a flag to true
	boolean cons = false;
	if (consistencies != null) {
		cons = true;
	}
	//If cons is true then add the propositions of the cons to a unified props set
	*/

	// Go through all the propositionNodeSet and check if getNegation(p) returns true. if so then return true
	//Handle if it was a base node 
	//Node test = negationTest.getNegation(); if(test == null) {System.out.println("null");} else{
	//if(test.equals(M1)) {System.out.println("true");} else {System.out.println("false");}}
	for (int prop : PropositionNodeSet.getPropsSafely(propositionNodeSet)) {
		if (Network.getNodeById(prop).getNegation().equals(p)) {
			return true;
		}
	}
	return false;

	}

//	public static void addPropositionNode(Integer att, PropositionNode p, Context c){
//		// Get the propositionNodeSet for the attitude if it exists if not create it 
//		
//		// Add the proposition to the propositionNodeSet
//		propositionNodeSet.add(p);
//	}

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
//	// create a new context
    PropositionNodeSet pns1 = new PropositionNodeSet();
//    Node base1 = Network.createNode("p", "propositionnode");
//    Node base2 = Network.createNode("q", "propositionnode");
//    pns1.add((PropositionNode)base1);
//    pns1.add((PropositionNode)base2);
//    PropositionNodeSet pns2 = new PropositionNodeSet();
//    Node base3 = Network.createNode("w", "propositionnode");
//    Node base4 = Network.createNode("z", "propositionnode");
//    pns2.add((PropositionNode)base3);
//    pns2.add((PropositionNode)base4);
//	Hashtable<Integer, PropositionNodeSet> attitudes = new Hashtable<Integer, PropositionNodeSet>();
//    attitudes.put(1, pns1);
//    attitudes.put(2, pns2);
//	Context context = controller.createContext(attitudes,"testContext");
//	attitudeNames.put(1, "belief");
//	attitudeNames.put(2, "desires");
//	//works !
//	System.out.println(attitudeNames.get(1));
//
//	// Print the initial list of contexts
//	System.out.println("Initial list of contexts:");
//	controller.printContexts();
//
//	// Remove the context with the specified name
//	//fails as its wrong and doesn't exist
//	//boolean removed = controller.removeContext("myContext");
//	//boolean removed = controller.removeContext("testContext");
//
//	//if (removed) {
//    //System.out.println("Context 'myContext' removed successfully.");
//	//} else {
//    //System.out.println("Failed to remove context 'myContext'.");
//	//}
//	//System.out.println("Updated list of contexts:");
//	//controller.printContexts();
//
//	//Testing Context to String method
	
//	System.out.println("=======================");
//	System.out.println("=======================");
//
//	String contextString = controller.contextToString("testContext");
//	System.out.println(contextString);

	//Creating negation prop
    Node zero = Network.createNode("0", "propositionnode");
	// Want to set the arg relation with a node in the down cable set
	Node negationTest = Network.createNode("negationTest", "propositionnode");
	//Create a node set to add negationTest node to it
	NodeSet pns = new NodeSet();
	NodeSet Zero = new NodeSet();
	Zero.add(zero);
	pns.add((PropositionNode)negationTest);
	pns1.add((PropositionNode)negationTest);
	//Creating the relations
	Relation arg = new Relation ("arg", "", Adjustability.NONE, 1);
	Relation min = new Relation ("min", "", Adjustability.NONE, 1);
	Relation max = new Relation ("max", "", Adjustability.NONE, 1);
	//Creating the down cables
	DownCable dc = new DownCable(arg, pns);
	DownCable Min = new DownCable(min, Zero);
	DownCable Max = new DownCable(max, Zero);
	// Create node M1 like example with arg cable to m2. m2 <= negationTest
	// M1 negates negationTest node
	Node M1 = Network.createNode("propositionnode", new DownCableSet(dc, Min, Max));
	// Above code generates the negation as in the figure 2.5
	// Below is testing detecting the contradiction.

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
//	if (attitudename.equals("belief")) {
//	    System.out.println("Output is correct: " + attitudename);
//	} else {
//	    System.out.println("Output is incorrect. Expected: belief, Actual: " + attitudename);
//	}

	// Testing SetAutomatic BR method :: Works !
//	System.out.println("Initial value of automaticBR: " + Controller.isAutomaticBR());
//	// Call the setAutomaticBR method to change the value to true
//	controller.setAutomaticBR(true);
//	// Print the updated value of automaticBR
//	System.out.println("Updated value of automaticBR: " + Controller.isAutomaticBR());


	// ======================== TESTING CONTRADICTIONS ======================== \\
    // Create a new context
    //Context context = new Context("ContextName");
    Node base1 = Network.createNode("base1", "propositionnode");
    Node base2 = Network.createNode("base2", "propositionnode");
//    pns1.add((PropositionNode)base1);
//    pns1.add((PropositionNode)base2);
    PropositionNodeSet pns2 = new PropositionNodeSet();
    Node base3 = Network.createNode("base3", "propositionnode");
    Node base4 = Network.createNode("base4", "propositionnode");
    pns2.add((PropositionNode)base3);
	//May cause an issue that you are type casting.
	pns1.add((PropositionNode) negationTest);
    pns2.add((PropositionNode)base4);
	Hashtable<Integer, PropositionNodeSet> attitudes = new Hashtable<Integer, PropositionNodeSet>();
    attitudes.put(1, pns1);
    attitudes.put(2, pns2);
	Context context = controller.createContext(attitudes,"testContext");

	//Node test = negationTest.getNegation(); if(test == null) {System.out.println("null");} else{
	//if(test.equals(M1)) {System.out.println("true");} else {System.out.println("false");}}
	//Possible issue is the type casting of the variables into prop nodes , Figure out a fix.
	boolean contradictionExists = controller.ContradictionExists(1, context, (PropositionNode) M1);
    // Check the result
    if (contradictionExists) {
        System.out.println("Contradiction exists!");
    } else {
        System.out.println("No contradiction found.");
    }

    }
}
