package pl.wojciechkostecki.devicelocation.exception;

public class LoginAlreadyUsedException extends RuntimeException{
    public LoginAlreadyUsedException(String message) {
        super(message);
    }
}
