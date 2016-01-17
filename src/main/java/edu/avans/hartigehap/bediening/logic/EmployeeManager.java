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
package edu.avans.hartigehap.bediening.logic;

import edu.avans.hartigehap.bediening.io.EmployeeDAO;
import edu.avans.hartigehap.bediening.model.Employee;

import java.util.ArrayList;

/**
 * @author David
 */
public class EmployeeManager {
    ArrayList<Employee> employeeList = new ArrayList<Employee>();
    private static EmployeeManager instance = null;

    public static EmployeeManager getInstance() {
        synchronized (EmployeeManager.class) {
            if (instance == null) {
                instance = new EmployeeManager();
            }
        }
        return instance;
    }

    private EmployeeManager() {

    }

    /* Function for loggin the user in an adding the user to an employeelist
     *@param email String
     *@return employee
     */
    public Employee logIn(String email) {
        EmployeeDAO EmployeeDAO = new EmployeeDAO();
        Employee employee = EmployeeDAO.CheckLogin(email);
        if (employee != null) {

            for (Employee Listemployee : employeeList) {
                if (Listemployee.getEmail().equals(employee.getEmail())) {
                    employee = null;
                }
            }

            if (employee != null) {
                employeeList.add(employee);
            }
        }
        return employee;
    }

    /* Function for loggin the user out and removing it from the employeelist
    *@param name String
    *@return employee
    */
    public void logOut(String Name) {
        for (Employee listEmployee : employeeList) {
            if ((listEmployee.getFirstName() + " " + listEmployee.getLastName()).equals(Name)) {
                employeeList.remove(listEmployee);
            }
        }
    }

    public ArrayList<Employee> getEmployee() {
        return employeeList;
    }

}
