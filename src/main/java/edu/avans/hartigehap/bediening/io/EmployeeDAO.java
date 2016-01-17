/*
 * Copyright (C) 2015, David Verhaak
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.avans.hartigehap.bediening.io;

import edu.avans.hartigehap.bediening.model.Employee;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class EmployeeDAO
{
    /*Check if the email from the employee matches with the one in the database
    *@param email string
    *@return empoyee
    */
    public Employee CheckLogin(String email){
        Employee employee = null;
        DatabaseConnection connection = new DatabaseConnection();
        if (connection.open()) {
            try {
                PreparedStatement statement = connection.createStatement("select * from dhh_employee where dhh_employee.email = ?");
                statement.setString(1, email);
                ResultSet resultSet = connection.execute(statement);
                if (resultSet != null) {
                     while (resultSet.next()) {
                        int employeeId = resultSet.getInt("employeeId");
                        String firstName = resultSet.getString("firstName");
                        String lastName = resultSet.getString("lastName");
                        String birthday = resultSet.getString("birthday");
                        Double salary = resultSet.getDouble("salary");
                        String address = resultSet.getString("address");
                        String postalCode = resultSet.getString("postalCode");
                        String phoneNo = ""; //resultSet.getString("phoneNo");
                        String joinDate = "";
                        String EMPLOYEEFUNCTIONfunctionName = resultSet.getString("EMPLOYEEFUNCTIONfunctionName");
                         
                         
                         employee = new Employee(employeeId, firstName, lastName, birthday, address, salary, joinDate, EMPLOYEEFUNCTIONfunctionName, email);
                         
                         
                     }
                }

                
                
            }catch (SQLException exception){
                Logger.getLogger(OrderDAO.class.getSimpleName()).log(Level.SEVERE, null, exception);
            }  
        
        }
    return employee;
    }   
}
