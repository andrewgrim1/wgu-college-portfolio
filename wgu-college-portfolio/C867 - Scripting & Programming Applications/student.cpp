#include <iostream>
#include <iomanip>
#include <string>
#include "student.h"
#include "degree.h"
using std::cout;
using namespace std;
//----------------------------------------------------------------------//
//constructors
//default constructor
Student::Student() {
	this->studentID = "";
	this->firstName = "";
	this->lastName = "";
	this->emailAddress = "";
	this->age = -1;
	for (int i = 0; i < daysInCourseMax; i++) {
		this->daysInCourse[i] = 0;
	}
	this->degreeProgram = NONE;
}
//true constructor
Student::Student(string studentID, string firstName, string lastName, string emailAddress, int age, int courseCompletionDays[], DegreeProgram degreeProgram) {
	this->studentID = studentID;
	this->firstName = firstName;
	this->lastName = lastName;
	this->emailAddress = emailAddress;
	this->age = age;
	for (int i = 0; i < daysInCourseMax; i++) {
		this->daysInCourse[i] = courseCompletionDays[i];
	}
	this->degreeProgram = degreeProgram;
}
//destructor
Student::~Student() {}
//----------------------------------------------------------------------//
//getters
string Student::getStudentID() const { return studentID; }
string Student::getFirstName() const { return firstName; }
string Student::getLastName() const { return lastName; }
string Student::getEmailAddress() const { return emailAddress; }
int Student::getAge() const { return age; }
int* Student::getDaysInCourse() { return daysInCourse; }
DegreeProgram Student::getDegreeProgram() const { return degreeProgram; }
//----------------------------------------------------------------------//
//setters
void Student::setStudentID(string studentID) { this->studentID = studentID; }
void Student::setFirstName(string firstName) { this->firstName = firstName; }
void Student::setLastName(string lastName) { this->lastName = lastName; }
void Student::setEmailAddress(string emailAddress) { this->emailAddress = emailAddress; }
void Student::setAge(int age) { this->age = age; }
void Student::setDaysInCourse(int courseCompletionDays[]) { 
	for (int i = 0; i < daysInCourseMax; i++) { this->daysInCourse[i] = courseCompletionDays[i]; } 
}
void Student::setDegreeProgram(DegreeProgram program) { this->degreeProgram = program; }
//----------------------------------------------------------------------//
//methods
void Student::print() {

	cout << "| " << studentID << " |" << "\t" << "First Name: " << firstName << " |" << "\t" << "Last Name: " << lastName << " |" << "\t" << "Age: " << age << " |" << "\t" << "daysInCourse: {" << daysInCourse[0] <<
		", " << daysInCourse[1] << ", " << daysInCourse[2] << "} " << " |" << "\t" << "Degree Program: " << degreeProgramStringArray[getDegreeProgram()] << " |" << endl;
}
//----------------------------------------------------------------------//