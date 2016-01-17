package edu.avans.hartigehap.bediening.model;

import edu.avans.hartigehap.bediening.logic.OrderManager;

import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by rubie_000 on 29-11-2015.
 */
public class OrderTableModel extends DefaultTableModel
{


	public OrderTableModel(Order order,OrderManager manager)
	{
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		Object[][] data = new Object[1][5];
		String[] columns = new String[]
		{
			"TafelNummer", "Gastnaam", "Totale prijs", "Datum", "Status"
		};

		data[0] = new Object[]
		{
			order.getTableNumber(), order.getGuestId(), order.getTotalAmount(), order.getDate(), order.getPaymentStatus()
		};
		this.setDataVector(data, columns);
		this.fireTableDataChanged();
		refreshOrders(manager,order.getTableNumber());
	}



	public void refreshOrders(OrderManager manager, int tableNumber)
	{
		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run() {
				Order newOrder = manager.getOrderByTableNumber(tableNumber);
				if (newOrder != null) {
					DecimalFormat decimalFormat = new DecimalFormat("0.00");
					Object[][] data = new Object[1][5];
					String[] columns = new String[]
							{
									"TafelNummer", "Gastnaam", "Totale prijs", "Datum", "Status"
							};

					data[0] = new Object[]
							{
									newOrder.getTableNumber(), newOrder.getGuestId(), newOrder.getTotalAmount(), newOrder.getDate(), newOrder.getPaymentStatus()
							};

					OrderTableModel.this.setDataVector(data, columns);
					OrderTableModel.this.fireTableDataChanged();
				}
				else
				{
					Object[][] data = new Object[1][5];
					String[] columns = new String[]
							{
									"TafelNummer", "Gastnaam", "Totale prijs", "Datum", "Status"
							};

					data[0] = new Object[]
							{

							};
					OrderTableModel.this.setDataVector(data, columns);
					OrderTableModel.this.fireTableDataChanged();
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
