
import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {AuthService} from "./auth/auth.service";
import AOS from "aos";
import {AlertComponent} from "./alert/alert.component";
import {NavComponent} from "./nav/nav.component";
import {FooterComponent} from "./footer/footer.component";

@Component({
	selector: 'app-root',
	standalone: true,
	imports: [
		RouterOutlet,
		FooterComponent,
		NavComponent,
		AlertComponent,
	],
	templateUrl: './app.component.html',
})

export class AppComponent {
	constructor(
		private authService: AuthService,
	) {
	}

	ngOnInit(): void {
		AOS.init();
		this.authService.getUser();
	}
}
