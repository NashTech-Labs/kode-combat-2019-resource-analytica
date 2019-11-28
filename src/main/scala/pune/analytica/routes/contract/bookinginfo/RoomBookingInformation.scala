package pune.analytica.routes.contract.bookinginfo

import play.api.libs.json.{ Json, Reads }

case class RoomBookingInformation(startDate: Long, endDate: Long)

object RoomBookingInformation {
  implicit val RoomBookingInformationRequestReads: Reads[RoomBookingInformation] = Json.reads[RoomBookingInformation]
}
