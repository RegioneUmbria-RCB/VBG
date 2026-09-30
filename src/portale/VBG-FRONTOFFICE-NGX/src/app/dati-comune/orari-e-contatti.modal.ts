export class RiferimentiSupporto {
    telefono: string;
    fax: string;
    pec: string;
    email: string;
}

export interface OrariEContatti {
    responsabileTrattamento: string;
    dataProtectionOfficer: string;
    denominazioneSportello: string;
    responsabileSportello: string;
    supporto: RiferimentiSupporto;
    indirizzoSportello: string;
    denominazioneComune: string;
}

export interface OrariEContattiItems {
    items: OrariEContatti;
}
