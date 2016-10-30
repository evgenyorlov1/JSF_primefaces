import DatabaseManager.DBMS;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import static java.lang.System.out;
import static java.lang.System.err;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.event.ValueChangeEvent;


@ManagedBean(name="InsertBean", eager = true)
@RequestScoped
public class InsertBean implements Serializable{

    private DBMS dbms;
    private String database;
    private String table;
    private Map<String,String> databases;
    private Map<String,String> tables;
    private String keyValue;
    
    
    public InsertBean() {
        out.println("InsertBean");
        dbms = DBMS.getInstance();
        if(!dbms.show_dbs().isEmpty())
            database = dbms.show_dbs().get(0);
    }
    
    public String getKeyValue() {
        out.println("InsertBean.getKeyValue");
        return keyValue;
    }
    
    public void setKeyValue(String keyValue) {
        out.println("InsertBean.setKeyValue");
        this.keyValue = keyValue;
    }    
    
    public Map<String, String> getDatabases() {
        out.println("InsertBean.getDatabases");
        databases = new LinkedHashMap<String,String>();
        try {
            ArrayList<String> dbs = dbms.show_dbs();
            for(String db : dbs) {
                databases.put(db, db);
            }
        } catch(Exception e) {err.println("InsertBean.getDatabases.error: " + e);}
        return databases;
    }

    public Map<String, String> getTables() {
        out.println("InsertBean.getTables");
        tables = new LinkedHashMap<String,String>();
        try {
            ArrayList<String> tbs = dbms.show_tables(database);
            for(String tb : tbs) {
                tables.put(tb, tb);
            }
        } catch(Exception e) {err.println("InsertBean.getTables.error: " + e);}     
        return tables;
    }
      
    public void setDatabase(String database) {
        out.println("InsertBean.setDatabase");
        this.database = database;
    }

    public String getDatabase() {
        out.println("InsertBean.getDatabase");
        return database;
    }
    
    public void setTable(String table) {
        out.println("InsertBean.setTable");
        this.table = table;
    }    

    public String getTable() {
        out.println("InsertBean.getTable");
        return table;
    }    
    
    public void insert() {
        out.println("InsertBean.insert");
        ArrayList<String[]> kv = new ArrayList<>();
        try {            
            for(String ko : keyValue.split(";")) {
                kv.add(ko.split("="));
            }
            out.println("INSERT PRE:" + dbms.count(database, table));
            dbms.insert(database, table, kv);
            out.println("INSERT AFTER:" + dbms.count(database, table));
        } catch(Exception e) {err.println("InsertBean.insert.error: " + e);}        
        this.keyValue=null;
    }
    
    public void valueChanged(ValueChangeEvent event) {
        out.println("InsertBean.valueChanged");
        out.println(database);
    }
                 
}