package BankSystem;

import java.util.ArrayList;
import java.util.List;

public class ATM implements ITransaction {
    private Double balance;

    private List<Card> cardList;

    public ATM(Double balance, List<Card> cardList) {
        this.balance = balance;
        this.cardList = cardList;
    }

    public ATM() {
        this.cardList = new ArrayList<>();
    }

    public ATM(Double balance) {
        this.balance = balance;
        this.cardList = new ArrayList<>();
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public void addCardToList(Card card){
        cardList.add(card);
    }


    @Override
    public void withdraw(double balance, Card card) {
        if (this.balance>=balance && card.getBalance()>=balance){
            this.balance-=balance;
            card.withdraw(balance);
            System.out.println("Successful withdrawal");
        }
        else System.out.println("Insufficient funds");
    }

    @Override
    public void deposit(double balance, Card card) {
        if(balance<=1000000 && balance >=0) {
            this.balance += balance;
            card.deposit(balance);
            System.out.println("Successful deposit");
        }
        else System.out.println("Deposit amount should not exceed 1 000 000");
    }
}
