// Sayel Rammaha    
// 2/10/18



package business;

public class MessageResult {
    private boolean result;
    private String message;

    public MessageResult()
    {

    }

    public MessageResult(boolean result, String message)
    {
        this.result = result;
        this.message = message;
    }
    
    public boolean successful()
    {
        return this.result;
    }
    public void setResult(boolean result)
    {
        this.result = result;
    }
    public String getMessage()
    {
        return this.message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
                
}