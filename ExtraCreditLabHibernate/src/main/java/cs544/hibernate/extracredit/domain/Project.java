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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Project {

	private Project() {
	}

	public Project(String name, String location, String description, Date startDate, Date endDate, Status status) {
		this.name = name;
		this.location = location;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", location=" + location + ", description=" + description + ", startDate="
				+ startDate + ", endDate=" + endDate + ", status=" + status + "]";
	}

	@Id
	@GeneratedValue
	private long id;

	private String name;

	private String location;

	private String description;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<Task> taskList = new ArrayList<Task>();

	@Enumerated(EnumType.STRING)
	private Status status;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<Beneficiary> beneficiaryList = new ArrayList<Beneficiary>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void addBeneficiary(Beneficiary beneficiary) {
		beneficiaryList.add(beneficiary);
		beneficiary.setProject(this);
	}

	public void removeBeneficiary(Beneficiary beneficiary) {
		beneficiaryList.remove(beneficiary);
		beneficiary.setProject(null);
	}

	public void addTask(Task task) {
		taskList.add(task);
		task.setProject(this);
	}

	public void removeTask(Task task) {
		taskList.remove(task);
		task.setProject(null);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Beneficiary> getBeneficiaryList() {
		return beneficiaryList;
	}

	public void setBeneficiaryList(List<Beneficiary> beneficiaryList) {
		this.beneficiaryList = beneficiaryList;
	}

}
