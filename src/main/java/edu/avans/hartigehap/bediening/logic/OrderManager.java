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

import edu.avans.hartigehap.bediening.io.DatabaseConnection;
import edu.avans.hartigehap.bediening.io.OrderDAO;
import edu.avans.hartigehap.bediening.model.OrderStatus;
import edu.avans.hartigehap.bediening.model.Order;
import edu.avans.hartigehap.bediening.model.OrderDetail;
import edu.avans.hartigehap.bediening.model.OrderStatus;

import javax.swing.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author David
 */
public class OrderManager {
    private final Logger LOG = Logger.getLogger(OrderManager.class.getSimpleName());
    private static OrderManager instance = null;
    private OrderDAO orderDAO;

    public static OrderManager getInstance() {
        synchronized (OrderManager.class) {
            if (instance == null) {
                instance = new OrderManager();
            }
        }
        return instance;
    }

    private OrderManager() {
        orderDAO = new OrderDAO();
    }

    public void refresh() {
        Logger.getLogger(OrderManager.class.getName()).log(Level.INFO, "Refresh");
    }

    /* Gets the order by tableNumber
     *@param tableNumber int
     *@return order
     */
    public Order getOrderByTableNumber(int tableNumber) {
        return orderDAO.getOrderByTableNumber(tableNumber);
    }

    /* Changes the orderstatus by id
       *@param orderId int, newStatus String, itemName String
       *@return void
      */
    public void changeStatusById(int orderId, String newStatus, String itemName) {
        orderDAO.changeStatusById(orderId, newStatus, itemName);

    }

    /* Gets all the orders from the database
     *@return ArrayList<Order>
     */
    public ArrayList<Order> getAllOrders() {
        return orderDAO.getAllOrders();

    }
    /* Changes the orderstatus from an order to paid with the matching id
          *@param orderNo int
          *@return void
         */
    public void setOrderPaid(int orderNo) {
        if (JOptionPane.showConfirmDialog(null, "Weet u zeker dat u de bestelling wil afronden?", "WARNING",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            orderDAO.setOrderPaid(orderNo);
        } else {

        }
    }
            /* On click event that changes the orderstatus in the GUI and database
            *@param newOrder Order
             *@param dataTable JTable
             *@return void
            */
    public void checkStatus(JTable dataTable, Order newOrder) {
        dataTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // yes option
                int row = dataTable.rowAtPoint(evt.getPoint());
                int col = dataTable.columnAtPoint(evt.getPoint());
                int orderId = newOrder.getId();
                int colCount = dataTable.getColumnCount() - 1;
                String itemName = (String) dataTable.getValueAt(row, 0);
                LOG.log(Level.INFO, "Col = {0}", col);

                if (row >= 0 && col >= colCount) {
                    if (JOptionPane.showConfirmDialog(null, "Weet u zeker dat u de status wilt aanpassen?", "WARNING",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        switch (dataTable.getValueAt(row, col).toString()) {
                            case "Besteld":
                                changeStatusById(orderId, "In_behandeling", itemName);
                                dataTable.setValueAt(OrderStatus.In_behandeling, row, col);
                                break;
                            case "In_behandeling":
                                changeStatusById(orderId, "Klaar", itemName);
                                dataTable.setValueAt(OrderStatus.Klaar, row, col);
                                break;
                            case "Klaar":
                                changeStatusById(orderId, "Besteld", itemName);
                                dataTable.setValueAt(OrderStatus.Besteld, row, col);
                                break;

                        }

                    }
                } else {
                    // no option
                }

            }
        });

    }

}
