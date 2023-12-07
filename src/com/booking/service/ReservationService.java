package com.booking.service;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;
import static com.booking.service.ValidationService.validateId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;


public class ReservationService {
    private static List<Person> PersonList = PersonRepository.getAllPerson();
    private static PrintService printService = new PrintService();
    private static List<Service> ServiceList = ServiceRepository.getAllService();
    private static List<Reservation> reservationList = new ArrayList<>();
    private static int lastReservationId = 0; 
    private static Scanner input = new Scanner(System.in);
    
    public static void createReservation() {   
        lastReservationId++;
        String reservationId = "Rsv-" + String.format("%03d", lastReservationId);
        System.out.println("Reservation ID generated: " + reservationId);

        printService.showAllCustomer(PersonList);
        String customerId = "";
        boolean isCustomerFound = false;
        while (!isCustomerFound) {
            System.out.print("Silahkan Masukkan Customer Id: ");
            customerId = input.nextLine();
            isCustomerFound = validateId(customerId, "customer");
            if (!isCustomerFound) {
                System.out.println("Customer yang dicari tidak tersedia");
            }
        }
        
        printService.showAllEmployee(PersonList);
        String employeeId = "";
        boolean isEmployeeFound = false;
        while (!isEmployeeFound) {
            System.out.print("Silahkan Masukkan Employee Id: ");
            employeeId = input.nextLine();
            isEmployeeFound = validateId(employeeId, "employee");
            if (!isEmployeeFound) {
                System.out.println("Employee yang dicari tidak tersedia");
            }
        }

        printService.ShowAllService(ServiceList);
        List<Service> selectedServices = new ArrayList<>();
        Set<String> selectedServiceIds = new HashSet<>();
        boolean isServiceFound;
        do {
            String serviceId;
            do {
                System.out.print("Silahkan Masukkan Service Id: ");
                serviceId = input.nextLine();
                isServiceFound = validateId(serviceId, "service");

                if (!isServiceFound) {
                    System.out.println("Service yang dicari tidak tersedia");
                } else if (selectedServiceIds.contains(serviceId)) {
                    System.out.println("Service ini sudah dipilih sebelumnya. Pilih yang lain.");
                    isServiceFound = false;
                }
            } while (!isServiceFound);
            selectedServices.add(getServiceById(serviceId));
            selectedServiceIds.add(serviceId);
            System.out.print("Ingin pilih service yang lain (Y/T)? ");
            String response = input.nextLine().toUpperCase();
            if (!response.equals("Y") || selectedServiceIds.size() == ServiceList.size()) {
                break;
            }
        } while (true);
    
        Customer customer = getCustomerById(customerId);
        Employee employee = getEmployeeById(employeeId);
        
        Reservation newReservation = new Reservation(
                reservationId,
                customer,
                employee,
                selectedServices,
                "In Process"
        );
        reservationList.add(newReservation);

        System.out.println("Booking berhasil!");
        double totalCost = newReservation.getReservationPrice();
        System.out.println("Total Biaya Booking: Rp. " + totalCost);
    }

    public static Customer getCustomerById(String Id) {
        for (Person person : PersonList) {
            if (person instanceof Customer && ((Customer) person).getId().equals(Id)) {
                return (Customer) person;
            }
        }
        return null;
    }
    
    public static Employee getEmployeeById(String Id) {
        for (Person person : PersonList) {
            if (person instanceof Employee && ((Employee) person).getId().equals(Id)) {
                return (Employee) person;
            }
        }
        return null;
    }

    public static Service getServiceById(String serviceId) {
        for (Service service : ServiceList) {
            if (service.getServiceId().equals(serviceId)) {
                return service;
            }
        }
        return null;
    }


    public static void editReservationWorkstage() {
        Scanner input = new Scanner(System.in);
        printService.showRecentReservation(reservationList);
        String reservationId;
        Reservation reservationToEdit;

        do {
            System.out.print("Silahkan Masukkan Reservation Id: ");
            reservationId = input.nextLine();
            reservationToEdit = findReservationById(reservationId);
            if (reservationToEdit == null) {
                System.out.println("Reservasi dengan id " + reservationId + " tidak ditemukan. Silakan coba lagi.");
            }
        } while (reservationToEdit == null);

        boolean isValidChoice = false;
        while (!isValidChoice) {
            System.out.print("Selesaikan reservasi (Finish/Cancel): ");
            String userChoice = input.nextLine().toLowerCase();
            if ("finish".equals(userChoice) || "cancel".equals(userChoice)) {
                // Update Workstage
                reservationToEdit.setWorkstage(userChoice.substring(0, 1).toUpperCase() + userChoice.substring(1));
                System.out.println("Reservasi dengan id " + reservationId + " sudah " + userChoice);

                if ("finish".equals(userChoice)) {
                    // Recalculate Wallet karena dikurangi reservasi yang finish
                    reservationToEdit.calculateWallet();
                }

                updateReservationList(reservationToEdit);
                isValidChoice = true;
            } else {
                System.out.println("Pilihan tidak valid. Silakan masukkan 'Finish' atau 'Cancel'.");
            }
        }
    }
    
    private static void updateReservationList(Reservation editedReservation) {
        int index = reservationList.indexOf(editedReservation);
        reservationList.set(index, editedReservation);
    }
    
    private static Reservation findReservationById(String reservationId) {
        for (Reservation reservation : reservationList) {
            if (reservation.getReservationId().equals(reservationId)) {
                return reservation;
            }
        }
        return null; 
    }

    //agar data bisa bertambah
    public static List<Reservation> getReservationList() {
        return reservationList;
    }  
}
