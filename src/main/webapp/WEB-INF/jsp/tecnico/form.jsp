<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Novo Técnico</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<%-- ... (cabeçalho e CSS sem alterações) ... --%>
<body>
    <form action="/tecnicos/salvar" method="post">
        <h2>${empty tecnico.id ? 'Novo' : 'Editar'} Técnico</h2>
        
        <input type="hidden" name="id" value="${tecnico.id}">

        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" value="${tecnico.nome}" required>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${tecnico.email}" required>
        
        <label for="nivel">Nível (Ex: N1, N2):</label>
        <input type="text" id="nivel" name="nivel" value="${tecnico.nivel}">

        <button type="submit">Salvar</button>
        <a href="/tecnicos" class="back-link">Voltar para a Lista</a>
    </form>
</body>
</html>