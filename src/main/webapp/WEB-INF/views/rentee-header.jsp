<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ParkMate</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      font-size: .875rem;
      overflow: hidden;
    }

    .navbar {
      padding: 0.5rem 1rem;
      position: fixed;
      top: 0;
      width: 100%;
      z-index: 1030;
    }

    .sidebar {
      height: 100vh;
      padding-top: 1rem;
      flex: 0 0 200px;
      position: fixed;
      top: 56px; /* Adjust to navbar height */
      left: 0;
      overflow-y: auto;
      background-color: #f8f9fa;
    }

    .sidebar .nav-link {
      font-weight: 500;
      /*color: #333;*/
      color: #212529;
      padding: 10px 15px;
      border-radius: 5px;
      transition: background-color 0.2s, color 0.2s;
    }

    .sidebar .nav-link.active {
      /*color: #007bff;*/
      background-color: #007bff;
      color: white;
    }

    .sidebar .nav-link:hover {
      background-color: #e9ecef;
      color: #007bff;
    }

    .container-fluid {
      padding-top: 56px;
      height: 100vh;
      overflow: hidden;
    }

    .main {
      margin-left: 250px !important; 
      padding: 20px;
      height: calc(100vh - 56px);
    }

    .content {
      overflow-y: auto;
      height: calc(100vh - 56px);
      flex: 1;
      margin-bottom: 40px;
      padding-bottom: 40px;
    }

    .background-image {
      background-image: url('${pageContext.request.contextPath}/resources/images/page-bg.jpg');
      background-size: cover;
      background-position: center;
      position: fixed;
      width: 100%;
      height: 100%;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      z-index: -1;
    }

  </style>
  <script>
    document.addEventListener("DOMContentLoaded", function () {
      const currentPath = window.location.pathname; // Get the current path
      const links = document.querySelectorAll(".sidebar .nav-link");

      links.forEach(link => {
        const linkPath = new URL(link.href).pathname;
        if (currentPath.startsWith(linkPath)) {
          link.classList.add("active");
        }
      });
    });
  </script>
</head>

<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <span class="navbar-brand">ParkMate</span>
  <div class="collapse navbar-collapse justify-content-end">
    <form action="${pageContext.request.contextPath}/logout" method="post">
      <button type="submit" class="btn btn-outline-light">Logout</button>
    </form>
  </div>
</nav>
<div class="container-fluid">
  <div class="row">
    <nav class="col-md-2 d-none d-md-block bg-light sidebar">
      <div class="sidebar-sticky">
        <ul class="nav flex-column">
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/rentee/parking-locations?lat=0&lon=0&radius=5">
              Search Parking
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/rentee/my-bookings">
              My Bookings
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/profile">
              Profile
            </a>
          </li>
        </ul>
      </div>
    </nav>
    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4 main">
      <div class="background-image"></div>
        <div class="content">
