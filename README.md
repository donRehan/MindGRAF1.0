# MindGRAF1.0 
Contexts and Consistency part of the MindGRAF system

NOTE !!
=> Context Class needs to have one unique name identifier and not a list of names

#### General 
- [ ] Add consisteny list to controller instead of Context and come up with an efficient implementation.
- [ ] Address nodes by Ids in Revision System.
- [ ] Check Context Creations and adding Propositions Attitudes as well as wether the name is unique or not.

## Context Class
- [ ] isAsserted() a method eventually to check for a hypothesis and say its supported in which attitude in which context. => CH
- [ ] isSupported. 
- [ ] allAsserted(). => CH
//  The three below were of the same style of the previous implementation called JavaSneps and Hence will also be dealt with.
- [ ] addName.
- [ ] addNames.
- [ ] removeName.
// Correct Part
- [ ] getName.
- [ ] Function for unique name or is it given by the user ?.

## ContextSet
- Double check rules for new implementation before proceeding .
- [x] ContextSet
- [x] ContextSet(String name)
- [x] ContextSet(Context context)
- [x] getContext(String name)
- [x] remove(String name)
- [x] add(context context)
- [ ] Fix Consturctors to only have one string as the name that identifies the Context

## Controller Class
> To be added later.

## ContextSet
Document your code here for your bachelor document.

### Methods

// Insert Screenshot of the method. 
Here remove gets the Context by its name and removes it from the ContextSet.

### removing and adding a Context
```java
// given a Context name it removes it from the ContextSet
    public boolean remove(String name) {
        return contexts.remove(name) != null;
    }
```
### Constructing  contextSets
 ``` java
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
```

### Adding a Context
```java

//Returns a Context Object given its name.
    public Context getContext(String name) {
        return contexts.get(name);
    }
```

> This section is not finished yet !