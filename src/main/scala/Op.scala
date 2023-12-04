/** Scala function in the opposite direction. */
case class Op[A, B](run: B => A)

type <= [A, B] = Op[A, B]

object Op:
  given Category[<=] with
    override def andThen[A, B, C](f: A <= B, g: B <= C): A <= C =
      Op(g.run andThen f.run)
    override def id[A]: A <= A =
      Op(a => a)
