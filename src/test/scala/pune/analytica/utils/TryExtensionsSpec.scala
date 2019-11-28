package pune.analytica.utils

import pune.analytica.BaseSpec
import pune.analytica.utils.TryExtensions._

import scala.util.{ Failure, Success, Try }

class TryExtensionsSpec extends BaseSpec {

  "TryOps#toFuture" should {

    "convert Success to Future properly" in {
      Success(3).toFuture.value shouldBe Some(Success(3))
    }

    "convert Failure to Future properly" in {
      val exception = new RuntimeException("fail")

      Failure(exception).toFuture.value shouldBe Some(Failure(exception))
    }

  }

  "TryObjOps#sequence" should {

    "convert List[Try] to Try[List] properly" in {
      val exception = new RuntimeException("fail")

      Try.sequence(List(Success(1), Success(2))) shouldBe Success(List(1, 2))
      Try.sequence(List(Success(1), Failure(exception))) shouldBe Failure(exception)
    }

  }

}
