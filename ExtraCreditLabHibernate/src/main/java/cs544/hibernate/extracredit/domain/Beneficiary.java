package cs544.hibernate.extracredit.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Beneficiary {
	private Beneficiary() {

	}

	public Beneficiary(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Beneficiary [id=" + id + ", name=" + name + ", project=" + project + "]";
	}

	@Id
	@GeneratedValue
	private long id;

	private String name;

	@ManyToOne
	private Project project;

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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
