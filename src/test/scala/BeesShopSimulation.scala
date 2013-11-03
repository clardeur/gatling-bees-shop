
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.util._

class BeesShopSimulation extends Simulation {

  val port = Properties.envOrElse("maven.tomcat.port", "8080")

  val httpConf = http.baseURL(s"http://localhost:$port/gatling-bees-shop")

  setUp(
    ConsultProductsScenario.scn.inject(rampRate(10 usersPerSec) to(200 usersPerSec) during(30 seconds))
  ).protocols(httpConf)
}