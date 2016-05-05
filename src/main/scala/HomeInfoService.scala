import com.twitter.finagle.Service
import com.twitter.finagle.http._
import com.twitter.util.Future

class HomeInfoService extends Service[Request, Response] with HttpInfoRequester {

  def apply(request: Request): Future[Response] = {
    val result = requestInfo

    result map { body =>
      val response = Response(Version.Http11, Status.Ok)
      response.contentString = body
      response
    }
  }

}
