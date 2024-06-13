package exceptions;

public class MonthlyWithdrawalLimitException extends WithdrawalException
{
    public MonthlyWithdrawalLimitException() {
        super("Amount exceeds the monthly withdrawal limit");
    }
}
