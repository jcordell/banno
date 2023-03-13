package banno.banno

import banno.banno.models.Coordinates
import banno.banno.services.WeatherService
import banno.banno.util.ImplicitEntityCodecs.jsonEncoder
import cats.effect.IO
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.dsl.impl.QueryParamDecoderMatcher

object WeatherRoutes {

  def weatherRoutes(W: WeatherService): HttpRoutes[IO] = {
    val dsl = new Http4sDsl[IO] {}
    import dsl._
    HttpRoutes.of[IO] {
      case GET -> Root / "weather" :?
          LatitudeQueryParamMatcher(latitude) +& LongitudeQueryParamMatcher(
            longitude
          ) =>
        for {
          maybeWeather <- W.getWeather(Coordinates(latitude, longitude))
          resp <- maybeWeather match {
            case Right(weather) => Ok(weather)
            case Left(error)    => InternalServerError(error)
          }
        } yield resp
    }
  }
}

// Could make "Coordinate" class and decoder to make sure query param is a valid coordinate value.
object LatitudeQueryParamMatcher
    extends QueryParamDecoderMatcher[Double]("latitude")
object LongitudeQueryParamMatcher
    extends QueryParamDecoderMatcher[Double]("longitude")
