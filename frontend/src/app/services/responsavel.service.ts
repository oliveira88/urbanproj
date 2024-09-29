import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
@Injectable({
  providedIn: 'root',
})
export class ResponsavelService {
  apiUrl = environment.apiUrl;
  constructor(private http: HttpClient) {}

  getResponsaveis() {
    return this.http.get(`${this.apiUrl}/responsavel`);
  }

  getResponsavelById(id: number) {
    return this.http.get(`${this.apiUrl}/responsavel/${id}`);
  }

  createResponsavel(responsavel: any) {
    return this.http.post(`${this.apiUrl}/responsavel`, responsavel);
  }

  deleteResponsavel(id: number) {
    return this.http.delete(`${this.apiUrl}/responsavel/${id}`);
  }

  updateResponsavel(responsavel: any) {
    return this.http.put(
      `${this.apiUrl}/responsavel/${responsavel.id}`,
      responsavel
    );
  }
}
