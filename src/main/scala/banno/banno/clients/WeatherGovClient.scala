package banno.banno.clients

import banno.banno.models.Coordinates
import banno.banno.models.external.weather.{
  GridpointsApiResponse,
  PointsApiResponse
}
import banno.banno.util.ImplicitEntityCodecs.jsonDecoder
import cats.effect.IO
import io.circe.generic.auto._
import org.http4s.client.Client

class WeatherGovClient(client: Client[IO]) {
  private val BaseUrl = "https://api.weather.gov"

  def getPoints(coordinates: Coordinates): IO[PointsApiResponse] = {
    val url =
      s"$BaseUrl/points/${coordinates.latitude},${coordinates.longitude}"
    client.expect[PointsApiResponse](url)
  }

  def getGridPoints(id: String, x: Int, y: Int): IO[GridpointsApiResponse] = {
    val url =
      s"$BaseUrl/gridpoints/$id/$x,$y/forecast"
    client.expect[GridpointsApiResponse](url)
  }

}
