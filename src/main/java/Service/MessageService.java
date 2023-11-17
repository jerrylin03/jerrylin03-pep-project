package Service;
import java.util.*;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {

    private MessageDAO messageDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
    }

    public Message addMessage(Message message){
        return messageDAO.addMessage(message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
    }

    public ArrayList<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id){
        return messageDAO.getMessageById(id);
    }

    public Message removeMessageById(int id){
        Message message = messageDAO.getMessageById(id);

        if(message!= null && messageDAO.removeMessageById(id)){
            return message;
        }
        else{
            return null;
        }
    }

    public Message updateMessageById(int id, String message_text){
        boolean result = messageDAO.updateMessageById(id, message_text);

        if(result){
            return messageDAO.getMessageById(id);
        }
        else{
            return null;
        }
    }

    public ArrayList<Message> getAllMessagesByUser(int account_id){
        return messageDAO.getAllMessagesByUser(account_id);
    }
    
}
