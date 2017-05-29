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

    int maxNumber;

    public URLJoke(){

    }

    public void initializeMaxValue(){

        try{

            //get JSON file including the maximum value for .../jokes/<number>
            String rawJSON = readURL("http://api.icndb.com/jokes/count");
            JSONObject myObj = new JSONObject(rawJSON);

            if(! myObj.getString("type").equals("success")){
                this.maxNumber = 0;
            }

            //set this.maxNumber to the value according to retrieved JSON file
            String maxNumberString = myObj.getString("value");
            this.maxNumber = Integer.parseInt(maxNumberString);

        }

        catch(Exception e){
            System.out.println("Problem getting max number");
            e.printStackTrace();
        }
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

    public String jokeWithNumber(int number) throws Exception{

        if(number > this.maxNumber){
            throw new NoJokeFoundException();
        }

        //assemble URL
        String myURL = "http://api.icndb.com/jokes/" + Integer.toString(number);

        try {
            String rawJSON = readURL(myURL);
            String joke = extractJoke(rawJSON);
            return joke;
        }

        catch(Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }

    }


    String jokeWithCategory(JokeCategory category) throws Exception {

        String baseURL = "http://api.icndb.com/jokes/random?\n" +
                "limitTo=[" + category.getURLCode() + "]";

        try{
            String rawJSON = readURL(baseURL);
            String joke = extractJoke(rawJSON);
            return joke;
        }

        catch(Exception e){
            throw new Exception(e);
        }
    }

    String randomJoke(String firstName, String lastName) throws Exception {

        //create base URL and modifiers (appendixes)
        String myURL = "http://api.icndb.com/jokes/random";
        String firstAppend = "?firstName=" + firstName;
        String secondAppend = "&lastName=" + lastName;

        //add modifiers to end of URL
        myURL += firstAppend + secondAppend;

        try {

            String rawJSON = readURL(myURL);
            String joke = extractJoke(rawJSON);
            return joke;

        }

        catch(Exception e){
            throw new Exception(e);
        }
    }

    String jokeWithNumber(int jokeNumber, String firstName, String lastName) throws Exception {

        if(jokeNumber > this.maxNumber){
            throw new NoJokeFoundException();
        }

        String baseURL = "http://api.icndb.com/jokes/";
        String firstAppend = "?firstName=" + firstName;
        String secondAppend = "&lastName=" + lastName;

        baseURL += Integer.toString(jokeNumber) + firstAppend + secondAppend;

        try{
            String rawJSON = readURL(baseURL);
            String joke = extractJoke(rawJSON);
            return joke;
        }

        catch(Exception e){
            throw new Exception(e);
        }
    }

    String jokeWithCategory(JokeCategory category, String firstName, String lastName){

        
    }
}
