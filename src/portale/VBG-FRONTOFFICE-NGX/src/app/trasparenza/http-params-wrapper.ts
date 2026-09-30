import { HttpParams } from "@angular/common/http";

export class HttpParamsWrapper {
    private pars = new HttpParams();

    public addIfNotNull(paramName: string, value: string): HttpParamsWrapper {
        if ((value || "") === "") {
            return this;
        }

        console.log(paramName, value);

        this.pars = this.pars.set(paramName, value);

        return this;
    }

    public getHttpParams(): HttpParams {
        return this.pars;
    }
}
