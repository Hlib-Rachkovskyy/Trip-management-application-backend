package com.project.masp;

public class Views {

    public static class UserView {}
    public static class ManagerView {}
    public static class OrganiserView {}
    public static class ExploreView {}
    
    // Association-specific views
    public static class UserTripsView {} // User -> UserInTrip -> Trip
    public static class UserContactFormsView {} // User -> ContactForm
    public static class OrganiserTripsView {} // Organiser -> Trip
    public static class OrganiserTouristServicesView {} // Organiser -> TouristServices
    public static class ManagerTripsView {} // TripManager -> Trip
    public static class ManagerAnnouncementsView {} // TripManager -> Announcement
    public static class TripUsersView {} // Trip -> UserInTrip -> User
    public static class TripAnnouncementsView {} // Trip -> Announcement
    public static class TripTouristServicesView {} // Trip -> HotelInTrip/VehicleInTrip
    public static class CompanyTripsView {} // Company -> Trip
    public static class CompanyEmployeesView {} // Company -> Employee (Organiser/TripManager)
    public static class CompanyContactFormsView {} // Company -> ContactForm
}