package com.revature.bank.prompts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.bank.account.Account;
import com.revature.bank.account.Transaction;
import com.revature.bank.dao.BankDao;
import com.revature.bank.dao.BankSerializer;
import com.revature.bank.user.User;

public class AddNewUserPrompt implements Prompt {

	private BankDao bd = new BankSerializer();
	private Scanner scan = new Scanner(System.in);
	private User user;
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private double intialAmount;
	private List<Transaction> transaction = new ArrayList<>();

	@Override
	public Prompt run() {
		// get all of the new users information and try to add them to the file
		System.out.println("Enter username");
		username = scan.nextLine();
		System.out.println("Enter first name");
		firstName = scan.nextLine();
		System.out.println("Enter last name");
		lastName = scan.nextLine();
		System.out.println("Enter password");
		password = scan.nextLine();
		System.out.println("Enter amount of initial deposit");
		intialAmount = scan.nextDouble();

		transaction.add(new Transaction("deposit", intialAmount, LocalDateTime.now()));

		user = new User(username, password, firstName, lastName,
				new Account(bd.getUsers().size() + 1, intialAmount, transaction), true);

		if (bd.addUser(user)) {
			System.out.println("\nWelcome " + user.getUsername() + ".");
			return new MenuPrompt();
		}

		username = scan.nextLine();

		return this;
	}

}