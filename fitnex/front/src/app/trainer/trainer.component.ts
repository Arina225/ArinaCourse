import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";
import {GlobalService} from "../global.service";
import {UserService} from "../user/user.service";
import {NavigateDirective} from "../navigate.directive";

@Component({
	selector: 'app-trainer',
	imports: [
		NavigateDirective
	],
	templateUrl: './trainer.component.html',
	standalone: true
})
export class TrainerComponent implements OnInit {

	trainers: any[] = [];

	get trainersSorted() {
		let res = this.trainers;

		return res;
	}

	constructor(
		private authService: AuthService,
		private router: Router,
		private global: GlobalService,
		private userService: UserService,
	) {
	}

	get role() {
		return this.global.role;
	}

	ngOnInit(): void {
		this.authService.getUser()

		this.userService.getTrainers().subscribe({
			next: (res: any) => {
				this.trainers = res.data
				console.log(this.trainers)
			},
			error: (e: any) => console.log(e.error)
		})
	}

}
