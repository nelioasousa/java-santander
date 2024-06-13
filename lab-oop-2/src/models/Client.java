package models;

import repository.DatabaseTableEntry;

public class Client implements DatabaseTableEntry
{
    private Integer id;
    private String firstName;
    private String lastName;
    private String registrationDate; /* Check Java datetime libraries */

    public Client(Integer id,
                  String firstName,
                  String lastName,
                  String registrationDate)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDate = registrationDate;
    }

    public Integer getId()
    {
        return this.id;
    }

    public String getName()
    {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    public String getRegistrationDate()
    {
        return this.registrationDate;
    }
}
