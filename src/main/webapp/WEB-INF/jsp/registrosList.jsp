<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="shortcut icon" type="image/png" href="/favicon.png">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">

    <link rel="stylesheet" href="/styles.css">
    <title>Prueba tecnica AXA Colpatria</title>

</head>

<body>

   <div class="container">
    <img src="favicon.png" alt="Axa Colpatria">

      <div class="title">
        <h1>Registros Servicio - AXA COLPATRIA</h1>
      </div>

      <c:choose>

            <c:when test="${not empty registrosList}">
    
                <ul>
                    <c:forEach var="item" items="${registrosList}">
                        <li><b></b> ${item.name} <br>  
                        <b></b><fmt:formatNumber value="${item.age}" />
                        <b></b> ${item.phoneNumber}  <b></b> ${item.address}</li>
                    </c:forEach>
                </ul>
    
            </c:when>
            
            <c:otherwise>
                <b>NO DATA</b>
            </c:otherwise>
            
        </c:choose>
    </div>

    <footer class="footer">
      <div class="container">
        <img src="favicon.png" alt="Axa Colpatria">
      </div>
    </footer>

</body>

</html>