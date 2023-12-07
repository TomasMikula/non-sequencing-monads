# Monads Are Not About Sequencing

This repo contains code for examples presented in my talk _Monads Are Not About Sequencing_ (Functional Scala 2023, [slides](https://continuously.dev/presentations/Monads-not-about-sequencing_20231201.pdf)).

Examples:
 - Monads in the category `<:<` (Scala's subtype relationship)
   - are closure operators on the partial order of Scala types
   - example given: `type Result[+A] = Error | A`
   - **no sequencing** because there's no execution or effects
 - Monads in the category `<=` (opposite category to `=>`)
   - are comonads from `=>`
   - example given: `Id`
   - executes in reverse order, i.e. **not the sequence** given in code
 - Writer monad (in `=>`)
   `type Writer[A] = (List[String], A)`
   with flipped concatenation
   `(as, bs) => bs ++ as`
   - constituent logs appear in reverse order, i.e. **not the sequence** given in code
 - Concurrent, nondeterministic Writer monad in `-⚬`, the category of [Libretto](https://github.com/TomasMikula/libretto/) functions
   - with concurrent `merge` as the monoid operation to combine logs
   - **non-deterministic order** of messages in the result log
