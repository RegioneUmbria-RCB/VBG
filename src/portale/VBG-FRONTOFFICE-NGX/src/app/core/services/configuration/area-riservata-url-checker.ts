import { ConfigurazioneAreaRiservataModel } from "./configuration.model";
import { UrlChecker } from "./url-checker";

export class AreaRiservataUrlChecker extends UrlChecker {
    constructor(baseUrl: string, alias: string, software: string) {
        super(baseUrl, alias, software);
    }

    verificaUrlAreaRiservata(
        arConfig: ConfigurazioneAreaRiservataModel
    ): ConfigurazioneAreaRiservataModel {
        return {
            urlLogin: this.check(arConfig.urlLogin),
            urlNuovaDomanda: this.check(arConfig.urlNuovaDomanda),
            urlLeMiePratiche: this.check(arConfig.urlLeMiePratiche),
            urlArchivioPratiche: this.check(arConfig.urlArchivioPratiche),
            urlScadenzario: this.check(arConfig.urlScadenzario),
            urlDownloadModelloDomanda: this.check(
                arConfig.urlDownloadModelloDomanda
            ),
            urlAutocompleteStradario: this.check(
                arConfig.urlAutocompleteStradario
            ),
            urlPresentaInterventoConsole: this.check(
                arConfig.urlPresentaInterventoConsole
            ),
            urlPresentaInterventoLocale: this.check(
                arConfig.urlPresentaInterventoLocale
            ),
        };
    }
}
