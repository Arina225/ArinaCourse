import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";
import {GlobalService} from "../global.service";
import {NavigateDirective} from "../navigate.directive";
import {ReactiveFormsModule} from "@angular/forms";
import {TrainingService} from "./training.service";

@Component({
	selector: 'app-training',
	imports: [
		NavigateDirective,
		ReactiveFormsModule
	],
	templateUrl: './training.component.html',
	standalone: true
})

export class TrainingComponent implements OnInit {

	trainings: any[] = [];

	get trainingsSorted() {
		let res = this.trainings;

		return res;
	}

	constructor(
		private authService: AuthService,
		private router: Router,
		private global: GlobalService,
		private trainingService: TrainingService,
	) {
	}

	ngOnInit(): void {
		this.authService.getUser().add(() => {
			if (this.global.role !== 'TRAINER') this.router.navigate(['/login']);
		})

		this.trainingService.trainingSubject.subscribe(value => {
			this.trainings = value.trainings;
		})
		this.trainingService.findAllMy();
	}

	delete(id: number) {
		this.trainingService.delete(id);
	}
}
