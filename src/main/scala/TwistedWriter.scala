final case class TwistedWriter[A](log: List[String], value: A):
  def map[B](f: A => B): TwistedWriter[B] =
    TwistedWriter(log, f(value))
  def flatMap[B](f: A => TwistedWriter[B]): TwistedWriter[B] =
    val TwistedWriter(log1, b) = f(value)
    TwistedWriter(log1 ++ log, b) // flipped arguments to ++

object TwistedWriter:
  given Monad[Function, TwistedWriter] with
    override given cat: Category[Function] with
      override def andThen[A, B, C](f: A => B, g: B => C): A => C = f andThen g
      override def id[A]: A => A = identity[A]

    override def map[A, B](f: A => B): TwistedWriter[A] => TwistedWriter[B] =
      _.map(f)

    override def pure[A]: A => TwistedWriter[A] =
      TwistedWriter(Nil, _)

    override def flatten[A]: TwistedWriter[TwistedWriter[A]] => TwistedWriter[A] =
      _.flatMap(identity)