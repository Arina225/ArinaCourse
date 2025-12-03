import {Routes} from '@angular/router';
import {ErrorComponent} from "./error/error.component";
import {StatsComponent} from "./stats/stats.component";
import {UserComponent} from "./user/user.component";
import {LoginComponent} from "./auth/login/login.component";
import {RegComponent} from "./auth/reg/reg.component";
import {MainComponent} from "./main/main.component";
import {AccountComponent} from "./account/account.component";
import {TicketComponent} from "./ticket/ticket.component";
import {TicketPageComponent} from "./ticket/ticket-page/ticket-page.component";
import {TicketAddComponent} from "./ticket/ticket-add/ticket-add.component";
import {TicketUpdateComponent} from "./ticket/ticket-update/ticket-update.component";
import {TrainerComponent} from "./trainer/trainer.component";
import {TrainingComponent} from "./training/training.component";
import {TrainingAddComponent} from "./training/training-add/training-add.component";
import {TrainingUpdateComponent} from "./training/training-update/training-update.component";
import {TrainerTrainingComponent} from "./trainer/trainer-training/trainer-training.component";
import {TicketOrderingComponent} from "./ticket-ordering/ticket-ordering.component";
import {TrainingOrderingService} from "./training-ordering/training-ordering.service";
import {TrainingOrderingComponent} from "./training-ordering/training-ordering.component";

export const routes: Routes = [

	{path: "", component: MainComponent},

	{path: "reg", component: RegComponent},
	{path: "login", component: LoginComponent},

	{path: "users", component: UserComponent},

	{path: "account", component: AccountComponent},

	{path: "tickets", component: TicketComponent},
	{path: "ticket", component: TicketPageComponent},
	{path: "ticket_add", component: TicketAddComponent},
	{path: "ticket_update", component: TicketUpdateComponent},

	{path: "tickets_orderings", component: TicketOrderingComponent},

	{path: "trainers", component: TrainerComponent},
	{path: "trainer_trainings", component: TrainerTrainingComponent},

	{path: "trainings", component: TrainingComponent},
	{path: "training_add", component: TrainingAddComponent},
	{path: "training_update", component: TrainingUpdateComponent},

	{path: "trainings_orderings", component: TrainingOrderingComponent},

	{path: "stats", component: StatsComponent},

	{path: "error", component: ErrorComponent},
	{path: "**", component: ErrorComponent},

];
