/* import { Injectable } from '@angular/core';
import { ConfigurationService } from './configuration.service';

@Injectable()
export class BackofficeUrlChecker {
    alias: string;
    baseUri: string;
    software: string;

    constructor(private config: ConfigurationService) {
        const cfg = this.config.getConfiguration().serviziRegionali;
        const areaRiservataCfg = this.config.getConfiguration().backend;

        this.alias = areaRiservataCfg.alias;
        this.baseUri = cfg.baseUri;
        this.software = areaRiservataCfg.software;
    }

    check(path: string): string {
        if (!path) {
            return '';
        }

        path = path.replace('{alias}', this.alias).replace('{software}', this.software);

        path = this.baseUri + path;

        return path;
    }
}
 */
