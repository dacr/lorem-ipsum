# Lorem ipsum

Lorem ipsum dolor sit amet, consectetur adipiscing elit,
sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
nisi ut aliquip ex ea.

Sed ut perspiciatis unde omnis iste natus error sit voluptatem
accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae
ab illo inventore veritatis et quasi architecto beatae vitae dicta
sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur
aut odit aut fugit, sed quia consequuntur magni dolores eos qui
ratione voluptatem sequi nesciunt.

Using `sbt console` :
```scala
import loremipsum._
println(LoremIpsum.generate(42*6).map(_.text).mkString("\n\n"))
```

Using ammonite `amm` :
```scala
import $ivy.`fr.janalyse::lorem-ipsum:0.0.2`
import loremipsum._
val story = LoremIpsum.generate(42*6,true).map(_.text).mkString("\n\n")
println(story)
```