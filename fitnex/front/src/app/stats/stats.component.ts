import {Component, OnInit} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";
import {NgApexchartsModule} from "ng-apexcharts";
import {FormsModule} from "@angular/forms";
import {GlobalService} from "../global.service";
import {StatsAgeCategoriesComponent} from "./stats-age-categories/stats-age-categories.component";
import {StatsGendersComponent} from "./stats-genders/stats-genders.component";
import {StatsTicketsComponent} from "./stats-tickets/stats-tickets.component";
import html2canvas from "html2canvas";
import {jsPDF} from "jspdf";

@Component({
	selector: 'app-stats',
	standalone: true,
	imports: [
		NgApexchartsModule,
		FormsModule,
		StatsAgeCategoriesComponent,
		StatsGendersComponent,
		StatsTicketsComponent,
	],
	templateUrl: './stats.component.html',
})

export class StatsComponent implements OnInit {

	constructor(
		private authService: AuthService,
		private router: Router,
		private global: GlobalService,
	) {
	}

	ngOnInit(): void {
		this.authService.getUser().add(() => {
			if (this.global.role !== 'ADMIN') this.router.navigate(['/login']);
		})
	}

	generatePDF() {
		let data: any = document.getElementById('pdf');
		html2canvas(data).then(canvas => {
			const content = canvas.toDataURL('image/png');

			let jsPdf;
			if (canvas.width > canvas.height) {
				jsPdf = new jsPDF('p', 'cm', 'a4');
				jsPdf.addImage(content, 'PNG', 0, 0, 21, 0);
			} else {
				jsPdf = new jsPDF('p', 'pt', [canvas.width, canvas.height]);
				jsPdf.addImage(content, 'PNG', 0, 0, canvas.width, canvas.height);
			}

			jsPdf.save('pdf.pdf');
		});
	}

}
