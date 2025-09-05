import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../../auth/auth.service';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})

export class UserService{
    constructor(private http: HttpClient,private authService: AuthService) {}

    private getAllUserUrl='http://localhost:8080/admin/users'

    private baseUrl='http://localhost:8080/admin/user';


    private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getAllUsers():Observable<User[]>{
    return this.http.get<User[]>(`${this.getAllUserUrl}`, { headers: this.getHeaders() });
  }

  updateUser(userId: number, role: string): Observable<string> {
  return this.http.put<string>(
    `${this.baseUrl}/${userId}/role?role=${role}`,
    {}, // empty body
    { headers: this.getHeaders() }
  );
}

deleteUser(userId: number): Observable<string> {
  return this.http.delete<string>(
    `${this.baseUrl}/${userId}`,
    { headers: this.getHeaders() }
  );
}

getUserById(userId:number): Observable<User>{
    return this.http.get<User>(`${this.baseUrl}/${userId}`, { headers: this.getHeaders() });
}

}


