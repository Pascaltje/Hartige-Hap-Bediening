/*
 * Copyright (C) 2015 David
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
package edu.avans.hartigehap.bediening.ui;

import edu.avans.hartigehap.bediening.logic.OrderManager;
import edu.avans.hartigehap.bediening.model.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 * @author David
 */
public class DetailGUI extends JDialog
{

	private final Logger LOG = Logger.getLogger(DetailGUI.class.getSimpleName());
	
	private OrderManager manager;
	private int orderId;
	private Order newOrder;
	private JTable dataTable, foodTable;

	public DetailGUI(Frame parent, boolean modal, final int tableNumber)
	{
		super(parent, modal);
		setPreferredSize(new Dimension(700, 350));
		setSize(750, 350);
		setUndecorated(true);
		rootPane.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, null));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel exitPanel = new JPanel();
		JLabel orderInfoLabel = new JLabel("Ordergegevens");
		JLabel orderDataLabel = new JLabel("Orderinhoud");
		JLabel orderFoodLabel = new JLabel("Order maaltijden");
		JTable infoTable = new JTable();
		dataTable = new JTable();
		foodTable = new JTable();
		JScrollPane infoScrollPane = new JScrollPane();
		JScrollPane dataScrollPane = new JScrollPane();
		JScrollPane foodScrollPane = new JScrollPane();
		JButton backButton = new JButton("Terug");

		manager = OrderManager.getInstance();
		newOrder = manager.getOrderByTableNumber(tableNumber);

		leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
		leftPanel.setPreferredSize(new Dimension(400, 350));

		orderInfoLabel.setPreferredSize(new Dimension(350, 14));
		leftPanel.add(orderInfoLabel);

		infoTable.setModel(new DefaultTableModel(
			new Object[][]
			{
				{
					null, null, null, null, null
				}
			},
			new String[]
			{
				"Tafelnummer", "Gastnaam", "Totale prijs", "Datum", "Status"
			}
		));

		infoTable.setPreferredSize(new Dimension(350, 64));
		infoScrollPane.setPreferredSize(new Dimension(350, 42));
		infoScrollPane.setViewportView(infoTable);
		infoScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		leftPanel.add(infoScrollPane);

		orderDataLabel.setPreferredSize(new Dimension(350, 30));
		leftPanel.add(orderDataLabel);

		dataTable.setModel(new DefaultTableModel(new Object[][] {},
			new String[]
			{
				"Naam", "EmployeeId", "Antal", "Prijs", "Beschrijving", "Status"
			}
		));
		dataScrollPane.setPreferredSize(new Dimension(350, 190));
		dataScrollPane.setViewportView(dataTable);
		leftPanel.add(dataScrollPane);

		rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
		rightPanel.setPreferredSize(new Dimension(300, 350));

		orderFoodLabel.setPreferredSize(new Dimension(250, 14));
		rightPanel.add(orderFoodLabel);

		foodTable.setModel(new DefaultTableModel(
			new Object[][]
			{

			},
			new String[]
			{
				"Naam", "Aantal", "Prijs", "Status"
			}
		));
		foodScrollPane.setViewportView(foodTable);
		foodScrollPane.setPreferredSize(new Dimension(250, 230));
		rightPanel.add(foodScrollPane);
		exitPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		exitPanel.setPreferredSize(new Dimension(250, 38));
		exitPanel.setLayout(new BorderLayout());
		backButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				back();
			}
		});
		if (newOrder != null)
		{
			OrderTableModel model = new OrderTableModel(newOrder);
			infoTable.setModel(model);
			DetailOrderTableModel model1 = new DetailOrderTableModel(manager, tableNumber);
			dataTable.setModel(model1);
			MealTableModel meals = new MealTableModel(manager, tableNumber);
			foodTable.setModel(meals);
		}
		exitPanel.add(backButton, BorderLayout.PAGE_END);
		rightPanel.add(exitPanel);
		getContentPane().add(leftPanel, BorderLayout.WEST);
		getContentPane().add(rightPanel, BorderLayout.EAST);
		checkDrinkStatus();
		checkMealStatus();
	}

	public void changeStatus(int selected, JTable dataTable, int row, int col, int orderId, String itemName)
	{
		LOG.info(dataTable.getValueAt(row, col).toString());
		OrderStatus status = OrderStatus.values()[selected];
		switch (status)
		{
			case NOT_STARTED:
				manager.changeStatusById(orderId, 2, itemName);
				dataTable.setValueAt(OrderStatus.STARTED, row, col);
				break;
			case STARTED:
				manager.changeStatusById(orderId, 3, itemName);
				dataTable.setValueAt(OrderStatus.READY, row, col);
				break;
			case READY:
				manager.changeStatusById(orderId, 4, itemName);
				dataTable.setValueAt(OrderStatus.FINISHED, row, col);
				break;
			case FINISHED:
				manager.changeStatusById(orderId, 1, itemName);
				dataTable.setValueAt(OrderStatus.NOT_STARTED, row, col);
				break;
			default:
				break;
		}
	}

	public void checkDrinkStatus()
	{
		if (dataTable.getRowCount() > 0)
		{
			dataTable.addMouseListener(new java.awt.event.MouseAdapter()
			{
				@Override
				public void mouseClicked(java.awt.event.MouseEvent evt)
				{
					int row = dataTable.rowAtPoint(evt.getPoint());
					int col = dataTable.columnAtPoint(evt.getPoint());
					int selected = 3;
					LOG.log(Level.INFO, "Col = {0}", col);
					if (row >= 0 && col >= 5)
					{
						switch (dataTable.getValueAt(row, col).toString())
						{
							case "NOT_STARTED":
								selected = 0;
								break;
							case "STARTED":
								selected = 1;
								break;
							case "READY":
								selected = 2;
								break;
							default:
								break;
						}
						orderId = newOrder.getId();
						String itemName = (String) dataTable.getValueAt(row, 0);
						changeStatus(selected, dataTable, row, col, orderId, itemName);
					}
				}
			});
		}
	}

	public void checkMealStatus()
	{
		if (foodTable.getRowCount() > 0)
		{
			foodTable.addMouseListener(new java.awt.event.MouseAdapter()
			{
				@Override
				public void mouseClicked(java.awt.event.MouseEvent evt)
				{
					int row = foodTable.rowAtPoint(evt.getPoint());
					int col = foodTable.columnAtPoint(evt.getPoint());
					int selected = 3;
					LOG.log(Level.INFO, "Col = {0}", col);
					if (row >= 0 && col >= 3)
					{
						switch (foodTable.getValueAt(row, col).toString())
						{
							case "NOT_STARTED":
								selected = 0;
								break;
							case "STARTED":
								selected = 1;
								break;
							case "READY":
								selected = 2;
								break;
							default:
								break;
						}
						orderId = newOrder.getId();
						String itemName = (String) foodTable.getValueAt(row, 0);
						changeStatus(selected, foodTable, row, col, orderId, itemName);
					}
				}
			});
		}
	}

	private void back()
	{
		this.dispose();
	}
}
