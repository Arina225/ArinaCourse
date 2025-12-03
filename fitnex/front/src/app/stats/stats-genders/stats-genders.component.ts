import {Component, OnInit} from '@angular/core';
import {StatsService} from "../stats.service";
import {ChartComponent} from "ng-apexcharts";

@Component({
	selector: 'app-stats-genders',
	imports: [
		ChartComponent
	],
	templateUrl: './stats-genders.component.html',
	standalone: true
})

export class StatsGendersComponent implements OnInit {

	chartOptions: any;

	names: any[] = [];
	values: any[] = [];

	constructor(
		private statsService: StatsService,
	) {
	}

	ngOnInit(): void {
		this.statsService.genders().subscribe({
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
			labels: this.names,
			series: this.values,
			chart: {
				type: "polarArea",
				height: 400,
			},
			stroke: {
				colors: ["#fff"]
			},
			fill: {
				opacity: 0.8
			},
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
