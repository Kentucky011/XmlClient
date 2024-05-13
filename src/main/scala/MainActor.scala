import ActorResponse.Command
import MainActor.Event
import akka.actor._
import akka.util.Timeout

import scala.concurrent.duration.DurationInt

class MainActor() extends Actor {
  implicit val timeout = Timeout(5.seconds)
  implicit val ec = context.dispatcher

  val actorResponse: ActorRef = context.actorOf(ActorResponse.props(), ActorResponse.name)
  val actorSave: ActorRef = context.actorOf(ActorSave.props(),ActorSave.name)

  override def preStart(): Unit = {
    self ! Event.Start
  }

  override def receive: Receive = {
    case Event.Start =>
      println("Начинаю работу: Отправляю команду ActorResponse на создание запроса к хосту")
      //реализация отправления команды ActorResponse

    case Command.Test => //сообщение от ActorResponse
      println("Получено сообщение от ActorResponse: Запрос на хост завершен, данные получены \n Сохранение данных: Отправляю команду ActorSave для записи данных на диск")
    //реализация отправления команды ActorSave(сообщение актору ActorSave)

    case Command.Test2 => //сообщение от ActorSave
      println("Сохранение данных: Сохранение данных завершено. Окончание работы.")
      //реализация завершения работы системы акторов

    case _ => "Что-то пошло не по плану"
  }
}

object MainActor {
  val name = "MainActor"

  def props(): Props = Props(new MainActor())


  sealed trait Event

  object Event {
    case object Start extends Event
  }

  sealed trait Command

  object Command {
    case object Test extends Command
  }
}