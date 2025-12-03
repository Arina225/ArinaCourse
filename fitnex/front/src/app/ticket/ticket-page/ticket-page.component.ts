import {Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NavigateDirective} from "../../navigate.directive";
import {AuthService} from "../../auth/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {GlobalService} from "../../global.service";
import {TicketService} from "../ticket.service";
import {NgIf} from "@angular/common";
import {TicketOrderingService} from "../../ticket-ordering/ticket-ordering.service";
import {AlertService} from "../../alert/alert.service";

@Component({
	selector: 'app-ticket-page',
	imports: [
		FormsModule,
		NavigateDirective,
		ReactiveFormsModule,
		NgIf
	],
	templateUrl: './ticket-page.component.html',
	standalone: true
})
export class TicketPageComponent implements OnInit {

	ticket: any = {
		name: '',
	}

	constructor(
		private authService: AuthService,
		private router: Router,
		private global: GlobalService,
		private activatedRoute: ActivatedRoute,
		private ticketService: TicketService,
		private orderingService: TicketOrderingService,
		private alert: AlertService,
	) {
	}

	get role() {
		return this.global.role;
	}

	ngOnInit(): void {
		this.authService.getUser()

		this.activatedRoute.queryParams.subscribe(params => {
			this.ticketService.find(params['id']).subscribe({
				next: (res: any) => this.ticket = res.data,
				error: (e: any) => {
					console.log(e.error)
					if (e.error.code === 404)
						this.router.navigate(['error'], {queryParams: {message: e.error.message}});
					else this.router.navigate(['login']);
				}
			})
		})

	}

	ordering() {
		this.orderingService.save(this.ticket.id).subscribe({
			next: () => this.alert.showAlertMessage("Заявка оформлена"),
			error: (e) => {
				console.log(e.error);
				this.alert.showAlertMessage(e.error.message);
			}
		})
	}

	delete() {
		this.ticketService.delete(this.ticket.id);
	}
}
