@main
def demoTwistedWriter =
  val TwistedWriter(log, _) =
    for
        i <- TwistedWriter(List("Have"), 5)
        j <- TwistedWriter(List("some"), i * 11)
        k <- TwistedWriter(List("fun"), j - 7)
    yield k

  log.foreach(println(_))
  // Output:
  //
  //   fun
  //   some
  //   Have

