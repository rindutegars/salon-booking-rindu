package com.booking.service;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import java.util.List;

import com.booking.models.Reservation;
import com.booking.models.Service;
import static com.booking.repositories.PersonRepository.getAllPerson;
import java.util.stream.Collectors;

public class PrintService {
    public static void printMenu(String title, String[] menuArr){
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {   
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);   
            num++;
        }
    }

    public String printServices(List<Service> serviceList){
        String result = "";
        // Bisa disesuaikan kembali
        for (Service service : serviceList) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public void showRecentReservation(List<Reservation> reservationList) {
        System.out.printf("| %-4s | %-8s | %-15s | %-20s | %-15s | %-10s |\n",
            "No.", "ID", "Nama Customer", "Service", "Total Biaya", "Workstage");
        System.out.println("+==========================================================================================+");
        
        for (int reservationNumber = 0; reservationNumber < reservationList.size(); reservationNumber++) {
            Reservation reservation = reservationList.get(reservationNumber);
            Customer customer = reservation.getCustomer();
            List<Service> services = reservation.getServices();
            String serviceNames = services.stream().map(Service::getServiceName).collect(Collectors.joining(", "));
            
            if (reservation.getWorkstage().equalsIgnoreCase("Waiting") ||
                reservation.getWorkstage().equalsIgnoreCase("In process")) {
                System.out.printf("| %-4s | %-8s | %-15s | %-20s | %-15s | %-10s |\n",
                    reservationNumber + 1,
                    reservation.getReservationId(),
                    customer.getName(),
                    serviceNames,
                    reservation.getReservationPrice(),
                    reservation.getWorkstage()
                );
            }
        }
        System.out.println("................................................................................................................................................................................");
    }

    public void showAllCustomer(List<Person> PersonList) {
        int num = 1;
        System.out.printf("| %-4s | %-8s | %-15s | %-8s | %-15s | %-8s |\n",
            "No.", "ID", "Name", "Address", "Membership", "Uang");
        System.out.println("+=============================================================================+");
        for (Person person : PersonList) {
            if (person instanceof Customer) {
                Customer customer = (Customer) person;
                System.out.printf("| %-4s | %-8s | %-15s | %-8s | %-15s | %-8s |\n",
                    num, customer.getId(), customer.getName(), customer.getAddress(),
                    customer.getMember().getMembershipName(), customer.getWallet());
                num++;
            }
        }
        System.out.println("................................................................................................................................................................................");
    }

    public void showAllEmployee(List<Person> PersonList){
        int num = 1;
        System.out.printf("| %-4s | %-8s | %-15s | %-8s | %-15s |\n",
            "No.", "ID", "Name", "Address", "Pengalaman");
        System.out.println("+==================================================================+");
        for (Person person : PersonList) {
            if (person instanceof Employee) {
                Employee employee = (Employee) person;
                System.out.printf("| %-4s | %-8s | %-15s | %-8s | %-15s |\n",
                    num, employee.getId(), employee.getName(), employee.getAddress(),
                    employee.getExperience());
                num++;
            }
        }
        System.out.println("................................................................................................................................................................................");
    }
    
    public static void showHistoryReservation(List<Reservation> reservationList) {
        System.out.printf("| %-4s | %-8s | %-15s | %-20s | %-15s | %-10s |\n",
            "No.", "ID", "Nama Customer", "Service", "Total Biaya", "Workstage");
        System.out.println("+==========================================================================================+");

        int reservationNumber = 1;
        double totalKeuntungan = 0.0;

        for (Reservation reservation : reservationList) {
            Customer customer = reservation.getCustomer();
            List<Service> services = reservation.getServices();
            String serviceNames = services.stream().map(Service::getServiceName).collect(Collectors.joining(", "));

            System.out.printf("| %-4s | %-8s | %-15s | %-20s | %-15s | %-10s |\n",
                reservationNumber++,
                reservation.getReservationId(),
                customer.getName(),
                serviceNames,
                reservation.getReservationPrice(),
                reservation.getWorkstage()
            );

            if (reservation.getWorkstage().equalsIgnoreCase("Finish")) {
                totalKeuntungan += reservation.getReservationPrice();
            }
        }
        
        System.out.printf("| %-65s | %-15s |\n", "Total Keuntungan", "Rp. " + totalKeuntungan);
        System.out.printf("+==========================================================================================+\n");
        System.out.println("................................................................................................................................................................................");
    }
    
    public void ShowAllService(List <Service> ServiceList){
        int num = 1;
        System.out.printf("| %-4s | %-8s | %-20s | %-8s |\n",
            "No.", "ID", "Name", "Harga");
        System.out.println("+=====================================================+");
        for (Service service : ServiceList){
            System.out.printf("| %-4s | %-8s | %-20s | %-8s |\n",
                num, service.getServiceId(), service.getServiceName(), service.getPrice());
            num++;
        }
        System.out.println("................................................................................................................................................................................");
    }
}
