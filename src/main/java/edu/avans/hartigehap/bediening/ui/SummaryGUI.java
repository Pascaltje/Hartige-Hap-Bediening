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

import edu.avans.hartigehap.bediening.logic.TableManager;
import edu.avans.hartigehap.bediening.model.Order;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 * @author David
 */
public class SummaryGUI extends JFrame {
    private TableManager tableManager = TableManager.getInstance();
    private JButton[] button;

    public SummaryGUI() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Windows".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(SummaryGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }
        setSize(800, 500);
        setLocationRelativeTo(null);
        getContentPane().setPreferredSize(new Dimension(800, 500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel tablesPanel = new JPanel();
        JLabel tableOverviewLabel = new JLabel("Tafeloverzicht");
        JLabel mealOverviewLabel = new JLabel("Te serveren gerechten");
        JScrollPane foodScrollPane = new JScrollPane();
        JTable foodTable = new JTable();
        setLayout(new BorderLayout(5, 5));

        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        leftPanel.setPreferredSize(new Dimension(500, 400));
        button = new JButton[13];
        tableOverviewLabel.setPreferredSize(new Dimension(450, 14));
        leftPanel.add(tableOverviewLabel);

        tablesPanel.setBackground(Color.WHITE);
        tablesPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        tablesPanel.setPreferredSize(new Dimension(450, 350));
        tablesPanel.setLayout(new GridLayout(4, 3, 20, 20));
        for (int i = 1; i < 13; i++) {
            button[i] = new JButton("Tafel " + i);
            button[i].setName("" + i);
            button[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    JButton source = (JButton) actionEvent.getSource();
                    checkDetail(Integer.parseInt(source.getName()));

                }
            });
            tablesPanel.add(button[i]);
        }
        leftPanel.add(tablesPanel);

        rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        rightPanel.setPreferredSize(new Dimension(300, 450));

        mealOverviewLabel.setPreferredSize(new Dimension(250, 14));
        rightPanel.add(mealOverviewLabel);

        foodScrollPane.setBackground(Color.WHITE);
        foodScrollPane.setPreferredSize(new Dimension(250, 150));

        foodTable.setModel(new DefaultTableModel(
                new Object[][]
                        {
                                {
                                        null, null, null
                                }
                        },
                new String[]
                        {
                                "Tafel", "Gerecht", "Status"
                        }
        ));
        foodScrollPane.setViewportView(foodTable);
        rightPanel.add(foodScrollPane);

        getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(rightPanel, BorderLayout.EAST);
        changeColor(tableManager.changeTableColor());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                changeColor(tableManager.changeTableColor());
            }
        }, 0, 10000);
    }

    private void changeColor(ArrayList<Order> orders) {
        for (int i = 1; i < button.length; i++) {
            button[i].setBackground(new JButton().getBackground());
        }
        for (Order order : orders) {
            if (order.getPaymentStatus().toString().equals("NOT_PAID")) {
                button[order.getTableNumber()].setBackground(Color.RED);
                button[order.getTableNumber()].setContentAreaFilled(false);
                button[order.getTableNumber()].setOpaque(true);
            } else {
                button[order.getTableNumber()].setBackground(Color.GREEN);
                button[order.getTableNumber()].setContentAreaFilled(false);
                button[order.getTableNumber()].setOpaque(true);
            }
        }


    }


    private void checkDetail(int tableNumber) {
        DetailGUI detail = new DetailGUI(this, true, tableNumber);
        detail.setLocationRelativeTo(this);
        detail.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        detail.setVisible(true);
    }
}
