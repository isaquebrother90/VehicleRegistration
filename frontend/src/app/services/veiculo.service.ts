import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Veiculo } from '../models/veiculo.model';
import { tap, catchError } from 'rxjs/operators';

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  last: boolean;
  first: boolean;
  empty: boolean;
} 

@Injectable({
  providedIn: 'root'
})
export class VeiculoService {
  private apiUrl = 'http://localhost:8080/veiculos';

  constructor(private http: HttpClient) { }

  getVeiculosPaginados(page: number = 0, size: number = 10): Observable<PaginatedResponse<Veiculo>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<PaginatedResponse<Veiculo>>(this.apiUrl, { params });
  }

  getVeiculo(id: number): Observable<Veiculo> {
    return this.http.get<Veiculo>(`${this.apiUrl}/${id}`);
  }

  createVeiculo(veiculo: Veiculo): Observable<Veiculo> {
    return this.http.post<Veiculo>(this.apiUrl, veiculo);
  }

  updateVeiculo(id: number, veiculo: Veiculo): Observable<Veiculo> {
    return this.http.put<Veiculo>(`${this.apiUrl}/${id}`, veiculo);
  }

  patchVeiculo(id: number, updates: any): Observable<Veiculo> {
    return this.http.patch<Veiculo>(`${this.apiUrl}/${id}`, updates);
  }

deleteVeiculo(id: number): Observable<void> {
  console.log('Enviando ID para exclusão:', id, typeof id);
  return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
    tap(() => console.log('Exclusão bem-sucedida para ID:', id)),
    catchError((erro: any) => {
      console.error('Erro na requisição:', erro);
      return throwError(() => erro);
    })
  );
}

  // Métodos de estatísticas
  getNaoVendidos(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/nao-vendidos`);
  }

  getDistribuicaoDecada(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/distribuicao-decada`);
  }

  getDistribuicaoFabricante(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/distribuicao-fabricante`);
  }

  getUltimaSemana(): Observable<Veiculo[]> {
    return this.http.get<Veiculo[]>(`${this.apiUrl}/ultima-semana`);
  }

  filtrarVeiculos(filtros: { 
    marca?: string; 
    ano?: number | null;
    cor?: string 
  }): Observable<Veiculo[]> {
    let params = new HttpParams();
    if (filtros.marca) params = params.set('marca', filtros.marca);
    if (filtros.ano) params = params.set('ano', filtros.ano);
    if (filtros.cor) params = params.set('cor', filtros.cor);

    return this.http.get<Veiculo[]>(`${this.apiUrl}/filtro`, { params });
  }
}