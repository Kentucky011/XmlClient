import ActorSave.Command
import akka.actor.{Actor, Props}

import java.io.PrintWriter

object ActorSave {

  val name = "ActorSave"

  def props(): Props = Props(new ActorSave())


  sealed trait Command

  object Command {
    case class Save(response: RestOperationResult) extends Command
  }
}

class ActorSave extends Actor with MyProtocol {

  override def receive: Receive = {

    case Command.Save(response) =>
      println("ActorSave: Получена команда на запись файла на диск.")

      val xml =
        <root>
          <status>
            {response.status}
          </status>
          <data>
            {response.data.getOrElse(List.empty[Rest]).map { rest =>
            <rest>
              <restId>
                {rest.restId}
              </restId>
              <data>
                {rest.data.map { item =>
                <id>
                  {item.id}
                </id>
                  <values>
                    {item.values.map { values =>
                    <value>
                      {values}
                    </value>
                  }}
                  </values>
                  <validate>
                    <idValid>
                      {item.validate.map(_.idValid).getOrElse("")}
                    </idValid>{if (item.validate.flatMap(_.isValid).nonEmpty) {
                    <isValid>
                      {item.validate.flatMap(_.isValid).getOrElse(false)}
                    </isValid>
                  }}
                  </validate>
              }}
              </data>
            </rest>
          }}
          </data>{if (response.error.nonEmpty) {
          <error>
            {response.error}
          </error>
        }}
        </root>

      val xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" + xml

      val pw = new PrintWriter("D:\\WorkHard\\TestFile\\res.xml")
      pw.write(xmlStr)
      pw.close()
      //println(s"$xmlStr")

      //val readFile = Source.fromFile("D:\\WorkHard\\TestFile\\res.xml")
      //println(readFile.toString)

      context.parent ! MainActor.Command.End

    case _ =>
      println("что-то пошло не так в акторе actorSave")

  }
}

