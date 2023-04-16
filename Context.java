// Create a method eventually to check for a hypothesis and say its supported in which attitude in which context.
// Check for keyword later as it contains uncompleted tasks
// chatGpt means check in asap with chatGPT
// Rename what needs to be renamed once all the methods have been finished.

import java.io.Serializable;
import java.util.*;

public class Context implements Serializable{
    
    // Attributes
    // rename to attitudePropositions to be more discreptive 
    private Hashtable<String, PropositionSet> attitudes;
    private String name;
    private Hashtable<String , BitSet> AttitudesBitset;
    // To be moved into Controller
    //private ArrayList<ArrayList<String>> consistencies;
	
	// Constructor
	// Name should be set by the controller
	protected Context(){
	this.attitudes = new Hashtable<String, PropositionSet>();
	this.AttitudesBitset = new Hashtable<String, BitSet>();
	}

	//Consturctor with an attitude
	protected Context(Hashtable<String, PropositionSet> attitudes){
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

}
