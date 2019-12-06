package DBManagementLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import DBManagementLayer.myconnector;

public class UserQuery implements IUserQuery {

    private static UserQuery userQuery;
    myconnector connection = null;
    private String emailAddress = null;

    private UserQuery(myconnector connection) throws SQLException {
        this.connection = connection;

        // setDataToCache();
    }

    public static UserQuery getInstance(myconnector connection) {

        if (userQuery == null) {
            try {
                userQuery = new UserQuery(connection);
            } catch (SQLException ex) {
                Logger.getLogger(UserQuery.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userQuery;
    }

    @Override
    public String createUser(String email, String userName, String password, String firstName, String lastName,
            String phoneNumber) {
        try {
            if (email.equals("") || userName.equals("") || password.equals("") || firstName.equals("")
                    || lastName.equals("") || phoneNumber.equals("")) {
                return "Parameter Error";// not empty
            }

            if (checkExistPrimaryKey("Users", email, 1)) {
                return "Email Error";// already exist
            }
            int userType = firstUser();// new number
            String sql = "INSERT INTO Users VALUES ( \'" + email + "\' , \' " + userName + "\' ,  AES_ENCRYPT(\'"
                    + password + "\', 'secret') , \'" + firstName + "\' , \'" + lastName + "\' , \'" + phoneNumber
                    + "\' , \'" + userType + "\' );";
            System.out.println(sql);
            connection.stmt.executeUpdate(sql);
            emailAddress = email;
            System.out.println("Success");
            return "";
        } catch (SQLException e) {
            return "Error Occured During Transaction";
        }
    }

    @Override
    public String getEmail() {
        return emailAddress;
    }

    private boolean checkPassword(String email, String password) throws SQLException {
        String sql = "SELECT CAST( AES_DECRYPT(`Password`, 'secret') AS CHAR(50)) FROM  USERS"
                + " where Email_Address=\'" + email + "\' ;";
        ResultSet resultSet = connection.stmt.executeQuery(sql);
        while (resultSet.next()) {
            if (resultSet.getString(1).equals(password)) {
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean logIn(String email, String password) throws SQLException {
        if (checkExistPrimaryKey("users", email, 1) && checkPassword(email, password)) {
            emailAddress = email;
            System.out.println(emailAddress);
            return true;
        }
        return false;
    }

    @Override
    public void logOut(String email) throws SQLException {
        // if (checkExistPrimaryKey("Users", email, 1)) {
        // removeShoppingCart(email);
        // }
    }

    @Override
    public ResultSet getUserData(String email) throws SQLException {
        String sql = "SELECT * from users where Email_Address = \'" + email + "\' ;";
        System.out.println(sql);
        ResultSet resultSet = connection.stmt.executeQuery(sql);
        // DBTablePrinter.printResultSet(resultSet);

        return resultSet;
    }

    public ResultSet getDrugData(String drugID) throws SQLException {
        String sql = "SELECT * from DRUGS where drugID = \'" + drugID + "\' ;";
        System.out.println(sql);
        ResultSet resultSet = connection.stmt.executeQuery(sql);
        // DBTablePrinter.printResultSet(resultSet);

        return resultSet;
    }

    public void deleteDrug(String drugID) throws SQLException {

        String sql = "DELETE from DRUGS where drugID = \'" + drugID + "\' ;";
        System.out.println(sql);
        connection.stmt.executeUpdate(sql);
        // DBTablePrinter.printResultSet(resultSet);
    }

    public String getPassword(String email) throws SQLException {
        String sql = "SELECT CAST( AES_DECRYPT(`Password`, 'secret') AS CHAR(50)) FROM  USERS"
                + " where Email_Address=\'" + email + "\' ;";
        ResultSet resultSet = connection.stmt.executeQuery(sql);
        while (resultSet.next()) {
            return resultSet.getString(1);
        }

        return "";
    }

    @Override
    public int getUserType(String email) throws SQLException {
        int userType = 0;
        String sql = "SELECT * from users where Email_Address = \'" + email + "\' ;";
        ResultSet resultSet = connection.stmt.executeQuery(sql);
        while (resultSet.next()) {
            userType = resultSet.getInt("User_Type");
        }
        return userType;
    }

    @Override
    public String addNewDrug(String drugID, String drugName, String classification, String concentration, String price,
            String amount, String type, String batchID, String expiredDate, String quantity, String producedDate) {
        try {
            if (drugID.equals("") || drugName.equals("") || concentration.equals("") || amount.equals("")
                    || classification.equals("") || price.equals("") || type.equals("") || batchID.equals("")
                    || expiredDate.equals("") || quantity.equals("") || producedDate.equals("")) {
                return "Parameter Error";
            }
            if (checkExistPrimaryKey("DRUGS", drugID, 1)) {
                return "DRUG ID already exist";
            }
            if (!checkNumber(quantity) || !checkNumber(concentration) || !checkNumber(amount) || !checkNumber(price)) {
                return "Parameter Error";
            }

            String sql = "INSERT INTO DRUGS VALUES (\'" + drugID + "\' , \'" + drugName + "\' , \'" + classification
                    + "\' , " + concentration + " , " + price + " , " + amount + " ,\'" + type + "\') ;";
            System.out.println(sql);
            connection.stmt.executeUpdate(sql);
            // cacheQuantity.put(drugID, Integer.parseInt(quantity));
            // String[] authorsNames = authors.split(",");
            // for (int i = 0; i < authorsNames.length; i++) {
            sql = "INSERT INTO DrugBatch VALUES (\'" + expiredDate + "\' , \'" + drugID + "\' , \'" + producedDate
                    + "\', " + quantity + ", \'" + batchID + "\' );";
            connection.stmt.executeUpdate(sql);

            return "";

        } catch (SQLException ex) {
            Logger.getLogger(UserQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";

    }

    @Override
    public String editPersonalInformation(String email, String userName, String password, String firstName,
            String lastName, String phoneNumber) {
        try {
            String sql = "UPDATE Users SET ";
            //
            if (email.equals("") || userName.equals("") || password.equals("") || firstName.equals("")
                    || lastName.equals("") || phoneNumber.equals("")) {
                return "Parameter Error";// not empty
            }
            sql = sql + "userName = \'" + userName + "\' ,  password = AES_ENCRYPT(\'" + password
                    + "\', 'secret') , firstName= \'" + firstName + "\' ,lastName =  \'" + lastName
                    + "\' ,phoneNumber =  \'" + phoneNumber + "\' where Email_Address = \'" + email + "\';";
            System.out.println(sql);
            connection.stmt.executeUpdate(sql);
            emailAddress = email;
            return "";
        } catch (SQLException e) {
            return "Error Occured During Transaction";
        }
    }

    public String editDrug(String drugID, String drugName, String classification, String concentration, String price,
            String amount, String type) {
        try {
            String sql = "UPDATE DRUGS SET ";
            if (drugID.equals("") || drugName.equals("") || classification.equals("") || concentration.equals("")
                    || price.equals("") || amount.equals("") || type.equals("")) {
                return "Parameter Error";// not empty
            }
            if (!checkNumber(concentration) || !checkNumber(amount) || !checkNumber(price)) {
                return "Parameter Error";
            }

            sql = sql + "drugName = \'" + drugName + "\' , classification  = \'" + classification
                    + "\', concentration= " + concentration + " ,price =  " + price + " ,amount =  " + amount
                    + " ,type = \'" + type + "\'  where drugID = \'" + drugID + "\';";
            System.out.println(sql);
            connection.stmt.executeUpdate(sql);
            return "";
        } catch (SQLException e) {
            return "Error Occured During Transaction";
        }
    }

    private int firstUser() throws SQLException {
        ResultSet resultSet = connection.stmt.executeQuery("SELECT * FROM Users;");
        while (resultSet.next()) {
            return 2;
        }

        return 1;
    }

    private boolean checkExistPrimaryKey(String tableName, String value, int attributeNumber) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " ;";
        System.out.println(sql);
        ResultSet resultSet = connection.stmt.executeQuery(sql);
        while (resultSet.next()) {
            if (resultSet.getString(attributeNumber).equals(value)) {
                System.out.println("Emailtrue");
                return true;
            }
        }
        return false;
    }

    private boolean checkNumber(String s) {
        if (s.length() == 0) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) > 57 || s.charAt(i) < 48) {
                return false;
            }
        }
        return true;
    }

    public boolean confirmOdrer(String drugID) throws SQLException {
        String sql = "select * from Orders where drugID = \'" + drugID + "\' ;";
        ResultSet resultSet = connection.stmt.executeQuery(sql);
        while (resultSet.next()) {

            sql = "delete from Orders where drugID = \'" + drugID + "\' ;";
            connection.stmt.executeUpdate(sql);
            return true;
        }
        return false;
    }

//    public String reduceQuantity(String drugID, String Quantity) throws SQLException {
//        if (!checkNumber(Quantity)) {
//            return "Quantity Should Be a Number!";
//        };
//        int quantity = Integer.parseInt(Quantity);
//        String sql = "select sum(quantity) from DrugBatch where drugID = \'" + drugID
//                + "\' and expiredDate > curdate();";
//        ResultSet resultSet = connection.stmt.executeQuery(sql);
//        while (resultSet.next()) {
//            int q = resultSet.getInt(1);
//            System.out.println("enter=" + q);
//            if (q < quantity) {
//                return "Not Enough Quantity";
//            }
//        }
//        while (quantity > 0) {
//            sql = "select quantity, batchID from DrugBatch where quantity > 0 and drugID = \'" + drugID
//                    + "\' and expiredDate > curdate() ORDER BY expiredDate limit 1;";
//            resultSet = connection.stmt.executeQuery(sql);
//            while (resultSet.next()) {
//                int q = resultSet.getInt(1);
//                String batch = resultSet.getString(2);
//                if (q > quantity) {
//                    int newQuantity = q - quantity;
//                    sql = "Update DrugBatch set quantity = " + newQuantity + " where drugID = \'" + drugID
//                            + "\' and batchID = \'" + batch + "\' ;";
//                    connection.stmt.executeUpdate(sql);
//                    quantity = 0;
//                } else {
//                    quantity = quantity - q;
//                    sql = "Update DrugBatch set quantity = " + 0 + " where drugID = \'" + drugID + "\' and batchID = \'"
//                            + batch + "\' ;";
//                    connection.stmt.executeUpdate(sql);
//                }
//                break;
//            }
//        }
//        return "";
//    }
    public String reduceQuantity(String email, String drugID, String quantit) throws SQLException {
        int sold = 0;
        String sql = "select sum(quantity) from DrugBatch where drugID = \'" + drugID
                + "\' and expiredDate > curdate();";
        ResultSet resultSet = connection.stmt.executeQuery(sql);
        if (!checkNumber(quantit)) {
            return "Parameter Error";
        }
        int quantity = Integer.parseInt(quantit);
        while (resultSet.next()) {
            int q = resultSet.getInt(1);
            System.out.println("enter=" + q);
            if (q < quantity) {
                return "Not Enough quantity in pharmacy";
            }
        }
        sql = "select price from Drugs where drugID = \'" + drugID + "\';";
        resultSet = connection.stmt.executeQuery(sql);
        int price = 0;
        while (resultSet.next()) {
            price = resultSet.getInt(1);
            break;
        }
        System.out.println(price);
        while (quantity > 0) {
            sql = "select quantity, batchID from DrugBatch where quantity > 0 and drugID = \'" + drugID
                    + "\' and expiredDate > curdate() limit 1;";
            resultSet = connection.stmt.executeQuery(sql);
            while (resultSet.next()) {
                int q = resultSet.getInt(1);
                String batch = resultSet.getString(2);
                int newQuantity;
                if (q > quantity) {
                    newQuantity = q - quantity;
                    sold = quantity;
                    sql = "Update DrugBatch set quantity = " + newQuantity + " where drugID = \'" + drugID
                            + "\' and batchID = \'" + batch + "\' ;";
                    connection.stmt.executeUpdate(sql);
                    quantity = 0;
                } else {
                    
                    newQuantity = quantity - q;
                    sold += q;
                    quantity = newQuantity;
                    sql = "Update DrugBatch set quantity = " + 0 + " where drugID = \'" + drugID + "\' and batchID = \'"
                            + batch + "\' ;";
                    connection.stmt.executeUpdate(sql);
                }
                sql = "select * from Sells where drugID = \'" + drugID + "\' and  Email_Address=\'" + email + "\';";
                ResultSet resultSet2 = connection.stmt.executeQuery(sql);
                boolean updated = false;
                while (resultSet2.next()) {
                    int qq = resultSet2.getInt(1);
                    qq += sold;
                    sql = "update Sells set quantity =" + qq + " where drugID = \'" + drugID
                            + "\' and Email_Address= \'" + email + "\';";
                    connection.stmt.executeUpdate(sql);
                    updated = true;
                    break;
                }
                if (updated) {
                    break;
                }
                sql = "insert into Sells values (" + sold + "," + price + ",\'" + drugID + "\','" + email
                        + "\');";
                connection.stmt.executeUpdate(sql);
                break;
            }
        }
        return "";
    }

    public String addBatch(String drugID, String batchID, String quantity, String expiredDate, String productedDate)
            throws SQLException {
        if (!checkNumber(quantity)) {
            return "Parameter Error";
        }
        if (!checkExistPrimaryKey("Drugs", drugID, 1)) {
            return "Drug does't exist in the pharmacy data please insert it's data before adding any batch";
        }
        if (checkExistPrimaryKey("DrugBatch", batchID, 5)) {
            return "Batch ID is already exist ";
        }

        String sql = "insert into DrugBatch values (\'" + expiredDate + "\',\'" + drugID + "\',\'" + productedDate
                + "\'," + quantity + ",\'" + batchID + "\');";
        connection.stmt.executeUpdate(sql);
        return "";
    }

    public ArrayList<TotalityObject> viewTopDrugsSells() {
        ResultSet rs = null;
        ArrayList<TotalityObject> list = new ArrayList();
        try {
            String sql = "select sum(quantity) as total from Sells where drugID not in (select drugID from(select sum(quantity) as total , drugID from Sells group by drugID order by total desc limit 5) temp)";
            rs = connection.stmt.executeQuery(sql);
            rs.next();
            int otherTotal = rs.getInt("total");
            sql = "select sum(quantity) as total , drugID from Sells group by drugID order by total desc limit 5;";
            rs = connection.stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new TotalityObject(rs.getInt("total"), rs.getString("drugID")));
            }
            list.add(new TotalityObject(otherTotal, "Others"));

            return list;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;

    }

    public static class TotalityObject {

        private int total;

        private String ID;

        public TotalityObject(int total, String ID) {
            this.total = total;
            this.ID = ID;
        }

        public int getTotal() {
            return total;
        }

        public String getID() {
            return ID;
        }
    }

    public ArrayList<TotalityObject> viewTopFiveUsersSells() {
        ArrayList<TotalityObject> list = new ArrayList();

        ResultSet rs = null;
        try {
            String sql = "select sum(quantity*price) as total from Sells where Email_Address not in (select Email_Address from(select sum(quantity*price) as total , Email_Address from Sells group by Email_Address order by total desc limit 5) temp)";
            rs = connection.stmt.executeQuery(sql);
            rs.next();
            int otherTotal = rs.getInt("total");
           
            sql = "select sum(quantity*price) as total , Email_Address from Sells group by Email_Address order by total desc limit 5;";
            rs = connection.stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new TotalityObject(rs.getInt("total"), rs.getString("Email_Address")));
            }
            list.add(new TotalityObject(otherTotal, "Others"));
            return list;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

}
