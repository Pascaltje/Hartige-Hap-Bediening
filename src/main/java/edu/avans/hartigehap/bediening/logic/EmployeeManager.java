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
 *
 * @author David
 */
public class EmployeeManager
{
        ArrayList<Employee> EmployeeList = new ArrayList<Employee>();
	private static EmployeeManager instance = null;

	public static EmployeeManager getInstance()
	{
		synchronized(EmployeeManager.class)
		{
			if(instance == null)
			{
				instance = new EmployeeManager();
			}
		}
		return instance;
	}
	
	private EmployeeManager()
	{

	}
        
        public Employee Login(String Email){
            EmployeeDAO EmployeeDAO = new EmployeeDAO();
            Employee Employee = EmployeeDAO.CheckLogin(Email);
            if(Employee!=null){
                
                for(Employee Listemployee : EmployeeList ){
                    if(Listemployee.getEmail().equals(Employee.getEmail())){
                        Employee = null;
                    }
                }
                
                if(Employee!=null){
                    EmployeeList.add(Employee); 
                }
            }
            return Employee;
        }
        
        public void Loguit(String Name){
            for(Employee Listemployee : EmployeeList ){
                if((Listemployee.getFirstName() + " " + Listemployee.getLastName()).equals(Name)){
                    EmployeeList.remove(Listemployee);
                }
            }
        }
        
        public ArrayList<Employee> getEmployee(){
            return EmployeeList;
        }
        
}
