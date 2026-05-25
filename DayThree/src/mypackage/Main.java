package mypackage;

import java.time.LocalDateTime;
import java.util.*;


public class Main {

	public static void main(String[] args) {
		List<Payment> payments = new ArrayList<>();
		List<PaymentDetails> successfulPayments = new ArrayList<>();
		
		// VERFIED
		payments.add(new CreditCardPayment(100.0, "1111222233334444"));
        payments.add(new PayPalPayment(123.0, "user@email.com"));
        payments.add(new BankTransferPayment(676.0, "1122334455"));

		// UNVERFIED
        payments.add(new CreditCardPayment(1000.0, "333"));
        payments.add(new PayPalPayment(345.0, "user12345"));
        payments.add(new BankTransferPayment(100.0, "123"));
        
        
        PaymentType online = new OnlinePaymentType();
        PaymentType offline = new OfflinePaymentType();
        
        PaymentGateway gateway = new PaymentGateway(); 
        
        for (Payment pay : payments)
        {
        	  System.out.println("\nChecking payment: " + pay.getFieldAmount());

              if (((Verifiable) pay).verifyPayment()) {

                  // Process payment
            	  gateway.processPayment(pay.getFieldAmount());
                  System.out.println("Payment has been processed");

                  // Create record
                  PaymentDetails details = new PaymentDetails(
                          "id-" + System.currentTimeMillis(),
                          pay.getFieldAmount(),
                          pay.getClass().getSimpleName(),
                          LocalDateTime.now()
                  );

                  successfulPayments.add(details);

              } else {
                  System.out.println("\nVerification failed.");
              }
        }
        
        System.out.println();
        for (PaymentDetails paydets : successfulPayments)
        {
        	System.out.println((paydets) + "\n");
        }
        
        
	}

}
