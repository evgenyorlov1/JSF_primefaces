package trash;

import DatabaseManager.DBMS;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import static java.lang.System.out;
import static java.lang.System.err;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.Application;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.model.ArrayDataModel;

import org.primefaces.component.column.Column;
import org.primefaces.component.columngroup.ColumnGroup;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.row.Row;

@ManagedBean(name="Table", eager = true)
@SessionScoped
public class Table implements Serializable{
    
    private DataTable myDataTable;
    private DBMS dbms;

    public void setMyDataTable(DataTable myDataTable) {
        this.myDataTable = myDataTable;
    }

    public DataTable getMyDataTable() {
        return myDataTable;
    }
        
    @PostConstruct
    public void init() {
        
        dbms = DBMS.getInstance();
                
        FacesContext fc = FacesContext.getCurrentInstance();
	Application application = fc.getApplication();
	ExpressionFactory ef = application.getExpressionFactory();
        ELContext elc = fc.getELContext();
		        
        //Model
        ArrayList<ArrayList<String[]>> arr = new ArrayList<>();
        ArrayList<String[]> a1 = new ArrayList<String[]>();
        a1.add(new String[] {"1","aa"});a1.add(new String[] {"2","bb"});
        a1.add(new String[] {"3","cc"});a1.add(new String[] {"4","dd"});
        arr.add(a1);
        
        ArrayList<String[]> a2 = new ArrayList<String[]>();
        a2.add(new String[] {"5","ee"});a2.add(new String[] {"6","ff"});
        a2.add(new String[] {"7","gg"});a2.add(new String[] {"8","kk"});
        arr.add(a2);
        
        ArrayList<String[]> a3 = new ArrayList<String[]>();
        a3.add(new String[] {"9","ll"});a3.add(new String[] {"10","nn"});
        a3.add(new String[] {"11","mm"});a3.add(new String[] {"12","qq"});
        arr.add(a3);
        
        ArrayList<String[]> a4 = new ArrayList<String[]>();
        a4.add(new String[] {"13","zz"});a4.add(new String[] {"14","hh"});
        a4.add(new String[] {"15","xx"});a4.add(new String[] {"16","ss"});
        arr.add(a4);
        
        ArrayList<String[]> a5 = new ArrayList<String[]>();
        a5.add(new String[] {"17","oo"});a5.add(new String[] {"18","jj"});
        a5.add(new String[] {"19","ll"});a5.add(new String[] {"20","kk"});
        arr.add(a5);
        
        String[][] values = new String[arr.size()][arr.get(0).size()];
        for(int i=0; i<arr.size(); i++) {
            for(int j=0; j<arr.get(i).size(); j++) {
                values[i][j] = arr.get(i).get(j)[1];
            }
        }      
        
        out.println("DONE");
                                
        
	String[][] rows = new String[][]{ { "1", "Foo" }, { "2", "Bar" },  {"3", "Baz" } };
        ArrayDataModel model = new ArrayDataModel<>(values);
        
        
        
        //TABLE
        myDataTable = new DataTable();
        myDataTable.setValue(model);
        myDataTable.setVar("item");
        UIOutput tableTitle = new UIOutput();
	tableTitle.setValue("Table Title");
        myDataTable.getFacets().put("header", tableTitle);
        
        //INnedx
        Column indexColumn = new Column();
        UIOutput indexColumnTitle = new UIOutput();
        indexColumnTitle.setValue("Index");
        indexColumn.getFacets().put("header", indexColumnTitle);        		
        myDataTable.getColumns().add(indexColumn);
        
        ValueExpression indexValueExp = ef.createValueExpression(elc, "#{item[0]}", Object.class);
	HtmlOutputText indexOutput = new HtmlOutputText();
	indexOutput.setValueExpression("value", indexValueExp);
        indexColumn.getChildren().add(indexOutput);	
        
        
        //NAME 
        Column nameColumn = new Column();
        UIOutput nameColumnTitle = new UIOutput();
        nameColumnTitle.setValue("Name");
        nameColumn.getFacets().put("header", nameColumnTitle);        		
        myDataTable.getColumns().add(nameColumn);
        
        ValueExpression nameValueExp = ef.createValueExpression(elc, "#{item[1]}", Object.class);
	HtmlOutputText nameOutput = new HtmlOutputText();
	nameOutput.setValueExpression("value", nameValueExp);
        nameColumn.getChildren().add( nameOutput );		
        
    }
}
