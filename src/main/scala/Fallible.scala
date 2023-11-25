type Fallible[+A] = Throwable | A

given Monad[<:<, Fallible] with
  override def cat: Category[[From, To] =>> From <:< To] =
    summon

  override def map[A, B](f: A <:< B): Fallible[A] <:< Fallible[B] =
    f.liftCo[Fallible]

  override def pure[A]: A <:< Fallible[A] =
    summon[A <:< (Throwable | A)]

  override def flatten[A]: Fallible[Fallible[A]] <:< Fallible[A] =
    summon[(Throwable | Throwable | A) =:= (Throwable | A)]
