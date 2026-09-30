import { Component, Input } from "@angular/core";

@Component({
    selector: "app-share-social",
    templateUrl: "./share-social.component.html",
    styleUrls: ["./share-social.component.less"],
    standalone: true,
})
export class ShareSocialComponent {
    @Input() titolo: string;

    canali = ["facebook", "twitter"];

    private _href = "";



    get href(): string {
        return this._href || document.location.href;
    }

    @Input()
    set href(val: string) {
        this._href = val;
    }

    facebookUrl(): string {
        return (
            "https://www.facebook.com/sharer/sharer.php?u=" +
            encodeURIComponent(this.href) +
            "&t=" +
            encodeURIComponent(this.titolo)
        );
    }

    twitterUrl(): string {
        return (
            "https://twitter.com/share?text=" +
            encodeURIComponent(this.titolo) +
            "&url=" +
            encodeURIComponent(this.href)
        );
    }
}
