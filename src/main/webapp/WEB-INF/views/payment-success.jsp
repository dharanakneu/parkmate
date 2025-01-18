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
      background-color: #f8f9fa;
      font-family: Arial, sans-serif;
    }
    .container {
      margin-top: 50px;
      max-width: 600px;
    }
    .card {
      border: none;
      border-radius: 10px;
    }
    .card-body {
      padding: 30px;
    }
    h1 {
      font-size: 2.5rem;
      margin-bottom: 20px;
    }
    .btn-primary {
      background-color: #007bff;
      border-color: #007bff;
      font-size: 1rem;
    }
    .btn-primary:hover {
      background-color: #0056b3;
      border-color: #0056b3;
    }
    p {
      font-size: 1rem;
      margin-bottom: 10px;
    }
    .text-success {
      font-weight: 600;
    }
    .thank-you {
      background-color: #ffffff;
      padding: 20px;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/rentee-header.jsp" %>
<div class="container text-center">
  <div class="thank-you">
    <h1 class="text-success">Thank You!</h1>
    <p class="text-muted">Your spot has been successfully booked.</p>
  </div>
  <div class="card shadow-sm mt-4">
    <div class="card-body">
      <h4 class="text-primary">Receipt</h4>
      <hr>
      <p><strong>Spot Number:</strong> <span class="text-secondary">${booking.parkingSpot.spotNumber}</span></p>
      <p><strong>Address:</strong> <span class="text-secondary">${booking.parkingSpot.parkingLocation.street}, ${booking.parkingSpot.parkingLocation.city}, ${booking.parkingSpot.parkingLocation.state}</span></p>
      <p><strong>Start Time:</strong> <span class="text-secondary">${booking.startTime}</span></p>
      <p><strong>End Time:</strong> <span class="text-secondary">${booking.endTime}</span></p>
      <p><strong>Total Charges:</strong> <span class="text-success">$${booking.payment.amount}</span></p>
      <a href="/rentee/generate-receipt" class="btn btn-primary mt-4">Download Receipt as PDF</a>
    </div>
  </div>
</div>
<%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>
