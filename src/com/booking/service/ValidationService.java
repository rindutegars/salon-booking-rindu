package com.booking.service;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;
import java.util.List;

public class ValidationService {
    // Buatlah function sesuai dengan kebutuhan
    private static List<Person> PersonList = PersonRepository.getAllPerson();
    private static List<Service> ServiceList = ServiceRepository.getAllService();
    
    public static void validateInput(){
    }
    
    public static boolean validateId(String id, String role) {
        //untuk customer dan employee
        for (Person person : PersonList) {
            if (role.equalsIgnoreCase("customer") && person instanceof Customer && ((Customer) person).getId().equals(id)) {
                return true;
            } else if (role.equalsIgnoreCase("employee") && person instanceof Employee && ((Employee) person).getId().equals(id)) {
                return true;
            }
        }
        //untuk service
        if (role.equalsIgnoreCase("service")) {
            for (Service service : ServiceList) {
                if (service.getServiceId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static Customer getCustomerById(String customerId) {
        for (Person person : PersonList) {
            if (person instanceof Customer && ((Customer) person).getId().equals(customerId)) {
                return (Customer) person;
            }
        }
        return null;
    }
}
