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

    public ManagerModule(ManagerDAO dao) {

        //Start up the client
        snsClient = SnsClient.builder()
                .region(Region.US_EAST_1)
                .build();

        get("/api/managers/{username}", ctx -> {
            String username = ctx.path("username").value();
            Manager manager = dao.getManagerByUsername(username);

            if (manager == null) {
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
            sendEmailNotification(manager); // Call the send email notification method.

            return ctx.send(StatusCode.CREATED);

        });

    }

    private void sendEmailNotification(Manager manager){

        String message = "Hello " + manager.getFirstName() + " " + manager.getLastName() + ",\n\n" +
                "Your manager account has been created. You can now log in to the management system using your username: " + manager.getUsername() + "\n\n" +
                "Thank you,\n" +
                "Management System Team";

        PublishRequest request = PublishRequest.builder()
                .message(message)
                .subject("New Manager Account Created")
                .topicArn(topicArn)
                .build();

        PublishResponse result = snsClient.publish(request);
        System.out.println("Message sent to topic with ID:" + result.messageId());
    }
}

