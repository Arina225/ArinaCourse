import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject} from "rxjs";
import {GlobalService} from "./global.service";
import {AlertService} from "./alert/alert.service";

@Injectable({
	providedIn: 'root'
})

export class EnumService {

	enumSubject = new BehaviorSubject<any>({
		roles: [],
		genders: [],
		ageCategories: [],
	})

	constructor(
		private http: HttpClient,
		private global: GlobalService,
		private alert: AlertService,
	) {
	}

	private get url() {
		return this.global.backendURL + '/enums';
	}

	private error(e: any) {
		console.log(e.error);
		this.alert.showAlertMessage(e.error.message);
	}

	getRoles() {
		this.http.get(
			this.url + '/roles',
		).subscribe({
			next: (res: any) => this.enumSubject.next({
				...this.enumSubject.value,
				roles: res.data,
			}),
			error: (e: any) => this.error(e),
		})
	}

	getGenders() {
		this.http.get(
			this.url + '/genders',
		).subscribe({
			next: (res: any) => this.enumSubject.next({
				...this.enumSubject.value,
				genders: res.data,
			}),
			error: (e: any) => this.error(e),
		})
	}

	getAgeCategories() {
		this.http.get(
			this.url + '/ageCategories',
		).subscribe({
			next: (res: any) => this.enumSubject.next({
				...this.enumSubject.value,
				ageCategories: res.data,
			}),
			error: (e: any) => this.error(e),
		})
	}

}
