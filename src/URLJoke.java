import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.json.*;
/**
 * Created by qngapparat on 29.05.17.
 */
public class URLJoke {

    public URLJoke(){

    }


    public String readURL (String urlString) throws Exception{

        try {

            //create a new scanner retrieving data from a URL
            URL myURL = new URL(urlString);
            Scanner myScanner = new Scanner(myURL.openStream());

            //scan and append everything from the URL stream to a string
            String fetchedString = "";
            while (myScanner.hasNext()) {
                fetchedString += myScanner.next();
            }
            myScanner.close();

            return fetchedString;
        }

        //IOException also includes MalformedURLException
        catch(IOException e){
            e.printStackTrace();
            throw new Exception(e);
        }


    }

    public String loadJokeFromURL (String urlString) throws Exception{

       try {

           //reads json from URL into String
           String rawJSON = readURL(urlString);
           //extracts "joke" String from the raw json String.
           String joke = extractJoke(rawJSON);
       }

       catch(MalformedURLException e){
           throw new Exception(e);
       }

       catch(Exception e){
           throw new NoJokeFoundException();
       }

    }

    public String extractJoke(String rawJSON){

        //create a Json object from the string
        JSONObject myObj = new JSONObject(rawJSON);

        //return in case "type" is not "success" (see icndb.com/api for more info)
        if(! myObj.getString("type").equals("success")){
            return null;
        }

        //extract "joke" as String from the retrieved JSON object
        String joke = myObj.getJSONObject("value").getString("joke");

        //return joke
        return joke;
    }

    /*public static void main(String[] args) throws Exception {

        URLJoke myJoke = new URLJoke();
        System.out.println(myJoke.loadJokeFromURL("http://api.icndb.com/jokes/89"));
    }*/

    public String randomJoke() throws Exception{

        try{
            String rawJSON = readURL("http://api.icndb.com/jokes/random");
            String joke = extractJoke(rawJSON);
            return joke;
        }

        catch(Exception e){
            throw new Exception(e);
        }
    }




}
