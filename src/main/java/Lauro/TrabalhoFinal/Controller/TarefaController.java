package Lauro.TrabalhoFinal.Controller;

import Lauro.TrabalhoFinal.Model.Prioridade;
import Lauro.TrabalhoFinal.Model.Tarefa;
import Lauro.TrabalhoFinal.Model.Status;
import Lauro.TrabalhoFinal.Service.ResourceNotFoundException;
import Lauro.TrabalhoFinal.Service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;


@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaService.criarTarefa(tarefa);
    }

    @GetMapping
    public List<Tarefa> obterTodasTarefas() {
        return tarefaService.obterTodasTarefas();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        Tarefa tarefa = tarefaService.atualizarTarefa(id, tarefaAtualizada);
        if (tarefa != null) {
            return ResponseEntity.ok(tarefa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirTarefa(@PathVariable Long id) {
        try {
            tarefaService.excluirTarefa(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    // Adicionar o endpoint para listar todas as tarefas organizadas por coluna
    @GetMapping("/colunas")
    public ResponseEntity<Map<String, List<Tarefa>>> listarTarefasPorColuna() {
        Map<String, List<Tarefa>> tarefasPorColuna = tarefaService.obterTarefasPorColuna();
        return ResponseEntity.ok(tarefasPorColuna);
    }

    @PutMapping("/{id}/mover")
    public ResponseEntity<?> moverTarefa(@PathVariable Long id) {
        try {
            Tarefa tarefa = tarefaService.moverTarefa(id);
            return ResponseEntity.ok(tarefa);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/colunas/{status}")
    public ResponseEntity<List<Tarefa>> listarTarefasPorStatus(@PathVariable Status status) {
        List<Tarefa> tarefas = tarefaService.listarTarefasPorStatusOrdenado(status);
        return ResponseEntity.ok(tarefas);
    }

    // Endpoint para listar todas as tarefas por prioridade
    @GetMapping("/prioridade/{prioridade}")
    public ResponseEntity<List<Tarefa>> listarTarefasPorPrioridade(@PathVariable String prioridade) {
        try {
            List<Tarefa> tarefas = tarefaService.listarTarefasPorPrioridade(Prioridade.valueOf(prioridade.toUpperCase()));
            return ResponseEntity.ok(tarefas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Endpoint para listar todas as tarefas atrasadas
    @GetMapping("/atrasadas")
    public ResponseEntity<List<Tarefa>> listarTarefasAtrasadas() {
        List<Tarefa> tarefasAtrasadas = tarefaService.listarTarefasAtrasadas();
        return ResponseEntity.ok(tarefasAtrasadas);
    }

    // Endpoint para listar tarefas por data limite
    @GetMapping("/data-limite/{data}")
    public ResponseEntity<List<Tarefa>> listarTarefasPorDataLimite(@PathVariable String data) {
        try {
            LocalDateTime dataLimite = LocalDateTime.parse(data);
            List<Tarefa> tarefas = tarefaService.listarTarefasPorDataLimite(dataLimite);
            return ResponseEntity.ok(tarefas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
//a