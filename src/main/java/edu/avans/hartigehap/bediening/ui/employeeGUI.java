/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.avans.hartigehap.bediening.ui;

import edu.avans.hartigehap.bediening.logic.EmployeeManager;
import edu.avans.hartigehap.bediening.model.Employee;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author Pascal van Hoof
 */
public class employeeGUI extends JFrame{
    private EmployeeManager EmployeeManager;
    private JTextField txtEmail;
    private JList EmplayersList;
    private DefaultListModel EmplayersListModel;
    public employeeGUI(){
        this.EmployeeManager = EmployeeManager.getInstance();
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Hartige Hap");
        setLayout(new BorderLayout());
        
        getContentPane().add(DrawLeftPanel(), BorderLayout.WEST);
        getContentPane().add(DrawRightPanel(), BorderLayout.EAST);
        setVisible(true);
        
        AddLoggedidUsers();
    }
    
    public Panel DrawLeftPanel(){
        Panel LeftPanel = new Panel();
        LeftPanel.setPreferredSize(new Dimension(290 , 400));
        JLabel lb = new JLabel("Email: ");
        txtEmail = new JTextField(15);
        JButton bt = new JButton("Login");
        bt.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
               Employee employee = EmployeeManager.logIn(txtEmail.getText());
               if(employee!=null){
                   EmplayersListModel.addElement(employee.getFirstName() + " " + employee.getLastName());
                           
                   
               }else{
                   Component component = (Component) ae.getSource();
                   JOptionPane.showMessageDialog((JFrame) SwingUtilities.getRoot(component),"Email adres bestaad niet.");
               }
               txtEmail.setText("");
            }
        });
        
       LeftPanel.add(lb); 
       LeftPanel.add(txtEmail);
       LeftPanel.add(bt);
       return LeftPanel;


    }
    
    public Panel DrawRightPanel(){
        Panel RightPanel = new Panel();
        JLabel lb = new JLabel("Ingelogde medewerkers");
        lb.setHorizontalAlignment(SwingConstants.CENTER);
        RightPanel.setLayout( new BorderLayout(10,10) );
        RightPanel.setPreferredSize(new Dimension(275 , 400));

        EmplayersListModel = new DefaultListModel();
        EmplayersList = new JList(EmplayersListModel);
        
        
        JButton bt = new JButton("Afmelden");
        bt.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                EmployeeManager.logOut((String) EmplayersList.getSelectedValue());
                EmplayersListModel.remove(EmplayersList.getSelectedIndex());
            }
        });
        
        RightPanel.add(lb,BorderLayout.NORTH );
        RightPanel.add(EmplayersList,BorderLayout.CENTER );
        RightPanel.add(bt,BorderLayout.SOUTH );
        
       return RightPanel;        
    }
    
    private void AddLoggedidUsers(){
        ArrayList<Employee> users = EmployeeManager.getEmployee();
        for(Employee employee : users ){
             EmplayersListModel.addElement(employee.getFirstName() + " " + employee.getLastName());
        }
    }
    
}
