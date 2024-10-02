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
            return ctx.send(StatusCode.CREATED);

        });




    }
}
