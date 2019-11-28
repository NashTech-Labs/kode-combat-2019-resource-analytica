package pune.analytica.utils

import pune.analytica.BaseSpec
import pune.analytica.utils.StringExtensions._

class StringExtensionsSpec extends BaseSpec {

  "StringOps#toOption" should {

    "convert empty string to None" in {
      "".toOption shouldBe None
    }

    "convert nonempty string to Some" in {
      "str".toOption shouldBe Some("str")
    }

  }

}
