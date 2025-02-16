import { Routes } from '@angular/router';
import { VeiculoListComponent } from './components/veiculo-list/veiculo-list.component';
import { VeiculoFormComponent } from './components/veiculo-form/veiculo-form.component';
import { StatsComponent } from './components/stats/stats.component';

export const routes: Routes = [
  { path: '', component: VeiculoListComponent },
  { path: 'novo', component: VeiculoFormComponent },
  { path: 'editar/:id', component: VeiculoFormComponent },
  { path: 'stats', component: StatsComponent },
  { path: '**', redirectTo: ''}
];