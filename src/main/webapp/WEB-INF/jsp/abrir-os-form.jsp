<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Abrir Nova Ordem de Serviço</title>
    <style>
        body { font-family: sans-serif; }
        form { max-width: 500px; margin: auto; padding: 20px; border: 1px solid #ccc; border-radius: 10px; }
        label, select, input, textarea { display: block; width: 100%; margin-bottom: 10px; box-sizing: border-box; }
        input, select, textarea { padding: 8px; }
        button { padding: 10px 15px; background-color: #007bff; color: white; border: none; border-radius: 5px; cursor: pointer; }
    </style>
</head>
<body>
    <form action="/os/salvar" method="post" modelAttribute="osRequest">
        <h2>Nova Ordem de Serviço</h2>

        <label for="solicitanteId">Solicitante:</label>
        <select id="solicitanteId" name="solicitanteId" required>
            <c:forEach var="solicitante" items="${solicitantes}">
                <option value="${solicitante.id}">${solicitante.nome}</option>
            </c:forEach>
        </select>

        <label for="tipo">Tipo:</label>
        <select id="tipo" name="tipo">
            <option value="corretiva">Corretiva</option>
            <option value="preventiva">Preventiva</option>
        </select>

        <label for="prioridade">Prioridade:</label>
        <select id="prioridade" name="prioridade">
            <c:forEach var="p" items="${prioridades}">
                <option value="${p}">${p}</option>
            </c:forEach>
        </select>

        <label for="titulo">Título:</label>
        <input type="text" id="titulo" name="titulo" required>

        <label for="descricao">Descrição:</label>
        <textarea id="descricao" name="descricao" rows="4"></textarea>

        <button type="submit">Salvar OS</button>
    </form>
</body>
</html>