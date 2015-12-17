package airportcodes;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.*;
import au.com.bytecode.opencsv.CSVReader;
import java.util.*;

public class AirportCodes{
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    static final String USER = "username";
    static final String PASS = "password";
    public static void main(String[] argv) {
        Properties props = new Properties();
		props.setProperty("user", argv[0]);
		props.setProperty("password", argv[1]);
		props.setProperty("ssl","true");
        readCsv();
        readCsvUsingLoad();
    }
    private static void readCsv(){
       
        try (CSVReader reader = new CSVReader(new FileReader("airportcodes.csv"), ','); 
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);){
                Class<?> driver = Class.forName(JDBC_DRIVER);
                String insertQuery = "Insert into airportcodes(airportcodes,cityname,airportcode,airportname , countryname, countryabbrev, areacode) values (null,?,?,?,?,?,?)";
                PreparedStatement pstmt = connection.prepareStatement(insertQuery);
                String[] rowData = null;
                int i = 0;
                while((rowData = reader.readNext()) != null){
                    for (String data : rowData){
                        pstmt.setString((i % 3) + 1, data);
                        if (++i % 3 == 0)
                            pstmt.addBatch();// add batch
                        if (i % 30 == 0)// insert when the batch size is 10
                            pstmt.executeBatch();
                    }
                }
                System.out.println("Data Successfully Uploaded");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        private static void readCsvUsingLoad(){
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
                String loadQuery = "LOAD DATA LOCAL INFILE '" + "airportcodes.csv" + "' INTO TABLE airportcodes FIELDS TERMINATED BY ','" + " LINES TERMINATED BY '\n' (cityname,airportcode,airportname , countryname, countryabbrev, areacode) ";
                System.out.println(loadQuery);
                Statement stmt = connection.createStatement();
                stmt.execute(loadQuery);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
