
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BeesShopSimulation extends Simulation {

  val httpConf = httpConfig.baseURL("http://localhost:8080/gatling-bees-shop")

  setUp(
    ConsultProductsScenario.scn
      .inject(ramp(1000 users) over (1 minute))
      .protocolConfig(httpConf),
    AddProductsInCartScenario.scn
      .inject(nothingFor(10 seconds), ramp(500 users) over (40 seconds))
      .protocolConfig(httpConf),
    SearchAndCommentProductsScenario.scn
      .inject(nothingFor(40 seconds), atOnce(200 users))
      .protocolConfig(httpConf)
  )
}