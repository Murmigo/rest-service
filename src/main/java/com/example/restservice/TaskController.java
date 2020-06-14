package com.example.restservice;

import java.util.Optional;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
	
	@Autowired
	private TaskRepository taskRepo;
	
	@GetMapping("/task/{id}")
	public Optional<Task> task(@PathVariable(value = "id") Integer taskId) {

		return taskRepo.findById(taskId);

	}

	@PostMapping("/task")
	public String createTask(@RequestBody Task t){
		Task myTask = new Task();
		
		myTask.setDescription(t.getDescription());
		myTask.setPriority(t.getPriority());
		myTask.setCompleted(false);
		taskRepo.save(myTask);
		
		return "Saved";
		
	}
	
	@PutMapping("/task")
	public String updateTask(@RequestBody Task t){	
		
		Task myTask = taskRepo.findById(t.getId()).get();
		if(myTask != null)
		{
			myTask.setDescription(t.getDescription());
			myTask.setPriority(t.getPriority());
			myTask.setCompleted(t.isCompleted());
			taskRepo.save(myTask);
			return "Update";
			
		}else {
		
		return "Unable to Updated, id not found";
		}
	}
	
	@DeleteMapping("/task/{id}")
	public String deleteTask(@PathVariable(value = "id") Integer taskId) {
		taskRepo.deleteById(taskId);
		return "Deleted";
	}
	
	@GetMapping("/task")
	public Iterable<Task> listAllTasks()
	{
		return taskRepo.findAll();
		
	}
}
