/** The subtype relationship `<:<` is a category. */
given Category[<:<] with
  override def andThen[A, B, C](f: A <:< B, g: B <:< C): A <:< C =
    f andThen g
  override def id[A]: A <:< A =
    <:<.refl
