<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Post Parking Location</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .parking-form {
            margin-top: 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: #ffffff;
            padding: 20px;
        }
        h2 {
            margin-bottom: 20px;
        }
        .parking-spot {
            border: 1px solid #dee2e6;
            border-radius: 5px;
            padding: 15px;
            margin-bottom: 15px;
            background-color: #f1f1f1;
        }
        .error-list {
          color: #dc3545;
          padding-left: 1rem;
        }
        .error {
          list-style: none;
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
    <script>
        let spotIndex = 1;

        function addParkingSpot() {
            const spotContainer = document.getElementById("parkingSpots");
            const newSpot = document.createElement("div");

            newSpot.className = "mb-3 parking-spot";
            newSpot.innerHTML = `
                <h5>Parking Spot</h5>
                <label>Spot Number</label>
                <input type="text" name="parkingSpots[` + spotIndex + `].spotNumber" class="form-control" required>
                <label>Spot Type</label>
                <select name="parkingSpots[` + spotIndex + `].spotType" class="form-select" required>
                    <option value="2 Wheeler">2 Wheeler</option>
                    <option value="4 Wheeler">4 Wheeler</option>
                </select>
                <label>Is Available</label>
                <select name="parkingSpots[` + spotIndex + `].isAvailable" class="form-select" required>
                    <option value="true">Yes</option>
                    <option value="false">No</option>
                </select>
                <label>Price Per Hour</label>
                <input type="number" name="parkingSpots[` + spotIndex + `].pricePerHour" class="form-control" step="0.01" required>
            `;

            spotContainer.appendChild(newSpot);
            spotIndex++;
            updateRemoveButtonVisibility();
        }

        function removeLastParkingSpot() {
            const spotContainer = document.getElementById("parkingSpots");
            const spots = spotContainer.getElementsByClassName("parking-spot");
            if (spots.length > 0) {
                spotContainer.removeChild(spots[spots.length - 1]);
                spotIndex--;
            }
            updateRemoveButtonVisibility();
        }

        function updateRemoveButtonVisibility() {
            const removeButton = document.getElementById("removeSpotButton");
            const spotContainer = document.getElementById("parkingSpots");
            const spots = spotContainer.getElementsByClassName("parking-spot");
            removeButton.style.display = spots.length > 1 ? "block" : "none";
        }
    </script>
</head>
<body>
<%@ include file="/WEB-INF/views/renter-header.jsp" %>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-10 col-lg-8 parking-form m-4">
            <h2 class="text-center">Post A Parking Location</h2>
            <div class="call-to-action">
                <p>Not using your parking spot right now? Let someone else park there and make a little extra cash! It&rsquo;s a win-win! </p>
            </div>
            <form:form action="${pageContext.request.contextPath}/renter/post-parking" method="post" modelAttribute="parkingLocationDTO" enctype="multipart/form-data">
                <div class="mb-3">
                    <label for="street" class="form-label">Street</label>
                    <form:input type="text" class="form-control" id="street" path="street" />
                    <form:errors path="street" class="text-danger" />
                </div>
                <div class="mb-3">
                    <label for="city" class="form-label">City</label>
                    <form:input type="text" class="form-control" id="city" path="city" />
                    <form:errors path="city" class="text-danger" />
                </div>
                <div class="mb-3">
                    <label for="state" class="form-label">State</label>
                    <form:input type="text" class="form-control" id="state" path="state" />
                    <form:errors path="state" class="text-danger" />
                </div>
                <div class="mb-3">
                    <label for="postalCode" class="form-label">Postal Code</label>
                    <form:input type="text" class="form-control" id="postalCode" path="postalCode" />
                    <form:errors path="postalCode" class="text-danger" />
                </div>
                <div class="mb-3">
                    <label for="country" class="form-label">Country</label>
                    <form:input type="text" class="form-control" id="country" path="country" />
                    <form:errors path="country" class="text-danger" />
                </div>
                <div class="mb-3">
                    <label for="image" class="form-label">Upload Image</label>
                    <input type="file" name="image" class="form-control" id="image" accept="image/*" />
                    <form:errors path="image" class="text-danger" />
                </div>
                <hr>
                <h4>Parking Spots</h4>
                <div id="parkingSpots">
                    <div class="mb-3 parking-spot">
                        <h5>Parking Spot</h5>
                        <div>
                            <label>Spot Number</label>
                            <form:input path="parkingSpots[0].spotNumber" class="form-control" />
                            <form:errors path="parkingSpots[0].spotNumber" class="text-danger" />
                        </div>
                        <div>
                            <label>Spot Type</label>
                            <form:select path="parkingSpots[0].spotType" class="form-select" >
                                <form:option value="2 Wheeler">2 Wheeler</form:option>
                                <form:option value="4 Wheeler">4 Wheeler</form:option>
                            </form:select>
                        </div>
                        <div>
                            <form:label path="parkingSpots[0].isAvailable">is Available</form:label><br/>
                            <form:select path="parkingSpots[0].isAvailable" class="form-select" >
                                <form:option value="true">Yes</form:option>
                                <form:option value="false">No</form:option>
                            </form:select>
                        </div>
                        <div>
                            <label>Price Per Hour</label>
                            <form:input path="parkingSpots[0].pricePerHour" class="form-control"  />
                            <form:errors path="parkingSpots[0].pricePerHour" class="text-danger" />
                        </div>

<%--                        <label>Spot Type</label>--%>
<%--                        <form:input path="parkingSpots[0].spotType" class="form-control" required="required" />--%>
                    </div>
                </div>
                <button type="button" class="btn btn-secondary" id="addSpotButton" onclick="addParkingSpot()">Add More Spots</button>
                <button type="button" class="btn btn-danger mt-3" id="removeSpotButton" onclick="removeLastParkingSpot()" style="display: none;">Remove Last Spot</button>
                <br>
                <c:if test="${not empty errorMessages}">
                  <ul class="error-list">
                    <c:forEach var="message" items="${errorMessages}">
                      <li class="error">${message}</li>
                    </c:forEach>
                  </ul>
                </c:if>
                <button type="submit" class="btn btn-primary mt-3">Submit</button>
            </form:form>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>