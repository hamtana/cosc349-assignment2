package web;

import dao.DAOFactory;
import dao.PropertyDAO;
import dao.RequestDAO;
import dao.TenantDAO;
import io.jooby.Jooby;
import io.jooby.ServerOptions;
import io.jooby.gson.GsonModule;
import io.jooby.StatusCode;
import io.jooby.handler.Cors;
import io.jooby.handler.CorsHandler;

public class Server extends Jooby {

    private static final TenantDAO tenantDAO = DAOFactory.getTenantDAO();
    private static final RequestDAO requestDAO = DAOFactory.getRequestDAO();
    private static final PropertyDAO propertyDAO = DAOFactory.getPropertyDAO();

    public Server(){
        install(new GsonModule());

        Cors cors = new Cors()
                .setOrigin("*")
                .setMethods("GET", "POST", "PUT", "DELETE")
                .setHeaders("*");
        use(new CorsHandler(cors));


        mount(new TenantModule(tenantDAO));
        mount(new RequestModule(requestDAO));
        mount(new PropertyModule(propertyDAO));

        error(StatusCode.SERVER_ERROR, (ctx, cause, code) -> {
            ctx.getRouter().getLog().error(cause.getMessage(), cause);
            ctx.send("Server error: " + cause.getMessage());
        });
        
        mount(new StaticAssetModule());


    }

    public static void main(String [] args){
        System.out.println("\nStarting Server...");
        new Server()
                .setServerOptions(new ServerOptions().setPort(8080))
                .start();
    }


}

