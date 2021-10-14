package edu.eci.escuelaing.arep.service;

import spark.Request;
import spark.Response;
import static spark.Spark.port;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import static spark.Spark.*;

public class App 
{
    private static List<String> urls = new ArrayList<String>();
    private static Boolean semaforo;
    public static void main( String[] args )
    {
        semaforo=true;
        urls.add("ec2-54-147-162-225.compute-1.amazonaws.com:4567/");
        urls.add("ec2-3-80-220-126.compute-1.amazonaws.com:4566/");
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
        String server;
        try{
            System.out.println("johhan es gurrero");
            if(semaforo)
            {
                server=urls.get(0);
            }else
            {
                server=urls.get(1);
            }
            semaforo= !semaforo;
            url=new URL(server+ oper + "?value="+ value);
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
        return 4567; //returns default port if heroku-port isn't set(i.e. on localhost)
    }
}
