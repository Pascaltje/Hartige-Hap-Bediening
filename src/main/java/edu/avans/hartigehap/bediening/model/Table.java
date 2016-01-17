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
package edu.avans.hartigehap.bediening.model;

/**
 * @author David
 */
public class Table {

    private int tableNumber;
    private int seats;

    public Table(int tableNumber, int seats) {
        this.tableNumber = tableNumber;
        this.seats = seats;
    }

    /* get the number of the table
     *@return int
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /* set the number of the table
    *@param tableNumber int
   *@return int
   */
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    /* gets the seats from a table
     *@return int
     */
    public int getSeats() {
        return seats;
    }

    /* sets the seats from a table
    *@param seats int
    *@return int
    */
    public void setSeats(int seats) {
        this.seats = seats;
    }
}
