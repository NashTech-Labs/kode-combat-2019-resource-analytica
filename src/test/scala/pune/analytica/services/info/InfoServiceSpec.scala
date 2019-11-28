package pune.analytica.services.info

import pune.analytica.BaseSpec

class InfoServiceSpec extends BaseSpec {

  trait Setup {
    val service = new InfoService()(ec, logger)
  }

  "InfoService#getUpTime" should {

    "get uptime" in new Setup {
      whenReady(service.getUptime())(_.uptime.toMillis should be > 0L)
    }

  }

}
