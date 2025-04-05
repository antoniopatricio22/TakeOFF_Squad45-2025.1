// Configurações globais da aplicação

// URL base da API
const API_BASE_URL = 'https://takeoff-squad45-2025-1.onrender.com';

// Rotas específicas
const API_ROUTES = {
    LOGIN: `${API_BASE_URL}/auth/login`,
    REGISTRO: {
        PROPRIETARIO: `${API_BASE_URL}/registro/proprietario`,
        ADMINISTRADOR: `${API_BASE_URL}/registro/administrador` // front?
    },
    PROPRIEDADES: {
        BASE: `${API_BASE_URL}/propriedades`,
        VALIDAR: (id) => `${API_BASE_URL}/propriedades/${id}/validar` //TODO: reajustar
    },
    
};

// Tempo de expiração do token em milissegundos (exemplo: 4 horas)
const TOKEN_EXPIRATION = 4 * 60 * 60 * 1000; //necessario?

// Outras configurações globais podem ser adicionadas aqui