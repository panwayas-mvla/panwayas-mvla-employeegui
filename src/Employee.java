
// TODO: Auto-generated Javadoc
/**
 * The Class Employee.
 */
public class Employee {

	/** The characteristics of the employee. */
	private String fName, lName, SSN, pronouns, dept;

	/** The salary. */
	private double salary;

	/** The emp ID. */
	private int years, age, empID;

	/** The emp ID counter. */
	private static int empIDCounter;


	/**
	 * Instantiates a new employee.
	 *
	 * @param fName the f name
	 * @param lName the l name
	 * @param SSN the ssn
	 * @param age the age
	 * @param pronouns the pronouns
	 * @param salary the salary
	 * @param years the years
	 * @param dept the dept
	 */
	public Employee (String fName, String lName, 
			String SSN, int age, String pronouns, double salary, int years, String dept) {
		this.fName = fName;
		this.lName = lName;
		this.SSN = SSN;
		this.age = age;
		this.pronouns = pronouns;
		this.salary = salary;
		this.years = years;
		this.dept = dept;
	}

	/**
	 * Gets the next ID.
	 *
	 * @return the next ID
	 */
	public static int getNextID() {
		return ++empIDCounter;
	}

	/**
	 * Gets the emp ID.
	 *
	 * @return the emp ID
	 */
	public int getEmpID() {
		return empID;
	}

	/**
	 * Sets the emp ID.
	 *
	 * @param empID the new emp ID
	 */
	public void setEmpID(int empID) {
		this.empID = empID;
	}

	/**
	 * Reset emp ID.
	 */
	public static void resetEmpID() {
		empIDCounter = 0;
	}

	/**
	 * Gets the f name.
	 *
	 * @return the f name
	 */
	public String getfName() {
		return fName;
	}

	/**
	 * Sets the f name.
	 *
	 * @param fName the new f name
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}


	/**
	 * Gets the l name.
	 *
	 * @return the l name
	 */
	public String getlName() {
		return lName;
	}

	/**
	 * Sets the l name.
	 *
	 * @param lName the new l name
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}

	/**
	 * Gets the ssn.
	 *
	 * @return the ssn
	 */
	public String getSSN() {
		return SSN;
	}

	/**
	 * Sets the ssn.
	 *
	 * @param sSN the new ssn
	 */
	public void setSSN(String sSN) {
		SSN = sSN;
	}

	/**
	 * Gets the pronouns.
	 *
	 * @return the pronouns
	 */
	public String getPronouns() {
		return pronouns;
	}

	/**
	 * Sets the pronouns.
	 *
	 * @param pronouns the new pronouns
	 */
	public void setPronouns(String pronouns) {
		this.pronouns = pronouns;
	}

	/**
	 * Gets the salary.
	 *
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}

	/**
	 * Sets the salary.
	 *
	 * @param salary the new salary
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}

	/**
	 * Gets the years.
	 *
	 * @return the years
	 */
	public int getYears() {
		return years;
	}

	/**
	 * Sets the years.
	 *
	 * @param years the new years
	 */
	public void setYears(int years) {
		this.years = years;
	}

	/**
	 * Gets the age.
	 *
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Sets the age.
	 *
	 * @param age the new age
	 */
	public void setAge(int age) {
		this.age = age;
	}



	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		return toString(false);
	}

	/**
	 * To string.
	 *
	 * @param isFile checks whether the toString will be uploaded into a file or the GUI
	 * @return the string
	 */
	public String toString(boolean isFile) {
		String separator = "|,|";

		if(isFile)
			return(toStringFile());
		else
			return(toStringGUI());

	}

	/**
	 * Returns the salary as a fixed point string (with two decimal places).
	 * This eliminates the problems where large floating point numbers get
	 * converted to scientific notation when writing to a file, to the console, or
	 * as part of a string to be displayed in the view. Updated the toString() method
	 * to call this to get the Salary.
	 *
	 * @return the fixed point salary
	 */
	public String getFixedPointSalary() {
		return String.format(String.format("%.2f",((long) (salary*100)/100.0)));
	}


	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toStringGUI( ) {
		String str = String.format("%-8s\t","Name:")+fName+" "+lName;
		str += (!pronouns.isEmpty()) ? " ("+pronouns+")\n" : "\n";
		str = str +    String.format("%-8s\t", "SSN")+String.format("%-16s\t",SSN );
		str = str +    String.format("%-16s\t","Employee ID:")+empID+"\n";
		str = str +    String.format("%-8s\t","Age:")+String.format("%-16d\t\t", 
				age)+String.format("%-20s\t","Years Exp:")+years+"\n";
		if(dept.equals("Finance")) {
			str = str +    String.format("%-8s\t","Dept:")+String.format("%-16s\t\t",dept);
		} else {
			str = str +    String.format("%-8s\t","Dept:")+String.format("%-16s\t",dept);		
		}
		str = str +    String.format("%-20s\t\t","Salary:")+ getFixedPointSalary();
		return(str);
	}

	/**
	 * To string for when the employee's data is added to a file.
	 *
	 * @return the string
	 */
	public String toStringFile() {
		String separator = "|,|";
		String str = empID + separator + fName+ separator +lName + separator;
		str = str +    SSN + separator;
		str = str +    age+ separator;
		str += (!pronouns.isEmpty()) ? ""+pronouns+"" + separator : separator;

		str = str +  String.format("%.2f",((long) (salary*100)/100.0)) + separator;
		str += years + separator;
		str = str + dept;
		return(str + "\n");
	}

}
