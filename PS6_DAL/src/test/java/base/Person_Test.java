package base;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import domain.PersonDomainModel;

public class Person_Test {

	private static PersonDomainModel per1;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		Date exDate = null;
		try {
			exDate = new SimpleDateFormat("yyyy-MM-dd").parse("1972-07-31");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		per1 = new PersonDomainModel();
		per1.setFirstName("Charlie");
		per1.setLastName("Chocolate");
		per1.setBirthday(exDate);
		per1.setStreet("15 Breeze Hill Road");
		per1.setCity("Wilmington");
		per1.setPostalCode(19807);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		PersonDomainModel per;	
		PersonDAL.deletePerson(per1.getPersonID());
		per = PersonDAL.getPerson(per1.getPersonID());
		assertNull("The Person shouldn't have been in the database",per);		
	}
	
	@Test
	public void AddPersonTest()
	{		
		//Make sure the table is empty
		PersonDomainModel per;		
		per = PersonDAL.getPerson(per1.getPersonID());		
		assertNull("The Person shouldn't have been in the database",per);		
		
		//Test an added person
		PersonDAL.addPerson(per1);	
		per = PersonDAL.getPerson(per1.getPersonID());
		assertNotNull("The Person should have been added to the database",per);
	}
	
	@Test
	public void DeletePersonTest()
	{		
		//Make sure the table is empty
		PersonDomainModel per;		
		per = PersonDAL.getPerson(per1.getPersonID());		
		assertNull("The Person shouldn't have been in the database",per);	
		
		//Add a person and make sure they are in the database
		PersonDAL.addPerson(per1);			
		per = PersonDAL.getPerson(per1.getPersonID());
		System.out.println(per1.getPersonID() + " found");
		assertNotNull("The Person should have been added to the database",per);
		
		//Test the delete method
		PersonDAL.deletePerson(per1.getPersonID());
		per = PersonDAL.getPerson(per1.getPersonID());		
		assertNull("The Person shouldn't have been in the database",per);	
	}
	
	@Test
	public void UpdatePersonTest()
	{		
		//Establish a new last name
		PersonDomainModel per;
		final String C_LASTNAME = "Smith";
		
		//Make sure the Person from other tests was removed
		per = PersonDAL.getPerson(per1.getPersonID());		
		assertNull("The Person shouldn't have been in the database",per);		
		PersonDAL.addPerson(per1);	
		
		//Test the update method
		per1.setLastName(C_LASTNAME);
		PersonDAL.updatePerson(per1);
		
		//Was the database updated?
		per = PersonDAL.getPerson(per1.getPersonID());
		assertTrue("Name Didn't Change", per1.getLastName() == C_LASTNAME);
	}
	
}
