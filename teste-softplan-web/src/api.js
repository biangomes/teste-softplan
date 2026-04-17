const API_BASE = '/api';

export async function getUsuarios() {
    const response = await fetch(`${API_BASE}/pessoas`);

    if (!response.ok) {
        throw new Error('Erro ao buscar pessoas');
    }

    return response.json();
}