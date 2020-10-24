package models;

public class SchoolClass {
    private String code;
    private String name;
    private String description;
    private String notes;
    private int ec;
    private int year;
    private int period;
    private int grade;
    private boolean mandatory;

    public SchoolClass(){

    };

    public SchoolClass(String code, String name, String description, String notes, int ec, int year, int period, int grade, boolean mandatory) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.notes = notes;
        this.ec = ec;
        this.year = year;
        this.period = period;
        this.grade = grade;
        this.mandatory = mandatory;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getEc() {
        return ec;
    }

    public void setEc(int ec) {
        this.ec = ec;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
}
