import com.twitter.finagle.exp.Mysql
import com.twitter.finagle.exp.mysql._
import com.twitter.util.Future

/**
  * MysqlInfoRequester
  * User: michael.felix
  * Date: 5/3/16
  */
class MysqlInfoRequester extends InfoRequester {
  val (username, password, db, host) = ("admin", "pass", "database", "myhost")

    val mySqlClient = Mysql.client
          .withCredentials(username, password)
          .withDatabase(db)
          .newRichClient(host)

    def selectQuery(client: Client): Future[Seq[_]] = {
      val query = "SELECT * FROM `finagle-mysql-example` WHERE `date` BETWEEN '2009-06-01' AND '2009-08-31'"

      client.select(query) { row =>
        val StringValue(event) = row("event").get
        val DateValue(date) = row("date").get
        val StringValue(name) = row("name").get
        val time = row("time").map {
          case FloatValue(f) => f
          case _ => 0.0F
        }.get

        (name, time, date)
      }
    }
  override def requestInfo = {
    val future = selectQuery(mySqlClient)
    future map {
      _.head.asInstanceOf[String]
    }
  }
}
