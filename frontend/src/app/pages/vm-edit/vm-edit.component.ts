import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { VmService, VmResponseDTO, VmUpdateDTO } from '../../services/vm.service';
import { ButtonComponent } from "../../shared/ui/button/button.component";

@Component({
  selector: 'app-vm-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, ButtonComponent],
  templateUrl: './vm-edit.component.html',
  styleUrls: ['./vm-edit.component.css'],
})
export class VmEditComponent {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private vmService = inject(VmService);

  vmId!: number;
  vm?: VmResponseDTO;

  loading = false;
  saving = false;
  errorMsg = '';
  successMsg = '';

  form = this.fb.group({
    nome: ['', [Validators.required, Validators.minLength(5)]],
    cpu: [1, [Validators.required, Validators.min(1)]],
    memoria: [2, [Validators.required, Validators.min(2)]],
    disco: [50, [Validators.required, Validators.min(50)]],
  });

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
        this.form.patchValue({
          nome: data.nome,
          cpu: data.cpu,
          memoria: data.memoria,
          disco: data.disco,
        });
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Não foi possível carregar a VM para edição.';
        this.loading = false;
      },
    });
  }

  submit() {
    this.errorMsg = '';
    this.successMsg = '';

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const dto = this.form.getRawValue() as VmUpdateDTO;

    this.saving = true;
    this.vmService.update(this.vmId, dto).subscribe({
      next: () => {
        this.saving = false;
        this.successMsg = 'VM atualizada com sucesso!';
        setTimeout(() => this.router.navigate(['/vm', this.vmId, 'view']), 400);
      },
      error: (err) => {
        console.error(err);
        this.errorMsg = 'Erro ao atualizar VM. Verifique os dados.';
        this.saving = false;
      },
    });
  }
}
