# 👤 API de Gerenciamento de Usuários

API REST desenvolvida com **Java e Spring Boot** para gerenciamento de usuários, endereços e telefones.

O projeto foi desenvolvido com o objetivo de aplicar conceitos de desenvolvimento Back-end utilizando o ecossistema Spring, incluindo **arquitetura em camadas, persistência de dados, DTOs, tratamento de exceções e autenticação utilizando JWT**.

## 🚀 Tecnologias utilizadas

* Java 17
* Spring Boot
* Spring Data JPA
* Spring Security
* JWT (JSON Web Token)
* PostgreSQL
* Gradle
* Lombok
* Postman
* Git

## ⚙️ Funcionalidades

A API permite realizar operações relacionadas ao gerenciamento de usuários e seus dados.

Entre as principais funcionalidades estão:

* Cadastro de usuários
* Autenticação de usuários
* Autenticação e autorização utilizando JWT
* Busca de usuários
* Atualização de dados do usuário
* Cadastro e atualização de endereços
* Cadastro e atualização de telefones
* Persistência de dados em PostgreSQL
* Validação e tratamento de exceções
* Utilização de DTOs para transferência de dados

### Controller

Responsável por receber as requisições HTTP e disponibilizar os endpoints da API.

### Service

Contém as regras de negócio e realiza a comunicação entre os controllers e a camada de persistência.

### Repository

Responsável pelo acesso e persistência dos dados utilizando **Spring Data JPA**.

### DTO

Utilizado para transferência de dados entre as diferentes camadas da aplicação, evitando a exposição direta das entidades.

## 🔐 Segurança

A aplicação utiliza **Spring Security** em conjunto com **JWT (JSON Web Token)** para autenticação e controle de acesso aos endpoints protegidos.

## 🗄️ Banco de dados

O projeto utiliza **PostgreSQL** como banco de dados relacional e **Spring Data JPA** para realizar a persistência das informações.

## 📡 API REST

A aplicação segue os princípios de uma API REST e utiliza métodos HTTP.

As requisições podem ser testadas utilizando ferramentas como **Postman** ou outros clientes HTTP.

## ▶️ Como executar o projeto

## 🎯 Objetivo do projeto

Este projeto faz parte do meu desenvolvimento profissional como **Desenvolvedor Back-end Java**, colocando em prática conceitos importantes do desenvolvimento de APIs utilizando **Java, Spring Boot, bancos de dados relacionais e autenticação com JWT**.

O projeto também busca aplicar boas práticas de organização de código, separação de responsabilidades e desenvolvimento de aplicações de fácil manutenção e evolução.

## 👨‍💻 Autor

**Rayan Almeida**

Desenvolvedor Back-end Java

Tecnologias principais:

`Java` • `Spring Boot` • `Spring Data JPA` • `Spring Security` • `JWT` • `PostgreSQL`
