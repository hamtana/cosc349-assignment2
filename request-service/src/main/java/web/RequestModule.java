package web;

import dao.RequestDAO;
import domain.Property;
import domain.Request;
import helpers.Argon2Helper;
import io.jooby.Jooby;
import domain.Tenant;
import io.jooby.StatusCode;

import java.nio.CharBuffer;
import java.util.Collection;

public class RequestModule extends Jooby {

    public RequestModule(RequestDAO dao){

        get("/api/requests", ctx -> {
            return dao.getAllRequests();
        });

        get("/api/requests/{username}", ctx ->{
           String username = ctx.path("username").value();
           Collection<Request> requests = dao.getRequestByTenant(username);

           if (requests.isEmpty()){
               return ctx.send(StatusCode.NOT_FOUND);
           } else {
               return requests;
           }
        });


        //Pattern to update a request
        put("/api/requests/{name}", ctx -> {
            String name = ctx.path("name").value();
            Request request = ctx.body().to(Request.class);
            dao.updateRequest(request);
            return ctx.send(StatusCode.OK);
        });

        //Pattern to delete a request
        delete("/api/requests/{name}", ctx -> {
            String name = ctx.path("name").value();
            Request request = dao.getRequestByName(name);
            if (request == null){
                return ctx.send(StatusCode.NOT_FOUND);
            } else {
                dao.deleteRequest(request);
                return ctx.send(StatusCode.OK);
            }
        });


        post("/api/requests", ctx -> {
            Request request = ctx.body().to(Request.class);
//            Tenant tenant = ctx.body().to(Tenant.class);
//            Property property = ctx.body().to(Property.class);
//            request.setTenant(tenant);
//            request.setProperty(property);
            dao.createRequest(request);
            return ctx.send(StatusCode.CREATED);
        });







    }
}
