import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-share-social',
  templateUrl: './share-social.component.html',
  styleUrls: ['./share-social.component.less']
})
export class ShareSocialComponent implements OnInit {

  @Input() titolo: string;

  canali = ['facebook', 'google-plus', 'twitter'];

  private _href = '';


  constructor() { }

  ngOnInit() {
  }

  get href(): string {
    return this._href || document.location.href;
  }

  @Input()
  set href(val: string) {
    this._href = val;
  }

  facebookUrl(): string {
    return 'https://www.facebook.com/sharer/sharer.php?u=' +
      encodeURIComponent(this.href) +
      '&t=' +
      encodeURIComponent(this.titolo);
  }

  gPlusUrl(): string {
    return 'https://plus.google.com/share?url=' +
      encodeURIComponent(this.href);
  }

  twitterUrl(): string {
    return 'https://twitter.com/share?text=' +
      encodeURIComponent(this.titolo) +
      '&url=' +
      encodeURIComponent(this.href);
  }

}
