package trash;

import DatabaseManager.DBMS;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import static java.lang.System.out;
import static java.lang.System.err;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.bean.SessionScoped;


@ManagedBean(name="ShowDatabasesBean", eager = true)
@SessionScoped
public class ShowDatabasesBean implements Serializable{

    private DBMS dbms;
    
    public ShowDatabasesBean() {
        this.dbms = DBMS.getInstance();
    }
    
    private Map<String,String> databases;
    private String size;
    
    
    public void setDatabases(Map<String, String> databases) {
        this.databases = databases;
    }

    public DBMS getDbms() {
        return dbms;
    }    
            
    
    
}
