import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class SpanishLanguageMessages: LanguageMessagesInterface {
    override fun thanksMessage(): String {
        return "Gracias por quedarse con JetBrains."
    }

    override fun failedPaymentGeneralMessage(data: FailedPaymentData): MutableList<String> {
        return mutableListOf(
            "Desafortunadamente, no pudimos cargar  ${data.cardDetails ?: "su tarjeta"} para tu ",
            "${data.subscriptionPack.billingPeriod.name.toLowerCase()} suscripción a ${
                data.items.joinToString(
                    ", "
                ) { it.productName }
            }.",
            "suscripción",
            " como parte de Subscription Pack ${
                data.subscriptionPack.subPackRef?.let { "#$it" }.orEmpty()
            } para el siguiente ",
            (when (data.subscriptionPack.billingPeriod) {
                BillingPeriod.MONTHLY -> "mes"
                BillingPeriod.ANNUAL -> "año"
                else -> "período"
            } + ": "))
    }

    override fun accessMessage(data: FailedPaymentData): MutableList<String> {
        return mutableListOf(
            "Para garantizar un acceso ininterrumpido a su ${data.subscriptionPack.pluralize()}, " +
                    "por favor siga el enlace y renueve su ${data.subscriptionPack.pluralize()} ",
            "a mano",
            " hasta ${DateTimeFormatter.ofPattern("MMM dd, YYYY", Locale.US).format(data.paymentDeadline)}"
        )
    }

    override fun retryMessage(): String {
        return "Puede verificar y probar su tarjeta de pago existente nuevamente, usar otra tarjeta o elegir un método de pago diferente."
    }

    override fun creditCardFailedPaymentReasons(): MutableList<String> {
        return mutableListOf(
            "Las razones comunes de los pagos fallidos con tarjeta de crédito incluyen:",
            "- La tarjeta está vencida o la fecha de vencimiento se ingresó incorrectamente;",
            "- Fondos insuficientes o límite de pago en la tarjeta; o",
            "- La tarjeta no está configurada para transacciones internacionales / en el extranjero, o el banco emisor ha rechazado la transacción."
        )
    }

    override fun paypalFailedPaymentReasons(): MutableList<String> {
        return mutableListOf(
            ("Asegúrese de que su cuenta de PayPal no esté cerrada ni eliminada." +
                    "La tarjeta de crédito conectada a su cuenta PayPal debe estar activa." +
                    "Los motivos habituales de los pagos con tarjeta fallidos incluyen:"),
        "- La tarjeta no está confirmada en su cuenta PayPal;",
        "- Los detalles de la tarjeta (número, fecha de vencimiento, CVC, dirección de facturación) están incompletos o se ingresaron incorrectamente;",
        "- La tarjeta está caducada; o",
        "- Fondos insuficientes o límite de pago en la tarjeta."


        )
    }

    private fun SubscriptionPack.pluralize(title: String = "suscripción"): String {
        return title.simplyPluralize(this.totalLicenses)
    }

    private fun String.simplyPluralize(amount: Int): String {
        return when (amount) {
            1 -> this
            else -> "${this}es"
        }
    }

}