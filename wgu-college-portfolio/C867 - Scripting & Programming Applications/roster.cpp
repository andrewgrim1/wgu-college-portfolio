#include <iostream>
#include <sstream>
#include <vector>
#include <string>
#include "student.h"
#include "roster.h"
#include "degree.h"
using namespace std;
//----------------------------------------------------------------------//
// class constructor
// default constructor
Roster::Roster() {
	this->classSize = 0;
	this->prevStudent = -1; //can't use 0; it's a valid array index
	this->students = nullptr;
}
// true constructor
Roster::Roster(int headCount) {
	this->classSize = headCount;
	this->prevStudent = -1;
	this->students = new Student * [headCount]; //new student pointer to an array of student instances
}
//destructor constructor
Roster::~Roster() {
	//for each student in the roster, delete the students - then the roster
	for (int i = 0; i < classSize; i++) {
		delete this->students[i]; //remove each student from memory
	}
	delete this; //remove empty roster from memory
}
//----------------------------------------------------------------------//
//methods
void Roster::vectorInput(string rawData) {
	vector<string> studentDataElements; 
	stringstream inputSStream(rawData);
	while (inputSStream.good()) {
		string data;
		getline(inputSStream, data, ',');
		studentDataElements.push_back(data);
	}
	add(studentDataElements.at(0), //ID
		studentDataElements.at(1), //First Name
		studentDataElements.at(2), //Last Name
		studentDataElements.at(3), //Email Address
		stoi(studentDataElements.at(4)), //age
		stoi(studentDataElements.at(5)), //Days in course 1
		stoi(studentDataElements.at(6)), //Days in course 2
		stoi(studentDataElements.at(7)), //Days in course 3
		stringToEnum(studentDataElements.at(8)) //degree program
	);
}
DegreeProgram Roster::stringToEnum(string str) {
	//convert the string to an enum
	if (str == "SECURITY") return SECURITY;
	else if (str == "NETWORK") return NETWORK;
	else if (str == "SOFTWARE") return SOFTWARE;
	else return NONE;
}
void Roster::add(string studentID, string firstName, string lastName, string emailAddress, int age, int daysInCourse1, int daysInCourse2, int daysInCourse3, DegreeProgram degreeProgram) {
	//add a new student to the roster
	if (prevStudent < classSize) {
		prevStudent++;
		this->students[prevStudent] = new Student();
		this->students[prevStudent]->setStudentID(studentID);
		this->students[prevStudent]->setFirstName(firstName);
		this->students[prevStudent]->setLastName(lastName);
		this->students[prevStudent]->setEmailAddress(emailAddress);
		this->students[prevStudent]->setAge(age);
		int days[3] = { daysInCourse1, daysInCourse2, daysInCourse3 };
		this->students[prevStudent]->setDaysInCourse(days);
		this->students[prevStudent]->setDegreeProgram(degreeProgram);

	}
	else {
		cout << "Error: Roster is greater than student headcount." << endl;
	}
}
void Roster::printAll() {
	//print a list of students and their data
	cout << "Displaying All Students:" << endl << endl;
	for (int i = 0; i <= this->prevStudent; i++) {
	(this->students)[i]->print();
	cout << endl;
}
	cout << endl;
}
void Roster::remove(string studentID) {
	//remove students from the roster by student ID
	bool checker = false;
	for (int i = 0; i <= prevStudent; i++) {
		if (this->students[i]->getStudentID() == studentID) {
			//student found
			checker = true;
			delete this->students[i];
			this->students[i] = this->students[prevStudent]; //removed student is replaced by last student in roster
			prevStudent--; //reduce class roster size
			cout << "Student: " << studentID << " removed from roster." << endl << endl;
		}
	}
	if (!checker) {
		//student not found
		cout << "Error: Student: " << studentID << " not found in roster." << endl << endl;
	}
}
void Roster::printAverageDaysInCourse(string studentID) {
	bool checker = false;
	for (int i = 0; i <= prevStudent; i++) {
		if (this->students[i]->getStudentID() == studentID) {
			checker = true;
			int* days = this->students[i]->getDaysInCourse(); 
			double averageDays = static_cast<double>(days[0] + days[1] + days[2]) / 3.0;
			cout << "Student " << studentID << " average is: " << averageDays << endl;
		}
	}
	if (!checker) {
		std::cerr << "Error: Student: " << studentID << " not found in roster." << endl << endl;
	}
}
void Roster::printInvalidEmails() {
	cout << "Validating and Displaying Student Email Information:" << endl << endl;
	string locatorDOT = ".";
	string locatorSPACE = " ";
	string locatorAT = "@";
	for (int i = 0; i <= prevStudent; i++) {
		string email = this->students[i]->getEmailAddress();
		if ((email.find(locatorDOT) == string::npos) || (email.find(locatorAT) == string::npos) || (email.find(locatorSPACE) != string::npos)) {
			cout << "Email: " << email << " is invalid." << endl;
		}
		else {
			cout << "Email: " << email << " is valid." << endl;
		}
	}
	cout << endl << endl;
}
void Roster::printByDegreeProgram(DegreeProgram degreeProgram) {
	cout << "Displaying Students Enrolled in the " << degreeProgramStringArray[degreeProgram] << " degree program:" << endl << endl;
	for (int i = 0; i <= prevStudent; i++) {
		if (this->students[i]->getDegreeProgram() == degreeProgram) {
			this->students[i]->print();
			cout << endl;
		}
	}
	cout << endl;
}
//----------------------------------------------------------------------//