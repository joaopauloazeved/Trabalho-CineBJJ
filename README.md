# CineBJJ 🎬🥋

Sistema de recomendação de filmes desenvolvido em Java, utilizando perfis de usuário, filtros personalizados e cálculo de pontuação para sugerir conteúdos compatíveis com os gostos do usuário.

“Ajudamos você a Descobrir o passado”

---

# 📌 Descrição do Projeto

O **CineBJJ** é uma aplicação focada em recomendação de filmes baseada em:

- gêneros favoritos;
- duração preferida;
- classificação indicativa;
- idiomas aceitos;
- afinidade entre usuário e filme;
- filtros personalizados.

O sistema utiliza diferentes componentes para calcular scores, filtrar filmes e gerar recomendações personalizadas.

---

# 👨‍💻 Integrantes

- Carlos Bernando Goes dos Santos  
- João Vitor Nascimento Lavigne
- João Paulo Viana Macedo de Azevedo  

---

# 🛠️ Tecnologias Utilizadas

- Java
- Maven
- JUnit 5
- Mockito
- JaCoCo

---

# 📂 Estrutura do Projeto

```text
src/
 ├── main/
 │    └── java/
 │         ├── model
 │         ├── service
 │         ├── exception
 │         └── util
 │
 └── test/
      └── java/
```

---

# 📂 Estrutura Principal do Sistema

O projeto possui componentes como:

- `Usuario`
- `PerfilCinefilo`
- `Filme`
- `Recomendacao`
- `RecomendadorService`
- `CalculadorScore`
- `FiltroFilmes`
- `FiltroCapsulaTempo`

Além de enums e exceções customizadas para validação do sistema.

---

# ✅ Funcionalidades

- Filtragem de filmes
- Cálculo de score
- Recomendações personalizadas
- Surpreenda-me
- Notificações
- Histórico de recomendações
- Filtro por gênero
- Filtro por duração
- Filtro por ano
- Testes automatizados

---

# ▶️ Como Rodar o Projeto

## 1. Clonar o repositório

```bash
git clone URL_DO_REPOSITORIO
```

---

## 2. Entrar na pasta do projeto

```bash
cd CineBJJ
```

---

## 3. Rodar o projeto

```bash
Run As > Java Application
```

---

## 4. Rodar os testes

```bash
Run As > JUnit Test
```

---


# 📋 Cenários de Teste

| ID | Cenário | Entrada | Resultado esperado | Status |
|----|----------------------------------------------|------------------------------------------------------------------|------------------------------------|------|
| CT01 | Criar perfil com dados válidos | Pesos entre 0.0 e 1.0, duração 90–150 | Perfil criado sem exceção | ✅ |
| CT02 | Peso de gênero maior que 1 | ACAO = 2.0 | Lança PesoInvalidoException | ✅ |
| CT03 | Peso de gênero menor que 0 | DRAMA = -0.5 | Lança PesoInvalidoException | ✅ |
| CT04 | Duração mínima maior que máxima | Min 150, Max 90 | Lança DuracaoInvalidaException | ✅ |
| CT05 | Adicionar nota válida | Filme F1, nota 5 | Nota registrada no perfil | ✅ |
| CT06 | Adicionar nota inválida acima do limite | Filme F1, nota 10 | Lança exceção | ✅ |
| CT07 | Marcar filme como assistido | Filme F1 | jaAssistido("F1") retorna true | ✅ |
| CT08 | Criar filme com dados válidos | ID, título, duração, gêneros, classificação, idioma e popularidade | Filme criado corretamente | ✅ |
| CT09 | Comparar filmes com mesmo ID | Dois filmes com ID F1 | Filmes considerados iguais | ✅ |
| CT10 | Remover filme já assistido | Filme F1 marcado no histórico | Filme removido do resultado filtrado | ✅ |
| CT11 | Remover filme acima da classificação permitida | Filme 18, perfil máximo 16 | Filme removido do resultado | ✅ |
| CT12 | Remover filme em idioma não aceito | Filme em inglês, perfil aceita português | Filme removido do resultado | ✅ |
| CT13 | Remover filme com gênero peso 0 | Filme Terror, perfil TERROR = 0.0 | Filme removido do resultado | ✅ |
| CT14 | Filtro com catálogo vazio | Lista vazia de filmes | Retorna lista vazia | ✅ |
| CT15 | Calcular score alto para gênero preferido | Filme Ação, perfil ACAO = 1.0 | Score alto | ✅ |
| CT16 | Calcular score baixo para gênero não preferido | Filme Ação, perfil ACAO = 0.0 | Score baixo | ✅ |
| CT17 | Recomendar com catálogo vazio | API retorna lista vazia | Retorna lista vazia | ✅ |
| CT18 | Recomendação limitada por Top N | Catálogo com 3 filmes, topN = 2 | Retorna apenas 2 recomendações | ✅ |
| CT19 | Não derrubar sistema quando catálogo falha | API lança exceção | Retorna lista vazia e não notifica | ✅ |
| CT20 | Modo Surpreenda-me | Gerador retorna índice controlado | Retorna o filme esperado | ✅ |

---

# 📚 Conceitos Aplicados

- Programação Orientada a Objetos
- SOLID
- Testes unitários
- Mocking
- Tratamento de exceções
- Separação de responsabilidades
- Modelagem UML

---

# 📌 Observações

O projeto foi desenvolvido com foco acadêmico para aplicação de conceitos de:

- arquitetura orientada a objetos;
- testes e qualidade de software;
- modelagem UML;
- boas práticas em Java.
  
---

# Testes com JUnit
  
![Logo](https://media.discordapp.net/attachments/1499842095136112913/1504166690815086814/A36FA2C6-FAFF-42C8-8C17-01ADA448AE5F.png?ex=6a05ffe5&is=6a04ae65&hm=369ac6e2fea5ec5ef3d3e3f31c348b92ceb31ad7ab9d9acabf4d20b5a034abb0&=&format=webp&quality=lossless&width=279&height=265)

---

# Teste com Jacoco

![Logo](https://media.discordapp.net/attachments/1499842095136112913/1504166897510387773/92EFC5FA-13BA-410F-B0BF-195B03C4D859.png?ex=6a060016&is=6a04ae96&hm=ba3191dd0a0d4742f90366ff87aa877100f7241690cda23f9855da56cabd9988&=&format=webp&quality=lossless&width=1508&height=191)


