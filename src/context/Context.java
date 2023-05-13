package context;

import network.Network;
import nodes.Node;
import nodes.PropositionNode;
import set.PropositionNodeSet;

import java.io.Serializable;
import java.util.*;

public class Context implements Serializable{
    
    // Attributes
    // rename to attitudePropositions to be more discreptive 
    private Hashtable< Integer, PropositionNodeSet> attitudes;
    private String name;
    private Hashtable<Integer , BitSet> AttitudesBitset;
	
	// Constructor
	protected Context(String name){
	this.name = name;
	this.attitudes = new Hashtable<Integer, PropositionNodeSet>();
	this.AttitudesBitset = new Hashtable<Integer, BitSet>();
	}

	//Consturctor with an attitude
	protected Context(Hashtable<Integer, PropositionNodeSet> attitudes, String name) {
	this.name = name;
	this.attitudes = attitudes;
	//for each String in Hashtable attitudes, create a new BitSet representing the propositions in the PropositionNodeSet
	for (Integer key : attitudes.keySet()){
    PropositionNodeSet current_props = this.attitudes.get(key);
		BitSet bitset = new BitSet();
    if(current_props != null)
    {
    //Need to set the exception handling below to shut this off
		int[] arr = PropositionNodeSet.getPropsSafely(current_props);
    //Set bitset of the current attitude to true for each proposition in the PropositionNodeSet
    for (int i = 0; i < arr.length; i++){
            bitset.set(arr[i]);
        }
    //add bitset then rest its value
    this.AttitudesBitset.put(key, bitset);
    bitset = new BitSet();

    }
    else{
        //set bitset to null if the PropositionNodeSet is null
        this.AttitudesBitset.put(key, null);
        continue;
      }

	}
	}
	//Methods
	
	//Retrieve PropositionNodeSet of an attitude
	protected PropositionNodeSet getAttitude_propositions(Integer attitude){
		return this.attitudes.get(attitude);
	}

	//return all keys of attitudes hashtable in a list
	protected ArrayList<Integer> getAttitudes(){
		ArrayList<Integer> attitudes = new ArrayList<Integer>();
		for (Integer key : this.attitudes.keySet()){
			attitudes.add(key);
		}
		return attitudes;
	}

	//retrive the name of the Context
	protected String getName(){
		return this.name;
	}

	    /**
     * Checks if a propositions is asserted in this Attitude
     *
     * @param p the proposition to be checked for assertion.
     * @return <code>true</code> if the proposition exists, otherwise <code>false</code>
     * @throws NotAPropositionNodeException   If the node p is not a proposition.
     * @throws NodeNotFoundInNetworkException If the node p doesn't exist in the network.
     */
    public boolean isAsserted(PropositionNode p, Integer att) {
        int hyp = p.getId();
		PropositionNodeSet hyps = this.attitudes.get(att);
        return Arrays.binarySearch(PropositionNodeSet.getPropsSafely(hyps), hyp) > 0
                || isSupported(p, att);
    }

    public boolean isSupported(PropositionNode node, Integer att) {
		PropositionNodeSet hyps = this.attitudes.get(att);
		//Support Needed
        Collection<PropositionNodeSet> assumptionSet = node.getBasicSupport()
                .getAssumptionBasedSupport()
                .values();
        for (PropositionNodeSet assumptionHyps : assumptionSet) {
            if (assumptionHyps.isSubSet(hyps)) {
                return true;
            }
        }
        return false;
    }

	/*
	* Returns a list of all proposition nodes that are asserted in this context given attitude
	* @param att the attitude to be checked for assertion
	* @return a list of all proposition nodes that are asserted in this context given attitude
	*/
	// Support Needed
	public PropositionNodeSet allAsserted(Integer att){
        Collection<PropositionNode> allPropositionNodes = Network.getPropositionNodes().values();
        PropositionNodeSet asserted = new PropositionNodeSet();
		PropositionNodeSet props = this.attitudes.get(att);
        int[] hyps;
        hyps = PropositionNodeSet.getPropsSafely(props);
        for (PropositionNode node : allPropositionNodes) {
            if (Arrays.binarySearch(hyps, node.getId()) > 0) {
                asserted.add(node.getId());
            } else if (isSupported(node,att)) {
                asserted.add(node.getId());
            }
        }

        return asserted;
    }


  // Create the main class to test some stuff 

  public static void main(String[] args) {

    //// Initiate proposition nodes
    //Node X = Network.createVariableNode("p1", "propositionnode");

    //
    //PropositionNodeSet props = new PropositionNodeSet();
    //props = props.add(p1.getId()).add(p2.getId()).add(p3.getId());
    //// create a PropositionNodeSet object with some dummy data
    //PropositionNodeSet props = new PropositionNodeSet();
    //props = props.add("p1").add("p2").add("p3");

    //// create a Hashtable object with some dummy data
    //Hashtable<String, PropositionNodeSet> attitudes = new Hashtable<>();
    //attitudes.put("a1", props);

    //// create a Context object with the above data
    //Context context = new Context(attitudes, "context1");
    } 
}
