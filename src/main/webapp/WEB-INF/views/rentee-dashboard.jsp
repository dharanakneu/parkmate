<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Rentee Dashboard</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h2 class="text-center">Welcome, <c:out value="${user.firstName}" />!</h2>
  <p class="text-center text-muted">You are logged in as a Rentee.</p>
  <div class="row justify-content-center mt-4">
    <div class="col-md-4">
      <div class="card">
        <div class="card-body text-center">
          <h5 class="card-title">My Profile</h5>
          <p class="card-text">View or edit your personal information.</p>
          <a href="/profile" class="btn btn-primary">Profile</a>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <div class="card">
        <div class="card-body text-center">
          <h5 class="card-title">Manage Listings</h5>
          <p class="card-text">Add, edit, or delete your listings.</p>
          <a href="/listings" class="btn btn-primary">Listings</a>
        </div>
      </div>
    </div>
  </div>
  <div class="text-center mt-4">
    <a href="/logout" class="btn btn-danger">Logout</a>
  </div>
</div>
</body>
</html>
