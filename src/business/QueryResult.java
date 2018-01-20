// Sayel Rammaha    
// 1/20/18

// to be able to pass two boolean values between function calls
// one for logical result of query, one indicating whether or not there were
// sql errors

package business;

public class QueryResult {
    private boolean result;
    private boolean sqlError;

    public QueryResult()
    {

    }

    public QueryResult(boolean result, boolean sqlErrors)
    {
        this.result = result;
        this.sqlError = sqlErrors;
    }
    
    public boolean successful()
    {
        return this.result;
    }
    public void setResult(boolean result)
    {
        this.result = result;
    }
    public boolean sqlErrors()
    {
        return this.sqlError;
    }
    public void setSqlError(boolean error)
    {
        this.sqlError = error;
    }
                
}