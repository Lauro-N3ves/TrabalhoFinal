package Lauro.TrabalhoFinal.Repository;

import Lauro.TrabalhoFinal.Model.Tarefa;
import Lauro.TrabalhoFinal.Model.Status; // Import necessário para usar Status
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByStatusOrderByPrioridadeDesc(Status status);
}
