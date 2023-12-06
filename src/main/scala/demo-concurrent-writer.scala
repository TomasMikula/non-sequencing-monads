import libretto.scaletto.StarterApp
import libretto.scaletto.StarterKit.*

object ConcurrentWriterDemo extends StarterApp:
  import ConcurrentWriters.*

  override def blueprint: Done -⚬ Done =
    λ.+ { go =>
      val log |*| ?(_) = {
        for
          u1 <- go :>> write("1")
          u2 <- go :>> write("2")
          u3 <- go :>> write("3")
          u4 <- go :>> write("4")
        yield returning(u1, u2, u3, u4)
      }.run

      toScalaList(log) :>> printLine(_.mkString(" "))
    }
    // Output non-deterministic, e.g.
    //
    //   2 1 4 3
