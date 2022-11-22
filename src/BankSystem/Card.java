package BankSystem;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Card
{
    private String cardNumber;
    private String pin;
    private double balance;
    private int tries;
    private Date lastDayOfBan;

    public Card(String cardNumber, String pin, double balance, int tries, Date lastDayOfBan) throws Exception {
            if(!IsValidCardNumber(cardNumber)) throw new ValidationException("Invalid card number format");
            if(pin.length()!=4) throw new ValidationException("Invalid pincode format");
            this.cardNumber = cardNumber;
            this.pin = pin;
            this.balance = balance;
            this.tries = tries;
            this.lastDayOfBan = lastDayOfBan;
            unBan();
    }

    public Card() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public Date getLastDayOfBan() {
        return lastDayOfBan;
    }

    public void setLastDayOfBan(Date lastDayOfBan) {
        this.lastDayOfBan = lastDayOfBan;
    }

    public Boolean IsValidCardNumber(String cardNumber){
        Pattern pattern = Pattern.compile("\\d{4}-\\d{4}-\\d{4}-\\d{4}");
        Matcher matcher = pattern.matcher(cardNumber);
        return matcher.find();
    }

    @Override
    public String toString() {
        return cardNumber + " " + pin + " " + balance + " " + tries + " " + BankSystem.convertDateToString(lastDayOfBan);
    }

    public void withdraw(double balance) {
        this.balance-=balance;
    }

    public void deposit(double balance) {
        this.balance+=balance;
    }

    public void unBan(){
        Date date = new Date();
        long diffInMillies = Math.abs(date.getTime() - lastDayOfBan.getTime());
        if(TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS)>=24){
            tries=3;
        }
        else tries=0;
    }
}
