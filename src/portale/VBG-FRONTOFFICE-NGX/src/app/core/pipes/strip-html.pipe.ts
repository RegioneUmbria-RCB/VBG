import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
    name: "stripHtml",
    standalone: true,
})
export class StripHtmlPipe implements PipeTransform {
    transform(value: string): string {
        let tmp = value ? value.replace(/<[^>]+>/gm, "") : "";

        tmp = tmp.replace(/&nbsp;/gm, " ");

        return tmp;
    }
}
