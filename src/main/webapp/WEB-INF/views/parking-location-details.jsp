<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Parking Location Details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

    <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 900px;
            margin-top: 30px;
        }
        .parking-details {
            margin-top: 20px;
        }
        .list-group-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .status-badge {
            font-size: 1.1rem;
        }
        .parking-location-card {
            margin-top: 20px;
        }
        .parking-location-card img {
            max-width: 80%; /* Smaller image size */
            height: auto;
        }
        .parking-spot-card {
            margin-top: 20px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #fff;
        }
        .star-rating {
            display: inline-block;
        }
        .fa-star {
            color: #d3d3d3; /* Light gray for empty stars */
            font-size: 20px;
        }
        .filled {
            color: #ffd700; /* Gold for filled stars */
        }
        .star-rating span {
            margin-left: 2px;
        }
        .container{
            max-width: 900px !important;
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/rentee-header.jsp" %>
    <div class="container">
        <div class="card shadow-sm parking-location-card">
            <div class="card-body">
                <div class="container mb-4" style="margin: 0; padding: 0">
                    <h4 class="text-center mb-4">Parking Location Details</h4>
                    <div class="row align-items-center">
                        <div class="col-md-6 text-center">
                            <img src="${pageContext.request.contextPath}/uploads/${parkingLocation.id}.jpg"
                                 alt="Parking Location Image"
                                 class="img-fluid rounded shadow">
                        </div>
                        <div class="col-md-6">
                            <h6><strong>Address:</strong></h6>
                            <h6 class="text-primary">${parkingLocation.city}, ${parkingLocation.street}</h6>
                            <h6><strong>Zip Code:</strong> ${parkingLocation.postalCode}</h6>
                            <h6><strong>State:</strong> ${parkingLocation.state}</h6>
                            <h6><strong>Country:</strong> ${parkingLocation.country}</h6>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="parking-details parking-spot-card">
            <h5>Parking Spots</h5>
            <form action="${pageContext.request.contextPath}/book-parking" method="post">
                <ul class="list-group">
                    <c:if test="${not empty parkingSpots}">
                        <c:forEach var="parkingSpot" items="${parkingSpots}">
                            <li class="list-group-item">
                                <div>
                                    <input type="radio" id="spot-${parkingSpot.spotNumber}" name="selectedSpot" value="${parkingSpot.id}" required>
                                    <label for="spot-${parkingSpot.spotNumber}">
                                        <strong>Spot Number:</strong> <c:out value="${parkingSpot.spotNumber}"/> |
                                        <strong>Spot Type:</strong> <c:out value="${parkingSpot.spotType}"/> |
                                        <strong>Price Per Hour:</strong> $<c:out value="${parkingSpot.pricePerHour}"/> |
                                        <span class="badge status-badge ${parkingSpot.isAvailable ? 'badge-success' : 'badge-danger'}">
                                            ${parkingSpot.isAvailable ? 'Available' : 'Not Available'}
                                        </span>
                                    </label>
                                </div>
                            </li>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty parkingSpots}">
                        <li class="list-group-item text-center text-muted">
                            No parking spots are available for this location.
                        </li>
                    </c:if>
                </ul>
                <button type="button" id="bookSelectedSlotButton" class="btn btn-primary mt-3" data-toggle="modal" data-target="#bookingModal" onclick="captureSelectedSpot()">
                    Book Selected Spot
                </button>
            </form>
        </div>

        <!-- Ratings and Comments Section -->
        <div class="parking-details parking-spot-card">
            <h5>Ratings and Comments</h5>
            <c:if test="${not empty reviews}">
                <ul class="list-group">
                    <c:forEach var="review" items="${reviews}">
                        <li class="list-group-item">

                            <div class="d-flex align-items-center">
                                <strong>${review.rentee.firstName} ${review.rentee.lastName}</strong>
                                <div class="star-rating ml-2">
                                    <c:forEach var="i" begin="1" end="5">
                                        <span class="fa fa-star ${review.rating >= i ? 'filled' : ''}"></span>
                                    </c:forEach>
                                </div>
                            </div>
                            <p>${review.comment}</p>

                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${empty reviews}">
                <p class="text-muted">No reviews available for this parking location yet.</p>
            </c:if>
        </div>
    </div>



    <!-- Modal -->
    <div class="modal fade" id="bookingModal" tabindex="-1" role="dialog" aria-labelledby="bookingModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/rentee/confirm-booking" method="post">
                    <div class="modal-header">
                        <h5 class="modal-title" id="bookingModalLabel">Book Parking Slot</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <!-- Hidden input to retain selectedSpot -->
                    <input type="hidden" id="selectedSpotHidden" name="selectedSpot">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="bookingDate">Date</label>
                            <input type="date" class="form-control" id="bookingDate" name="bookingDate" required>
                        </div>
                        <div class="form-group">
                            <label for="startTime">Start Time</label>
                            <input type="time" class="form-control" id="startTime" name="startTime" required>
                        </div>
                        <div class="form-group">
                            <label for="endTime">End Time</label>
                            <input type="time" class="form-control" id="endTime" name="endTime" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary" id="submitBookingButton" onclick="captureSelectedTime()">Book Time Slot</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <%@ include file="/WEB-INF/views/footer.jsp" %>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const today = new Date();
            const formattedDate = today.toISOString().split('T')[0]; // Format as YYYY-MM-DD
            document.getElementById('bookingDate').setAttribute('min', formattedDate);
        });

        function captureSelectedSpot() {
            const selectedSpot = document.querySelector('input[name="selectedSpot"]:checked');
            if (selectedSpot) {
                document.getElementById('selectedSpotHidden').value = selectedSpot.value;
            } else {
                alert('Please select a parking spot before proceeding.');
            }
        }

        function captureSelectedTime(){
            const startTime = document.getElementById('startTime').value;
            const endTime = document.getElementById('endTime').value;

            // Check if endTime is at least 1 hour greater than startTime
            if (startTime && endTime) {
                const start = new Date("1970-01-01T" + startTime + "Z");
                const end = new Date("1970-01-01T" + endTime + "Z");

                // Check if end time is at least 1 hour later than start time
                const oneHour = 60 * 60 * 1000; // 1 hour in milliseconds
                if (end - start < oneHour) {
                    alert("The end time has to be greater than start time and the booking has to be atleast for 1 hour");
                    event.preventDefault();  // Prevent form submission
                    return;
                }
            }
        }
    </script>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
