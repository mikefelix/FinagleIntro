import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http._
import com.twitter.util.Future

trait HttpInfoRequester extends InfoRequester {

  def requestInfo: Future[String] = {
    val result = for {
      current <- weatherRequest(currentUrl)
      forecast <- weatherRequest(forecastUrl)
      garage <- garageRequest
    } yield mergeReponses(garage, current, forecast)

    result onSuccess { body =>
      println(body)
    } onFailure { r =>
      println("Error: " + r.getMessage)
    } rescue {
      case _: Exception => Future.value("FAIL")
    }
  }

  private[this] val forecastUrl = "/api/4ff057a10c9613a4/forecast/q/UT/Murray.json"
  private[this] val currentUrl = "/api/4ff057a10c9613a4/conditions/q/UT/Murray.json"
  private[this] val garageUrl = "/garage/state"

  private[this] val weatherHostname = "api.wunderground.com"
  private[this] val garageHostname = "mozzarelly.com"

  private[this] val weatherClient: Service[Request, Response] = Http.newService(s"$weatherHostname:80")
  private[this] val garageClient: Service[Request, Response] = Http.newService(s"$garageHostname:80")

  private[this] def weatherRequest(url: String): Future[Response] = {
    println(s"Retrieve $url")
    val req = Request(Version.Http11, Method.Get, url)
    req.host = weatherHostname
    weatherClient(req)
  }

  private[this]def garageRequest = {
    println(s"Retrieve garage info")
    val req = com.twitter.finagle.http.Request(Version.Http11, Method.Get, garageUrl)
    req.host = garageHostname
    garageClient(req)
  }

  private[this]def mergeReponses(garage: Response, current: Response, forecast: Response) =
    s"""{
       | "garage": "${garage.contentString}",
       | "current": ${current.contentString},
       | "forecast": ${forecast.contentString}
       | }
      """.stripMargin
}
