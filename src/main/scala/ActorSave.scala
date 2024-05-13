import akka.actor.{Actor, Props}

class ActorSave() extends Actor {

  override def receive: Receive = ???

}

object ActorSave {

  val name = "ActorSave"

  def props(): Props = Props(new ActorSave())


  sealed trait Command {

    case object Test extends Command

    case object Test2 extends Command

  }

}