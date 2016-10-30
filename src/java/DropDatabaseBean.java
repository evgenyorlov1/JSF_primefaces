import DatabaseManager.DBMS;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import static java.lang.System.out;
import static java.lang.System.err;


@ManagedBean(name="DropDatabaseBean", eager = true)
@RequestScoped
public class DropDatabaseBean implements Serializable{
    
    private DBMS dbms;
    
    public DropDatabaseBean() {
        out.println("DropDatabaseBean");
        dbms = DBMS.getInstance();
    }
    
    private String database;
    
    public String getDatabase() {
        out.println("DropDatabaseBean.getDatabase");
        return database;
    }
    
    public void setDatabase(String database) {
        out.println("DropDatabaseBean.setDatabase");
        this.database = database;
    }
    
    public void dropDatabase() {
        out.println("DropDatabaseBean.dropDatabase");
        try {
            dbms.drop_database(database);
        } catch(Exception e) {err.println("DropDatabaseBean.dropDatabase.error: " + e);}  
        database = null;
    }
}
