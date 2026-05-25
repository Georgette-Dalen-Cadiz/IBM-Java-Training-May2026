package mypackage;

abstract sealed class Gateway permits PaymentGateway {
	public abstract void processPayment(double amount);
}
