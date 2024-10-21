<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listagem de Usuários</title>
    <script>
        // Função para listar todos os usuários
        async function listarUsuarios() {
            try {
                // Faz uma requisição GET para a API de usuários
                const response = await fetch('/users');

                // Verifica se a resposta foi bem-sucedida (status 200 OK)
                if (!response.ok) {
                    throw new Error('Erro ao buscar usuários');
                }

                // Converte a resposta para JSON
                const usuarios = await response.json();

                // Seleciona o elemento da tabela onde os dados serão inseridos
                const tabela = document.getElementById('tabelaUsuarios');
                tabela.innerHTML = ''; // Limpa a tabela antes de preenchê-la

                // Itera sobre a lista de usuários e cria uma linha para cada um
                usuarios.forEach(usuario => {
                    const linha = `<tr>
                                    <td>${usuario.id_user}</td>
                                    <td>${usuario.name}</td>
                                    <td>${usuario.email}</td>
                                    <td>${usuario.id_userType}</td>
                                    <td>${usuario.registrationDate}</td>
                                    <td>${usuario.inactive ? 'Inativo' : 'Ativo'}</td>
                                    <td>
                                        <button onclick="deletarUsuario(${usuario.id_user})">Deletar</button>
                                        <button onclick="editarUsuario(${usuario.id_user})">Editar</button>
                                    </td>
                                  </tr>`;

                    // Adiciona a linha à tabela
                    tabela.innerHTML += linha;
                });
            } catch (error) {
                console.error('Erro:', error);
                alert('Ocorreu um erro ao listar os usuários.');
            }
        }

        // Função para ser executada quando a página carregar
        window.onload = listarUsuarios;
    </script>
</head>
<body>
    <h1>Listagem de Usuários</h1>

    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Email</th>
                <th>Tipo de Usuário</th>
                <th>Data de Registro</th>
                <th>Status</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody id="tabelaUsuarios">
            <!-- As linhas de usuários serão inseridas aqui dinamicamente -->
        </tbody>
    </table>
</body>
</html>
