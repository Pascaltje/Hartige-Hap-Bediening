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
package edu.avans.hartigehap.bediening.model;

import java.util.Date;

/**
 *
 * @author David Verhaak
 * @version 1.0.0
 */
public class Employee
{

	private int id;
	private String firstName;
	private String lastName;
	private String birthday;
	private String address;
	private double salary;
	private String dateStarted;
	private String function;
	private String email;

	public Employee(int id, String firstName,  String lastName, String birthday, String address, double salary, String dateStarted, String function, String email)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.address = address;
		this.salary = salary;
		this.dateStarted = dateStarted;
		this.function = function;
		this.email = email;
	}
	/* refreshes the JTable with the correct order data
                       *@param tableNumber int
                        *@param orderManager OrderManager
                        *@return void
                       */
	public int getId()
	{
		return id;
	}
	/* refreshes the JTable with the correct order data
                       *@param tableNumber int
                        *@param orderManager OrderManager
                        *@return void
                       */
	public void setId(int id)
	{
		this.id = id;
	}
	/* refreshes the JTable with the correct order data
                       *@param tableNumber int
                        *@param orderManager OrderManager
                        *@return void
                       */
	public String getFirstName()
	{
		return firstName;
	}
	/* refreshes the JTable with the correct order data
                       *@param tableNumber int
                        *@param orderManager OrderManager
                        *@return void
                       */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public double getSalary()
	{
		return salary;
	}

	public void setSalary(double salary)
	{
		this.salary = salary;
	}

	public String getDateStarted()
	{
		return dateStarted;
	}

	public void setDateStarted(String dateStarted)
	{
		this.dateStarted = dateStarted;
	}

	public String getFunction()
	{
		return function;
	}

	public void setFunction(String function)
	{
		this.function = function;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
}
