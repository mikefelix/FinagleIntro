import java.net.InetSocketAddress

import com.twitter.finagle.builder.ServerBuilder
import com.twitter.util.Await
//import com.twitter.finagle.exp.Mysql
//import com.twitter.finagle.exp.mysql._

object HomeInfoServer {
  val errorFilter = new HandleErrorServerFilter

  def main(args: Array[String]) {
    val server = ServerBuilder()
      .codec(com.twitter.finagle.http.Http())
      .bindTo(new InetSocketAddress(7690))
      .name("HomeInfoServer")
      .build(new HomeInfoService())

    println("Starting...")
    Await.ready(server)
  }
}
