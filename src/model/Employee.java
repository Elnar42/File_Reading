package model;

import enums.Department;
import enums.Position;

import java.time.LocalDate;

public class Employee extends Person {

    private double salary;
    private Department department;
    private boolean isEmployee;
    private LocalDate startDate;
    private String email;
    private String phoneNumber;
    private Position position;
    private String address;

    public Employee(int id, String name, String surname, int age, double salary, Department department, boolean isEmployee, LocalDate startDate, String email, String phoneNumber, Position position, String address) {
        super(id, name, surname, age);
        this.salary = salary;
        this.department = department;
        this.isEmployee = isEmployee;
        this.startDate = startDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.address = address;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toFormattedString() {
        return String.format(
                "%-10s | %s | %s | %s | %s | %s | %s | %s",
                super.toFormattedString(),
                String.format("Salary: %-10.2f", salary),
                String.format("Department: %-15s", department),
                String.format("Is Employee: %-10b", isEmployee),
                String.format("Start Date: %-15s", startDate),
                String.format("Email: %-32s", email),
                String.format("Phone Number: %-15s", phoneNumber),
                String.format("Position: %-15s", position),
                String.format("Address: %-30s", address)
        );
    }

    @Override
    public String toString() {
        return super.toString() + "," + salary + "," + department + "," + isEmployee + "," + startDate + "," + email + "," + phoneNumber + "," + position + "," + address;
    }
}
