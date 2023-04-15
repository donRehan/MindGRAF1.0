import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;

//Send the mail to Dr Nourhan Cotaining your Bachelor
//Some method were not written in his bachelor so check when done other methods.
//Adding a context must have NAME hence check with the constructors this issue

public class ContextSet implements Serializable {

	private Hashtable<String, Context> contexts;

	//Empty Constructor
    public ContextSet() {
        contexts = new Hashtable<String, Context>();
    }

	//Constructor with name of an empty Context
    public ContextSet(String name) {
        this();
        this.contexts.put(name, new Context(name));
    }

	// Constructor with a Context
	public ContextSet(Context context) {
        this();
        this.add(context);
    }

	//Returns a Context Object given its name.
    public Context getContext(String name) {
        return contexts.get(name);
    }

	// given a Context name it removes it from the ContextSet
    public boolean remove(String name) {
        return contexts.remove(name) != null;
    }

    //Creates an error now as the method doesn't exist yet hence will be commeneted
    public Context add(Context c) {
        contexts.put(c.getName(), c);
    return c;
    }

}
