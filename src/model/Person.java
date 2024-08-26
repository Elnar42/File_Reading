package model;

public abstract class Person {
    private int id;
    private String name;
    private String surname;
    private int age;


    public Person(int id, String name, String surname, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public String getFullName(){
        return name + " " + surname;
    }

    public String toFormattedString() {
        return String.format(
                "ID: %-5d | Name: %-10s | Surname: %-10s | Age: %-8d",
                id,
                name,
                surname,
                age
        );
    }

    @Override
    public String toString() {
        return id + "," + name + "," + surname + "," + age;
    }
}
