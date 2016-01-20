# jinject-framework
JInject is a small binding framework coupled with a dependency injection system.

It provides :
* Injection of dependencies
* Binding system (key, value, <name>)  (bind any type to anything)
* Context to facilitate usage

Features to come :
* Event system
* Injection by constructor
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


# Binding examples
Some examples to show you what you can bind (actually almost everything :p ) :
```java
IBinder binder = new Binder();
binder.bind(IMyInterface.class).to(MyConcreteClass.class);  // bind interface to concrete class
binder.bind(ConcreteClass.class).to(ConcreteClass.class); // bind concrete class to itself 
binder.bind(ConcreteClass.class).to(instanceOfMyConcreteClass); // bind concrete class to an instance
binder.bind(ConcreteClass.class).to(instanceOfMyConcreteClass).toName("myInstance"); // bind concrete class to an instance and to a name
binder.bind(ConcreteClass.class).to(instanceOfMyConcreteClass).toName(MyEnum.ENUM_VALUE); // bind concrete class to an instance and to an enum

// others :)
binder.bind(100).to("Hello world");  // binding primitive (wrappers) to String object
binder.bind("Hi jack").to(instanceObject);  // String to an instance...
binder.bind("me").to("Thibaud Giovannetti"); // String to string
```

# Events
JInject implements some events, but due to the implementation of the java langage their usage is not trivial at this time.

// doc comming

###### Version
JInject v0.2
