import config.FileHandler;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileHandler fh = new FileHandler();
        fh.loadAllEmployees(new File("src/rows/employees.txt"));
        fh.raiseSalaryOfInternsInITDepartmentByWorkingYear(5, 15);
        fh.createDepartmentSummary();
        fh.extractNewEmployees();
        fh.extractUniqueEmployees();
        fh.findAverageSalaryInEachDepartment();
        fh.findLongestServingEmployee();
        fh.groupByFirstDepartmentThenPosition();
        fh.findNumberOfEmployeesInEachPosition();
    }
}