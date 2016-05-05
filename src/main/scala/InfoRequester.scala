import com.twitter.util.Future

/**
  * InfoRequester
  * User: michael.felix
  * Date: 5/3/16
  */
trait InfoRequester {
  def requestInfo: Future[String]
}
