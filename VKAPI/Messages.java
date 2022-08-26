package VKAPI;

import java.io.File;

import VKAPI.JSONParse.JSONElements;
import VKAPI.JSONParse.JSONObject;
import VKAPI.Object.Photo;

import java.awt.image.BufferedImage;

public class Messages {
    private static String name = "messages.";
    public static String getHistory(Requests requests, int offset, int count, String user_id){
        return requests.createVKResponse(name+"getHistory?"+
                                        "offset="+offset+
                                        "&count="+count+
                                        "&user_id="+user_id);
    }
    /**
     * Send message with attachments photo
     * @param requests 
     * @param user_id 
     * @param random_id 
     * @param message text message for sending
     * @param file only photo format
     */
    public static void sendMessage(Requests requests, String user_id, long random_id, String message, File file){
        VKAPI.Object.Photo ph = new VKAPI.Object.Photo();
        String JSON = ph.getMessagesUploadServer(requests, user_id);
        JSONParse.JSONObject jObj = (JSONParse.JSONObject)(JSONParse.JSONObject.parse(JSON).get("response").getValue().value);
        String serverurl = jObj.get("upload_url").getValue().value+"";
        serverurl = serverurl.substring(0, serverurl.length());
        serverurl = Requests.useControlSym(serverurl);
        JSON = requests.sendFileOnServer(serverurl, file);
        jObj = JSONParse.JSONObject.parse(JSON);
        String server = jObj.get("server").getValue().value+"";
        String photo = jObj.get("photo").getValue().value+"";
        String hash = jObj.get("hash").getValue().value+"";
        JSON = ph.saveMessagesPhoto(requests, server, hash, photo);
        jObj = JSONParse.JSONObject.parse(JSON);
        JSONElements el = (JSONElements)jObj.get("response").getValue().value;
        ph = new Photo((JSONObject)el.get(0).value);
        sendMessage(requests, user_id, random_id, message, ph.toPost());
    }

    /**
     * Send message with attachments photo
     * @param requests 
     * @param user_id 
     * @param random_id 
     * @param message text message for sending
     * @param image 
     */
    public static void sendMessage(Requests requests, String user_id, long random_id, String message, BufferedImage image){
        VKAPI.Object.Photo ph = new VKAPI.Object.Photo();
        String JSON = ph.getMessagesUploadServer(requests, user_id);
        JSONParse.JSONObject jObj = (JSONParse.JSONObject)(JSONParse.JSONObject.parse(JSON).get("response").getValue().value);
        String serverurl = jObj.get("upload_url").getValue().value+"";
        serverurl = serverurl.substring(0, serverurl.length());
        serverurl = Requests.useControlSym(serverurl);
        JSON = requests.sendImageOnServer(serverurl, image);
        jObj = JSONParse.JSONObject.parse(JSON);
        String server = jObj.get("server").getValue().value+"";
        String photo = jObj.get("photo").getValue().value+"";
        photo = Requests.useControlSym(photo);
        photo = photo.replace('\'', '"');
        String hash = jObj.get("hash").getValue().value+"";
        JSON = ph.saveMessagesPhoto(requests, server, hash, photo);
        jObj = JSONParse.JSONObject.parse(JSON);
        JSONElements el = (JSONElements)jObj.get("response").getValue().value;
        ph = new Photo((JSONObject)el.get(0).value);
        sendMessage(requests, user_id, random_id, message, ph.toPost());
    }
    public static void sendMessage(Requests requests,String user_id, long random_id, String message, String attachment){
        message = Requests.encodeTextFromHttp(message);
        requests.createVKResponse("messages.send?"
                        + "user_id="+user_id
                        + "&random_id="+random_id
                        + "&message="+message
                        + "&attachment="+attachment
                ); 
    }
    public static void sendMessage(Requests requests,String user_id, long random_id, String message){
        message = Requests.encodeTextFromHttp(message);
        requests.createVKResponse("messages.send?"
                        + "user_id="+user_id
                        + "&random_id="+random_id
                        + "&message="+message
                ); 
    }
    /*
     * offset - Смещение, необходимое для выборки определенного подмножества бесед.
     * count - Количество сообщений, которое необходимо получить (но не более 200).
     * filter - Типы бесед, которые нужно вернуть, перечисленные через запятую.
Может принимать следующие значения:
    •all — все беседы;
    •important — беседы, отмеченные важными (доступно только в сообщениях сообществ);
    •unanswered — беседы, отмеченные непрочтенными (доступно только в сообщениях сообществ);
    •unread — беседы, в которых есть непрочтенные сообщения.
    По умолчанию будут возвращены все беседы.
     */
    public static String getConversations(Requests requests, int offset, String filter){
        return requests.createVKResponse(name+"getConversations?"
                            + "offset="+offset
                            + "&count=1"
                            + "&filter="+filter);
    }
    /*
     * message_id - Уникальное id сообщения
     */
    public static String getById(Requests requests, String message_id){
        return requests.createVKResponse(name+"getById?"
                            + "message_ids="+message_id);
    }
}
