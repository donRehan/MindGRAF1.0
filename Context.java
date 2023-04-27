import java.io.Serializable;
import java.util.*;

public class Context implements Serializable{
    
    // Attributes
    // rename to attitudePropositions to be more discreptive 
    private Hashtable<String, PropositionSet> attitudes;
    private String name;
    private Hashtable<String , BitSet> AttitudesBitset;
	
	// Constructor
	protected Context(String name){
	this.name = name;
	this.attitudes = new Hashtable<String, PropositionSet>();
	this.AttitudesBitset = new Hashtable<String, BitSet>();
	}

	//Consturctor with an attitude
	protected Context(Hashtable<String, PropositionSet> attitudes, String name){
	this.name = name;
	this.attitudes = attitudes;
	//for each String in Hashtable attitudes, create a new BitSet representing the propositions in the PropositionSet
	for (String key : attitudes.keySet()){
		BitSet bitset = new BitSet();
		PropositionSet propSet = attitudes.get(key);
		for (Proposition prop : propSet.getPropsSafely()){
			bitset.set(prop.getId());
		}
		AttitudesBitset.put(key, bitset);
	}
	}
	//Methods
	
	//Retrieve PropositionSet of an attitude
	protected PropositionSet getAttitude_propositions(String attitude){
		return this.attitudes.get(attitude);
	}

	//return all keys of attitudes hashtable in a list
	protected ArrayList<String> getAttitudes(){
		ArrayList<String> attitudes = new ArrayList<String>();
		for (String key : this.attitudes.keySet()){
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
    public boolean isAsserted(PropositionNode p, Attitude att) {
        int hyp = p.getId();
		PropositionSet hyps = this.attitudes.get(att);
        return Arrays.binarySearch(PropositionSet.getPropsSafely(hyps), hyp) > 0
                || isSupported(p, att);
    }

    public boolean isSupported(PropositionNode node, Attitude att) {
		PropositionSet hyps = this.attitudes.get(att);
        Collection<PropositionSet> assumptionSet = node.getBasicSupport()
                .getAssumptionBasedSupport()
                .values();
        for (PropositionSet assumptionHyps : assumptionSet) {
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
	public PropositionSet allAsserted(Attitude att){
        Collection<PropositionNode> allPropositionNodes = Network.getPropositionNodes().values();
        PropositionSet asserted = new PropositionSet();
		PropositionSet props = this.attitudes.get(att);
        int[] hyps;
        hyps = PropositionSet.getPropsSafely(props);
        for (PropositionNode node : allPropositionNodes) {
            if (Arrays.binarySearch(hyps, node.getId()) > 0) {
                asserted = asserted.add(node.getId());
            } else if (isSupported(node,att)) {
                asserted = asserted.add(node.getId());
            }
        }

        return asserted;
    }


}
