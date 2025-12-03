import {Component, OnInit} from '@angular/core';
import {ChartComponent} from "ng-apexcharts";
import {StatsService} from "../stats.service";

@Component({
	selector: 'app-stats-tickets',
    imports: [
        ChartComponent
    ],
	templateUrl: './stats-tickets.component.html',
	standalone: true
})

export class StatsTicketsComponent implements OnInit{

	chartOptions: any;

	names: any[] = [];
	values: any[] = [];

	constructor(
		private statsService: StatsService,
	) {
	}

	ngOnInit(): void {
		this.statsService.tickets().subscribe({
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
			series: [
				{
					name: "Прибыль",
					data: this.values
				}
			],
			chart: {
				height: 350,
				type: "bar",
			},
			colors: [
				"#775DD0",
				"#FEB019",
				"#008FFB",
				"#D10CE8",
				"#FF4560",
			],
			plotOptions: {
				bar: {
					columnWidth: "45%",
					distributed: true
				}
			},
			dataLabels: {
				enabled: false
			},
			legend: {
				show: false
			},
			grid: {
				show: false
			},
			xaxis: {
				categories: this.names,
				labels: {
					style: {
						colors: [
							"#775DD0",
							"#FEB019",
							"#008FFB",
							"#D10CE8",
							"#FF4560",
						],
						fontSize: "12px"
					}
				}
			}
		};
	}

}
