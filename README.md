# MindGRAF1.0 

> To Be added :: Table of contents

Contexts and Consistency part of the MindGRAF system

NOTE !!
=> Context Class needs to have one unique name identifier and not a list of names

As per last meeting here is the reason for HypsBitset in previous implementation.
```
hypsBitSet:
hypsBitset is a private attribute of type BitSet.
It stores all the propositions that are in the hyps in it as binary 1’s at indices corresponding the ids of these hyps.
It is generated every time a Context is constructed.
```
## General 
- [ ] Add consisteny list to controller instead of Context and come up with an efficient implementation.
- [ ] Address nodes by Ids in Revision System.
- [ ] Check Context Creations and adding Propositions Attitudes as well as wether the name is unique or not.

## Context Class (**TODO LIST**)
To be revised as it seems a bit vague at the moment for me.
- [ ] isAsserted() a method eventually to check for a hypothesis and say its supported in which attitude in which context. => CH
- [ ] isSupported. 
- [ ] allAsserted(). => CH
//  The three below were of the same style of the previous implementation called JavaSneps and Hence will also be dealt with.
- [ ] addName.
- [ ] addNames.
- [ ] removeName.
// Correct Part
- [x] getName.
- [ ] Handled Context Creations with unqiue names.

## ContextSet (**TODO LIST**)
- Double check rules for new implementation before proceeding .
- [x] ContextSet
- [x] ContextSet(String name)
- [x] ContextSet(Context context)
- [x] getContext(String name)
- [x] remove(String name)
- [x] add(context context)
- [x] Fix Consturctors to only have one string as the name that identifies the Context

## Controller Class (**TODO LIST**)
> To be added later.

# ContextSet
> This is where all Contexts are stored and can be acccessed from the controller class through this class.

## Methods

#### removing and adding a Context
```java
// given a Context name it removes it from the ContextSet
    public boolean remove(String name) {
        return contexts.remove(name) != null;
    }
```
#### Constructing  contextSets
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

#### Getting a Context
```java

//Returns a Context Object given its name.
    public Context getContext(String name) {
        return contexts.get(name);
    }
```
#### Adding a Context
```java 

//Add a context into the ContextSet using its name as the key.
    public Context add(Context c) {
        contexts.put(c.getName(), c);
    return c;
    }
```
