import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ButtonComponent } from '../../shared/ui/button/button.component';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, ButtonComponent],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  private fb = inject(FormBuilder);
  private userService = inject(UserService);
  private router = inject(Router);

  loading = false;
  errorMsg = '';
  successMsg = '';

  form = this.fb.group({
    nome: ['', [Validators.required, Validators.minLength(5)]],
    email: ['', [Validators.required, Validators.email]],
    senha: ['', [Validators.required, Validators.minLength(6)]]
  });

  submit() {
    this.errorMsg = '';
    this.successMsg = '';

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const dto = this.form.getRawValue() as {
      nome: string;
      email: string;
      senha: string;
    };

    this.loading = true;
    this.userService.register(dto).subscribe({
      next: () => {
        this.loading = false;
        this.successMsg = 'Cadastro realizado! Agora faça login.';
        setTimeout(() => this.router.navigate(['/login']), 600);
      },
      error: (err) => {
        this.loading = false;
        if (err?.status === 409) {
          this.errorMsg = 'Esse email já está cadastrado.';
        } else {
          this.errorMsg = 'Erro ao cadastrar. Verifique os dados.';
        }
        console.error(err);
      }
    });
  }
}

