package edu.eci.escuelaing.arep.service;

import spark.Request;
import spark.Response;
import static spark.Spark.port;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import static spark.Spark.*;

public class App 
{
    public static void main( String[] args )
    {
        port(getPort());

        get("/ln","application/json",(req,res)->{
            res.type("application/json");
            return getRequest("ln",req.queryParams("value"));
        });
        get("/acos","application/json",(req,res)->{
            res.type("application/json");
            return getRequest("acos",req.queryParams("value"));
        });
    }



    public static String getRequest(String oper,String value) throws IOException
    {
        URL url;
        String ans="respuesta";
        System.out.println(ans);
        try{
            System.out.println("johhan es gurrero");
            url=new URL("http://localhost:4567/"+ oper + "?value="+ value);
            System.out.println(url);
            HttpURLConnection urlconection=(HttpURLConnection) url.openConnection();
            System.out.println(urlconection);
            urlconection.setRequestMethod("GET");
            
            BufferedReader input= new BufferedReader(new InputStreamReader(urlconection.getInputStream()));
            String inputline;
            StringBuffer content=new StringBuffer();
            while ((inputline = input.readLine()) !=null)
            {
                content.append(inputline);
                System.out.println("bagres");
            }
            System.out.println(inputline);
            input.close();
            urlconection.disconnect();
            ans= content.toString();
            System.out.println(ans);
            System.out.println("jeg");
            return ans;
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
            System.out.println("puta");
        }

        return ans;

        
    } 

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4568; //returns default port if heroku-port isn't set(i.e. on localhost)
    }
}
