
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.util._

class BeesShopSimulation extends Simulation {

  val port = Properties.envOrElse("maven.tomcat.port", "8080")

  val httpConf = http.baseURL(s"http://localhost:$port/gatling-bees-shop")

  setUp(
    ConsultProductsScenario.scn.inject(ramp(100 users) over (20 seconds)),
    AddProductsInCartScenario.scn.inject(ramp(100 users) over (20 seconds)),
    SearchAndCommentProductsScenario.scn.inject(ramp(100 users) over (20 seconds))
  ).protocols(httpConf)
}