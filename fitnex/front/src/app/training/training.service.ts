import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GlobalService} from "../global.service";
import {BehaviorSubject} from "rxjs";
import {AlertService} from "../alert/alert.service";
import {Router} from "@angular/router";

@Injectable({
	providedIn: 'root'
})
export class TrainingService {

	trainingSubject = new BehaviorSubject<any>({
		trainings: [],
	});

	constructor(
		private http: HttpClient,
		private global: GlobalService,
		private alert: AlertService,
		private router: Router,
	) {
	}

	private get url() {
		return this.global.backendURL + '/trainings'
	}

	private error(e: any) {
		console.log(e.error)
		this.alert.showAlertMessage(e.error.message);
	}

	findAllMy() {
		this.http.get(
			this.url + '/my',
			{headers: this.global.headersToken}
		).subscribe({
			next: (res: any) => this.trainingSubject.next({
				...this.trainingSubject.value,
				trainings: res.data,
			}),
			error: (e: any) => this.error(e),
		})
	}

	findAllByTrainerId(id: number) {
		this.http.get(
			this.url + `/trainer/${id}`,
		).subscribe({
			next: (res: any) => this.trainingSubject.next({
				...this.trainingSubject.value,
				trainings: res.data,
			}),
			error: (e: any) => this.error(e),
		})
	}

	find(id: number) {
		return this.http.get(
			this.url + `/${id}`,
			{headers: this.global.headersToken}
		);
	}

	save(training: any) {
		this.http.post(
			this.url,
			JSON.stringify(training),
			{headers: this.global.headersJsonToken},
		).subscribe({
			next: () => this.router.navigate(['trainings']),
			error: (e: any) => this.error(e),
		})
	}

	update(id: number, training: any) {
		this.http.put(
			this.url + `/${id}`,
			JSON.stringify(training),
			{headers: this.global.headersJsonToken},
		).subscribe({
			next: () => this.router.navigate(['trainings']),
			error: (e: any) => this.error(e),
		})
	}

	delete(id: number) {
		this.http.delete(
			this.url + `/${id}`,
			{headers: this.global.headersToken},
		).subscribe({
			next: () => {
				let trainings = this.trainingSubject.value.trainings;
				trainings = trainings.filter((i: any) => i.id !== id);
				this.trainingSubject.next({
					...this.trainingSubject.value,
					trainings: trainings,
				})
			},
			error: (e: any) => this.error(e),
		})
	}

}
