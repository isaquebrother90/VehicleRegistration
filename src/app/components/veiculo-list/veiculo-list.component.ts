import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { VeiculoService, PaginatedResponse  } from '../../services/veiculo.service';
import { Veiculo } from '../../models/veiculo.model';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './veiculo-list.component.html',
  selector: 'app-veiculo-list',
  styleUrls: ['./veiculo-list.component.css']
})

export class VeiculoListComponent implements AfterViewInit {
  displayedColumns: string[] = ['veiculo', 'marca', 'ano', 'vendido', 'actions'];
  dataSource = new MatTableDataSource<Veiculo>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private service: VeiculoService,
    private router: Router
  ) {}

  ngAfterViewInit() {
    this.loadVeiculos();
  }

  loadVeiculos() {
    this.service.getVeiculosPaginados().subscribe({
      next: (response) => {
        
        this.dataSource.data = response.content.map(veiculo => {
          const idConvertido = Number(veiculo.id);
          if (isNaN(idConvertido)) {
            console.error('ID inválido vindo da API:', veiculo.id);
            throw new Error('Dados corrompidos da API');
          }
          return { ...veiculo, id: idConvertido };
        });
      }
    });
  }

  criar(id: number): void {
    this.router.navigate(['/novo']);
  }

  editar(id: number): void {
    this.router.navigate(['/editar', id]);
  }

  excluir(id: any): void {
  if (id === null || id === undefined || id === '') {
    console.error('ID indefinido:', id);
    alert('Erro interno: ID não recebido');
    return;
  }

  const idNumerico = Number(id);
  
  if (isNaN(idNumerico) || idNumerico <= 0) {
    console.error('ID inválido:', id);
    alert('ID do veículo está corrompido');
    return;
  }

  if (confirm('Tem certeza que deseja excluir?')) {
    this.service.deleteVeiculo(idNumerico).subscribe({
      next: () => {
        this.loadVeiculos();
        alert('Excluído com sucesso!');
      },
      error: (erro) => {
        console.error('Erro completo:', erro);
        alert(`Erro na exclusão: ${erro.status} - ${erro.error?.message || 'Erro desconhecido'}`);
      }
    });
  }
  }
}