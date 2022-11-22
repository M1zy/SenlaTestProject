package BankSystem;

public interface ITransaction{
    void withdraw(double balance, Card card);

    void deposit(double balance, Card card);
}
