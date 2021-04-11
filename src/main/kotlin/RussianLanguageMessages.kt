import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class RussianLanguageMessages: LanguageMessagesInterface {
    override fun thanksMessage(): String {
        return "Спасибо, что остаетесь с JetBrains."
    }

    override fun failedPaymentGeneralMessage(data: FailedPaymentData): MutableList<String> {
        return mutableListOf(
            "К сожалению, не удалось снять средства с ${data.cardDetails ?: "вашей карты"} за вашу ",
            "${data.subscriptionPack.billingPeriod.name.toLowerCase()} подписку на ${
                data.items.joinToString(
                    ", "
                ) { it.productName }
            }.",
            "подписки",
            " как часть Subscription Pack ${
                data.subscriptionPack.subPackRef?.let { "#$it" }.orEmpty()
            } за следующий ",
            (when (data.subscriptionPack.billingPeriod) {
                BillingPeriod.MONTHLY -> "месяц"
                BillingPeriod.ANNUAL -> "год"
                else -> "период"
            } + ": ")
        )
    }

    override fun accessMessage(data: FailedPaymentData): MutableList<String> {
        return mutableListOf(
            "Чтобы обеспечить бесперебойный доступ к вашим подпискам, " +
                    "пожалуйста пройдите по ссылке и обновите ваши подписки ",
            "вручную",
            " до ${DateTimeFormatter.ofPattern("MMM dd, YYYY", Locale.US).format(data.paymentDeadline)}"
        )
    }

    override fun retryMessage(): String {
        return "Вы можете перепроверить и попробовать вашу текущую карту еще раз, попробовать воспользоваться другой картой, или выбрать другой метод оплаты."
    }

    override fun creditCardFailedPaymentReasons(): MutableList<String> {
        return mutableListOf(
            "Распространенные причины неудачных платежей по кредитной карте:",
            "- Срок действия карты истек, либо срок годности введен неверно;",
            "- Недостаточно средств или платежного лимита на карте; или",
            "- Карта не предназначена для международных / зарубежных транзакций, или банк-эмитент отклонил транзакцию."
        )
    }

    override fun paypalFailedPaymentReasons(): MutableList<String> {
        return mutableListOf(
            ("Убедитесь, что ваша учетная запись PayPal не закрыта и не удалена. " +
                    "Кредитная карта, подключенная к вашей учетной записи PayPal, должна быть активной. " +
                    "Распространенные причины неудачных платежей по карте:"),
            "- Карта не подтверждена в вашем аккаунте PayPal;",
            "- Реквизиты карты (Номер, Срок действия, CVC, Платежный адрес) являются неполными или были введены неверно;",
            "- Срок действия карты истек; или",
            "- Недостаточно средств или платежного лимита на карте."
        )
    }
}