trait Category[●-○[_, _]]:
  def andThen[A, B, C](f: A ●-○ B, g: B ●-○ C): A ●-○ C
  def id[A]: A ●-○ A

  extension [A, B](f: A ●-○ B)
    def >>>[C](g: B ●-○ C): A ●-○ C =
      andThen(f, g)

    /** `f(g)` as sugar for `g >>> f` */
    def apply[Z](g: Z ●-○ A): Z ●-○ B =
      g >>> f