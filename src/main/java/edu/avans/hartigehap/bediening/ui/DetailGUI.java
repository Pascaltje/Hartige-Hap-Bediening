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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 * @author David
 */
public class DetailGUI extends JDialog {

    private final Logger LOG = Logger.getLogger(DetailGUI.class.getSimpleName());

    private OrderManager manager;
    private int orderId;
    private Order newOrder;
    private JTable dataTable, foodTable;

    public DetailGUI(Frame parent, boolean modal, final int tableNumber) {
        super(parent, modal);
        setPreferredSize(new Dimension(1100, 620));
        setSize(1150, 620);
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
        JButton paidButton = new JButton("Bestelling afronden");

        manager = OrderManager.getInstance();
        newOrder = manager.getOrderByTableNumber(tableNumber);

        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        leftPanel.setPreferredSize(new Dimension(600, 750));

        orderInfoLabel.setPreferredSize(new Dimension(550, 14));
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

        infoTable.setPreferredSize(new Dimension(550, 64));
        infoScrollPane.setPreferredSize(new Dimension(550, 42));
        infoScrollPane.setViewportView(infoTable);
        infoScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        leftPanel.add(infoScrollPane);

        orderDataLabel.setPreferredSize(new Dimension(550, 30));
        leftPanel.add(orderDataLabel);

        dataTable.setModel(new DefaultTableModel(new Object[][]{},
                new String[]
                        {
                                "Naam", "EmployeeId", "Antal", "Prijs", "Beschrijving", "Status"
                        }
        ));
        dataScrollPane.setPreferredSize(new Dimension(550, 350));
        dataScrollPane.setViewportView(dataTable);
        leftPanel.add(dataScrollPane);

        rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        rightPanel.setPreferredSize(new Dimension(550, 250));

        orderFoodLabel.setPreferredSize(new Dimension(500, 14));
        rightPanel.add(orderFoodLabel);

        foodTable.setModel(new DefaultTableModel(
                new Object[][]
                        {

                        },
                new String[]
                        {
                                "Naam", "EmployeeId", "Antal", "Prijs", "Beschrijving", "Status"
                        }
        ));
        foodScrollPane.setViewportView(foodTable);
        foodScrollPane.setPreferredSize(new Dimension(500, 390));
        rightPanel.add(foodScrollPane);
        exitPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        exitPanel.setPreferredSize(new Dimension(300, 38));
        exitPanel.setLayout(new BorderLayout());
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                back();
            }
        });

        leftPanel.add(paidButton);

        if (newOrder != null) {
            OrderTableModel order = new OrderTableModel(newOrder, manager);
            infoTable.setModel(order);
            DetailOrderTableModel drinks = new DetailOrderTableModel(manager, tableNumber);
            dataTable.setModel(drinks);
            MealTableModel meals = new MealTableModel(manager, tableNumber);
            foodTable.setModel(meals);

            paidButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    if (newOrder.getPaymentStatus().toString().equals("WANTS_TO_PAY")) {
                        manager.setOrderPaid(newOrder.getId());
                        JOptionPane.showMessageDialog(null, "De bestelling is afgerond", "Title", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            });
        }
        exitPanel.add(backButton, BorderLayout.PAGE_END);
        rightPanel.add(exitPanel);
        getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(rightPanel, BorderLayout.EAST);


        if (dataTable.getRowCount() > 0) {
            manager.checkStatus(dataTable, newOrder);

        }
        if (foodTable.getRowCount() > 0) {
            manager.checkStatus(foodTable, newOrder);
        }
    }

    private void back() {
        this.dispose();
    }
}
