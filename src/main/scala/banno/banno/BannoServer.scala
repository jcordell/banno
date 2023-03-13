package banno.banno

import banno.banno.clients.WeatherGovClient
import banno.banno.services.WeatherService
import cats.effect.IO
import com.comcast.ip4s._
import org.http4s.client.Client
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
import org.http4s.server.middleware.Logger

object BannoServer {

  def run: IO[Nothing] = {
    for {
      client: Client[IO] <- EmberClientBuilder.default[IO].build
      weatherGovClient = new WeatherGovClient(client)
      weatherService = new WeatherService(weatherGovClient)

      // Combine Service Routes into an HttpApp.
      // Can also be done via a Router if you
      // want to extract segments not checked
      // in the underlying routes.
      httpApp = WeatherRoutes.weatherRoutes(weatherService).orNotFound

      // With Middlewares in place
      finalHttpApp = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)

      _ <-
        EmberServerBuilder
          .default[IO]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"8080")
          .withHttpApp(finalHttpApp)
          .build
    } yield ()
  }.useForever
}
