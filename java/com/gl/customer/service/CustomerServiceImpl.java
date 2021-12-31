package com.gl.customer.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gl.customer.entities.Customer;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	
	private SessionFactory sessionFactory;
	
	private Session session;
	
	@Autowired
	public CustomerServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		try {
			session = sessionFactory.getCurrentSession();
		}
		catch(HibernateException ex) {
			session = sessionFactory.openSession();
		}
	}

	@Override
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		Transaction tx = session.beginTransaction();
		List<Customer> customers = session.createQuery("from Customer").list();
		tx.commit();
		return customers;
	}

	@Override
	@Transactional
	public Customer findById(int id) {
		// TODO Auto-generated method stub
		Transaction tx = session.beginTransaction();
		Customer customer = session.get(Customer.class, id);
		tx.commit();
		return customer;
	}

	@Override
	@Transactional
	public void save(Customer customer) {
		// TODO Auto-generated method stub
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(customer);
		tx.commit();
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		Transaction tx = session.beginTransaction();
		Customer customer = session.get(Customer.class, id);
		session.delete(customer);
		tx.commit();
		
	}

	@Override
	@Transactional
	public List<Customer> searchBy(String first_name, String last_name, String email) {
		// TODO Auto-generated method stub
		Transaction tx = session.beginTransaction();
		
		String query="";
		if(first_name.length()!=0 && last_name.length()!=0 && email.length()!=0) {
			query = "from Customer where first_name like '%"+first_name+"%' or last_name like '%"+last_name+"%' or email like '%"+email+"%'";
		}
		else if(first_name.length()!=0) {
			query = "from Customer where first_name like '%"+first_name+"%'";
		}
		else if(last_name.length()!=0) {
			query = "from Customer where last_name like '%"+last_name+"%'";
		}
		else if(email.length()!=0) {
			query = "from Customer where email like '%"+email+"%'";
		}
		
		List<Customer> customers = session.createQuery(query).list();
		tx.commit();
		return customers;
		
	}


}
