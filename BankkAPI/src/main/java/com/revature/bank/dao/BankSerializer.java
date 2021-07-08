package com.revature.bank.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.bank.account.Transaction;
import com.revature.bank.user.User;

public class BankSerializer implements BankDao {
	private final String FILE_LOCATION = "src/main/resources/bank.txt";
	private Scanner scan = new Scanner(System.in);
	private List<User> users = getUsers();
	private List<Transaction> transaction;
	private List<Transaction> transactions;
	private double amount;

	// this will take in a user and try to withdrawal the amount of money that
	// is requested
	// if the user doesn't have enough money the user will be notified and sent
	// back to the
	// menu prompt.
	@Override
	public boolean witdrawal(User user) {

		System.out.println("Please enter the amount you would like to withdrawl.");
		amount = scan.nextDouble();

		for (User u : users) {
			if (u.getBankAccount().getAccountNumber() == user.getBankAccount().getAccountNumber()) {
				if (u.getBankAccount().getAccountBalance() >= amount) {
					transaction = getTransactions(user);
					transaction.add(new Transaction("withdrawl", amount, LocalDateTime.now()));
					u.getBankAccount().setAccountBalance(u.getBankAccount().getAccountBalance() - amount);
					u.getBankAccount().setTransactions(transaction);
				} else {
					System.out.println("Not enough money to withdrawl that amount.");
					return false;
				}
			}
		}

		return outputToFile(users);
	}

	// This will ask the user how much money they are adding to their account
	// and then
	// add that amount to their account balance
	@Override
	public boolean deposit(User user) {
		System.out.print("Please enter the amount you would like to deposit: ");
		amount = scan.nextDouble();

		for (User u : users) {
			if (u.getBankAccount().getAccountNumber() == user.getBankAccount().getAccountNumber()) {
				transaction = getTransactions(user);
				transaction.add(new Transaction("deposit", amount, LocalDateTime.now()));
				u.getBankAccount().setAccountBalance(u.getBankAccount().getAccountBalance() + amount);
				u.getBankAccount().setTransactions(transaction);
			}
		}

		return outputToFile(users);
	}

	// this will print out the current users balance.
	@Override
	public void viewBalance(User user) {
		// TODO Auto-generated method stub
		for (User u : users) {
			if (u.getBankAccount().getAccountNumber() == user.getBankAccount().getAccountNumber()) {
				System.out.printf("\nAccount balance: $%,.2f\n", u.getBankAccount().getAccountBalance());
			}
		}
	}

	// this will return a list of all the current users transactions
	@Override
	public List<Transaction> getTransactions(User user) {
		// TODO Auto-generated method stub
		for (User u : users) {
			if (u.getBankAccount().getAccountNumber() == user.getBankAccount().getAccountNumber()) {
				transactions = u.getBankAccount().getTransactions();
			}
		}

		return transactions;
	}

	// This will set the user login status to true
	@Override
	public boolean userLoggedIn(User user) {
		for (User u : users) {
			if (u.getBankAccount().getAccountNumber() == user.getBankAccount().getAccountNumber())
				u.setLoggedIn(true);
		}

		return outputToFile(users);

	}

	// this will add a new user to the if the account is not the same and the
	// username is
	// not already being used
	@Override
	public boolean addUser(User user) {
		if (users == null) {
			users = new ArrayList<>();
		}

		for (User u : users) {
			if (u.getBankAccount().getAccountNumber() == user.getBankAccount().getAccountNumber()) {
				// System.out.println("Something went terribly wrong please try
				// again.\n");
				return false;
			} else if (u.getUsername().equals(user.getUsername())) {
				System.out.println("Username is already being used.\n" + "please try again.\n");
				return false;
			}
		}

		users.add(user);

		return outputToFile(users);

	}

	// returns a list of all the users
	@Override
	public List<User> getUsers() {
		try (ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(FILE_LOCATION))) {
			return (List<User>) inStream.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// this will change the users login status to false
	@Override
	public boolean userLogout(User user) {
		// TODO Auto-generated method stub
		for (User u : users) {
			if (u.getBankAccount().getAccountNumber() == user.getBankAccount().getAccountNumber())
				u.setLoggedIn(false);
		}

		return outputToFile(users);
	}

	// this will display the transactions for all of the users
	// only the admin can do this
	@Override
	public void viewAllTransactions(User user) {
		// TODO Auto-generated method stub
		if (users.size() > 1) {
			for (User u : users) {
				if (u.getBankAccount().getAccountNumber() != 0) {
					transactions = u.getBankAccount().getTransactions();
					System.out.printf("User: %s", u.getUsername());
					for (Transaction transaction : transactions) {
						System.out.printf(
								"\nA %s for the amount of $%,.2f was made on %tB %<te, %<tY at %<tH:%<tM %<Tp",
								transaction.getType(), transaction.getAmount(), transaction.getDate());

					}
				}
				System.out.println("\n");
			}
		} else
			System.out.println("No transactions currently have been made at this time.");

	}

	public boolean outputToFile(List<User> users) {
		try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(FILE_LOCATION))) {
			outStream.writeObject(users);
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return false;
	}

}