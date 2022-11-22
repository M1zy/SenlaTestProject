package BankSystem;

public class ValidationException extends Exception{
    String message;

    ValidationException(String str){
        message = str;
    }

    public String toString() {
        return ("Validation Exception Occurred: " + message);
    }
}
