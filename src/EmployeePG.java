
public class EmployeePG {
	private String fName, lName, SSN, pronouns, dept;
	private double salary;
	private int years, age, empID;
	
	private static int empIDCounter;


	public EmployeePG (int empID, String fName, String lName, 
			String SSN, int age, String pronouns, double salary, int years, String dept) {
		this.fName = fName;
		this.lName = lName;
		this.SSN = SSN;
		this.age = age;
		this.pronouns = pronouns;
		this.salary = salary;
		this.years = years;
		this.empID = empID;
		this.dept = dept;
	}
	
	public static int getNextID() {
		return ++empIDCounter;
	}
 
	public int getEmpID() {
		return empID;
	}

	public void setEmpID(int empID) {
		this.empID = empID;
	}
	
	public static void resetEmpID() {
		empIDCounter = 0;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}


	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String sSN) {
		SSN = sSN;
	}

	public String getPronouns() {
		return pronouns;
	}

	public void setPronouns(String pronouns) {
		this.pronouns = pronouns;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}



	public String toString() {
		return toString(false);
	}

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
	 * To string for when the employee's data is added to a file
	 *
	 * @return the string
	 */
	public String toStringFile() {
		String separator = "|,|";
		String str = empID + separator + fName+ separator +lName + separator;
		
		str += (!pronouns.isEmpty()) ? " "+pronouns+"" + separator : separator;
		str = str +    SSN + separator;
		str = str +    empID + separator;
		str = str +    age+ separator + years+ separator;
		str = str +    dept + separator;
			
		str = str +  String.format("%.2f",((long) (salary*100)/100.0));
		return(str + "\n");
	}

}
