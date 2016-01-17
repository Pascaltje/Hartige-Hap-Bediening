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

import java.util.ArrayList;
import java.util.List;

/**
 * @author David Verhaak
 * @version 1.0.0
 */
public class Order
{

	private int id;
	private int guestId;
	private PaymentStatus paymentStatus;
	private String date;
	private double totalAmount;
	private int tableNumber;
	private List<OrderDetail> orderDetails;

	public Order(int id, int guestId, PaymentStatus paymentStatus, String date, double totalAmount, int tableNumber)
	{
		this.id = id;
		this.guestId = guestId;
		this.paymentStatus = paymentStatus;
		this.date = date;
		this.totalAmount = totalAmount;
		this.tableNumber = tableNumber;
		orderDetails = new ArrayList<>();
	}
	/* get the tablenumber of an order
	*@return int
	*/
	public int getTableNumber()
	{
		return tableNumber;
	}
	/* set the tablenumber of an order
	*@param tableNumber int
	*@return void
 	*/
	public void setTableNumber(int tableNumber)
	{
		this.tableNumber = tableNumber;
	}
	/* get the id of an order
	*@return int
 	*/
	public int getId()
	{
		return id;
	}
	/* set the id of an order
	*@param id int
	*@return void
 	*/
	public void setId(int id)
	{
		this.id = id;
	}
	/* get the guesId from an order
	*@return int
 	*/
	public int getGuestId()
	{
		return guestId;
	}
	/* set the guestId from an order
	*@param guestId int
	*@return void
 	*/
	public void setGuestId(int guestId)
	{
		this.guestId = guestId;
	}
	/* get the paymentstatus from an order
	*@return PaymentStatus
 	*/
	public PaymentStatus getPaymentStatus()
	{
		return paymentStatus;
	}
	/* set the paymentstatus from an order
	*@param paymentStatus PaymentStatus
	*@return void
 	*/
	public void setPaymentStatus(PaymentStatus paymentStatus)
	{
		this.paymentStatus = paymentStatus;
	}
	/* get the date from an order
	*@return String
 	*/
	public String getDate()
	{
		return date;
	}
	/* set the date from an order
	*@param date String
	*@return void
 	*/
	public void setDate(String date)
	{
		this.date = date;
	}
	/* get the total amount from an order
	*@return double
 	*/
	public double getTotalAmount()
	{
		return totalAmount;
	}
	/* set the total amount from an order
	*@param totalAmount Double
	*@return void
 	*/
	public void setTotalAmount(double totalAmount)
	{
		this.totalAmount = totalAmount;
	}
	/* get all the orderItems from an order and places them into a arrayList with orderItems
	*@return List<OrderDetaiL>
 	*/
	public List<OrderDetail> getOrderDetails()
	{
		return orderDetails;
	}
	/* set all the orderItems from an order and places them into a arrayList with orderItems
	*@param orderDetails ArrayList<OrderDetail>
	*@return void
 	*/
	public void setOrderDetails(ArrayList<OrderDetail> orderDetails)
	{
		orderDetails.clear();
		orderDetails.addAll(orderDetails);
	}
	/* add an orderItem from an order to the orderItem list
	*@return void
 	*/
	public void addOrderDetail(OrderDetail orderDetail)
	{
		orderDetails.add(orderDetail);
	}
}
