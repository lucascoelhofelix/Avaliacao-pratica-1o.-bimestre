// --- 1. Interface Strategy: Define a interface comum para todos os algoritmos. ---

/**
 * Interface comum para todos os algoritmos de cálculo de frete (Estratégias).
 * Ela garante que todas as modalidades de transporte tenham um método 'calcular'.
 */
interface EstrategiaCalculoFrete {
    double calcular(double parametro1, double parametro2);
}

// --- 2. Estratégias Concretas: Implementam a lógica específica de cada modalidade. ---

/**
 * Estratégia para Transporte Terrestre.
 * O cálculo é baseado na distância em km (parametro1) * um custo fixo.
 */
class CalculoTerrestre implements EstrategiaCalculoFrete {
    private static final double CUSTO_POR_KM = 0.75; // Exemplo de regra específica

    @Override
    public double calcular(double distanciaKm, double peso) {
        // A lógica do frete terrestre foca apenas na distância (parametro1)
        return distanciaKm * CUSTO_POR_KM;
    }
}

/**
 * Estratégia para Transporte Aéreo.
 * O cálculo é baseado no peso da carga em kg (parametro1) * um custo fixo,
 * além de uma taxa de manuseio (parametro2 - que poderia ser um valor fixo).
 */
class CalculoAereo implements EstrategiaCalculoFrete {
    private static final double CUSTO_POR_KG = 2.50; // Exemplo de regra específica
    private static final double TAXA_AEREA_FIXA = 50.00;

    @Override
    public double calcular(double pesoKg, double volume) {
        // A lógica do frete aéreo foca no peso (parametro1) e adiciona uma taxa fixa
        return (pesoKg * CUSTO_POR_KG) + TAXA_AEREA_FIXA;
    }
}

/**
 * Estratégia para Transporte Marítimo.
 * O cálculo é baseado em uma taxa base mais o volume cúbico (parametro1) * um custo.
 */
class CalculoMaritimo implements EstrategiaCalculoFrete {
    private static final double TAXA_BASE_MARITIMA = 150.00; // Exemplo de regra específica
    private static final double CUSTO_POR_VOLUME_CUBICO = 1.20;

    @Override
    public double calcular(double volumeCubico, double peso) {
        // A lógica do frete marítimo foca no volume (parametro1) e na taxa base
        return TAXA_BASE_MARITIMA + (volumeCubico * CUSTO_POR_VOLUME_CUBICO);
    }
}

// --- 3. Contexto: A classe que usa a Estratégia. ---

/**
 * Classe Contexto (CalculadoraDeFrete).
 * Mantém uma referência para a Estratégia (CalculoFrete) e a utiliza.
 * Ela não precisa saber qual estratégia concreta está sendo usada.
 */
class CalculadoraDeFrete {
    private EstrategiaCalculoFrete estrategia;

    /**
     * Construtor que permite definir a estratégia no momento da criação.
     */
    public CalculadoraDeFrete(EstrategiaCalculoFrete estrategia) {
        this.estrategia = estrategia;
    }

    /**
     * Permite mudar a estratégia em tempo de execução.
     */
    public void setEstrategia(EstrategiaCalculoFrete novaEstrategia) {
        this.estrategia = novaEstrategia;
    }

    /**
     * Método genérico que delega o cálculo para a estratégia atual.
     * Os parâmetros são genéricos (param1, param2) e seu significado
     * (distância, peso, volume) é interpretado pela estratégia concreta.
     */
    public double calcularFrete(double parametro1, double parametro2) {
        // Delega o trabalho de cálculo para o objeto Estratégia.
        return estrategia.calcular(parametro1, parametro2);
    }
}

// --- 4. Exemplo de Uso (Main): Demonstra a flexibilidade. ---

public class SistemaLogistica {
    public static void main(String[] args) {
        // 1. Cria a calculadora (Contexto) com uma estratégia inicial (Terrestre)
        // O cálculo será: Distância (150 km) * 0.75 = 112.50
        CalculadoraDeFrete calculadora = new CalculadoraDeFrete(new CalculoTerrestre());
        double freteTerrestre = calculadora.calcularFrete(150.0, 0.0);
        System.out.printf("Frete Terrestre (150 km): R$ %.2f\n", freteTerrestre); // Saída: 112.50

        // 2. Muda a estratégia em tempo de execução para Aéreo.
        // Novo cálculo: Peso (20 kg) * 2.50 + 50.00 = 100.00
        calculadora.setEstrategia(new CalculoAereo());
        double freteAereo = calculadora.calcularFrete(20.0, 0.0);
        System.out.printf("Frete Aéreo (20 kg): R$ %.2f\n", freteAereo); // Saída: 100.00

        // 3. Muda a estratégia para Marítimo.
        // Novo cálculo: Taxa Base (150.00) + Volume (10 m³) * 1.20 = 162.00
        calculadora.setEstrategia(new CalculoMaritimo());
        double freteMaritimo = calculadora.calcularFrete(10.0, 0.0);
        System.out.printf("Frete Marítimo (10 m³): R$ %.2f\n", freteMaritimo); // Saída: 162.00
    }
}
