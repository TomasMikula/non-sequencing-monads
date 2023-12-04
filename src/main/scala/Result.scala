type Result[+A] = Throwable | A

given Monad[<:<, Result] with
  override def cat: Category[[From, To] =>> From <:< To] =
    summon

  override def map[A, B](f: A <:< B): Result[A] <:< Result[B] =
    f.liftCo[Result]

  override def pure[A]: A <:< Result[A] =
    summon[A <:< (Throwable | A)]

  override def flatten[A]: Result[Result[A]] <:< Result[A] =
    summon[(Throwable | Throwable | A) <:< (Throwable | A)]
