package pune.analytica.routes.bookinginfo

import play.api.libs.json.{ Json, OWrites }
import pune.analytica.services.elasticservice.ESClient.TopThreeUsedAsset

case class TopThreeUsedAssetResponse(name: String, time: Double)

object TopThreeUsedAssetResponse {

  implicit val TopThreeUsedAssetResponseWrites: OWrites[TopThreeUsedAssetResponse] =
    Json.writes[TopThreeUsedAssetResponse]

  def fromDomain(in: TopThreeUsedAsset) = TopThreeUsedAssetResponse(
    in.name,
    in.time
  )

}
