import java.time.LocalDate

interface LanguageMessagesInterface {
    fun thanksMessage(): String
    fun failedPaymentGeneralMessage(data: FailedPaymentData): MutableList<String>
    fun accessMessage(data: FailedPaymentData): MutableList<String>
    fun retryMessage(): String
    fun creditCardFailedPaymentReasons(): MutableList<String>
    fun paypalFailedPaymentReasons(): MutableList<String>
}
