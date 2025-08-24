<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Novo Solicitante</title>
    <link rel="stylesheet" href="/css/style.css"> <%-- Usaremos um estilo comum --%>
</head>
<%-- ... (cabeçalho e CSS sem alterações) ... --%>
<body>
    <form action="/solicitantes/salvar" method="post">
        <%-- Título dinâmico --%>
        <h2>${empty solicitante.id ? 'Novo' : 'Editar'} Solicitante</h2>
        
        <%-- Campo oculto para enviar o ID durante a edição --%>
        <input type="hidden" name="id" value="${solicitante.id}">

        <label for="nome">Nome:</label>
        <%-- O campo value preenche o input com os dados existentes --%>
        <input type="text" id="nome" name="nome" value="${solicitante.nome}" required>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${solicitante.email}" required>
        
        <label for="departamento">Departamento:</label>
        <input type="text" id="departamento" name="departamento" value="${solicitante.departamento}">
        
        <label for="cargo">Cargo:</label>
        <input type="text" id="cargo" name="cargo" value="${solicitante.cargo}">

        <label for="telefone">Telefone:</label>
        <input type="text" id="telefone" name="telefone" value="${solicitante.telefone}">

        <button type="submit">Salvar</button>
        <a href="/solicitantes" class="back-link">Voltar para a Lista</a>
    </form>
</body>
</html>