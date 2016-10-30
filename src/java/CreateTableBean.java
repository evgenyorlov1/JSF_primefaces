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


@ManagedBean(name="CreateTableBean", eager = true)
@SessionScoped
public class CreateTableBean implements Serializable{
    
    private DBMS dbms;
    
    public CreateTableBean() {
        out.println("CreateTableBean");
        dbms = DBMS.getInstance();        
    }
    
    private String database;
    private String table;
    private String fields;
    private Map<String,String> databases;    
    
    
    public void setFields(String fields) {
        out.println("CreateTableBean.setkeyType");
        this.fields = fields;
    }
    
    public String getFields() {
        out.println("CreateTableBean.getKeyType");
        return this.fields;
    }
    
    public String getTable() {
        out.println("CreateTableBean.getTable");
        return table;
    }
    
    public void setTable(String table) {
        out.println("CreateTableBean.setTable");
        this.table = table;
    }
    
    public String getDatabase() {
        out.println("CreateTableBean.getDatabase");
        return database;
    }
    
    public void setDatabase(String database) {
        out.println("CreateTableBean.setDatabase");
        this.database = database;
    }
    
    public Map<String,String> getDatabases() {
        out.println("CreateTableBean.getDatabases");
        databases = new LinkedHashMap<String,String>();
        ArrayList<String> dbs = dbms.show_dbs();
        for(String db : dbs) {
            databases.put(db, db);
        }                
        return databases;
    }
    
    public void createTable() {
        out.println("CreateTableBean.createTable");        
        ArrayList<String[]> kt = new ArrayList<>();
        fields = fields.replace(" ", "");        
        for(String t : fields.split(";")) {
            kt.add(t.split("="));
        }               
        try {
            dbms.create_table(database, table, kt);
        } catch(Exception e) {err.println("CreateTableBean.createTable.error: " + e);}
        out.println("CreateTableBean tbs size: " + dbms.show_tables(database).size());
        this.database = null;
        this.table = null;
        this.fields = null;
        this.databases = null;
    }
}
