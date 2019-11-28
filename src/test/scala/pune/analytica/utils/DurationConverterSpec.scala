package pune.analytica.utils

import java.time.Duration
import java.util.concurrent.TimeUnit

import pune.analytica.BaseSpec
import pune.analytica.utils.DurationConverter.toScalaFiniteDuration

import scala.concurrent.duration.FiniteDuration

class DurationConverterSpec extends BaseSpec {

  "DurationConverter#toScalaFiniteDuration" should {

    "convert Duration to FiniteDuration properly" in {
      toScalaFiniteDuration(Duration.ofMillis(3)) shouldBe FiniteDuration(3000000, TimeUnit.NANOSECONDS)
    }

  }

}
