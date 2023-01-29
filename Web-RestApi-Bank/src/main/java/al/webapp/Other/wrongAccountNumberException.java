package al.webapp.Other;

public class wrongAccountNumberException extends Exception{

    @Override
    public String getMessage() {
        return "Wrong account number was given";
    }
}
