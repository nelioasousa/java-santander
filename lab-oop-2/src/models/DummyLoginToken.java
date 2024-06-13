package models;

import repository.DatabaseTableEntry;

public class DummyLoginToken implements DatabaseTableEntry {
    private Integer id;
    private Account account;
    private String loginToken;
    private String hashedPassword;
    private byte[] hashSalt;

    public DummyLoginToken(Integer id, Account account, String loginToken)
    {
        this.id = id;
        this.account = account;
        this.loginToken = loginToken;
        this.parseToken(loginToken);
    }

    public DummyLoginToken(Account account, String plainTextPassword) {
        this.account = account;
        this.hashSalt = generateRandomSalt();
        this.hashedPassword = hashText(plainTextPassword, this.hashSalt);
        this.loginToken = combineInToken(this.hashedPassword, this.hashSalt);
    }

    public Integer getId()
    {
        return this.id;
    }

    public Account getAccount()
    {
        return this.account;
    }

    public String getLoginToken()
    {
        return this.loginToken;
    }

    public boolean checkPassword(String plainTextPassword) {
        return this.hashedPassword
                   .equals(hashText(plainTextPassword, this.hashSalt));
    }

    public void changePassword(String newPassword, String oldPassword)
        throws FailedAuthenticationException
    {
        if (!this.checkPassword(oldPassword))
        {
            throw new FailedAuthenticationException();
        }
        this.hashSalt = generateRandomSalt();
        this.hashedPassword = hashText(newPassword, this.hashSalt);
        this.loginToken = combineInToken(this.hashedPassword, this.hashSalt);
    }

    private void parseToken(String token)
    {
        this.hashedPassword = token;
        this.hashSalt = new byte[] {0, 1, 2, 3, 4, 5};
    }

    static private String combineInToken(String hash, byte[] salt)
    {
        return hash;
    }

    static private byte[] generateRandomSalt()
    {
        return new byte[] {0, 1, 2, 3, 4, 5};
    }

    static private String hashText(String textToHash, byte[] salt)
    {
        return textToHash;
    }
}

class FailedAuthenticationException extends Exception
{
    public FailedAuthenticationException()
    {
        super("Authentication failed");
    }
}
