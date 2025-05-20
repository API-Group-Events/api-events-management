# 🎉 API de Gerenciamento de Eventos

## 🛠️ Tecnologias Utilizadas
- Java 21
- Spring Boot 3.4.5
- MySQL 8.0

## 📋 Visão Geral
API RESTful para gerenciamento de eventos e participantes, desenvolvida com Spring Boot. Permite criar, listar, atualizar e excluir eventos, além de gerenciar a inscrição de participantes.

## 🚀 Endpoints

### Eventos

#### 1. Listar todos os eventos
- **Método:** `GET`
- **Endpoint:** `/eventos`
- **Corpo:** Não requer
- **Resposta de Sucesso:** 200 OK com lista de eventos

#### 2. Buscar evento por ID
- **Método:** `GET`
- **Endpoint:** `/eventos/{id}`
- **Corpo:** Não requer
- **Resposta de Sucesso:** 200 OK com detalhes do evento
- **Resposta de Erro:** 404 Not Found se não encontrado

#### 3. Criar novo evento
- **Método:** `POST`
- **Endpoint:** `/eventos`
- **Headers:** `Content-Type: application/json`
- **Corpo:**
  ```json
  {
      "nome": "Workshop de Spring Boot",
      "descricao": "Aprenda Spring Boot do zero",
      "data": "2025-06-15",
      "local": "Auditório Principal",
      "vagas": 50
  }
  ```
- **Resposta de Sucesso:** 201 Created com o evento criado

#### 4. Atualizar evento
- **Método:** `PUT`
- **Endpoint:** `/eventos/{id}`
- **Headers:** `Content-Type: application/json`
- **Corpo:**
  ```json
  {
      "nome": "Workshop Atualizado",
      "descricao": "Descrição atualizada",
      "data": "2025-06-20",
      "local": "Novo Local",
      "vagas": 40
  }
  ```
- **Resposta de Sucesso:** 200 OK com o evento atualizado
- **Resposta de Erro:** 404 Not Found se não encontrado

#### 5. Excluir evento
- **Método:** `DELETE`
- **Endpoint:** `/eventos/{id}`
- **Corpo:** Não requer
- **Resposta de Sucesso:** 204 No Content
- **Resposta de Erro:** 404 Not Found se não encontrado

### Participantes

#### 1. Inscrever participante em evento
- **Método:** `POST`
- **Endpoint:** `/eventos/{eventoId}/participantes`
- **Headers:** `Content-Type: application/json`
- **Corpo:**
  ```json
  {
      "participanteId": 1
  }
  ```
- **Resposta de Sucesso:** 200 OK
- **Resposta de Erro:** 
  - 400 Bad Request se participante já estiver inscrito
  - 404 Not Found se evento ou participante não existirem
  - 400 Bad Request se não houver vagas disponíveis

#### 2. Listar participantes de um evento
- **Método:** `GET`
- **Endpoint:** `/eventos/{eventoId}/participantes`
- **Corpo:** Não requer
- **Resposta de Sucesso:** 200 OK com lista de participantes

#### 3. Cancelar inscrição de participante
- **Método:** `DELETE`
- **Endpoint:** `/eventos/{eventoId}/participantes/{participanteId}`
- **Corpo:** Não requer
- **Resposta de Sucesso:** 204 No Content
- **Resposta de Erro:** 404 Not Found se não encontrado

