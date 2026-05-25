package mypackage;

public class PayPalPayment extends Payment implements Verifiable {
	private String userEmail;
	
	PayPalPayment(double fieldAmount, String userEmail) {
		super(fieldAmount);
		this.userEmail = userEmail;
	}

	
	// Implement interface method
    @Override
    public boolean verifyPayment() {
    	System.out.print("Processing PayPal payment...");
    	if (userEmail.contains("@"))
    	{
    		return true;
    	} else {
    		return false;
    	}
       
    }
	
    // Getter 
    String getUserEmail()
    {
    	return userEmail;
    }
}
