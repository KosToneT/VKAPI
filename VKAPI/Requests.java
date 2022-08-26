package VKAPI;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;


public class Requests {
    private String key;

    public String getKey(){
        return key;
    }

    public Requests(String key){
        this.key = key;
    }
    public String createVKResponse(String str, String message){
        return createVKResponse(str + "&message="+encodeTextFromHttp(message));
    }
    public String createVKResponse(String str){
        String url = "https://api.vk.com/method/" +str
                      + "&access_token="+key
                      + "&v=5.130";
        return sendRequests(url); 
    }

    public String sendRequests(String https){
        try {
            URL url = new URL(https);
            HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            BufferedReader in;
            try {
                in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF8"));
            } catch (Exception e) {
                in = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream(), "UTF8"));
            }
            String str[] ={""};
            in.lines().forEach((s)->str[0]+=s);;
            return str[0];
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    /**
     * load image file
     * @param http
     * @param file
     * @return
     */
    public String sendFileOnServer(String http, File file){
        try{
            FileInputStream isr = new FileInputStream(file);
            String typeName = "photo";
            String attachmentFileName = file.getName();
            String crlf = "\r\n";
            String twoHyphens = "--";
            String boundary =  "*****";

            HttpURLConnection httpUrlConnection = null;
            URL url = new URL(http);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDoOutput(true);

            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
            httpUrlConnection.setRequestProperty(
                "Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream request = new DataOutputStream(httpUrlConnection.getOutputStream());
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" +typeName
                                + "\";filename=\"" + attachmentFileName 
                                + "\"" + crlf);
            request.writeBytes(crlf);
            request.write(isr.readAllBytes());//Write file
            isr.close();
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
            request.flush();
            request.close();

            BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), "UTF8"));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            responseStreamReader.close();
            String response = stringBuilder.toString();
            return response;
        }catch(Exception ex){
            System.out.println(ex);
        }
        return null;

    }

    public String sendImageOnServer(String http, BufferedImage img){
        try{
            String typeName = "photo";
            String attachmentFileName = "photo1.bmp";
            String crlf = "\r\n";
            String twoHyphens = "--";
            String boundary =  "*****";
            HttpURLConnection httpUrlConnection = null;
            URL url = new URL(http);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDoOutput(true);

            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
            httpUrlConnection.setRequestProperty(
                "Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream request = new DataOutputStream(httpUrlConnection.getOutputStream());
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" +typeName
                                + "\";filename=\"" + attachmentFileName 
                                + "\"" + crlf);
            request.writeBytes(crlf);

            //img.getWidth(observer);

            ImageIO.write((RenderedImage)img, "jpg",request);
            

            //request.write(isr.readAllBytes());
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
            request.flush();
            request.close();

            InputStream responseStream = new BufferedInputStream(httpUrlConnection.getInputStream());
            BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            responseStreamReader.close();
            String response = stringBuilder.toString();
            return response;
        }catch(Exception ex){
            System.out.println(ex);
        }
        return null;

    }
    public static String useControlSym(String str){
        str = str.replace("\\n", "\n");
        str = str.replace("\\/","/" );
        str = str.replace("\\\"","'" );
        return str;
    }

    public static String encodeTextFromHttp(String str){
         try{
            str = URLEncoder.encode(str, "UTF-8");
            }catch(Exception ex){
                System.out.println(ex);
            }
        
        return str;
    }
    
    public static String decodeTextFromHttp(String str){
         try{
            str = URLDecoder.decode(str,"UTF-8");
            }catch(Exception ex){
                System.out.println(ex);
            }
        
        return str;
    }
}