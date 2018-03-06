// Sayel Rammaha    
// 2/4/18
// method for retrieving file from storage

package data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileServe {
    
    // this will serve the requested file to the user trying to access it
    // when a case worker requests to view documentation for a request, call this method
    // send this method the "current servlet context" by passing "getServletContext()" as 3rd param [sc]
    // also send request and response, and (int)requestID of the documentation you are retrieving
    // after you call this method, do not redirect the response like normal at the end of the servlet
    // "sc.getRequestDispatcher(url).forward(request, response);  "  this is unnecessary as this
    // method writes the file directly to the response and the browser prompts the user to save/open the file
    // if file isn't found, method will return false, and you will need to process than and redirect like normal
    public static boolean retrieve(HttpServletRequest request, HttpServletResponse response, ServletContext sc, int requestID) throws IOException, ServletException
    {
        String id = requestID + "";
        
        // set directory for uploaded files
        String fileDir = System.getProperty("user.home") + "/My Documents";
        // create file object from fileDir
        File searchDir = new File(fileDir);
        // reference for chosen file
        File fileToServe = null;
        
        // search file directory for files with prefix matching request id
        File[] list = searchDir.listFiles();
        boolean found = false;
        if (list != null)
        {        
            for (File f : list)
            {
                String prefix = f.getName().substring(0, id.length() + 1);
                if (prefix.equals(id + "-"))
                {
                    fileToServe = f; // keep ref to correct file when found
                    found = true;
                    break;
                }
            }
        }                
        if (!found)
            return false;        
        
        response.setHeader("Content-Type", sc.getMimeType(fileToServe.getName()));
        response.setHeader("Content-Length", String.valueOf(fileToServe.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + fileToServe.getName() + "\"");
        Files.copy(fileToServe.toPath(), response.getOutputStream());
        
        return true;
    }
}
