package edu.avans.hartigehap.bediening.model;

import edu.avans.hartigehap.bediening.logic.OrderManager;

import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by rubie_000 on 14-1-2016.
 */
public class SimpleMealTableModel extends DefaultTableModel {
    private ArrayList<OrderDetail> orderItems;
    private ArrayList<Order> orders;
    private ArrayList<OrderDetail> meals;
    /* populates the JTable with the correct order data
                       *@param tableNumber int
                        *@param orderManager OrderManager
                        *@return void
                       */
    public SimpleMealTableModel(OrderManager orderManager, ArrayList<Order> orders1) {
        meals = new ArrayList<>();
        orderItems = new ArrayList<>();
        HashMap<Integer, Order> orders = new HashMap<Integer, Order>();
        for (Order o : orders1) {
            orders.put(o.getId(), o);
            orderItems.addAll(o.getOrderDetails());

        }

        for (OrderDetail item : orderItems) {
            if (!"Drankjes".equals(item.getCourseName())) {
                meals.add(item);
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        Object[][] data = new Object[meals.size()][5];
        String[] columns = new String[]
                {
                        "Gerecht", "Tafel", "Aantal", "Prijs", "Beschrijving", "Status"
                };
        for (int i = 0; i < meals.size(); i++) {
            OrderDetail orderItem = meals.get(i);

            data[i] = new Object[]
                    {
                            orderItem.getItemName(),orders.get(orderItem.getOrderId()).getTableNumber() , orderItem.getAmount(), "\u20ac" + decimalFormat.format(orderItem.getTotalPrice()), orderItem.getDescription(), orderItem.getStatus()
                    };
        }

        this.setDataVector(data, columns);
        this.fireTableDataChanged();
        refreshOrders(orderManager);
    }
    /* refreshed the JTable with the correct order data
                       *@param tableNumber int
                        *@param orderManager OrderManager
                        *@return void
                       */
    public void refreshOrders(OrderManager orderManager) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                HashMap<Integer, Order> orderMap = new HashMap<Integer, Order>();
                meals = new ArrayList<>();
                orderItems = new ArrayList<>();
                orders = orderManager.getAllOrders();
                for (Order o : orders) {
                    orderMap.put(o.getId(), o);
                    orderItems.addAll(o.getOrderDetails());

                }

                for (OrderDetail item : orderItems) {
                    if (!"Drankjes".equals(item.getCourseName())) {
                        meals.add(item);
                    }
                }
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                Object[][] data = new Object[meals.size()][5];
                String[] columns = new String[]
                        {
                                "Gerecht", "Tafel", "Aantal", "Prijs", "Beschrijving", "Status"
                        };
                for (int i = 0; i < meals.size(); i++) {
                    OrderDetail orderItem = meals.get(i);

                    data[i] = new Object[]
                            {
                                    orderItem.getItemName(),orderMap.get(orderItem.getOrderId()).getTableNumber() , orderItem.getAmount(), "\u20ac" + decimalFormat.format(orderItem.getTotalPrice()), orderItem.getDescription(), orderItem.getStatus()
                            };
                }

                SimpleMealTableModel.this.setDataVector(data, columns);
                SimpleMealTableModel.this.fireTableDataChanged();
            }
        }, 0, 10000);

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
