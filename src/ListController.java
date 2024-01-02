
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import myfileio.MyFileIO;

/**
 * The Class ListController.
 */
public class ListController {

	/** The employees. */
	private ArrayList<Employee> employees;

	/** The fio. */
	private MyFileIO fio;

	/** Indexes to avoid magic numbers */
	private static int FNAME = 1, LNAME = 2, SSN_INDEX = 3, AGE_INDEX = 4, PRONOUNS_INDEX = 5, SALARY_INDEX = 6,
			YEARS_INDEX = 7, DEPT_INDEX = 8;

	/** Min wage */
	private static double MIN_WAGE = 15.00 * 2080;

	/**
	 * Instantiates a new list controller.
	 */
	public ListController () {
		resetStaticID();
		employees = new ArrayList<Employee>();
		fio = new MyFileIO();
		initializeEmpData();
	}

	/**
	 * Adds the employee.
	 *
	 * @param fName the f name
	 * @param lName the l name
	 * @param SSN the ssn
	 * @param age the age
	 * @param pronouns the pronouns
	 * @param salary the salary
	 * @param years the years
	 * @param dept the dept
	 * @return the string
	 */
	public String addEmployee(String fName, String lName, String SSN, String age,
			String pronouns, String salary, String years, String dept) {
		if(!fName.matches("^[A-Za-z]+")) return "First name not inputted in correct format: " + fName;
		if(!lName.matches("^[A-Za-z]+")) return "Last name not inputted in correct format: " + lName;
		if(!age.matches("^[\\d]+")) return "Age incorrectly formatted: " + age;
		if(Integer.parseInt(age) < 16) return "Too young to work in industry: " + age;
		if(Integer.parseInt(age) > 100) System.out.println("There many be an error in the age"); 
		if(!SSN.matches("[\\d]{3}-[\\d]{2}-[\\d]{4}")) return "SSN not inputted in correct format: " + SSN;
		for(Employee employee: employees)
			if(employee.getSSN().equals(SSN)) return "Identical SSN to someone else: " + SSN;
		if(!salary.matches("^[\\d]+\\.?[\\d]*$")) 
			return "Salary not inputted correctly";
		if(Double.parseDouble(salary) < (MIN_WAGE)) return "Salary below minimum wage: " + salary;
		if(!years.matches("^(?:[0-9]|[1-8][0-9])")){
			return "Years worked incorrectly formatted: " + years ;
		} else {
			if((Integer.parseInt(age)-Integer.parseInt(years)) < 16) return "Person has worked while underage: "+years;
		}
		try {
			int yearsInt = Integer.parseInt(years), ageInt = Integer.parseInt(age);
			double salaryDouble = Double.parseDouble(salary);
			Employee e = (new Employee(fName, lName, SSN, ageInt, pronouns, salaryDouble, yearsInt, dept));
			employees.add(e);
			e.setEmpID(Employee.getNextID());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Gets the employee data str, wrapper method
	 *
	 * @return the employee data str
	 */
	public String[] getEmployeeDataStr() {
		return getEmployeeDataStr(false);
	}


	/**
	 * Gets the employee data str.
	 *
	 * @param isFile the is file
	 * @return the employee data str
	 */
	public String[] getEmployeeDataStr(boolean isFile) {
		String[] employeesList = new String[employees.size()];
		for(int i = 0; i < employees.size(); i++) 
			employeesList[i] = employees.get(i).toString(isFile);
		return employeesList;
	}



	/**
	 * Save employee DB to then be uploaded back into GUI next time it is opened
	 */
	public void saveEmployeeDB() {
		String dbFile = "empDB.dat";
		File empDB = fio.getFileHandle(dbFile);
		if(fio.checkFileStatus(empDB, true) == MyFileIO.FILE_DOES_NOT_EXIST) 
			fio.createEmptyFile(dbFile);
		BufferedWriter bw = fio.openBufferedWriter(empDB);
		try {
			sortByID();
			String[] employeeList = getEmployeeDataStr(true);
			for(int i = 0; i < employeeList.length; i++) {	
				bw.write(employeeList[i]);
			}
			fio.closeFile(bw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Initializes employee data from when the GUI is first opened
	 */
	public void initializeEmpData() {
		File empDB = fio.getFileHandle("empDB.dat");
		if(!(fio.checkFileStatus(empDB, true) == MyFileIO.FILE_OK)) 
			return;
		BufferedReader br = fio.openBufferedReader(empDB);
		try {
			String line;
			while((line = br.readLine()) != null) {
				String[] empInfo = line.split("\\|,\\|");
				String fName = empInfo[FNAME], lName = empInfo[LNAME], SSN = empInfo[SSN_INDEX], 
						age = empInfo[AGE_INDEX], pronouns = empInfo[PRONOUNS_INDEX], salary = empInfo[SALARY_INDEX], 
						years = empInfo[YEARS_INDEX], dept = empInfo[DEPT_INDEX];
				String msg = addEmployee(fName, lName, SSN, age, pronouns, salary, years, dept);
			}

			fio.closeFile(br);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reset static ID.
	 */
	public void resetStaticID() {
		Employee.resetEmpID();
	}

	/**
	 * Clear text fields.
	 *
	 * @param fName the f name
	 * @param lName the l name
	 * @param SSN the ssn
	 * @param age the age
	 * @param pronouns the pronouns
	 * @param salary the salary
	 * @param years the years
	 */
	public void clearTextFields(TextField fName, TextField lName, TextField SSN, TextField age,
			TextField pronouns, TextField salary, TextField years) {
		fName.clear();
		lName.clear();
		SSN.clear();
		age.clear();
		pronouns.clear();
		salary.clear();
		years.clear();
	}

	/**
	 * The Class ByID.
	 */
	private class ByID implements Comparator<Employee> {

		/**
		 * Compare.
		 *
		 * @param o1 the o 1
		 * @param o2 the o 2
		 * @return the int
		 */
		public int compare(Employee o1, Employee o2) {
			return Integer.compare(o1.getEmpID(), o2.getEmpID());
		}
	}

	/**
	 * Sort by ID.
	 */
	public void sortByID() {
		Collections.sort(employees, new ByID());
	}

	/**
	 * The Class BySalary.
	 */
	private class BySalary implements Comparator<Employee> {

		/**
		 * Compare.
		 *
		 * @param o1 the o 1
		 * @param o2 the o 2
		 * @return the int
		 */
		public int compare(Employee o1, Employee o2) {
			return Double.compare(o1.getSalary(), o2.getSalary());
		}
	}

	/**
	 * Sort by salary.
	 */
	public void sortBySalary() {
		Collections.sort(employees, new BySalary());
	}

	/**
	 * The Class ByName.
	 */
	private class ByName implements Comparator<Employee> {

		/**
		 * Compare.
		 *
		 * @param o1 the o 1
		 * @param o2 the o 2
		 * @return the int
		 */
		public int compare(Employee o1, Employee o2) {
			if(o1.getlName().compareTo(o2.getlName()) == 0)
				return o1.getfName().compareTo(o2.getfName());
			else 
				return o1.getlName().compareTo(o2.getlName());
		}
	}

	/**
	 * Sort by name.
	 */
	public void sortByName() {
		Collections.sort(employees, new ByName());
	}

	/**
	 * Gets the num employees.
	 *
	 * @return the num employees
	 */
	public int getNumEmployees() {
		System.out.println("Employees: "+ employees.toString());
		return employees.size();
	}
}

