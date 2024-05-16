package Common

import com.typesafe.config.{Config, ConfigFactory}


case class XmlClientConfig(
                            uri: String
                          )

object ConfigParser {
  private val conf: Config = ConfigFactory.load()

  def parse(): XmlClientConfig = parseXmlClientConfig(conf)


  private def parseXmlClientConfig(config: Config): XmlClientConfig = {
    val uri = config.getString("uri")
    XmlClientConfig(uri)
  }
}