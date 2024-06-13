package exceptions;

public class OverdraftLimitException extends WithdrawalException
{
    public OverdraftLimitException() {
        super("Amount exceeds the overdraft limit");
    }
}
