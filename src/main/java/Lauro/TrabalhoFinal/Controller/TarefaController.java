package Lauro.TrabalhoFinal.Controller;

import Lauro.TrabalhoFinal.Model.Tarefa;
import Lauro.TrabalhoFinal.Service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> excluirTarefa(@PathVariable Long id) {
        tarefaService.excluirTarefa(id);
        return ResponseEntity.noContent().build();
    }
}
//a