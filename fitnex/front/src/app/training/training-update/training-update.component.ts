import {Component, OnInit} from '@angular/core';
import {NavigateDirective} from "../../navigate.directive";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {AuthService} from "../../auth/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {GlobalService} from "../../global.service";
import {TrainingService} from "../training.service";

@Component({
	selector: 'app-training-update',
	imports: [
		NavigateDirective,
		ReactiveFormsModule
	],
	templateUrl: './training-update.component.html',
	standalone: true
})

export class TrainingUpdateComponent implements OnInit {

	id: number = 0;

	trainingFormGroup = new FormGroup({
		name: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		date: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		duration: new FormControl(null, [Validators.required, Validators.min(1), Validators.max(4)]),
		places: new FormControl(null, [Validators.required, Validators.min(1), Validators.max(10)]),
		price: new FormControl(null, [Validators.required, Validators.min(0.01), Validators.max(1000000)]),
	})

	constructor(
		private authService: AuthService,
		private router: Router,
		private global: GlobalService,
		private trainingService: TrainingService,
		private activatedRoute: ActivatedRoute,
	) {
	}

	ngOnInit(): void {
		this.authService.getUser().add(() => {
			if (this.global.role !== 'TRAINER') this.router.navigate(['/login']);
		})

		this.activatedRoute.queryParams.subscribe(params => {
			this.id = params['id']
			this.trainingService.find(params['id']).subscribe({
				next: (res: any) => {
					this.trainingFormGroup.setValue({
						name: res.data.name,
						date: res.data.date,
						duration: res.data.duration,
						places: res.data.places,
						price: res.data.price,
					})
				},
				error: (e: any) => {
					console.log(e.error)
					if (e.error.code === 404)
						this.router.navigate(['error'], {queryParams: {message: e.error.message}});
					else this.router.navigate(['login']);
				}
			})
		})
	}

	update() {
		this.trainingService.update(this.id, this.trainingFormGroup.value);
	}

}
