import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment.development';

export interface UserCreateDTO {
  nome: string;
  email: string;
  senha: string;
}

@Injectable({ providedIn: 'root' })
export class UserService {
  private baseUrl = `${environment.apiUrl}/api/users`;

  constructor(private http: HttpClient) {}

  register(dto: UserCreateDTO) {
    return this.http.post(this.baseUrl, dto);
  }
}
