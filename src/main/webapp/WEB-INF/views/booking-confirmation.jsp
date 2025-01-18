<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Confirmation</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f0f2f5;
            font-family: Arial, sans-serif;
        }
        .container {
            margin-top: 50px;
            max-width: 800px !important;
        }
        .card {
            border-radius: 10px;
            overflow: hidden;
        }
        .card-body {
            padding: 30px;
        }
        .card-body h3 {
            color: #343a40;
            font-weight: bold;
        }
        .list-group-item {
            border: none;
            padding: 15px;
            background-color: #f8f9fa;
        }
        .list-group-item strong {
            color: #495057;
        }
        .btn-primary {
            background-color: #007bff;
            border: none;
            border-radius: 25px;
            padding: 10px 20px;
            font-size: 16px;
            font-weight: bold;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/rentee-header.jsp" %>
    <div class="container">
        <div class="card shadow-sm">
            <div class="card-body">
                <h3 class="text-center mb-4">Booking Confirmation</h3>
                <ul class="list-group mb-4">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <strong>Parking Spot:</strong>
                        <span><c:out value="${parkingSpot.spotNumber}" /></span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <strong>Address:</strong>
                        <span><c:out value="${parkingSpot.parkingLocation.city}, ${parkingSpot.parkingLocation.street}" /></span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <strong>Date:</strong>
                        <span><c:out value="${bookingDate}" /></span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <strong>Start Time:</strong>
                        <span><c:out value="${startTime}" /></span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <strong>End Time:</strong>
                        <span><c:out value="${endTime}" /></span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <strong>Price Per Hour:</strong>
                        <span>$<c:out value="${parkingSpot.pricePerHour}" /></span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <strong>Total Charges:</strong>
                        <span>$<c:out value="${totalCharges}" /></span>
                    </li>
                </ul>
                <form action="${pageContext.request.contextPath}/rentee/process-payment" method="post">
                    <input type="hidden" name="selectedSpot" value="${parkingSpot.id}">
                    <input type="hidden" name="bookingDate" value="${bookingDate}">
                    <input type="hidden" name="startTime" value="${startTime}">
                    <input type="hidden" name="endTime" value="${endTime}">
                    <input type="hidden" name="totalCharges" value="${totalCharges}">
                    <button type="submit" class="btn btn-primary btn-block">Pay Now</button>
                </form>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>
