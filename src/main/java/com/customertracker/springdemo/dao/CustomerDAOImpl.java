package com.customertracker.springdemo.dao;

import com.customertracker.springdemo.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO{

    // inject SessionFactory
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Customer> query = currentSession.createQuery("from Customer order by lastName", Customer.class);

        List<Customer> customers = query.getResultList();

        return customers;
    }

    @Override
    public void saveCustomer(Customer theCustomer) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(theCustomer);
    }

    @Override
    public Customer getCustomer(int theId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Customer.class, theId);
    }

    @Override
    public void deleteCustomer(int theId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Customer where id=:theCustomerId");
        query.setParameter("theCustomerId", theId);
        query.executeUpdate();
    }

    @Override
    public List<Customer> searchCustomer(String searchName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = null;
        if(searchName != null && searchName.trim().length() > 0) {
            query = session.createQuery("from Customer where lower(firstName) like :theSearchName " +
                    "or lower(lastName) like :theSearchName", Customer.class); // using LIKE operator
            query.setParameter("theSearchName", "%" + searchName.toLowerCase() + "%");
            List<Customer> customerList = query.getResultList();
            return customerList;
        }
        else{
            query = session.createQuery("from Customer", Customer.class);
            List<Customer> customerList = query.getResultList();
            return customerList;
        }
    }
}
