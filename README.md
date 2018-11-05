# Factory Method

## Intent
The Factory Method Pattern defines an interface for creating an object, but lets subclasses decide which class to instantiate. Factory Method lets a class defer instantiation to subclasses.

## Also Known As
Virtual Constructor

## Motivation

### Problem
Imagine that you are creating an application for a Furniture Store. You make the best chairs in the town, so you decide to produce chairs only. Therefore, you only have a Chair class.

Within months, your chairs become so popular that you get tons of requests to produce tables as well.

Business expansion is a great idea. But how about the code? At this point, most of your code is coupled to the Chair class. Adding Tables would require making changes to the entire codebase. Moreover, in future, if you decide to add Bed or Wardrobe to the app, you will probably need to make all of those change again.

You will end up with long code riddled with conditionals.

```
Furniture createFurniture(String type){
    Furniture furniture;
    if(type.equals("chair")){
        furniture = new Chair();
    } else if (type.equals("table")){
        furniture = new Table();
    } else if (type.equals("bed")){
        furniture = new Bed();
    }
     return furniture;
}
```

### Solution

The Factory Method pattern suggests replacing direct object creation (using a new operator) with a call to a special "factory" method. The constructor call should be moved inside that method. Objects returned by factory methods are often referred to as "products."

Now you can override the factory method in a subclass and change the class of an object that will be created. Let's see how it works:

![Project Diagram](/resources/images/1.png)


As we can see, there is a slight limitation: all products must have a common interface (in this case, Furniture). Factory method in a base class should be returning this common interface. Subclasses may return different concrete products as long as these products have a common base class or interface (for example, both Chair and Table implement the Furniture interface).

Clients of a factory method do not care about the particular type of a product they receive. They work with all products using a common product interface. For example, FurnitureStore does not care what type of Furniture it gets, it only cares that it can call pack() and deliver() on the returned Product. 

## Applicability

Use the Factory Method pattern when
* a class can't anticipate the class of objects it must create.
* a class wants its subclasses to specify the objects it creates.
* classes delegate responsibility to one of several helper subclasses, and you want to localize the knowledge of which helper subclass is the delegate.

## Structure

![Factory Method Structure](/resources/images/2.gif)

## Participants

### Product (Furniture)
- declares the interface for all objects that can be produced by the creator and its subclasses.
### ConcreteProduct (Chair, Table)
- are the different implementations of the Product interface.
- ConcreteCreators create and return instances of these classes.
### Creator (FurnitureStore)
- declares a factory method that returns the Product type. This method can either be abstract or have some default implementation. In the first case, all ConcreteCreators must implement their factory methods.
- product creation is not the main responsibility of a Creator class. Usually, it has some core business logic that works with Products.
- creator never really knows what concrete product was produced.
### ConcreteCreator (ChairMaker, TableMaker)
- implement or override the base factory method, by creating and returning an instance of a ConcreteProduct.

## Collaborations
Creator relies on its subclasses to define the factory method so that it returns an instance of the appropriate ConcreteProduct.

## Consequences

### Pros

* Encapsulates object creation by letting subclasses decide what objects to create.
* Simplifies adding new products to the program. The code only deals with the Product interface; therefore it can work with any user-defined ConcreteProduct classes.
* Avoids tight coupling between concrete products and code that uses them.
* Simplifies code due to moving all creational code to one place.

### Cons

* Requires extra subclasses. Subclassing is fine when the client has to subclass the Creator class anyway, but otherwise, the client now must deal with another point of evolution.

## Implementation

Consider the following issues when applying the FactoryMethod pattern:

1. Two major varieties: The two main variations of the FactoryMethod pattern are (1) the case when the Creator class is an abstract class and does not provide an implementation for the factory method it declares, and (2) the case when the Creator is a concrete class and provides a default implementation for the factory method. It's also possible to have an abstract class that defines a default implementation, but this is less common. The first case requires subclasses to define an implementation because there's no reasonable default. In the second case, the ConcreteCreator uses the factory method primarily for flexibility. It's following a rule that says, "Create objects in a separate operation so that subclasses can override the way they're created." This rule ensures that designers of subclasses can change the class of objects their parent class instantiates if necessary.
2. Parameterized factory methods: Another variation on the pattern lets the factory method create multiple kinds of products. The factory method takes a parameter that identifies the kind of object to create. All objects the factory method creates will share the Product interface.

## Sample Code

```
abstract class FurnitureStore
    Furniture furniture = createFurniture()
    furniture.pack()
    furniture.deliver()
    abstract Furniture createFurniture()

class ChairMaker extends FurnitureStore
    Furniture createFurniture()
        return new Chair()
    // other methods

class TableMaker extends FurnitureStore
    Furniture createFurniture()
        return new Table()
    //other methods

interface Furniture
    pack()
    deliver()

class Chair implements Furniture
    pack()
    deliver()

class Table implements Furniture
    pack()
    deliver()
```

## Relations with Other Patterns

* Abstract Factory classes are often implemented with Factory Methods, but they can also be implemented using Prototype.
* Factory Method can be used along with the Iterator pattern to let collection subclasses return proper iterators.
* Prototype doesn't require subclassing, but it does require an "initialize" operation. Factory Method requires subclassing but doesn't require initialization step.
* Factory Method is a specialization of Template Method. On the other hand, Factory Methods often serve as a step in a large Template Method.
* Factory Method: creation through inheritance. Prototype: creation through delegation.
* The advantage of a Factory Method is that it can return the same instance multiple times, or can return a subclass rather than an object of that exact type.

## Q&A

*Q: What’s the advantage of the Factory Method Pattern when you only have one ConcreteCreator?*

A: The Factory Method Pattern is useful if you’ve only got one concrete creator because you are decoupling the implementation of the product from its use. If you add additional products or change a product’s implementation, it will not affect your Creator (because the Creator is not tightly coupled to any ConcreteProduct). 

*Q: Are the factory method and the Creator always abstract?*

A: No, you can define a default factory method to produce some concrete product. Then you always have a means of creating products even if there are no subclasses of the Creator.

*Q: What is a parameterized factory method?*

A: It can make more than one object based on a parameter passed in which is also a valid form of the pattern. Here’s an example (refer to the Sample Code section): 

```

abstract class FurnitureStore
    Furniture furniture = createFurniture(type)
    furniture.pack()
    furniture.deliver()
    abstract Furniture createFurniture(String type)

class ChairMaker extends FurnitureStore
    Furniture createFurniture(String type)
        if(type.equals("plastic")
            return new PlasticChair()
        else if(type.equals("wood")
            return new WoodChair()
    // other methods
```
Of course, we will need PlasticChair and WoodChair classes extending Chair class (which implements Furniture interface).

*Q: I’m a bit confused about the difference between Simple Factory and Factory Method. They
look very similar, except that in the Factory Method, the class that returns the product is a subclass. Can you explain?*

A: Let’s look at a Simple Factory first which, by the way, is not a REAL pattern -
```java
public class SimpleFurnitureFactory {
    Furniture createFurniture(String type){
        Furniture furniture = null;
        if(type.equals("chair")){
            furniture = new Chair();
        } else if (type.equals("table")){
            furniture = new Table();
        } else if (type.equals("bed")){
            furniture = new Bed();
        }
        return furniture
    }
}
```

Client: FurnitureStore
```java
class FurnitureStore {
    SimpleFurnitureFactory factory;

    public FurnitureStore(SimpleFurnitureFactory factory) {
        this.factory = factory;
    }
    public Furniture orderFurniture(String type){
        Furniture furniture = factory.createFurniture(type);
        furniture.pack();
        furniture.deliver();
    }
}
```
 
Think of Simple Factory as a one-shot deal, while with Factory Method you are creating a framework that lets the subclasses decide which implementation will be used. Factory Method Pattern provides a general framework for creating products and relies on a factory method to actually create the concrete classes. Compare that with SimpleFactory, which gives you a way to encapsulate object creation, but doesn’t give you the flexibility of the Factory Method because there is no way to vary the products you’re creating.

*Q: When to encapsulate object creation?*

A:  If you have a class that isn’t likely to change, and you know it, then it’s not the end of the world if you instantiate a concrete class in your code. Think about it; we instantiate String objects all the time without thinking twice. Because String is very unlikely to change. If, on the other hand, a class you write is likely to change, you have some good techniques like the Factory Method to encapsulate that change.

## References

1. [Head First Design Patterns](https://www.amazon.com/Head-First-Design-Patterns-Brain-Friendly/dp/0596007124)
2. [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612/ref=sr_1_1?s=books&ie=UTF8&qid=1541448469&sr=1-1&keywords=elements+of+reusable)
3. [Refactoring Guru](https://refactoring.guru/design-patterns/factory-method)
4. [Source Making](https://sourcemaking.com/design_patterns/factory_method)