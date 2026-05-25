package mypackage;

public non-sealed class PaymentGateway extends Gateway {
	PaymentGateway()
	{
		System.out.println("Payment has been processed");
	}

	public void processPayment(double fieldAmount) {
		System.out.println("\nProcessing payment of: " + fieldAmount);
	}

}
