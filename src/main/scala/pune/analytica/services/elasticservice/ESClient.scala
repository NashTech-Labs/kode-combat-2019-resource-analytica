package pune.analytica.services.elasticservice

import com.sksamuel.elastic4s.http.ElasticClient
import com.typesafe.config.Config
import pune.analytica.services.elasticservice.ESClient.{
  EmployeeWithBookingTime,
  EmployeeWithBookingTimeAndAsset,
  TopThreeUsedAsset
}

class ESClient(conf: Config, client: ElasticClient) {

  import com.sksamuel.elastic4s.http.ElasticDsl._

  val index: String = conf.getString("index")

  def getTopThreeBookedAsset(startTime: Long, endTime: Long): List[TopThreeUsedAsset] =
    client
      .execute {
        search(index).query(rangeQuery("startTime") gte startTime lte (endTime)).aggregations {
          termsAgg("group_by", "assetName.keyword").subAggregations {
            sumAgg("totalTime", "bookedFor")
          }
        }
      }
      .await
      .result
      .aggregations
      .data
      .getOrElse("group_by", throw new RuntimeException())
      .asInstanceOf[Map[String, Any]]
      .getOrElse("buckets", throw new RuntimeException())
      .asInstanceOf[List[Map[String, Any]]]
      .map { map =>
        val name = map.getOrElse("key", throw new RuntimeException()).toString
        val totalTime = map.getOrElse("totalTime", throw new RuntimeException()).asInstanceOf[Map[String, Double]]
        val value = totalTime.getOrElse("value", throw new RuntimeException())
        TopThreeUsedAsset(name, value)
      }
      .reverse
      .take(3)

  def getTopThreeEmployeesBookedAsset(startTime: Long, endTime: Long): List[EmployeeWithBookingTimeAndAsset] =
    client
      .execute {
        search(index).query(rangeQuery("startTime") gte startTime lte (endTime)).aggregations {
          termsAgg("group_by", "username.keyword").subAggregations {
            sumAgg("totalTime", "bookedFor")
          }
        }

      }
      .await
      .result
      .aggregations
      .data
      .getOrElse("group_by", throw new RuntimeException())
      .asInstanceOf[Map[String, Any]]
      .getOrElse("buckets", throw new RuntimeException())
      .asInstanceOf[List[Map[String, Any]]]
      .map { map =>
        val name = map.getOrElse("key", throw new RuntimeException()).toString
        val totalTime = map.getOrElse("totalTime", throw new RuntimeException()).asInstanceOf[Map[String, Double]]
        val value = totalTime.getOrElse("value", throw new RuntimeException())
        EmployeeWithBookingTime(name, value)
      }
      .reverse
      .take(3)
      .flatMap { employeeWithBookingTime =>
        val name = employeeWithBookingTime.name
        client
          .execute {
            search(index)
              .query(rangeQuery("startTime") gte startTime lte (endTime))
              .bool {
                must {
                  matchQuery("username", name)
                }
              }
              .aggregations {
                termsAgg("group_asset_name", "assetName.keyword")
              }
          }
          .await
          .result
          .aggregations
          .data
          .getOrElse("group_asset_name", throw new RuntimeException())
          .asInstanceOf[Map[String, Any]]
          .getOrElse("buckets", throw new RuntimeException())
          .asInstanceOf[List[Map[String, Any]]]
          .map { map =>
            val assetName =
              map.getOrElse("key", throw new RuntimeException()).toString
            EmployeeWithBookingTimeAndAsset(
              employeeWithBookingTime.name,
              employeeWithBookingTime.time,
              assetName
            )
          }
          .take(3)
      }

}

object ESClient {

  case class EmployeeWithBookingTime(name: String, time: Double)
  case class EmployeeWithBookingTimeAndAsset(name: String, time: Double, asset: String)

  case class TopThreeUsedAsset(name: String, time: Double)

}
