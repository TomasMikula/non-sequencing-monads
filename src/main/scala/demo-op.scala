@main
def demoOp =

  val f: String  <= Id[Boolean]   = Op { case Id(b)  => println("f"); String.valueOf(b) }
  val g: Boolean <= Id[Int]       = Op { case Id(i)  => println("g"); i % 2 == 0 }
  val h: Int     <= Id[List[Int]] = Op { case Id(xs) => println("h"); xs.sum }

  println {
    (f >=> g >=> h)
      .run(Id(List.range(1, 7)))
  }
  // Output:
  //
  //   h
  //   g
  //   f
  //   false
  //
  // That is, f, g, h execute in the opposite order

  println {
    (for
      s <- summon[Monad[<=, Id]].pure[String]
      b <- f(s)
      i <- g(b)
      l <- h(i)
    yield l)
      .run(Id(List.range(1, 7)))
  }
  // Output:
  //
  //   h
  //   g
  //   f
  //   false
  //
  // That is, f, g, h execute in the opposite order

