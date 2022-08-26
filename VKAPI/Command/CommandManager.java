package VKAPI.Command;

import java.util.Arrays;
import java.util.LinkedList;

import VKAPI.JSONParse;
import VKAPI.Messages;
import VKAPI.Requests;
import VKAPI.Object.Message;

public class CommandManager extends Thread{
    LinkedList<CommandListener> commandList = new LinkedList<>();
    String notFoundCommandMessage = "Command not found use /help";
    String chatId;
    Requests req;

    private Message lastMessage = new Message();
    public CommandManager(String chatId, Requests req){
        this.chatId = chatId;
        this.req = req;
    }
    public Requests getRequests(){
        return req;
    }
    
    /**
     *  Example how to used
        commandManager.registerCommand(new CommandListener() {
            @Override
            public boolean listen(String command, String args[]) {
                if(command.equals("help")){
                    commandManager.answer("info: \n/start can start something "
                                                +"\n/getTime get current time"
                                                +"\n@Bot_Version_pre0.5");
                    return true;
                }
                return false;
            }
        });
     * @param commandListener
     */
    
    public void registerCommand(CommandListener commandListener){
        commandList.add(commandListener);
    }   
    public void removeCommand(CommandListener commandListener){
        commandList.remove(commandListener);
    }   
    /**
     * send answer to chat
     * Note: dont send message start with /command because bot can handle myself command
     * @param message 
     */
    public void answer(String message){
        Messages.sendMessage(req, chatId, System.currentTimeMillis(), message);
    }
    /**
     * handle chat message and try to get command
     */
    public void handle(){
        String JSON = Messages.getHistory(req, 0, 1, chatId);
        JSONParse.JSONObject jobj = JSONParse.JSONObject.parse(JSON);
        jobj = (JSONParse.JSONObject)jobj.get("response").getValue().value;
        JSONParse.JSONElements elements = (JSONParse.JSONElements)(jobj.get("items").getValue().value);
        Message newMessage = new Message((JSONParse.JSONObject)elements.get(0).value);
        if(newMessage.getRandomId().equals(lastMessage.getRandomId())){
            return;
        }
        lastMessage = newMessage;
        String message = lastMessage.getText();
        if(message.indexOf("\\")==1||message.indexOf("\\/")==1){
            message = message.substring(3, message.length()-1);
            String args[] = message.split(" ");
            String command = args[0].toLowerCase();
            args = Arrays.copyOfRange(args, 1, args.length);
            boolean handle = false;
            for(CommandListener i:commandList){
                if(i.listen(command,args)){
                    handle = true;
                    break;
                }
            }
            if(!handle){
                answer(notFoundCommandMessage);
            }
        }
    }

    @Override
    public void run(){
        while(true){
            handle();
            try {
                sleep(999);
            } catch (Exception e) {
                break;
            }
        }
    }
}
