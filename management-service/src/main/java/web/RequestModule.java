package web;

import dao.RequestDAO;
import domain.Request;
import io.jooby.Jooby;
import io.jooby.StatusCode;

import java.util.Collection;

public class RequestModule extends Jooby {

    public RequestModule(RequestDAO dao){

        get("/api/requests/{username}", ctx ->{
            String username = ctx.path("username").value();
            Collection<Request> requests = dao.getRequestByTenant(username);

            if (requests.isEmpty()){
                return ctx.send(StatusCode.NOT_FOUND);
            } else {
                return requests;
            }
        });
    }
}
