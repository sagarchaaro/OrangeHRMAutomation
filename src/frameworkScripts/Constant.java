
package frameworkScripts;

public class Constant {

	
	public static final String TestCaseID="TC_01_01";
	
	// SheetName detail of HRM_TestData sheet
	public static final String sheet_TestCases = "TestCases";
	public static final String sheet_Login = "Login";
	public static final String sheet_AddEmployeeCases = "Add Employee";
	public static final String sheet_EditUserCases = "Edit Location";
	public static final String sheet_AddVacancyCases = "Add Vacancy";
	public static final String sheet_EditEmployeeCases = "EditEmployee";
	public static final String sheet_ApplyLeaveCases = "Apply Leave";
	public static final String sheet_AddUserCases = "Add User";
	public static final String sheet_DeciplinaryCases = "Deciplinary Cases";
	public static final String sheet_TravelRequestCases = "Travel Request";

	/*
	 * COMMON DATA IN ALL SHEET IN EVERY TEST CASE TEST "CASE ID" AND
	 * "TEST CASE NAME" WILL BE IN COLOMN 1 AND 2 LOG IN DETAIL, IN EVERY SHEET THE
	 * USER ID AND PASSWORD WILL BE IN COLOMN 3 AND 4
	 */
	public static final int col_Sno = 0;
	public static final int col_TestCaseName = 1;
	public static final int col_TestID = 2;
	public static final int col_UserName = 3;
	public static final int col_Password = 4;

	// TestCase Sheet Columns,

	public static final int col_TestCaseDesc = 3;
	public static final int col_Browser = 4;
	public static final int col_Enviornment = 5;
	public static final int col_RunMode = 6;
	public static final int col_Status = 7;
	public static final int col_Comments = 8;
	
	// Login Sheet Columns,
	
	public static final int col_NewUserName = 5;
	public static final int col_NewPassword = 6;
	

	// Add Employee test data columns
	public static final int col_firstName = 5;
	public static final int col_middleName = 6;
	public static final int col_lastName = 7;
	public static final int col_location = 8;
	public static final int col_EEORace = 9;
	public static final int col_Bloodgroup = 10;
	public static final int col_Hobby = 11;
	public static final int col_Region = 12;
	public static final int col_FTE = 13;
	public static final int col_Temp_Department = 14;
	public static final int col_employeeName = 15;
	public static final int col_employeeID = 16;

	// Edit User test data columns
	public static final int col_ExistingLocationName = 5;
	public static final int col_NewLocationName = 6;
	public static final int col_NewPhoneNo = 7;
	public static final int col_NewEmployeee = 8;

	// Add Vacancy test data columns
	public static final int col_Vacancy_name = 5;
	public static final int col_Vacancy_JobTitle = 6;
	public static final int col_Vacancy_location = 7;
	public static final int col_subUnit = 8;
	public static final int col_HiringManagers = 9;
	public static final int col_NoOfPositions = 10;

	// EditEmployee Cases TestData columns
	public static final int col_UserLastName = 5;
	public static final int col_DateOfBirth = 6;
	public static final int col_Nationality = 7;
	public static final int col_BloodGroup = 8;
	public static final int col_HubbyFirst = 9;
	public static final int col_HubbySecond = 10;
	public static final int col_EditEmployeeName = 11;
	public static final int col_EditEmployeeID = 12;

	// Apply Leave case Test data colomns
	public static final int col_leaveType = 5;
	public static final int col_leaveDateFrom = 6;
	public static final int col_leaveDateTo = 7;
	public static final int col_leaveDesc = 8;

	// Add User cases test data colomns
	public static final int col_AdminRole = 5;
	public static final int col_UserPassword = 6;
	public static final int col_UserID = 7;

	// DisciplinaryCases TestData columns

	public static final int col_caseName = 5;
	public static final int col_Desciption = 6;
	public static final int col_DueDate = 7;
	public static final int col_ActionStatus = 8;
	public static final int col_DisciplinaryAction = 9;
	public static final int col_EmpName = 10;
	public static final int col_OwnerName = 11;
	public static final int col_CreatedBy = 12;
	public static final int col_CreatedOn = 13;

	// Travel Request test data columns
	public static final int col_Main_Destination = 5;
	public static final int col_From_Date = 6;
	public static final int col_To_Date = 7;
	public static final int col_Expense_Type = 8;
	public static final int col_Paid_By = 9;
	public static final int col_Employee_Name = 10;
	public static final int col_Currency_Name = 11;
	public static final int col_Destination_Address = 12;
	public static final int col_Request_ID = 13;
	public static final int col_Request_Status = 14;

}
