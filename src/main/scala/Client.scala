import com.twitter.finagle.{Http, Service, http}
import com.twitter.util.{Await, Future}

object Client extends App {
  val client: Service[http.Request, http.Response] = Http.newService("www.scala-lang.org:80")

  val request = http.Request(http.Method.Get, "/")
  request.host = "www.scala-lang.org"

  val response: Future[http.Response] = client(request)

  val f = response.onSuccess { rep: http.Response =>
    println("GET success: " + rep.contentString)
  } onFailure { err =>
    println("Error: " + err)
  }

  Await.result(f)
  Thread.sleep(1000)
}
