package config;

import enums.Department;
import enums.Position;
import model.Employee;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class FileHandler {

    public static List<Employee> employees = new ArrayList<>();

    // 1st Task
    public void loadAllEmployees(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            employees.add(createEmployee(line));
        }
        br.close();
    }

    // 2nd and 3rd Tasks
    public void raiseSalaryOfInternsInITDepartmentByWorkingYear(int workingYear, int percentage) throws IOException {
        List<Employee> listOfInterns = employees.stream().filter(employee -> employee.getPosition().equals(Position.INTERN)).toList();
        List<Employee> sortedByStartDate = listOfInterns.stream().sorted(Comparator.comparing(Employee::getStartDate, Comparator.naturalOrder())).toList();
        sortedByStartDate.forEach(employee -> {
            if (Period.between(employee.getStartDate(), LocalDate.now()).getYears() >= workingYear && employee.getDepartment().equals(Department.IT)) {
                double currentSalary = employee.getSalary();
                double increaseAmount = currentSalary * (percentage / 100.0);
                double newSalary = currentSalary + increaseAmount;
                employee.setSalary(newSalary);
            }
        });

        StringBuilder sb = new StringBuilder();
        for (Employee employee : sortedByStartDate) {
            sb.append(employee.toString()).append("\n");
        }
        if (sortedByStartDate.isEmpty()) {
            try {
                writeToFile(new File("src/rows/proceed_employees.txt"), "No Proceed Employee", false);
            } catch (IOException e) {
                System.out.println("Unable to save proceed employees to file");
            }
        } else {
            try {
                writeToFile(new File("src/rows/proceed_employees.txt"), sb.toString(), false);
            } catch (IOException io) {
                System.out.println("Unable to save proceed employees to file");
            }
        }


    }


    // 4th Task -> 1st
    public void createDepartmentSummary() throws IOException {
        Map<Department, Long> numberOfEmployeesInEachDepartment = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        Map<Department, Double> averageSalaryInEachDepartment = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        Map<Department, Optional<Employee>> employeeWithMaxSalaryInEachDepartment = extractComparingEmployee(Collectors.maxBy(Comparator.comparing(Employee::getSalary)));
        Map<Department, Optional<Employee>> employeeWithMinSalaryInEachDepartment = extractComparingEmployee(Collectors.minBy(Comparator.comparing(Employee::getSalary)));
        Map<Department, Double> averageAgeInEachDepartment = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingInt(Employee::getAge)));
        Map<Department, Optional<Employee>> employeeWithMaxAgeInEachDepartment = extractComparingEmployee(Collectors.maxBy(Comparator.comparing(Employee::getAge)));
        Map<Department, Optional<Employee>> employeeWithMinAgeInEachDepartment = extractComparingEmployee(Collectors.minBy(Comparator.comparing(Employee::getAge)));
        Map<Department, Optional<Employee>> employeeWithMinWorkingYear = extractComparingEmployee(Collectors.maxBy(Comparator.comparing(Employee::getStartDate)));
        Map<Department, Optional<Employee>> employeeWithMaxWorkingYear = extractComparingEmployee(Collectors.minBy(Comparator.comparing(Employee::getStartDate)));

        StringBuilder department_summary = new StringBuilder();
        for (int i = 0; i < Department.values().length - 1; i++) {
            String sb = Department.values()[i] + " :\n" +
                    "Number of employee: " + numberOfEmployeesInEachDepartment.get(Department.values()[i]) + "\n" +
                    "Average salary: " + averageSalaryInEachDepartment.get(Department.values()[i]) + "\n" +
                    "Employee with max salary: " + Objects.requireNonNull(employeeWithMaxSalaryInEachDepartment.get(Department.values()[i]).orElse(null)).getFullName() + "\n" +
                    "Employee with min salary: " + Objects.requireNonNull(employeeWithMinSalaryInEachDepartment.get(Department.values()[i]).orElse(null)).getFullName() + "\n" +
                    "Average age: " + averageAgeInEachDepartment.get(Department.values()[i]) + "\n" +
                    "Employee with max age: " + Objects.requireNonNull(employeeWithMaxAgeInEachDepartment.get(Department.values()[i]).orElse(null)).getFullName() + "\n" +
                    "Employee with min age: " + Objects.requireNonNull(employeeWithMinAgeInEachDepartment.get(Department.values()[i]).orElse(null)).getFullName() + "\n" +
                    "Employee with min working year: " + Objects.requireNonNull(employeeWithMinWorkingYear.get(Department.values()[i]).orElse(null)).getFullName() + "\n" +
                    "Employee with max working year: " + Objects.requireNonNull(employeeWithMaxWorkingYear.get(Department.values()[i]).orElse(null)).getFullName() + "\n" +
                    "------------------------------------------------------";
            department_summary.append(sb).append("\n");
        }
        try {
            writeToFile(new File("src/rows/department_summary.txt"), department_summary.toString(), false);
        } catch (IOException e) {
            System.out.println("Error while saving the department summary!");
        }

    }

    // 4th Task -> 2nd
    public void extractNewEmployees() throws IOException {
        List<Employee> list = employees.stream().filter(employee -> Period.between(employee.getStartDate(), LocalDate.now()).getYears() <= 2).toList();
        File file = new File("src/rows/new_employees.txt");
        if (list.isEmpty()) {
            try {
                writeToFile(file, "NO NEW EMPLOYEES FOUND", false);
            } catch (IOException e) {
                System.out.println("Error while saving the new employees!");
            }
        } else {
            StringBuilder new_employees = new StringBuilder();
            for (Employee employee : list) {
                new_employees.append(employee.toString()).append("\n");
            }
            try {
                writeToFile(file, new_employees.toString(), false);
            } catch (IOException e) {
                System.out.println("Unable to save new employees!");
            }

        }
    }


    //5th Task
    public void extractUniqueEmployees() {
        Set<Integer> seenIds = new HashSet<>();
        Set<String> seenEmail = new HashSet<>();
        List<Employee> uniqueEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (seenIds.contains(employee.getId()) || seenEmail.contains(employee.getEmail())) continue;
            uniqueEmployees.add(employee);
            seenEmail.add(employee.getEmail());
            seenIds.add(employee.getId());
        }

        StringBuilder unique_employees = new StringBuilder();
        for (Employee employee : uniqueEmployees) {
            unique_employees.append(employee.toString()).append("\n");
        }
        File file = new File("src/rows/unique_employees.txt");
        try {
            writeToFile(file, unique_employees.toString(), false);
        } catch (IOException io) {
            System.out.println("Unable to save unique employees!");
        }
    }


    //6th Task
    public void findAverageSalaryInEachDepartment() {
        Map<Department, Double> averageSalaryInEachDepartment = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        StringBuilder averageSalary = new StringBuilder();
        for (Department department : Department.values()) {
            String average = "Department: " + department.toString() + "\n" + "Average Salary: " + averageSalaryInEachDepartment.get(department) + "\n" + "--------------------------------";
            averageSalary.append(average).append("\n");
        }
        File file = new File("src/rows/average_salary.txt");
        try {
            writeToFile(file, averageSalary.toString(), false);
        } catch (IOException io) {
            System.out.println("Unable to save average salary!");
        }
    }


    //7th Task
    public void findLongestServingEmployee() {
        Employee longest_serving = employees.stream().sorted(Comparator.comparing(Employee::getStartDate)).toList().getFirst();
        try {
            writeToFile(new File("src/rows/longest_serving_employee.txt"), longest_serving.toString(), false);
        } catch (IOException e) {
            System.out.println("Error while saving the longest serving employee!");
        }
    }

    //8th Task
    public void findNumberOfEmployeesInEachPosition() {
        Map<Position, Long> employeeNumber = employees.stream().collect(Collectors.groupingBy(Employee::getPosition, Collectors.counting()));
        StringBuilder numberOfEmployees = new StringBuilder();
        for (Position position : Position.values()) {
            String employee_num = "Position: " + position.toString() + "\nNumber Of Employee: " + employeeNumber.get(position) + "\n" + "--------------------------------";
            numberOfEmployees.append(employee_num).append("\n");
        }
        File file = new File("src/rows/employees_by_position.txt");
        try {
            writeToFile(file, numberOfEmployees.toString(), false);
        } catch (IOException io) {
            System.out.println("Unable to save number of employees by position!");
        }
    }


    public void groupByFirstDepartmentThenPosition() {
        Map<Department, Map<Position, List<Employee>>> collect = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.groupingBy(Employee::getPosition)));
        StringBuilder groupedEmployees = new StringBuilder();
        Set<Department> departments = collect.keySet();
        for (Department department : departments) {
            StringBuilder sb = new StringBuilder();
            sb.append("Department: ").append(department.toString()).append("\n\n");
            Set<Position> positions = collect.get(department).keySet();
            for (Position position : positions) {
                sb.append("Position: ").append(position.toString()).append(":\n\n");
                for (Employee employee : collect.get(department).get(position)) {
                    sb.append(employee.toString()).append("\n");
                }
                sb.append("\n");
            }

            sb.append("---------------------------------------------------------------------------------------------------------").append(":\n");
            groupedEmployees.append(sb).append("\n");
        }
        File file = new File("src/rows/employees_by_department_and_position.txt");
        try {
            writeToFile(file, groupedEmployees.toString(), false);
        } catch (IOException e) {
            System.out.println("Unable to save number of employees by position!");
        }

    }


    private void writeToFile(File file, String text, boolean append) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
            bw.write(text);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Unable to write to file!");
        }
    }

    private Employee createEmployee(String line) {
        String[] data = line.split(",");
        return new Employee(Integer.parseInt(data[0]), data[1], data[2], Integer.parseInt(data[3]), Double.parseDouble(data[4]), Department.getDepartment(data[5]), Boolean.parseBoolean(data[6]), LocalDate.parse(data[7]), data[8], data[9], Position.getPosition(data[10]), data[11]);
    }

    private Map<Department, Optional<Employee>> extractComparingEmployee(Collector<Employee, ?, Optional<Employee>> collector) {
        return employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, collector));

    }

}
