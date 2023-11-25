case class Id[A](a: A)

object Id:
  /** `Id` is a monad in the category of opposite functions `<=`. */
  given Monad[<=, Id] with
    override def cat: Category[<=] =
      summon

    override def map[A, B](f: A <= B): Id[A] <= Id[B] =
      Op { case Id(b) => Id(f.run(b)) }

    override def pure[A]: A <= Id[A] =
      Op(_.a)

    override def flatten[A]: Id[Id[A]] <= Id[A] =
      Op(Id(_))
