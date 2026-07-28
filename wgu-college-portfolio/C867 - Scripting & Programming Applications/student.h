#pragma once
#include <string>
#include "degree.h"
using namespace std;
//----------------------------------------------------------------------//
class Student {
	//----------------------------------------------------------------------//
public:
	const static int daysInCourseMax = 3;
	//----------------------------------------------------------------------//
private:
	string studentID;
	string firstName;
	string lastName;
	string emailAddress;
	int age;
	int daysInCourse[daysInCourseMax];
	DegreeProgram degreeProgram;
	//----------------------------------------------------------------------//
public:
	//DEFINITIONS START
	//----------------------------------------------------------------------// 
	//constructors
	Student(); //default
	Student(string studentID, string firstName, string lastName, string emailAddress, int age, int courseCompletionDays[], DegreeProgram degreeProgram); //true constructor
	~Student(); //destructor
	//----------------------------------------------------------------------//
	//getters
	string getStudentID() const;
	string getFirstName() const;
	string getLastName() const;
	string getEmailAddress() const;
	int getAge() const;
	int* getDaysInCourse();
	DegreeProgram getDegreeProgram() const;
	//----------------------------------------------------------------------//
	//setters
	void setStudentID(string studentID);
	void setFirstName(string firstName);
	void setLastName(string lastName);
	void setEmailAddress(string emailAddress);
	void setAge(int age);
	void setDaysInCourse(int courseCompletionDays[]);
	void setDegreeProgram(DegreeProgram degreeProgram);
	//----------------------------------------------------------------------//
	//method
	void print();
	//DEFINITIONS END
};
//----------------------------------------------------------------------//