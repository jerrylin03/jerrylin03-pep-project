package Controller;

import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.*;
import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::addAccount);
        app.post("/login", this::verifyLogin);
        app.post("/messages", this::addMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::removeMessageById);
        app.patch("/messages/{message_id}", this::updateMessageById);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void addAccount(Context context) {
        // get info from request
        Account accountFromBody = context.bodyAsClass(Account.class);
        // call service layer
        if(!accountFromBody.getUsername().equals("") && accountFromBody.getPassword().length() >= 4){
            Account accountAdded = accountService.addAccount(accountFromBody);
            if(accountAdded != null){
                context.json(accountAdded).status(200);
            }
            else {
                context.status(400);
            }
        }
        else{
            context.status(400);
        }

    }

    private void verifyLogin(Context context) {
        Account accountFromBody = context.bodyAsClass(Account.class);

        Account accountVerifed = accountService.verifyLogin(accountFromBody);

        if(accountVerifed != null){
            context.json(accountVerifed).status(200);
        }
        else {
            context.status(401);
        }
    }

    private void addMessage(Context context){
        Message messageFromBody = context.bodyAsClass(Message.class);

        Account accountPosting = accountService.getAccountById(messageFromBody.getPosted_by());
        if(accountPosting != null && !messageFromBody.getMessage_text().equals("") && messageFromBody.getMessage_text().length() <= 255){
            Message messageAdded = messageService.addMessage(messageFromBody);

            if(messageAdded != null){
                context.json(messageAdded).status(200);
            }
            else{
                context.status(400);
            }
        }
        else{
            context.status(400);
        }

    }

    private void getAllMessages(Context context){
        ArrayList<Message> messages = messageService.getAllMessages();

        context.json(messages).status(200);
    }

    private void getMessageById(Context context){
        int idFromPath = Integer.parseInt(context.pathParam("message_id"));

        Message messageRetrived = messageService.getMessageById(idFromPath);

        if(messageRetrived == null){
            context.json("").status(200);
        }
        else{
            context.json(messageRetrived).status(200);
        }
    }

    private void removeMessageById(Context context){
        int idFromPath = Integer.parseInt(context.pathParam("message_id"));

        Message messageDeleted = messageService.removeMessageById(idFromPath);

        if(messageDeleted == null){
            context.status(200);
        }
        else{
            context.json(messageDeleted).status(200);
        }
    }

    private void updateMessageById(Context context){
        Message messageFromBody = context.bodyAsClass(Message.class);
        int idFromPath = Integer.parseInt(context.pathParam("message_id"));

        Message messageRetrived = messageService.getMessageById(idFromPath);
        if(messageRetrived != null && messageFromBody.getMessage_text().length() <= 255 && !messageFromBody.getMessage_text().equals("")){
            Message updatedMessage = messageService.updateMessageById(idFromPath, messageFromBody.getMessage_text());

            context.json(updatedMessage).status(200);
        }
        else{
            context.status(400);
        }
    }

    private void getAllMessagesByUser(Context context){
        int idFromPath = Integer.parseInt(context.pathParam("account_id"));

        ArrayList<Message> messages = messageService.getAllMessagesByUser(idFromPath);

        context.json(messages).status(200);
    }






}