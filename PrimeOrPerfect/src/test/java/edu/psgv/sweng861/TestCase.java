package edu.psgv.sweng861;

import static org.junit.jupiter.api.Assertions.*;


//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Java imports
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.Scanner;
//Mockito
import static org.mockito.Mockito.*;

import edu.psgv.sweng861.PrimeOrPerfect.NumberType;


class TestCase {

	@Test
	// test the analyzeNumber method with a prime number.
	public void analyzePrimeNumber() {
	assertEquals(NumberType.PRIME, PrimeOrPerfect.analyzeNumber(499));
	}
	@Test
	// test the analyzeNumber method with perfect numbers.
	public void analyzePerfectNumbers() {
	assertEquals(NumberType.PERFECT, PrimeOrPerfect.analyzeNumber( 6));
	assertEquals(NumberType.PERFECT, PrimeOrPerfect.analyzeNumber( 28));
	assertEquals(NumberType.PERFECT, PrimeOrPerfect.analyzeNumber(496));
	}
	@Test
	// test the analyzeNumber method with ordinary numbers.
	public void analyzeOrdinaryNumbers() {
	assertEquals(NumberType.ORDINARY, PrimeOrPerfect.analyzeNumber( 4));
	assertEquals(NumberType.ORDINARY, PrimeOrPerfect.analyzeNumber( 27));
	assertEquals(NumberType.ORDINARY, PrimeOrPerfect.analyzeNumber(498));
	}
	
	@Test
	// test getNumber with a valid input.
	public void getNumberValidInput() {
		// When the getNumber calls Scanner.nextLine() return "496" via
		// the mocked Scanner.
		// declare a mock Scanner object.
		Scanner mockScanner = mock(Scanner.class);
		when (mockScanner.nextLine()).thenReturn("496");
		int val = PrimeOrPerfect.getNumber(mockScanner);
		assertEquals(496, val);
	}
	
	@Test
	// test getNumber with invalid inputs followed by a valid input.
	public void getNumberInvalidInputs() {
	// getNumber loops until a valid number is entered. So let's
	// test that behavior.
	// When the getNumber calls Scanner.nextLine() return "abc" which
	// is rejected w/ System.err message.
	// Next Scanner.nextLine() returns "1001" which is rejected since
	// it is out of range w/ System.err message.
	// Finally, Scanner.nextLine() returns "496" which it accepts.
	// declare a mock Scanner object.
		Scanner mockScanner = mock(Scanner.class);
		when (mockScanner.nextLine()).thenReturn("abc","1001","-1","496");
		int val = PrimeOrPerfect.getNumber(mockScanner);
		assertEquals(496, val);
	}
	
	//@Test
	// normalizeExpectedOutput - generate the eol character at run-time.
	// then there is no need to hard-code "\r\n" or "\n" for eol
	// and string comparisons are portable between Windows, macOS, Linux.
	
	public String normalizeExpectedOutput(String expectedOutput) {
			String normExpectedOutput;
			String [] outputs = expectedOutput.split("\n");
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			for (String str: outputs) {
			pw.println(str);
		}
		pw.close();
		normExpectedOutput = sw.toString();
		return normExpectedOutput;
	}
	@Test
	// test the main method by using Java constructs (no mocking)
	// to control System.in and System.out. The alternative is
	// to mock the static method getNumber() but mockito cannot mock
	// static methods (PowerMock needed here).
	//
	// Note that this technique is fragile as any change to the
	// output format will break the test case. Also printf method
	// and println methods produce different eol sequences!
	public void mainTest() {
		// simulated input
		String input = "496\n497\n499\n0\n";
		// save current System.in and System.out
		InputStream sysIn = System.in;
		PrintStream sysOut = System.out;
		InputStream myIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(myIn);
		final String unNormalizedExpectedOutput =
		"Enter a positive integer (0 to quit):\n" +
		"496 is perfect!\n" +
		"Enter a positive integer (0 to quit):\n" +
		"497 is nothing special.\n" +
		"Enter a positive integer (0 to quit):\n" +
		"499 is prime!\n" +
		"Enter a positive integer (0 to quit):\n" ;
		final String expectedOutput =
		normalizeExpectedOutput(unNormalizedExpectedOutput);
		// This is the technique to capture the textual output
		// from System.out.print calls.
		final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(myOut));;
		// test
		PrimeOrPerfect.main(null);
		// check results
		final String printResult = myOut.toString();
		assertEquals(expectedOutput, printResult);
		// return System variables to their previous values.
		System.setOut(sysOut);
		System.setIn(sysIn);
	}

	
}
