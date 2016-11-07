package cs544.hibernate.extracredit.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Task {

	private Task() {
	}

	public Task(String taskName, Status status, Date startDate, Date endDate) {
		this.taskName = taskName;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Task [taskName=" + taskName + ", status=" + status + ", startDate=" + startDate + ", endDate=" + endDate
				+ "]";
	}

	@Id
	@GeneratedValue
	private long id;

	private String taskName;
	@ManyToOne
	@JoinColumn(name = "Project_Id")
	private Project project;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Task_Resource", joinColumns = @JoinColumn(name = "task_Id"), inverseJoinColumns = @JoinColumn(name = "resource_Id"))
	private List<Resource> resourceList = new ArrayList<Resource>();

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Volunteer> volunteerList = new ArrayList<Volunteer>();

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public List<Volunteer> getVolunteerList() {
		return volunteerList;
	}

	public void addResource(Resource resource) {
		resourceList.add(resource);
		resource.getTaskList().add(this);
	}

	public void removeResource(Resource resource) {
		resourceList.remove(resource);
		resource.getTaskList().add(null);
	}

	public void setVolunteerList(List<Volunteer> volunteerList) {
		this.volunteerList = volunteerList;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Resource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}

}
