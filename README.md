# jinject-framework
JInject is a small binding framework coupled with a dependency injection system.

It provides :
* Injection of dependencies by field
* Binding system (key, value, <name>) type safe
* Context to facilitate usage

Features to come :
* Injection by constructor
* Binding by Object
* Possibility to bind any type to any value
* MVC context
* Bind event to specific actions

# Basic usage
### Create a context
You have to create a derived class of AbstractContext :

```java
public class MyContext extends AbstractContext {
	
	@Override
	public void setupBindings() {
	  // your bindings
	}

	@Override
	public void start() {
		// this method is called after all bindings setup
		// you can add logic here like create a view
	}
}
```

### Edit your bindings
You can add now bindings in setupBindings() method using the injectionBinder :

```java
@Override
public void setupBindings() {
  // bind by type
  injectionBinder.bind(IMyInterface.class).to(MyConcreteClass.class); 
  
  // bind by instance, to prevent replacing previous binding, name it !
  injectionBinder.bind(IOtherInterface.class).to(new OtherObject()).toName("NAMED"); 
  
  // bind by name
  injectionBinder.bind(IOtherInterface.class).to(new MyObject()).toName("MY_OBJECT");
  
  // bind by value and lock the binding to prevent later modifications
  injectionBinder.bind(MySpecialClass.class).to(new MySpecialClass()).lock(); 
}
```

### Create a test class
It's time to try your work.

Create a test class with specific annotation to inject content :

```java
// test class
public class MyTestClass {
  @Inject
  public MySpecialClass mySpecialClass; // injected by value
  
  @Inject
  public IOtherObject otherObject; // new object instance
  
  @Inject("NAMED")
  public IOtherObject otherObject; // injected by value, always the same object accross all instances
}


// for example, you can implement MySpecialClass like this and observe the cascade injection
public class MySpecialClass {
  @Inject
  public IMyInterface concreteClassInstance; // new instance
  
  @Inject("MY_OBJECT")
  public IOtherInterface otherObject; // injected by value, always the same object
}

```

Create it and register this class with the context.

```java
@Override
public void start() {
  MyTestClass test = new MyTestClass();
  register(test); // cascade injection
  
  System.out.println(test.mySpecialClass.concreteClassInstance);
}
```

###### Version
JInject v0.2
