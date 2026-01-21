import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment.development';
import { tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = `${environment.apiUrl}/api/auth`;

  constructor(private http: HttpClient) {}

  login(email: string, senha: string) {
    return this.http
      .post<{ token: string }>(`${this.baseUrl}/login`, { email, senha })
      .pipe(tap(res => localStorage.setItem('token', res.token)));
  }

  logout() { localStorage.removeItem('token'); }
  get token() { return localStorage.getItem('token'); }
}
