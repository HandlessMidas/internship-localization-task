import kotlinx.html.*
import java.time.format.DateTimeFormatter
import java.util.*

class FailedPaymentEmail(
    private val data: FailedPaymentData
) {

    // TODO: accept the user's language and build a localized version of the email
    fun buildContent(body: HTML, language: LanguageMessagesInterface) = with(body) {
        body {
            p {
                +language.thanksMessage()
            }
            val failedPaymentGeneralMessage = language.failedPaymentGeneralMessage(data)
            p {
                +failedPaymentGeneralMessage[0]
                if (data.customerType == CustomerType.PERSONAL) {
                    +failedPaymentGeneralMessage[1]
                } else {
                    +failedPaymentGeneralMessage[2]
                    +failedPaymentGeneralMessage[3]
                    +failedPaymentGeneralMessage[4]
                    br()
                    data.items.forEach {
                        +"- ${it.quantity} x ${it.description}";br()
                    }
                }
            }

            if (data.cardProvider == CardProvider.PAY_PAL)
                paypalFailedPaymentReasons(language.paypalFailedPaymentReasons())
            else
                creditCardFailedPaymentReasons(language.creditCardFailedPaymentReasons())
            val accessMessage = language.accessMessage(data)
            p {
                +accessMessage[0]
                a(href = "https://foo.bar/ex") {
                    +accessMessage[1]
                }
                +accessMessage[2]
            }
            p {
                +language.retryMessage()
            }
        }
    }
}

private fun FlowContent.creditCardFailedPaymentReasons(reasons: MutableList<String>) {
    p {
        +reasons[0]; br()
        +reasons[1]; br()
        +reasons[2]; br()
        +reasons[3]; br()
    }
}

private fun FlowContent.paypalFailedPaymentReasons(reasons: MutableList<String>) {
    p {
        +reasons[0]; br()
        +reasons[1]; br()
        +reasons[2]; br()
        +reasons[3]; br()
        +reasons[4]
    }
}


