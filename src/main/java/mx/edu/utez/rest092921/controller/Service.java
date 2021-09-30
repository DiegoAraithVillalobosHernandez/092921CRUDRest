package mx.edu.utez.rest092921.controller;

import mx.edu.utez.rest092921.model.Customer;
import mx.edu.utez.rest092921.model.CustomerDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;

@Path("/customer")
public class Service {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getCustomers(){
        return new CustomerDao().findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getCustomers(@PathParam("id") int customerNumber){
        return new CustomerDao().findByCustomerNumber(customerNumber);
    }

    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Customer save(MultivaluedMap<String, String> formParams){
        int customerNumber = Integer.parseInt(formParams.get("customerNumber").get(0));
        if(new CustomerDao().save(getParams(customerNumber, formParams), true))
            return new CustomerDao().findByCustomerNumber(customerNumber);
        return null;
    }

    @POST
    @Path("/save/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Employee save(@PathParam("id") int employeeNumber, MultivaluedMap<String, String> formParams){
        if(new EmployeeDAO().save(getParams(employeeNumber, formParams), false))
            return new EmployeeDAO().findByEmployeeNumber(employeeNumber);
        return null;
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteEmployee(@PathParam("id") int employeeNumber){
        return new EmployeeDAO().delete(employeeNumber);
    }

    private Customer getParams(int customerNumber, MultivaluedMap<String, String> formParams){
        String lastName = formParams.get("lastName").get(0);
        String firstName = formParams.get("firstName").get(0);
        String extension = formParams.get("extension").get(0);
        String email = formParams.get("email").get(0);
        int officeCode = Integer.parseInt(formParams.get("officeCode").get(0));
        int reportsTo = Integer.parseInt(formParams.get("reportsTo").get(0));
        String jobTitle = formParams.get("jobTitle").get(0);

        Employee employee = new Employee(employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle);
        System.out.println(employee);
        return employee;
    }
}

