package org.discala



object Container{

  object DIScope extends Enumeration{
    type DIScope = Value
    val
    PROTOTYPE,
    SINGLETON = Value
  }

  import DIScope._

  def inject[A](clazz : Class[A]) (implicit manifest:Manifest[A]) : A = {
    val (a,b,c) = configuration(manifest.erasure.getCanonicalName)
    ( if(b==SINGLETON){
        if(c==null){
          val ret = a()
          configuration += ( manifest.erasure.getCanonicalName -> ( a,b,ret ) )
          ret
         }
         else c
      }
      else a()
    ).asInstanceOf[A]
  }

  def getBean[A](clazz : Class[A])(implicit manifest:Manifest[A]) : A = inject(clazz)(manifest)

  def addBean[A <: AnyRef ](factory : () => A, scope :DIScope = PROTOTYPE ) (implicit manifest:Manifest[A]){
    val func : () => AnyRef = factory
    configuration += ((manifest.erasure.getCanonicalName, (func, scope, null )))
  }

  def addBean2[A <: AnyRef ](clazz : Class[A], scope :DIScope = PROTOTYPE ) (implicit manifest:Manifest[A]) {
    val func : () => AnyRef = () => manifest.erasure.newInstance().asInstanceOf[AnyRef]
    configuration +=( manifest.erasure.getCanonicalName -> (func , scope, null ) )
  }

  private[discala] val configuration = scala.collection.mutable.Map[String, Tuple3[()=>AnyRef,DIScope, AnyRef] ]()

}
