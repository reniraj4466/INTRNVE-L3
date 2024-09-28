import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Custom Exception for insufficient balance
class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

// BankAccount class to hold account details
class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialDeposit;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Successfully deposited $" + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal.");
        } else {
            balance -= amount;
            System.out.println("Successfully withdrew $" + amount);
        }
    }
}

// Bank class to manage multiple accounts
class Bank {
    private Map<String, BankAccount> accounts = new HashMap<>();

    public void createAccount(String accountNumber, String accountHolder, double initialDeposit) {
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account with this number already exists.");
        } else {
            BankAccount account = new BankAccount(accountNumber, accountHolder, initialDeposit);
            accounts.put(accountNumber, account);
            System.out.println("Account created successfully.");
        }
    }

    public void depositToAccount(String accountNumber, double amount) {
        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            account.deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdrawFromAccount(String accountNumber, double amount) {
        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            try {
                account.withdraw(amount);
            } catch (InsufficientBalanceException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    public void checkBalance(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            System.out.println("Account Holder: " + account.getAccountHolder());
            System.out.println("Current Balance: $" + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }
}

// Main class to interact with the user
public class BankingApp {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        String option;

        while (true) {
            System.out.println("\n--- Banking System ---");
            System.out.println("1. Create an account");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. Check balance");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.print("Enter Account Number: ");
                    String accountNumber = scanner.nextLine();
                    System.out.print("Enter Account Holder Name: ");
                    String accountHolder = scanner.nextLine();
                    System.out.print("Enter Initial Deposit: ");
                    double initialDeposit = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    bank.createAccount(accountNumber, accountHolder, initialDeposit);
                    break;
                case "2":
                    System.out.print("Enter Account Number: ");
                    accountNumber = scanner.nextLine();
                    System.out.print("Enter Deposit Amount: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    bank.depositToAccount(accountNumber, depositAmount);
                    break;
                case "3":
                    System.out.print("Enter Account Number: ");
                    accountNumber = scanner.nextLine();
                    System.out.print("Enter Withdrawal Amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    bank.withdrawFromAccount(accountNumber, withdrawalAmount);
                    break;
                case "4":
                    System.out.print("Enter Account Number: ");
                    accountNumber = scanner.nextLine();
                    bank.checkBalance(accountNumber);
                    break;
                case "5":
                    System.out.println("Thank you for using the banking system.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}