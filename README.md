ScalaServiceLocator
===================

Simple and lightweight prototype of IoC Container/ServiceLocator for Scala.

You might say, OMG, one more DI? And, it does not support interface binding or circullar referencies!!
Yes, I know, again, this is just fun prototype written in just 50 loc(!), lets say, trying out and tasting the power of scala.

In fact, this example implements  rather hidden Service Locator pattern with IoC support than pure DI approach. Endeed ```inject``` method knows information about all objects so it might be called Service Locator.
Anyway, this scala approach based on functional decorator is very attracrive and powerful.


#Usage example:
```
class A {
  println("A created")
  def doSomething = 3

}

class E{
  val a = inject(classOf[A])

  println("E created")

  def doSomething = 4 + a.doSomething
}

class B{

  val a = inject(classOf[A])
  val e = inject(classOf[E])

  println("B created")

  def doOtherThings = a.doSomething + e.doSomething
}


object DIApp  extends App{

  // register beans
  addBean2(classOf[A],SINGLETON)
  addBean2(classOf[E],PROTOTYPE)
  addBean2(classOf[B])

  val b: B = getBean(classOf[B])
  println( b.doOtherThings())
}


```

#Must have features:
 * interface bindings
```trait A
class B extends A

class C {
  val b = inject(classOf[A])
}
```

 * binding by resource id
```trait A
class B extends A

class C {
  val b = inject(classOf[A],'B)
}

...

addBean(classOf[B], 'B)
```
 * circular referencies support

```
###Example of interface binding
class B{
  val a  = inject(classOf[A])
  println("B created")
}

class A{
  val a : A = inject(classOf[A])
  println("A created")
}
```

