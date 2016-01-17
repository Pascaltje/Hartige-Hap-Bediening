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

/**
 *
 * @author David Verhaak
 * @version 1.0.0
 */
public class OrderDetail
{

	private int orderId;
	private String description;
	private OrderStatus status;
	private String itemName;
	private int employeeId;
	private int amount;
	private double totalPrice;
	private int consumableId;
	private String courseName;

	public OrderDetail(int orderId, OrderStatus status, int employeeId, int amount, String description, String itemName, Double totalPrice, String courseName)
	{
		this.description = description;
		this.orderId = orderId;
		this.status = status;
		this.employeeId = employeeId;
		this.amount = amount;
		this.itemName = itemName;
		this.totalPrice = totalPrice;
		this.courseName = courseName;
	}
	/* get the coursename from an orderItem
       *@return String
       */
	public String getCourseName()
	{
		return courseName;
	}
	/* set the coursename from an orderItem
       *@param courseName String
       *@return void
       */
	public void setCourseName(String courseName)
	{
		this.courseName = courseName;
	}
	/* get the orderId
       *@param seats int
       *@return int
       */
	public int getOrderId()
	{
		return orderId;
	}
	/* set the orderId
     *@param orderId int
     *@return void
     */
	public void setOrderId(int orderId)
	{
		this.orderId = orderId;
	}
	/* get the orderStatus from an orderItem
       *@return OrderStatus
       */
	public OrderStatus getStatus()
	{
		return status;
	}
	/* set get the orderStatus from an orderItem
       *@param status orderStatus
       *@return void
       */
	public void setStatus(OrderStatus status)
	{
		this.status = status;
	}
	/* get the employeeId from an orderItem
       *@return int
       */
	public int getEmployeeId()
	{
		return employeeId;
	}
	/* set the employeeId from an orderItem
       *@param employeeId int
       *@return void
       */
	public void setEmployeeId(int employeeId)
	{
		this.employeeId = employeeId;
	}
	/* get the amount from an orderId
       *@return int
       */
	public int getAmount()
	{
		return amount;
	}
	/* set the amount from an orderId
       *@param amount int
       *@return void
       */
	public void setAmount(int amount)
	{
		this.amount = amount;
	}
	/* get the totalprice from an orderItem
       *@return double
       */
	public double getTotalPrice()
	{
		return totalPrice;
	}
	/* set the totalprice from an orderItem
       *@param totalprice doublew
       *@return void
       */
	public void setTotalPrice(double totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	/* get the consumbleId from an orderItem
       *@return int
       */
	public int getConsumableId()
	{
		return consumableId;
	}
	/* set the consumbleId from an orderItem
       *@param consumableId int
       *@return void
       */
	public void setConsumableId(int consumableId)
	{
		this.consumableId = consumableId;
	}
	/* get the description from an orderItem
       *@return String
       */
	public String getDescription()
	{
		return description;
	}
	/* set the description from an orderItem
       *@param description String
       *@return void
       */
	public void setDescription(String description)
	{
		this.description = description;
	}
	/* get the name of the item
       *@return Stromg
       */
	public String getItemName()
	{
		return itemName;
	}
	/* set the name of the item
       *@param itemname String
       *@return void
       */
	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}

}
