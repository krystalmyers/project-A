package com.revature.bank.prompts;

import java.util.Scanner;

import com.revature.bank.dao.BankDao;
import com.revature.bank.dao.BankSerializer;
import com.revature.bank.user.User;

public class AdminPrompt implements Prompt {

	private BankDao bd = new BankSerializer();
	private Scanner scan = new Scanner(System.in);

	@Override
	public Prompt run() {
		// TODO Auto-generated method stub
		User user = MenuPrompt.getCurrentUser(bd);

		System.out.println("\nPress 1 to view all transactions.");
		System.out.println("Press 2 to Logout.");
		String input = scan.nextLine();

		switch (input) {
		case "1":
			bd.viewAllTransactions(user);
			break;
		case "2":
			bd.userLogout(user);
			return new LoginPrompt();

		default:
			break;
		}

		return this;
	}

}