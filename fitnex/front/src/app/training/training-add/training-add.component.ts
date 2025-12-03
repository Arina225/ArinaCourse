import {Component, OnInit} from '@angular/core';
import {NavigateDirective} from "../../navigate.directive";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";
import {GlobalService} from "../../global.service";
import {TrainingService} from "../training.service";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";

@Component({
	selector: 'app-training-add',
	imports: [
		NavigateDirective,
		ReactiveFormsModule
	],
	templateUrl: './training-add.component.html',
	standalone: true
})

export class TrainingAddComponent implements OnInit {

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
	) {
	}

	ngOnInit(): void {
		this.authService.getUser().add(() => {
			if (this.global.role !== 'TRAINER') this.router.navigate(['/login']);
		})
	}

	save() {
		this.trainingService.save(this.trainingFormGroup.value);
	}

}
