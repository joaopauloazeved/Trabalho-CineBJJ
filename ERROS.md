Relatório de erros:
Classe RecomendadorServiceTest
Bug 01: Falha no registro;
deve_RetornarListaVazia_Quando_CatalogoEstaVazio; Movido a chamada de registrarRecomendacao para garantir a execução mesmo sem filmes encontrados.
Bug 02: Ordenação;
deve_OrdenarPorScoreDesc_Quando_Recomendar; Utilizado Mock no GeradorAleatorio para tornar o teste determinístico e evitar falhas.
Bug 03: Notificação;
deve_RetornarListaVazia_Quando_CatalogoEstaVazio; Adicionado o check if (usuario.isNotificacoesAtivas()) antes de chamar o notificador.
Bug 04: Processamento após erro de API; deve_RetornarListaVazia_Quando_CatalogoLancaExcecao; Adicionado return imediato no bloco catch para abortar o fluxo após a exceção.
Bug 05: Erro de índice no sorteio;
deve_RetornarFilmeAleatorio_Quando_SurpreendaMe; Adicionamos -1 no tamanho para evitar index out of bound

Classe CalculadoraScoreTest
Bug 01: Quebra de limite; deve_NuncaPassarDe100; Aplicado Math.min(100, ...) no resultado dos calculos.
Bug 02: Erro de  duração; 
deve_TerScoreMaior_Quando_DuracaoEstaDentroDoIntervalo; implementada pontuação dinamica conforme duração;
Bug 03: Erro de divisão por zero em perfis desatualizados/ novos; Adicionada verificação if (perfil.getNotas().isEmpty()) no cálculo de afinidade.
Bug 04: Peso de gênero desproporcional; deve_TerScoreBaixo_Quando_GeneroNaoPreferido; Alterada a soma simples pela média aritmética dos pesos de todos os gêneros do filme.

Classe PerfilCinefiloTest
Bug 01: Erro de intervalo de tempo; deve_CriarPerfil_Quando_DadosValidos; Lançamento de DuracaoInvalidaException no construtor caso o mínimo seja maior que o máximo.
Bug 02: Notas fora da escala permitida; deve_LancarExcecao_Quando_NotaInvalida; Adicionada trava de segurança no método adicionarNota para aceitar apenas valores entre 1 e 5.
Bug 03: Pesos de gênero inválidos; deve_CriarPerfil_Quando_DadosValidos; Validação no construtor para impedir pesos menores que 0.0 ou maiores que 1.0.
Classe FilmeTest
Bug 02: Atribuição trocada no construtor; deve_CriarFilme_Quando_DadosValidos; Correção da lógica de atribuição dos parâmetros para os atributos correspondentes.
Bug 03: Vulnerabilidade de estado; Estrutura da classe; Definição de todos os atributos como private final para garantir que os dados do catálogo sejam imutáveis.
