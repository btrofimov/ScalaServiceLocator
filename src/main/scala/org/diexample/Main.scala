package org.diexample

import org.discala.Container.{inject,getBean,addBean2,DIScope}
import DIScope._



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
  println( b.doOtherThings)
}


