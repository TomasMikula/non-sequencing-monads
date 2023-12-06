import libretto.lambda.util.SourcePos
import libretto.scaletto.StarterKit.coreLib.LList
import libretto.scaletto.StarterKit.{Monad => _, *}
import libretto.scaletto.StarterKit.dsl

object ConcurrentWriters {
  opaque type ConcurrentWriter[A] = LList[Val[String]] |*| A

  def write(msg: String): Done -⚬ ConcurrentWriter[One] =
    λ { d => LList.singletonOnSignal[Val[String]](constVal(msg)(d)) |*| $.one }

  given monad: Monad[-⚬, ConcurrentWriter] with
    override given cat: Category[-⚬] with
      override def andThen[A, B, C](f: A -⚬ B, g: B -⚬ C): A -⚬ C = f > g
      override def id[A]: A -⚬ A = dsl.id[A]

    override def map[A, B](f: A -⚬ B): ConcurrentWriter[A] -⚬ ConcurrentWriter[B] =
      dsl.snd(f)

    override def pure[A]: A -⚬ ConcurrentWriter[A] =
      λ { a => constant(LList.nil) |*| a }

    override def flatten[A]: ConcurrentWriter[ConcurrentWriter[A]] -⚬ ConcurrentWriter[A] =
      λ { case log1 |*| (log2 |*| a) =>
        // merge is concurrent and non-deterministic
        LList.merge(log1 |*| log2) |*| a
      }

  extension [A](ma: $[ConcurrentWriter[A]])
    def run: $[LList[Val[String]] |*| A] =
      ma
    def map[B](f: $[A] => $[B])(using SourcePos, LambdaContext): $[ConcurrentWriter[B]] =
      val log |*| a = ma
      log |*| f(a)
    def flatMap[B](f: $[A] => $[ConcurrentWriter[B]])(using SourcePos, LambdaContext): $[ConcurrentWriter[B]] =
      val log1 |*| a = ma
      val log2 |*| b = f(a)
      LList.merge(log1 |*| log2) |*| b
}
