export class AreaRiservataUrlChecker {

    constructor(private baseUrl: string, private alias: string, private software: string) {
    }

    check(path: string): string {
        if (!path) {
            return '';
        }

        path = path.replace('{alias}', this.alias).replace('{software}', this.software);

        if (!path.startsWith('http')) {
            path = this.baseUrl + path;
        }

        return path;
    }
}