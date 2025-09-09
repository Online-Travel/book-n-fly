import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-landing',
  imports: [RouterLink],
  standalone:true,
  templateUrl: './landing.html',
  styleUrl: './landing.css'
})
export class Landing {

}
