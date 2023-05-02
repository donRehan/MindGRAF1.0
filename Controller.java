import java.util.*;

public class Controller{
    private static String currContext = "default";
    private static ContextSet contextSet = new ContextSet(currContext);
    private static ArrayList<BitSet> minimalNoGoods = new ArrayList<>();
    private static String conflictingContext = null;
    private static PropositionSet conflictingHyps;
    //Consistencies list

	/**
	 * Creates a new Context given its name and adds it to SNeBR's ContextSet.
	 * @param contextName the name of the Context to be created
	 * @return the newly Created Context object
	 * @throws DuplicateContextNameException If a context with the same name exists
	 */
	public static Context createContext(String contextName) {
                // if a context already exists with that name then throw  an error
		if (contextSet.getContext(contextName) != null){
			//throw new DuplicateContextNameException(contextName);
                 }

		Context c = new Context(contextName);
		return contextSet.add(c);
	}

	/**
	 * Creates a new empty Context
	 *
	 * @return new Context object
	 */
	public static Context createContext() {
		return new Context();
	}

	/**
	 * Clears the global data strctures of SNeBR
	 */
	public static void clearSNeBR() {
		contextSet.clear();
		minimalNoGoods.clear();
		currContext = "default";
		contextSet.add(new Context(currContext));
	}


	/**
	 * Removes a context from SNeBR's ContextSet.
	 *
	 * @param contextName name of the context desired to be removed
	 * @return <code>true</code> if a context with this name exists,
	 *         <code>false</code> otherwise
	 */
	public static boolean removeContext(String contextName) {
		Context c = contextSet.getContext(contextName);
		if (c == null)
			return false;

		boolean bool = c.removeName(contextName);
		return contextSet.remove(contextName) && bool;
	}


	/**
	 * Creates a new Context given its name and a set of hyps, and adds it to
	 * SNeBR's ContextSet.
	 *
	 * @param contextName name of the Context to be created
	 * @param hyps        the set of hyps to be asserted in this Context
	 * @return the created Context
	 * @throws DuplicateContextNameException if a Context with this name exists in
	 *                                       SNeBr's ContextSet
         * 
        throws DuplicateContextNameException,
	ContradictionFoundException, NotAPropositionNodeException, NodeNotFoundInNetworkException,
	ContextNameDoesntExistException, DuplicatePropositionException, NodeNotFoundInPropSetException 
         *
	 */
	public static Context createContext(Hashtable<String, PropositionSet> attitudes, String name){
		if (contextSet.getContext(contextName) != null) {
			//throw new DuplicateContextNameException(contextName);
		}

		Context newContext = new Context(contextName);
		contextSet.add(newContext);

                //modify this after fixing addProps method first to mindGRAF
		return addPropsToContext(contextName, attitudes);
	}


	/**
	 * Asserts a hyp in an existing Context
	 *
	 * @param contextName the name of the context to assert the hyp in
	 * @param hyp         the hyp to be asserted
	 * @return a new Context object containing the old Context with the hyp asserted
	 *         in it
	 * @throws ContextNameDoesntExistException if no Context with this name exists
	 *                                         in SNeBr's ContextSet
	 * @throws DuplicatePropositionException   if the hyp to be asserted in the
	 *                                         Context is already asserted
	 * @throws NodeNotFoundInNetworkException throws ContextNameDoesntExistException, NotAPropositionNodeException, DuplicatePropositionException,
			NodeNotFoundInNetworkException, ContradictionFoundException 
	 */
	public static Context addPropToContext(String contextName,String att, int hyp)
			{
		Context oldContext = contextSet.getContext(contextName);

		//fix when exceptions are handled.
		if (oldContext == null)
			throw new ContextNameDoesntExistException(contextName);

		//Context temp = new Context(contextName,
		//		new PropositionSet(PropositionSet.getPropsSafely(oldContext.getHypothesisSet())));

		// Implement this first so you can continue.
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


	/**
	 * Checks if some node's addition to a context c introduces a contradiction.
	 *
	 * @param node
	 * @param c
	 * @param skipCache This is a boolean flag to allow skipping the cache checking
	 *                  stage. It is useful for testing purposes only, for now.
	 * @return
	 * @throws NodeNotFoundInNetworkException
	 * @throws DuplicatePropositionException
	 * @throws NotAPropositionNodeException
	 */
	//Not finished.
	public static ArrayList<NodeSet> checkForContradiction(PropositionNode node, Context c, boolean skipCache)
			throws NodeNotFoundInNetworkException, DuplicatePropositionException, NotAPropositionNodeException {

		if (c.getNames().contains(conflictingContext)) {
			checkForContradictionCore(node, c, true);
			return checkForContradictionCore(node, c, false);
		}

		return checkForContradictionCore(node, c, skipCache);
	}
}
