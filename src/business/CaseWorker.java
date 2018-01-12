// Sayel Rammaha    
// 1/12/17

package business;

public class CaseWorker extends User{
    private int caseWorkerID;
    private String office;

    public CaseWorker()
    {
        super("", "", "", "");        
    }
    
    public CaseWorker(int id, String first, String last, String phone, 
            String email, String office)
    {
        super(first, last, phone, email);
        caseWorkerID = id;
        this.office = office;
    }
    
    public int getCaseWorkerID()
    {
        return caseWorkerID;
    }
    public void setCaseWorkerID(int caseWorkerID)
    {
        this.caseWorkerID = caseWorkerID;
    }
    public String getOffice()
    {
        return office;
    }
    public void setOffice(String office)
    {
        this.office = office;
    }
}
