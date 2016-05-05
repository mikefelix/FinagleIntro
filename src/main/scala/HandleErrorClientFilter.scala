import com.twitter.finagle.http._
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.util.Future

class InvalidRequest extends Exception

class HandleErrorClientFilter extends SimpleFilter[Request, Response] {
  def apply(request: Request, service: Service[Request, Response]): Future[Response] = {

    // flatMap asynchronously responds to requests and can "map" them to both
    // success and failure values:
    service(request) flatMap { response =>
      response.status match {
        case Status.Ok => Future.value(response)
        case Status.Forbidden => Future.exception(new InvalidRequest)
        case _ => Future.exception(new Exception(response.status.reason))
      }
    }
  }
}
