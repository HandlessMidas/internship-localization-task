import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class EnglishLanguageMessages: LanguageMessagesInterface {
    override fun thanksMessage(): String {
        return "Thank you for staying with JetBrains."
    }

    override fun failedPaymentGeneralMessage(data: FailedPaymentData): MutableList<String> {
        return mutableListOf(
            "Unfortunately, we were not able to charge ${data.cardDetails ?: "your card"} for your ",
            "${data.subscriptionPack.billingPeriod.name.toLowerCase()} subscription to ${
                data.items.joinToString(
                    ", "
                ) { it.productName }
            }.",
            "subscription".simplyPluralize(data.items.sumBy { it.quantity }),
            " as part of Subscription Pack ${
                data.subscriptionPack.subPackRef?.let { "#$it" }.orEmpty()
            } for the next ",
            (when (data.subscriptionPack.billingPeriod) {
                BillingPeriod.MONTHLY -> "month"
                BillingPeriod.ANNUAL -> "year"
                else -> "period"
            } + ": ")
        )
    }

    override fun accessMessage(data: FailedPaymentData): MutableList<String> {
        return mutableListOf(
            "To ensure uninterrupted access to your ${data.subscriptionPack.pluralize()}, " +
                    "please follow the link and renew your ${data.subscriptionPack.pluralize()} ",
            "manually",
            " till ${DateTimeFormatter.ofPattern("MMM dd, YYYY", Locale.US).format(data.paymentDeadline)}"
        )
    }

    override fun retryMessage(): String {
        return "You can double-check and try your existing payment card again, use another card, or choose a different payment method."
    }

    override fun creditCardFailedPaymentReasons(): MutableList<String> {
        return mutableListOf(
            "Common reasons for failed credit card payments include:",
            "- The card is expired, or the expiration date was entered incorrectly;",
            "- Insufficient funds or payment limit on the card; or",
            "- The card is not set up for international/overseas transactions, or the issuing bank has rejected the transaction."
        )
    }

    override fun paypalFailedPaymentReasons(): MutableList<String> {
        return mutableListOf(
            ("Please make sure that your PayPal account is not closed or deleted. " +
                "The credit card connected to your PayPal account should be active. " +
                "Common reasons for failed card payments include:"),
            "- The card is not confirmed in your PayPal account;",
            "- The card details (Number, Expiration date, CVC, Billing address) are incomplete or were entered incorrectly;",
            "- The card is expired; or",
            "- Insufficient funds or payment limit on the card."
        )
    }

    private fun SubscriptionPack.pluralize(title: String = "subscription"): String {
        return title.simplyPluralize(this.totalLicenses)
    }

    private fun String.simplyPluralize(amount: Int): String {
        return when (amount) {
            1 -> this
            else -> "${this}s"
        }
    }
}