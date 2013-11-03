
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BeesShopSimulation extends Simulation {

  val httpConf = http.baseURL("http://localhost:8080/gatling-bees-shop")

  setUp(
    ConsultProductsScenario.scn.inject(ramp(100 users) over (20 seconds)),
    AddProductsInCartScenario.scn.inject(ramp(100 users) over (20 seconds)),
    SearchAndCommentProductsScenario.scn.inject(ramp(100 users) over (20 seconds))
  ).protocols(httpConf)
}