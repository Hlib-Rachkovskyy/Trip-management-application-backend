package com.project.masp;

import com.project.masp.Models.Company.Company;
import com.project.masp.Models.Company.ContactForm;
import com.project.masp.Models.Enums.Activity;
import com.project.masp.Models.Enums.RegistrationState;
import com.project.masp.Models.Enums.Role;
import com.project.masp.Models.TouristService.Hotel;
import com.project.masp.Models.TouristService.HotelInTrip;
import com.project.masp.Models.TouristService.Vehicle;
import com.project.masp.Models.TouristService.VehicleInTrip;
import com.project.masp.Models.Trip.Announcement;
import com.project.masp.Models.Trip.Trip;
import com.project.masp.Models.Users.Organiser;
import com.project.masp.Models.Users.TripManager;
import com.project.masp.Models.Users.User;
import com.project.masp.Models.Users.UserInTrip;
import com.project.masp.Repository.*;
import org.apache.catalina.Manager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
@EntityScan("com.project.masp.Models")
public class MaspApplication {
    public static void main(String[] args) {
        SpringApplication.run(MaspApplication.class, args);
    }
    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("*");
        }
    }
    @Bean
    public CommandLineRunner commandLineRunner(
        CompanyRepository companyRepository,
        UserRepository userRepository,
        AnnouncementRepository announcementRepository,
        ContactFormRepository contactFormRepository,
        HotelRepository hotelRepository,
        HotelInTripRepository hotelInTripRepository,
        OrganiserRepository organiserRepository,
        TripManagerRepository tripManagerRepository,
        TripRepository tripRepository,
        UserInTripRepository userInTripRepository,
        VehicleInTripRepository vehicleInTripRepository,
        VehicleRepository vehicleRepository
    ) {
        return args -> {
            if (false) {
                var user = User.builder()
                        .name("User1")
                        .surname("User1")
                        .phoneNumber("+12356135")
                        .email("user@email.com")
                        .identificationData("Passport number")
                        .build();

                var manager = TripManager.builder()
                        .name("Somemanager")
                        .surname("SomemanagerSurname")
                        .country("Poland")
                        .email("Somemanager@gmail.com")
                        .phoneNumber("123456789")
                        .build();

                var organiser = Organiser.builder()
                        .name("Someguy")
                        .surname("SomeguySurname")
                        .country("Poland")
                        .email("someguy@gmail.com")
                        .phoneNumber("123456789")
                        .build();


                var contactForm = ContactForm.builder()
                        .text("Why price's are so big?")
                        .sendDate(LocalDate.of(2025, 12, 1))
                        .user(user)
                        .build();

                var company = Company.builder()
                        .name("SomeCompany")
                        .description("Some description")
                        .companyEmail("a@gmail.com")
                        .build();

                var announcement = Announcement.builder()
                        .announcementDate(LocalDate.of(2025, 2, 10))
                        .content("Hello everyone")
                        .build();

                var vehicle = Vehicle.builder()
                        .name("SomeVehicle")
                        .state(Activity.Assigned)
                        .driverCompany("Driver Company")
                        .vehicleType("Bus")
                        .build();

                var vehicleInTrip = VehicleInTrip.builder()
                        .startDate(LocalDate.of(2025, 2, 10))
                        .endDate(LocalDate.of(2025, 2, 11))
                        .startPoint("Warsaw")
                        .vehicle(vehicle)
                        .build();

                var hotel = Hotel.builder()
                        .name("SomeHotel")
                        .state(Activity.Assigned)
                        .hotelAddress("Poland")
                        .hotelWebsite("example.com")
                        .hotelEmail("email")
                        .build();

                var hotelInTrip = HotelInTrip.builder()
                        .hotelStartDate(LocalDate.of(2025, 2, 10))
                        .hotelEndDate(LocalDate.of(2025, 2, 11))
                        .hotel(hotel)
                        .build();


                var trip = Trip.builder()
                        .company(company)
                        .name("Trip to poland")
                        .price(100.00)
                        .arrivalPoint("Warsaw")
                        .departurePoint("Warsaw")
                        .startDate(LocalDate.of(2025, 12, 31))
                        .endDate(LocalDate.of(2026, 12, 10))
                        .numberOfUsersInGroup(100)
                        .registrationDateEnd(LocalDate.of(2025, 11, 10))
                        .programDescription("Description")
                        .registrationState(RegistrationState.Active)
                        .organiser(organiser)
                        .build();

                var trip1 = Trip.builder()
                        .company(company)
                        .name("Trip without user")
                        .price(100.00)
                        .arrivalPoint("Warsaw")
                        .departurePoint("Warsaw")
                        .startDate(LocalDate.of(2025, 12, 31))
                        .endDate(LocalDate.of(2026, 12, 10))
                        .numberOfUsersInGroup(100)
                        .registrationDateEnd(LocalDate.of(2025, 11, 10))
                        .programDescription("Description")
                        .registrationState(RegistrationState.Active)
                        .organiser(organiser)
                        .build();


                trip.getHotelsInTrip().add(hotelInTrip);
                trip.getVehiclesInTrip().add(vehicleInTrip);
                trip.getUsers();
                trip.getTripManager().add(manager);
                trip1.getTripManager().add(manager);

                trip.getAnnouncement().add(announcement);

                announcement.setTrip(trip);
                announcement.setTripManager(manager);

                manager.getAnnouncements().add(announcement);
                manager.getTrips().add(trip);
                manager.getTrips().add(trip1);
                manager.setCompany(company);

                organiser.getTrips().add(trip);
                organiser.getTrips().add(trip1);

                organiser.getTouristServices().add(hotel);
                organiser.getTouristServices().add(vehicle);
                organiser.setCompany(company);

                company.getTrips().add(trip);
                company.getTrips().add(trip1);
                company.getEmployee().add(organiser);
                company.getEmployee().add(manager);
                company.getContactForms().add(contactForm);

                contactForm.setCompany(company);

                hotelInTrip.setTrip(trip);
                vehicleInTrip.setTrip(trip);

                hotel.getHotelInTripList().add(hotelInTrip);
                vehicle.getVehiclesInTrip().add(vehicleInTrip);

                hotel.setOrganiser(organiser);
                vehicle.setOrganiser(organiser);

                user.getUserInTripList();
                user.getContactFormList().add(contactForm);


                companyRepository.save(company);
                userRepository.save(user);
                organiserRepository.save(organiser);
                hotelRepository.save(hotel);
                vehicleRepository.save(vehicle);

                tripManagerRepository.save(manager);

                tripRepository.save(trip);
                tripRepository.save(trip1);


                hotelInTripRepository.save(hotelInTrip);
                vehicleInTripRepository.save(vehicleInTrip);
                announcementRepository.save(announcement);
                contactFormRepository.save(contactForm);
            }
            };
    }
}
