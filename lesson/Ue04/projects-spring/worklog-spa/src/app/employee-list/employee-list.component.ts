import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {EmployeeDto} from "../api/models/employee-dto";
import {EmployeeRestControllerService} from "../api/services/employee-rest-controller.service";

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  errorInfo: string | null = null;
  employeeList: EmployeeDto[] = [];

  constructor(private employeeService: EmployeeRestControllerService) { }

  ngOnInit(): void {
    this.employeeService.getAllEmployees().subscribe(employees => {
      this.employeeList = employees;
      this.resetError();
    });
  }

  private displayError(resp: HttpErrorResponse): void {
    this.errorInfo = `${resp.error.error} (${resp.error.status}): ${resp.error.message}.`;
  }

  private resetError(): void {
    this.errorInfo = null;
  }
}
