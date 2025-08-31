package com.project.masp;

public class Views {

    public static class UserView {}
    public static class ManagerView {}
    public static class OrganiserView {}

    public static class UserTripsView {} // User -> UserInTrip -> Trip
    public static class UserContactFormsView {} // User -> ContactForm
    public static class OrganiserTouristServicesView {} // Organiser -> TouristServices
    public static class TripAnnouncementsView {} // Trip -> Announcement
    public static class CompanyTripsView {} // Company -> Trip


    // Employee
    public static class TripUsersView {} // Trip -> UserInTrip -> User |

    // Organiser
    public static class OrganiserTripsView {} // Organiser -> Trip |
    public static class CompanyContactFormsView {} // Company -> ContactForm |
    public static class CompanyManagersView {} // Company -> Manager |
    public static class TripManagersView {}


    // Manager
    public static class ManagerAnnouncementsView {} // Manager -> Announcement |
    public static class ManagerTripsView {} // Manager -> Trips |

    public static class CompanyEmployee { }
}