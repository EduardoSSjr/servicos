<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Detalhes da OS #${os.id}</title>
    <link rel="stylesheet" href="/css/style.css">
    <style>
        .detail-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; max-width: 900px; margin: auto; }
        .detail-card { background-color: white; padding: 2em; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        .detail-card h2, .detail-card h3 { margin-top: 0; color: #0056b3;}
        .info-item { margin-bottom: 1em; }
        .info-item strong { display: block; color: #555; margin-bottom: 4px;}
        hr { border: 0; height: 1px; background-color: #eee; margin: 2em 0; }
    </style>
</head>
<body>
    <div class="detail-grid">
        <div class="detail-card">
            <h2>Detalhes do Chamado #${os.id}</h2>
            <div class="info-item"><strong>Título:</strong> ${os.titulo}</div>
            <div class="info-item"><strong>Descrição:</strong> ${os.descricao}</div>
            <div class="info-item"><strong>Solicitante:</strong> ${os.solicitante.nome} (${os.solicitante.email})</div>
            <div class="info-item"><strong>Status:</strong> ${os.status}</div>
            <div class="info-item"><strong>Prioridade:</strong> ${os.prioridade}</div>
            <div class="info-item"><strong>Tipo:</strong> ${os.tipo}</div>
            <div class="info-item"><strong>Técnico Responsável:</strong>
                <c:if test="${empty os.tecnicoResponsavel}">
                    Nenhum técnico atribuído
                </c:if>
                <c:if test="${not empty os.tecnicoResponsavel}">
                    ${os.tecnicoResponsavel.nome}
                </c:if>
            </div>
            <div class="info-item"><strong>Data de Abertura:</strong> ${os.dataDeAbertura}</div>
            <div class="info-item"><strong>Data de Fechamento:</strong> ${os.dataDeFechamento}</div>
            <a href="/" class="back-link" style="margin-top: 20px;">Voltar ao Dashboard</a>
        </div>

        <div class="detail-card">
            <h2>Ações</h2>
            
            <form action="/os/${os.id}/alterar-status" method="post" style="padding:0; border:none; box-shadow:none;">
                <label for="novoStatus">Alterar Status:</label>
                <select id="novoStatus" name="novoStatus">
                    <c:forEach var="status" items="${statusOptions}">
                        <option value="${status}" ${status == os.status ? 'selected' : ''}>
                            ${status}
                        </option>
                    </c:forEach>
                </select>
                <button type="submit" style="margin-top:10px;">Salvar Status</button>
            </form>
            
            <hr>

            <form action="/os/${os.id}/atribuir-tecnico" method="post" style="padding:0; border:none; box-shadow:none;">
                <label for="tecnicoId">Atribuir Técnico:</label>
                <select id="tecnicoId" name="tecnicoId">
                    <option value="">-- Desatribuir --</option>
                    <c:forEach var="tecnico" items="${tecnicos}">
                        <option value="${tecnico.id}" ${tecnico.id == os.tecnicoResponsavel.id ? 'selected' : ''}>
                            ${tecnico.nome}
                        </option>
                    </c:forEach>
                </select>
                <button type="submit">Salvar Técnico</button>
            </form>
            
            <hr>

            <form action="/os/${os.id}/editar" method="post" style="padding:0; border:none; box-shadow:none;">
                <h3>Editar Chamado</h3>
                <label for="titulo">Título:</label>
                <input type="text" id="titulo" name="titulo" value="${os.titulo}" required>

                <label for="descricao">Descrição:</label>
                <textarea id="descricao" name="descricao" rows="3">${os.descricao}</textarea>

                <label for="tipo">Tipo:</label>
                <select id="tipo" name="tipo">
                    <option value="Corretiva" ${os.tipo == 'Corretiva' ? 'selected' : ''}>Corretiva</option>
                    <option value="Preventiva" ${os.tipo == 'Preventiva' ? 'selected' : ''}>Preventiva</option>
                </select>

                <label for="prioridade">Prioridade:</label>
                <select id="prioridade" name="prioridade">
                    <c:forEach var="p" items="${prioridades}">
                        <option value="${p}" ${p == os.prioridade ? 'selected' : ''}>${p}</option>
                    </c:forEach>
                </select>
                <button type="submit">Salvar Alterações</button>
            </form>
            
            <hr>

            <form action="/os/${os.id}/excluir" method="post" onsubmit="return confirm('Tem certeza que deseja excluir este chamado?');" style="padding:0; border:none; box-shadow:none;">
                <h3>Excluir Chamado</h3>
                <p>Esta ação não pode ser desfeita.</p>
                <button type="submit" class="btn-delete">Excluir Permanentemente</button>
            </form>
        </div>
    </div>
</body>
</html>