import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public abstract class TableCreator {
	
	/*
	 * If you are getting an error about ojdbc drivers. Add ojdbc jars to your project libraries.
	 * Build path .-> Libraries -> Add external jars.
	 */
	
	/*
	 * DONT CREATE A TableCreator OBJECT THIS ENTIRE CLASS IS USING STATIC METHODS. Just call the createPanelWithResultSet method. 
	 */
	
	public static void main(String[] args) {
		
	}
	
	/*
	 * You only need to call createPanelWithResultSet method. Send a connection and the sql statement.
	 * The method will return a JScrollPanel with necessary data being showed with a table.
	 * 
	 */
	public static JScrollPane createPanelWithResultSet(Connection con, String statement) {
		return new JScrollPane(resultSetToTable(getResultSet(con, statement)));
	} 
	
	private static JTable resultSetToTable(ResultSet result) {
		
		//GET COLUMN NAMES AND PUT THEM TO AN ARRAY
		ResultSetMetaData metaData;
		int columnCount = 0;
		String columnNames[] = null;
		if(result != null) {
		try {
			metaData = result.getMetaData();
			//System.out.println(metaData.getColumnName(1));
			columnCount = metaData.getColumnCount();
			columnNames = new String[columnCount];
			for(int i = 0; i < columnCount; i++) {
				columnNames[i] = metaData.getColumnName(i+1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Data array gets every row of the result set
		ArrayList<Object[]> dataArray = new ArrayList<Object[]>();
		
		//If there is a result set to work with
			try {
				//Moves the cursor to start -leave it as it is-
				result.next();
				Object[] rowData = new Object[columnCount];
				do {
					//Reads data one by one for each row. And puts it to rowData object
					for(int i = 0; i <columnCount; i++) {
						//object = result.getObject(i);
						rowData[i] = result.getObject(i+1);
						System.out.printf("| %3s |", (rowData[i] == null ? "NULL" : rowData[i].toString()));
					}
					//When finished reading a row put it to an arrayList
					dataArray.add(rowData.clone());
					
					System.out.println();
					result.next();//Move the cursor to next row
				}while(!result.isAfterLast());
				
				return new JTable(convertToArray(dataArray),columnNames);
				
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		else System.out.println("RESULT SET IS EMTPY");
		return null;
	}

	private static ResultSet getResultSet(Connection c,String s) {
		
		try {
			
			Statement stmt = c.createStatement();
			return stmt.executeQuery(s);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Could not create a statement or execute it");
		}
		
		return null;
	}
	
	private static Object[][] convertToArray(ArrayList<Object[]> a){
		//Converts an arraylist to a static array. Needed for table creation.
		Object[][] arr = new Object[a.size()][a.get(0).length];
		for(int i = 0; i<a.size(); i++) {
			arr[i] = a.get(i);
		}
		return arr;
	}
	
	
}
