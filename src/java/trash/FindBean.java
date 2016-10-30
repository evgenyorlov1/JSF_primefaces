package trash;

import DatabaseManager.DBMS;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import static java.lang.System.out;
import static java.lang.System.err;
import static java.lang.System.out;
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


@ManagedBean(name="FindBean", eager = true)
@SessionScoped
public class FindBean implements Serializable{

    private DBMS dbms;
    private DataTable myDataTable;
    private Map<String,String> databases;
    private Map<String,String> tables;
    private String database;
    private String table;
    
    
    public FindBean() {
        out.println("FindBean");
        dbms = DBMS.getInstance();
        if(!dbms.show_dbs().isEmpty())
            database = dbms.show_dbs().get(0);
    }
    
    
    public void setDatabase(String database) {
        out.println("FindBean.setDatabase");
        this.database = database;
    }

    public String getDatabase() {
        out.println("FindBean.getDatabase");
        return database;
    }
    
    public void setTable(String table) {
        out.println("FindBean.setTable");
        this.table = table;
    }    

    public String getTable() {
        out.println("FindBean.getTable");
        return table;
    }
    
    public void setDatabases(Map<String, String> databases) {
        out.println("FindBean.setDatabases");
        this.databases = databases;
    }

    public void setTables(Map<String, String> tables) {
        out.println("FindBean.setTables");
        this.tables = tables;
    }

    public Map<String, String> getDatabases() {
        out.println("FindBean.getDatabases");
        databases = new LinkedHashMap<String,String>();
        try {
            ArrayList<String> dbs = dbms.show_dbs();
            for(String db : dbs) {
                databases.put(db, db);
            }
        } catch(Exception e) {err.println("FindBean.getDatabases.error: " + e);} 
        return databases;
    }

    public Map<String, String> getTables() {
        out.println("FindBean.getTables");
        tables = new LinkedHashMap<String,String>();
        try {
            ArrayList<String> tbs = dbms.show_tables(database);
            for(String tb : tbs) {
                tables.put(tb, tb);
            }
        } catch(Exception e) {err.println("FindBean.getTables.error: " + e);}    
        return tables;
    }  
    
    public void valueChanged(ValueChangeEvent event) {
        out.println("FindBean.valueChanged");
        out.println(database);
    }    
    
    public void find() {
        out.println("FindBean.find");
        try {
            ArrayList<ArrayList<String[]>> rows = dbms.find(database, table);
            ArrayList<String[]> md = dbms.get_metadata(database, table);
            ArrayList<String> metadata = new ArrayList<>();
            for(String[] kv : md) {
                metadata.add(kv[0]);
            }
            
            FacesContext fc = FacesContext.getCurrentInstance();
            Application application = fc.getApplication();
            ExpressionFactory ef = application.getExpressionFactory();
            ELContext elc = fc.getELContext();
            
            String[][] values = new String[rows.size()][rows.get(0).size()];
            for(int i=0; i<rows.size(); i++) {
                for(int j=0; j<rows.get(i).size(); j++) {
                    values[i][j] = rows.get(i).get(j)[1];
                }
            }     
            ArrayDataModel model = new ArrayDataModel<>(values);
            
            myDataTable = new DataTable();
            myDataTable.setValue(model);
            myDataTable.setVar("item");
            UIOutput tableTitle = new UIOutput();
            tableTitle.setValue("Table Title");
            myDataTable.getFacets().put("header", tableTitle);
            
            for(String key : metadata) {
                Column column = new Column();
                UIOutput columnTitle = new UIOutput();
                columnTitle.setValue(key);
                column.getFacets().put("header", columnTitle);        		
                myDataTable.getColumns().add(column);

                ValueExpression indexValueExp = ef.createValueExpression(elc, "#{item[0]}", Object.class);
                HtmlOutputText indexOutput = new HtmlOutputText();
                indexOutput.setValueExpression("value", indexValueExp);
                column.getChildren().add(indexOutput);
            }
            
        } catch(Exception e) {err.println("FindBean.find.error: " + e);} 
        this.database = null;
        this.table = null;       
    }
}
