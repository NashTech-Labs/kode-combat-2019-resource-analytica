package pune.analytica.routes.bookinginfo

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import pune.analytica.routes.BaseRoutes
import pune.analytica.routes.contract.bookinginfo.RoomBookingInformation
import pune.analytica.services.elasticservice.ESClient

class BookingInfoRoutes(service: ESClient) extends BaseRoutes {

  val routes: Route =
    pathPrefix("top") {
      path("most-booked") {
        (post & entity(as[RoomBookingInformation])) { request =>
          val result = service.getTopThreeBookedAsset(request.startDate, request.endDate).map { in =>
            TopThreeUsedAssetResponse.fromDomain(in)
          }
          complete(result)
        }
      } ~
        path("most-booked-by") {
          (post & entity(as[RoomBookingInformation])) { request =>
            val result = service.getTopThreeEmployeesBookedAsset(request.startDate, request.endDate).map { in =>
              EmployeeWithBookingTimeAndAssetResponse.fromDomain(in)
            }
            complete(result)
          }
        }
    } ~
      pathPrefix("stats") {
        path("total-hours-booked") {
          (post & entity(as[RoomBookingInformation])) { request =>
            complete("understand-construction")
          }
        } ~
          (pathPrefix("total-hours-booked") & pathPrefix(Segment)) { roomName =>
            (post & entity(as[RoomBookingInformation])) { request =>
              complete("understand-construction")
            }
          } ~ path("most-booked-room") {
          (post & entity(as[RoomBookingInformation])) { request =>
            complete("understand-construction")
          }
        } ~
          path("least-booked-room") {
            (post & entity(as[RoomBookingInformation])) { request =>
              complete("understand-construction")
            }
          }
      } ~
      (pathPrefix("room") & pathPrefix(Segment)) { roomName =>
        (post & entity(as[RoomBookingInformation])) { request =>
          complete("understand-construction")
        }
      } ~
      (pathPrefix("person") & pathPrefix(Segment)) { personName =>
        (post & entity(as[RoomBookingInformation])) { request =>
          complete("understand-construction")
        }
      }

}
