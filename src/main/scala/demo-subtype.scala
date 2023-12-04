@main
def demoSubtype =

  val f: Nil.type  <:< Result[List[0]]   = summon
  val g: List[0]   <:< Result[List[Int]] = summon
  val h: List[Int] <:< Result[Seq[Int]]  = summon

  println {
    (f >=> g >=> h)
      : Nil.type <:< Result[Seq[Int]]
  }

  println {
    (for
      a <- summon[Nil.type <:< Result[Nil.type]]
      b <- f(a)
      c <- g(b)
      d <- h(c)
    yield d)
      : Nil.type <:< Result[Seq[Int]]
  }
