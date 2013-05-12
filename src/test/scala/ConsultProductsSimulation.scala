
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import bootstrap._

class ConsultProductsSimulation extends Simulation {

  val httpConf = httpConfig
    .baseURL("http://bees-shop.clardeur.cloudbees.net.")

  val scn = scenario("Consult one or many products")
    .exec(
      http("Home page")
        .get("/")
        .check(status.is(200)))
    .exec(
      http("See the list of products")
        .get("/product")
        .check(status.is(200))
        .check(regex("""<a href="/product/(\d+)">""").findAll.exists.saveAs("products")))
    .foreach("${products}", "productId") {
      exec(
        http("Consult product ${productId}")
          .get("/product/${productId}")
          .check(status.is(200))
      )
    }

  setUp(scn.inject(ramp(3 users) over (5 seconds)).protocolConfig(httpConf))
}