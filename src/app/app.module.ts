import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { EmployeeSingleComponent } from './employee/employee-single/employee-single.component'
import { EmployeeListComponent } from './employee/employee-list/employee-list.component'

import { HttpClientModule} from '@angular/common/http'; 

const routes: Routes = [
	{ path: '', pathMatch: 'full', redirectTo: 'home' },	// No URI specification is redirected to /home
	{ path: 'home', component: HomeComponent },
	{ path: 'employees', component: EmployeeListComponent },
	{ path: 'employee', component: EmployeeSingleComponent }
];

@NgModule({
	declarations: [
		AppComponent,
		HomeComponent,
		EmployeeListComponent,
		EmployeeSingleComponent
	],
	imports: [
		RouterModule.forRoot(routes),	// Needed for navigation
		BrowserModule,
		AppRoutingModule,
		FormsModule,
		ReactiveFormsModule,

		HttpClientModule,
	],
	providers: [],
	bootstrap: [AppComponent]
})
export class AppModule { }
