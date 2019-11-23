package org.scalajs.jsdependencies.core.json

private[core] trait JSONSerializer[T] {
  def serialize(x: T): JSON
}

private[core] object JSONSerializer {

  implicit object stringJSON extends JSONSerializer[String] {
    def serialize(x: String): JSON = Impl.fromString(x)
  }

  implicit object intJSON extends JSONSerializer[Int] {
    def serialize(x: Int): JSON = Impl.fromNumber(x)
  }

  implicit object booleanJSON extends JSONSerializer[Boolean] {
    def serialize(x: Boolean): JSON = Impl.fromBoolean(x)
  }

  implicit def listJSON[T: JSONSerializer]: JSONSerializer[List[T]] = {
    new JSONSerializer[List[T]] {
      def serialize(x: List[T]): JSON = Impl.fromList(x.map(_.toJSON))
    }
  }

  implicit def mapJSON[V: JSONSerializer]:JSONSerializer[Map[String, V]] = {
    new JSONSerializer[Map[String, V]] {
      def serialize(x: Map[String, V]): JSON =
        Impl.fromMap(x.map(kv => kv._1 -> kv._2.toJSON))
    }
  }

}
