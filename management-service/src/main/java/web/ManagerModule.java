package web;

import dao.ManagerDAO;
import dao.TenantDAO;
import domain.Management;
import domain.Manager;
import domain.Tenant;
import helpers.Argon2Helper;
import io.jooby.Jooby;
import io.jooby.StatusCode;

//Amazon Imports
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.nio.CharBuffer;

public class ManagerModule extends Jooby {

    private final SnsClient snsClient;
    private final String topicArn = "arn:aws:sns:us-east-1:281765895893:registration";

    public ManagerModule(ManagerDAO dao){

        //Start up the client
        snsClient = SnsClient.builder()
                .region(Region.US_EAST_1)
                .build();

        get("/api/managers/{username}", ctx -> {
            String username = ctx.path("username").value();
            Manager manager = dao.getManagerByUsername(username);

            if (manager == null){
                return ctx.send(StatusCode.NOT_FOUND);
            } else {
                return manager;
            }
        });


        post("/api/managers", ctx -> {
            Manager manager = ctx.body().to(Manager.class);
            String password = manager.getPassword();
            CharBuffer hash = Argon2Helper.hashPasswordChar(password);
            manager.setPassword(hash.toString());

            dao.saveManager(manager);
            sendEmailNotifcation(manager); // Call the send email notifcation method.

            return ctx.send(StatusCode.CREATED);

        });




    }

    private void sendEmailNotifcation(Manager manager){

        StringBuilder emailBody = new StringBuilder();
        emailBody.append("A new manager has been registered\n\n");
        emailBody.append("First Name: ").append(manager.getFirstName()).append("\n");
        emailBody.append("Last Name: ").append(manager.getLastName()).append("\n");
        emailBody.append("Email: ").append(manager.getUsername()).append("\n");

        PublishRequest request = PublishRequest.builder()
                .message(emailBody.toString())
                .subject("New Manager Account Created")
                .topicArn(topicArn)
                .build();

        PublishResponse result = snsClient.publish(request);
        System.out.println("Message sent to topic with ID:" + result.messageId());
    }
}
