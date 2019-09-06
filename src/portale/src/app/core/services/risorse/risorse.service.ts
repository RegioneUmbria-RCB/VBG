import { Injectable } from '@angular/core';
import { ConfigurationService } from '../configuration';

@Injectable()
export class RisorseService {
    constructor(private configurationService: ConfigurationService) {

    }

    getRisorsa(nomeRisorsa: string, defaultValue: string): string {
        const risorse = this.configurationService.getConfiguration().risorse;

        if (risorse == null) {
            return defaultValue;
        }

        const tmp = risorse[nomeRisorsa];

        if (tmp == null) {
            return defaultValue;
        }

        return tmp;
    }
}
