// Sayel Rammaha    
// 1/12/17

package business;

public abstract class User {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    
    
    protected User(String first, String last, String phone, String email)
    {
        firstName = first;
        lastName = last;
        this.phone = phone;
        this.email = email;
    }
    
    
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    
}
