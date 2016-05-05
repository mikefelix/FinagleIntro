import com.twitter.finagle.{Service, _}
import com.twitter.util.{Await, Future}

/**
  * Server
  * User: michael.felix
  * Date: 4/30/16
  */
object Server extends App {
   val service = new Service[http.Request, http.Response] {
     def apply(req: http.Request): Future[http.Response] = {
       val r = http.Response(req.version, http.Status.Ok)
       r.contentString = "Hey, listen!"
       Future.value(r)
     }
   }

   val server = Http.serve(":8080", service)
   Await.ready(server)
 }