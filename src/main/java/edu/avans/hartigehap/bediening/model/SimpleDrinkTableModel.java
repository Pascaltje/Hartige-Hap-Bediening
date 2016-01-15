package edu.avans.hartigehap.bediening.model;

import edu.avans.hartigehap.bediening.logic.OrderManager;

import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by rubie_000 on 14-1-2016.
 */
public class SimpleDrinkTableModel extends DefaultTableModel {
    private ArrayList<OrderDetail> orderItems;
    private ArrayList<Order> orders;


    public SimpleDrinkTableModel(OrderManager orderManager, ArrayList<Order> orders1) {
        ArrayList<OrderDetail> drinks = new ArrayList<>();
        orderItems = new ArrayList<>();
        HashMap<Integer, Order> orders = new HashMap<Integer, Order>();
        for (Order o : orders1) {
            orders.put(o.getId(), o);
            orderItems.addAll(o.getOrderDetails());

        }

        for (OrderDetail item : orderItems) {
            if ("Drankjes".equals(item.getCourseName())) {
                drinks.add(item);
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        Object[][] data = new Object[drinks.size()][5];
        String[] columns = new String[]
                {
                        "Tafel", "Drankje", "Aantal", "Prijs", "Beschrijving", "Status"
                };
        for (int i = 0; i < drinks.size(); i++) {
            OrderDetail orderItem = drinks.get(i);

            data[i] = new Object[]
                    {
                            orders.get(orderItem.getOrderId()).getTableNumber(), orderItem.getItemName(), orderItem.getAmount(), "\u20ac" + decimalFormat.format(orderItem.getTotalPrice()), orderItem.getDescription(), orderItem.getStatus()
                    };
        }

        this.setDataVector(data, columns);
        this.fireTableDataChanged();

        refreshOrders(orderManager);
    }


    public void refreshOrders(OrderManager orderManager) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                HashMap<Integer, Order> orderMap = new HashMap<Integer, Order>();
                ArrayList<OrderDetail> drinks = new ArrayList<>();
                orderItems = new ArrayList<>();
                orders = orderManager.getAllOrders();
                for (Order o : orders) {
                    orderMap.put(o.getId(), o);
                    orderItems.addAll(o.getOrderDetails());

                }

                for (OrderDetail drink : orderItems) {
                    if ("Drankjes".equals(drink.getCourseName())) {
                        drinks.add(drink);
                    }
                }
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                Object[][] data = new Object[drinks.size()][5];
                String[] columns = new String[]
                        {
                                "Drankje", "Tafel", "Aantal", "Prijs", "Beschrijving", "Status"
                        };
                for (int i = 0; i < drinks.size(); i++) {
                    OrderDetail orderItem = drinks.get(i);
                    data[i] = new Object[]
                            {
                                    orderItem.getItemName(), orderMap.get(orderItem.getOrderId()).getTableNumber(), orderItem.getAmount(), "\u20ac" + decimalFormat.format(orderItem.getTotalPrice()), orderItem.getDescription(), orderItem.getStatus()
                            };
                }

                SimpleDrinkTableModel.this.setDataVector(data, columns);
                SimpleDrinkTableModel.this.fireTableDataChanged();
            }
        }, 0, 10000);

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
