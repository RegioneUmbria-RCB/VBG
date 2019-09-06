import { Component, OnInit } from '@angular/core';

import { FaqService, TopFaqModel } from '../../faq';
import { UrlLocaliService } from '../../core/services/url';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home-faq',
  templateUrl: './home-faq.component.html'
})
export class HomeFaqComponent implements OnInit {

  topFaq$: Observable<TopFaqModel[]>;
  urlDettaglioFaq: string;

  constructor(private faqService: FaqService, urlLocali: UrlLocaliService) {
    this.urlDettaglioFaq = urlLocali.url('/faq');
  }

  ngOnInit() {
    this.topFaq$ = this.faqService.getTop();
  }

}
