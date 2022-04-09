package questao1.tcp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dados {
    private static Map<Integer, List<Object>> dados = new HashMap<>();

    public static void addDados(Processo processo) {
        List<Object> dadosProcesso = new ArrayList<>();
        dadosProcesso.add(processo.getPorta());

        if (processo.getMensagem() == null)
            dadosProcesso.add(-1);
        else
            dadosProcesso.add(processo.getMensagem());

        dados.put(processo.getId(), dadosProcesso);
    }

    public static void setMensagem(int id, Integer mensagem) {
        dados.get(id).set(1, mensagem);
    }

    public static Integer getMensagem(int id) {
        return (Integer) dados.get(id).get(1);
    }
}
