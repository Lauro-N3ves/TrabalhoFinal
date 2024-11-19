package Lauro.TrabalhoFinal.Repository;

import Lauro.TrabalhoFinal.Model.Prioridade;
import Lauro.TrabalhoFinal.Model.Tarefa;
import Lauro.TrabalhoFinal.Model.Status; // Import necessário para usar Status
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByStatusOrderByPrioridadeDesc(Status status);

    // Filtrar tarefas por prioridade
    List<Tarefa> findByPrioridade(Prioridade prioridade);

    // Filtrar tarefas que estão atrasadas (data limite passada e status não "CONCLUIDO")
    @Query("SELECT t FROM Tarefa t WHERE t.dataLimite < :dataAtual AND t.status <> 'CONCLUIDO'")
    List<Tarefa> findAtrasadas(LocalDateTime dataAtual);

    // Filtrar tarefas por data limite específica
    List<Tarefa> findByDataLimite(LocalDateTime dataLimite);

}
