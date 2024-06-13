package models;

import java.util.List;
import repository.DatabaseTableEntry;
import java.lang.Math;
import exceptions.*;

/* Should use fixed point arithmetic */

public class Account implements DatabaseTableEntry
{
    private Integer id;
    private Client client;
    private Double balance;
    private Double interestRate;
    private Double dailyWithdrawalLimit;
    private Double monthlyWithdrawalLimit;
    private Double transferLimit;
    private Double overdraftLimit;

    public Account(Integer id,
                   Client client,
                   Double balance,
                   Double interestRate,
                   Double dailyWithdrawalLimit,
                   Double monthlyWithdrawalLimit,
                   Double transferLimit,
                   Double overdraftLimit)
    {
        this.id = id;
        this.client = client;
        this.balance = balance;
        this.interestRate = interestRate;
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
        this.monthlyWithdrawalLimit = monthlyWithdrawalLimit;
        this.transferLimit = transferLimit;
        this.overdraftLimit = overdraftLimit;
    }

    public Integer getId()
    {
        return this.id;
    }

    public Client getClient()
    {
        return this.client;
    }

    public Double getInterestRate()
    {
        return this.interestRate;
    }

    public Double getBalance()
    {
        return this.balance;
    }

    public Transaction withdrawal(double value, List<Transaction> monthWithdrawals)
        throws WithdrawalException
    {
        if (value <= 0.0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        Double newBalance = this.balance - value;
        if (newBalance < 0 && Math.abs(newBalance) > this.overdraftLimit) {
            throw new OverdraftLimitException();
        }
        double dayAccumulated = monthWithdrawals.stream()
            .filter(t -> t.itWasToday())
            .mapToDouble(t -> t.getTransactionAmount())
            .sum();
        if ((this.dailyWithdrawalLimit - dayAccumulated) < value) {
            throw new DailyWithdrawalLimitException();
        }
        double monthAccumulated = monthWithdrawals.stream()
            .mapToDouble(t -> t.getTransactionAmount())
            .sum();
        if ((this.monthlyWithdrawalLimit - monthAccumulated) < value) {
            throw new MonthlyWithdrawalLimitException();
        }
        this.balance = newBalance;
        return new Transaction(this, "withdrawal", value);
    }

    public Transaction deposit(double value)
    {
        if (value <= 0.0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.balance += value;
        return new Transaction(this, "deposit", value);
    }

    public Transaction transfer(double value,
                                Account toAccount,
                                List<Transaction> monthWithdrawalTransactions)
        throws TransferException, WithdrawalException
    {
        if (value <= 0.0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (value > this.transferLimit) {
            throw new TransferException();
        }
        this.withdrawal(value, monthWithdrawalTransactions);
        toAccount.deposit(value);
        return new Transaction(this, "transfer", value);
    }
}
