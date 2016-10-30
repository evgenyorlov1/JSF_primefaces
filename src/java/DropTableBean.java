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

@ManagedBean(name="DropTableBean", eager = true)
@RequestScoped
public class DropTableBean implements Serializable{
 
    private DBMS dbms;
    
    public DropTableBean() {
        out.println("DropTableBean");
        dbms = DBMS.getInstance();
        if(!dbms.show_dbs().isEmpty())
            database = dbms.show_dbs().get(0);
    }
    
    private String database;
    private String table;
    private Map<String,String> databases;
    private Map<String,String> tables;

    public Map<String, String> getDatabases() {
        databases = new LinkedHashMap<String,String>();
        try {
            ArrayList<String> dbs = dbms.show_dbs();
            for(String db : dbs) {
                databases.put(db, db);
            }
        } catch(Exception e) {err.println("DropTableBean.getDatabases.error: " + e);}        
        return databases;
    }

    public Map<String, String> getTables() {
        tables = new LinkedHashMap<String,String>();
        try {
            ArrayList<String> tbs = dbms.show_tables(database);
            for(String tb : tbs) {
                tables.put(tb, tb);
            }
        } catch(Exception e) {err.println("DropTableBean.getTables.error: " + e);}                
        return tables;
    }
      
    public void setDatabase(String database) {
        out.println("DropTableBean.setDatabase");
        this.database = database;
    }

    public String getDatabase() {
        out.println("DropTableBean.getDatabase");
        return database;
    }
    
    public void setTable(String table) {
        out.println("DropTableBean.setTable");
        this.table = table;
    }    

    public String getTable() {
        out.println("DropTableBean.getTable");
        return table;
    }    
        
    public void dropTable() {
        out.println("DropTableBean.dropTable");
        try {
            dbms.drop_table(database, table);
        } catch(Exception e) {err.println("DropTableBean.dropTable.error: " + e);}        
    }
    
    public void valueChanged(ValueChangeEvent event) {
        out.println("DropTableBean.valueChanged");
        out.println(database);
    }
        
    
}
