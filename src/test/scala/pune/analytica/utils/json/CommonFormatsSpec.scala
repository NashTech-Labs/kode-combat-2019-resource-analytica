package pune.analytica.utils.json

import java.util.concurrent.TimeUnit

import pune.analytica.BaseSpec
import play.api.libs.json._

import scala.concurrent.duration._

class CommonFormatsSpec extends BaseSpec {

  "DurationFormat#reads" should {

    "deserialize Duration properly" in {
      CommonFormats.DurationFormat.reads(JsString("2 minutes")) shouldBe JsSuccess(2.minutes)
    }

    "not deserialize incorrect string" in {
      CommonFormats.DurationFormat.reads(JsString("Not a duration")) shouldBe a[JsError]
    }

    "not deserialize not a string" in {
      CommonFormats.DurationFormat.reads(JsNumber(3)) shouldBe a[JsError]
    }

  }

  "DurationFormat#writes" should {

    "serialize Duration properly" in {
      CommonFormats.DurationFormat.writes(16.hours) shouldBe JsString("16 hours")
    }

  }

  "FloatWrites#writes" should {

    "serialize Float properly" in {
      CommonFormats.FloatWrites.writes(0.5f) shouldBe JsNumber(0.5)
    }

  }

  "TimeUnitFormat#reads" should {

    "deserialize TimeUnit properly" in {
      CommonFormats.TimeUnitFormat.reads(JsString("SECONDS")) shouldBe JsSuccess(TimeUnit.SECONDS)
    }

    "not deserialize incorrect string" in {
      CommonFormats.TimeUnitFormat.reads(JsString("Not a time unit")) shouldBe a[JsError]
    }

    "not deserialize not a string" in {
      CommonFormats.TimeUnitFormat.reads(JsNumber(3)) shouldBe a[JsError]
    }

  }

  "DurationFormat#writes" should {

    "serialize TimeUnit properly" in {
      CommonFormats.TimeUnitFormat.writes(TimeUnit.MINUTES) shouldBe JsString("MINUTES")
    }

  }

  "FiniteDurationFormat#reads" should {

    "deserialize FiniteDuration properly" in {
      CommonFormats.FiniteDurationFormat.reads(
        JsObject(
          Map(
            "length" -> JsNumber(3),
            "unit" -> JsString("SECONDS"),
          )
        )
      ) shouldBe JsSuccess(FiniteDuration(3, TimeUnit.SECONDS))
    }
  }

  "FiniteDurationFormat#writes" should {
    "serialize FiniteDuration properly" in {
      CommonFormats.FiniteDurationFormat.writes(FiniteDuration(11, TimeUnit.MINUTES)) shouldBe JsObject(
        Map(
          "length" -> JsNumber(11),
          "unit" -> JsString("MINUTES"),
        )
      )
    }

  }

}
