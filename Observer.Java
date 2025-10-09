import java.util.ArrayList;
import java.util.List;

// --- 1. Interface Observador (Observer): Define o contrato para os leitores. ---

/**
 * Interface Observador (Leitor).
 * Todos os objetos que precisam ser notificados devem implementar esta interface.
 */
interface Observador {
    void atualizar(String topico, String noticia);
}

// --- 2. Interface Assunto (Subject): Define o contrato para os tópicos de interesse. ---

/**
 * Interface Assunto (Topico).
 * Define métodos para gerenciar (anexar, desanexar) observadores e para notificar.
 */
interface Assunto {
    void inscrever(Observador observador);
    void desinscrever(Observador observador);
    void notificarObservadores();
}

// --- 3. Assunto Concreto (Concrete Subject): O Tópico de Notícias. ---

/**
 * Classe TopicoNoticias (Assunto Concreto).
 * Mantém o estado (a lista de leitores) e notifica quando o estado muda (nova noticia).
 */
class TopicoNoticias implements Assunto {
    private List<Observador> leitoresInscritos = new ArrayList<>();
    private String nomeTopico;
    private String ultimaNoticia;

    public TopicoNoticias(String nomeTopico) {
        this.nomeTopico = nomeTopico;
        System.out.println("Tópico criado: " + nomeTopico);
    }

    @Override
    public void inscrever(Observador observador) {
        leitoresInscritos.add(observador);
        System.out.println(observador + " inscrito no tópico: " + nomeTopico);
    }

    @Override
    public void desinscrever(Observador observador) {
        leitoresInscritos.remove(observador);
        System.out.println(observador + " desinscrito do tópico: " + nomeTopico);
    }

    @Override
    public void notificarObservadores() {
        System.out.println("\n--- NOTIFICANDO leitores de " + nomeTopico + " ---");
        for (Observador leitor : leitoresInscritos) {
            // Delega a atualização para o método 'atualizar' do observador
            leitor.atualizar(nomeTopico, ultimaNoticia);
        }
    }

    /**
     * Método que simula a publicação de uma nova notícia e dispara a notificação.
     */
    public void publicarNoticia(String novaNoticia) {
        this.ultimaNoticia = novaNoticia;
        System.out.println("\n[SITE] Nova notícia publicada em " + nomeTopico + ": " + ultimaNoticia);
        // Após a mudança de estado, notifica todos os dependentes.
        notificarObservadores();
    }
}

// --- 4. Observadores Concretos (Concrete Observers): Os Leitores. ---

class Leitor implements Observador {
    private String nome;

    public Leitor(String nome) {
        this.nome = nome;
    }

    @Override
    public void atualizar(String topico, String noticia) {
        // Implementação da lógica de como o leitor recebe a notificação
        System.out.println("-> " + nome + " recebeu notificação do tópico " + topico + ": " + noticia);
    }

    @Override
    public String toString() {
        return "Leitor(" + nome + ")";
    }
}

// --- 5. Exemplo de Uso (Cliente) ---

public class SistemaNotificacao {
    public static void main(String[] args) {
        // 1. Cria os Assuntos (Tópicos)
        TopicoNoticias politica = new TopicoNoticias("Política");
        TopicoNoticias esportes = new TopicoNoticias("Esportes");

        // 2. Cria os Observadores (Leitores)
        Leitor leitorA = new Leitor("Ana");
        Leitor leitorB = new Leitor("Bruno");
        Leitor leitorC = new Leitor("Carlos");

        // 3. Inscreve os leitores nos tópicos de interesse
        politica.inscrever(leitorA);
        politica.inscrever(leitorB); // Bruno se inscreve em Política

        esportes.inscrever(leitorB); // Bruno também se inscreve em Esportes
        esportes.inscrever(leitorC);

        // 4. Publica notícia em um tópico (dispara a notificação)
        politica.publicarNoticia("Reforma Tributária Aprovada.");
        // Saída: Notifica Ana e Bruno.

        // 5. Bruno decide não receber mais notícias de Política
        politica.desinscrever(leitorB);

        // 6. Publica nova notícia (dispara novamente)
        esportes.publicarNoticia("Brasil vence a final de Vôlei!");
        // Saída: Notifica Bruno e Carlos (somente os inscritos em Esportes).
    }
}
