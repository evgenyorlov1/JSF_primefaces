package DatabaseManager;

import java.util.ArrayList;
import java.util.Collections;


public class DBMS {
   
    private static DBMS instance = null;        
    public static ArrayList<DataBase> databases = new ArrayList<>();                    
    
    private DBMS() {}
    
    public static DBMS getInstance() {
        if(instance == null) {
            DBMS dbms = new DBMS();
            instance = dbms;
            return instance;
        }
        return instance;
    }
        
    public ArrayList<String> show_dbs() {
        System.out.println("SingletonDBMS.show_dbs");
        ArrayList<String> databaseNames = new ArrayList<String>();                
        for(int i=0; i<databases.size(); i++) 
            databaseNames.add(databases.get(i).name);                                       
        return databaseNames;
    }

    
    public ArrayList<String> show_tables(String DBname) {
        System.out.println("SingletonDBMS.show_tables");        
        ArrayList<String> tables = new ArrayList<String>();                
        for(int i=0; i<databases.size(); i++)             
            if(databases.get(i).name.equals(DBname)) 
                for(int j=0; j<databases.get(i).tables.size(); j++)                    
                    tables.add(databases.get(i).tables.get(j).name);                                
        return tables;
    }

    
    public void drop_database(String DBname) {
        System.out.println("SingletonDBMS.drop_database");        
        for(int i=0; i<databases.size(); i++) 
            if(databases.get(i).name.equals(DBname)) 
                databases.remove(i);                                    
    }

   
    public void drop_table(String DBname, String Tname) {
        System.out.println("SingletonDBMS.drop_table");        
        for(int i=0; i<databases.size(); i++) 
            if(databases.get(i).name.equals(DBname)) 
                for(int j=0; j<databases.get(i).tables.size(); j++) 
                   if(databases.get(i).tables.get(j).name.equals(Tname)) 
                       databases.get(i).tables.remove(j);                                                                               
    }
    
    public void create_database(String DBname) {
        System.out.println("SingletonDBMS.create_database");                    
        DataBase db = new DataBase(DBname);            
        databases.add(db);            
                
    }
    
    public void create_table(String DBname, String Tname, ArrayList<String[]> keyType) {                
        System.out.println("SingletonDBMS.create_table");        
        final String[] id = new String[] {"_id", "integer"};        
        keyType.add(id);                
        for(int i=0; i<databases.size(); i++)
            if(databases.get(i).name.equals(DBname))   
                databases.get(i).tables.add(new Table(Tname, keyType));                                    
    }
       
    public ArrayList<ArrayList<String[]>> find(String DBname, String Tname) {
        System.out.println("SingletonDBMS.find");
        ArrayList<Record> recs = get_records(DBname, Tname);  
        ArrayList<ArrayList<String[]>> records = new ArrayList<>();        
        for(int i=0; i<recs.size(); i++) 
            records.add(recs.get(i).keyValue);                
        return records;
    }
    
    public ArrayList<String[]> get_metadata(String DBname, String Tname) {
        System.out.println("SingletonDBMS.get_metadata");        
        ArrayList<String[]> metadata = new ArrayList<>();                
        for(DataBase db : databases) 
            if(db.name.equals(DBname)) 
                for(Table tb : db.tables)
                    if(tb.name.equals(Tname))
                        metadata = tb.keyType;                                                                                                                    
        return metadata;
    }
        
    public ArrayList<ArrayList<String[]>> sort(String DBname, String Tname, String key, int order) {
        System.out.println("SingletonDBMS.sort");
        ArrayList<Record> recs = get_records(DBname, Tname);  
        ArrayList<ArrayList<String[]>> records = new ArrayList<>();        
        for(int i=0; i<recs.size(); i++) 
            records.add(recs.get(i).keyValue); 
        
        String type = "";        
        ArrayList<String[]> KeyType = get_metadata(DBname, Tname);                                 
        
        for(String[] keyType : KeyType)
            if(keyType[0].equals(key))
                type = keyType[1];     
        
        Comparator comparator = new Comparator(type, order);        
        ArrayList<ArrayList<String[]>> sorted = bubble_sort(recs, comparator, key);                
        return sorted;
    }    
   
    public void insert(String DBname, String Tname, ArrayList<String[]> keyValue) {
        System.out.println("SingletonDBMS.insert");        
        String[] id = new String[2];
        id[0] = "_id";        
        for(String[] row : keyValue) 
            id[1] += row[1];                        
        id[1] = String.valueOf(id[1].hashCode()); 
        System.out.println("SingletonDBMS.insert hash: " + id[1]);
        keyValue.add(id);                
        for(DataBase db : databases) 
            if(db.name.equals(DBname))
                for(Table tb : db.tables)
                    tb.insert(keyValue);                                   
    }       
    
    public boolean is_unique_name(String useState) {
        for(int i=0; i<databases.size(); i++) 
            if(databases.get(i).name.equals(useState))
                return false;        
        return true;
    }
    
    public void remove_id(String DBname, String Tname, String id) {
        System.out.println("SingletonDBMS.remove id");
        for(DataBase db : databases) {
            System.out.println("STEP 1");
            System.out.println("STEP 1 DB.NAME: " + db.name);
            System.out.println("STEP 1 DBname: " + DBname);
            if(db.name.equals(DBname))  {
                System.out.println("STEP 2");
                for(Table tb : db.tables) { 
                    System.out.println("STEP 3");
                    if(tb.name.equals(Tname)) {
                        System.out.println("STEP 4");
                        for(int i=0; i<tb.records.size(); i++)  {                            
                            System.out.println("ORIGINAL ID: " + tb.records.get(i).get_by_key("_id"));
                            System.out.println("PASSED ID: " + id);
                            if(tb.records.get(i).get_by_key("_id").equals(id)) {
                                tb.records.remove(i);    
                                System.out.println("REMOVED!!!!!!!!!!!!!!!!!!!!!!!!!!");
                            }
                        }
                    }
                }
            }
        }
    }
    
    public ArrayList<Record> get_records(String DBname, String Tname) {
        System.out.println("SingletonDBMS.get_records");        
        ArrayList<Record> rec = new ArrayList<>();        
        for(DataBase db : databases) 
            if(db.name.equals(DBname)) 
                for(Table tb : db.tables) 
                    if(tb.name.equals(Tname)) 
                        rec = tb.records;                                                                                                               
        return rec;
    }
    
    public ArrayList<ArrayList<String[]>> bubble_sort(ArrayList<Record> records, Comparator camparator, String key) {        
        for(int i=0; i<records.size(); i++) 
            for(int j=0; j<records.size(); j++)
                if(camparator.compare(records.get(i).get_by_key(key),records.get(j).get_by_key(key))) 
                    Collections.swap(records, j, i);                                   
        
        ArrayList<ArrayList<String[]>> result = new ArrayList<>();        
        for(int i=0; i<records.size(); i++) 
            result.add(records.get(i).keyValue);         
        return result;
    }
    
    public int count(String DBname, String Tname) {
        System.out.println("SingletonDBMS.count");
        int count = 0;
        for(DataBase db : databases) {
            if(db.name.equals(DBname)) {
                for(Table tb : db.tables) {
                    if(tb.name.equals(Tname)) {
                        count = tb.records.size();
                    }
                }
            }
        }
        return count;
    }        
    
}
