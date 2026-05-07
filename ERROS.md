# Relatório de Erros

## Classe `RecomendadorServiceTest`

### Bug 01 — Falha no registro
- **Teste:** `deve_RetornarListaVazia_Quando_CatalogoEstaVazio`
- **Correção:** Movida a chamada de `registrarRecomendacao` para garantir a execução mesmo sem filmes encontrados.

### Bug 02 — Ordenação
- **Teste:** `deve_OrdenarPorScoreDesc_Quando_Recomendar`
- **Correção:** Utilizado mock no `GeradorAleatorio` para tornar o teste determinístico e evitar falhas.

### Bug 03 — Notificação
- **Teste:** `deve_RetornarListaVazia_Quando_CatalogoEstaVazio`
- **Correção:** Adicionado o check `if (usuario.isNotificacoesAtivas())` antes de chamar o notificador.

### Bug 04 — Processamento após erro de API
- **Teste:** `deve_RetornarListaVazia_Quando_CatalogoLancaExcecao`
- **Correção:** Adicionado `return` imediato no bloco `catch` para abortar o fluxo após a exceção.

### Bug 05 — Erro de índice no sorteio
- **Teste:** `deve_RetornarFilmeAleatorio_Quando_SurpreendaMe`
- **Correção:** Adicionado `-1` no tamanho da lista para evitar `IndexOutOfBoundsException`.

---

## Classe `CalculadoraScoreTest`

### Bug 01 — Quebra de limite
- **Teste:** `deve_NuncaPassarDe100`
- **Correção:** Aplicado `Math.min(100, ...)` no resultado dos cálculos.

### Bug 02 — Erro de duração
- **Teste:** `deve_TerScoreMaior_Quando_DuracaoEstaDentroDoIntervalo`
- **Correção:** Implementada pontuação dinâmica conforme a duração.

### Bug 03 — Divisão por zero em perfis desatualizados ou novos
- **Correção:** Adicionada verificação `if (perfil.getNotas().isEmpty())` no cálculo de afinidade.

### Bug 04 — Peso de gênero desproporcional
- **Teste:** `deve_TerScoreBaixo_Quando_GeneroNaoPreferido`
- **Correção:** Alterada a soma simples pela média aritmética dos pesos de todos os gêneros do filme.

---

## Classe `PerfilCinefiloTest`

### Bug 01 — Erro de intervalo de tempo
- **Teste:** `deve_CriarPerfil_Quando_DadosValidos`
- **Correção:** Lançamento de `DuracaoInvalidaException` no construtor caso o mínimo seja maior que o máximo.

### Bug 02 — Notas fora da escala permitida
- **Teste:** `deve_LancarExcecao_Quando_NotaInvalida`
- **Correção:** Adicionada trava de segurança no método `adicionarNota` para aceitar apenas valores entre 1 e 5.

### Bug 03 — Pesos de gênero inválidos
- **Teste:** `deve_CriarPerfil_Quando_DadosValidos`
- **Correção:** Validação no construtor para impedir pesos menores que `0.0` ou maiores que `1.0`.

---

## Classe `FilmeTest`

### Bug 01 — Atribuição trocada no construtor
- **Teste:** `deve_CriarFilme_Quando_DadosValidos`
- **Correção:** Correção da lógica de atribuição dos parâmetros para os atributos correspondentes.

### Bug 02 — Vulnerabilidade de estado
- **Teste:** Estrutura da classe
- **Correção:** Definição de todos os atributos como `private final` para garantir que os dados do catálogo sejam imutáveis.