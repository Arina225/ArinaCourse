import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NavigateDirective} from "../../navigate.directive";
import {AuthService} from "../../auth/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {GlobalService} from "../../global.service";
import {TicketService} from "../ticket.service";

@Component({
	selector: 'app-ticket-update',
	imports: [
		FormsModule,
		NavigateDirective,
		ReactiveFormsModule
	],
	templateUrl: './ticket-update.component.html',
	standalone: true
})

export class TicketUpdateComponent implements OnInit {

	id: number = 0;

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
		private activatedRoute: ActivatedRoute,
	) {
	}

	ngOnInit(): void {
		this.authService.getUser().add(() => {
			if (this.global.role !== 'ADMIN') this.router.navigate(['/login']);
		})

		this.activatedRoute.queryParams.subscribe(params => {
			this.id = params['id'];
			this.ticketService.find(params['id']).subscribe({
				next: (res: any) => {
					this.ticketFormGroup.setValue({
						name: res.data.name,
						price: res.data.price,
					});
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

	changeImg(event: any) {
		this.img = event.target.files;
	}

	changeFile(event: any) {
		this.file = event.target.files;
	}

	checkSubmit(): boolean {
		return !this.ticketFormGroup.invalid;
	}

	update() {
		this.ticketService.update(this.id, this.ticketFormGroup.value, this.img, this.file);
	}

}
