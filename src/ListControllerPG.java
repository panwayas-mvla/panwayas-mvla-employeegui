
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import myfileio.MyFileIO;

public class ListControllerPG {
	private ArrayList<EmployeePG> employees;
	private static final boolean DEBUG = true;
	private MyFileIO fio;
	private EmpDataGUIPG gui;

	public ListControllerPG () {
		resetStaticID();
		employees = new ArrayList<EmployeePG>();
		fio = new MyFileIO();
		initializeEmpData();
	}

	// adds a new employee
	public String addEmployee(int empID, String fName, String lName, String SSN, String age,
			String pronouns, String salary, String years, String dept) {
		if(!fName.matches("^[A-Za-z]+")) return "First name not inputted in correct format";
		if(!lName.matches("^[A-Za-z]+")) return "Last name not inputted in correct format";
		if(!age.matches("^(?:[0-9]|[1-8][0-9])")) 
			return "Age incorrectly formatted";
		 else 
			if(Integer.parseInt(age) < 16) return "Too young to work in industry";
		if(!SSN.matches("[\\d]{3}-[\\d]{2}-[\\d]{4}")) return "SSN not inputted in correct format";
		for(EmployeePG employee: employees)
			if(employee.getSSN().equals(SSN)) return "Identical SSN to someone else";
		if(!salary.matches("[\\d]*.[\\d]{2}")) return "Salary not inputted correctly";
			if(Integer.parseInt(salary) < 31200) return "Salary below minimum wage";
		if(!years.matches("^(?:[0-9]|[1-8][0-9])")){
			return "Years worked incorrectly formatted";
		} else {
			if((Integer.parseInt(age) - Integer.parseInt(years)) < 16)
				return "Person has worked while underage";
		}
		try {
			int yearsInt = Integer.parseInt(years), ageInt = Integer.parseInt(age);
			double salaryDouble = Double.parseDouble(salary);
			employees.add(new EmployeePG(empID, fName, lName, SSN, ageInt, pronouns, salaryDouble, yearsInt, dept));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String[] getEmployeeDataStr() {
		return getEmployeeDataStr(false);
	}


	// returns a string array of the employee information to be viewed
	public String[] getEmployeeDataStr(boolean isFile) {
		String[] employeesList = new String[employees.size()];
		for(int i = 0; i < employees.size(); i++) 
			employeesList[i] = employees.get(i).toString(isFile);
		return employeesList;
	}



	public void saveEmployeeDB() {
		String dbFile = "empDB.dat";
		File empDB = fio.getFileHandle(dbFile);
		if(fio.checkFileStatus(empDB, true) == MyFileIO.FILE_DOES_NOT_EXIST) 
			fio.createEmptyFile(dbFile);
		BufferedWriter bw = fio.openBufferedWriter(empDB);
		String[] employeeList = getEmployeeDataStr(true);
		try {
			for(int i = 0; i < employeeList.length; i++) {	
				bw.write(employeeList[i]);
			}
			fio.closeFile(bw);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public void initializeEmpData() {
		File empDB = fio.getFileHandle("empDB.dat");
		if(!(fio.checkFileStatus(empDB, true) == MyFileIO.FILE_OK)) 
			return;
		BufferedReader br = fio.openBufferedReader(empDB);
		try {
			String line;
			while((line = br.readLine()) != null) {
				String[] empInfo = line.split("\\|,\\|");

				String fName = empInfo[1], lName = empInfo[2], SSN = empInfo[3], age = empInfo[4]
						, pronouns = empInfo[5], salary = empInfo[6], years = empInfo[7], dept = empInfo[8];


				int empID = Integer.parseInt(empInfo[0]);
				//String fName = empInfo[1], lName = empInfo[2], SSN = "a", age = "1"
				//		, pronouns = "c", salary = "1.1", years = "2", dept = "finance";
				addEmployee(empID, fName, lName, SSN, age, pronouns, salary, years, dept);
			}
			fio.closeFile(br);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void resetStaticID() {
		EmployeePG.resetEmpID();
	}

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

	private class ByID implements Comparator<EmployeePG> {
		public int compare(EmployeePG o1, EmployeePG o2) {
			return Integer.compare(o1.getEmpID(), o2.getEmpID());
		}
	}

	public void sortByID() {
		Collections.sort(employees, new ByID());
	}

	private class BySalary implements Comparator<EmployeePG> {
		public int compare(EmployeePG o1, EmployeePG o2) {
			return Double.compare(o1.getSalary(), o2.getSalary());
		}
	}

	public void sortBySalary() {
		Collections.sort(employees, new BySalary());
	}

	private class ByName implements Comparator<EmployeePG> {
		public int compare(EmployeePG o1, EmployeePG o2) {
			if(o1.getlName().compareTo(o2.getlName()) == 0)
				return o1.getfName().compareTo(o2.getfName());
			else 
				return o1.getlName().compareTo(o2.getlName());
		}
	}

	public void sortByName() {
		Collections.sort(employees, new ByName());
	}
}

