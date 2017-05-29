import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.json.*;
/**
 * Created by qngapparat on 29.05.17.
 */
public class URLJoke {

    public String readURL(String urlString) throws Exception{

       try {
           URL myURL = new URL(urlString);
           Scanner myScanner = new Scanner(myURL.openStream());

           String fetchedString = "";
           while(myScanner.hasNext()){
               fetchedString += myScanner.next();
           }
           myScanner.close();

           JSONObject myObj = new JSONObject(fetchedString);

           //return in case "type" is not "success"
           if(! myObj.getString("type").equals("success")){
               return null;
           }
            String joke = myObj.getJSONObject("value").getString("joke");

           
       }

       catch(MalformedURLException e){
           throw new Exception(e);
       }

    }
}
