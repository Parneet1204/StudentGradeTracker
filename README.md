# Student Grade Tracker 🎓

![Java](https://img.shields.io/badge/Java-17+-blue)
![License](https://img.shields.io/badge/License-MIT-green)
![CLI](https://img.shields.io/badge/Interface-CLI-lightgrey)

A comprehensive command-line application for managing student grades with multiple assessment categories and detailed analytics.

## Features ✨

- **Student Management**
  - ✅ Add new students with grades
  - ✏️ Edit existing student records
  - ❌ Remove students
- **Multi-Category Grading**
  - 📝 Tests
  - ✍️ Quizzes
  - 📚 Assignments
- **Advanced Analytics**
  - 📊 Calculate averages per category
  - 🔍 Find highest/lowest scores
  - 📈 Generate comprehensive reports
- **Data Persistence**
  - 💾 Automatic save/load functionality
  - 🔄 Binary serialization
- **Grade Conversion**
  - 🔤 Automatic letter grade (A-F) conversion

## Installation ⚙️

1. **Prerequisites**: Java 17 or later
2. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/student-grade-tracker.git
   cd student-grade-tracker
3. **Compile:**
   javac StudentGradeTracker.java

4  **Run:**
   java StudentGradeTracker

##  Usage 🖥️
**Main Menu Options:**

**Option	Description**
- 1	Add a new student
- 2	Edit a student's grades
- 3	Remove a student
- 4	View all students
- 5	Calculate statistics
- 6	Generate summary report
- 7	Save data to file
- 8	Exit program

##  Sample Workflow:
- Add students with their grades
- View all entries to verify data
- Generate reports when needed
- Save before exiting

## Data Storage 💾
- File: student_data_cli.ser (binary format)
- Auto-save: On manual save (option 7) and program exit
- Auto-load: At program startup

## Class Structure 🏗️

    class StudentGradeTracker{
        -students: ArrayList<Student>
        -scanner: Scanner
        +main()
        -run()
        -addStudent()
        -editStudent()
        -removeStudent()
        -viewAllStudents()
        -calculateStatistics()
        -generateSummaryReport()
        -saveData()
        -loadData()
    }
    
    class Student{
        -name: String
        -testGrade: double
        -quizGrade: double
        -assignmentGrade: double
        +getAverage()
        +getLetterGrade()
    }
    
    StudentGradeTracker "1" *-- "0..*" Student

##  Contributing 🤝
Fork the project

- Create your feature branch (git checkout -b feature/amazing-feature)
- Commit your changes (git commit -m 'Add some amazing feature')
- Push to the branch (git push origin feature/amazing-feature)
- Open a Pull Request

##  License 📄
This project is licensed under the MIT License - see the LICENSE file for details.

##  Acknowledgments 🙏
- Built with Java Standard Edition
- Uses Java Object Serialization
- Inspired by educator needs for grade management
