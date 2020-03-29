import cats.implicits._
import cats.tagless.implicits._
import cats.tagless.finalAlg
import cats.tagless.autoInstrument
import cats.tagless.diagnosis.Instrumentation
import cats.tagless.diagnosis.Instrument
import cats.tagless.autoFunctorK
import cats.tagless.FunctorK
import cats.~>
import cats.data.Const
import cats.Apply

object types {
  type Strings[A] = Const[List[String], A]
}

@finalAlg
trait Console[F[_]] {
  def putStrLn(s: String): F[Unit]
}

object Console {
  implicit val constConsole: Console[types.Strings] = s => Const(List(s))
}

@finalAlg
@autoInstrument
@autoFunctorK
trait Foo[F[_]] {
  def bar(a: Int): F[Int]
}

//object Foo {}

object Main extends App {

  import Console.constConsole._

  import types.Strings

  val fooIO: Foo[Strings] = new Foo[Strings] {
    def bar(a: Int): Strings[Int] = putStrLn(show"bar($a)").as(42)
  }

  def logged[Alg[_[_]]: Instrument: FunctorK, F[_]: Console: Apply](alg: Alg[F]): Alg[F] =
    Instrument[Alg]
      .instrument(alg)
      .mapK(
        λ[Instrumentation[F, *] ~> F](i =>
          Console[F].putStrLn(show"${i.algebraName}.${i.methodName}") *> i.value
        )
      )

  println(logged(fooIO).bar(10).getConst)
}
