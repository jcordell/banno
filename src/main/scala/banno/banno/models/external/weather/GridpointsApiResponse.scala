package banno.banno.models.external.weather

case class GridpointsApiResponse(
    properties: GridpointProperties
)

case class GridpointProperties(
    periods: Seq[Periods]
)

case class Periods(
    temperature: Int,
    shortForecast: String
)
