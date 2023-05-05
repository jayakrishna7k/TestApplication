package Inmar.Test.app.exception;

public class SkuDataNotFoundException extends RuntimeException{
    private String message;

    public SkuDataNotFoundException() {}

    public SkuDataNotFoundException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}
