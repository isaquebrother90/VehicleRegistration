import { Component, OnInit } from '@angular/core';
import { VeiculoService } from '../../services/veiculo.service';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatListModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    FormsModule
  ],
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.css']
})
export class StatsComponent implements OnInit {
  naoVendidos: number = 0;
  distribuicaoDecada: string[] = [];
  distribuicaoFabricante: string[] = [];
  ultimaSemana: any[] = [];
  filtros = { 
    marca: '', 
    ano: undefined as number | undefined,
    cor: '' 
  };

  converterAno(valor: string): void {
    this.filtros.ano = valor ? parseInt(valor, 10) : undefined;
  }
  veiculosFiltrados: any[] = [];

  constructor(private veiculoService: VeiculoService) {}

  ngOnInit() {
    this.carregarEstatisticas();
  }
  
  carregarEstatisticas() {
    this.veiculoService.getNaoVendidos().subscribe({
      next: (quantidade) => this.naoVendidos = quantidade,
      error: (err) => console.error('Erro ao carregar não vendidos:', err)
    });
  
    this.veiculoService.getDistribuicaoDecada().subscribe({
      next: (decadas) => this.distribuicaoDecada = decadas,
      error: (err) => console.error('Erro ao carregar décadas:', err)
    });
  
    this.veiculoService.getDistribuicaoFabricante().subscribe({
      next: (fabricantes) => this.distribuicaoFabricante = fabricantes,
      error: (err) => console.error('Erro ao carregar fabricantes:', err)
    });
  
    this.veiculoService.getUltimaSemana().subscribe({
      next: (veiculos) => this.ultimaSemana = veiculos,
      error: (err) => console.error('Erro ao carregar última semana:', err)
    });
  }  

  aplicarFiltros() {
    const filtrosLimpos = {
      marca: this.filtros.marca || undefined, 
      ano: this.filtros.ano || undefined, 
      cor: this.filtros.cor || undefined
    };
  
    this.veiculoService.filtrarVeiculos(filtrosLimpos).subscribe({
      next: (veiculos) => this.veiculosFiltrados = veiculos,
      error: (err) => console.error('Erro ao filtrar:', err)
    });
  }
  
}