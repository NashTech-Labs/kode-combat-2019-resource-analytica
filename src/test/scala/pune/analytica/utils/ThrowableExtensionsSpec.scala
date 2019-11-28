package pune.analytica.utils

import pune.analytica.BaseSpec
import pune.analytica.utils.ThrowableExtensions._

class ThrowableExtensionsSpec extends BaseSpec {

  "ThrowableOps#printInfo" should {

    "convert Throwable to String properly" in {
      val exception = new RuntimeException("fail")
      val info = exception.printInfo

      info should include("RuntimeException")
      info should include("fail")
    }

  }
}
