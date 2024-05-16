case class Rest(
                 restId: String,
                 data: List[Data]
               )

case class Data(
                 id: String,
                 values: List[String],
                 validate: Option[Validate]
               )

case class RestOperationResult(
                                status: String,
                                data: Option[List[Rest]],
                                error: Option[String]
                              )

case class Validate(
                     idValid: String,
                     isValid: Option[Boolean]
                   )
