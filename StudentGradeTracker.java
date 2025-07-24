import java.io.*;
import java.util.*;

public class StudentGradeTracker{
    private ArrayList<Student> students;
    private Scanner scanner;
    private static final String DATA_FILE = "student_data_cli.ser";

    public StudentGradeTracker() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadData();
    }

    public static void main(String[] args) {
        StudentGradeTracker tracker = new StudentGradeTracker();
        tracker.run();
    }

    private void run() {
        System.out.println("=== Enhanced Student Grade Tracker (CLI) ===");
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1: addStudent(); break;
                case 2: editStudent(); break;
                case 3: removeStudent(); break;
                case 4: viewAllStudents(); break;
                case 5: calculateStatistics(); break;
                case 6: generateSummaryReport(); break;
                case 7: saveData(); break;
                case 8: running = false; break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Exiting the program. Goodbye!");
    }

    private void displayMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Add a new student");
        System.out.println("2. Edit a student's grades");
        System.out.println("3. Remove a student");
        System.out.println("4. View all students");
        System.out.println("5. Calculate statistics");
        System.out.println("6. Generate summary report");
        System.out.println("7. Save data to file");
        System.out.println("8. Exit");
    }

    private void addStudent() {
        System.out.print("\nEnter student name: ");
        String name = scanner.nextLine();
        
        Student student = new Student(name);
        System.out.println("Enter grades for categories (0-100):");
        
        double testGrade = getValidGrade("Test grade: ");
        double quizGrade = getValidGrade("Quiz grade: ");
        double assignmentGrade = getValidGrade("Assignment grade: ");
        
        student.setTestGrade(testGrade);
        student.setQuizGrade(quizGrade);
        student.setAssignmentGrade(assignmentGrade);
        
        students.add(student);
        System.out.println("\nStudent added successfully!");
    }

    private void editStudent() {
        if (students.isEmpty()) {
            System.out.println("\nNo students in the system to edit.");
            return;
        }
        
        viewAllStudents();
        int index = getIntInput("\nEnter student number to edit: ") - 1;
        
        if (index < 0 || index >= students.size()) {
            System.out.println("Invalid student number.");
            return;
        }
        
        Student student = students.get(index);
        System.out.println("\nEditing " + student.getName());
        System.out.println("Current grades:");
        System.out.println("1. Test: " + student.getTestGrade());
        System.out.println("2. Quiz: " + student.getQuizGrade());
        System.out.println("3. Assignment: " + student.getAssignmentGrade());
        
        int choice = getIntInput("\nWhich grade to edit? (1-3): ");
        double newGrade = getValidGrade("Enter new grade: ");
        
        switch (choice) {
            case 1: student.setTestGrade(newGrade); break;
            case 2: student.setQuizGrade(newGrade); break;
            case 3: student.setAssignmentGrade(newGrade); break;
            default: System.out.println("Invalid choice. No changes made."); return;
        }
        
        System.out.println("\nGrade updated successfully!");
        System.out.println("Updated grades for " + student.getName() + ":");
        System.out.println("Test: " + student.getTestGrade());
        System.out.println("Quiz: " + student.getQuizGrade());
        System.out.println("Assignment: " + student.getAssignmentGrade());
    }

    private void removeStudent() {
        if (students.isEmpty()) {
            System.out.println("\nNo students in the system to remove.");
            return;
        }
        
        viewAllStudents();
        int index = getIntInput("\nEnter student number to remove: ") - 1;
        
        if (index < 0 || index >= students.size()) {
            System.out.println("Invalid student number.");
            return;
        }
        
        String name = students.get(index).getName();
        students.remove(index);
        System.out.println("\n" + name + " has been removed from the system.");
    }

    private void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("\nNo students in the system.");
            return;
        }
        
        System.out.println("\nList of Students:");
        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-3s %-20s %-10s %-10s %-12s %-10s %-10s%n", 
                         "#", "Name", "Test", "Quiz", "Assignment", "Average", "Letter");
        
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.printf("%-3d %-20s %-10.2f %-10.2f %-12.2f %-10.2f %-10s%n", 
                            i+1, s.getName(), s.getTestGrade(), 
                            s.getQuizGrade(), s.getAssignmentGrade(),
                            s.getAverage(), s.getLetterGrade());
        }
    }

    private void calculateStatistics() {
        if (students.isEmpty()) {
            System.out.println("\nNo students to calculate statistics.");
            return;
        }
        
        // Initialize variables for category statistics
        double testSum = 0, quizSum = 0, assignmentSum = 0, overallSum = 0;
        double testHigh = Double.MIN_VALUE, testLow = Double.MAX_VALUE;
        double quizHigh = Double.MIN_VALUE, quizLow = Double.MAX_VALUE;
        double assignHigh = Double.MIN_VALUE, assignLow = Double.MAX_VALUE;
        double overallHigh = Double.MIN_VALUE, overallLow = Double.MAX_VALUE;
        
        for (Student s : students) {
            double test = s.getTestGrade();
            double quiz = s.getQuizGrade();
            double assign = s.getAssignmentGrade();
            double average = s.getAverage();
            
            // Update sums
            testSum += test;
            quizSum += quiz;
            assignmentSum += assign;
            overallSum += average;
            
            // Update highs and lows for each category
            if (test > testHigh) testHigh = test;
            if (test < testLow) testLow = test;
            if (quiz > quizHigh) quizHigh = quiz;
            if (quiz < quizLow) quizLow = quiz;
            if (assign > assignHigh) assignHigh = assign;
            if (assign < assignLow) assignLow = assign;
            if (average > overallHigh) overallHigh = average;
            if (average < overallLow) overallLow = average;
        }
        
        System.out.println("\nGrade Statistics:");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-15s %-10s %-10s %-10s%n", "Category", "Average", "Highest", "Lowest");
        System.out.printf("%-15s %-10.2f %-10.2f %-10.2f%n", 
                         "Tests", testSum/students.size(), testHigh, testLow);
        System.out.printf("%-15s %-10.2f %-10.2f %-10.2f%n", 
                         "Quizzes", quizSum/students.size(), quizHigh, quizLow);
        System.out.printf("%-15s %-10.2f %-10.2f %-10.2f%n", 
                         "Assignments", assignmentSum/students.size(), assignHigh, assignLow);
        System.out.printf("%-15s %-10.2f %-10.2f %-10.2f%n", 
                         "Overall", overallSum/students.size(), overallHigh, overallLow);
        
        // Letter grade distribution
        System.out.println("\nLetter Grade Distribution:");
        System.out.println("--------------------------");
        int a = 0, b = 0, c = 0, d = 0, f = 0;
        for (Student s : students) {
            switch (s.getLetterGrade()) {
                case "A": a++; break;
                case "B": b++; break;
                case "C": c++; break;
                case "D": d++; break;
                case "F": f++; break;
            }
        }
        System.out.printf("A: %d | B: %d | C: %d | D: %d | F: %d%n", a, b, c, d, f);
    }

    private void generateSummaryReport() {
        if (students.isEmpty()) {
            System.out.println("\nNo data available for report.");
            return;
        }
        
        System.out.println("\n=== COMPREHENSIVE GRADE REPORT ===");
        System.out.println("=================================");
        System.out.println("Generated on: " + new Date());
        System.out.println("Total students: " + students.size());
        System.out.println("=================================");
        
        viewAllStudents();
        calculateStatistics();
        
        System.out.println("\nAdditional Information:");
        System.out.println("=================================");
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
            System.out.println("\nData saved successfully to " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("\nError saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("No existing data file found. Starting with empty database.");
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            students = (ArrayList<Student>) ois.readObject();
            System.out.println("Data loaded successfully from " + DATA_FILE);
            System.out.println("Loaded " + students.size() + " student records.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            System.out.println("Starting with empty database.");
        }
    }

    private double getValidGrade(String prompt) {
        double grade;
        do {
            grade = getDoubleInput(prompt);
            if (grade < 0 || grade > 100) {
                System.out.println("Grade must be between 0 and 100.");
            }
        } while (grade < 0 || grade > 100);
        return grade;
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid integer.");
            scanner.next();
            System.out.print(prompt);
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
            System.out.print(prompt);
        }
        double input = scanner.nextDouble();
        scanner.nextLine();
        return input;
    }
}

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private double testGrade;
    private double quizGrade;
    private double assignmentGrade;

    public Student(String name) {
        this.name = name;
    }

    // Getters and setters
    public String getName() { return name; }
    public double getTestGrade() { return testGrade; }
    public double getQuizGrade() { return quizGrade; }
    public double getAssignmentGrade() { return assignmentGrade; }

    public void setName(String name) { this.name = name; }
    public void setTestGrade(double grade) { testGrade = grade; }
    public void setQuizGrade(double grade) { quizGrade = grade; }
    public void setAssignmentGrade(double grade) { assignmentGrade = grade; }

    public double getAverage() {
        return (testGrade + quizGrade + assignmentGrade) / 3;
    }

    public String getLetterGrade() {
        double avg = getAverage();
        if (avg >= 90) return "A";
        if (avg >= 80) return "B";
        if (avg >= 70) return "C";
        if (avg >= 60) return "D";
        return "F";
    }
}
