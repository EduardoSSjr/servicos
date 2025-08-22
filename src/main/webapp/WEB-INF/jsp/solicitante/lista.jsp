<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Lista de Solicitantes</title>
    <link rel="stylesheet" href="/css/dashboard-style.css"> <%-- Usaremos um novo CSS --%>
</head>
<body>
    <div class="container">
        <h1>Solicitantes</h1>
        <div class="actions">
            <a href="/solicitantes/novo" class="btn-primary">Novo Solicitante</a>
            <a href="/" class="btn-secondary">Voltar ao Dashboard</a>
        </div>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>Departamento</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="solicitante" items="${solicitantes}">
                    <tr>
                        <td>${solicitante.id}</td>
                        <td>${solicitante.nome}</td>
                        <td>${solicitante.email}</td>
                        <td>${solicitante.departamento}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>