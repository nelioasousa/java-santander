package exceptions;

public class TransferException extends Exception
{
    public TransferException() {
        super("Amount exceeds the transfer limit");
    }
}
