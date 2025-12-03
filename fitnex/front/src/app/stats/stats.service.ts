import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GlobalService} from "../global.service";

@Injectable({
	providedIn: 'root'
})
export class StatsService {

	constructor(
		private http: HttpClient,
		private global: GlobalService
	) {
	}

	private get url() {
		return this.global.backendURL + '/stats'
	}

	ageCategories() {
		return this.http.get(
			this.url + '/ageCategories',
			{headers: this.global.headersToken}
		);
	}

	genders() {
		return this.http.get(
			this.url + '/genders',
			{headers: this.global.headersToken}
		);
	}

	tickets() {
		return this.http.get(
			this.url + '/tickets',
			{headers: this.global.headersToken}
		);
	}

}
