package pune.analytica.utils.json

import pune.analytica.BaseSpec
import play.api.libs.json._

class EnumFormatBuilderSpec extends BaseSpec {

  sealed trait EnumSample

  object EnumSample {

    case object Item1 extends EnumSample

    case object Item2 extends EnumSample

  }

  "EnumFormatBuilder#build" should {

    "build enum serializer" in {
      val enumSampleFormat = EnumFormatBuilder.build[EnumSample](
        {
          case "ITEM 1" => EnumSample.Item1
          case "ITEM 2" => EnumSample.Item2
        },
        {
          case EnumSample.Item1 => "ITEM 1"
          case EnumSample.Item2 => "ITEM 2"
        },
        "enum sample"
      )

      enumSampleFormat.writes(EnumSample.Item1) shouldBe JsString("ITEM 1")
      enumSampleFormat.reads(JsString("ITEM 2")) shouldBe JsSuccess(EnumSample.Item2)
      enumSampleFormat.reads(JsString("ITEM 3")) shouldBe a[JsError]
      enumSampleFormat.reads(JsNumber(1)) shouldBe a[JsError]
    }

  }

}
