import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ButtonComponent } from "../../shared/ui/button/button.component";
import { VmService, VmCreateDTO } from '../../services/vm.service';


@Component({
  selector: 'app-vm-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, ButtonComponent],
  templateUrl: './vm-create.component.html',
  styleUrls: ['./vm-create.component.css']
})
export class VmCreateComponent {
  private fb = inject(FormBuilder);
  private vmService = inject(VmService);
  private router = inject(Router);

  loading = false;
  errorMsg = '';
  successMsg = '';

  form = this.fb.group({
    nome: ['', [Validators.required, Validators.minLength(5)]],
    cpu: [1, [Validators.required, Validators.min(1)]],
    memoria: [1, [Validators.required, Validators.min(1)]],
    disco: [1, [Validators.required, Validators.min(1)]],
    status: 'STOP'
  });

submit() {
  this.errorMsg = '';
  this.successMsg = '';

  if (this.form.invalid) {
    this.form.markAllAsTouched();
    return;
  }

  const dto = this.form.getRawValue() as VmCreateDTO;

  this.loading = true;
  this.vmService.create(dto).subscribe({
    next: () => {
      this.loading = false;
      this.successMsg = 'VM criada com sucesso!';
      setTimeout(() => this.router.navigate(['/dashboard']), 500);
    },
    error: (err) => {
      this.loading = false;
      this.errorMsg = 'Erro ao criar VM. Verifique os dados.';
      console.error(err);
    }
  });
}

}