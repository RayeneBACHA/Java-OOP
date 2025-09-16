public class Client {
    private int cliCode;
    private String firstname;
    private String lastname;


    public Client(int cliCode, String firstname, String lastname){
        this.cliCode = cliCode;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getCliCode() {return this.cliCode;}
    public String getFirstname() {return this.firstname;}
    public void setFirstname(String newFirstname) {this.firstname = newFirstname;}
    public String getlastname() {return this.lastname;}
    public void setLastname(String newLastname) {this.lastname = newLastname;}
}
