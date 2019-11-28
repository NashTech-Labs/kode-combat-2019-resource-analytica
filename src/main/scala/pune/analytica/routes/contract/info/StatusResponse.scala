package pune.analytica.routes.contract.info

import pune.analytica.domain.info.Status
import play.api.libs.json.{ Json, OWrites }
import pune.analytica.utils.json.CommonFormats.DurationFormat

import scala.concurrent.duration.Duration

case class StatusResponse(uptime: Duration)

object StatusResponse {

  implicit val StatusResponseWrites: OWrites[StatusResponse] = Json.writes[StatusResponse]

  def fromDomain(status: Status): StatusResponse = StatusResponse(
    uptime = status.uptime
  )

}
