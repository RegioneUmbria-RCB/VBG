import { UrlChecker } from "./url-checker";
import { ConfigurazioneBackendModel } from "./configuration.model";
import { UrlMerger } from "./url-merger";

export class BackendUrlChecker extends UrlChecker {
    /**
     *
     */
    constructor(baseUrl: string, alias: string, software: string) {
        super(baseUrl, alias, software);
    }

    verificaUrlBackend(
        config: ConfigurazioneBackendModel
    ): ConfigurazioneBackendModel {
        return {
            ...config,
            urlComuniAssociati: this.check(
                new UrlMerger(config.baseUrl).mergeWith(
                    config.urlComuniAssociati
                )
            ),
            urlStatiIstanza: this.check(
                new UrlMerger(config.baseUrl).mergeWith(
                    config.urlStatiIstanza
                )
            ),
        };
    }
}
