import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {GlobalService} from "../global.service";

@Injectable({
	providedIn: 'root'
})
export class AccountService {

	constructor(
		private http: HttpClient,
		private global: GlobalService
	) {
	}

	private get url() {
		return this.global.backendURL + '/users/profiles'
	}

	update(profile: any, gender: string, ageCategory: string) {
		return this.http.put(
			this.url,
			JSON.stringify(profile),
			{
				headers: this.global.headersJsonToken,
				params: new HttpParams({}).appendAll({
					gender: gender,
					ageCategory: ageCategory,
				}),
			}
		);
	}

	updateImg(files: any) {
		let formData = new FormData();
		for (let i = 0; i < files.length; i++) {
			formData.append("files", files[i]);
		}
		return this.http.patch(
			this.url + '/img',
			formData,
			{headers: this.global.headersMultipartToken,}
		);
	}

}
