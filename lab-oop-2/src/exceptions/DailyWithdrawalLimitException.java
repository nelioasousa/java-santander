package exceptions;

public class DailyWithdrawalLimitException extends WithdrawalException
{
    public DailyWithdrawalLimitException() {
        super("Amount exceeds the daily withdrawal limit");
    }
}
