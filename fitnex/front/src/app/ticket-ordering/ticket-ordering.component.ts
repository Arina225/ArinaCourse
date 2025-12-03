import {Component} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";
import {GlobalService} from "../global.service";
import {NavigateDirective} from "../navigate.directive";
import {NgIf} from "@angular/common";
import {TicketOrderingService} from "./ticket-ordering.service";

@Component({
	selector: 'app-ticket-ordering',
	imports: [
		NavigateDirective,
		NgIf
	],
	templateUrl: './ticket-ordering.component.html',
	standalone: true
})

export class TicketOrderingComponent {

	orderings: any[] = [];

	constructor(
		private authService: AuthService,
		private router: Router,
		private global: GlobalService,
		private orderingService: TicketOrderingService,
	) {
	}

	ngOnInit(): void {
		this.authService.getUser().add(() => {
			if (this.global.role !== 'ADMIN') this.router.navigate(['/login']);
		})

		this.orderingService.orderingSubject.subscribe(value => {
			this.orderings = value.orderings;
		})
		this.orderingService.findAll();
	}

	confirmed(id: number) {
		this.orderingService.confirmed(id);
	}

	rejected(id: number) {
		this.orderingService.rejected(id);
	}

}
