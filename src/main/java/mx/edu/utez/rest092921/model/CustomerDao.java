package mx.edu.utez.rest092921.model;


import mx.edu.utez.rest092921.database.ConnectionMysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    Connection con;
    PreparedStatement pstm;
    ResultSet rs;
    Statement stm;
    String query;

    public List<Customer> findAll(){
        List<Customer> listCustomers = new ArrayList();
        try{
            con = ConnectionMysql.getConnection();
            query = "SELECT * FROM customers;";
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            while(rs.next()){
                Customer customer = new Customer();
                customer.setCustomerNumber(rs.getInt("customerNumber"));
                customer.setCustomerName(rs.getString("customerName"));
                customer.setContactLastName(rs.getString("contactLastName"));
                customer.setContactFirstName(rs.getString("contactFirstName"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddressLine1(rs.getString("addressLine1"));
                customer.setAddressLine2(rs.getString("addressLine2"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setPostalCode(rs.getString("postalCode"));
                customer.setCountry(rs.getString("country"));
                customer.setSalesRepEmployeeNumber(rs.getInt("salesRepEmployeeNumber"));
                customer.setCreditLimit(rs.getDouble("creditLimit"));

                listCustomers.add(customer);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            closeConnections();
        }
        return listCustomers;
    }

    public Customer findByCustomerNumber(int customerNumber){
        Customer customer = null;
        try{
            con = ConnectionMysql.getConnection();
            query = "SELECT * FROM customers WHERE customerNumber = ?";
            pstm = con.prepareStatement(query);
            pstm.setInt(1, customerNumber);
            rs = pstm.executeQuery();
            if(rs.next()){
                customer = new Customer();
                customer.setCustomerNumber(rs.getInt("customerNumber"));
                customer.setCustomerName(rs.getString("customerName"));
                customer.setContactLastName(rs.getString("contactLastName"));
                customer.setContactFirstName(rs.getString("contactFirstName"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddressLine1(rs.getString("addressLine1"));
                customer.setAddressLine2(rs.getString("addressLine2"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setPostalCode(rs.getString("postalCode"));
                customer.setCountry(rs.getString("country"));
                customer.setSalesRepEmployeeNumber(rs.getInt("salesRepEmployeeNumber"));
                customer.setCreditLimit(rs.getDouble("creditLimit"));
            }
        }catch (SQLException | NullPointerException e){
            e.printStackTrace();
        }finally {
            closeConnections();
        }
        return customer;
    }

    public boolean saveCustomer(Customer customer, boolean isCreated){
        boolean state = false;
        try{
            con = ConnectionMysql.getConnection();
            if(isCreated){
                query = "INSERT INTO customers(customerNumber, customerName, contactLastName, contactFirstName, phone, addressLine1, addressLine2," +
                        "city, state, postalCode, country, salesRepEmployeeNumber, creditLimit) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                pstm = con.prepareStatement(query);
                pstm.setInt(1, customer.getCustomerNumber());
                pstm.setString(2, customer.getCustomerName());
                pstm.setString(3, customer.getContactLastName());
                pstm.setString(4, customer.getContactFirstName());
                pstm.setString(5, customer.getPhone());
                pstm.setString(6, customer.getAddressLine1());
                pstm.setString(7, customer.getAddressLine2());
                pstm.setString(8, customer.getCity());
                pstm.setString(9, customer.getState());
                pstm.setString(10, customer.getPostalCode());
                pstm.setString(11, customer.getCountry());
                pstm.setInt(12, customer.getSalesRepEmployeeNumber());
                pstm.setDouble(13, customer.getCreditLimit());
                state = pstm.executeUpdate() == 1;
            }else{
                query = "UPDATE customers SET customerName = ?, contactLastName = ?, contactFirstName = ?, phone = ?, addressLine1 = ?, " +
                        "addressLine2 = ?, city = ?, state = ?, postalCode = ?, country = ?, salesRepEmployeeNumber = ?, creditLimit = ?" +
                        "WHERE customerNumber = ?";
                pstm = con.prepareStatement(query);
                pstm.setInt(13, customer.getCustomerNumber());
                pstm.setString(1, customer.getCustomerName());
                pstm.setString(2, customer.getContactLastName());
                pstm.setString(3, customer.getContactFirstName());
                pstm.setString(4, customer.getPhone());
                pstm.setString(5, customer.getAddressLine1());
                pstm.setString(6, customer.getAddressLine2());
                pstm.setString(7, customer.getCity());
                pstm.setString(8, customer.getState());
                pstm.setString(9, customer.getPostalCode());
                pstm.setString(10, customer.getCountry());
                pstm.setInt(11, customer.getSalesRepEmployeeNumber());
                pstm.setDouble(12, customer.getCreditLimit());
                state = pstm.executeUpdate() == 1;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnections();
        }
        return state;
    }

    public boolean deleteCustomer(int customerNumber){
        boolean state = false;
        try{
            con = ConnectionMysql.getConnection();
            query = "DELETE FROM customers WHERE customerNumber = ?;";
            pstm = con.prepareStatement(query);
            pstm.setInt(1, customerNumber);
            state = pstm.executeUpdate() == 1;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnections();
        }
        return state;
    }

    public void closeConnections(){
        try{
            if(con != null){
                con.close();
            }
            if(pstm != null){
                pstm.close();
            }
            if(rs != null){
                rs.close();
            }
            if(stm != null){
                stm.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}