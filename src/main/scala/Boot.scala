import akka.actor.ActorSystem


object Boot extends App {
  val system = ActorSystem("XmlSystem")
  val mainActor = system.actorOf(MainActor.props(), MainActor.name)
  //system.actorOf(ActorPing.props(pong), ActorPing.name)
}

