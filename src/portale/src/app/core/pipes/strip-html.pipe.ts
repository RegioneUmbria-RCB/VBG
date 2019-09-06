import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'stripHtml'
})
export class StripHtmlPipe implements PipeTransform {

  transform(value: string, args?: any): any {
    let tmp = value ? value.replace(/<[^>]+>/gm, '') : '';

    tmp = tmp.replace(/&nbsp;/gm, ' ');

    return tmp;
  }
}
