import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDatepickerInputEvent, MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';


@Component({
  selector: 'app-datepicker',
  standalone: true,
  imports: [
    CommonModule, 
    MatDatepickerModule, 
    MatNativeDateModule, 
    FormsModule,
    MatFormFieldModule 
  ],
  template: `
    <mat-form-field appearance="fill">
      <mat-label>Choose a date</mat-label>
      <input matInput [matDatepicker]="picker" [(ngModel)]="selectedDate" (dateChange)="onDateChange($event)"> 
      <mat-datepicker #picker></mat-datepicker>
    </mat-form-field>
  `,
  styleUrl: './datepicker.component.css'
})
export class DatepickerComponent {
  @Input() selectedDate: Date | null = null; 
  @Output() selectedDateChange = new EventEmitter<Date | null>();

  onDateChange(event: MatDatepickerInputEvent<Date>) { 
    this.selectedDate = event.value; 
    this.selectedDateChange.emit(event.value);
  }
}