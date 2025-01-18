<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Bookings</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  <style>

    .booking-card {
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
      margin-bottom: 20px;
      padding: 10px;
      border-radius: 8px;
      background-color: rgba(255, 255, 255, 0.8);
    }
    .booking-card .card-img {
      max-width: 100%;
      height: auto;
      padding: 30px;
      border-radius: 8px;
    }
    .booking-card .details {
      padding: 10px;
    }
    .booking-card .buttons {
      padding: 10px;
    }
    .booking-card .btn {
      margin-left: 5px;
    }
    .booking-card .card-body {
      font-size: 0.9rem;
    }
    body {
      background-color: #f8f9fa;
      font-family: Arial, sans-serif;
    }
    .alert {
      margin-top: 15px;
    }
    .page-header {
      margin-bottom: 20px;
      padding: 10px;
      text-align: center;
      border-radius: 5px;
    }
    .container{
      max-width: 900px !important;
    }
    /* Style the star rating */
    .star-rating {
      font-size: 1.5rem;
      color: #FFD700;
    }

    .fa-star, .fa-star-o {
      cursor: pointer;
    }

    .modal-dialog-centered {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/rentee-header.jsp" %>
<div class="container">
  <div class="page-header">
    <h2>My Bookings</h2>
  </div>

  <c:if test="${not empty successMessage}">
    <div class="alert alert-success">
      <i class="fas fa-check-circle"></i> ${successMessage}
    </div>
  </c:if>
  <c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">
      <i class="fas fa-exclamation-circle"></i> ${errorMessage}
    </div>
  </c:if>

  <div class="row">
  <c:forEach var="booking" items="${bookings}">
    <div class="col-12">
      <div class="card booking-card">
        <div class="row no-gutters">
          <div class="col-md-4">
            <!-- Parking Location Image -->
            <img src="${pageContext.request.contextPath}/uploads/${booking.parkingSpot.parkingLocation.id}.jpg" class="card-img" alt="Parking Location Image">
          </div>
          <div class="col-md-8 d-flex flex-column justify-content-between">
            <div class="details">
              <p><strong>Location:</strong> ${booking.parkingSpot.parkingLocation.street}</p>
              <p><strong>Spot #:</strong> ${booking.parkingSpot.spotNumber}</p>
              <p><strong>Start Time:</strong> <fmt:formatDate value="${booking.startTime}" pattern="MMM dd, yyyy hh:mm a" /></p>
              <p><strong>End Time:</strong> <fmt:formatDate value="${booking.endTime}" pattern="MMM dd, yyyy hh:mm a" /></p>
              <p><strong>Total Charges:</strong> $${booking.payment.amount}</p>
              <p><strong>Status:</strong>
                <span class="badge
                  ${booking.status == 'Confirmed' ? 'badge-success' : ''}
                  ${booking.status == 'Pending' ? 'badge-warning' : ''}
                  ${booking.status == 'Cancelled' ? 'badge-danger' : ''}">
                  ${booking.status}
                </span>
              </p>
            </div>
            <div class="buttons text-right">
              <form action="/rentee/cancel-booking" method="post" style="display:inline;" onsubmit="return confirmCancel()">
                <input type="hidden" name="bookingId" value="${booking.id}" />
                <button type="submit" class="btn btn-danger btn-sm">
                  <i class="fas fa-times-circle"></i> Cancel
                </button>
              </form>
              <button id="navigateButton_${booking.id}" class="btn btn-primary btn-sm"
                    onclick="navigateToLocation(event, ${booking.parkingSpot.parkingLocation.latitude}, ${booking.parkingSpot.parkingLocation.longitude}, this)">
                <i class="fas fa-map-marker-alt"></i> Navigate
                <span class="spinner-border spinner-border-sm" id="loadingSpinner_${booking.id}" style="display:none;" aria-hidden="true"></span>
                <span id="loadingText_${booking.id}" class="visually-hidden" style="display:none;" role="status">Loading...</span>
              </button>

              <button type="button" class="btn btn-secondary btn-sm leave-review-button"
                      data-toggle="modal" data-target="#reviewModal"
                      data-parking-location-id="${booking.parkingSpot.parkingLocation.id}">
                <i class="fas fa-star"></i> Review
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </c:forEach>
</div>

<div class="modal fade" id="reviewModal" tabindex="-1" role="dialog" aria-labelledby="reviewModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="reviewModalLabel">Leave a Review</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <form action="/rentee/submit-review" method="post">
        <div class="modal-body">
          <input type="hidden" id="parkingLocationId" name="parkingLocationId">

          <!-- Rating with Stars -->
          <div class="form-group">
            <label for="rating">Rating</label>
            <div id="starRating" class="star-rating">
              <i class="far fa-star" data-value="1"></i>
              <i class="far fa-star" data-value="2"></i>
              <i class="far fa-star" data-value="3"></i>
              <i class="far fa-star" data-value="4"></i>
              <i class="far fa-star" data-value="5"></i>
            </div>
            <input type="hidden" id="rating" name="rating" required>
          </div>

          <div class="form-group">
            <label for="comment">Comment</label>
            <textarea class="form-control" id="comment" name="comment" rows="2" required></textarea>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-primary">Submit</button>
        </div>
      </form>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/views/footer.jsp" %>

  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>

  <script>
  function navigateToLocation(event, destinationLat, destinationLng, button) {
    event.preventDefault();

    // Get the booking ID from the button ID
    var bookingId = button.id.split('_')[1];

    // Show loading spinner and disable the button
    var spinner = document.getElementById('loadingSpinner_' + bookingId);
    var loadingText = document.getElementById('loadingText_' + bookingId);
    spinner.style.display = 'inline-block';
    loadingText.style.display = 'inline';
    button.disabled = true;

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(function(position) {
        var originLatitude = position.coords.latitude;
        var originLongitude = position.coords.longitude;

        var googleMapsUrl = 'https://www.google.com/maps/dir/?api=1&origin='
                + originLatitude + ',' + originLongitude
                + '&destination=' + destinationLat + ',' + destinationLng;

        window.open(googleMapsUrl, '_blank');

        // Hide the spinner and re-enable the button after opening the new window
        spinner.style.display = 'none';
        loadingText.style.display = 'none';
        button.disabled = false;
      }, function(error) {
        alert('Error retrieving your location: ' + error.message);

        // Hide the spinner and re-enable the button in case of an error
        spinner.style.display = 'none';
        loadingText.style.display = 'none';
        button.disabled = false;
      });
    } else {
      alert('Geolocation is not supported by this browser.');

      // Hide the spinner and re-enable the button if geolocation is not supported
      spinner.style.display = 'none';
      loadingText.style.display = 'none';
      button.disabled = false;
    }
  }

  document.querySelectorAll('.leave-review-button').forEach(function(button) {
    button.addEventListener('click', function() {
      var parkingLocationId = this.getAttribute('data-parking-location-id');
      document.getElementById('parkingLocationId').value = parkingLocationId;
    });
  });

  function confirmCancel() {
    return confirm('Are you sure you want to cancel?');
  }

  // Star rating logic
  document.querySelectorAll('#starRating i').forEach(function(star) {
    star.addEventListener('mouseenter', function() {
      let rating = this.getAttribute('data-value');
      // Highlight the stars up to the hovered star
      document.querySelectorAll('#starRating i').forEach(function(s) {
        if (s.getAttribute('data-value') <= rating) {
          s.classList.remove('far');
          s.classList.add('fas');
        } else {
          s.classList.remove('fas');
          s.classList.add('far');
        }
      });
    });

    star.addEventListener('click', function() {
      let rating = this.getAttribute('data-value');
      document.getElementById('rating').value = rating; // Set rating input value

      // Fill stars up to the clicked one and capture the number
      document.querySelectorAll('#starRating i').forEach(function(s) {
        if (s.getAttribute('data-value') <= rating) {
          s.classList.remove('far');
          s.classList.add('fas');
        } else {
          s.classList.remove('fas');
          s.classList.add('far');
        }
      });
    });

    // Remove highlight when mouse leaves the stars
    star.addEventListener('mouseleave', function() {
      let rating = document.getElementById('rating').value;
      document.querySelectorAll('#starRating i').forEach(function(s) {
        if (s.getAttribute('data-value') <= rating) {
          s.classList.remove('far');
          s.classList.add('fas');
        } else {
          s.classList.remove('fas');
          s.classList.add('far');
        }
      });
    });
  });
</script>

</body>
</html>
