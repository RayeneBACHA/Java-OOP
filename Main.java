public class Main{
    public static void main(String[] args) {
        Bank bank = new Bank();

        bank.loadDefaultUsers();
        bank.loadClients();
        bank.loadAccounts();

        User loggedIN = null;
        while(loggedIN == null) loggedIN = bank.login();

        bank.mainMenu(loggedIN);

        bank.saveClients();
        bank.saveAccounts();
        bank.scannerClose();
    }
}