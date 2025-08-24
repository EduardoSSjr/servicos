<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Lista de Técnicos</title>
    <link rel="stylesheet" href="/css/dashboard-style.css">
</head>
<body>
    <div class="container">
        <h1>Técnicos</h1>
        <div class="actions">
            <a href="/tecnicos/novo" class="btn-primary">Novo Técnico</a>
            <a href="/" class="btn-secondary">Voltar ao Dashboard</a>
        </div>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Email</th>
                <th>Nível</th>
                <th>Ações</th> <%-- Nova coluna --%>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="tecnico" items="${tecnicos}">
                <tr>
                    <td>${tecnico.id}</td>
                    <td>${tecnico.nome}</td>
                    <td>${tecnico.email}</td>
                    <td>${tecnico.nivel}</td>
                    <td class="actions-cell">
                        <a href="/tecnicos/${tecnico.id}/editar" class="btn-action btn-edit">Editar</a>
                        <form action="/tecnicos/${tecnico.id}/excluir" method="post" onsubmit="return confirm('Tem certeza que deseja excluir?');">
                            <button type="submit" class="btn-action btn-delete">Excluir</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
        </table>
    </div>
</body>
</html>