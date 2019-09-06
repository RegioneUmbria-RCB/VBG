
import { map, finalize, tap } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { TopNewsModel } from '../top-news.model';
import { NewsService } from '../news.service';
import { UrlLocaliService } from '../../core/services/url';


@Component({
    selector: 'app-news-list',
    templateUrl: './news-list.component.html'
})
export class NewsListComponent implements OnInit {

    public dataSource$: Observable<TopNewsModel[]>;
    public caricamentoCompletato = false;
    public urlDettaglio: string;
    public currentPage = 0;
    public pageCount = -1;

    private pageSize = 10;
    private pageStream = new Subject<number>();

    constructor(private service: NewsService, urlService: UrlLocaliService) {
        this.urlDettaglio = urlService.url('/news');
    }

    newsPrecedenti() {
        let newPage = this.currentPage + 1;

        if (newPage >= this.pageCount) {
            newPage = this.pageCount - 1;
        }

        this.pageStream.next(newPage);
    }

    newsSuccessive() {
        let newPage = this.currentPage - 1;

        if (newPage < 0) {
            newPage = 0;
        }

        this.pageStream.next(newPage);
    }


    ngOnInit() {

        this.caricamentoCompletato = false;

        this.pageStream.subscribe(
            pageNum => {
                this.currentPage = pageNum;

                this.dataSource$ = this.service.getList().pipe(
                    tap((listaNews) => {

                        if (this.pageCount < 0) {
                            this.pageCount = Math.floor(listaNews.length / this.pageSize);

                            if (listaNews.length % this.pageSize > 0) {
                                this.pageCount++;
                            }
                        }
                    }),
                    map(listaNews => {
                        const offset = this.currentPage * this.pageSize;

                        return listaNews.slice(offset, offset + this.pageSize);
                    }),
                    // setTimeout risolve l'errore "ExpressionChangedAfterItHasBeenCheckedError"
                    finalize(() => setTimeout(() => {
                        this.caricamentoCompletato = true;
                        document.documentElement.scrollTop = 0;
                    }))
                );
            }
        );

        this.pageStream.next(0);
    }
}
