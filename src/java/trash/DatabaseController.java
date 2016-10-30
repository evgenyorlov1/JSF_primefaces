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


@ManagedBean(name="DatabaseController", eager = true)
@SessionScoped
public class DatabaseController implements Serializable{

    private DBMS dbms;
    private String database;
    private String table;
    private String fields;
    private Map<String,String> databases;
    private Map<String,String> tables;
    private String id; 
    
    public DatabaseController() {
        out.println("DatabaseController");
        dbms = DBMS.getInstance();
    }
    
    
    
}
