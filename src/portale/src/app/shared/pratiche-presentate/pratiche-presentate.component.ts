import { Component, OnInit } from '@angular/core';
import { ConfigurationService } from '../../core';

@Component({
  selector: 'app-pratiche-presentate',
  templateUrl: './pratiche-presentate.component.html',
  styleUrls: ['./pratiche-presentate.component.less']
})
export class PratichePresentateComponent implements OnInit {

  constructor(private arConfig: ConfigurationService) { }

  ngOnInit() {
    console.log(this.arConfig.getConfiguration().backend.areaRiservata.urlLeMiePratiche);
    document.location.replace(this.arConfig.getConfiguration().backend.areaRiservata.urlLeMiePratiche);
  }

}
