package Service;
import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDao;

    public MessageService(){
        messageDao = new MessageDAO();
    }
    public MessageService(MessageDAO messageDao){
        this.messageDao=messageDao;
    }

    public Message creatMessage(Message message) {
        Message result = messageDao.createMessage(message);
        return result;
    }

    public List<Message> getAllMessages(){
        List<Message> result = messageDao.getAllMessages();
        return result;
    }

    public Message getMessageId(int message_id){
        Message result = messageDao.getMessageId(message_id);
        return result;
    }

    public Message deleteMessageId(int message_id){
        Message result = messageDao.deleteMessageId(message_id);
        return result;
    }

    public Message updateMessageId(int message_id,String message_text){
        Message result = messageDao.updateMessageId(message_id,message_text);
        return result;
    }

    public List<Message> getAllMessagesAccountId(int account_id){
        List<Message> result = messageDao.getAllMessagesAccountId(account_id);
        return result;
    }
}
