package VKAPI.Object;

import VKAPI.JSONParse;

public class Post {
    int id;
    int from_id;
    int date;
    int owner_id;
    boolean marked_as_ads;
    String text;
    String attachments;
    
    public Post(){}
    public Post(JSONParse.JSONObject jObj){
        this.id = (int)jObj.get("id").getValue().value;
        this.from_id = (int)jObj.get("from_id").getValue().value;
        this.date = (int)jObj.get("date").getValue().value;
        this.owner_id = (int)jObj.get("owner_id").getValue().value;
        this.marked_as_ads = ((int)jObj.get("marked_as_ads").getValue().value)==1;
        this.text = (String)jObj.get("text").getValue().value;
        
        this.attachments = jObj.get("attachments").getValue().toString();
    }

    public int getDate(){
        return date;
    }
    @Override
    public String toString(){
        return "id: "+id+"\n"
                +"date: "+date+"\n"
                +"attach: "+attachments;
    }
    

}
