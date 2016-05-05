import com.twitter.util.Await

object InfoRequestTask extends App {

  val requester = new HttpInfoRequester {}
  val future = requester.requestInfo

  val result = Await.result(future)

  println(result)
}
