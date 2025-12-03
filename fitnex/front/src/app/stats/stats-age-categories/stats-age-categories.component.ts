import {Component, OnInit} from '@angular/core';
import {StatsService} from "../stats.service";
import {ChartComponent} from "ng-apexcharts";

@Component({
	selector: 'app-stats-age-categories',
	imports: [
		ChartComponent
	],
	templateUrl: './stats-age-categories.component.html',
	standalone: true
})

export class StatsAgeCategoriesComponent implements OnInit {

	chartOptions: any;

	names: any[] = [];
	values: any[] = [];

	constructor(
		private statsService: StatsService,
	) {
	}

	ngOnInit(): void {
		this.statsService.ageCategories().subscribe({
			next: (res: any) => {
				this.names = res.data.names;
				this.values = res.data.values;
				this.draw();
			},
			error: (e: any) => console.log(e.error)
		})
	}

	draw() {
		this.chartOptions = {
			series: this.values,
			chart: {
				type: "donut",
				height: 400,
			},
			labels: this.names,
			responsive: [
				{
					breakpoint: 480,
					options: {
						chart: {
							width: 200
						},
						legend: {
							position: "bottom"
						}
					}
				}
			]
		};
	}

}
