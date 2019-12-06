package DBManagementLayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public interface IUserQuery {

    public String createUser(String email, String userName, String password, String firstName, String lastName,
            String phoneNumber);

    public boolean logIn(String email, String password) throws SQLException;

    public void logOut(String email) throws SQLException;

    public String getEmail();

    public String addNewDrug(String drugID, String drugName, String classification, String concentration, String price,
            String amount, String type, String barchID, String expiredDate, String quantity, String producedDate);

    public String editPersonalInformation(String email, String userName, String password, String firstName, String lastName,
            String phoneNumber);

    public ResultSet getUserData(String email) throws SQLException;

    public int getUserType(String email) throws SQLException;

}
