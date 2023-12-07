package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;

public class MenuService {
    private static List<Person> PersonList = PersonRepository.getAllPerson();
    private static List<Service> ServiceList = ServiceRepository.getAllService();
    private static List<Reservation> reservationList = new ArrayList<>();
    private static PrintService printService = new PrintService();
    private static ReservationService reservationService = new ReservationService();
    
    private static Scanner input = new Scanner(System.in);

    public static void mainMenu() {
        String[] mainMenuArr = {"Tampilkan Data", "Membuat Reservasi", "Finish / Cancel Reservasi", "Exit"};
        String[] subMenuArr = {"Tampilkan Recent Reservation", "Tampilkan Customer", "Tampilkan Employee", "Tampilkan History Reservation + Total Keuntungan", "Back to main menu"};

        int optionMainMenu;
        int optionSubMenu;

        boolean backToMainMenu = false;
        boolean backToSubMenu = false;

        do {
            PrintService.printMenu("Aplikasi Booking Salon", mainMenuArr);
            try {
                System.out.print("Pilih>>");
                optionMainMenu = Integer.valueOf(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, masukkan nomer yang valid.");
                continue;
            }

            switch (optionMainMenu) {
                case 1:
                    backToSubMenu = true;
                    do {
                        System.out.println("................................................................................................................................................................................");                        PrintService.printMenu("Data Booking Salon", subMenuArr);
                        try {
                            System.out.print("Pilih>>");
                            optionSubMenu = Integer.valueOf(input.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Input tidak valid, masukkan nomer yang valid.");
                            continue;
                        }
                        // Sub menu - menu 1
                        switch (optionSubMenu) {
                            case 1:
                                //panggil fitur semua recent reservasi
                                printService.showRecentReservation(ReservationService.getReservationList());
                                break;
                            case 2:
                                // panggil fitur tampilkan semua customer
                                printService.showAllCustomer(PersonList);
                                break;
                            case 3:
                                // panggil fitur tampilkan semua employee
                                printService.showAllEmployee(PersonList);
                                break;
                            case 4:
                                //panggil fitur history reservasi dan keuntungan
                                printService.showHistoryReservation(ReservationService.getReservationList());
                                break;
                            case 0:
                                backToSubMenu = true;
                                break;
                            default:
                                System.out.println("Tidak valid, pilih option yang valid");
                        }
                    } while (!backToSubMenu);
                    break;
                case 2:
                    // panggil fitur menambahkan reservation
                    reservationService.createReservation();
                    break;
                case 3:
                    //panggil fitur edit workstage reservasi
                    reservationService.editReservationWorkstage();
                    break;
                case 0:
                    backToMainMenu = true;
                    System.out.println("Aplikasi berakhir!");
                    break;
                default:
                    System.out.println("Tidak valid, pilih option yang valid");
            }
        } while (!backToMainMenu);
    }
}

