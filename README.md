# Textrix
Um projeto robusto de CRUD de Parceiros com Spring Boot 🚀  

## 📖 Sobre o Projeto
**Textrix** é um projeto desenvolvido com Java 17 e Spring Boot. Ele implementa um CRUD completo com configurações para produção e homologação, MySQL (produção) e H2 (homologação).  

# Principais recursos incluem:  
- Gerenciamento de Parceiros e seus Endereços.  
- Suporte a perfis de ambiente (prod e dev).  
- Validações personalizadas e documentação com **Springdoc OpenAPI**.  
- Testes unitários, de integração e de ponta a ponta.

## 🛠️ Tecnologias Utilizadas
As principais ferramentas e bibliotecas usadas no projeto:  

| Tecnologia             | Versão        | Descrição                            |
|------------------------|---------------|--------------------------------------|
| Java                   | 17            | Linguagem principal do projeto       |
| Spring Boot            | 3.4.1         | Framework para criação da aplicação  |
| MySQL                  | 8.0.40        | Banco de dados para produção         |
| H2 Database            | x             | Banco em memória para homologação    |
| Springdoc OpenAPI      | 2.7.0         | Documentação automática da API       |
| Maven                  | 3.x           | Gerenciador de dependências          |
| IntelliJ IDEA          | Última        | IDE recomendada para desenvolvimento |

## ⚙️ Configuração e Execução

1️⃣ Clone o Repositório 
git clone https://github.com/diegomarquesf/textrix.git
cd textrix

2️⃣ Configure o Banco de Dados
# Produção (MySQL):
Crie um banco de dados no MySQL com o nome textrix.
Atualize o arquivo application-dev.properties com as credenciais de acesso ao banco de dados.
# Homologação (H2):
O banco H2 será configurado automaticamente ao rodar o perfil test.
** em application.properties mude o profile para o perfil desejado.

## ✨ Principais Funcionalidades
# Gerenciamento de Parceiros:
📌 Cadastro, atualização, busca parceiro por chave, listagem parceiros ativos e listagem de todos parceiros.
🚫 Exclusão lógica com flag excluido.
# Relacionamentos:
📍 Relacionamento @OneToOne entre Parceiro e Endereço, com cascata para persistência e atualização.
# Validações Personalizadas:
🛡️ Validações customizadas com mensagens detalhadas de erro, melhorando a experiência do usuário e a integridade dos dados.
# Documentação Interativa:
🔍 Documentação gerada automaticamente via Springdoc OpenAPI.
# Testes:
✅ Testes unitários, de integração e de ponta a ponta (e2e).

## 📄 Estrutura de Diretórios
textrix
├── src
│   ├── main
│   │   ├── java
│   │   │   └── br.com.diegomarques.textrix
│   │   │       ├── configs           # Arquivos de configuração da aplicação
│   │   │       ├── domains           # Entidades, modelos e objetos de domínio
│   │   │       │   ├── dtos          # Objetos de transferência de dados (DTOs)
│   │   │       │   └── enums         # Enums
│   │   │       ├── repositories      # Repositórios JPA (interface com o banco de dados)
│   │   │       ├── resources         # Arquivos estáticos, templates e mensagens
│   │   │       │   └── exceptions    # Exceções e mensagens personalizadas
│   │   │       ├── services          # Lógica de negócios e serviços da aplicação
│   │   │       │   └── exceptions    # Exceções de serviço personalizadas
│   │   │       ├── validations       # Validações personalizadas e interfaces
│   │   └── resources
│   │       ├── application-dev.properties   # Configurações para o ambiente de desenvolvimento
│   │       ├── application-test.properties  # Configurações para o ambiente de teste
│   │       ├── application.properties       # Configurações principais da aplicação
│   ├── test
│   │   ├── java
│   │   │   └── br.com.diegomarques.textrix
│   │   │       ├── e2e
│   │   │           └── api          # Testes de ponta a ponta para a API
│   │   │       ├── integration
│   │   │           └── services     # Testes de integração
│   │   │       ├── services
│   │   │           └── tests        # Testes unitários

## 🌐 Documentação da API
Após iniciar a aplicação, acesse a documentação interativa da API através do navegador:
http://localhost:8080/swagger-ui.html

## 📜 Licença
Este projeto está licenciado sob a MIT License. Consulte o arquivo LICENSE para mais detalhes.
