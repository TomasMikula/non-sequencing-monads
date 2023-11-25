import scala.annotation.targetName

/** Witnesses that `M` is a nonad in category `●-○`. */
trait Monad[●-○[_, _], M[_]]:
  given cat: Category[●-○]

  def map[A, B](f: A ●-○ B): M[A] ●-○ M[B]
  def pure[A]: A ●-○ M[A]
  def flatten[A]: M[M[A]] ●-○ M[A]

  def flatMap[A, B](f: A ●-○ M[B]): M[A] ●-○ M[B] =
    val mf: M[A] ●-○ M[M[B]] = map(f)
    mf >>> flatten

  extension [A, B](f: A ●-○ M[B])
    /** Kleisli composition. */
    def >=>[C](g: B ●-○ M[C]): A ●-○ M[C] =
      f >>> this.map(g) >>> flatten

    /** Support for Scala's `for` comprehensions. */
    @targetName("mapSyntax")
    def map[C](g: (B ●-○ B) => (B ●-○ C)): A ●-○ M[C] =
      f >>> this.map(g(cat.id[B]))

    /** Support for Scala's `for` comprehensions. */
    @targetName("flatMapSyntax")
    def flatMap[C](g: (B ●-○ B) => (B ●-○ M[C])): A ●-○ M[C] =
      f >>> this.flatMap(g(cat.id[B]))