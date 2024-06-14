package models;

import repository.DatabaseTableEntry;

public class Transaction implements DatabaseTableEntry
{
    private Integer id;
    private Account sourceAccount;
    private Account destinationAccount;
    private String transactionType;
    private Double transactionAmount;
    private String transactionTimestamp;

    public Transaction(Account sourceAccount,
                       String transactionType,
                       Double transactionAmount)
    {
        this.id = null;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = null;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionTimestamp = "9999-99-99 T00:00:00.000";
    }

    public Transaction(Account sourceAccount,
                       Account destinationAccount,
                       String transactionType,
                       Double transactionAmount)
    {
        this.id = null;
        this.sourceAccount = sourceAccount;
        if (destinationAccount != null
            && destinationAccount.equals(sourceAccount))
        {
            destinationAccount = null;
        }
        this.destinationAccount = destinationAccount;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionTimestamp = "9999-99-99 T00:00:00.000";
    }

    public Transaction(Integer id,
                       Account sourceAccount,
                       Account destinationAccount,
                       String transactionType,
                       Double transactionAmount)
    {
        this.id = id;
        this.sourceAccount = sourceAccount;
        if (destinationAccount != null
            && destinationAccount.equals(sourceAccount))
        {
            destinationAccount = null;
        }
        this.destinationAccount = destinationAccount;
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

    public Account getSourceAccount()
    {
        return this.sourceAccount;
    }

    public Account getDestinationAccount()
    {
        return this.destinationAccount;
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
            "%s(id=%d, sourceAccount=%d, type=%s, value=%.2f)",
            this.getClass().getName(), this.id, this.sourceAccount.getId(),
            this.transactionType, this.transactionAmount
        );
    }
}
