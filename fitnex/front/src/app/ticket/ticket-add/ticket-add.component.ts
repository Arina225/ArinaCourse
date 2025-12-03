import {Component, OnInit} from '@angular/core';
import {NavigateDirective} from "../../navigate.directive";
import {NgIf} from "@angular/common";
import {AuthService} from "../../auth/auth.service";
import {Router} from "@angular/router";
import {GlobalService} from "../../global.service";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {TicketService} from "../ticket.service";

@Component({
	selector: 'app-ticket-add',
	imports: [
		NavigateDirective,
		ReactiveFormsModule
	],
	templateUrl: './ticket-add.component.html',
	standalone: true
})

export class TicketAddComponent implements OnInit {

	ticketFormGroup = new FormGroup({
		name: new FormControl('', [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		price: new FormControl(null, [Validators.required, Validators.min(0.01), Validators.max(1000000)]),
	})

	img: any = null;
	file: any = null;

	constructor(
		private authService: AuthService,
		private router: Router,
		private global: GlobalService,
		private ticketService: TicketService,
	) {
	}

	ngOnInit(): void {
		this.authService.getUser().add(() => {
			if (this.global.role !== 'ADMIN') this.router.navigate(['/login']);
		})
	}

	changeImg(event: any) {
		this.img = event.target.files;
	}

	changeFile(event: any) {
		this.file = event.target.files;
	}

	checkSubmit(): boolean {
		if (this.img === null) return false;
		if (this.file === null) return false;
		return !this.ticketFormGroup.invalid;
	}

	save() {
		this.ticketService.save(this.ticketFormGroup.value, this.img, this.file);
	}

}
