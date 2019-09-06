import { Component, OnInit } from '@angular/core';
import { NewsService, TopNewsModel } from '../../news';
import { UrlLocaliService } from '../../core/services/url';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home-news',
  templateUrl: './home-news.component.html',
  styleUrls: ['./home-news.component.less']
})
export class HomeNewsComponent implements OnInit {

  topNews$: Observable<TopNewsModel[]>;
  urlDettaglioNews: string;

  constructor(private newsService: NewsService, urlLocaliService: UrlLocaliService) {

    this.urlDettaglioNews = urlLocaliService.url('/news');
  }

  ngOnInit() {
    this.topNews$ = this.newsService.getTop();
  }

}
