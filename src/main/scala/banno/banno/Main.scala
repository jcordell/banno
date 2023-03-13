package banno.banno

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple {
  val run = BannoServer.run[IO]
}
