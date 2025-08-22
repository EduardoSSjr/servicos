<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Dashboard de Chamados</title>
    <link rel="stylesheet" href="/css/dashboard-style.css">
</head>
<body>
    <div class="container">
        <h1>Dashboard de Chamados</h1>
        <div class="actions">
            <a href="/os/novo" class="btn-primary">Nova OS</a>
            <a href="/solicitantes" class="btn-secondary">Ver Solicitantes</a>
            <a href="/tecnicos" class="btn-secondary">Ver Técnicos</a>
        </div>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Título</th>
                    <th>Solicitante</th>
                    <th>Técnico</th>
                    <th>Status</th>
                    <th>Prioridade</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="chamado" items="${chamados}">
                    <tr>
                        <td>${chamado.id}</td>
                        <td><a href="/os/${chamado.id}">${chamado.titulo}</a></td>
                        <td>${chamado.solicitante.nome}</td>
                        <td>${chamado.tecnicoResponsavel.nome}</td>
                        <td>${chamado.status}</td>
                        <td>${chamado.prioridade}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>