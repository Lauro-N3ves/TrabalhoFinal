package Lauro.TrabalhoFinal.Service;

import Lauro.TrabalhoFinal.Model.Status;
import Lauro.TrabalhoFinal.Model.Tarefa;
import Lauro.TrabalhoFinal.Repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public Tarefa criarTarefa(Tarefa tarefa) {
        tarefa.setDataCriacao(LocalDateTime.now());
        tarefa.setStatus(Status.A_FAZER);
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> obterTodasTarefas() {
        return tarefaRepository.findAll();
    }

    public Tarefa atualizarTarefa(Long id, Tarefa detalhesTarefa) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        tarefa.setTitulo(detalhesTarefa.getTitulo());
        tarefa.setDescricao(detalhesTarefa.getDescricao());
        tarefa.setPrioridade(detalhesTarefa.getPrioridade());
        return tarefaRepository.save(tarefa);
    }

    public void excluirTarefa(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        tarefaRepository.delete(tarefa);
    }
}
