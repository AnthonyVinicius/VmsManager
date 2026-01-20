import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { VmCreateComponent } from './pages/vm-create/vm-create.component';

export const routes: Routes = [

    {path:"login", component: LoginComponent},
    {path: "register", component: RegisterComponent},
    {path: "dashboard" , component: DashboardComponent},
    {path: "vmCreate" , component: VmCreateComponent},
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: '**', redirectTo: 'login' },
];
