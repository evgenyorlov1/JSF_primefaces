import DatabaseManager.DBMS;
import java.io.Serializable;
import static java.lang.System.err;
import static java.lang.System.out;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.event.ValueChangeEvent;


@ManagedBean(name="RemoveBean", eager = true)
@RequestScoped
public class RemoveBean implements Serializable{
    
    private DBMS dbms;
    
    public RemoveBean() {
        out.println("RemoveBean");
        dbms = DBMS.getInstance();
        if(!dbms.show_dbs().isEmpty())
            database = dbms.show_dbs().get(0);
    }
    
    private String table;
    private String database;
    private String id;   
    private Map<String,String> databases;
    private Map<String,String> tables;

    
    public Map<String, String> getDatabases() {
        out.println("RemoveBean.getDatabases");
        databases = new LinkedHashMap<String,String>();
        try {
            ArrayList<String> dbs = dbms.show_dbs();
            for(String db : dbs) {
                databases.put(db, db);
            }
        } catch(Exception e) {err.println("RemoveBean.getDatabases.error: " + e);}
        return databases;
    }

    public Map<String, String> getTables() {
        out.println("RemoveBean.getTables");
        tables = new LinkedHashMap<String,String>();
        try {
            ArrayList<String> tbs = dbms.show_tables(database);
            for(String tb : tbs) {
                tables.put(tb, tb);
            }
        } catch(Exception e) {err.println("RemoveBean.getTables.error: " + e);}     
        return tables;
    }
    
    public void setDatabase(String database) {
        out.println("RemoveBean.setDatabase");
        this.database = database;
    }

    public String getDatabase() {
        out.println("RemoveBean.getDatabase");
        return database;
    }
    
    public void setTable(String table) {
        out.println("RemoveBean.setTable");
        this.table = table;
    }    

    public String getTable() {
        out.println("RemoveBean.getTable");
        return table;
    }    
    
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    
    public void remove() {
        out.println("RemoveBean.remove");
        try {
            out.println("----------------------------------------");
            out.println("----------------------------------------");
            out.println("---- PRE COUNT: " + dbms.count(database, table));
            out.println("----TABLE: " + table + " ;;; " + "DATABASE: " + database);
            out.println("----ID: " + id);
            dbms.remove_id(database, table, id);            
            out.println("----POST COUNT: " + dbms.count(database, table));
            out.println("----------------------------------------");
            out.println("----------------------------------------");
        } catch(Exception e) {err.println("RemoveBean.remove.error: "+e);}
        this.database=null;
        this.id=null;
        this.table=null;
    }
    
    public void valueChanged(ValueChangeEvent event) {
        out.println("DropTableBean.valueChanged");        
    }
    
}
