import Common.XmlClientConfig
import MainActor.Command
import MainActor.Event
import akka.actor._
import akka.util.Timeout

import scala.concurrent.duration.DurationInt

class MainActor(xmlClientConfig: XmlClientConfig) extends Actor {
  implicit val timeout: Timeout = Timeout(5.seconds)

  private val actorResponse: ActorRef = context.actorOf(ActorResponse.props(xmlClientConfig), ActorResponse.name)
  private val actorSave: ActorRef = context.actorOf(ActorSave.props(), ActorSave.name)

  override def preStart(): Unit = {
    self ! Event.Start
  }


  override def receive: Receive = {
    case Event.Start =>
      println("MainActor: Отправляю команду ActorResponse на создание запроса к хосту")
      actorResponse ! ActorResponse.Command.Request

    case Command.Response(response) =>
      println("MainActor: Получено сообщение от ActorResponse: Запрос на хост завершен, данные получены.")
      println("MainActor: Отправляю команду ActorSave для записи данных на диск")
    actorSave ! ActorSave.Command.Save(response)

    case Command.End => //сообщение от ActorSave
      println("ActorSave: Сохранение данных завершено. Окончание работы.")

      context.system.terminate()

    case _ =>
      println("что-то пошло не так в акторе MainActor")
  }
}

object MainActor {
  val name = "MainActor"

  def props(xmlClientConfig: XmlClientConfig): Props = Props(new MainActor(xmlClientConfig))

  sealed trait Event

  object Event {
    case object Start extends Event
  }

  sealed trait Command

  object Command {
    case object End extends Command
    case class Response(response: RestOperationResult) extends Command
  }
}