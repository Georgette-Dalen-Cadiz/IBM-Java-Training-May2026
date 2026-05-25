package mypackage;

class CreditCardPayment extends Payment implements Verifiable {
	private String cardNumber;
	
    // Constructor
    public CreditCardPayment(double fieldAmount, String cardNumber) {
        super(fieldAmount);
        this.cardNumber = cardNumber;
    }
    
    public CreditCardPayment(double fieldAmount) {
        super(fieldAmount);
    }

    // Implement interface method
    @Override
    public boolean verifyPayment() {
    	System.out.print("Processing credit card payment...");
        if(cardNumber.length() == 16)
        {
        	return true;
        } else {
        	return false;
        }
       
    }
    
    
    // Getter 
    String getCardNumber()
    {
    	return cardNumber;
    }
}