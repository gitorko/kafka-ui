import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { ClarityModule, ClrFormsNextModule } from '@clr/angular';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './routing/app-routing.module';
import { HomeComponent } from './home/home.component';
import { KafkaService } from './svc/kafka.service';
import { TopicComponent } from './topic/topic.component';
import { BrokerComponent } from './broker/broker.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    TopicComponent,
    BrokerComponent
  ],
  imports: [
    BrowserModule,
    ClarityModule,
    ClrFormsNextModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [KafkaService],
  bootstrap: [AppComponent]
})
export class AppModule { }
