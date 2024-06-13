package models;

import repository.DatabaseTableEntry;

public class Transaction implements DatabaseTableEntry
{
    private Integer id;
    private Account account;
    private String transactionType;
    private Double transactionAmount;
    private String transactionTimestamp;

    public Transaction(Account account,
                       String transactionType,
                       Double transactionAmount)
    {
        this.id = null;
        this.account = account;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionTimestamp = "9999-99-99 T00:00:00.000";
    }

    public Transaction(Integer id,
                       Account account,
                       String transactionType,
                       Double transactionAmount)
    {
        this.id = id;
        this.account = account;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionTimestamp = "9999-99-99 T00:00:00.000";
    }

    public Integer getId()
    {
        return this.id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Double getTransactionAmount()
    {
        return this.transactionAmount;
    }

    public Account getAccount()
    {
        return this.account;
    }

    public String getTransactionTimestamp()
    {
        return this.transactionTimestamp;
    }

    public boolean isWithdrawal()
    {
        return this.transactionType.equals("withdrawal");
    }

    public boolean itWasToday()
    {
        return false;
    }

    @Override
    public String toString()
    {
        return String.format(
            "%s(id=%d, account=%d, type=%s, value=%.2f)",
            this.getClass().getName(), this.id, this.account.getId(),
            this.transactionType, this.transactionAmount
        );
    }
}
