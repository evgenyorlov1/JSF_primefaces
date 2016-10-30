import DatabaseManager.DBMS;
import java.io.Serializable;
import static java.lang.System.err;
import static java.lang.System.out;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;


@ManagedBean(name="SortBean", eager=true)
@SessionScoped
public class SortBean implements Serializable{
    
    private DBMS dbms;
    
    public SortBean() {
        out.println("SortBean");
        dbms = DBMS.getInstance();
        if(!dbms.show_dbs().isEmpty())
            database = dbms.show_dbs().get(0);
    }
    
    private DataTable myDataTable;
    private String keyOrder;
    private String database;
    private String table;
    private Map<String,String> databases;
    private Map<String,String> tables;

    public void setTables(Map<String, String> tables) {
        this.tables = tables;
    }
    
    public Map<String, String> getTables() {
        out.println("SortBean.getTables");
        tables = new LinkedHashMap<String,String>();
        try {
            ArrayList<String> tbs = dbms.show_tables(database);
            for(String tb : tbs) {
                tables.put(tb, tb);
            }
        } catch(Exception e) {err.println("SortBean.getTables.error: " + e);}     
        return tables;
    }
       
    public void setMyDataTable(DataTable myDataTable) {
        out.println("SortBean.setMyDataTable");
        this.myDataTable = myDataTable;
    }
        
    public DataTable getMyDataTable() {
        out.println("----------------------------");
        out.println("SortBean.getMyDataTable");
        out.println("----------------------------");
        return myDataTable;
    }   

    public String getKeyOrder() {
        out.println("SortBean.getKeyOrder");
        return keyOrder;
    }
    
    public void setKeyOrder(String keyOrder) {
        out.println("SortBean.setKeyOrder");
        this.keyOrder = keyOrder;
    }    
    
    public Map<String, String> getDatabases() {
        out.println("SortBean.getDatabases");
        databases = new LinkedHashMap<String,String>();
        try {
            ArrayList<String> dbs = dbms.show_dbs();
            for(String db : dbs) {
                databases.put(db, db);
            }
        } catch(Exception e) {err.println("SortBean.getDatabases.error: " + e);}
        return databases;
    }   
      
    public void setDatabase(String database) {
        out.println("SortBean.setDatabase");
        this.database = database;
    }

    public String getDatabase() {
        out.println("SortBean.getDatabase");
        return database;
    }

    public void setDatabases(Map<String, String> databases) {
        this.databases = databases;
    }
    
    public void setTable(String table) {
        out.println("SortBean.setTable");
        this.table = table;
    }    

    public String getTable() {
        out.println("SortBean.getTable");
        return table;
    }    
    
    public void sort() {
        out.println("SortBean.sort");
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            Application application = fc.getApplication();
            ExpressionFactory ef = application.getExpressionFactory();
            ELContext elc = fc.getELContext();
            
            
            ArrayList<String[]> md = dbms.get_metadata(database, table);
            ArrayList<String> metadata = new ArrayList<>();
            for(String[] kv : md) {
                metadata.add(kv[0]);
            }
            
            out.println("SortBean.sort STEP0");            
            String[] ko = keyOrder.split("="); 
            out.println("SortBean.sort STEP01");
            out.println("DATABASE: " + database);
            out.println("TABLE: " + table);
            out.println("KEY: " + ko[0]);
            out.println("VALUE: " + ko[1]);            
            ArrayList<ArrayList<String[]>> records = dbms.sort(database, table, ko[0], Integer.parseInt(ko[1]));
            out.println("FIND SIZE: " + records.size());
            out.println("SortBean.sort STEP1");
            
            String[][] values = new String[records.size()][records.get(0).size()];
            for(int i=0; i<records.size(); i++) {
                for(int j=0; j<records.get(i).size(); j++) {
                    values[i][j] = records.get(i).get(j)[1];
                }
            }     
            //out.println(values[0][0] + ", " + values[0][1] + ", " + values[0][2]);
            //String[][] rows = new String[][]{ { "1", "Foo" }, { "2", "Bar" },  {"3", "Baz" } };
            ArrayDataModel model = new ArrayDataModel<>(values);
            
            out.println("SortBean.sort STEP2");
            
            
            //DATABASE
            myDataTable = new DataTable();
            myDataTable.setValue(model);
            myDataTable.setVar("item");
            UIOutput tableTitle = new UIOutput();
            tableTitle.setValue("Records");
            myDataTable.getFacets().put("header", tableTitle);
            
            out.println("SortBean.sort STEP3");
            
            //COLUMNS
            int i = 0;
            for(String key : metadata) {
                Column column = new Column();
                UIOutput columnTitle = new UIOutput();
                columnTitle.setValue(key);
                column.getFacets().put("header", columnTitle);        		
                myDataTable.getColumns().add(column);
                
                out.println("SortBean.sort KEY: " + key);
                
                ValueExpression indexValueExp = ef.createValueExpression(elc, "#{item["+String.valueOf(i)+"]}", Object.class);
                HtmlOutputText indexOutput = new HtmlOutputText();
                indexOutput.setValueExpression("value", indexValueExp);
                column.getChildren().add(indexOutput);
                i++;
            }
            
            out.println("SortBean.sort FINISH");
            
        } catch(Exception e) {err.println("SortBean.sort.error: " + e);}        
        this.keyOrder=null;
    }
    
    public void valueChanged(ValueChangeEvent event) {
        out.println("SortBean.valueChanged");        
    }
    
}
