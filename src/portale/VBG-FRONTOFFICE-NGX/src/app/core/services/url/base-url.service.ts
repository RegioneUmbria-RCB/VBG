import { IResolveUrlParamsCallback } from "./IResolveUrlParamsCallback";

export class BaseUrlservice {
    constructor(private callback: IResolveUrlParamsCallback) { }

    url(path: string, pathParts?: string[]): string {
        const params = this.callback();

        const fullParts = [params.alias, params.software];

        if (pathParts) {
            Array.prototype.push.apply(fullParts, pathParts);
        }

        const url = params.baseUrl + path + "/" + fullParts.join("/");

        return url;
    }

    urlDownloadModello(
        path: string,
        alias: string,
        software: string,
        idIntervento: number
    ): string {
        if (!path) {
            return "";
        }

        path =
            path.replace("{alias}", alias).replace("{software}", software) +
            "&Intervento=" +
            idIntervento.toString() +
            "&Endo=&pdf=true";

        return path;
    }
}
