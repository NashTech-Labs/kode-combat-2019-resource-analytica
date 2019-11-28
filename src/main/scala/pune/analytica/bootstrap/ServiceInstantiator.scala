package pune.analytica.bootstrap

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.stream.ActorMaterializer
import com.sksamuel.elastic4s.http.{ ElasticClient, ElasticProperties }
import com.typesafe.config.Config
import pune.analytica.services.bookinginfo.BookingInfoService
import pune.analytica.services.elasticservice.ESClient
import pune.analytica.services.info.InfoService

import scala.concurrent.ExecutionContext

class ServiceInstantiator(conf: Config)(
  implicit system: ActorSystem,
  val logger: LoggingAdapter,
  materializer: ActorMaterializer
) {

  implicit val ec: ExecutionContext = system.dispatcher

  val esConfig: Config = conf.getConfig("elastic-search")
  val esHost: String = esConfig.getString("host")
  val esPort: String = esConfig.getString("port")
  val client = ElasticClient(ElasticProperties(s"http://$esHost:$esPort"))

  lazy val infoService: InfoService = new InfoService
  lazy val esClient: ESClient = new ESClient(esConfig, client)
  lazy val bookingInfoService: BookingInfoService = new BookingInfoService

}
