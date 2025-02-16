import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckbox } from '@angular/material/checkbox';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { VeiculoService } from '../../services/veiculo.service';
import { Veiculo } from '../../models/veiculo.model';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatCheckboxModule } from '@angular/material/checkbox';

@Component({
  standalone: true,
  imports: [
    MatInputModule,
    MatCheckboxModule,
    ReactiveFormsModule,
    MatCardModule,
    MatSelectModule,
    MatFormFieldModule,
    CommonModule,
    MatCheckbox,
    MatSort,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatButtonModule,
    MatIconModule
  ],
  selector: 'app-veiculo-form',
  templateUrl: './veiculo-form.component.html',
  styleUrls: ['./veiculo-form.component.css']
})
export class VeiculoFormComponent implements OnInit {
  form!: FormGroup;

  currentYear = new Date().getFullYear();

  marcasValidas = ['Ford', 'Honda', 'Toyota', 'Volkswagen', 'Chevrolet'];

  constructor(
    private fb: FormBuilder,
    private service: VeiculoService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.criarFormulario();
  }

  private criarFormulario(): void {
    this.form = this.fb.group({
      id: [null],
      veiculo: ['', Validators.required],
      marca: ['', Validators.required],
      ano: [null, [
        Validators.required, 
        Validators.min(1900), 
        Validators.max(new Date().getFullYear())
      ]],
      descricao: [''],
      vendido: [false]
    });
  }

  cancelar(): void {
    this.router.navigate(['/']);
  }

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.service.getVeiculo(+id).subscribe(veiculo => {
        this.form.patchValue({
          ...veiculo,
          id: veiculo.id ?? null
        });
      });
    }
  }

  onSubmit() {
    if (this.form.valid) {
      const formValue = this.form.value;
      const veiculo: Veiculo = {
        id: formValue.id ?? undefined,
        veiculo: formValue.veiculo!,
        marca: formValue.marca!,
        ano: Number(formValue.ano),
        descricao: formValue.descricao ?? '',
        vendido: formValue.vendido ?? false
      };

      const operation = veiculo.id 
        ? this.service.updateVeiculo(veiculo.id, veiculo)
        : this.service.createVeiculo(veiculo);

      operation.subscribe(() => this.router.navigate(['/']));
    }
  }
}