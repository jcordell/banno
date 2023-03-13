package banno.banno.models.external.weather

case class PointsApiResponse(
    id: String,
    properties: PointsProperties
)

case class PointsProperties(
    gridId: String,
    gridX: Int,
    gridY: Int
)
