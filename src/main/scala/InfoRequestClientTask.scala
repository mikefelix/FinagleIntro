import com.twitter.finagle.http.{Request, Version, Method}
import com.twitter.util.Await

object InfoRequestClientTask extends App {

  val client = new HomeInfoService

  val req = Request(Version.Http11, Method.Get, "")
  val future = client(req)

  val result = Await.result(future)

  println(result)
}
