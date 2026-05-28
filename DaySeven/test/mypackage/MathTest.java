package mypackage;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.junit.jupiter.api.Test;

class MathTest {

	@Test
	void shouldReturnCorrectAdd() {
	    // Act
	    float result = Math.add(3, 4);

	    // Assert
	    assertEquals(7, result);
	}
	
	@Test
	void shouldReturnCorrectSubtract() {


	    // Act
	    float result = Math.subtract(10, 5);

	    // Assert
	    assertEquals(5, result);
	}
	
	@Test
	void shouldReturnCorrectMultiply() {
	    
	    

	    // Act
	    float result = Math.multiply(10, 5);

	    // Assert
	    assertEquals(50, result);
	}
	
	@Test
	void shouldReturnCorrectDivide() {
	    
	    

	    // Act
	    float result = Math.divide(10, 5);

	    // Assert
	    assertEquals(2, result);
	}
	
	@Test
	void divideThrowsOnZero() {
	    
	    

	    // Act + Assert
	    ArithmeticException ex = assertThrows(
	        ArithmeticException.class,
	        () -> Math.divide(20, 0)
	    );

	    assertEquals("Cannot Divide by 0", ex.getMessage());
	}
	
	
	@ParameterizedTest
	@CsvSource({
	    "2,3,5",
	    "3,4,7",
	    "-1,1,0",
	    "0,0,0",
	    "-5,-5,-10"
	})
	void shouldAddNumbers(float a, float b, float expected) {
	    
	    assertEquals(expected, Math.add(a, b), 0.0001);
	}
	
	@ParameterizedTest
	@CsvSource({
	    "10,5,5",
	    "5,10,-5",
	    "-5,-5,0",
	    "0,5,-5",
	    "100,50,50"
	})
	void shouldSubtractNumbers(float a, float b, float expected) {
	    
	    assertEquals(expected, Math.subtract(a, b), 0.0001);
	}
	
	@ParameterizedTest
	@CsvSource({
	    "2,3,6",
	    "3,4,12",
	    "-1,5,-5",
	    "0,100,0",
	    "-5,-5,25"
	})
	void shouldMultiplyNumbers(float a, float b, float expected) {
	    
	    assertEquals(expected, Math.multiply(a, b), 0.0001);
	}
	
	@ParameterizedTest
	@CsvSource({
	    "10,5,2",
	    "9,3,3",
	    "-10,5,-2",
	    "5,2,2.5",
	    "0,5,0"
	})
	void shouldDivideNumbers(float a, float b, float expected) {
	    
	    assertEquals(expected, Math.divide(a, b), 0.0001);
	}
	
}
