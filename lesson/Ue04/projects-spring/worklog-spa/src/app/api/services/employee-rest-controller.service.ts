/* tslint:disable */
/* eslint-disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { RequestBuilder } from '../request-builder';
import { Observable } from 'rxjs';
import { map, filter } from 'rxjs/operators';

import { EmployeeDto } from '../models/employee-dto';

@Injectable({
  providedIn: 'root',
})
export class EmployeeRestControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation hello
   */
  static readonly HelloPath = '/workLog/hello';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `hello()` instead.
   *
   * This method doesn't expect any request body.
   */
  hello$Response(params?: {
  }): Observable<StrictHttpResponse<string>> {

    const rb = new RequestBuilder(this.rootUrl, EmployeeRestControllerService.HelloPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'text',
      accept: 'text/plain'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<string>;
      })
    );
  }

  /**
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `hello$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  hello(params?: {
  }): Observable<string> {

    return this.hello$Response(params).pipe(
      map((r: StrictHttpResponse<string>) => r.body as string)
    );
  }

  /**
   * Path part for operation getAllEmployees
   */
  static readonly GetAllEmployeesPath = '/workLog/employees';

  /**
   * Employee list.
   *
   * Returns a list of all stored employees.
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllEmployees()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllEmployees$Response(params?: {
  }): Observable<StrictHttpResponse<Array<EmployeeDto>>> {

    const rb = new RequestBuilder(this.rootUrl, EmployeeRestControllerService.GetAllEmployeesPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<EmployeeDto>>;
      })
    );
  }

  /**
   * Employee list.
   *
   * Returns a list of all stored employees.
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getAllEmployees$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllEmployees(params?: {
  }): Observable<Array<EmployeeDto>> {

    return this.getAllEmployees$Response(params).pipe(
      map((r: StrictHttpResponse<Array<EmployeeDto>>) => r.body as Array<EmployeeDto>)
    );
  }

  /**
   * Path part for operation getEmployeeById
   */
  static readonly GetEmployeeByIdPath = '/workLog/employees/{id}';

  /**
   * Employee data.
   *
   * Returns detailed data for a given employee.
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getEmployeeById()` instead.
   *
   * This method doesn't expect any request body.
   */
  getEmployeeById$Response(params: {
    id: number;
  }): Observable<StrictHttpResponse<EmployeeDto>> {

    const rb = new RequestBuilder(this.rootUrl, EmployeeRestControllerService.GetEmployeeByIdPath, 'get');
    if (params) {
      rb.path('id', params.id, {});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<EmployeeDto>;
      })
    );
  }

  /**
   * Employee data.
   *
   * Returns detailed data for a given employee.
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getEmployeeById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getEmployeeById(params: {
    id: number;
  }): Observable<EmployeeDto> {

    return this.getEmployeeById$Response(params).pipe(
      map((r: StrictHttpResponse<EmployeeDto>) => r.body as EmployeeDto)
    );
  }

}
