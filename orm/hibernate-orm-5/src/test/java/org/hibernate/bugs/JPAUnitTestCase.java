package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.bugs.dao.ActionDao;
import org.hibernate.bugs.entity.Action;
import org.hibernate.bugs.entity.Lead;
import org.hibernate.bugs.entity.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	private ActionDao actionDao;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
		actionDao = new ActionDao();
		populateSampleTableData(entityManagerFactory.createEntityManager());
	}

	public void populateSampleTableData(EntityManager entityManager) {
		entityManager.getTransaction().begin();

		Lead leadA = new Lead();
		leadA.setId(1l);
		leadA.setLocationName("Lead A");
		leadA.setContact("Contact A");
		leadA.setActive(true);
		entityManager.merge(leadA);

		Lead leadB = new Lead();
		leadB.setId(2l);
		leadB.setLocationName("Lead B");
		leadB.setContact("Contact B");
		leadB.setActive(false);
		entityManager.merge(leadB);

		Location locationA = new Location();
		locationA.setId(1l);
		locationA.setAccountNumber("ABC");
		locationA.setActive(true);
		entityManager.merge(locationA);

		Location locationB = new Location();
		locationB.setId(2l);
		locationB.setAccountNumber("XYZ");
		locationB.setActive(false);
		entityManager.merge(locationB);

		Action actionA = new Action();
		actionA.setActionsDetails("Action A");
		actionA.setLocation(locationA);
		entityManager.merge(actionA);

		Action actionB = new Action();
		actionB.setActionsDetails("Action B");
		actionB.setLocation(locationA);
		entityManager.merge(actionB);

		Action actionC = new Action();
		actionC.setActionsDetails("Action C");
		actionC.setLead(leadB);
		entityManager.merge(actionC);

		Action actionD = new Action();
		actionD.setActionsDetails("Action D");
		actionD.setLocation(locationB);
		entityManager.merge(actionD);

		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh123Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		/**
		 * Generated SQL Statement
		 *     select
		 *         action0_.id as id1_0_,
		 *         action0_.action_details as action_d2_0_,
		 *         action0_.lead_id as lead_id3_0_,
		 *         action0_.location as location4_0_
		 *     from
		 *         actions action0_
		 *     left outer join
		 *         location location1_
		 *             on action0_.location=location1_.id
		 *     left outer join
		 *         leads lead2_
		 *             on action0_.lead_id=lead2_.id
		 *     where
		 *         location1_.Active=true
		 *         or lead2_.Active=true
		 */
		List<Action> actionList = actionDao.getActionList(entityManager);

		Assert.assertEquals(2, actionList.size());
	}
}
