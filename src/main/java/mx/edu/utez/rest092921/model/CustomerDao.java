package mx.edu.utez.rest092921.model;


import mx.edu.utez.rest092921.database.ConnectionMysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    private Connection con;
    private CallableStatement cstm;
    private ResultSet rs;

    public List<Customer> findAll(){
        List<Customer> listCustomers = new ArrayList<>();

        try{
            con = ConnectionMysql.getConnection();
            cstm = con.prepareCall("SELECT * FROM customers;");
            rs = cstm.executeQuery();

            while(rs.next()){
                Customer customer = new Customer();

                customer.setCustomerName(rs.getString("customerName"));
                customer.setContactFirstName(rs.getString("contactFirstName"));
                customer.setContactLastName(rs.getString("contactLastName"));
                customer.setPhone(rs.getString("phone"));

                listCustomers.add(customer);
            }
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMysql.closeConnections(con, cstm, rs);
        }
        return listCustomers;
    }

    public Customer findByCustomerNumber(int customerNumber){
        Customer customer = null;

        try{
            con = ConnectionMysql.getConnection();
            cstm = con.prepareCall("SELECT * FROM customer WHERE customerNumber = ?;");
            cstm.setInt(1, customerNumber);
            rs = cstm.executeQuery();

            if(rs.next()){
                customer = new Customer();

                customer.setCustomerName(rs.getString("customerName"));
                customer.setContactFirstName(rs.getString("contactFirstName"));
                customer.setContactLastName(rs.getString("contactLastName"));
                customer.setPhone(rs.getString("phone"));

            }
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMysql.closeConnections(con, cstm, rs);
        }
        return customer;
    }

    public boolean save(Customer customer, boolean isCreate){
        boolean flag = false;

        try{
            con = ConnectionMysql.getConnection();
            if(isCreate){
                cstm = con.prepareCall("INSERT INTO customers(customerNumber,customerName,contactLastName,contactFirstName,phone,addressLine1,addressLine2,city," +
                        "state,postalCode,country,salesRepEmployeeNumber,creditLimit)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);");

                cstm.setInt(1, customer.getCustomerNumber());
                cstm.setString(2, customer.getCustomerName());
                cstm.setString(3, customer.getContactLastName());
                cstm.setString(4, customer.getContactFirstName());
                cstm.setString(5, customer.getPhone());
                cstm.setString(6, customer.getAddressLine1());
                cstm.setString(7, customer.getAddressLine2());
                cstm.setString(8, customer.getCity());
                cstm.setString(9, customer.getState());
                cstm.setString(10, customer.getPostalCode());
                cstm.setString(11, customer.getCountry());
                cstm.setInt(12, customer.getSalesRepEmployeeNumber());
                cstm.setDouble(13, customer.getCreditLimit());

            } else {
                cstm = con.prepareCall("UPDATE customer SET customerName = ?, contactLastName = ?, contactFirstName = ?, phone = ? WHERE customerNumber = ?;");

                cstm.setString(1, customer.getCustomerName());
                cstm.setString(2, customer.getContactLastName());
                cstm.setString(3, customer.getContactFirstName());
                cstm.setString(4, customer.getPhone());
                cstm.setInt(5, customer.getCustomerNumber());
            }

            flag = cstm.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMysql.closeConnections(con, cstm, rs);
        }
        return flag;
    }

    public boolean delete(int customerNumber){
        boolean flag = false;

        try{
            con = ConnectionMysql.getConnection();
            cstm = con.prepareCall("DELETE FROM customers WHERE customerNumber = ?;");
            cstm.setInt(1, customerNumber);
            flag = cstm.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMysql.closeConnections(con, cstm, rs);
        }
        return flag;
    }
}