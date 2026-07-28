#include <iostream>
#include <iomanip>
#include "roster.h"
using std::cout;
using namespace std;
//----------------------------------------------------------------------//
int main() {
	const int studentCount = 5;
	const string studentData[] = {
		"A1,John,Smith,John1989@gm ail.com,20,30,35,40,SECURITY", 
		"A2,Suzan,Erickson,Erickson_1990@gmailcom,19,50,30,40,NETWORK",
		"A3,Jack,Napoli,The_lawyer99yahoo.com,19,20,40,33,SOFTWARE", 
		"A4,Erin,Black,Erin.black@comcast.net,22,50,58,40,SECURITY",
		"A5,First,Last ,flast1@wgu.edu,21,30,30,30,SOFTWARE"
	};
	//----------------------------------------------------------------------//
	cout << endl;
	cout << "| Scripting and Programming - Applications - C867 |" << "\t" << "| Coding Language - C++ |" << "\t" << "| Student ID - 000000000 |" << "\t" << "| Student Name - First Last |" << endl;
	cout << "..." << endl;
	//----------------------------------------------------------------------//
		Roster* classRoster = new Roster(studentCount); //create a new Roster pointer to classRoster w/ a default size of studentCount
	for (int i = 0; i < studentCount; i++) {
		classRoster->vectorInput(studentData[i]);
	}
	cout << "Students added to roster." << endl << endl;
	//----------------------------------------------------------------------//
	classRoster->printAll();
	classRoster->printInvalidEmails();
	//loop through classRosterArray
	cout << "Displaying Average Days In Course by Student: " << endl << endl;
	for (int i = 0; i < classRoster->classSize; i++) {
		classRoster->printAverageDaysInCourse(classRoster->students[i]->getStudentID());
	}
	cout << endl;
	classRoster->printByDegreeProgram(SOFTWARE);
	classRoster->remove("A3");
	cout << endl;
	classRoster->printAll();
	classRoster->remove("A3");
	//----------------------------------------------------------------------//
	return 0;
}
//----------------------------------------------------------------------//