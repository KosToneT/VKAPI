package VKAPI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;


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
    public String SendResponse(String http){
        String answer = new String();
        try {

            URL obj = new URL(http);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            answer = response.toString(); 
            while ((inputLine = in.readLine()) != null) {
              response.append(inputLine);
            }
            in.close();
            answer = response.toString();   
        }
        catch(Exception e){
            System.out.println(e);
        }
        if(info){
            System.out.println(http);
            System.out.println(answer);
        }
        return answer;
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


            request.write(isr.readAllBytes());
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
            str = URLDecoder.decode(str,"Cp1251");
            }catch(Exception ex){
                System.out.println(ex);
            }
        
        return str;
    }
    
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
    public static char DecodeBL(int sym){
        char q = '0';
        if (sym == 1232){
            q = '??';
        }
        else if (sym == 1233){
            q = '??';
        }
        else if (sym == 2086){
            q = '??';
        }
        else if (sym == 2166){
            q = '??';
        }
        else if (sym == 2225){
            q = '??';
        }
        else if (sym == 1237){
            q = '??';
        }
        else if (sym == 9273){
            q = '??';
        }
        else if (sym == 1238){
            q = '??';
        }
        else if (sym == 1239){
            q = '??';
        }
        else if (sym == 2161){
            q = '??';
        }
        else if (sym == 9526){
            q = '??';
        }
        else if (sym == 2164){
            q = '??';
        }
        else if (sym == 1243){
            q = '??';
        }
        else if (sym == 2168){
            q = '??';
        }
        else if (sym == 2085){
            q = '??';
        }
        else if (sym == 2165){
            q = '??';
        }
        else if (sym == 2167){
            q = '??';
        }
        else if (sym == 2083){
            q = '??';
        }
        else if (sym == 2084){
            q = '??';
        }
        else if (sym == 9275){
            q = '??';
        }
        else if (sym == 9279){
            q = '??';
        }
        else if (sym == 9287){
            q = '??';
        }
        else if (sym == 9281){
            q = '??';
        }
        else if (sym == 9282){
            q = '??';
        }
        else if (sym == 9421){
            q = '??';
        }
        else if (sym == 9297){
            q = '??';
        }
        else if (sym == 2090){
            q = '??';
        }
        else if (sym == 9306){
            q = '??';
        }
        else if (sym == 2091){
            q = '??';
        }
        else if (sym == 2093){
            q = '??';
        }
        else if (sym == 2092){
            q = '??';
        }
        else if (sym == 2096){
            q = '??';
        }
        else if (sym == 2162){
            q = 'A';
        }
        else if (sym == 9272){
            q = '??';
        }
        else if (sym == 9276){
            q = '??';
        }
        else if (sym == 9277){
            q = '??';
        }
        else if (sym == 9267){
            q = '??';
        }
        else if (sym == 9268){
            q = '??';
        }
        else if (sym == 66589){
            q = '??';
        }
        else if (sym == 9538){
            q = '??';
        }
        else if (sym == 2169){
            q = '??';
        }
        else if (sym == 2170){
            q = '??';
        }
        else if (sym == 2172){
            q = '??';
        }
        else if (sym == 2171){
            q = '??';
        }
        else if (sym == 2175){
            q = '??';
        }
        else if (sym == 1216){
            q = '??';
        }
        else if (sym == 2094){
            q = '??';
        }
        else if (sym == 2174){
            q = '??';
        }
        else if (sym == 2088){
            q = '??';
        }
        else if (sym == 1220){
            q = '??';
        }
        else if (sym == 2224){
            q = '??';
        }
        else if (sym == 1222){
            q = '??';
        }
        else if (sym == 1223){
            q = '??';
        }
        else if (sym == 2081){
            q = '??';
        }
        else if (sym == 1225){
            q = '??';
        }
        else if (sym == 1227){
            q = '??';
        }
        else if (sym == 1228){
            q = '??';
        }
        else if (sym == 1229){
            q = '??';
        }
        else if (sym == 1230){
            q = '??';
        }
        else if (sym == 2087){
            q = '??';
        }
        return q;
    }
    
}