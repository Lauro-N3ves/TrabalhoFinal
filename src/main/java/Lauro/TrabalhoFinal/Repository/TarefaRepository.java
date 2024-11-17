package Lauro.TrabalhoFinal.Repository;


import Lauro.TrabalhoFinal.Model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    // Métodos de consulta personalizados podem ser adicionados aqui, se necessário
}