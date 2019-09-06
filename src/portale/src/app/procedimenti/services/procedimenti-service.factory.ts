import { Injectable } from '@angular/core';

import { ProcedimentiBaseService } from './procedimenti-base.service';
import { ProcedimentiLocaliService } from './procedimenti-locali.service';
import { ProcedimentiRegionaliService } from './procedimenti-regionali.service';

@Injectable()
export class ProcedimentiServiceFactory {


    constructor(private servizioLocale: ProcedimentiLocaliService, private servizioRegionale: ProcedimentiRegionaliService) {
    }

    create(isLocale: boolean): ProcedimentiBaseService {
        if (isLocale) {
            return this.servizioLocale;
        }

        return this.servizioRegionale;
    }
}
