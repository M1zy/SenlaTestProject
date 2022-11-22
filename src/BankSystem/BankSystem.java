package BankSystem;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BankSystem {
    private ATM atm;

    public void start(){
        try{
            String content = readFile("ATMData.txt", StandardCharsets.UTF_8);
            String[] words = content.split(" ");
            atm = new ATM(Double.parseDouble(words[0]));
            for(int i = 1; i < words.length; i+=5){

                atm.addCardToList(new Card(words[i],words[i+1],Double.parseDouble(words[i+2]),Integer.parseInt(words[i+3]), convertStringToDate(words[i+4])));
            }
            System.out.println("System's been launched");
            loginCardUser();
        }
        catch (NoSuchFileException ex){
            System.out.println("Invalid data");
        }
        catch (ValidationException | NumberFormatException validationException){
            System.out.println("Invalid data format");
        }
        catch (ParseException ex){
            System.out.println("Invalid date format");
        }
        catch (Exception ex){
            System.out.println("System error");
        }
    }

    public void loginCardUser(){
        List<Card> cards = atm.getCardList();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Main menu.\n 1. Input card \n 2. Exit");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.print("Card number:");
                String cardNumber = scanner.next();
                for (Card card:
                        cards) {
                    if(card.getCardNumber().equals(cardNumber)){
                        String pin = "";
                        while(true){
                            if (card.getPin().equals(pin)){
                                System.out.println("Welcome!");
                                card.setTries(3);
                                userMenu(card);
                                break;
                            }
                            else if(card.getTries()==0){
                                System.out.println("Card's been blocked");
                                loginCardUser();
                                break;
                            }
                            else{
                                System.out.print("Tries left: "+card.getTries()+"\nPincode:");
                                pin = scanner.next();
                                card.setTries(card.getTries()-1);
                            }
                        }
                    }
                }
                    System.out.println("No available card.");
                    loginCardUser();
            }
            case 2 -> {
                saveFile();
                System.exit(0);
            }
        }
    }

    public void userMenu(Card card){
        System.out.println(" 1. Check Balance \n 2. Withdraw \n 3. Deposit \n 4. Exit");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("Balance:"+card.getBalance());
                userMenu(card);
            }
            case 2 -> {
                System.out.print("Withdrawal amount:");
                atm.withdraw(scanner.nextDouble(),card);
                userMenu(card);
            }
            case 3 -> {
                System.out.print("Deposit amount:");
                atm.deposit(scanner.nextDouble(),card);
                userMenu(card);
            }
            case 4 -> {
                saveFile();
                System.exit(0);
            }
        }
    }

    //return full text from file
    public static String readFile(String path, Charset encoding) throws IOException {
        return Files.lines(Paths.get(path), encoding)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public void saveFile(){
        try(FileWriter writer = new FileWriter("ATMData.txt", false))
        {
            writer.write(atm.getBalance().toString());
            for (Card card:
                 atm.getCardList()) {
                writer.append(" ").append(card.toString());
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static Date convertStringToDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd/MM/yyyy-HH:mm:ss");
        return format.parse(date);
    }

    public static String convertDateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        return formatter.format(date);
    }
}
