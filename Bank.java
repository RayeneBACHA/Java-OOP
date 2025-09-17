import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Bank {
    private Client[] clients = new Client[100];
    private Account[] accounts = new Account[100];
    int clientCount = 0;
    int accountCount = 0;
    private Scanner sc = new Scanner(System.in);

    // Load & Save
    public void loadClients() {
        try (ObjectInputStream inp  = new ObjectInputStream(new FileInputStream("clients.dat"))){
            while (true) {
                Client c = (Client) inp.readObject();
                clients[clientCount++] = c;
            }
        } catch (IOException | ClassNotFoundException e) {
        }
        // ignore
    }
    public void loadAccounts() {
        try (ObjectInputStream inp  = new ObjectInputStream(new FileInputStream("accounts.dat"))){
            while (true) {
                Account acc = (Account) inp.readObject();
                accounts[accountCount++] = acc;
            }
        } catch (IOException | ClassNotFoundException e) {
        }
        // ignore
    }
    public void saveClients() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("clients.dat"))){
            for (int i = 0; i < clientCount; i++) {
                out.writeObject(clients[i]);
            }
            System.out.println("Clients saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveAccounts() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("accounts.dat"))){
            for (int i = 0; i < accountCount; i++) {
                out.writeObject(accounts[i]);
            }
            System.out.println("Accounts saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Safe input helpers
    private int readInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            if (sc.hasNextInt()) {
                int value = sc.nextInt();
                sc.nextLine();
                return value;
            } else {
                sc.nextLine();
                System.out.println("Invalid input. Please enter a number!");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.println(prompt);
            if (sc.hasNextDouble()) {
                double value = sc.nextDouble();
                sc.nextLine();
                return value;
            } else {
                sc.nextLine();
                System.out.println("Invalid input. Please enter a number!");
            }
        }
    }

    // Client methods
    public void addClient(Client c) {
        clients[clientCount++] = c;
    }

    public void modifyClient(int cliCode, String newFirstname, String newLastname) {
        Client c = findClient(cliCode);
        if (c != null) {
            c.setFirstname(newFirstname);
            c.setLastname(newLastname);
            System.out.println("Client's data successfully modified!");
        } else {
            System.out.println("No Client with such a code found!");
        }
    }

    public void deleteClient(int cliCode) {
        for (int i = 0; i < accountCount; ) {
            if (accounts[i].getCliCode() == cliCode) {
                deleteAccount(accounts[i].getAccCode());
            } else {
                i++;
            }
        }

        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getCliCode() == cliCode) {
                for (int j = i; j < clientCount - 1; j++) {
                    clients[j] = clients[j + 1];
                }
                clients[--clientCount] = null;
            }
        }
    }

    public void displayClients() {
        Client[] sorted = Arrays.copyOf(clients, clientCount);
        Arrays.sort(sorted, (a, b) -> a.getLastname().compareToIgnoreCase(b.getLastname()));
        for (Client c : sorted) {
            System.out.printf("Code: %d Name: %s %s%n", c.getCliCode(), c.getLastname(), c.getFirstname());
        }
    }

    public Client findClient(int cliCode) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getCliCode() == cliCode) return clients[i];
        }
        return null;
    }

    // Account methods
    public void addAccount(Account acc) {
        if (findClient(acc.getCliCode()) == null) {
            System.out.println("Account must have an owner!");
            return;
        }
        if (findAccount(acc.getAccCode()) != null) {
            System.out.println("Account exists already!");
        } else {
            accounts[accountCount++] = acc;
        }
    }

    public void deleteAccount(int accCode) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccCode() == accCode) {
                for (int j = i; j < accountCount - 1; j++) {
                    accounts[j] = accounts[j + 1];
                }
                accounts[--accountCount] = null;
                System.out.println("Account closed.");
                return;
            }
        }
        throw new IllegalArgumentException("Account " + accCode + " not found!");
    }

    public Account findAccount(int accCode) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccCode() == accCode) return accounts[i];
        }
        return null;
    }

    public void displayAccounts() {
        for (int i = 0; i < accountCount; i++) {
            Account a = accounts[i];
            System.out.printf("Account %d | Client %d | Balance %.2f | Opened %s%n",
                    a.getAccCode(), a.getCliCode(), a.getBalance(), a.getOpendate());
        }
    }

    // Main Menu
    public void mainMenu() {
        while (true) {
            System.out.println("\n1- Client Management\n2- Account Management\n3- Transactions\n4- Exit");
            int choice = readInt("choose an option: ");
            switch (choice) {
                case 1: clientMenu(); break;
                case 2: accountMenu(); break;
                case 3: transactionsMenu(); break;
                case 4: return;
                default: System.out.println("Invalid choice");
            }
        }
    }

    // Submenus
    private void clientMenu() {
        System.out.println("\n1- Add\n2- Modify\n3- Delete\n4- Display\n5- Back");
        int choice = readInt("choose an option: ");
        switch (choice) {
            case 1:
                int cliCode = readInt("cliCode : ");
                if (String.valueOf(cliCode).length() != 8) {
                    System.out.println("Code must contain 8 digits!");
                    return;
                }
                if (findClient(cliCode) == null) {
                    System.out.print("firstname : ");
                    String firstname = sc.nextLine();
                    System.out.print("lastname : ");
                    String lastname = sc.nextLine();
                    addClient(new Client(cliCode, firstname, lastname));
                    System.out.println("Client successfully added!");
                } else {
                    System.out.printf("client code %d exists already%n", cliCode);
                }
                break;
            case 2:
                cliCode = readInt("Client code: ");
                if (String.valueOf(cliCode).length() != 8) {
                    System.out.println("Code must contain 8 digits!");
                    return;
                }
                System.out.print("new firstname : ");
                String newfirstname = sc.nextLine();
                System.out.print("new lastname : ");
                String newlastname = sc.nextLine();
                modifyClient(cliCode, newfirstname, newlastname);
                break;
            case 3:
                cliCode = readInt("cliCode : ");
                if (String.valueOf(cliCode).length() != 8) {
                    System.out.println("Code must contain 8 digits!");
                    return;
                }
                deleteClient(cliCode);
                break;
            case 4:
                displayClients();
                break;
        }
    }

    private void accountMenu() {
        System.out.println("\n1- Create\n2- Search\n3- Display all\n4- Close\n5- Back");
        int choice = readInt("choose an option: ");
        switch (choice) {
            case 1:
                int cliCode = readInt("client Code : ");
                if (String.valueOf(cliCode).length() != 8) {
                    System.out.println("client code must contain 8 digits!");
                    return;
                }
                int accCode = readInt("account Code: ");
                if (String.valueOf(accCode).length() != 8) {
                    System.out.println("account code must contain 8 digits!");
                    return;
                }
                double balance = readDouble("initial balance : ");
                if (balance >= 250) {
                    addAccount(new Account(cliCode, accCode, balance, LocalDate.now()));
                } else {
                    System.out.println("Initial balance should be at least 250");
                }
                break;
            case 2:
                accCode = readInt("account Code: ");
                if (String.valueOf(accCode).length() != 8) {
                    System.out.println("account code must contain 8 digits!");
                    return;
                }
                Account acc = findAccount(accCode);
                if (acc == null) {
                    System.out.println("Account not found!");
                } else {
                    acc.showBalance();
                }
                break;
            case 3:
                displayAccounts();
                break;
            case 4:
                accCode = readInt("account Code: ");
                if (String.valueOf(accCode).length() != 8) {
                    System.out.println("account code must contain 8 digits!");
                    return;
                }
                deleteAccount(accCode);
                break;
        }
    }

    private void transactionsMenu() {
        int accCode = readInt("account Code : ");
        if (String.valueOf(accCode).length() != 8) {
            System.out.println("account code must contain 8 digits!");
            return;
        }
        Account acc = findAccount(accCode);
        if (acc == null) {
            System.out.println("Account not found!");
        } else {
            System.out.println("1- Withdraw\n2- Deposit\n3- Back");
            int choice = readInt("choose an option: ");
            switch (choice) {
                case 1:
                    double amount = readDouble("amount to withdraw (max 500, balance " + acc.getBalance() + "): ");
                    if (amount >= 0 && amount <= acc.getBalance() && amount <= 500) {
                        acc.withdraw(amount);
                        System.out.println(amount + " withdrawn successfully!");
                        acc.showBalance();
                    } else {
                        System.out.println("Invalid amount. Check balance and withdrawal limit.");
                    }
                    break;
                case 2:
                    amount = readDouble("amount to deposit: ");
                    if (amount > 0) {
                        acc.deposit(amount);
                        System.out.println(amount + " deposited successfully!");
                        acc.showBalance();
                    }
                    break;
            }
        }
    }

    public void scannerClose() {
        sc.close();
    }
}
