import './App.css';
import { useState } from 'react';
import AppV1 from './AppV1';
import AppV2 from './AppV2';

function App() {
  const [selectedVersion, setSelectedVersion] = useState(null);

  if (selectedVersion === null) {
    return (
      <main className="app-container">
        <section className="form-card">
          <div style={{ textAlign: 'center', padding: '40px' }}>
            <h1>Bem-vindo ao Teste Softplan</h1>
            <p>Escolha qual versão da API deseja utilizar:</p>

            <div style={{
              display: 'flex',
              gap: '20px',
              justifyContent: 'center',
              marginTop: '40px',
              flexWrap: 'wrap'
            }}>
              <div style={{
                border: '2px solid #007bff',
                borderRadius: '8px',
                padding: '30px',
                textAlign: 'center',
                minWidth: '300px',
                backgroundColor: '#f8f9fa'
              }}>
                <h2>Versão 1</h2>
                <p><strong>Endpoint:</strong> /pessoas/v1</p>
                <p><strong>Endereço:</strong> Opcional</p>
                <p style={{ fontSize: '14px', color: '#666' }}>
                  Cadastra apenas dados básicos sem endereço.
                </p>
                <button
                  onClick={() => setSelectedVersion('v1')}
                  style={{
                    padding: '12px 30px',
                    fontSize: '16px',
                    backgroundColor: '#007bff',
                    color: 'white',
                    border: 'none',
                    borderRadius: '4px',
                    cursor: 'pointer',
                    marginTop: '20px'
                  }}
                >
                  Usar Versão 1
                </button>
              </div>

              <div style={{
                border: '2px solid #28a745',
                borderRadius: '8px',
                padding: '30px',
                textAlign: 'center',
                minWidth: '300px',
                backgroundColor: '#f8f9fa'
              }}>
                <h2>Versão 2</h2>
                <p><strong>Endpoint:</strong> /api/v2/pessoas</p>
                <p><strong>Endereço:</strong> <strong style={{ color: '#28a745' }}>Obrigatório</strong></p>
                <p style={{ fontSize: '14px', color: '#666' }}>
                  Cadastra dados completos com endereço incluído.
                </p>
                <button
                  onClick={() => setSelectedVersion('v2')}
                  style={{
                    padding: '12px 30px',
                    fontSize: '16px',
                    backgroundColor: '#28a745',
                    color: 'white',
                    border: 'none',
                    borderRadius: '4px',
                    cursor: 'pointer',
                    marginTop: '20px'
                  }}
                >
                  Usar Versão 2
                </button>
              </div>
            </div>
          </div>
        </section>
      </main>
    );
  }

  return (
    <>
      <div style={{
        backgroundColor: selectedVersion === 'v1' ? '#007bff' : '#28a745',
        color: 'white',
        padding: '15px',
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center'
      }}>
        <h2 style={{ margin: 0 }}>
          {selectedVersion === 'v1' ? 'API V1 - Sem Endereço' : 'API V2 - Com Endereço Obrigatório'}
        </h2>
        <button
          onClick={() => setSelectedVersion(null)}
          style={{
            padding: '8px 16px',
            fontSize: '14px',
            backgroundColor: 'rgba(255, 255, 255, 0.3)',
            color: 'white',
            border: '1px solid white',
            borderRadius: '4px',
            cursor: 'pointer'
          }}
        >
          Trocar Versão
        </button>
      </div>
      {selectedVersion === 'v1' ? <AppV1 /> : <AppV2 />}
    </>
  );
}

export default App;
