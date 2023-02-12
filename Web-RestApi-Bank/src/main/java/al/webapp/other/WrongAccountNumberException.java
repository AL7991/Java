package al.webapp.other;

public class WrongAccountNumberException extends Exception{

    @Override
    public String getMessage() {
        return "Wrong account number was given";
    }
}
