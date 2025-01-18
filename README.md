# ParkMate - Nearest Parking Space Tracker 🚗  
**Revolutionizing urban parking with a smart and user-friendly platform for real-time parking management.**  

## 📖 Overview  
ParkMate is an innovative parking space tracking system designed to address the challenges of urban and high-traffic areas, such as stadiums and event venues. The platform allows users to:  
- Find and reserve nearby parking spaces effortlessly.  
- Rent out unused parking spaces for additional income.  
- Securely manage reservations, payments, and user profiles.  
With features like real-time geolocation, Google Maps integration, and Stripe-powered cashless transactions, ParkMate enhances convenience for drivers and parking space owners alike.  

---

## 🌟 Key Features  
1. **User Authentication & Profile Management**  
   - Secure login/registration with encrypted user data (Bcrypt).  
   - Profile management.
     
2. **Private Parking Space Listing for Renters**  
   - Renters can list and manage private parking spots.
   - Post new parking location and spots.
   - Change availability of the parking spots as needed.

3. **Home Dashboard & Location Detection**
   - User's current location detection.
   - Nearby parking spots displayed within a 2km radius.  

4. **Parking Spot Details & Navigation**  
   - Detailed information on parking spots (location, type, pricing, hours).  
   - Turn-by-turn navigation using Google Maps API from current location to destination parking space.  

5. **Reservation & Payment Processing**  
   - Reserve parking for current or future times.  
   - Confirm booking and download receipt as pdf. 

6. **User Reviews & Ratings**  
   - Leave feedback for reserved parking spots.  

---

## 🛠️ Tech Stack  

### **Backend**  
- **Spring Boot**: Framework for building RESTful web services and annotated controllers.  
- **Hibernate**: Object-Relational Mapping (ORM) with annotated POJOs for seamless database integration.  
- **DAO Pattern**: Ensures clean data access logic, promoting maintainability and scalability.  
- **MySQL**: Relational database for storing and managing data.  
- **Bcrypt**: Secure password hashing and encryption to ensure safe storage of user credentials.
- **Interceptor**: Used for pre- and post-processing of HTTP requests and responses, enhancing request handling and security.  
- **HttpSession**: Manages user session data, enabling session-based user authentication and tracking.  

### **Frontend**  
- **JSP (JavaServer Pages)**: Server-side rendering for dynamic content generation.  
- **Bootstrap**: Framework for creating responsive and visually appealing UI components.  
- **AJAX**: Asynchronous client-server communication for real-time data updates and enhanced user experience. 

### **Architecture**  
- **Web 2.0 Application**: Designed with modern web standards for enhanced interactivity and usability.  
- **MVC Implementation**: Clear separation of concerns with:  
  - **Model:** Hibernate-mapped POJOs.  
  - **View:** JSP with Bootstrap for UI styling.  
  - **Controller:** Spring Boot for handling HTTP requests and responses.

### **APIs & Services**  
- **Google Maps API**: For map integration and providing turn-by-turn navigation to parking spots.  
- **Opencage Geocoding API**: For address-to-coordinates conversion and reverse geocoding.   

### **Reporting**  
- **AbstractPdfView**: Used for generating PDF reports from the backend, offering users a convenient format for parking reservations, invoices, etc.  

---

## Disclaimer

This repository is intended solely for **evaluation purposes** to showcase my coding skills, design practices, and technical expertise. 

### Usage Restrictions

- Unauthorized use, reproduction, modification, or distribution of the code in this repository is **strictly prohibited**.  
- If you wish to use any part of this code, please contact me directly to obtain explicit permission.

### Contact Information

If you have any questions or require further information, feel free to reach out:

- **Name**: Dharana Kashyap  
- **Email**: [kashyap.dh@northeastern.edu]  
- **LinkedIn**: [Dharana Kashyap](https://www.linkedin.com/in/dharanakashyap/)  

Thank you for respecting my work!

---
