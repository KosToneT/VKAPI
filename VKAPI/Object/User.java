package VKAPI.Object;

import VKAPI.JSONParse.JSONObject;

public class User {
    public String id;
    public String first_name;
    public String last_name;
    public String can_access_closed;
    public String is_closed;
    public User(JSONObject jObject){
        for(int i=0; i<jObject.size(); i++){
            String key = jObject.get(i).getKey();
            key = key.substring(1, key.length()-1);
            String value = jObject.get(i).getValue().toString();
            setValue(key, value);
        }
    }
    public void setValue(String key, String value){
        switch(key){
            case "id": 
                this.id = value;
                break;
            case "first_name": 
                this.first_name = value;
                break;
            case "last_name": 
                this.last_name = value;
                break;
            case "can_access_closed": 
                this.can_access_closed = value;
                break;
            case "is_closed": 
                this.is_closed = value;
                break;
            default:
                break;
        }
    }
}
