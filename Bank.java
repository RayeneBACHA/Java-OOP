import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;


public class Bank {
    private Client [] clients = new Client[100];
    private Account [] accounts = new Account[100];
    private int clientCount = 0;
    private int accountCount = 0;
    private Scanner sc = new Scanner(System.in);
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
        }else{
            System.out.println("No Client with such a code found!");
        }
    }
    public void deleteClient(int cliCode) {
        for(int i = 0; i < accountCount; ) {
            if(accounts[i].getCliCode() == cliCode) {
                deleteAccount(accounts[i].getCliCode());
            }else{
                i++;
            }
        }

        for(int i = 0; i < clientCount; i++) {
            if (clients[i].getCliCode() == cliCode) {
                for (int j = i; j < clientCount - 1; j++) {
                    clients[j] = clients[j+1];
                }
                clients[--clientCount] = null;
            }
        }
    }
    public void displayClients() {
        Client [] sorted = Arrays.copyOf(clients, clientCount); 
        Arrays.sort(sorted, (a,b) -> a.getLastname().compareToIgnoreCase(b.getLastname()));
        for(Client c : sorted) {
            System.out.printf("Code: %d Name: %s %s", c.getCliCode(), c.getLastname(), c.getFirstname());
        }
    }
    public Client findClient (int cliCode) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getCliCode() == cliCode) return clients[i];
        }
        return null;
    }

    // Account methods
    public void addAccount(Account acc) {
        if(findClient(acc.getCliCode()) == null) {
            System.out.println("Account must have an owner!");
            return;
        }
        if(findAccount(acc.getAccCode()) != null) {
            System.out.println("Account exists already!");
        }else{
            accounts[accountCount++] = acc;
        }
    }
    public void deleteAccount(int accCode) {
        for(int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccCode() == accCode) {
                for (int j = i; j < accountCount - 1; j++) {
                    accounts[j] = accounts[j+1];
                }
                accounts[--accountCount] = null;
                return;
            }
        }
        throw new IllegalArgumentException("Account " + accCode + " not found!");
    }
    public Account findAccount (int accCode) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccCode() == accCode) return accounts[i];
        }
        return null;
    }
    public void displayAccounts() {
        for (int i = 0; i < accountCount; i++) {
            Account a = accounts[i];
            System.out.printf("Account %d | Client %d | Balance %.2f | Opened %s\n",
                    a.getAccCode(), a.getCliCode(), a.getBalance(), a.getOpendate());
        }
    }
    // Main Menu (instance method)
    public void mainMenu() {
        while(true) {
            System.out.println("\n1- Client Management\n2- Account Management\n3- Transactions\n4- Exit");
            System.out.print("choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:clientMenu(sc); break;
                case 2:accountMenu(sc); break;
                case 3:transactionsMenu(sc); break;
                case 4:return;
                default:System.out.println("Invalid choice");
            }
        }
    }
    // Submenus
    private void clientMenu(Scanner sc) {
        System.out.println("\n1- Add\n2- Modify\n3- Delete\n4- Display\n5- Back");
        System.out.print("choose an option: ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.print("cliCode :");
                int cliCode = sc.nextInt();
                sc.nextLine();
                if(String.valueOf(cliCode).length() != 8) {
                    System.out.println("Code must contain 8 digits!");
                    return;
                }
                if (findClient(cliCode) == null) {
                    System.out.print("firstname : ");
                    String firstname = sc.nextLine();
                    System.out.print("laststname : ");
                    String laststname = sc.nextLine();
                    addClient(new Client(cliCode, firstname, laststname));
                    System.out.println("Client successfully added!");
                }else{
                    System.out.printf("client code %d exists already", cliCode);
                }
                break;
            case 2:
                System.out.print("cliCode :");
                cliCode = sc.nextInt();
                sc.nextLine();
                if(String.valueOf(cliCode).length() != 8) {
                    System.out.println("Code must contain 8 digits!");
                    return;
                }
                System.out.print("newfirstname : ");
                String newfirstname = sc.nextLine();
                System.out.print("newlaststname : ");
                String newlaststname = sc.nextLine();
                modifyClient(cliCode, newfirstname, newlaststname);
                break;
            case 3:
                System.out.print("cliCode :");
                cliCode = sc.nextInt();
                sc.nextLine();
                if(String.valueOf(cliCode).length() != 8) {
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
    private void accountMenu(Scanner sc) {
        System.out.println("\n1- Create\n2- Search\n3- Display all\n4- Close\n5- Back");
        System.out.print("choose an option: ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                System.out.print("client Code :");
                int cliCode = sc.nextInt();
                if(String.valueOf(cliCode).length() != 8) {
                    System.out.println("client code must contain 8 digits!");
                    return;
                }
                System.out.print("account Code :");
                int accCode = sc.nextInt();
                sc.nextLine();
                if(String.valueOf(accCode).length() != 8) {
                    System.out.println("account code must contain 8 digits!");
                    return;
                }
                System.out.print("initial balance :");
                double balance= sc.nextDouble();
                if(balance >= 250) {
                    addAccount(new Account(cliCode, accCode, balance, LocalDate.now()));
                }
                break;
            case 2:
                System.out.print("account Code :");
                accCode = sc.nextInt();
                sc.nextLine();
                if(String.valueOf(accCode).length() != 8) {
                    System.out.println("account code must contain 8 digits!");
                    return;
                }
                Account acc = findAccount(accCode);
                if(acc == null) {
                    System.out.println("Account not found!");
                }else{
                    acc.showBalance();
                }
                break;
            case 3:
                displayAccounts();
                break;
            case 4:
                System.out.print("account Code :");
                accCode = sc.nextInt();
                sc.nextLine();
                if(String.valueOf(accCode).length() != 8) {
                    System.out.println("account code must contain 8 digits!");
                    return;
                }
                deleteAccount(accCode);
                break;
        }
    }
    private void transactionsMenu(Scanner sc) {
        System.out.print("account Code :");
        int accCode = sc.nextInt();
        sc.nextLine();
        if(String.valueOf(accCode).length() != 8) {
            System.out.println("account code must contain 8 digits!");
                return;
        }
        Account acc = findAccount(accCode);
        if(acc == null) {
            System.out.println("Account not found!");
        }else{
            System.out.println("1- Withdraw\n2- Deposit\n3- Back");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("amount to withdraw");
                    double amount= sc.nextDouble();
                    if(amount >= 0 && amount <=acc.getBalance() && amount <= 500) {
                        acc.withdraw(amount);
                        System.out.println(amount + "withdrawed succsessfully!");
                        acc.showBalance();
                    }
                    break;
                case 2:
                    System.out.println("amount to deposit");
                    amount= sc.nextDouble();
                    if(amount > 0) {
                        acc.deposit(amount);
                        System.out.println(amount + "deposited succsessfully!");
                        acc.showBalance();
                    }
                    break;
            }
        }

    }
}
