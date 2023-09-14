package edu.psgv.sweng861;

import java.util.Scanner;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.PrintWriter;
//import java.io.Writer;
//import java.io.PrintStream;
//import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 

/**
 * PrimeOrPerfect class implements a simple way to test positive integers 
 * as prime, perfect, or neither (ordinary).
 * prime - pos. integer that is divisible by only 1 and itself, e.g. 2, 3, 5, ...
 * perfect - pos. integer that is equal to the sum of its proper factors, e.g. 6 = 1+2+3
 * ordinary - neither prime nor perfect
 *
 */
public class PrimeOrPerfect {

	private PrimeOrPerfect() {
		// don't allow instances.
	}
	private static final Logger logger = LogManager.getLogger();
	protected enum NumberType {PRIME, PERFECT, ORDINARY};
	
/**
 * main() implements a Command Line Interface (console) with the user.
 * The user enters integers and the program characterizes them as prime, perfect, ordinary.
 * @param args - CLI arguments (not used)
 */
public static void main(String[] args) {
		logger.debug(">>main()");
		Scanner scanner = new Scanner(System.in);
		int number;
		NumberType result;
		while ((number = getNumber(scanner)) > 0) {
			result = analyzeNumber(number);
			if (result == NumberType.PRIME) {
				System.out.printf("%d is prime!%n", number);
			} else if (result == NumberType.PERFECT) {
				System.out.printf("%d is perfect!%n", number);
			} else if (result == NumberType.ORDINARY) {
				System.out.printf("%d is nothing special.%n", number);
			} else {
				logger.error("Cannot categorize number");
			}
		}
		// Best practice is to close the scanner.
		scanner.close();
		logger.debug("<<main()");
	}

/**
 * getNumber() gets a positive integer from the user	
 * @return a positive integer between 2 and 1000 inclusive.
 */
	protected static int getNumber(Scanner scanner) {
		logger.debug(">>getNumber()");
		boolean validInput = false;
		int number;
		System.out.println("Enter a positive integer (0 to quit):");
		do {
			String line = scanner.nextLine();
			logger.info("input number: {}", line);
			number = 0;
			try {
				number = Integer.parseInt(line);
			} catch (NumberFormatException e) {
				logger.info("input is invalid: {}", e.toString());
				System.err.println("Please enter a positive integer less than 1001.");
				continue;
			}
			if (number < 0 || number > 1000 ) {
				logger.info("input is out of range 1..1000.");
				System.err.println("Please enter a valid number between 1 and 1,000 inclusive.");
			} else {
				validInput = true;
			}
		} while (!validInput);
		logger.debug("<<getNumber()");
		return number;
	}

	protected static NumberType analyzeNumber(int num) {
		logger.debug(">>analyzeNumber()");
		int factorSum = 0;
		NumberType result = null;
		int maxFactor =  num / 2;
		for (int i=1; i <= maxFactor; i++) {
			if (num % i == 0) {
				logger.info("factor: {}", i);
				factorSum += i;
			}
		}
		if (factorSum == 1) {
			result = NumberType.PRIME;
		} else if (factorSum == num) {
			result = NumberType.PERFECT;
		} else {
			result = NumberType.ORDINARY;
		}
		logger.info("result: {}", result.toString());
		logger.debug("<<analyzeNumber()");
		return result;
	}
	
}
