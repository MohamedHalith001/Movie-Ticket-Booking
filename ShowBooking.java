package bookmymovie;

import com.mohamedhalith.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class ShowBooking {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		welcome();

		long mob, mobile;
		String pwd, defPwd, date, time, dOB;
		int choice;
		double tickets, cost, tax;
		boolean output, dateTime, senior;
		String[] seating = { "First Class", "Second Class", "Balcony" };
		int[] price = { 100, 150, 200 };
		LocalDate Date, current, doB;
		LocalTime Time, currTime;
		LocalDateTime now;

		mob = 9524896959L;
		defPwd = "pass1234";

		/*
		 * System.out.println("Mobile Number:    "); mobile = input.nextLong();
		 */
		mobile = 9524896959L;
		/*
		 * System.out.println("Password:         "); pwd = input.next();
		 */
		pwd = "pass1234";

		output = CredentialValidator.authenticate(mobile, defPwd, pwd, mob);
		if (output == false)
			System.exit(1);

		seatingDetails(seating, price);
		// choice = input.nextInt();
		choice = 3;
		/*
		 * System.out.println("Enter Booking Date (YYYY-MM-DD)"); date = input.next();
		 */
		date = "2021-04-18";
		/*
		 * System.out.println("Enter Show Time (HH:MM)"); time = input.next();
		 */
		time = "11:00";
		Date = LocalDate.parse(date);
		Time = LocalTime.parse(time);
		current = LocalDate.now();
		currTime = LocalTime.now();
		dateTime = DateTimeValidator.validateDateTime(Date, Time, current, currTime);
		if (dateTime == false) {
			System.out.println("Enter correct Date and Time");
			System.exit(1);
		}
		/*
		 * System.out.println("Enter the number of tickets"); tickets =
		 * input.nextDouble();
		 */
		tickets = 3;

		cost = Calculation.calculate(price, tickets, choice);
		tax = Calculation.calculateGST(cost);

		/*
		 * System.out.println("Enter Your Date Of Birth"); dOB = input.next();
		 */
		dOB = "2000-01-11";

		try {
			doB = LocalDate.parse(dOB);
			senior = DateTimeValidator.isSeniorCitizen(doB, current);
			if (senior)
				cost /= 2;
			cost = Math.ceil(cost);
			now = LocalDateTime.now();
			ShowBooking.printReceipt(cost, tax, tickets, choice, Date, Time, now, senior, mobile, seating);
		} catch (Exception e) {
			System.out.println("Please Enter correct format");
		}
		input.close();
	}

	/**
	 * Prints the welcome message to the customer
	 **/
	private static void welcome() {
		System.out.println("------------------------------------------------------------");
		System.out.println("\t\tWelcome to the Ram Cinemas");
		System.out.println("------------------------------------------------------------");
	}

	/**
	 * This methods lists all the available types of services provided by the
	 * service provider. Thus, helps the user to choose a service from the list.
	 * 
	 * @param cabs
	 * @param price
	 */
	private static void seatingDetails(String[] seats, int[] price) {
		System.out.println("---------AVAILABLE SEATINGS--------");
		for (int i = 0; i < seats.length; i++) {
			System.out.println((i + 1) + ". " + seats[i] + " -(Rs." + price[i] + ")");
		}
		// System.out.println("Enter your choice (1/2/3)");
	}

	/**
	 * Prints Receipt for the booking
	 * 
	 * @param cost
	 * @param tax
	 * @param tickets
	 * @param choice
	 * @param date
	 * @param time
	 * @param timestamp
	 * @param senior
	 * @param mobile
	 * @param cabs
	 */
	private static void printReceipt(double cost, double tax, double tickets, int choice, LocalDate date,
			LocalTime time, LocalDateTime timestamp, boolean senior, long mobile, String[] cabs) {
		System.out.println("------------------Receipt------------------");
		System.out.println("Contact No:                           " + mobile);
		System.out.println("No.of Tickets booked                  " + tickets);
		System.out.println("Booking Date                          " + date);
		System.out.println("Show Time                             " + time);
		System.out.println("Seating Choice                        " + cabs[choice - 1]);
		System.out.println("Cost for the show                     " + cost);
		System.out.println("GST                   7%              " + String.format("%.5f", tax));
		System.out.println("Total                                 " + Math.ceil(cost + tax));
		if (senior)
			System.out.println("\nSenior Citizen Discount      50%      " + Math.ceil(cost / 2));
		System.out.println("\n\nDate of Booking        " + timestamp);
	}
}
