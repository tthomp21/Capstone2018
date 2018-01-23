// Sayel Rammaha    
// 1/12/17

package business;
import java.time.LocalDate;

public class Client extends User {
    private int clientID;
    private String SSN;
    private String middleInit;
    private String city;
    private String street;
    private String state;
    private String zip;
    private LocalDate birthDate;
    private boolean married;
    private LocalDate enrollmentDate;
    private int dependents;
    private int partnerID;
    private int caseWorkerID;
        
    
    public Client() 
    {
        super("", "", "", "", "");
    }
    
    public Client(int id, String userName, String first, String mInit, String last, String phone, 
            String email, String ssn, String city, String state, String zip,
            LocalDate birthDate, boolean married, LocalDate enrollmentDate, 
            int dependents, int partnerID, int caseWorkerID)
    {
        super(first, last, phone, email, userName);
        clientID = id;
        middleInit = mInit;
        SSN = ssn;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.birthDate = birthDate;
        this.married = married;
        this.enrollmentDate = enrollmentDate;
        this.dependents = dependents;
        this.partnerID = partnerID;
        this.caseWorkerID = caseWorkerID;        
    }

    
    public int getClientID()
    {
        return clientID;
    }
    public void setClientID(int clientID)
    {
        this.clientID = clientID;
    }
    public String getSSN()
    {
        return SSN;
    }
    public void setSSN(String sSN)
    {
        SSN = sSN;
    }
    public String getMiddleInit()
    {
        return middleInit;
    }
    public void setMiddleInit(String middleInit)
    {
        this.middleInit = middleInit;
    }
    public String getStreet()
    {
        return street;
    }
    public void setStreet(String street)
    {
        this.street = street;
    }
    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public String getState()
    {
        return state;
    }
    public void setState(String state)
    {
        this.state = state;
    }
    public String getZip()
    {
        return zip;
    }
    public void setZip(String zip)
    {
        this.zip = zip;
    }
    public LocalDate getBirthDate()
    {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate)
    {
        this.birthDate = birthDate;
    }
    public boolean isMarried()
    {
        return married;
    }
    public void setMarried(boolean married)
    {
        this.married = married;
    }
    public LocalDate getEnrollmentDate()
    {
        return enrollmentDate;
    }
    public void setEnrollmentDate(LocalDate enrollmentDate)
    {
        this.enrollmentDate = enrollmentDate;
    }
    public int getDependents()
    {
        return dependents;
    }
    public void setDependents(int dependents)
    {
        this.dependents = dependents;
    }
    public int getPartnerID()
    {
        return partnerID;
    }
    public void setPartnerID(int partnerID)
    {
        this.partnerID = partnerID;
    }
    public int getCaseWorkerID()
    {
        return caseWorkerID;
    }
    public void setCaseWorkerID(int caseWorkerID)
    {
        this.caseWorkerID = caseWorkerID;
    }
}
