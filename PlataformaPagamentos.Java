// --- 1. Interface Produto: Define a interface comum para todos os objetos a serem criados. ---

/**
 * Interface ProcessadorPagamento (Produto).
 * Define o contrato comum para todos os tipos de pagamento (Cartão, Boleto, PIX).
 */
interface ProcessadorPagamento {
    void processarPagamento(double valor);
}

// --- 2. Classes Produto Concreto: Implementam as lógicas de pagamento. ---

class ProcessadorCartaoCredito implements ProcessadorPagamento {
    @Override
    public void processarPagamento(double valor) {
        System.out.printf("[Cartão] Pagamento de R$ %.2f aprovado. Cobrança enviada à operadora.\n", valor);
    }
}

class ProcessadorBoleto implements ProcessadorPagamento {
    @Override
    public void processarPagamento(double valor) {
        System.out.printf("[Boleto] Pagamento de R$ %.2f gerado. Vencimento em 3 dias úteis.\n", valor);
    }
}

class ProcessadorPIX implements ProcessadorPagamento {
    @Override
    public void processarPagamento(double valor) {
        System.out.printf("[PIX] Pagamento de R$ %.2f realizado. Chave: 123456. Confirmação instantânea.\n", valor);
    }
}

// --- 3. Classe Criadora (Creator): Define o Factory Method. ---

/**
 * Classe Abstrata FabricaProcessador (Creator).
 * Declara o método de fábrica, que deve retornar um objeto ProcessadorPagamento.
 * Note que a lógica de criação é abstrata e será implementada pelas subclasses.
 */
abstract class FabricaProcessador {
    /**
     * O Factory Method (Método de Fábrica).
     * Ele é o responsável por "fabricar" o produto (ProcessadorPagamento).
     * Subclasses implementarão este método para criar um produto concreto específico.
     */
    public abstract ProcessadorPagamento criarProcessador();

    /**
     * Lógica de Negócio (ex: iniciar o pagamento).
     * Esta lógica opera com a interface ProcessadorPagamento, sem se importar
     * com a classe concreta real. Isto é o que garante o desacoplamento.
     */
    public void iniciarTransacao(double valor) {
        ProcessadorPagamento processador = criarProcessador();
        System.out.println("--- Iniciando transação ---");
        processador.processarPagamento(valor);
        System.out.println("---------------------------\n");
    }
}

// --- 4. Classes Criadoras Concretas (Concrete Creators): Implementam o Factory Method. ---

class FabricaCartaoCredito extends FabricaProcessador {
    @Override
    public ProcessadorPagamento criarProcessador() {
        // Implementa o Factory Method para criar o Processador Cartão
        return new ProcessadorCartaoCredito();
    }
}

class FabricaBoleto extends FabricaProcessador {
    @Override
    public ProcessadorPagamento criarProcessador() {
        // Implementa o Factory Method para criar o Processador Boleto
        return new ProcessadorBoleto();
    }
}

class FabricaPIX extends FabricaProcessador {
    @Override
    public ProcessadorPagamento criarProcessador() {
        // Implementa o Factory Method para criar o Processador PIX
        return new ProcessadorPIX();
    }
}

// --- 5. Exemplo de Uso (Cliente): Onde a flexibilidade é demonstrada. ---

public class PlataformaPagamentos {
    public static void main(String[] args) {
        // O cliente usa apenas o FabricaProcessador abstrato (o Contrato).
        // A lógica de instanciar a fábrica correta pode vir de uma configuração externa.

        // 1. Cliente escolhe pagar com Cartão de Crédito
        FabricaProcessador fabricaCartao = new FabricaCartaoCredito();
        System.out.println("Usuário escolheu Cartão de Crédito:");
        // O método iniciarTransacao chama internamente o criarProcessador() da subclasse FabricaCartaoCredito.
        fabricaCartao.iniciarTransacao(450.75);

        // 2. Cliente escolhe pagar com PIX
        FabricaProcessador fabricaPIX = new FabricaPIX();
        System.out.println("Usuário escolheu PIX:");
        fabricaPIX.iniciarTransacao(99.99);

        // 3. Cliente escolhe pagar com Boleto
        FabricaProcessador fabricaBoleto = new FabricaBoleto();
        System.out.println("Usuário escolheu Boleto Bancário:");
        fabricaBoleto.iniciarTransacao(1200.00);

        // Demonstração da OCP: Se um novo pagamento (Ex: Cripto) for adicionado,
        // apenas uma nova FabricaCripto e ProcessadorCripto seriam criados,
        // sem alterar este código main nem as fábricas/processadores existentes.
    }
}
