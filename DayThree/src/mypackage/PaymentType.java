package mypackage;

public sealed abstract class PaymentType permits OnlinePaymentType, OfflinePaymentType  {
	
}
