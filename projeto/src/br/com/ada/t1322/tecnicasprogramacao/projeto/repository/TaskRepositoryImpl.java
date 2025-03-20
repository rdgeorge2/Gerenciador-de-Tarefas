package br.com.ada.t1322.tecnicasprogramacao.projeto.repository;

import br.com.ada.t1322.tecnicasprogramacao.projeto.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class TaskRepositoryImpl implements TaskRepository {

    private static final TaskRepositoryImpl INSTANCE = new TaskRepositoryImpl();
    // Singleton: GoF grupo de engenheiros e arquitetos escreveram catalogo: Design Patterns: Boas praticas de desenvolvimento, eficiencia no teu software
    private final List<Task> tasks = new ArrayList<>();
    // Estrutura de dados: Decisões feitas em DS (data structure) impactar profundamente o desempenho da aplicação

    // Pesquisa
    // Ordenação

    //Array: ArrayList uma classe no Java que facilita o uso de array: Array
    // [t1,t2,t3,t4,t5,t6] array de tarefas. Econtre a tarefa com id = 4

    //Tabela Hash: HashMap que facilita o uso de tabela hash
    //{[102, t1], [645, t2], [12, t3], [765, t4] [56, t5]}

    // array.length = N = 100_000_000
    // esforço = N O(n)
    // BigO Notation: DSA: Data Structures and Algorithm (search and order)
    // Divide to conquer: Binary Search

    //ArrayList e HashMap: Qual seria ideal para armazenar em memoria objetos que possuem um identificador unico.

    private static Long idCounter = 1L;

    private TaskRepositoryImpl() {
        // Construtor privado para impedir instanciação externa
    }

    public static TaskRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            // Criando um novo ID para a tarefa
            task.setId(getIncrementalId());
            tasks.add(task);
        } else {
            // Buscando a tarefa existente para atualização. Pare e pense se essa abordagem tem bom desempenho. FindById vai percorrer toda a lista para encontrar match e filtrar. Como poderíamos fazer uma busca mais eficiente? Considere usar outra estrutura de dados para além do array
            Optional<Task> existingTask = findById(task.getId());

            if (existingTask.isPresent()) {
                Task currentTask = existingTask.get();
                currentTask.setTitle(task.getTitle());
                currentTask.setDescription(task.getDescription());
                currentTask.setDeadline(task.getDeadline());
                currentTask.setStatus(task.getStatus());
            } else {
                throw new IllegalArgumentException("Tentativa de salvar uma nova tarefa com um ID inexistente.");
            }
        }
        return task;
    }

    private static Long getIncrementalId() {
        return idCounter++;
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks); // Retorna uma cópia para evitar modificação direta
    }

    @Override
    public List<Task> findByStatus(String status) {
        return tasks.stream()
                .filter(task -> task.getStatus().getDescricao().equalsIgnoreCase(status))
                .toList();
    }

    @Override
    public List<Task> findByStatus(Task.Status status) {
        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    @Override
    public List<Task> findBy(Predicate<Task> predicate) {
        return tasks.stream()
                .filter(predicate)
                .toList();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean deleteById(Long id) {
        return tasks.removeIf(task -> task.getId().equals(id));
    }

    @Override
    public void deleteAll() {
        tasks.clear();
        idCounter = 1L; // Reinicia o contador de IDs
    }
}
