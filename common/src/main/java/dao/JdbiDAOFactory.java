package dao;

import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class JdbiDAOFactory {

    private static final String DB_USERNAME = "admin";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_Name = "cosc349_database";

    //For running with docker.
    private static String dockerUri = "jdbc:postgresql://db:5432/cosc349_database";
    private static String awsUri = "jdbc:postgresql://cosc349-database.ceagrzeyldkp.us-east-1.rds.amazonaws.com:5432/cosc349_database";
    //For running locally for testing.
    private static String jdbiUri = "jdbc:postgresql://localhost:1234/cosc349_database";

    private static HikariDataSource HIKARI_DATA_SOURCE;
    private static Jdbi JDBI;

    public static void setJdbcUri(String uri){
        if(HIKARI_DATA_SOURCE != null){
            throw new IllegalStateException("Connection has been established, cannot change URI");
        }
        jdbiUri = uri;
    }

    public static void initialisePool(){
        HIKARI_DATA_SOURCE = new HikariDataSource();
//        HIKARI_DATA_SOURCE.setJdbcUrl(dockerUri);
//        HIKARI_DATA_SOURCE.setJdbcUrl(jdbiUri);
        HIKARI_DATA_SOURCE.setJdbcUrl(awsUri);
        HIKARI_DATA_SOURCE.setUsername(DB_USERNAME);
        HIKARI_DATA_SOURCE.setPassword(DB_PASSWORD);
        JDBI = Jdbi.create(HIKARI_DATA_SOURCE);
        JDBI.installPlugin(new SqlObjectPlugin());
    }

    public static ManagerDAO getManagerDAO() {
        if (HIKARI_DATA_SOURCE == null) {
            initialisePool();
        }
        return JDBI.onDemand(ManagerJdbiDAO.class);
    }

    public static PropertyDAO getPropertyDAO() {
        if (HIKARI_DATA_SOURCE == null) {
            initialisePool();
        }
        return JDBI.onDemand(PropertyJdbiDAO.class);
    }

    public static RequestDAO getRequestDAO() {
        if (HIKARI_DATA_SOURCE == null) {
            initialisePool();
        }
        return JDBI.onDemand(RequestJdbiDAO.class);
    }

    public static TenantDAO getTenantDAO() {
        if (HIKARI_DATA_SOURCE == null) {
            initialisePool();
        }
        return JDBI.onDemand(TenantJdbiDAO.class);
    }

    public static ManagementDAO getManagementDAO() {
        if (HIKARI_DATA_SOURCE == null) {
            initialisePool();
        }
        return JDBI.onDemand(ManagementJdbiDAO.class);
    }

    public static Jdbi getJdbi() {
        if (HIKARI_DATA_SOURCE == null) {
            initialisePool();
        }
        return JDBI;
    }

    public static void closePool(){
        if(HIKARI_DATA_SOURCE != null){
            HIKARI_DATA_SOURCE.close();
        }
    }


}
