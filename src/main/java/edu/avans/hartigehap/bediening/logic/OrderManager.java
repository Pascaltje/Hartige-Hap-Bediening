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
public class OrderManager
{
	private final Logger LOG = Logger.getLogger(OrderManager.class.getSimpleName());
	private static OrderManager instance = null;
	private OrderDAO orderDAO;

	public static OrderManager getInstance()
	{
		synchronized (OrderManager.class)
		{
			if (instance == null)
			{
				instance = new OrderManager();
			}
		}
		return instance;
	}

	private OrderManager()
	{
		orderDAO = new OrderDAO();
	}

	public void refresh()
	{
		Logger.getLogger(OrderManager.class.getName()).log(Level.INFO, "Refresh");
	}

	public Order getOrderByTableNumber(int tableNumber)
	{
		return orderDAO.getOrderByTableNumber(tableNumber);
	}

	public void changeStatusById(int orderId, int newStatus, String itemName)
	{
		orderDAO.changeStatusById(orderId, newStatus, itemName);

	}

	public ArrayList<Order> getAllOrders(){
		return orderDAO.getAllOrders();

	}
	public void setOrderPaid(int orderNo)
	{
		if (JOptionPane.showConfirmDialog(null, "Weet u zeker dat u de bestelling wil afronden?", "WARNING",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			orderDAO.setOrderPaid(orderNo);
		}
		else{

		}
	}

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
							case "NOT_STARTED":
								changeStatusById(orderId, 2, itemName);
								dataTable.setValueAt(OrderStatus.STARTED, row, col);
								break;
							case "STARTED":
								changeStatusById(orderId, 3, itemName);
								dataTable.setValueAt(OrderStatus.READY, row, col);
								break;
							case "READY":
								changeStatusById(orderId, 4, itemName);
								dataTable.setValueAt(OrderStatus.FINISHED, row, col);
								break;
							case "FINISHED":
								changeStatusById(orderId, 1, itemName);
								dataTable.setValueAt(OrderStatus.NOT_STARTED, row, col);
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
