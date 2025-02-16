import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { VeiculoListComponent } from './components/veiculo-list/veiculo-list.component';
import { StatsComponent } from './components/stats/stats.component';

@Component({
  selector: 'app-root',
  imports: [VeiculoListComponent, StatsComponent, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'veiculos-frontend';
}
