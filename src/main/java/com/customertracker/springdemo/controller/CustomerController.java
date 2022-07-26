package com.customertracker.springdemo.controller;

import com.customertracker.springdemo.dao.CustomerDAO;
import com.customertracker.springdemo.entity.Customer;
import com.customertracker.springdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    // inject CustomerService
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomers(Model theModel){

        // get customers from DAO
        List<Customer> theCustomers = customerService.getCustomers();

        // add customers to the model
        theModel.addAttribute("customers", theCustomers);

        return "list-customer";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){
        Customer theCustomer = new Customer();
        theModel.addAttribute("customer", theCustomer);
        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer theCustomer){
        // save using customer service
        customerService.saveCustomer(theCustomer);

        return "redirect:/customer/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel){
        Customer theCustomer = customerService.getCustomer(theId);
        theModel.addAttribute("customer", theCustomer);
        return "customer-form";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam("customerId") int theId){
        customerService.deleteCustomer(theId);
        return "redirect:/customer/list";
    }

    @GetMapping("/search")
    public String searchCustomer(@RequestParam("searchName") String searchName, Model theModel){
        List<Customer> theCustomers = customerService.searchCustomer(searchName);
        theModel.addAttribute("customers", theCustomers);
        return "list-customer";
    }
}
