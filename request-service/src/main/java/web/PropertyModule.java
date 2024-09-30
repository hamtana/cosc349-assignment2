package web;

import dao.PropertyDAO;
import dao.RequestDAO;
import domain.Property;
import io.jooby.Jooby;

public class PropertyModule extends Jooby {
    //Retrieve properties by tenant

    public PropertyModule(PropertyDAO dao){

        get("/api/properties/tenant/{username}", ctx -> {
            String username = ctx.path("username").value();
            Property property = dao.getPropertyByTenantUsername(username);
            if(property == null){
                return ctx.send("No property found for tenant: " + username);
            } else {
                return property;
            }
        });

    }


}
