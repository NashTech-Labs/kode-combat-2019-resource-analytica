package pune.analytica.utils.validation

import cats.data.EitherT
import cats.implicits._
import pune.analytica.BaseSpec
import pune.analytica.utils.validation.Option._

import scala.concurrent.Future
import scala.util.Success

class OptionSpec extends BaseSpec {

  "OptionOps#validate" should {

    "validate option properly" in {
      Some(3).validate { x: Int => Left(x.toString) } shouldBe Left("3")
      None.validate { x: Int => Left(x.toString) } shouldBe Right(())

      Some(4).validate { x: Int =>
        Future.successful(Left(x.toString))
      }.value shouldBe Some(Success(Left("4")))
      None.validate { x: Int =>
        Future.successful(Left(x.toString))
      }.value shouldBe Some(Success(Right(())))

      Some(5).validate { x: Int =>
        EitherT.leftT[Future, Unit](x.toString)
      }.value.value shouldBe Some(Success(Left("5")))
      None.validate { x: Int =>
        EitherT.leftT[Future, Unit](x.toString)
      }.value.value shouldBe Some(Success(Right(())))
    }

  }

}
