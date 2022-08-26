package VKAPI.Object;

import VKAPI.Requests;
import VKAPI.JSONParse.JSONObject;

public class Photo{

    String name = "photo";

    int height;
    String url;
    int width;
    String owner_id;
    String id;

    public Photo(){}

    public Photo(JSONObject jObject){ 
        owner_id = jObject.get("owner_id").getValue().value.toString();
        id = jObject.get("id").getValue().value.toString();
    }


    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    
    public String getName() {
        return name;
    }
    public String getUrl(){
        return url;
    }

    

    // @Override
    // public Photo parseAttach(String answer) {
    //     try {
    //         answer = ParseJSON.getArgs(name, answer);
    //     } catch (RuntimeException ex) {
    //         throw new RuntimeException("Photo is not found");
    //     }
    //     this.id = Integer.parseInt(ParseJSON.getArgs("id", answer));
    //     this.owner_id = Integer.parseInt(ParseJSON.getArgs("owner_id", answer));
        
    //     try{
    //         String sizes = ParseJSON.getArgs("sizes", answer);
    //         //String url = ParseJSON.getArgs("url", sizes);
            
    //         int end=-1;
    //         while((end = ParseJSON.findEnd(sizes, '{'))>1){
                
    //             if(height <= Integer.parseInt(ParseJSON.getArgs("height", sizes))){
    //                 height = Integer.parseInt(ParseJSON.getArgs("height", sizes));
    //                 url = ParseJSON.getArgs("url", sizes);
                    
    //                 try{
    //                     String width = ParseJSON.getArgs("width", sizes);
    //                     this.width = Integer.parseInt(width.substring(0, width.length()-1));
    //                 }catch(Exception ex){
                        
    //                 }
                    
    //             }
    //             sizes = sizes.substring(end+1);
                
                
    //         }
    //     }catch(Exception ex){
    //         System.out.println(ex);
    //     }
        

        
        
        
    //     return this;
    // }
    
    
    public String toPost(){
        return name + owner_id + "_" + id;
    }
    // @Override
    // public String toString() {
    //     return name + owner_id + "_" + id;
    // }

    public String getMessagesUploadServer(Requests requests, String peer_id){
        return requests.createVKResponse("photos.getMessagesUploadServer?"
                                        + "peer_id="+peer_id);  
    }
    public String saveMessagesPhoto(Requests requests, String server, String hash, String photo){
        return requests.createVKResponse("photos.saveMessagesPhoto?"
                                        + "photo="+photo
                                        + "&server="+server
                                        + "&hash="+hash);
    }

    public String getProfileUploadServer(Requests requests, String owner_id){
        return requests.createVKResponse("photos.getProfileUploadServer?"
                                        + "owner_id="+owner_id);  
    }
    public String saveProfilePhoto (Requests requests, String server, String hash, String photo){
        return requests.createVKResponse("photos.saveProfilePhoto?"
                                        + "photo="+photo
                                        + "&server="+server
                                        + "&hash="+hash);
    }
    public String getWallUploadServer(Requests requests, String group_id){
        return requests.createVKResponse("photos.getWallUploadServer?"
                                        + "group_id="+group_id);  
    }

    public String saveWallPhoto (Requests requests, String user_id, String server, String hash, String photo){
        return requests.createVKResponse("photos.saveWallPhoto?"
                                        + "photo="+photo
                                        + "&server="+server
                                        + "&hash="+hash
                                       );
    }

}