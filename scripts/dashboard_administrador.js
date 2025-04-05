// Verificar se o usuário está autenticado como administrador
document.addEventListener('DOMContentLoaded', function() {
    const token = localStorage.getItem('authToken');
    const userData = JSON.parse(localStorage.getItem('userData') || '{}'); 
    const userRole = userData.role;
    const userName = userData.nome;
    
    if (!token || userRole !== 'ADMINISTRADOR') {
        // Redirecionar para a página de login se não estiver autenticado ou não for administrador
        window.location.href = 'index.html';
        return;
    }
    
    // Exibir nome do usuário
    if (userName) {
        document.getElementById('usuarioNome').textContent = `Olá, ${userName}`;
    }
    
    // Configurar evento de logout
    document.getElementById('btnLogout').addEventListener('click', function(e) {
        e.preventDefault();
        logout();
    });
    
    // Configurar evento de filtro
    document.getElementById('btnFiltrar').addEventListener('click', function() {
        carregarPropriedades();
    });
    
    // Configurar evento de validação de propriedade
    document.getElementById('btnConfirmarValidacao').addEventListener('click', validarPropriedade);
    
    // Carregar lista de propriedades
    carregarPropriedades();
});

// Função para fazer logout
function logout() {
    // Limpar dados da sessão
    localStorage.removeItem('authToken');
    localStorage.removeItem('userData');
    
    // Redirecionar para a página de login
    window.location.href = 'index.html';
}

// Função para carregar todas as propriedades
function carregarPropriedades() {
    const token = localStorage.getItem('authToken');
    const filtroStatus = document.getElementById('filtroStatus').value;
    const filtroProprietario = document.getElementById('filtroProprietario').value;
    
    //TODO: Aqui poderia ser implementada a lógica para incluir os filtros na requisição
    
    
    fetch(API_ROUTES.PROPRIEDADES.BASE, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.status === 'success') {
            // TODO: Filtrar os dados no lado do cliente
            let propriedades = data.data;
            
            if (filtroStatus) {
                propriedades = propriedades.filter(p => p.status === filtroStatus);
            }
            
            if (filtroProprietario) {
                propriedades = propriedades.filter(p => 
                    p.proprietario && p.proprietario.nome && 
                    p.proprietario.nome.toLowerCase().includes(filtroProprietario.toLowerCase())
                );
            }
            
            exibirPropriedades(propriedades);
        } else {
            mostrarMensagem('Erro', data.error || 'Erro ao carregar propriedades');
        }
    })
    .catch(error => {
        console.error('Erro ao carregar propriedades:', error);
        mostrarMensagem('Erro', 'Erro ao carregar propriedades. Por favor, tente novamente.');
    });
}

// Função para exibir as propriedades na tabela
function exibirPropriedades(propriedades) {
    const listaElement = document.getElementById('listaPropriedades');
    const semPropriedadesElement = document.getElementById('semPropriedades');
    
    if (!propriedades || propriedades.length === 0) {
        listaElement.innerHTML = '';
        semPropriedadesElement.classList.remove('d-none');
        return;
    }
    
    semPropriedadesElement.classList.add('d-none');
    listaElement.innerHTML = '';
    
    propriedades.forEach(prop => {
        const row = document.createElement('tr');
        
        // Determinar a classe de status para estilização
        let statusClass = '';
        switch (prop.status) {
            case 'PENDENTE':
                statusClass = 'text-warning';
                break;
            case 'APROVADO':
                statusClass = 'text-success';
                break;
            case 'RECUSADO':
                statusClass = 'text-danger';
                break;
        }
        
        row.innerHTML = `
            <td>${prop.nome}</td>
            <td>${prop.proprietario ? prop.proprietario.nome : 'N/A'}</td>
            <td>${prop.cidade}, ${prop.estado}, ${prop.pais}</td>
            <td>${prop.areaPreservada} m²</td>
            <td>${prop.producaoCarbono} ton</td>
            <td class="${statusClass}">${prop.status}</td>
            <td>
                ${prop.status === 'PENDENTE' ? 
                    `<button class="btn btn-sm btn-primary btn-validar" data-id="${prop.id}">Validar</button>` : 
                    `<button class="btn btn-sm btn-outline-secondary btn-detalhes" data-id="${prop.id}">Detalhes</button>`
                }
            </td>
        `;
        
        listaElement.appendChild(row);
    });
    
    // Adicionar eventos aos botões de validação
    document.querySelectorAll('.btn-validar').forEach(btn => {
        btn.addEventListener('click', function() {
            const propriedadeId = this.getAttribute('data-id');
            abrirModalValidacao(propriedadeId);
        });
    });
    
    // Adicionar eventos aos botões de detalhes (caso implementado no futuro)
    document.querySelectorAll('.btn-detalhes').forEach(btn => {
        btn.addEventListener('click', function() {
            const propriedadeId = this.getAttribute('data-id');
            // Implementação futura - exibir detalhes da propriedade
            mostrarMensagem('Detalhes', `Detalhes da propriedade ID: ${propriedadeId}`);
        });
    });
}

// Função para abrir o modal de validação de propriedade
function abrirModalValidacao(propriedadeId) {
    document.getElementById('propriedadeId').value = propriedadeId;
    document.getElementById('statusPropriedade').value = 'APROVADA'; // Valor padrão
    document.getElementById('mensagemValidacao').value = '';
    
    const modal = new bootstrap.Modal(document.getElementById('validarPropriedadeModal'));
    modal.show();
}

// Função para validar uma propriedade
function validarPropriedade() {
    const token = localStorage.getItem('authToken');
    const propriedadeId = document.getElementById('propriedadeId').value;
    const status = document.getElementById('statusPropriedade').value;
    const mensagem = document.getElementById('mensagemValidacao').value;
    
    fetch(API_ROUTES.PROPRIEDADES.VALIDAR(propriedadeId), {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => response.json())
    .then(data => {
        // Fechar o modal de validação
        const modalElement = document.getElementById('validarPropriedadeModal');
        const modal = bootstrap.Modal.getInstance(modalElement);
        modal.hide();
        
        if (data.success) {
            mostrarMensagem('Sucesso', 'Propriedade validada com sucesso!');
            // Recarregar a lista de propriedades
            carregarPropriedades();
        } else {
            mostrarMensagem('Erro', data.message || 'Erro ao validar propriedade');
        }
    })
    .catch(error => {
        console.error('Erro ao validar propriedade:', error);
        mostrarMensagem('Erro', 'Erro ao validar propriedade. Por favor, tente novamente.');
    });
}

// Função para mostrar mensagens em um modal
function mostrarMensagem(titulo, mensagem) {
    const modalTitle = document.getElementById('messageModalLabel');
    const modalBody = document.getElementById('messageModalBody');
    
    modalTitle.textContent = titulo;
    modalBody.textContent = mensagem;
    
    const messageModal = new bootstrap.Modal(document.getElementById('messageModal'));
    messageModal.show();
}