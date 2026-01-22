import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { VmService, VmResponseDTO, VmTaskHistoryResponseDTO } from '../../services/vm.service';
import { ButtonComponent } from "../../shared/ui/button/button.component";

@Component({
  selector: 'app-vm-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, ButtonComponent],
  templateUrl: './vm-detail.component.html',
  styleUrls: ['./vm-detail.component.css'],
})
export class VmDetailComponent {
  private route = inject(ActivatedRoute);
  private vmService = inject(VmService);

  loading = false;
  historyLoading = false;

  errorMsg = '';
  successMsg = '';

  vm?: VmResponseDTO;
  vmId!: number;

  history: VmTaskHistoryResponseDTO[] = [];
  historyError = '';

  ngOnInit() {
    this.vmId = Number(this.route.snapshot.paramMap.get('id'));
    this.load();
    this.loadHistory();
  }

  load() {
    this.loading = true;
    this.errorMsg = '';
    this.successMsg = '';

    this.vmService.getById(this.vmId).subscribe({
      next: (data) => {
        this.vm = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Não foi possível carregar a VM.';
        this.loading = false;
      },
    });
  }

  loadHistory() {
    this.historyLoading = true;
    this.historyError = '';

    this.vmService.getHistoryByVm(this.vmId).subscribe({
      next: (items) => {
        this.history = items;
        this.historyLoading = false;
      },
      error: (err) => {
        console.error(err);
        this.historyError = 'Não foi possível carregar o histórico.';
        this.historyLoading = false;
      },
    });
  }

  setStatus(status: VmResponseDTO['status']) {
    if (!this.vm) return;

    this.vmService.updateStatus(this.vmId, status).subscribe({
      next: (updated) => {
        this.vm = updated;
        this.successMsg = `Status atualizado para ${status}.`;
        this.loadHistory();
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Erro ao atualizar status.';
      },
    });
  }
}
