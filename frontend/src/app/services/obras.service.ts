import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { map, Observable } from 'rxjs';
import { ProjetoObra } from '../model';
@Injectable({
  providedIn: 'root',
})
export class ObrasService {
  apiUrl = environment.apiUrl;
  constructor(private http: HttpClient) {}

  getObras(): Observable<Array<ProjetoObra>> {
    return this.http.get<Array<ProjetoObra>>(`${this.apiUrl}/obras`);
  }

  getObraById(id: number) {
    return this.http.get(`${this.apiUrl}/obras/${id}`);
  }

  createObra(obra: any) {
    return this.http.post(`${this.apiUrl}/obras`, obra);
  }

  deleteObra(id: number) {
    return this.http.delete(`${this.apiUrl}/obras/${id}`);
  }

  updateObra(obra: any) {
    return this.http.put(`${this.apiUrl}/obras/${obra.id}`, obra);
  }

  obterStatus() {
    return this.http.get(`${this.apiUrl}/obras/status`);
  }
}
