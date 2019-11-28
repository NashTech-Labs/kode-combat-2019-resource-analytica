package pune.analytica.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.typesafe.config.ConfigFactory
import play.api.libs.json._

class BaseRoutesSpec extends RoutesSpec {

  sealed trait Param

  case object Param extends Param

  implicit val paramFormat: Format[Param] = new Format[Param] {
    override def writes(o: Param): JsValue = JsString("param")

    override def reads(json: JsValue): JsResult[Param] = json match {
      case JsString("param") => JsSuccess(Param)
      case _ => JsError("error")
    }
  }

  private val config = ConfigFactory.parseString("routes.debug-exceptions = true")

  trait Setup extends RoutesSetup {
    val routes: Route = BaseRoutes.seal(config)(
      new BaseRoutes {
        val routes: Route = concat(
          path("fail") {
            get {
              throw new RuntimeException("Some fail")
            }
          },
          path("params") {
            (get & parameters(
              'x.as[Param](fromStringUnmarshaller[Param])
            )) { x =>
              complete(x)
            }
          }
        )
      }.routes
    )
  }

  "BaseRoutes#seal" should {

    "add an exception handler" in new Setup {
      Get("/fail").check {
        status shouldBe StatusCodes.InternalServerError
      }
    }

    "add a rejection handler" in new Setup {
      Get("/not-found").check {
        status shouldBe StatusCodes.NotFound
      }
    }

  }

  "BaseRoutes#fromStringUnmarshaller" should {

    "parse string" in new Setup {
      Get(s"/params?x=param").check {
        status shouldBe StatusCodes.OK
      }
    }
  }

}
