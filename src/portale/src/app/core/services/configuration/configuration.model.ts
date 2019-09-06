export interface LinkModel {
    titolo: string;
    link: string;
}

export interface IRisorsa {
    [key: string]: string;
}

export interface CanaleSocialModel {
    tipo: string;
    link: string;
}

export interface ItemsModel {
    label: string;
    link: string;
    stato: string;
}

export interface PassaALinksModel{
    etichetta: string;
    icona: string;
    items: ItemsModel[];
}

export interface ConfigurazioneGlobalsModel {
    denominazioneComune: string;
    denominazioneSportello: string;
    denominazioneRegione: string;
    linkRegione: string;
    urlQuestionario: string;
    themeLocation: string;

    canaliSocial: CanaleSocialModel[];
    footerLinks: LinkModel[];
    passaALinks: PassaALinksModel;
    linkComune: string;
}

export interface ConfigurazioneAreaRiservataModel {
    baseUrl: string;
    urlLogin: string;
    urlRegistrazione: string;
    urlNuovaDomanda: string;
    urlLeMiePratiche: string;
    urlArchivioPratiche: string;
    urlScadenzario: string;
    urlDownloadModelloDomanda: string;
}


export interface ConfigurazioneBackendModel {
    baseUrl: string;
    alias: string;
    software: string;
    urlVisura: string;

    areaRiservata: ConfigurazioneAreaRiservataModel;
}

export interface ConfigurazioneServiziRegionaliModel {

    baseUrl: string;
    alias: string;
    software: string;
}


export interface Configuration {
    risorse: IRisorsa;
    globals: ConfigurazioneGlobalsModel;
    serviziRegionali: ConfigurazioneServiziRegionaliModel;
    backend: ConfigurazioneBackendModel;
    menu?: LinkModel[];
    homeConfiguration: HomeConfigurationModel;
}

export interface HomeConfigurationModel {
  primoPianoVisible: boolean;
  newsVisible: boolean;
  faqVisible: boolean;
  infoVisible: boolean;
}
