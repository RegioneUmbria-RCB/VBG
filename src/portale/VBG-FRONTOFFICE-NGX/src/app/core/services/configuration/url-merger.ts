export class UrlMerger {
    public readonly scheme: string;
    public readonly parts: string[];

    private readonly schemeSeparator = "://";
    private readonly pathSeparator = "/";
    private readonly salmon = "..";

    constructor(baseUrl: string) {
        this.scheme = this.readScheme(baseUrl);
        this.parts = this.readParts(baseUrl);
    }

    mergeWith(partial: string): string {
        // se l'url inizia per http oppure https non unisco i due path
        if (this.readScheme(partial) !== "") {
            return partial;
        }

        // se l'url non inizia per http o https allora le due parti del percors vanno unite
        if (partial.startsWith(this.pathSeparator)) {
            partial = partial.substring(1);
        }

        const localParts = partial.split(this.pathSeparator);
        let skip = 0;

        // se l'url inizia per .. devo scalare di una cartella nel path
        for (let i = 0; i < localParts.length; i++) {
            if (localParts[i] !== this.salmon) {
                skip = i;
                break;
            }
        }

        let finalUrl =
            this.scheme +
            this.schemeSeparator +
            this.parts
                .slice(0, this.parts.length - skip)
                .join(this.pathSeparator);
        finalUrl +=
            this.pathSeparator +
            localParts.slice(skip).join(this.pathSeparator);

        return finalUrl;
    }

    readScheme(baseUrl: string): string {
        if (baseUrl.indexOf(this.schemeSeparator) === -1) {
            return "";
        }

        return baseUrl.split(this.schemeSeparator)[0];
    }

    readParts(baseUrl: string): string[] {
        if (this.scheme !== "") {
            baseUrl = baseUrl.substring(
                this.scheme.length + this.schemeSeparator.length
            );
        }

        const parts = baseUrl.split(this.pathSeparator);

        return baseUrl.endsWith(this.pathSeparator)
            ? parts.slice(0, parts.length - 1)
            : parts;
    }
}
