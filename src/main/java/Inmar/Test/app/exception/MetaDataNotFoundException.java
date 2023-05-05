package Inmar.Test.app.exception;

public class MetaDataNotFoundException extends RuntimeException{
    private String message;

    public MetaDataNotFoundException() {}

    public MetaDataNotFoundException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}
