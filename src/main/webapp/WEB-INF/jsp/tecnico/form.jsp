<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Novo Técnico</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <form action="/tecnicos/salvar" method="post">
        <h2>Novo Técnico</h2>
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" required>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        
        <label for="nivel">Nível (Ex: N1, N2):</label>
        <input type="text" id="nivel" name="nivel">

        <button type="submit">Salvar</button>
        <a href="/" class="back-link">Voltar ao Dashboard</a>
    </form>
</body>
</html>