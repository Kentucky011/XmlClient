import Common.ConfigParser
import akka.actor.ActorSystem


object Boot extends App {
  //println(args.toList)
  val system = ActorSystem("XmlSystem")
  val config = ConfigParser.parse()
  system.actorOf(MainActor.props(xmlClientConfig = config), MainActor.name)
}
