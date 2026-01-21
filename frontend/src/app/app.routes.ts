import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { VmCreateComponent } from './pages/vm-create/vm-create.component';
import { VmDetailComponent } from './pages/vm-detail/vm-detail.component';
import { VmEditComponent } from './pages/vm-edit/vm-edit.component';
export const routes: Routes = [

    {path:"login", component: LoginComponent},
    {path: "register", component: RegisterComponent},
    {path: "dashboard" , component: DashboardComponent},
    {path: "vmCreate" , component: VmCreateComponent},
    { path: 'vm/:id/view', component: VmDetailComponent },
    { path: 'vm/:id/edit', component: VmEditComponent },
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: '**', redirectTo: 'login' },
];
