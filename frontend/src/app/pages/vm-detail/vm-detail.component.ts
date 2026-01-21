import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { VmService, VmResponseDTO } from '../../services/vm.service';
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
  errorMsg = '';
  successMsg = '';

  vm?: VmResponseDTO;
  vmId!: number;

  ngOnInit() {
    this.vmId = Number(this.route.snapshot.paramMap.get('id'));
    this.load();
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

  setStatus(status: VmResponseDTO['status']) {
    if (!this.vm) return;

    this.vmService.updateStatus(this.vmId, status).subscribe({
      next: (updated) => {
        this.vm = updated;
        this.successMsg = `Status atualizado para ${status}.`;
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Erro ao atualizar status.';
      },
    });
  }
}
