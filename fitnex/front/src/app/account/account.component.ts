import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";
import {GlobalService} from "../global.service";
import {UserService} from "../user/user.service";
import {AccountService} from "./account.service";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {EnumService} from "../enum.service";
import {KeyValuePipe, NgForOf, NgIf} from "@angular/common";
import {AlertService} from "../alert/alert.service";
import {TicketOrderingService} from "../ticket-ordering/ticket-ordering.service";
import {NavigateDirective} from "../navigate.directive";
import {log} from "@angular-devkit/build-angular/src/builders/ssr-dev-server";
import {TrainingOrderingService} from "../training-ordering/training-ordering.service";

@Component({
	selector: 'app-account',
	imports: [
		NgIf,
		ReactiveFormsModule,
		KeyValuePipe,
		NgForOf,
		NavigateDirective
	],
	templateUrl: './account.component.html',
	standalone: true
})
export class AccountComponent implements OnInit {

	accountFormGroup = new FormGroup({
		fio: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		tel: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		date: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		educate: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		speciality: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
	});

	img: string = "";
	gender: string = "";
	ageCategory: string = "";

	genders: any[] = [];
	ageCategories: any[] = [];

	ticketOrderings: any[] = [];
	trainingOrderings: any[] = [];

	constructor(
		private authService: AuthService,
		private router: Router,
		private global: GlobalService,
		private userService: UserService,
		private accountService: AccountService,
		private enumService: EnumService,
		private alert: AlertService,
		private ticketOrderingService: TicketOrderingService,
		private trainingOrderingService: TrainingOrderingService,
	) {
	}

	get role() {
		return this.global.role;
	}

	private error(e: any) {
		console.log(e.error);
		this.alert.showAlertMessage(e.error.message);
	}

	ngOnInit(): void {
		this.authService.getUser().add(() => {
			if (this.role !== 'TRAINER' && this.role !== 'CLIENT') this.router.navigate(['/login']);
		})

		if (this.role === 'CLIENT') {
			this.enumService.enumSubject.subscribe(value => {
				this.genders = value.genders;
				this.ageCategories = value.ageCategories;
			})
			this.enumService.getGenders()
			this.enumService.getAgeCategories()

			this.ticketOrderingService.orderingSubject.subscribe(value => {
				this.ticketOrderings = value.orderings;
			})
			this.ticketOrderingService.findAllMy();

			this.trainingOrderingService.orderingSubject.subscribe(value => {
				this.trainingOrderings = value.orderings;
			})
			this.trainingOrderingService.findAllMy();
		}

		this.userService.find().subscribe({
			next: (res: any) => {
				let profile = res.data.profile;
				this.accountFormGroup.setValue({
					fio: profile.fio,
					tel: profile.tel,
					date: profile.date,
					educate: profile.educate,
					speciality: profile.speciality,
				})
				this.img = profile.img;
				this.gender = profile.gender;
				this.ageCategory = profile.ageCategory;
			},
			error: (e: any) => {
				console.log(e.error)
				this.router.navigate(['/login']);
			}
		})
	}

	changeImg(event: any) {
		this.accountService.updateImg(event.target.files).subscribe({
			next: (res: any) => this.img = res.data.img,
			error: (e: any) => this.error(e),
		})
	}

	changeGender(event: any) {
		this.gender = event.target.value;
	}

	changeAgeCategory(event: any) {
		this.ageCategory = event.target.value;
	}

	checkSubmit(): boolean {
		if (this.gender === '') return false;
		if (this.ageCategory === '') return false;
		return !this.accountFormGroup.invalid;
	}

	update() {
		this.accountService.update(this.accountFormGroup.value, this.gender, this.ageCategory).subscribe({
			next: () => this.alert.showAlertMessage("Данные обновлены"),
			error: (e: any) => this.error(e),
		});
	}

}
