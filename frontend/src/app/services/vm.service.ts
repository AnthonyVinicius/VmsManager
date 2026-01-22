import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment.development';
import { Observable } from 'rxjs';

export interface VmResponseDTO {
  id: number;
  nome: string;
  cpu: number;
  memoria: number;
  disco: number;
  status: 'START' | 'STOP' | 'SUSPEND';
  dataCriacao: string;
}

export interface VmCreateDTO {
  nome: string;
  cpu: number;
  memoria: number;
  disco: number;
  status: 'STOP';
}

export type VmTaskHistoryResponseDTO = {
  usuario: string;
  dataHora: string; 
  nomeMaquina: string;
  acao: string;
  detalhes?: string;
};

export interface VmUpdateDTO {
  nome: string;
  cpu: number;
  memoria: number;
  disco: number;
}

@Injectable({ providedIn: 'root' })
export class VmService {
  private baseUrl = `${environment.apiUrl}/api/vm`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<VmResponseDTO[]> {
    return this.http.get<VmResponseDTO[]>(this.baseUrl);
  }

  getById(id: number): Observable<VmResponseDTO> {
    return this.http.get<VmResponseDTO>(`${this.baseUrl}/${id}`);
  }

  create(dto: VmCreateDTO): Observable<VmResponseDTO> {
    return this.http.post<VmResponseDTO>(this.baseUrl, dto);
  }

  update(id: number, dto: VmUpdateDTO): Observable<VmResponseDTO> {
    return this.http.put<VmResponseDTO>(`${this.baseUrl}/${id}`, dto);
  }

  updateStatus(id: number, status: VmResponseDTO['status']): Observable<VmResponseDTO> {
    return this.http.patch<VmResponseDTO>(`${this.baseUrl}/${id}/status`, { status });
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  getHistory(): Observable<VmTaskHistoryResponseDTO[]> {
    return this.http.get<VmTaskHistoryResponseDTO[]>(`${this.baseUrl}/history`);
  }

  getHistoryByVm(id: number): Observable<VmTaskHistoryResponseDTO[]> {
    return this.http.get<VmTaskHistoryResponseDTO[]>(`${this.baseUrl}/${id}/history`);
  }
}

