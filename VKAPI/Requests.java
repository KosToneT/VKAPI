package VKAPI;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;


public class Requests {
    private String key;
    public boolean info = false;
    public String getKey(){
        return key;
    }
    public Requests(String key){
        this.key = key;
    }
    public String createVKResponse(String str){
        String url = "https://api.vk.com/method/" +str
                      + "&access_token="+key
                      + "&v=5.130";
        return SendResponse(url); 
    }
    public String SendResponse(String https){
        String response = new String();
        try {

            BufferedReader in;
            URL url = new URL(https);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            try {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF8"));
            } catch (Exception e) {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "UTF8"));
            }
            String inputLine;
            while((inputLine = in.readLine())!=null){
                response += inputLine;
            }
            in.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        if(info){
            System.out.println(https);
            System.out.println(response);
        }
        return response;
    }

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

    public static String booleanTonumStr(boolean value){
        return value?"1":"0";
    }
    public static String decodeTextWithControlSym(String str){
        str = decodeTextFromHttp(str);
        return str.replace("\\n", "\n");
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

    @Deprecated
    public static String DecodeBL(String str){
       char[] hm = str.toCharArray();
       char sym;
       String word="";
       for (int i=0; i<str.length()-1; i++){
           sym = (char) (str.charAt(i));
           int s = sym;
           
           if (s>1000){
               sym = (char) (str.charAt(++i));
               s += sym;
               sym = DecodeBL(s);
           }
           //System.out.println("Sym = "+ sym + " s = " + s + " i = "+i);
           word += sym; 
       }
       str = word;
       return str;
       }
    @Deprecated
    public static char DecodeBL(int sym){
        char q = '0';
        if (sym == 1232){
            q = 'а';
        }
        else if (sym == 1233){
            q = 'б';
        }
        else if (sym == 2086){
            q = 'в';
        }
        else if (sym == 2166){
            q = 'г';
        }
        else if (sym == 2225){
            q = 'д';
        }
        else if (sym == 1237){
            q = 'е';
        }
        else if (sym == 9273){
            q = 'ё';
        }
        else if (sym == 1238){
            q = 'ж';
        }
        else if (sym == 1239){
            q = 'з';
        }
        else if (sym == 2161){
            q = 'и';
        }
        else if (sym == 9526){
            q = 'й';
        }
        else if (sym == 2164){
            q = 'к';
        }
        else if (sym == 1243){
            q = 'л';
        }
        else if (sym == 2168){
            q = 'м';
        }
        else if (sym == 2085){
            q = 'н';
        }
        else if (sym == 2165){
            q = 'о';
        }
        else if (sym == 2167){
            q = 'п';
        }
        else if (sym == 2083){
            q = 'р';
        }
        else if (sym == 2084){
            q = 'с';
        }
        else if (sym == 9275){
            q = 'т';
        }
        else if (sym == 9279){
            q = 'ф';
        }
        else if (sym == 9287){
            q = 'х';
        }
        else if (sym == 9281){
            q = 'ц';
        }
        else if (sym == 9282){
            q = 'ч';
        }
        else if (sym == 9421){
            q = 'ш';
        }
        else if (sym == 9297){
            q = 'щ';
        }
        else if (sym == 2090){
            q = 'ъ';
        }
        else if (sym == 9306){
            q = 'ы';
        }
        else if (sym == 2091){
            q = 'ь';
        }
        else if (sym == 2093){
            q = 'э';
        }
        else if (sym == 2092){
            q = 'ю';
        }
        else if (sym == 2096){
            q = 'я';
        }
        else if (sym == 2162){
            q = 'A';
        }
        else if (sym == 9272){
            q = 'Б';
        }
        else if (sym == 9276){
            q = 'Г';
        }
        else if (sym == 9277){
            q = 'Д';
        }
        else if (sym == 9267){
            q = 'Ж';
        }
        else if (sym == 9268){
            q = 'З';
        }
        else if (sym == 66589){
            q = 'И';
        }
        else if (sym == 9538){
            q = 'Й';
        }
        else if (sym == 2169){
            q = 'К';
        }
        else if (sym == 2170){
            q = 'М';
        }
        else if (sym == 2172){
            q = 'Н';
        }
        else if (sym == 2171){
            q = 'О';
        }
        else if (sym == 2175){
            q = 'П';
        }
        else if (sym == 1216){
            q = 'Р';
        }
        else if (sym == 2094){
            q = 'С';
        }
        else if (sym == 2174){
            q = 'Т';
        }
        else if (sym == 2088){
            q = 'У';
        }
        else if (sym == 1220){
            q = 'Ф';
        }
        else if (sym == 2224){
            q = 'Х';
        }
        else if (sym == 1222){
            q = 'Ц';
        }
        else if (sym == 1223){
            q = 'Ч';
        }
        else if (sym == 2081){
            q = 'Ш';
        }
        else if (sym == 1225){
            q = 'Щ';
        }
        else if (sym == 1227){
            q = 'Ы';
        }
        else if (sym == 1228){
            q = 'Ь';
        }
        else if (sym == 1229){
            q = 'Э';
        }
        else if (sym == 1230){
            q = 'Ю';
        }
        else if (sym == 2087){
            q = 'Я';
        }
        return q;
    }
    
}