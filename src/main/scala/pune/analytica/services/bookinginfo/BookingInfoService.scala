package pune.analytica.services.bookinginfo

import akka.event.LoggingAdapter

import scala.concurrent.{ ExecutionContext, Future }

class BookingInfoService(
  implicit val ec: ExecutionContext,
  val logger: LoggingAdapter
) {

  def getMostBooked(startDate: Long, endDate: Long): Future[String] = Future.successful("Most Booked Data")

  def getMostBookedBy(startDate: Long, endDate: Long): Future[String] =
    Future.successful("Most Booked Data By - gets top 3")

  def getTotalHoursBooked(startDate: Long, endDate: Long): Future[String] =
    Future.successful("Total hours booked")

  def getTotalHoursBookedByRoom(roomName: String, startDate: Long, endDate: Long): Future[String] =
    Future.successful("Total hours booked By Room")

  def getMostBookedRoom(startDate: Long, endDate: Long): Future[String] =
    Future.successful("most booked room")

  def getLeastBookedRoom(startDate: Long, endDate: Long): Future[String] =
    Future.successful("least booked room")

  def getInformationByRoom(roomName: String, startDate: Long, endDate: Long): Future[String] =
    Future.successful("Info of that Room")

  def getInformationByPerson(personName: String, startDate: Long, endDate: Long): Future[String] =
    Future.successful("Info of that Person")

}
