package cs544.hibernate.extracredit.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Volunteer {
	private Volunteer() {
	}

	public Volunteer(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue
	private long id;

	private String name;
	
	public void addTask(Task task) {
		taskList.add(task);
		task.getVolunteerList().add(this);
	}
	
	public void removeTask(Task task) {
		taskList.remove(task);
		task.getVolunteerList().remove(this);
	}

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Task> taskList = new ArrayList<Task>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

}
