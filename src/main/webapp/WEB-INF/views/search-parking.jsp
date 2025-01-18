<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nearby Parking Locations</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script>
        function getCurrentLocation() {
            console.log("Requesting current location..."); // Debug log
            document.getElementById("loading").style.display = "block"; // Show loading indicator

            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(
                    showPosition,
                    showError
                );
            } else {
                alert("Geolocation is not supported by this browser.");
                document.getElementById("loading").style.display = "none"; // Hide loading indicator
            }
        }

        function showPosition(position) {
            const latitude = position.coords.latitude;
            const longitude = position.coords.longitude;

            // Check if latitude and longitude exist
            if (latitude !== undefined && longitude !== undefined) {
                // Redirect with the parameters
                const radius = 2;
                const url = "/rentee/parking-locations?lat=" + latitude + "&lon=" + longitude + "&radius=" + radius;
                window.location.href = url;
            } else {
                console.error("Latitude or Longitude is undefined.");
                alert("Failed to retrieve coordinates. Please try again.");
            }
        }

        function showError(error) {
            console.error("Geolocation error: ", error); // Log the error
            switch (error.code) {
                case error.PERMISSION_DENIED:
                    alert("User denied the request for Geolocation.");
                    break;
                case error.POSITION_UNAVAILABLE:
                    alert("Location information is unavailable.");
                    break;
                case error.TIMEOUT:
                    alert("The request to get user location timed out.");
                    break;
                case error.UNKNOWN_ERROR:
                    alert("An unknown error occurred.");
                    break;
            }
            document.getElementById("loading").style.display = "none"; // Hide loading indicator
        }
    </script>
    <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }

        .container {
            max-width: 900px;
            margin-top: 30px;
        }

        .btn-primary {
            font-size: 1.2rem;
        }

        .list-group-item {
            border-radius: 8px;
            margin-bottom: 10px;
            background-color: #ffffff;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }

        .list-group-item:hover {
            transform: translateY(-5px);
        }

        .location-details {
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .location-image {
            max-width: 100px;
            max-height: 100px;
            object-fit: cover;
            border-radius: 8px;
            margin-right: 15px;
        }

        .location-info {
            flex-grow: 1;
        }

        .location-info h5 {
            font-size: 1.2rem;
            font-weight: bold;
        }

        .location-info p {
            margin-bottom: 5px;
            color: #666;
        }

        .spinner-border {
            width: 3rem;
            height: 3rem;
            border-width: 4px;
        }
        .container{
            max-width: 900px !important;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/views/rentee-header.jsp" %>

    <div class="container">
        <h1 class="text-center mb-4">Nearby Parking Locations</h1>
        <button class="btn btn-primary mb-3" onclick="getCurrentLocation()">Get My Location</button>
        <div id="loading" style="display: none;" class="text-center">
            <p style="color: white; font-size: 1.1rem;">Loading... Please wait.</p>
            <div class="spinner-border" role="status" style="width: 2rem; height: 2rem; border-width: 0.3rem; color: white">
                <span class="sr-only">Loading...</span>
            </div>
        </div>
        <div class="list-group">

        <c:if test="${not empty parkingLocations}">
                <c:forEach var="location" items="${parkingLocations}">
                    <a href="${pageContext.request.contextPath}/rentee/parking-location-details?id=${location.id}" class="list-group-item list-group-item-action">
                        <div class="location-details">
                            <img src="${pageContext.request.contextPath}/uploads/${location.id}.jpg" alt="Location Image" class="location-image">
                            <div class="location-info">
                                <h5 class="mb-1">${location.city}</h5>
                                <small>Street: ${location.street}</small>
                                <p class="mb-1">Zip Code: ${location.postalCode}</p>
                                <small>State: ${location.state}</small>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </c:if>
            <c:if test="${empty parkingLocations}">
                <div class="list-group-item text-center">
                    Click on "Get my Location" button and allow us to fetch your current location to display parking spaces near you
                </div>
            </c:if>
        </div>
    </div>
<%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>


