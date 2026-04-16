import './App.css';
import { useEffect, useState } from 'react';

const initialForm = {
  nome: '',
  sexo: '',
  email: '',
  dataNascimento: '',
  estado: '',
  pais: '',
  cpf: ''
};

function formatDateToBackend(dateValue) {
  const [year, month, day] = dateValue.split('-');
  return `${day}/${month}/${year}`;
}

function formatDateToInput(dateValue) {
  if (!dateValue) {
    return '';
  }

  const [day, month, year] = dateValue.split('/');
  if (!day || !month || !year) {
    return '';
  }

  return `${year}-${month}-${day}`;
}

function buildErrorMessage(responseBody) {
  if (!responseBody) {
    return 'Nao foi possivel processar a operacao.';
  }

  if (responseBody.erros && typeof responseBody.erros === 'object') {
    return Object.values(responseBody.erros).join(' | ');
  }

  if (responseBody.mensagem) {
    return responseBody.mensagem;
  }

  return 'Nao foi possivel processar a operacao.';
}

async function readJsonSafely(response) {
  try {
    return await response.json();
  } catch (error) {
    return null;
  }
}

function App() {
  const [form, setForm] = useState(initialForm);
  const [editingId, setEditingId] = useState(null);
  const [removingId, setRemovingId] = useState(null);
  const [pessoas, setPessoas] = useState([]);
  const [listLoading, setListLoading] = useState(false);
  const [listError, setListError] = useState('');
  const [loading, setLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');


  const handleDelete = async (id) => {
    if (!id) return;

    const confirmDelete = window.confirm(
        'Tem certeza que deseja excluir esta pessoa?'
    );

    if (!confirmDelete) return;

    setLoading(true);
    setSuccessMessage('');
    setErrorMessage('');

    try {
      let response = await fetch(`/api/v1/pessoas/${id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        }
      });

      // Fallback para versões antigas da API, se necessário
      if (response.status === 404) {
        response = await fetch(`/pessoas/${id}`, {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json'
          }
        });
      }

      const responseBody = await readJsonSafely(response);

      if (!response.ok) {
        setErrorMessage(buildErrorMessage(responseBody));
        return;
      }

      setSuccessMessage('Pessoa excluída com sucesso.');

      // Se a pessoa excluída estiver em edição, limpa o formulário
      if (editingId === id) {
        setForm(initialForm);
        setEditingId(null);
      }

      await loadPessoas();
    } catch (error) {
      setErrorMessage('Erro de conexão com o backend.');
    } finally {
      setLoading(false);
    }
  };

  const loadPessoas = async () => {
    setListLoading(true);
    setListError('');

    try {
      let response = await fetch('/pessoas/');

      if (response.status === 404) {
        response = await fetch('/pessoas/v1/');
      }

      if (!response.ok) {
        setListError('Nao foi possivel carregar as pessoas cadastradas.');
        setPessoas([]);
        return;
      }

      const body = await response.json();
      setPessoas(Array.isArray(body) ? body : []);
    } catch (error) {
      setListError('Erro de conexao ao consultar pessoas.');
      setPessoas([]);
    } finally {
      setListLoading(false);
    }
  };

  useEffect(() => {
    loadPessoas();
  }, []);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setForm((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setLoading(true);
    setSuccessMessage('');
    setErrorMessage('');

    const payload = {
      ...form,
      sexo: form.sexo || null,
      dataNascimento: formatDateToBackend(form.dataNascimento)
    };

    try {
      let response;
      if (editingId) {
        response = await fetch(`/pessoas/${editingId}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(payload)
        });

        if (response.status === 404) {
          response = await fetch(`/pessoas/v1/${editingId}`, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
          });
        }
      } else {
        response = await fetch('/pessoas/v1/', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(payload)
        });
      }

      const responseBody = await readJsonSafely(response);

      if (!response.ok) {
        setErrorMessage(buildErrorMessage(responseBody));
        return;
      }

      setSuccessMessage(
        editingId
          ? 'Pessoa atualizada com sucesso.'
          : 'Pessoa cadastrada com sucesso.'
      );
      setForm(initialForm);
      setEditingId(null);
      await loadPessoas();
    } catch (error) {
      setErrorMessage('Erro de conexao com o backend.');
    } finally {
      setLoading(false);
    }
  };

  const handleStartEdit = (pessoa) => {
    setSuccessMessage('');
    setErrorMessage('');
    setEditingId(pessoa.id);
    setForm({
      nome: pessoa.nome || '',
      sexo: pessoa.sexo || '',
      email: pessoa.email || '',
      dataNascimento: formatDateToInput(pessoa.dataNascimento),
      estado: pessoa.estado || '',
      pais: pessoa.pais || '',
      cpf: pessoa.cpf || ''
    });
  };

  const handleCancelEdit = () => {
    setEditingId(null);
    setForm(initialForm);
    setSuccessMessage('');
    setErrorMessage('');
  };

  return (
    <main className="app-container">
      <section className="form-card split-layout">
        <div>
          <h1>{editingId ? 'Editar Pessoa' : 'Cadastro de Pessoa'}</h1>
          <p>
            {editingId
              ? 'Edite os dados e envie para o endpoint PUT /pessoas/{id}.'
              : 'Preencha os campos e envie para o endpoint POST /pessoas/v1.'}
          </p>

          <form onSubmit={handleSubmit} className="person-form">
            <label htmlFor="nome">Nome*</label>
            <input id="nome" name="nome" value={form.nome} onChange={handleChange} required />

            <label htmlFor="cpf">CPF*</label>
            <input id="cpf" name="cpf" value={form.cpf} onChange={handleChange} required />

            <label htmlFor="sexo">Sexo</label>
            <select id="sexo" name="sexo" value={form.sexo} onChange={handleChange}>
              <option value="">Selecione</option>
              <option value="M">Mulher</option>
              <option value="H">Homem</option>
            </select>

            <label htmlFor="email">Email</label>
            <input id="email" name="email" type="email" value={form.email} onChange={handleChange} />

            <label htmlFor="dataNascimento">Data de nascimento*</label>
            <input
              id="dataNascimento"
              name="dataNascimento"
              type="date"
              value={form.dataNascimento}
              onChange={handleChange}
              required
              />

            <label htmlFor="estado">Estado</label>
            <input id="estado" name="estado" maxLength="2" value={form.estado} onChange={handleChange} />

            <label htmlFor="pais">Pais</label>
            <input id="pais" name="pais" value={form.pais} onChange={handleChange} />


            <div className="form-actions">
              <button type="submit" disabled={loading}>
                {loading ? 'Enviando...' : editingId ? 'Salvar alteracoes' : 'Cadastrar Pessoa'}
              </button>
              {editingId && (
                <button type="button" className="cancel-button" onClick={handleCancelEdit} disabled={loading}>
                  Cancelar edicao
                </button>
              )}
            </div>
          </form>

          {successMessage && <p className="feedback success">{successMessage}</p>}
          {errorMessage && <p className="feedback error">{errorMessage}</p>}
        </div>

        <div className="list-section">
          <div className="list-header">
            <h2>Pessoas cadastradas</h2>
            <button type="button" onClick={loadPessoas} disabled={listLoading}>
              {listLoading ? 'Carregando...' : 'Atualizar lista'}
            </button>
          </div>

          {listError && <p className="feedback error">{listError}</p>}

          {!listError && pessoas.length === 0 && !listLoading && (
            <p className="empty-list">Nenhuma pessoa cadastrada.</p>
          )}

          {pessoas.length > 0 && (
            <div className="table-wrapper">
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>CPF</th>
                    <th>Acoes</th>
                  </tr>
                </thead>
                <tbody>
                  {pessoas.map((pessoa) => (
                    <tr key={pessoa.id}>
                      <td>{pessoa.id}</td>
                      <td>{pessoa.nome}</td>
                      <td>{pessoa.email || '-'}</td>
                      <td>{pessoa.cpf}</td>
                      <td>
                        <button
                          type="button"
                          className="table-edit-button"
                          onClick={() => handleStartEdit(pessoa)}
                        >
                          Editar
                        </button>
                      </td>
                      <td>
                        <button
                            type="button"
                            className="table-edit-button"
                            onClick={() => handleStartEdit(pessoa)}
                        >
                          Excluir
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </section>
    </main>
  );
}

export default App;
