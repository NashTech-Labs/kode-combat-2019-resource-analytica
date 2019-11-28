package pune.analytica.bootstrap

import akka.http.scaladsl.server.Directives.{ concat, ignoreTrailingSlash }
import akka.http.scaladsl.server.Route
import com.typesafe.config.Config
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import pune.analytica.routes.BaseRoutes
import pune.analytica.routes.bookinginfo.BookingInfoRoutes
import pune.analytica.routes.info.InfoRoutes

import scala.concurrent.ExecutionContext

class RoutesInstantiator(
  conf: Config,
  services: ServiceInstantiator
)(
  implicit val ec: ExecutionContext
) extends PlayJsonSupport {

  private val infoRoutes = new InfoRoutes(services.infoService)
  private val bookingInfoRoutes = new BookingInfoRoutes(services.esClient)

  val routes: Route = BaseRoutes.seal(conf) {
    ignoreTrailingSlash {
      concat(
        infoRoutes.routes,
        bookingInfoRoutes.routes
      )
    }
  }

}
