package com.booking.models;

import com.booking.repositories.PersonRepository;
import static com.booking.service.ReservationService.getCustomerById;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private String reservationId;
    private Customer customer;
    private Employee employee;
    private List<Service> services;
    private double reservationPrice;
    private String workstage;
    // workStage (In Process, Finish, Canceled)
   


    public Reservation(String reservationId, Customer customer, Employee employee, List<Service> services,
            String workstage) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.employee = employee;
        this.services = services;
        this.reservationPrice = calculateReservationPrice(customer, services); // Call the method here
        this.workstage = workstage;
    }

    // hitung reservasi customer yang membership
    public double calculateReservationPrice(Customer customer, List<Service> services) {
        Membership customerMembership = customer.getMember();
        double totalServicePrice = services.stream().mapToDouble(Service::getPrice).sum();
        double discount = 0.0;

        if (customerMembership != null) {
            String membershipName = customerMembership.getMembershipName();
            switch (membershipName) {
                case "Silver": 
                    discount = 0.05;
                    break;
                case "Gold": 
                    discount = 0.10;
                    break;
                default:
                    break;
            }
        }

        double discountedPrice = totalServicePrice - (totalServicePrice * discount);
        return discountedPrice;
    }
   
    // hitung wallet customer setelah reservasi finish
    public void calculateWallet() {
        Customer customer = getCustomerById(this.customer.getId());
        if (customer != null) {
            double remainingWallet = customer.getWallet() - this.reservationPrice;
            customer.setWallet(remainingWallet);
        }
    }
}
