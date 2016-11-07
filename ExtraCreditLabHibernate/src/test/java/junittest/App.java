package junittest;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.junit.Test;

import cs544.hibernate.extracredit.domain.Beneficiary;
import cs544.hibernate.extracredit.domain.Project;
import cs544.hibernate.extracredit.domain.Resource;
import cs544.hibernate.extracredit.domain.Status;
import cs544.hibernate.extracredit.domain.Task;
import cs544.hibernate.extracredit.domain.Volunteer;
import junit.framework.TestCase;

public class App extends TestCase {
	private static Logger logger = Logger.getLogger(App.class);;

	private static final EntityManagerFactory emf;

	static {
		try {
			emf = Persistence.createEntityManagerFactory("extracredithibernate");
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	@Test
	public void test() throws ParseException {
		EntityManager em = emf.createEntityManager();
		fillDataBase();
		projectAndBenificiaryInfo("Project1", em);
		listTaskForProject("Project1", em);
		listProjectByStatus(Status.IN_PROGRESS, em);
		listProjectByResouce("R2", em);
		listProjectByLocation("Fairfield", em);
		projectAndBenificiaryInfoFromKeyword("Pro", em);
		listTaskForProjectWithVolunteer("V1", em);
		em.close();
	}

	private void projectAndBenificiaryInfo(String name, EntityManager em) {
		Query query = em.createQuery("SELECT p FROM Project p WHERE p.name = '" + name + "'");
		List<Project> pL = query.getResultList();
		System.out.println("\n\n--------Printing project with beneficiary list-------\n");
		for (Project p : pL) {
			System.out.println(p);
			System.out.println("Beneficiary List:");
			for (Beneficiary b : p.getBeneficiaryList())
				System.out.println(b);
		}
	}

	private void listTaskForProject(String name, EntityManager em) {
		Query query = em.createQuery("SELECT p FROM Project p WHERE p.name = '" + name + "'");
		List<Project> pL = query.getResultList();
		System.out.println("\n\n--------Printing project with task list-------\n");
		for (Project p : pL) {
			System.out.println(p);
			System.out.println("Task List:");
			for (Task t : p.getTaskList())
				System.out.println(t);
		}
	}

	private void listProjectByStatus(Status status, EntityManager em) {
		Query query = em.createQuery("SELECT p FROM Project p WHERE p.status = :status");
		query.setParameter("status", status);
		List<Project> pL = query.getResultList();
		System.out.println("\n\n--------Printing project with status-------\n");
		for (Project p : pL) {
			System.out.println(p);
		}
	}

	private void listProjectByResouce(String name, EntityManager em) {
		Query query = em.createQuery(
				"SELECT p FROM Project p join p.taskList t join t.resourceList r WHERE r.type = '" + name + "'");
		List<Project> pL = query.getResultList();
		System.out.println("\n\n--------Printing project with resource-------\n");
		for (Project p : pL) {
			System.out.println(p);
		}
	}

	private void listProjectByLocation(String location, EntityManager em) {
		Query query = em.createQuery("SELECT p FROM Project p WHERE p.location = '" + location + "'");
		List<Project> pL = query.getResultList();
		System.out.println("\n\n--------Printing project with location-------\n");
		for (Project p : pL) {
			System.out.println(p);
		}
	}

	private void listTaskForProjectWithVolunteer(String name, EntityManager em) {
		Query query = em.createQuery(
				"SELECT DISTINCT p FROM Project p JOIN p.taskList t join t.volunteerList v WHERE v.name = '" + name
						+ "' ORDER BY t.endDate");
		List<Project> pL = query.getResultList();
		System.out.println("\n\n--------Printing project with task list from volunteer name-------\n");
		for (Project p : pL) {
			System.out.println(p);
			System.out.println("Task List:");
			for (Task t : p.getTaskList())
				System.out.println(t);
		}
	}

	private void projectAndBenificiaryInfoFromKeyword(String name, EntityManager em) {
		Query query = em.createQuery("SELECT p FROM Project p WHERE p.name LIKE '%" + name + "%'");
		List<Project> pL = query.getResultList();
		System.out.println("\n\n--------Printing project with beneficiary list-------\n");
		for (Project p : pL) {
			System.out.println(p);
			System.out.println("Beneficiary List:");
			for (Beneficiary b : p.getBeneficiaryList())
				System.out.println(b);
		}
	}

	public static void fillDataBase() throws ParseException {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
			Date dSP = dateFormat.parse("2014-01-23");
			Date dEP = dateFormat.parse("2014-09-23");
			Date dST1 = dateFormat.parse("2014-01-24");
			Date dET1 = dateFormat.parse("2014-05-23");
			Date dST2 = dateFormat.parse("2014-05-24");
			Date dET2 = dateFormat.parse("2014-09-22");
			Resource r1 = new Resource("R1");
			Resource r2 = new Resource("R2");
			Beneficiary b1 = new Beneficiary("B1");
			Beneficiary b2 = new Beneficiary("B2");
			Volunteer v1 = new Volunteer("V1");
			Volunteer v2 = new Volunteer("V2");
			Task t1 = new Task("Task1", Status.COMPLETED, dST1, dET1);
			t1.addResource(r1);
			t1.addResource(r2);
			Task t2 = new Task("Task2", Status.IN_PROGRESS, dST2, dET2);
			t2.addResource(r2);
			v1.addTask(t1);
			v1.addTask(t2);
			v2.addTask(t1);
			Project p1 = new Project("Project1", "Fairfield", "Desc1", dSP, dEP, Status.IN_PROGRESS);
			p1.addBeneficiary(b1);
			p1.addBeneficiary(b2);
			p1.addTask(t1);
			p1.addTask(t2);
			p1.setCover(loadCover("smiths.jpg"));
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			em.persist(p1);

			tx.commit();

		} catch (PersistenceException e) {
			if (tx != null) {
				logger.error("Rolling back", e);
				tx.rollback();
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	private static byte [] loadCover(String filename) {
        Path p = FileSystems.getDefault().getPath(filename);
        byte [] fileData = null;
        try {
			fileData = Files.readAllBytes(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileData;
	}

}
