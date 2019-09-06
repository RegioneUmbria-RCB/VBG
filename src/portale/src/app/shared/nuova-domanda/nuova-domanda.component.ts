import { Component, OnInit } from '@angular/core';
import { ConfigurationService } from '../../core';


@Component({
  selector: 'app-nuova-domanda',
  templateUrl: './nuova-domanda.component.html',
  styleUrls: ['./nuova-domanda.component.less']
})
export class NuovaDomandaComponent implements OnInit {

  constructor(private arConfig: ConfigurationService) { }

  ngOnInit() {
    console.log(this.arConfig.getConfiguration().backend.areaRiservata.urlNuovaDomanda);
    document.location.replace(this.arConfig.getConfiguration().backend.areaRiservata.urlNuovaDomanda);
  }

}
