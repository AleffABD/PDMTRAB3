📌 ToDo List com Autenticação Firebase
📖 Descrição

Este projeto é a reescrita do aplicativo ToDo List desenvolvido no Trabalho 1 da disciplina, agora integrado com autenticação em nuvem utilizando Firebase Authentication.

O objetivo foi implementar:

Autenticação de usuário (Login e Sign Up)

Gerenciamento de estado com Jetpack Compose

Persistência local com Room

Separação de tarefas por usuário autenticado

Navegação protegida

Cada usuário possui sua própria conta e visualiza apenas suas próprias tarefas.

🚀 Funcionalidades

Cadastro de usuário (Sign Up)

Login com e-mail e senha

Logout

Criar tarefa

Editar tarefa

Marcar tarefa como concluída

Excluir tarefa

Persistência local com Room

Separação de tarefas por usuário (via UID do Firebase)

🏗️ Arquitetura

O projeto segue o padrão MVVM (Model-View-ViewModel):

View (Compose) → Telas e componentes de interface

ViewModel → Controle de estado e lógica da tela

Repository → Intermediação entre ViewModel e banco de dados

Room (Database) → Persistência local

Firebase Authentication → Controle de autenticação

O componente AuthGate é responsável por verificar se existe um usuário autenticado e decidir qual tela deve ser exibida (Login ou Lista de Tarefas).

🧠 Gerenciamento de Estado

O estado da aplicação é controlado pelos ViewModels.

A lista de tarefas é exposta como Flow

A UI observa usando collectAsState()

A interface é automaticamente atualizada quando o banco muda

Isso garante uma UI reativa e organizada.

🔐 Autenticação

A autenticação é feita com Firebase Authentication (Email e Senha).

O usuário cria conta

O Firebase mantém a sessão ativa

O UID do usuário é usado para associar cada tarefa ao dono

As consultas do Room filtram tarefas por userId

Isso garante que cada usuário veja apenas suas próprias tarefas.

🗄️ Persistência de Dados

A persistência é feita utilizando Room Database.

Cada tarefa possui:

id

title

description

isCompleted

userId (UID do Firebase)

O banco é recriado automaticamente caso o schema seja alterado (fallbackToDestructiveMigration), pois trata-se de um projeto acadêmico.

🛠️ Tecnologias Utilizadas

Kotlin

Jetpack Compose

Room Database

Firebase Authentication

Navigation Compose

MVVM

🔄 Melhorias Futuras

Integração com Firebase Firestore (sincronização em nuvem)

Backup automático

Modo offline avançado

Notificações

Melhorias visuais (UI/UX)

🤖 Uso de LLMs

Foram utilizadas LLMs como apoio ao desenvolvimento:

ChatGPT

Gemini

As LLMs auxiliaram na:

Correção de erros

Estruturação da arquitetura

Ajustes na integração com Firebase

Organização do código

Todas as decisões finais e integrações foram validadas manualmente pelo grupo.

👥 Integrantes

Aleff Arantes Abdala Azevedo

diogo ricarte loureiro menezes
