package edu.avans.hartigehap.bediening.model;

import edu.avans.hartigehap.bediening.logic.OrderManager;

import javax.swing.table.DefaultTableModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by rubie_000 on 8-12-2015.
 */
public class MealTableModel extends DefaultTableModel
{

	private List<OrderDetail> orderItems;
	/* populates the JTable with the correct order data
                       *@param tableNumber int
                        *@param orderManager OrderManager
                        *@return void
                       */
	public MealTableModel(OrderManager orderManager, int tableNumber)
	{
		ArrayList<OrderDetail> meals = new ArrayList<>();
		Order newOrder = orderManager.getOrderByTableNumber(tableNumber);
		orderItems = newOrder.getOrderDetails();
		for (OrderDetail meal : orderItems)
		{
			if (!"Drankjes".equals(meal.getCourseName()))
			{
				meals.add(meal);
			}
		}
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		Object[][] data = new Object[meals.size()][5];
		String[] columns = new String[]
		{
				"Naam", "EmployeeId", "Antal", "Prijs", "Beschrijving", "Status"
		};
		for (int i = 0; i < meals.size(); i++)
		{
			OrderDetail orderItem = meals.get(i);
			data[i] = new Object[]
			{
					orderItem.getItemName(), orderItem.getEmployeeId(), orderItem.getAmount(), "\u20ac" + decimalFormat.format(orderItem.getTotalPrice()), orderItem.getDescription(), orderItem.getStatus()
			};
		}

		this.setDataVector(data, columns);
		this.fireTableDataChanged();
		refreshOrders(orderManager, tableNumber);

	}
	/* refreshes the JTable with the correct order data
                       *@param tableNumber int
                        *@param orderManager OrderManager
                        *@return void
                       */
	public void refreshOrders(OrderManager orderManager, int tableNumber)
	{
		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run() {
				List<OrderDetail> meals = new ArrayList<>();
				Order newOrder = orderManager.getOrderByTableNumber(tableNumber);
				if (newOrder != null) {
					orderItems = newOrder.getOrderDetails();
					for (OrderDetail meal : orderItems) {
						if (!"Drankjes".equals(meal.getCourseName())) {
							meals.add(meal);
						}
					}
					DecimalFormat decimalFormat = new DecimalFormat("0.00");
					Object[][] data = new Object[meals.size()][5];
					String[] columns = new String[]
							{
									"Naam", "EmployeeId", "Antal", "Prijs", "Beschrijving", "Status"
							};
					for (int i = 0; i < meals.size(); i++) {
						OrderDetail orderItem = meals.get(i);
						data[i] = new Object[]
								{
										orderItem.getItemName(), orderItem.getEmployeeId(), orderItem.getAmount(), "\u20ac" + decimalFormat.format(orderItem.getTotalPrice()), orderItem.getDescription(), orderItem.getStatus()
								};
					}
					MealTableModel.this.setDataVector(data, columns);
					MealTableModel.this.fireTableDataChanged();
				}
				else{
					DecimalFormat decimalFormat = new DecimalFormat("0.00");
					Object[][] data = new Object[meals.size()][5];
					String[] columns = new String[]
							{
									"Naam", "EmployeeId", "Antal", "Prijs", "Beschrijving", "Status"
							};
					for (int i = 0; i < meals.size(); i++) {
						OrderDetail orderItem = meals.get(i);
						data[i] = new Object[]
								{

								};
					}
					MealTableModel.this.setDataVector(data, columns);
					MealTableModel.this.fireTableDataChanged();
				}
			}

		}, 0, 10000);

	}

	@Override
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}

}
