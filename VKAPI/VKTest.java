package VKAPI;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;

import VKAPI.Command.CommandListener;
import VKAPI.Command.CommandManager;
import VKAPI.JSONParse.JSONElements;
import VKAPI.JSONParse.JSONObject;
import VKAPI.Object.Photo;
import VKAPI.Object.User;

//TODO: need add update listen interface for command from users
public class VKTest {
    public static void main(String args[]){
        String TOKEN = "TOKEN";
        Requests req = new Requests(TOKEN);

       

        //Wall.post(req, owner_id, friends_only, from_group, message, attachments, signed, guid, close_comments)
        int id = Account.getProfileId(req);
        Messages.sendMessage(req, id+"", System.currentTimeMillis(), "Бот запущен");
        CommandManager commandManager = new CommandManager(id+"", req);
        commandManager.start();


        //Messages.sendMessageWithFile(req, id+"", System.currentTimeMillis(), "", new File("C:\\Users\\Overthink\\Desktop\\background1.jpg"));

        //Account.setImageProfile(req, id+"", new BufferedImage(600,600, BufferedImage.TYPE_INT_ARGB));
        // CommandManager commandManager = new CommandManager(id+"", req);
        // commandManager.start();
        commandManager.registerCommand(new CommandListener() {
            @Override
            public boolean listen(String command, String args[]) {
                if(command.equals("help")){
                    commandManager.answer("info: \n/start can start something "
                                                +"\n/getTime get current time"
                                                + "\n/getInfo get info about user"
                                                + "\n/screen take screenshot or"
                                                + "\n/screen x y width height "
                                                + "\n/shutdown turn off pc"
                                                + "\n/command args[] enter args to console"
                                                +"\n@Bot_Version_pre0.5");
                    return true;
                }
                return false;
            }
        });
        commandManager.registerCommand(new CommandListener() {
            @Override
            public boolean listen(String command, String args[]) {
                if(command.equals("start")){
                    commandManager.answer("what do you want to start?");
                    return true;
                }
                return false;
            }
        });
        commandManager.registerCommand(new CommandListener() {
            @Override
            public boolean listen(String command, String args[]) {
                if(command.equals("gettime")){
                    Calendar date = Calendar.getInstance();
                    commandManager.answer(String.format("Время сейчас: %02d:%02d",date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE)));
                    return true;
                }
                return false;
            }
        });
        commandManager.registerCommand(new CommandListener() {
            @Override
            public boolean listen(String command, String args[]) {
                if(command.equals("getinfo")){
                    if(args.length==0){
                        return false;
                    }
                    String JSON = Users.get(commandManager.getRequests(), args[0]);
                    System.out.println(JSON);
                    JSONElements elements = (JSONElements)JSONObject.parse(JSON).get("response").getValue().value;
                    if(elements.size()==0){
                        commandManager.answer("Нет пользователя с таким id или сокращением");
                        return true;
                    }
                    User user =new User((JSONObject)elements.get(0).value);
                    String str = "";
                    str += "Имя: "+ user.first_name + "\n";
                    str += "Фамилия: " + user.last_name + "\n";
                    str += "Айди: "+ user.id + "\n";
                    commandManager.answer(str);
                    return true;
                }
                return false;
            }
        });
        commandManager.registerCommand(new CommandListener() {
            @Override
            public boolean listen(String command, String[] args) {
                if(command.equals("screen")){
                    int x= 0;
                    int y = 0;
                    int width = 1366+1280;
                    int height = 1024;
                    if(args.length>=4){
                        x = Integer.parseInt(args[0]);
                        y = Integer.parseInt(args[1]);
                        width = Integer.parseInt(args[2]);
                        height = Integer.parseInt(args[3]);
                    }
                    try {
                        Robot robot = new Robot();
                        BufferedImage screen = robot.createScreenCapture(new Rectangle(x,y,width,height));
                        Messages.sendMessage(req, id+"", System.currentTimeMillis(), "screenshot :", screen);
                    } catch (Exception e) {
                        Messages.sendMessage(req, id+"", System.currentTimeMillis(), e.getMessage());
                    }
                    return true;
                }
                return false;
            }
        });
        commandManager.registerCommand(new CommandListener() {
            @Override
            public boolean listen(String command, String[] args) {
                if(command.equals("shutdown")){
                    try {
                        Runtime.getRuntime().exec("shutdown /s");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
        commandManager.registerCommand(new CommandListener() {
            @Override
            public boolean listen(String command, String[] args) {
                if(command.equals("command")){
                    String arg = "";
                    for(String i:args){
                        arg+= i+" ";
                    }
                    try {
                        Runtime.getRuntime().exec(arg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

        // String JSON = Messages.getHistory(req, 0, 10, id+"");

        
        // JSONObject jobj = JSONObject.parse(JSON);
        // jobj = (JSONObject)jobj.get("response").getValue().value;
        // JSONParse.JSONElements elements = (JSONParse.JSONElements)(jobj.get("items").getValue().value);
        // for(int i=0; i<elements.size(); i++){
        //     Message message = new Message((JSONObject)elements.get(i).value);
        //     System.out.println(message.getText());
        // }

    }
}
