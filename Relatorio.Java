// --- 1. Interface Componente: Define o contrato para o objeto a ser decorado. ---

/**
 * Interface Relatorio (Componente).
 * Define a operação que tanto o componente básico quanto os decoradores devem implementar.
 */
interface Relatorio {
    String gerarConteudo();
}

// --- 2. Componente Concreto: A funcionalidade base. ---

/**
 * RelatorioBase (Componente Concreto).
 * Contém a lógica fundamental: gerar a lista básica de pedidos.
 */
class RelatorioBase implements Relatorio {
    @Override
    public String gerarConteudo() {
        // Simulação do conteúdo básico do relatório
        return "\n--- RELATÓRIO DE VENDAS BÁSICO ---\n" +
               "Pedidos: [Pedido #1001, Pedido #1002, ...]";
    }
}

// --- 3. Decorador Abstrato: Mantém a referência ao componente original. ---

/**
 * RelatorioDecorator (Decorador Abstrato).
 * Implementa a interface Relatorio e mantém uma referência ao objeto Relatorio (Componente ou outro Decorador).
 * É a chave para a composição em cadeia.
 */
abstract class RelatorioDecorator implements Relatorio {
    // Referência ao componente que será decorado (o relatório base ou outro decorador)
    protected Relatorio relatorioDecorado;

    public RelatorioDecorator(Relatorio relatorio) {
        this.relatorioDecorado = relatorio;
    }

    // Delega a chamada para o objeto envolvido.
    // As subclasses (decoradores concretos) sobrescreverão este método para adicionar comportamento antes ou depois.
    @Override
    public String gerarConteudo() {
        return relatorioDecorado.gerarConteudo();
    }
}

// --- 4. Decoradores Concretos: Adicionam as novas responsabilidades. ---

class EstatisticasDecorator extends RelatorioDecorator {
    public EstatisticasDecorator(Relatorio relatorio) {
        super(relatorio);
    }

    private String adicionarEstatisticas() {
        // Lógica de adicionar estatísticas
        return "\n[FUNCIONALIDADE ADICIONAL: Estatísticas de Faturamento]" +
               "\nTotal de Vendas: R$ 5.450,00 | Média por Pedido: R$ 120,00";
    }

    @Override
    public String gerarConteudo() {
        // 1. Chama a funcionalidade do objeto envolvido (RelatorioBase ou Decorador anterior)
        String conteudoBase = super.gerarConteudo();
        // 2. Adiciona o novo comportamento
        return conteudoBase + adicionarEstatisticas();
    }
}

class GraficoDecorator extends RelatorioDecorator {
    public GraficoDecorator(Relatorio relatorio) {
        super(relatorio);
    }

    private String adicionarGrafico() {
        // Lógica de simular a geração de um gráfico
        return "\n[FUNCIONALIDADE ADICIONAL: Gráfico de Vendas]" +
               "\nGráfico: [Vendas mensais em barras]";
    }

    @Override
    public String gerarConteudo() {
        // 1. Chama a funcionalidade do objeto envolvido
        String conteudoBase = super.gerarConteudo();
        // 2. Adiciona o novo comportamento
        return conteudoBase + adicionarGrafico();
    }
}

class ExportacaoPDFDecorator extends RelatorioDecorator {
    public ExportacaoPDFDecorator(Relatorio relatorio) {
        super(relatorio);
    }

    private String formatarParaPDF() {
        // Lógica de simular a formatação para exportação
        return "\n[FUNCIONALIDADE ADICIONAL: Exportação para PDF]" +
               "\n-- Início da formatação PDF --";
    }

    @Override
    public String gerarConteudo() {
        // Adicionando a funcionalidade ANTES e DEPOIS do conteúdo principal
        String pdfFormatacao = formatarParaPDF();
        String conteudoBase = super.gerarConteudo();
        return pdfFormatacao + conteudoBase + "\n-- Fim da formatação PDF --";
    }
}

// --- 5. Exemplo de Uso (Cliente): Demonstração da Composição ---

public class SistemaRelatorios {
    public static void main(String[] args) {
        // 1. O Componente Básico (Relatório de Pedidos simples)
        Relatorio relatorioBasico = new RelatorioBase();
        System.out.println("--- Geração do Relatório Simples ---");
        System.out.println(relatorioBasico.gerarConteudo());

        System.out.println("\n===================================");

        // 2. Relatório com Múltiplas Decorações (Composição em cadeia)
        // O RelatorioBase é envolvido por EstatisticasDecorator, que é envolvido por GraficoDecorator,
        // que é envolvido por ExportacaoPDFDecorator.
        Relatorio relatorioCompleto = new ExportacaoPDFDecorator(
                                         new GraficoDecorator(
                                             new EstatisticasDecorator(
                                                 new RelatorioBase()
                                             )
                                         )
                                     );

        System.out.println("--- Geração do Relatório Completo (Estatísticas + Gráfico + PDF) ---");
        System.out.println(relatorioCompleto.gerarConteudo());
    }
}
