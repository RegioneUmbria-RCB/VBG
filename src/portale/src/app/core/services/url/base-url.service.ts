import { IResolveUrlParamsCallback } from './IResolveUrlParamsCallback';

export class BaseUrlservice {

    constructor(private callback: IResolveUrlParamsCallback) { }

    url(path: string, pathParts?: string[]) {

        const params = this.callback();

        const fullParts = [params.alias, params.software];

        if (pathParts) {
            Array.prototype.push.apply(fullParts, pathParts);
        }

        const url = params.baseUrl + path + '/' + fullParts.join('/');

        return url;
    }
}
