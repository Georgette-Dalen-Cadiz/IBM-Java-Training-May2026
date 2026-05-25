package mypackage;

import java.time.LocalDateTime;

public record PaymentDetails(
		String transactionId,
		double amount,
		String paymentMethod,
		LocalDateTime timestamp
) {}
