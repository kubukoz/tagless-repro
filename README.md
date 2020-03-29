# tagless-repro

Reproduction of an issue with cats-tagless: if you run the code as it is, you should get an error like this:

```scala
[E] Exception in thread "main" java.lang.NoClassDefFoundError: Foo$
[E]     at Main$.delayedEndpoint$Main$1(Main.scala:54)
[E]     at Main$delayedInit$body.apply(Main.scala:35)
[E]     at scala.Function0.apply$mcV$sp(Function0.scala:39)
[E]     at scala.Function0.apply$mcV$sp$(Function0.scala:39)
[E]     at scala.runtime.AbstractFunction0.apply$mcV$sp(AbstractFunction0.scala:17)
[E]     at scala.App.$anonfun$main$1(App.scala:73)
[E]     at scala.App.$anonfun$main$1$adapted(App.scala:73)
[E]     at scala.collection.IterableOnceOps.foreach(IterableOnce.scala:553)
[E]     at scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:551)
[E]     at scala.collection.AbstractIterable.foreach(Iterable.scala:921)
[E]     at scala.App.main(App.scala:73)
[E]     at scala.App.main$(App.scala:71)
[E]     at Main$.main(Main.scala:35)
[E]     at Main.main(Main.scala)
[E] Caused by: java.lang.ClassNotFoundException: Foo$
[E]     at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:581)
[E]     at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:178)
[E]     at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:521)
[E]     ... 14 more
```

(that's bloop output, but I've been able to reliably get this issue every time running from a native-packager generated script or from sbt).

Also got a Scastie: https://scastie.scala-lang.org/UqkDN6TERKyoTnLvwwl9vg

Tried OpenJDK 8, 11 and 14, as well as GraalVM 20 (JDK11).

Uncommenting the companion object of Foo fixes the issue.
