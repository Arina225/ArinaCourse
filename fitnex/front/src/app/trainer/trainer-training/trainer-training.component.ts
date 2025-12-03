import {Component, OnInit} from '@angular/core';
import {NavigateDirective} from "../../navigate.directive";
import {AuthService} from "../../auth/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {GlobalService} from "../../global.service";
import {TrainingService} from "../../training/training.service";
import {TrainingOrderingService} from "../../training-ordering/training-ordering.service";
import {AlertService} from "../../alert/alert.service";
import {NgIf} from "@angular/common";

@Component({
	selector: 'app-trainer-training',
	imports: [
		NavigateDirective,
		NgIf
	],
	templateUrl: './trainer-training.component.html',
	standalone: true
})
export class TrainerTrainingComponent implements OnInit {

	id: number = 0;

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
		private activatedRoute: ActivatedRoute,
		private orderingService: TrainingOrderingService,
		private alert: AlertService,
	) {
	}

	get role() {
		return this.global.role;
	}

	get userid() {
		return this.global.userid;
	}

	ngOnInit(): void {
		this.authService.getUser()

		this.activatedRoute.queryParams.subscribe(params => {
			this.id = params['id'];
			this.trainingService.findAllByTrainerId(params['id']);
		})
		this.trainingService.trainingSubject.subscribe(value => {
			this.trainings = value.trainings;
		})
	}

	checkOrdering(training: any): boolean {
		for (let i of training.orderingsClientsId) if (i === this.userid) return false;
		return true;
	}

	ordering(id: number) {
		this.orderingService.save(id).subscribe({
			next: () => {
				this.alert.showAlertMessage("Заявка оформлена")
				this.trainingService.findAllByTrainerId(this.id);
			},
			error: (e: any) => {
				console.log(e.error);
				this.alert.showAlertMessage(e.error.message);
			}
		})
	}

}
