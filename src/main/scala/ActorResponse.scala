import ActorResponse.Command
import Common.XmlClientConfig
import akka.actor.{Actor, ActorLogging, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import spray.json._

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}


object ActorResponse {
  val name = "ActorResponse"

  def props(xmlClientConfig: XmlClientConfig): Props = Props(new ActorResponse(xmlClientConfig))

  sealed trait Command

  object Command {
    case object Request extends Command
  }
}

class ActorResponse(xmlClientConfig: XmlClientConfig) extends Actor with MyProtocol with ActorLogging{
  override def receive: Receive = {
    case Command.Request =>
      log.info("ActorResponse: Получена команда на запрос к серверу. Запрос данных выполняется...")
      implicit val executionContext: ExecutionContextExecutor = context.dispatcher
      implicit val materializer: ActorMaterializer = ActorMaterializer()
      implicit val actorSystem = context.system
      val getHttpRequest = RequestBuilding.Get(xmlClientConfig.uri)
      val responseFuture: Future[HttpResponse] = Http().singleRequest(getHttpRequest)

      responseFuture.onComplete {
        case Success(httpResponse) =>
          Unmarshal(httpResponse.entity).to[RestOperationResult].onComplete {
          case Success(value) =>
            val json = value.toJson

            val result = json.convertTo[RestOperationResult]

            context.parent ! MainActor.Command.Response(result)

          case Failure(exception) => throw exception
            log.error(s"ActorResponse: Ошибка, работа завершается. ${exception.getMessage}")
            context.system.terminate()
        }

        case Failure(exception) => throw exception
          context.system.terminate()

        case _ =>
          println("что-то пошло не так в акторе actorResponse")
      }
  }
}
