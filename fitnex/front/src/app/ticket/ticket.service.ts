import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GlobalService} from "../global.service";
import {AlertService} from "../alert/alert.service";
import {Router} from "@angular/router";
import {BehaviorSubject} from "rxjs";

@Injectable({
	providedIn: 'root'
})

export class TicketService {

	ticketSubject = new BehaviorSubject<any>({
		tickets: [],
	})

	constructor(
		private http: HttpClient,
		private global: GlobalService,
		private alert: AlertService,
		private router: Router,
	) {
	}

	private get url() {
		return this.global.backendURL + '/tickets'
	}

	private error(e: any) {
		console.log(e.error);
		this.alert.showAlertMessage(e.error.message);
	}

	private page(id: number) {
		this.router.navigate(['ticket'], {queryParams: {id: id}});
	}

	findAll() {
		this.http.get(
			this.url,
		).subscribe({
			next: (res: any) => this.ticketSubject.next({
				...this.ticketSubject.value,
				tickets: res.data,
			}),
			error: (e: any) => this.error(e)
		})
	}

	find(id: number) {
		return this.http.get(
			this.url + `/${id}`,
		)
	}

	save(ticket: any, img: any, file: any) {
		this.http.post(
			this.url,
			JSON.stringify(ticket),
			{headers: this.global.headersJsonToken}
		).subscribe({
			next: (res: any) => this.updateImg(res.data.id, img, file),
			error: (e: any) => this.error(e)
		})
	}

	update(id: number, ticket: any, img: any, file: any) {
		this.http.put(
			this.url + `/${id}`,
			JSON.stringify(ticket),
			{headers: this.global.headersJsonToken}
		).subscribe({
			next: (res: any) => {
				if (img !== null) this.updateImg(res.data.id, img, file)
				else if (file !== null) this.updateFile(res.data.id, file)
				else this.page(res.data.id)
			},
			error: (e: any) => this.error(e)
		})
	}

	private updateImg(id: number, files: any, file: any) {
		let formData = new FormData();
		for (let i = 0; i < files.length; i++) {
			formData.append('files', files[i]);
		}
		this.http.patch(
			this.url + `/${id}/img`,
			formData,
			{headers: this.global.headersMultipartToken}
		).subscribe({
			next: (res: any) => {
				if (file !== null) this.updateFile(res.data.id, file)
				else this.page(res.data.id)
			},
			error: (e: any) => this.error(e)
		})
	}

	private updateFile(id: number, files: any) {
		let formData = new FormData();
		for (let i = 0; i < files.length; i++) {
			formData.append('files', files[i]);
		}
		this.http.patch(
			this.url + `/${id}/file`,
			formData,
			{headers: this.global.headersMultipartToken}
		).subscribe({
			next: (res: any) => this.page(res.data.id),
			error: (e: any) => this.error(e)
		})
	}

	delete(id: number) {
		this.http.delete(
			this.url + `/${id}`,
			{headers: this.global.headersToken}
		).subscribe({
			next: () => this.router.navigate(['tickets']),
			error: (e: any) => this.error(e)
		})
	}

}
