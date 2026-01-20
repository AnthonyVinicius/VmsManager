import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ButtonComponent } from "../../shared/ui/button/button.component";


@Component({
  selector: 'app-vm-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, ButtonComponent],
  templateUrl: './vm-create.component.html',
  styleUrls: ['./vm-create.component.css']
})
export class VmCreateComponent {
  private fb = inject(FormBuilder);
  
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
}
