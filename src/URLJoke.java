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

    public String readURL(String urlString) throws NoJokeFoundException{

       try {

           //create a new scanner retrieving data from a URL
           URL myURL = new URL(urlString);
           Scanner myScanner = new Scanner(myURL.openStream());

           //scan and append everything from the URL stream to a string
           String fetchedString = "";
           while(myScanner.hasNext()){
               fetchedString += myScanner.next();
           }
           myScanner.close();

           //create a Json object from the string
           JSONObject myObj = new JSONObject(fetchedString);

           //return in case "type" is not "success" (see icndb.com/api for more info)
           if(! myObj.getString("type").equals("success")){
               return null;
           }

           //extract "joke" as String from the retrieved JSON object
           String joke = myObj.getJSONObject("value").getString("joke");

           //return joke
           return joke;
       }

       catch(MalformedURLException e){
           throw new Exception(e);
       }

    }

    public String loadJokeFromURL(String urlString){


    }
}
