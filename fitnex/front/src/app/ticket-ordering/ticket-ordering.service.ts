import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {GlobalService} from "../global.service";
import {AlertService} from "../alert/alert.service";

@Injectable({
	providedIn: 'root'
})

export class TicketOrderingService {

	orderingSubject = new BehaviorSubject<any>({
		orderings: [],
	})

	constructor(
		private http: HttpClient,
		private global: GlobalService,
		private alert: AlertService,
	) {
	}

	private get url() {
		return this.global.backendURL + '/tickets/orderings'
	}

	private error(e: any) {
		console.log(e.error);
		this.alert.showAlertMessage(e.error.message);
	}

	findAll() {
		this.http.get(
			this.url,
			{headers: this.global.headersToken}
		).subscribe({
			next: (res: any) => this.orderingSubject.next({
				...this.orderingSubject.value,
				orderings: res.data,
			}),
			error: (e: any) => this.error(e)
		})
	}

	findAllMy() {
		this.http.get(
			this.url + '/my',
			{headers: this.global.headersToken}
		).subscribe({
			next: (res: any) => this.orderingSubject.next({
				...this.orderingSubject.value,
				orderings: res.data,
			}),
			error: (e: any) => this.error(e)
		})
	}

	save(ticketId: number) {
		return this.http.post(
			this.url,
			"",
			{
				headers: this.global.headersToken,
				params: new HttpParams({}).appendAll({ticketId: ticketId})
			}
		);
	}

	confirmed(id: number) {
		this.http.get(
			this.url + `/${id}/confirmed`,
			{headers: this.global.headersToken}
		).subscribe({
			next: (res: any) => {
				let orderings = this.orderingSubject.value.orderings;
				orderings = orderings.map((i: any) => i.id === id ? res.data : i);
				this.orderingSubject.next({
					...this.orderingSubject.value,
					orderings: orderings,
				})
			},
			error: (e: any) => this.error(e)
		})
	}

	rejected(id: number) {
		this.http.get(
			this.url + `/${id}/rejected`,
			{headers: this.global.headersToken}
		).subscribe({
			next: (res: any) => {
				let orderings = this.orderingSubject.value.orderings;
				orderings = orderings.map((i: any) => i.id === id ? res.data : i);
				this.orderingSubject.next({
					...this.orderingSubject.value,
					orderings: orderings,
				})
			},
			error: (e: any) => this.error(e)
		})
	}

}
