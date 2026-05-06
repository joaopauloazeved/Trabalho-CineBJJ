# CineMatch

Sistema de recomendação de filmes desenvolvido em Java.

## Tecnologias
- Java
- JUnit 5
- Mockito
- Maven

## Estrutura do Projeto

- model
- service
- exception
- util

## Funcionalidades

- Filtragem de filmes
- Cálculo de score
- Recomendações personalizadas
- Surpreenda-me
- Notificações
- Histórico de recomendações

## Testes

O projeto possui testes unitários utilizando:
- JUnit
- Mockito

## Cenários de Teste

| ID   | Cenário                                        | Entrada                                                            | Resultado esperado                   | Status |
| ---- | ---------------------------------------------- | ------------------------------------------------------------------ | ------------------------------------ | ------ |
| CT01 | Criar perfil com dados válidos                 | Pesos entre 0.0 e 1.0, duração 90–150                              | Perfil criado sem exceção            | ✅      |
| CT02 | Peso de gênero maior que 1                     | ACAO = 2.0                                                         | Lança PesoInvalidoException          | ✅      |
| CT03 | Peso de gênero menor que 0                     | DRAMA = -0.5                                                       | Lança PesoInvalidoException          | ✅      |
| CT04 | Duração mínima maior que máxima                | Min 150, Max 90                                                    | Lança DuracaoInvalidaException       | ✅      |
| CT05 | Adicionar nota válida                          | Filme F1, nota 5                                                   | Nota registrada no perfil            | ✅      |
| CT06 | Adicionar nota inválida acima do limite        | Filme F1, nota 10                                                  | Lança exceção                        | ✅      |
| CT07 | Marcar filme como assistido                    | Filme F1                                                           | jaAssistido("F1") retorna true       | ✅      |
| CT08 | Criar filme com dados válidos                  | ID, título, duração, gêneros, classificação, idioma e popularidade | Filme criado corretamente            | ✅      |
| CT09 | Comparar filmes com mesmo ID                   | Dois filmes com ID F1                                              | Filmes considerados iguais           | ✅      |
| CT10 | Remover filme já assistido                     | Filme F1 marcado no histórico                                      | Filme removido do resultado filtrado | ✅      |
| CT11 | Remover filme acima da classificação permitida | Filme 18, perfil máximo 16                                         | Filme removido do resultado          | ✅      |
| CT12 | Remover filme em idioma não aceito             | Filme em inglês, perfil aceita português                           | Filme removido do resultado          | ✅      |
| CT13 | Remover filme com gênero peso 0                | Filme Terror, perfil TERROR = 0.0                                  | Filme removido do resultado          | ✅      |
| CT14 | Filtro com catálogo vazio                      | Lista vazia de filmes                                              | Retorna lista vazia                  | ✅      |
| CT15 | Calcular score alto para gênero preferido      | Filme Ação, perfil ACAO = 1.0                                      | Score alto                           | ✅      |
| CT16 | Calcular score baixo para gênero não preferido | Filme Ação, perfil ACAO = 0.0                                      | Score baixo                          | ✅      |
| CT17 | Recomendar com catálogo vazio                  | API retorna lista vazia                                            | Retorna lista vazia                  | ✅      |
| CT18 | Recomendação limitada por Top N                | Catálogo com 3 filmes, topN = 2                                    | Retorna apenas 2 recomendações       | ✅      |
| CT19 | Não derrubar sistema quando catálogo falha     | API lança exceção                                                  | Retorna lista vazia e não notifica   | ✅      |
| CT20 | Modo Surpreenda-me                             | Gerador retorna índice controlado                                  | Retorna o filme esperado             | ✅      |
