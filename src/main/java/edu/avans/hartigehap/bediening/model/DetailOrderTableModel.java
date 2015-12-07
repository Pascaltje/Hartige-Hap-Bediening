package edu.avans.hartigehap.bediening.model;

import edu.avans.hartigehap.bediening.logic.OrderManager;

import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by rubie_000 on 30-11-2015.
 */
public class DetailOrderTableModel extends DefaultTableModel {


    private ArrayList<OrderDetail> orderItems;

    public DetailOrderTableModel(OrderManager orderManager,int tableNumber) {

        Order newOrder = null;
        newOrder = orderManager.getOrderByTableNumber(tableNumber);
        orderItems = newOrder.getOrderDetails();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        Object[][] data = new Object[orderItems.size()][5];
        String[] columns = new String[]
                {
                        "Naam", "EmployeeId", "Aantal", "Prijs", "Beschrijving", "Status"
                };
        for (int i = 0; i < orderItems.size(); i++) {
            OrderDetail orderItem = orderItems.get(i);
            data[i] = new Object[]
                    {
                            orderItem.getItemName(), orderItem.getEmployeeId(), orderItem.getAmount(), orderItem.getTotalPrice(), orderItem.getDescription(), orderItem.getStatus()
                    };
        }
        this.setDataVector(data, columns);
        this.fireTableDataChanged();
        refreshOrders(orderManager,tableNumber);

    }

    public void refreshOrders(OrderManager orderManager,int tableNumber) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Order newOrder = null;
                newOrder = orderManager.getOrderByTableNumber(tableNumber);
                orderItems = newOrder.getOrderDetails();
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                Object[][] data = new Object[orderItems.size()][5];
                String[] columns = new String[]
                        {
                                "Naam", "EmployeeId", "Aantal", "Prijs", "Beschrijving", "Status"
                        };
                for (int i = 0; i < orderItems.size(); i++) {
                    OrderDetail orderItem = orderItems.get(i);
                    data[i] = new Object[]
                            {
                                    orderItem.getItemName(), orderItem.getEmployeeId(), orderItem.getAmount(), "\u20ac" + decimalFormat.format(orderItem.getTotalPrice()), orderItem.getDescription(), orderItem.getStatus()
                            };
                }
                DetailOrderTableModel.this.setDataVector(data, columns);
                DetailOrderTableModel.this.fireTableDataChanged();
            }
        }, 0, 10000);



    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}