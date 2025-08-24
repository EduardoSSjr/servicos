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
        <%-- Exibe a mensagem de erro, se houver --%>
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>
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
                    <th>Ações</th> <%-- Nova coluna --%>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="solicitante" items="${solicitantes}">
                    <tr>
                        <td>${solicitante.id}</td>
                        <td>${solicitante.nome}</td>
                        <td>${solicitante.email}</td>
                        <td>${solicitante.departamento}</td>
                        <td class="actions-cell"> <%-- Links e formulário de ação --%>
                            <a href="/solicitantes/${solicitante.id}/editar" class="btn-action btn-edit">Editar</a>
                            <form action="/solicitantes/${solicitante.id}/excluir" method="post" onsubmit="return confirm('Tem certeza que deseja excluir?');">
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