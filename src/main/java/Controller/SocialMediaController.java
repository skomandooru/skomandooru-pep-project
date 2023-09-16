package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
/**
 *TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
        /**
         * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
         * suite must receive a Javalin object from this method.
         * @return a Javalin app object which defines the behavior of the Javalin controller.
         */
        public Javalin startAPI() {
            Javalin app = Javalin.create();
            app.get("example-endpoint", this::exampleHandler);
            app.post("register",ctx -> {
                register(ctx);
            });
            app.post("login",this::postLogin);
            app.post("messages",this::postMessage);
            app.get("messages",this::getAllMessages);
            app.get("messages/{message_id}",this::getMessageId);
            app.delete("messages/{message_id}",this::deleteMessageId);
            app.patch("messages/{message_id}",this::updateMessageId);
            app.get("accounts/{account_id}/messages",this::getAllMessagesAccountId);
    
            return app;
        }
    
        /**
         * This is an example handler for an example endpoint.
         * @param context The Javalin Context object manages information about both the HTTP request and response.
         */
        private void exampleHandler(Context context) {
            context.json("sample text");
        }
    
        private void register(Context ctx) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Account account = mapper.readValue(ctx.body().toString(), Account.class);

                if (isBlank(account.getUsername()) || isPasswordTooShort(account.getPassword())) {
                    ctx.status(400);
                    return;
                }

                Account existingAccount = AccountService.findbyusername(account.getPassword());

                Account registeredAccount = AccountService.findbyusername(account.getUsername());

                String jsonRepresentation = mapper.writeValueAsString(registeredAccount);

                ctx.status(200)
                    .json(jsonRepresentation)
                    .contentType("application/json");
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(400);
            }
        }

        private boolean isBlank(String str) {
            return str == null || str.trim().isEmpty();
        }

        private boolean isPasswordTooShort(String password) {
            return password == null || password.length() < 5;
        }
    
        private void postLogin(Context ctx) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(ctx.body(), Account.class);
                Account result = existingAccount(account);
                if(result!=null){
                    ctx.json(mapper.writeValueAsString(result));
                } else{
                    ctx.status(401);
                }
            }
    
            private Account existingAccount(Account account) {
            return null;
        }

            private void postMessage(Context ctx) throws JsonProcessingException {
                ObjectMapper mapper = new ObjectMapper();
                Message message = mapper.readValue(ctx.body(), Message.class);
                if( message.getMessage_text() !="" && message.getMessage_text().length()<255){
                    MessageService mg = new MessageService();
                    Message result = mg.creatMessage(message);
                    if(result!=null){
                        ctx.json(mapper.writeValueAsString(result));
                    } else{
                        ctx.status(400); //400
                    }
                }
                else{
                    ctx.status(400); //400
                }
            }   
            
            private void getAllMessages(Context ctx){
                MessageService mg = new MessageService();
                List<Message> result = mg.getAllMessages();
                ctx.json(result);
            }
    
            private void getMessageId(Context ctx) throws JsonProcessingException {
                MessageService mg = new MessageService();
                ObjectMapper mapper = new ObjectMapper();
                int message_id  = mapper.readValue(ctx.pathParam("message_id"), Integer.class);
                Message result = mg.getMessageId(message_id);
                if (result != null){
                    ctx.json(result);
                } else{
                    ctx.json("");
                }
               
            }
    
            private void deleteMessageId(Context ctx) throws JsonProcessingException {
                MessageService mg = new MessageService();
                ObjectMapper mapper = new ObjectMapper();
                int message_id  = mapper.readValue(ctx.pathParam("message_id"), Integer.class);
                Message result = mg.deleteMessageId(message_id);
                if(result != null){
                    ctx.json(result);
                } else{
                    ctx.json("");
                }
               
            }
    
            private void updateMessageId(Context ctx) throws JsonProcessingException {
                MessageService mg = new MessageService();
                ObjectMapper mapper = new ObjectMapper();
                int message_id  = mapper.readValue(ctx.pathParam("message_id"), Integer.class);
                Message message = mapper.readValue(ctx.body(), Message.class);
                String message_text = message.getMessage_text();
                if(message_text!="" && message_text.length()<255){
                    Message result = mg.updateMessageId(message_id,message_text);
                    if(result!=null){
                        ctx.json(mapper.writeValueAsString(result));
                    } else{
                        ctx.status(400); //400
                    }
                
                }else{
                    ctx.status(400); //400
                }
            }
    
                private void getAllMessagesAccountId(Context ctx) throws JsonProcessingException {
                    MessageService mg = new MessageService();
                    ObjectMapper mapper = new ObjectMapper();
                    int account_id  = mapper.readValue(ctx.pathParam("account_id"), Integer.class);
                    List<Message> result = mg.getAllMessagesAccountId(account_id);
                    ctx.json(result);
    
                }
        }