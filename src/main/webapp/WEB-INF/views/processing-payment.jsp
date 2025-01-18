<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Processing Payment</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <style>
    body {
      background-color: #f8f9fa;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }
    .spinner-border {
      width: 3rem;
      height: 3rem;
    }
  </style>
</head>
<body>
<div class="text-center">
  <div class="spinner-border text-primary" role="status">
    <span class="sr-only">Loading...</span>
  </div>
  <h3 class="mt-3">Processing Payment...</h3>
</div>
<script>
  setTimeout(() => {
    window.location.href = "${pageContext.request.contextPath}/rentee/payment-success";
  }, 3000);
</script>
</body>
</html>
