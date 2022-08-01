package VKAPI.Command;

public interface CommandListener {
    /**
     * 
     * @param command without / or \ , command only lowercase
     * @param args any arguments
     * @return if handle command return true else false
     */
    boolean listen(String command, String args[]);
}
