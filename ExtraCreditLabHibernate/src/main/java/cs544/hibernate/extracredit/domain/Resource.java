package cs544.hibernate.extracredit.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Resource {
	private Resource() {}
	
	public Resource(String type) {
		this.type = type;
	}

	@Id
	@GeneratedValue
	private long id;

	private String type;

	@ManyToMany(mappedBy = "resourceList")
	private List<Task> taskList = new ArrayList<Task>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ManyToMany
	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

}
