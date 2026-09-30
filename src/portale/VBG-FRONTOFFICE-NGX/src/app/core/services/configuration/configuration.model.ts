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

export interface PassaALinksModel {
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
    baseUrl?: string;
    urlLogin: string;
    // urlRegistrazione: string;
    urlNuovaDomanda: string;
    urlLeMiePratiche: string;
    urlArchivioPratiche: string;
    urlScadenzario: string;
    urlDownloadModelloDomanda: string;
    urlAutocompleteStradario: string;
    urlPresentaInterventoLocale: string;
    urlPresentaInterventoConsole: string;
}

export const DEFAULT_CONFIGURAZIONE_AREA_RISERVATA: ConfigurazioneAreaRiservataModel =
    {
        urlArchivioPratiche:
            "reserved/ArchivioPratiche.aspx?idComune={alias}&software={software}",
        urlAutocompleteStradario:
            "public/webservices/autocompletestradario.asmx/AutocomlpeteStradario",
        urlDownloadModelloDomanda:
            "public/modellodomanda/modellodomandahandler.ashx?idComune={alias}&software={software}",
        urlLeMiePratiche:
            "reserved/IstanzePresentate.aspx?idComune={alias}&software={software}",
        urlLogin: "login.aspx?idComune={alias}&software={software}",
        urlNuovaDomanda:
            "reserved/NuovaIstanza.aspx?idComune={alias}&software={software}",
        urlScadenzario:
            "reserved/Scadenzario.aspx?idComune={alias}&software={software}",
        urlPresentaInterventoLocale:
            "/presenta-domanda-locale/{alias}/{software}/{codiceIntervento}",
        urlPresentaInterventoConsole:
            "/presenta-domanda-console/{alias}/{software}/{codiceIntervento}",
    };

export interface ConfigurazioneBackendModel {
    baseUrl: string;
    urlComuniAssociati: string;
    alias: string;
    software: string;
    urlVisura: string;
    urlStatiIstanza: string;
    areaRiservata: ConfigurazioneAreaRiservataModel;
}

export const DEFAULT_CONFIGURAZIONE_BACKEND: ConfigurazioneBackendModel = {
    baseUrl: "baseUrl backend non configurato",
    alias: "alias backend non configurato",
    software: "software backend non configurato",
    urlVisura: "urlVisura backend non configurato",
    urlComuniAssociati:
        "../services/rest/backend/generici/{alias}/comuniassociati",
    urlStatiIstanza:
        "../services/rest/backend/generici/{alias}/{software}/stati_istanza",
    areaRiservata: DEFAULT_CONFIGURAZIONE_AREA_RISERVATA,
};

export interface ConfigurazioneServiziRegionaliModel {
    baseUrl: string;
    alias: string;
    software: string;

    urlDownloadModelloDomanda: string;
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

export const DEFAULT_HOME_CONFIGURATION: HomeConfigurationModel = {
    primoPianoVisible: true,
    newsVisible: true,
    faqVisible: true,
    infoVisible: true,
};

export const DEFAULT_GLOBALS_CONFIGURATION: ConfigurazioneGlobalsModel = {
    canaliSocial: null,
    denominazioneComune: "",
    denominazioneRegione: "",
    denominazioneSportello: "",
    footerLinks: new Array<LinkModel>(),
    linkComune: "",
    linkRegione: "",
    passaALinks: null,
    themeLocation: "default",
    urlQuestionario: null,
};
