package app;

import java.util.*;

import enums.*;
import model.*;
import service.*;
import util.GeradorAleatorio;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        CatalogoFilmesAPI catalogo = criarCatalogo();

        HistoricoUsuarioRepository historico = (usuario, recomendacoes) ->
            System.out.println("\n[Histórico] Recomendações registradas para " + usuario.getNome());

        NotificadorPush notificador = (usuario, mensagem) ->
            System.out.println("[Push] " + mensagem);

        GeradorAleatorio gerador = (min, max) -> min;

        RecomendadorService service = new RecomendadorService(
            catalogo,
            historico,
            notificador,
            gerador,
            new CalculadoraScore(),
            new FiltroFilmes(),
            new FiltroCapsulaTempo()
        );

        System.out.println("===== CINE MATCH =====");
        System.out.println("Antes de começar, vamos criar seu perfil.");

        Usuario usuario = criarUsuario(sc);

        int opcao;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Ver recomendações");
            System.out.println("2 - Surpreenda-me");
            System.out.println("3 - Cápsula do Tempo por ano");
            System.out.println("4 - Cápsula do Tempo por década");
            System.out.println("5 - Marcar filme como assistido");
            System.out.println("6 - Avaliar filme");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    mostrarRecomendacoes(service.recomendar(usuario, 5));
                    break;

                case 2:
                    Recomendacao surpresa = service.recomendarAleatorio(usuario);
                    if (surpresa == null) {
                        System.out.println("Nenhuma recomendação encontrada.");
                    } else {
                        mostrarRecomendacao(surpresa);
                    }
                    break;

                case 3:
                    System.out.print("Digite o ano: ");
                    int ano = sc.nextInt();
                    mostrarRecomendacoes(service.recomendarPorAno(usuario, ano, 5));
                    break;

                case 4:
                    System.out.print("Digite a década inicial, ex: 1990: ");
                    int decada = sc.nextInt();
                    mostrarRecomendacoes(service.recomendarPorDecada(usuario, decada, 5));
                    break;

                case 5:
                    System.out.println("\nFilmes disponíveis:");
                    mostrarCatalogo(catalogo.buscarTodos());

                    System.out.print("Digite o ID do filme assistido: ");
                    String idAssistido = sc.next();

                    usuario.getPerfil().marcarAssistido(idAssistido);
                    System.out.println("Filme marcado como assistido.");
                    break;

                case 6:
                    System.out.println("\nFilmes disponíveis:");
                    mostrarCatalogo(catalogo.buscarTodos());

                    System.out.print("Digite o ID do filme: ");
                    String idNota = sc.next();

                    System.out.print("Digite a nota de 1 a 5: ");
                    int nota = sc.nextInt();

                    usuario.getPerfil().adicionarNota(idNota, nota);
                    System.out.println("Nota registrada.");
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        sc.close();
    }

    private static Usuario criarUsuario(Scanner sc) {
        sc.nextLine();

        System.out.print("\nDigite seu nome: ");
        String nome = sc.nextLine();

        System.out.print("Digite sua idade: ");
        int idade = sc.nextInt();

        Map<Genero, Double> pesos = new HashMap<>();

        pesos.put(Genero.ACAO, 0.0);
        pesos.put(Genero.FICCAO_CIENTIFICA, 0.0);
        pesos.put(Genero.DRAMA, 0.0);
        pesos.put(Genero.COMEDIA, 0.0);
        pesos.put(Genero.TERROR, 0.0);

        System.out.println("\nQual gênero você mais gosta?");
        Genero[] generosDisponiveis = Genero.values();

        System.out.println("\nGêneros disponíveis:");

        for (int i = 0; i < generosDisponiveis.length; i++) {
            System.out.println((i + 1) + " - " + generosDisponiveis[i]);
        }

        System.out.println("\nDigite os números dos gêneros que você gosta separados por espaço.");
        System.out.println("Exemplo: 1 2");

        sc.nextLine();
        String entradaGeneros = sc.nextLine();

        String[] opcoesGenero = entradaGeneros.split(" ");

        Map<Genero, Double> pesos1 = new HashMap<>();

        for (Genero genero : generosDisponiveis) {
            pesos1.put(genero, 0.0);
        }

        for (String opcao : opcoesGenero) {

            int indice = Integer.parseInt(opcao) - 1;

            if (indice >= 0 && indice < generosDisponiveis.length) {

                Genero generoSelecionado =
                    generosDisponiveis[indice];

                pesos1.put(generoSelecionado, 1.0);
            }
        }

        int genero = sc.nextInt();

        if (genero == 1) pesos1.put(Genero.ACAO, 1.0);
        if (genero == 2) pesos1.put(Genero.FICCAO_CIENTIFICA, 1.0);
        if (genero == 3) pesos1.put(Genero.DRAMA, 1.0);
        if (genero == 4) pesos1.put(Genero.COMEDIA, 1.0);

        System.out.print("\nDuração mínima preferida: ");
        int duracaoMin = sc.nextInt();

        System.out.print("Duração máxima preferida: ");
        int duracaoMax = sc.nextInt();

        System.out.println("\nClassificação máxima aceita:");
        System.out.println("1 - Livre");
        System.out.println("2 - 10 anos");
        System.out.println("3 - 12 anos");
        System.out.println("4 - 14 anos");
        System.out.println("5 - 16 anos");
        System.out.println("6 - 18 anos");
        System.out.print("Escolha: ");

        int opcaoClassificacao = sc.nextInt();
        ClassificacaoEtaria classificacaoMaxima = escolherClassificacao(opcaoClassificacao);

        Idioma[] idiomasDisponiveis = Idioma.values();

        System.out.println("\nIdiomas disponíveis:");

        for (int i = 0; i < idiomasDisponiveis.length; i++) {
            System.out.println((i + 1) + " - " + idiomasDisponiveis[i]);
        }

        System.out.println("\nDigite os números dos idiomas separados por espaço.");
        System.out.println("Exemplo: 1 2");

        sc.nextLine();
        String entradaIdiomas = sc.nextLine();

        String[] opcoes = entradaIdiomas.split(" ");

        Set<Idioma> idiomas1 = new HashSet<>();

        for (String opcao : opcoes) {
            int indice = Integer.parseInt(opcao) - 1;

            if (indice >= 0 && indice < idiomasDisponiveis.length) {
                idiomas1.add(idiomasDisponiveis[indice]);
            }
        }
        PerfilCinefilo perfil = new PerfilCinefilo(
            pesos1,
            duracaoMin,
            duracaoMax,
            classificacaoMaxima,
            idiomas1
        );
        System.out.println("\nAntes de começar, marque os filmes que você já assistiu.");

        List<Filme> filmes = criarCatalogo().buscarTodos();
        mostrarCatalogo(filmes);

        System.out.println("\nDigite os IDs dos filmes assistidos separados por espaço.");
        System.out.println("Exemplo: F1 F3 F5");
        System.out.println("Se não assistiu nenhum, digite 0:");
        System.out.print("Resposta: ");

        sc.nextLine();
        String resposta = sc.nextLine();

        if (!resposta.equals("0")) {
            String[] ids = resposta.split(" ");

            for (String id : ids) {
                perfil.marcarAssistido(id);
            }

            System.out.println("Filmes assistidos registrados.");
        }
        System.out.println("\nDeseja ativar notificações?");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");
        System.out.print("Escolha: ");

        int notificacao = sc.nextInt();
        boolean notificacoesAtivas = notificacao == 1;

        return new Usuario(nome, idade, perfil, notificacoesAtivas);
    }

    private static ClassificacaoEtaria escolherClassificacao(int opcao) {
        switch (opcao) {
            case 1:
                return ClassificacaoEtaria.LIVRE;
            case 2:
                return ClassificacaoEtaria.DEZ;
            case 3:
                return ClassificacaoEtaria.DOZE;
            case 4:
                return ClassificacaoEtaria.QUATORZE;
            case 5:
                return ClassificacaoEtaria.DEZESSEIS;
            case 6:
                return ClassificacaoEtaria.DEZOITO;
            default:
                return ClassificacaoEtaria.DEZESSEIS;
        }
    }

    private static CatalogoFilmesAPI criarCatalogo() {
        return () -> List.of(
            new Filme("F1", "A Chegada", 116,
                List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA),
                ClassificacaoEtaria.DOZE,
                Idioma.INGLES,
                84,
                true,
                2016,
                8.0),

            new Filme("F2", "Duna: Parte Dois", 166,
                List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA),
                ClassificacaoEtaria.QUATORZE,
                Idioma.INGLES,
                92,
                true,
                2024,
                8.7),

            new Filme("F3", "Click", 107,
                List.of(Genero.COMEDIA, Genero.DRAMA),
                ClassificacaoEtaria.DOZE,
                Idioma.INGLES,
                65,
                false,
                2006,
                6.5),

            new Filme("F4", "O Iluminado", 146,
                List.of(Genero.TERROR),
                ClassificacaoEtaria.DEZOITO,
                Idioma.INGLES,
                88,
                false,
                1980,
                8.4),

            new Filme("F5", "Matrix", 136,
                List.of(Genero.FICCAO_CIENTIFICA, Genero.ACAO),
                ClassificacaoEtaria.QUATORZE,
                Idioma.INGLES,
                95,
                true,
                1999,
                8.7),

            new Filme("F6", "Tropa de Elite", 115,
                List.of(Genero.ACAO, Genero.DRAMA),
                ClassificacaoEtaria.DEZOITO,
                Idioma.PORTUGUES,
                80,
                true,
                2007,
                8.0),

            new Filme("F7", "O Auto da Compadecida", 104,
                List.of(Genero.COMEDIA, Genero.DRAMA),
                ClassificacaoEtaria.DOZE,
                Idioma.PORTUGUES,
                90,
                true,
                2000,
                8.6)
        );
    }

    private static void mostrarCatalogo(List<Filme> filmes) {
        for (Filme filme : filmes) {
            System.out.println(
                filme.getId() + " - " +
                filme.getTitulo() +
                " (" + filme.getAnoLancamento() + ")"
            );
        }
    }

    private static void mostrarRecomendacoes(List<Recomendacao> recomendacoes) {
        if (recomendacoes.isEmpty()) {
            System.out.println("\nNenhuma recomendação encontrada.");
            return;
        }

        System.out.println("\nResultado:");

        for (Recomendacao r : recomendacoes) {
            mostrarRecomendacao(r);
        }
    }

    private static void mostrarRecomendacao(Recomendacao r) {
        Filme filme = r.getFilme();

        System.out.println("\nRecomendamos: " + filme.getTitulo());
        System.out.println("Ano: " + filme.getAnoLancamento());
        System.out.println("Avaliação: " + filme.getAvaliacao());
        System.out.println("Popularidade: " + filme.getPopularidade());
        System.out.println("Score: " + r.getScore());
        System.out.println("Justificativa: " + r.getJustificativa());
    }
}