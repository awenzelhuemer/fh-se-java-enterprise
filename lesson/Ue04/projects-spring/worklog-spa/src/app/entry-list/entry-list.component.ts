import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {EmployeeRestControllerService} from "../api/services/employee-rest-controller.service";
import {ActivatedRoute} from "@angular/router";
import {EmployeeDto} from "../api/models/employee-dto";
import {LogbookEntryDto} from "../api/models/logbook-entry-dto";
import {LogbookEntryRestControllerService} from "../api/services/logbook-entry-rest-controller.service";

@Component({
  selector: 'app-entry-list',
  templateUrl: './entry-list.component.html'
})
export class EntryListComponent implements OnInit {

  errorInfo: string | null = null;
  employee: EmployeeDto | null = null;
  entryList: LogbookEntryDto[] = [];

  constructor(
    private router: ActivatedRoute,
    private employeeService: EmployeeRestControllerService,
    private entryService: LogbookEntryRestControllerService) {
  }

  ngOnInit(): void {
    this.router.paramMap.subscribe(p => {
      const employeeId = Number(p.get("id"))
      this.employeeService.getEmployeeById({id: employeeId}).subscribe({
        next: (employee: EmployeeDto) => {
          this.employee = employee;
          this.refreshEntryList(employeeId);
          this.resetError();
        }
      });
    });

  }

  refreshEntryList(emplId: number): void {
    this.entryService.getLogbookEntriesForEmployee({id: emplId}).subscribe({
      next: (entries: LogbookEntryDto[]) => {
        this.entryList = entries;
        this.resetError()
      }
    })
  }

  private displayError(resp: HttpErrorResponse): void {
    this.errorInfo = `${resp.error.error} (${resp.error.status}): ${resp.error.message}.`;
  }

  private resetError(): void {
    this.errorInfo = null;
  }
}
