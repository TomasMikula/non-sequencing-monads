@main
def demoSubtype =

  val f: Nil.type  <:< Fallible[List[0]]   = summon
  val g: List[0]   <:< Fallible[List[Int]] = summon
  val h: List[Int] <:< Fallible[Seq[Int]]  = summon

  println {
    (f >=> g >=> h)
      : Nil.type <:<  Fallible[Seq[Int]]
  }

  println {
    (for
      a <- summon[Monad[<:<, Fallible]].pure[Nil.type]
      b <- f(a)
      c <- g(b)
      d <- h(c)
    yield d)
      : Nil.type <:<  Fallible[Seq[Int]]
  }
