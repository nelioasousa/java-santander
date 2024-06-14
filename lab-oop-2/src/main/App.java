package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import exceptions.TransferException;
import exceptions.WithdrawalException;
import models.*;
import repository.DummyRepository;
import java.lang.IllegalStateException;

public class App {
    public static void main(String args[])
    {
        // Hard-coded database entities
        Client c1 = new Client(10, "Cornélio", "Sousa", "2024-06-13");
        Client c2 = new Client(20, "John", "Doe", "2000-05-24");
        Account a1 =
            new Account(100, c1, 2000.0, 0.015, 200.0, 1000.0, 200.0, 0.0);
        Account a2 =
            new Account(200, c2, 10000.0, 0.015, 1000.0, 5000.0, 1000.0, 5.000);
        DummyLoginToken a1Token = new DummyLoginToken(1000, a1, "12345");
        DummyLoginToken a2Token = new DummyLoginToken(2000, a2, "qwert");


        // Repositories
        DummyRepository<Client> clientsRepo = getClientsRepository();
        clientsRepo.save(c1);
        clientsRepo.save(c2);

        DummyRepository<Account> accountsRepo = getAccountsRepository();
        accountsRepo.save(a1);
        accountsRepo.save(a2);

        DummyRepository<DummyLoginToken> tokensRepo = getTokensRepository();
        tokensRepo.save(a1Token);
        tokensRepo.save(a2Token);
        
        DummyRepository<Transaction> transactionsRepo =
            getTransactionsRepository();


        // Application example
        System.out.println(
            String.format("Account 1 Balance before : %.2f", a1.getBalance())
        );
        // Deposit example
        Transaction t1 = a1.deposit(500.0);
        System.out.println(
            String.format("Account 1 Balance after  : %.2f", a1.getBalance())
        );
        t1.setId(100);
        transactionsRepo.save(t1);

        // Failed withdrawal example
        try
        {
            /* Throws DailyWithdrawalLimitException */
            Transaction t2 =
                a1.withdrawal(1000.0, new ArrayList<Transaction>());
            t2.setId(200);
            transactionsRepo.save(t2);
        }
        catch (WithdrawalException e)
        {
            e.printStackTrace();
        }

        // Successful withdrawal example
        try
        {
            // Login example
            Account acc =
                accountLogin(accountsRepo, tokensRepo, 200, "qwert").get();
            System.out.println(
                String.format(
                    "Account 2 Balance before : %.2f", acc.getBalance()
                )
            );
            Transaction t3 =
                a2.withdrawal(800.0, new ArrayList<Transaction>());
            System.out.println(
                String.format(
                    "Account 2 Balance after  : %.2f", acc.getBalance()
                )
            );
            t3.setId(300);
            transactionsRepo.save(t3);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Successful transfer example
        try
        {
            List<Transaction> withdrawals = new ArrayList<Transaction>();
            withdrawals.add(transactionsRepo.get(300).get());
            Transaction t4 =
                a2.transfer(150.0, a1, withdrawals);
            t4.setId(400);
            transactionsRepo.save(t4);
        }
        catch (WithdrawalException | TransferException e)
        {
            e.printStackTrace();
        }

        // Transaction storage check
        transactionsRepo.getAll()
                        .forEach(t -> System.out.println(t.toString()));
    }

    static private DummyRepository<Client> getClientsRepository()
    {
        return new DummyRepository<Client>();
    }

    static private DummyRepository<Account> getAccountsRepository()
    {
        return new DummyRepository<Account>();
    }

    static private DummyRepository<Transaction> getTransactionsRepository()
    {
        return new DummyRepository<Transaction>();
    }

    static private DummyRepository<DummyLoginToken> getTokensRepository()
    {
        return new DummyRepository<DummyLoginToken>();
    }

    static private Optional<Account> accountLogin(
        DummyRepository<Account> accountsRepository,
        DummyRepository<DummyLoginToken> tokensRepository,
        Integer accountId,
        String password)
    {
        Optional<Account> account = accountsRepository.get(accountId);
        if (account.isEmpty())
        {
            return account;
        }
        Optional<DummyLoginToken> token = 
            tokensRepository.getAll()
                            .stream()
                            .filter(t -> t.getAccount().getId().equals(accountId))
                            .findFirst();
        if (token.isEmpty())
        {
            throw new IllegalStateException();
        }
        if (token.get().checkPassword(password))
        {
            return account;
        }
        return Optional.empty();
    }
}
