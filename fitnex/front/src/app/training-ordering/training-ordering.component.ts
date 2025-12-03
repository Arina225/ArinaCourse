import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";
import {GlobalService} from "../global.service";
import {TrainingOrderingService} from "./training-ordering.service";
import {NavigateDirective} from "../navigate.directive";
import {NgIf} from "@angular/common";

@Component({
	selector: 'app-training-ordering',
	imports: [
		NgIf
	],
	templateUrl: './training-ordering.component.html',
	standalone: true
})

export class TrainingOrderingComponent implements OnInit {

	orderings: any[] = [];

	constructor(
		private authService: AuthService,
		private router: Router,
		private global: GlobalService,
		private orderingService: TrainingOrderingService,
	) {
	}

	ngOnInit(): void {
		this.authService.getUser().add(() => {
			if (this.global.role !== 'TRAINER') this.router.navigate(['/login']);
		})

		this.orderingService.orderingSubject.subscribe(value => {
			this.orderings = value.orderings;
		})
		this.orderingService.findAll();
	}

	confirmed(id: number) {
		this.orderingService.confirmed(id).add(() => {
			this.orderingService.findAll();
		});
	}

	rejected(id: number) {
		this.orderingService.rejected(id).add(() => {
			this.orderingService.findAll();
		});
	}

}
