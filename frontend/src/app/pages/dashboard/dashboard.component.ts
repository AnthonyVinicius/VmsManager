import { Component, ElementRef, ViewChild, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ButtonComponent } from "../../shared/ui/button/button.component";
import { VmService, VmResponseDTO } from '../../services/vm.service';

import {
  Chart,
  BarController,
  BarElement,
  CategoryScale,
  LinearScale,
  Tooltip,
  Legend,
  PieController,
  ArcElement
} from 'chart.js';
Chart.register(
  BarController, BarElement, CategoryScale, LinearScale, Tooltip, Legend,
  PieController, ArcElement
);
@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, ButtonComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent {
  private vmService = inject(VmService);

  @ViewChild('statusBarCanvas') statusBarCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('capacityPieCanvas') capacityPieCanvas!: ElementRef<HTMLCanvasElement>;

  private statusChart?: Chart;
  private capacityChart?: Chart;

  loading = false;
  deletingId: number | null = null;
  errorMsg = '';

  vms: VmResponseDTO[] = [];

  totals = { total: 0, START: 0, STOP: 0, SUSPEND: 0 };
  readonly maxVms = 5;

  ngOnInit() {
    this.load();
  }

  load() {
    this.loading = true;
    this.errorMsg = '';

    this.vmService.getAll().subscribe({
      next: (data) => {
        this.vms = data ?? [];
        this.computeTotals();
        this.loading = false;

        queueMicrotask(() => this.renderCharts());
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Não foi possível carregar as VMs.';
        this.loading = false;
      },
    });
  }

  deleteVm(id: number, ev?: Event) {

    ev?.stopPropagation();
    ev?.preventDefault();

    const ok = confirm('Deseja deletar esta VM?');
    if (!ok) return;

    this.deletingId = id;
    this.errorMsg = '';

    this.vmService.delete(id).subscribe({
      next: () => {
        this.deletingId = null;
        this.load();
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Erro ao deletar VM.';
        this.deletingId = null;
      },
    });
  }

  private computeTotals() {
    const counts = { total: 0, START: 0, STOP: 0, SUSPEND: 0 };
    counts.total = this.vms.length;

    for (const vm of this.vms) {
      if (vm.status === 'START') counts.START++;
      else if (vm.status === 'SUSPEND') counts.SUSPEND++;
      else if (vm.status === 'STOP') counts.STOP++;

    }

    this.totals = counts;
  }

  get latestVms(): VmResponseDTO[] {
    return [...this.vms]
      .sort((a, b) => (a.dataCriacao < b.dataCriacao ? 1 : -1))
      .slice(0, 5);
  }

  private renderCharts() {
    this.statusChart?.destroy();
    this.capacityChart?.destroy();

    const COLORS = {
      green: '#22c55e',
      red: '#ef4444',
      orange: '#f59e0b',
      blue: '#3b82f6',
      slate: '#64748b',
      grid: '#e2e8f0',
      light: '#e5e7eb',
    };

    const barCtx = this.statusBarCanvas?.nativeElement?.getContext('2d');
    if (barCtx) {
      this.statusChart = new Chart(barCtx, {
        type: 'bar',
        data: {
          labels: ['START', 'STOP', 'SUSPEND'],
          datasets: [
            {
              label: 'VMs',
              data: [this.totals.START, this.totals.STOP, this.totals.SUSPEND],
              backgroundColor: [COLORS.green, COLORS.red, COLORS.orange],
              borderWidth: 0,
              borderRadius: 10,
              borderSkipped: false,
              maxBarThickness: 42,
            },
          ],
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: { display: false },
            tooltip: { enabled: true },
          },
          scales: {
            y: {
              beginAtZero: true,
              ticks: { precision: 0, color: COLORS.slate },
              grid: { color: COLORS.grid },
            },
            x: {
              ticks: { color: COLORS.slate },
              grid: { display: false },
            },
          },
        },
      });
    }

    const used = Math.min(this.totals.total, this.maxVms);
    const remaining = Math.max(this.maxVms - used, 0);

    const pieCtx = this.capacityPieCanvas?.nativeElement?.getContext('2d');
    if (pieCtx) {
      this.capacityChart = new Chart(pieCtx, {
        type: 'doughnut',
        data: {
          labels: ['Usadas', 'Disponíveis'],
          datasets: [
            {
              data: [used, remaining],
              backgroundColor: [COLORS.blue, COLORS.light],
              borderWidth: 0,
              hoverOffset: 6,
            },
          ],
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          cutout: '72%',
          plugins: {
            legend: {
              position: 'bottom',
              labels: {
                color: COLORS.slate,
                boxWidth: 12,
                boxHeight: 12,
              },
            },
            tooltip: { enabled: true },
          },
        },
      });
    }
  }

}
