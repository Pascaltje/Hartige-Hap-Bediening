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
package edu.avans.hartigehap.bediening.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class DatabaseConnection
{

    private static final Logger LOG = Logger.getLogger(DatabaseConnection.class.getName());

	private final String HOST = "145.48.6.147";
	private final String DATABASE = "ivp4a";
	private final String USER = "root";
	private final String PASSWORD = "10ec4u";
	
    private Connection connection;

    public DatabaseConnection()
    {
        connection = null;
    }
    /* opens the connection to the database
    *@return boolean
     */
    public boolean open()
    {
        boolean result = true;
        if(connection == null)
        {
            try
            {
                connection = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + DATABASE, USER, PASSWORD);
                result = true;
            } catch(SQLException exception)
            {
                LOG.log(Level.SEVERE, "", exception);
                result = false;
            }
        }
        return result;
    }
    /* checks if the database connection is open
      *@return boolean
       */
    public boolean isOpen()
    {
        if(connection != null)
        {
            try
            {
                return !connection.isClosed();
            } catch(SQLException exception)
            {
                LOG.log(Level.SEVERE, "", exception);
            }
        }
        return false;
    }
    /* closes the database connection
      *@return void
       */
    public void close()
    {
        try
        {
            connection.close();
        } catch(SQLException exception)
        {
            LOG.log(Level.SEVERE, "", exception);
        }
    }
    /* function for a createstatement
      *@return PreparedStatement
       */
    public PreparedStatement createStatement(String query)
    {
        if(query != null && isOpen())
        {
            try
            {
                return connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            } catch(SQLException exception)
            {
                LOG.log(Level.SEVERE, "", exception);
            }
        }
        return null;
    }
    /* function for a update statment
      *@return boolean
       */
    public boolean executeUpdate(PreparedStatement query)
    {
        try
        {
            return query.executeUpdate() > 0;
        } catch(SQLException exception)
        {
            LOG.log(Level.SEVERE, "", exception);
        }
        return false;
    }
    /* function for executing an preparedstatment
      *@return ResultSet
       */
    public ResultSet execute(PreparedStatement query)
    {
        try
        {
            return query.executeQuery();
        } catch(SQLException exception)
        {
            LOG.log(Level.SEVERE, "", exception);
        }
        return null;
    }
}