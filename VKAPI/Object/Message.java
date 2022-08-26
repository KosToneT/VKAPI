package VKAPI.Object;

import VKAPI.JSONParse;

public class Message {
    String date ="";
    String from_id ="";
    String id ="";
    String out ="";
    String attachments ="";
    String conversation_message_id ="";
    String fwd_messages ="";
    String important ="";
    String is_hidden ="";
    String peer_id ="";
    String random_id ="";
    String text ="";
    public Message(){}
    public Message(JSONParse.JSONObject jObject){
        date = jObject.get("date").getValue().toString();
        from_id = jObject.get("from_id").getValue().toString();
        id = jObject.get("id").getValue().toString();
        out = jObject.get("out").getValue().toString();
        attachments = jObject.get("attachments").getValue().toString();
        conversation_message_id = jObject.get("conversation_message_id").getValue().toString();
        fwd_messages = jObject.get("fwd_messages").getValue().toString();
        important = jObject.get("important").getValue().toString();
        is_hidden = jObject.get("is_hidden").getValue().toString();
        peer_id = jObject.get("peer_id").getValue().toString();
        random_id = jObject.get("random_id").getValue().toString();
        text = jObject.get("text").getValue().toString();
    }
    public String getText(){
        return text;
    }
    public String getRandomId(){
        return random_id;
    }
}
