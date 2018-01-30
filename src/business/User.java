// Sayel Rammaha    
// 1/12/17

package business;

public abstract class User {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String userName;
    
    
    protected User(String first, String last, String phone, String email, String userName)
    {
        firstName = first;
        lastName = last;
        this.phone = phone;
        this.email = email;
        this.userName = userName;
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
    public String getUserName()
    {
        return userName;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public String getPhoneFormatted()
    {
        return phone.replaceFirst("(\\d{3})(\\d{3})(\\d{4})", "($1) $2-$3");
    }
}
