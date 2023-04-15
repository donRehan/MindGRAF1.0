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
    // Each attitude would have its own hypsBitset.
    //rename to attitudeHypotheses to be more discreptive
    private Hashtable<String , BitSet> AttitudesBitset;
    // Not efficient , Want to make it efficient
    // rename it to consistentAttitudes to be more discreptive
    private ArrayList<ArrayList<String>> consistencies;

    //CHANGE THIS FORMAT.
    protected BitSet getHypsBitset() {
        return hypsBitset;
    }

    protected Context() {
		// We shouldn't have a default name hence think of a solution to propose to this.
        names = new HashSet<String>();
        // double check this one too
        this.attitudes = new Hashtable<>(); 
        // now bitset  is going to be different for each attitude and hence requires revision too
        this.AttitudesBitset = new Hashtable<>();
        this.consistencies = new ArrayList<>();
    }

    protected Context(String contextName) {
        this();
        this.name = contextName;
    }

    protected Context(Context c) {
      // To create getAttitudes list method later
      // Change this too.
        this.attitudes = c.getAttitudesSet();
        // to be created. later
        this.name = c.getName();
        // to be created later
        this.AttitudesBitset = c.getAttitudesBitset();
    }

    // to create exceptions later ... to add exceptions for wrong attitude etc
    protected Context(Context c, int hyp, String attitude) throws NotAPropositionNodeException, DuplicatePropositionException, NodeNotFoundInNetworkException {
        this.name = c.getName();
        this.attitudes = c.getAttitudeslist().add(hyp, attitude);
        BitSet temp = attitudesBitset.get(attitude).clone(); 
        temp.set(hyp);
        this.AttitudesBitset.put(attitude, temp);
    }

    protected Context(String contextName, Context c) {
        this(c);
        this.name.add(contextName);
    }

    protected Context(String contextName, PropositionSet hyps, String attitude) throws NotAPropositionNodeException, NodeNotFoundInNetworkException {
        this(contextName);
        this.attitudes.put(attitude, hyps);
        BitSet temp = new BitSet(); 
        PropositionSet retrievedPropSet = attitudes.get(attitude);
        int [] arr = PropositionSet.getPropsSafely(retrievedPropSet);
        for (int i = 0; i < arr.length; i++){
             temp.set(arr[i]);
        }
        this.AttitudesBitset.put(attitude, temp);
    } 

    public PropositionSet getHypothesisSet(String attitude) { // of the specific attitude 
        return this.attitudes.get(attitude);
    }

    protected String getName() {
        return this.name;
    }

    // to be revised and adjusted as its from AI and all the functions beneath
    public void addConsistency(String attitudeName, ArrayList<String> consistentAttitudes) {
        int attitudeIndex = getAttitudeIndex(attitudeName);
        if (attitudeIndex == -1) {
            // Attitude not found
            throw new IllegalArgumentException("Attitude " + attitudeName + " not found.");
        }

        for (String consistentAttitudeName : consistentAttitudes) {
            int consistentIndex = getAttitudeIndex(consistentAttitudeName);
            if (consistentIndex == -1) {
                // Consistent attitude not found
                throw new IllegalArgumentException("Attitude " + consistentAttitudeName + " not found.");
            }

            // Add the consistency relationship to the list
            ArrayList<String> relationship = new ArrayList<String>();
            relationship.add(attitudeName);
            relationship.add(consistentAttitudeName);
            this.consistencies.add(relationship);
        }
    }

    public void removeConsistency(String attitudeName, String consistentAttitudeName) {
        // Find the relationship in the list and remove it
        ArrayList<String> relationshipToRemove = null;
        for (ArrayList<String> relationship : this.consistencies) {
            if (relationship.get(0).equals(attitudeName) && relationship.get(1).equals(consistentAttitudeName)) {
                relationshipToRemove = relationship;
                break;
            }
        }

        if (relationshipToRemove != null) {
            this.consistencies.remove(relationshipToRemove);
        }
    }

    public ArrayList<String> getConsistentAttitudes(String attitudeName) {
        ArrayList<String> consistentAttitudes = new ArrayList<String>();

        // Find all relationships in the list that involve the specified attitude
        for (ArrayList<String> relationship : this.consistencies) {
            if (relationship.get(0).equals(attitudeName)) {
                consistentAttitudes.add(relationship.get(1));
            }
        }

        return consistentAttitudes;
    }

    private int getAttitudeIndex(String attitudeName) {
        // Find the index of the specified attitude in the list of attitudes
        int index = -1;
        int i = 0;
        for (String name : this.attitudes.keySet()) {
            if (name.equals(attitudeName)) {
                index = i;
                break;
            }
            i++;
        }
        return index;
    }

}
