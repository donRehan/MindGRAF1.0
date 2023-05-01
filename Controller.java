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
}
