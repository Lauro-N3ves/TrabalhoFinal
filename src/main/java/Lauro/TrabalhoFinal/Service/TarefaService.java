package Lauro.TrabalhoFinal.Service;

import Lauro.TrabalhoFinal.Model.Status;
import Lauro.TrabalhoFinal.Model.Tarefa;
import Lauro.TrabalhoFinal.Repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public Tarefa criarTarefa(Tarefa tarefa) {
        tarefa.setDataCriacao(LocalDateTime.now());
        tarefa.setStatus(Status.A_FAZER);
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> obterTodasTarefas() {
        return tarefaRepository.findAll();
    }

    public Tarefa atualizarTarefa(Long id, Tarefa detalhesTarefa) {
        return tarefaRepository.findById(id).map(tarefa -> {
            tarefa.setTitulo(detalhesTarefa.getTitulo());
            tarefa.setDescricao(detalhesTarefa.getDescricao());
            tarefa.setStatus(detalhesTarefa.getStatus());
            tarefa.setPrioridade(detalhesTarefa.getPrioridade());
            tarefa.setDataLimite(detalhesTarefa.getDataLimite());
            return tarefaRepository.save(tarefa);
        }).orElseThrow(() -> new ResourceNotFoundException("ta errado! Não tem tarefa com ID: " + id));
    }


    public void excluirTarefa(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não tem tarefa com ID: " + id));
        tarefaRepository.delete(tarefa);
    }


    public Map<String, List<Tarefa>> obterTarefasPorColuna() {
        List<Tarefa> todasTarefas = tarefaRepository.findAll();

        // Agrupar as tarefas por status
        Map<String, List<Tarefa>> tarefasPorColuna = new HashMap<>();
        tarefasPorColuna.put("A_FAZER", todasTarefas.stream()
                .filter(tarefa -> tarefa.getStatus() == Status.A_FAZER)
                .collect(Collectors.toList()));
        tarefasPorColuna.put("EM_PROGRESSO", todasTarefas.stream()
                .filter(tarefa -> tarefa.getStatus() == Status.EM_PROGRESSO)
                .collect(Collectors.toList()));
        tarefasPorColuna.put("CONCLUIDO", todasTarefas.stream()
                .filter(tarefa -> tarefa.getStatus() == Status.CONCLUIDO)
                .collect(Collectors.toList()));

        return tarefasPorColuna;
    }

    public Tarefa moverTarefa(Long id) {
        // Procurar a tarefa ou lançar exceção caso não exista
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com id: " + id));

        // Verificar e mover para o próximo status
        switch (tarefa.getStatus()) {
            case A_FAZER:
                tarefa.setStatus(Status.EM_PROGRESSO);
                break;
            case EM_PROGRESSO:
                tarefa.setStatus(Status.CONCLUIDO);
                break;
            case CONCLUIDO:
                throw new IllegalStateException("A tarefa já está concluída e não pode ser movida.");
        }

        // Salvar a tarefa atualizada
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTarefasPorStatusOrdenado(Status status) {
        return tarefaRepository.findByStatusOrderByPrioridadeDesc(status);
    }

}
//a