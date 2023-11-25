/** Scala function in the opposite direction. */
case class Op[A, B](run: B => A):
  def andThen[C](that: Op[B, C]): Op[A, C] =
    Op(that.run andThen this.run)

object Op:
  given Category[Op] with
    override def andThen[A, B, C](f: Op[A, B], g: Op[B, C]) =
      Op(g.run andThen f.run)
    override def id[A]: Op[A, A] =
      Op(a => a)

type <= [A, B] = Op[A, B]
