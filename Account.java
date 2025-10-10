import java.time.LocalDate;
import java.io.Serializable;

public class Account implements Serializable{
    private int cliCode;
    private int AccCode;
    private double balance;
    private LocalDate opendate;

    public Account(int cliCode, int AccCode, double balance, LocalDate opendate){
        this.cliCode = cliCode;
        this.AccCode = AccCode;
        this.balance = balance;
        this.opendate= opendate;
    }
    public int getCliCode() {return cliCode;}
    public int getAccCode() {return AccCode;}
    public double getBalance() {return balance;}
    public LocalDate getOpendate() {return opendate;}


    public void deposit(double amount) {this.balance += amount;}
    public void withdraw(double amount) {this.balance -= amount;}
    public void showBalance() {
        System.out.printf("Account : %d 'balance is %.2f\n", AccCode, balance);
    }
}