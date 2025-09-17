public class Main{
    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.loadClients();
        bank.loadAccounts();
        bank.mainMenu();
        bank.saveClients();
        bank.saveAccounts();
        bank.scannerClose();
    }
}