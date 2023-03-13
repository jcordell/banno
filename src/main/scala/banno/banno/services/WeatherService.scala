package banno.banno.services

import banno.banno.clients.WeatherGovClient
import banno.banno.models.{Coordinates, WeatherApiResponse}
import cats.data.EitherT
import cats.effect.IO

class WeatherService(weatherGovClient: WeatherGovClient) {
  def getWeather(
      coordinates: Coordinates
  ): IO[Either[String, WeatherApiResponse]] = {
    (for {
      points <- EitherT.liftF(weatherGovClient.getPoints(coordinates))
      gridPoints <- EitherT.liftF(
        weatherGovClient.getGridPoints(
          points.properties.gridId,
          points.properties.gridX,
          points.properties.gridY
        )
      )
      currentWeather <- EitherT.fromOption[IO](
        gridPoints.properties.periods.headOption,
        "Could not get current weather temperature from response."
      )
    } yield WeatherApiResponse(
      currentWeather.shortForecast,
      toCharacterization(currentWeather.temperature)
    )).value
  }

  private def toCharacterization(temperature: Int): String =
    if (temperature > 82) "Hot"
    else if (temperature > 40) "Moderate"
    else "Cold"

}
