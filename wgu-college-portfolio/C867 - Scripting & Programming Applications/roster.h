#pragma once
#include "student.h"
using namespace std;
class Roster {
public: 
	int prevStudent; //index of last student
	int classSize; //roster size
	Student** students; //array of pointers to the student objects
	//constructors
	Roster(); //default constructor
	Roster(int headCount); //true constructor
	~Roster(); //destructor
	//methods
	void vectorInput(string rawData); //raw data string inputs to vector
	DegreeProgram stringToEnum(string str); //string is converted to enum value
	void add(string studentID, string firstName, string lastName, string emailAddress, int studentAge, int daysInCourse1, int daysInCourse2, int daysInCourse3, DegreeProgram degreeProgram); //add new student to roster
	void printAll(); //print list of students and student info
	void remove(string studentID); //remove a student and shift the roster
	void printAverageDaysInCourse(string studentID); //prints avg num of days in 3 courses
	void printInvalidEmails(); //validates and prints student emails
	void printByDegreeProgram(DegreeProgram degreeProgram); //prints student info by enum
};