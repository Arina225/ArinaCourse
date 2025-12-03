import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";
import {GlobalService} from "../global.service";
import {NavigateDirective} from "../navigate.directive";
import {NgIf} from "@angular/common";
import {TicketService} from "./ticket.service";

@Component({
	selector: 'app-ticket',
	imports: [
		NavigateDirective,
		NgIf
	],
	templateUrl: './ticket.component.html',
	standalone: true
})
export class TicketComponent implements OnInit {

	tickets: any[] = [];

	get ticketsSorted(){
		let res = this.tickets;

		return res;
	}

	constructor(
		private authService: AuthService,
		private router: Router,
		private global: GlobalService,
		private ticketService: TicketService,
	) {
	}

	get role() {
		return this.global.role;
	}

	ngOnInit(): void {
		this.authService.getUser()

		this.ticketService.ticketSubject.subscribe(value => {
			this.tickets = value.tickets;
		})
		this.ticketService.findAll();
	}

}
