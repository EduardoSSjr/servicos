<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Novo Solicitante</title>
    <link rel="stylesheet" href="/css/style.css"> <%-- Usaremos um estilo comum --%>
</head>
<body>
    <form action="/solicitantes/salvar" method="post">
        <h2>Novo Solicitante</h2>
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" required>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        
        <label for="departamento">Departamento:</label>
        <input type="text" id="departamento" name="departamento">
        
        <label for="cargo">Cargo:</label>
        <input type="text" id="cargo" name="cargo">

        <label for="telefone">Telefone:</label>
        <input type="text" id="telefone" name="telefone">

        <button type="submit">Salvar</button>
        <a href="/" class="back-link">Voltar ao Dashboard</a>
    </form>
</body>
</html>