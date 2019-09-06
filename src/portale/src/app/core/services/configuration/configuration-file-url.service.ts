import { Injectable } from '@angular/core';
import { AliasSoftwareService } from '../alias-software/alias-software.service';

import { environment } from '../../../../environments/environment';

@Injectable()
export class ConfigurationFileUrlService {

    private BASE_PATH = './assets/configuration/';
    private isProduction = environment.production;

    constructor(private aliasSoftwareService: AliasSoftwareService) { }

    getDefaultFileUrl() {
        return this.BASE_PATH + 'config.default.json';
    }

    getLocalizedFileUrl() {

        if (!this.aliasSoftwareService.getAlias() || !this.aliasSoftwareService.getSoftware()) {
            return this.getDefaultFileUrl();
        }

        return this.BASE_PATH +
            'config.' +
            this.aliasSoftwareService.getAlias() + '.' +
            this.aliasSoftwareService.getSoftware() +
            (!this.isProduction ? '.dev' : '') +
            '.json';
    }
}
