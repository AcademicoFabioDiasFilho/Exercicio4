import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

final class Start {
    private Start() {

    }

    public static void main(final String[] args) {
        // Começa o programa
        comecar();
    }

    private static void comecar() {
        /*
         Declaração das variáveis locais.
         numeroDeMercadorias recebe zero, e servirá como contador de número de mercadorias.

         valorTotal é instanciado com uma nova instância do tipo BigDecimal (BigDecimal é mais preciso e seguro do que
         double).
        */
        BigDecimal valorTotal = new BigDecimal("0.0").setScale(2, RoundingMode.HALF_EVEN);
        int numeroDeMercadorias = 0;
        boolean inserindoMercadorias = true;

        do {
            /*
             Salvando o que as duas strings desse código tem em comum
             (para não repetir o mesmo código)
             \u00AA é o código do indicador ordinal
             i + 1 porque i começa em zero
            */
            final String ordinal = String.format(" da %d\u00AA mercadoria", numeroDeMercadorias + 1);

            // Entrada do valor da mercadoria do usuário
            final String valorStr = JOptionPane.showInputDialog(null, String.format("Qual é o valor%s (Exemplo de resposta: 5.99 ou 6)?", ordinal), null, JOptionPane.QUESTION_MESSAGE);

            // Se o usuário selecionou a opção cancelar, o programa é finalizado.
            if (valorStr == null) {
                return;
            }

            /*
             Tenta criar do valor em String (texto) para uma nova instância do tipo BigDecimal e guarda na variável
             valor.
             Se o usuário errar a entrada, o programa o alertará e a entrada de dados da vez será reiniciada.
            */
            final BigDecimal valor;

            try {
                valor = new BigDecimal(valorStr);
            } catch (final NumberFormatException e) {
                JOptionPane.showMessageDialog(null, String.format("\"%s\" não é um número reconhecido.", valorStr));
                continue;
            }

            valorTotal = valorTotal.add(valor);
            numeroDeMercadorias++;

            do {
                final String resposta = JOptionPane.showInputDialog(null, "MAIS MERCADORIAS (S/N)?", null, JOptionPane.QUESTION_MESSAGE);

                // Caso o usuário selecione a opção cancelar, o programa para de inserir mercadorias.
                if (resposta == null) {
                    inserindoMercadorias = false;
                    break;
                }

                final String respostaLowerCase = resposta.toLowerCase(Locale.ROOT);

                // Caso o usuário selecione digite 'n', o programa para de inserir mercadorias, caso o usuário digite
                // 's', o programa continua inserindo mercadorias, caso contrário a pergunta é repetida.
                if (respostaLowerCase.equals("n")) {
                    inserindoMercadorias = false;
                    break;
                } else if (respostaLowerCase.equals("s")) {
                    break;
                }
            } while (true);
        } while (inserindoMercadorias);

        /*
         Tenta calcular a média de valor das mercadorias, imprime o valor total em estoque e a média de valor das
         mercadorias, caso a divisão entre o valor total e o número de mercadoras falhe, a média de valor das
         mercadoras é indefinida.
        */
        try {
            final BigDecimal mediaDeValor = valorTotal.divide(new BigDecimal(numeroDeMercadorias), 2, RoundingMode.HALF_EVEN);
            imprimirMensagem(String.format("Valor total em estoque: R$%.2f\nMédia de valor das mercadorias: R$%.2f", valorTotal.doubleValue(), mediaDeValor.doubleValue()));
        } catch (final ArithmeticException e) {
            imprimirMensagem(String.format("Valor total em estoque: R$%.2f\nMédia de valor das mercadorias: Indefinida", valorTotal.doubleValue()));
        }
    }

    /*
     Imprime uma mensagem de confirmação na tela, caso o usuário cancele,
     o programa será re-executado, caso contrário o programa será finalizado.
    */
    private static void imprimirMensagem(final String mensagem) {
        final int escolha = JOptionPane.showConfirmDialog(null, mensagem, null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null);

        if (escolha == JOptionPane.CANCEL_OPTION) {
            comecar();
        }
    }
}
