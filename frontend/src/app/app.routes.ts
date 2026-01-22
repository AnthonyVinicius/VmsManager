import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { VmCreateComponent } from './pages/vm-create/vm-create.component';
import { VmDetailComponent } from './pages/vm-detail/vm-detail.component';
import { VmEditComponent } from './pages/vm-edit/vm-edit.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [

    { path: "login", component: LoginComponent },
    { path: "register", component: RegisterComponent },
    { path: "dashboard", component: DashboardComponent, canActivate: [AuthGuard] },
    { path: "vmCreate", component: VmCreateComponent, canActivate: [AuthGuard] },
    { path: "vm/:id/view", component: VmDetailComponent, canActivate: [AuthGuard] },
    { path: "vm/:id/edit", component: VmEditComponent, canActivate: [AuthGuard] },
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: '**', redirectTo: 'login' },
];
