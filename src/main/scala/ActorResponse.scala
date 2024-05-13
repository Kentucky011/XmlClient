import akka.actor.{Actor, Props}

class ActorResponse() extends Actor {
  override def receive: Receive = ???

}

object ActorResponse {
  val name = "ActorResponse"

  def props(): Props = Props(new ActorResponse())

  sealed trait Command

  object Command {
    case object Test extends Command
    case object Test2 extends Command

  }
}