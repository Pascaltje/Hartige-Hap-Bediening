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

import edu.avans.hartigehap.bediening.model.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David
 */
public class OrderDAO {
    /*Gets the order from the database with the same tablenumber
       *@param tableNumber int
       *@return order
       */
    public Order getOrderByTableNumber(int tableNumber) {

        Order order = null;
        DatabaseConnection connection = new DatabaseConnection();
        if (connection.open()) {
            try {
                PreparedStatement statement = connection.createStatement("SELECT * FROM dhh_receipt where TABLEtableNo = ? and paymentStatus in (?,?) ");
                statement.setInt(1, tableNumber);
                statement.setString(2, "NOT_PAID");
               statement.setString(3,"WANTS_TO_PAY");
                ResultSet resultSet = connection.execute(statement);
                if (resultSet != null) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("receiptNo");
                        String paymentStatus = resultSet.getString("paymentStatus");
                        int tableNo = resultSet.getInt("TABLEtableNo");
                        Date date = resultSet.getDate("orderDatetime");
                        Date dt = new java.util.Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentTime = sdf.format(date);
                        order = new Order(id, 1, PaymentStatus.valueOf(resultSet.getString("paymentStatus")), currentTime, 0.00, tableNo);

                        PreparedStatement subStament = connection.createStatement("SELECT dhh_orderline . * ,dhh_item.COURSEcourseName as coursename, dhh_item.Price * dhh_orderline.amount AS Itemtotalprice FROM dhh_orderline, dhh_item WHERE RECEIPTreceiptNo = ? AND dhh_orderline.ITEMitemName = dhh_item.itemName");
                        subStament.setInt(1, order.getId());
                        ResultSet subResultSet = connection.execute(subStament);
                        if (subResultSet != null) {
                            while (subResultSet.next()) {
                                OrderDetail orderDetails = new OrderDetail(subResultSet.getInt("RECEIPTreceiptNo"),  OrderStatus.valueOf(subResultSet.getString("ORDERSTATUSstatusName")), subResultSet.getInt("EMPLOYEEemployeeid"), subResultSet.getInt("amount"), subResultSet.getString("description"), subResultSet.getString("ITEMitemName"), subResultSet.getDouble("Itemtotalprice"), subResultSet.getString("coursename"));
                                order.addOrderDetail(orderDetails);
                            }
                        }
                    }

                }
            } catch (SQLException exception) {
                Logger.getLogger(OrderDAO.class.getSimpleName()).log(Level.SEVERE, null, exception);
            }
            connection.close();
        }

        return order;

    }
         /* Changes the status from an orderLine with matching parameters
          *@param orderId int
          *@param newStatus String
          *@param itemName String
          *@return void
          */
    public void changeStatusById(int orderId, String newStatus, String itemName) {
        DatabaseConnection connection = new DatabaseConnection();
        if (connection.open()) {
            try {
                PreparedStatement statement = connection.createStatement("UPDATE dhh_orderline set ORDERSTATUSstatusName = ? where RECEIPTreceiptNo = ? and ITEMitemName = ?");
                statement.setString(1, newStatus);
                statement.setInt(2, orderId);
                statement.setString(3, itemName);
                statement.execute();
            } catch (SQLException exception) {
                Logger.getLogger(OrderDAO.class.getSimpleName()).log(Level.SEVERE, null, exception);
            }
            connection.close();
        }

    }
    /* Set the order status from an order to PAID
         *@param orderNo int
         *@return void
         */
    public void setOrderPaid(int orderNo) {
        DatabaseConnection connection = new DatabaseConnection();
        if (connection.open()) {
            try {
                PreparedStatement statement = connection.createStatement("UPDATE dhh_receipt set paymentStatus = ? where receiptNo = ? ");
                statement.setString(1, "PAID");
                statement.setInt(2, orderNo);
                statement.execute();
            } catch (SQLException exception) {
                Logger.getLogger(OrderDAO.class.getSimpleName()).log(Level.SEVERE, null, exception);
            }
            connection.close();
        }

    }

    /* Gets all the order where the status is NOT_PAID and WANTS_TO_PAY
             *
             *@return ArrayList<Order>
             */
    public ArrayList<Order> getOrderStatus() {
        ArrayList<Order> orders = new ArrayList<>();

        DatabaseConnection connection = new DatabaseConnection();
        if (connection.open()) {
            try {
                PreparedStatement statement = connection.createStatement("SELECT * FROM dhh_receipt where paymentStatus = ? or paymentStatus = ?");
                statement.setString(1, "NOT_PAID");
                statement.setString(2, "WANTS_TO_PAY");
                ResultSet resultSet = connection.execute(statement);
                if (resultSet != null) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("receiptNo");
                        String paymentStatus = resultSet.getString("paymentStatus");
                        int tableNo = resultSet.getInt("TABLEtableNo");
                        Date date = resultSet.getDate("orderDatetime");
                        Date dt = new java.util.Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentTime = sdf.format(date);
                        Order newOrder = new Order(id, 1, PaymentStatus.valueOf(resultSet.getString("paymentStatus")), currentTime, 0.00, tableNo);
                        orders.add(newOrder);
                    }

                }


            } catch (SQLException exception) {
                Logger.getLogger(OrderDAO.class.getSimpleName()).log(Level.SEVERE, null, exception);
            }
            connection.close();
        }
        return orders;
    }
    /* Get all the order from the database where the status is NOT_PAID and WANTS_TO_PAY
             *
             *@return ArrayList<Order>
             */
    public ArrayList<Order> getAllOrders(){
        ArrayList<Order> orders = new ArrayList<>();

        DatabaseConnection connection = new DatabaseConnection();
        if (connection.open()) {
            try {
                PreparedStatement statement = connection.createStatement("SELECT * FROM dhh_receipt where paymentStatus in (?,?) ");

                statement.setString(1, "NOT_PAID");
                statement.setString(2,"WANTS_TO_PAY");
                ResultSet resultSet = connection.execute(statement);
                if (resultSet != null) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("receiptNo");
                        String paymentStatus = resultSet.getString("paymentStatus");
                        int tableNo = resultSet.getInt("TABLEtableNo");
                        Date date = resultSet.getDate("orderDatetime");
                        Date dt = new java.util.Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentTime = sdf.format(date);
                        Order newOrder = new Order(id, 1, PaymentStatus.valueOf(resultSet.getString("paymentStatus")), currentTime, 0.00, tableNo);

                        PreparedStatement subStament = connection.createStatement("SELECT dhh_orderline . * ,dhh_item.COURSEcourseName as coursename, dhh_item.Price * dhh_orderline.amount AS Itemtotalprice FROM dhh_orderline, dhh_item WHERE RECEIPTreceiptNo = ? AND dhh_orderline.ITEMitemName = dhh_item.itemName AND dhh_orderline.ORDERSTATUSstatusName <> ?");
                        subStament.setInt(1,newOrder.getId());
                        subStament.setString(2,"Klaar");
                        ResultSet subResultSet = connection.execute(subStament);
                        if (subResultSet != null) {
                            while (subResultSet.next()) {
                                OrderDetail orderDetails = new OrderDetail(subResultSet.getInt("RECEIPTreceiptNo"),  OrderStatus.valueOf(subResultSet.getString("ORDERSTATUSstatusName")), subResultSet.getInt("EMPLOYEEemployeeid"), subResultSet.getInt("amount"), subResultSet.getString("description"), subResultSet.getString("ITEMitemName"), subResultSet.getDouble("Itemtotalprice"), subResultSet.getString("coursename"));
                                newOrder.addOrderDetail(orderDetails);

                            }
                        }
                        orders.add(newOrder);
                    }

                }
            } catch (SQLException exception) {
                Logger.getLogger(OrderDAO.class.getSimpleName()).log(Level.SEVERE, null, exception);
            }
            connection.close();
        }

        return orders;
    }
}
