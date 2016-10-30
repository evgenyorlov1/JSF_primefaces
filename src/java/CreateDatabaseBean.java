import DatabaseManager.DBMS;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import static java.lang.System.out;
import static java.lang.System.err;
import javax.faces.bean.SessionScoped;


@ManagedBean(name="CreateDatabaseBean", eager = true)
@SessionScoped
public class CreateDatabaseBean implements Serializable{
    
    private DBMS dbms;
    
    public CreateDatabaseBean() {
        out.println("CreateDatabaseBean");
        dbms = DBMS.getInstance();
    }      
    
    private String databaseName;
    
    public String getDatabaseName() {
        out.println("CreateDatabaseBean.getDatabaseName");
        return databaseName;
    }
    
    public void setDatabaseName(String databaseName) {
        out.println("CreateDatabaseBean.setDatabaseName");
        this.databaseName = databaseName; 
    }
    
    public void createDatabase() {
        out.println("CreateDatabaseBean.createDatabase");
        try {
            dbms.create_database(databaseName); 
            out.println("CreateDatabaseBean table size");
            this.databaseName = null;
        } catch(Exception e) {err.println("CreateDatabaseBean.createDatabase.error: " + e);}
    }
        
    
}   
