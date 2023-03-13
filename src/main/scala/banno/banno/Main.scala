package banno.banno

import cats.effect.IOApp

object Main extends IOApp.Simple {
  val run = BannoServer.run
}
