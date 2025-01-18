<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Parking Locations</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .parking-list {
            margin-top: 30px;
        }
        .parking-item {
            border: 1px solid #dee2e6;
            border-radius: 8px;
            margin-bottom: 20px;
            padding: 20px;
            background-color: #ffffff;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }
        .parking-item:hover {
            transform: scale(1.05);
        }
        .parking-item img {
            width: 100%;
            height: 200px; /* Set a fixed height */
            object-fit: cover; /* Ensures the image is cropped */
            border-radius: 8px;
        }
        .parking-item-header {
            font-size: 1.25rem;
            font-weight: bold;
        }
        .btn-custom {
            background-color: #007bff !important; /* Blue color */
            color: white !important;
            transition: background-color 0.3s ease; /* Smooth transition */
        }

        .btn-custom:hover {
            background-color: #0056b3 !important; /* Darker blue */
            color: white !important; /* Ensure text remains white on hover */
        }

        .modal-content {
            border-radius: 10px;
            padding: 20px;
        }
        .modal-dialog {
            max-width: 600px; /* Reduce modal width */
        }
        .modal-dialog-centered {
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .container {
            max-width: 900px !important;
        }
        .call-to-action {
            background-color: #f1f8ff;
            padding: 15px;
            margin-bottom: 30px;
            border-radius: 8px;
            text-align: center;
            font-size: 0.8rem;
            font-weight: bold;
            color: #007bff;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/views/renter-header.jsp" %>

<div class="container">
    <h2 class="text-center my-4">Your Posted Parking Locations</h2>
    <div class="call-to-action">
        <p>Welcome to your parking dashboard! Here, you can view all the parking spots you've posted. Need to change the availability of your spots? You can update the status anytime to make sure you're earning cash when you're not using them. It's that easy! </p>
    </div>
    <div class="parking-list">
        <c:forEach items="${parkingLocations}" var="location" varStatus="status">
            <div class="parking-item">
                <div class="row">
                    <div class="col-md-4">
                        <img src="${pageContext.request.contextPath}/uploads/${location.id}.jpg" alt="Parking Location Image" class="img-fluid">
                    </div>
                    <div class="col-md-8">
                        <h4 class="parking-item-header">Parking Location #${status.index + 1}</h4>
                        <p><strong>Street:</strong> ${location.street}</p>
                        <p><strong>City:</strong> ${location.city}</p>
                        <p><strong>State:</strong> ${location.state}</p>
                        <p><strong>Postal Code:</strong> ${location.postalCode}</p>
                        <p><strong>Country:</strong> ${location.country}</p>
                        <div class="d-flex justify-content-between">
                            <form action="${pageContext.request.contextPath}/renter/delete-parking/${location.id}" method="post" onsubmit="return confirm('Are you sure you want to delete this parking location?');">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                            <button type="button" class="btn btn-custom" data-bs-toggle="modal" data-bs-target="#parkingSpotsModal" onclick="loadParkingSpots(${location.id})">
                                View Parking Spots
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Modal for Viewing Parking Spots -->
<div class="modal fade" id="parkingSpotsModal" tabindex="-1" aria-labelledby="parkingSpotsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="parkingSpotsModalLabel">Parking Spots</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div id="parkingSpotsContent">
                    <!-- Parking spots will be dynamically loaded here -->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-success" onclick="saveParkingSpots()">Save</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    var contextPath = '${pageContext.request.contextPath}';

    function loadParkingSpots(locationId) {
        // Fetch parking spots for the selected location
        fetch(contextPath + '/renter/get-parking-spots?locationId=' + locationId)
            .then(function(response) { return response.json(); })
            .then(function(data) {
                var content = data.map(function(spot) {
                    return '<div class="d-flex align-items-center mb-3">' +
                        '<span class="me-auto">Spot Number:' + spot.spotNumber + '</span>' +
                        '<label class="form-check-label me-2">Available:</label>' +
                        '<input type="checkbox" class="form-check-input" ' + (spot.isAvailable ? 'checked' : '') + ' data-spot-id="' + spot.id + '">' +
                        '</div>';
                }).join('');
                document.getElementById('parkingSpotsContent').innerHTML = content;
            });
    }

    function saveParkingSpots() {
        var checkboxes = document.querySelectorAll('#parkingSpotsContent input[type="checkbox"]');
        var updates = Array.from(checkboxes).map(function(checkbox) {
            return {
                id: checkbox.getAttribute('data-spot-id'),
                isAvailable: checkbox.checked
            };
        });

        // Send updates to the server
        fetch(contextPath + '/renter/update-parking-spots', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updates)
        }).then(function(response) {
            if (response.ok) {
                alert('Parking spots updated successfully!');
            } else {
                alert('Failed to update parking spots.');
            }
        });
    }
</script>

<%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>
