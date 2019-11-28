package pune.analytica.routes.bookinginfo

import play.api.libs.json.{ Json, OWrites }
import pune.analytica.services.elasticservice.ESClient.EmployeeWithBookingTimeAndAsset

case class EmployeeWithBookingTimeAndAssetResponse(name: String, time: Double, favoriteAsset: String)

object EmployeeWithBookingTimeAndAssetResponse {

  implicit val EmployeeWithBookingTimeAndAssetWrites: OWrites[EmployeeWithBookingTimeAndAssetResponse] =
    Json.writes[EmployeeWithBookingTimeAndAssetResponse]

  def fromDomain(in: EmployeeWithBookingTimeAndAsset) = EmployeeWithBookingTimeAndAssetResponse(
    in.name,
    in.time,
    in.asset
  )

}
