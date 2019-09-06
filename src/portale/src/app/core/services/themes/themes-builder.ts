import { BackgroundImagePath } from './background-image.path';

export class ThemesPathBuilder {
  constructor(private themeBasePath: string) {
  }

  public getBackgroundImage(): BackgroundImagePath {
    return new BackgroundImagePath(this.themeBasePath);
  }

  public getCssPath(): string {
    return 'assets/themes/' + this.themeBasePath + '/css/style.css';
  }

  public getLogoComune(): string {
    return 'assets/themes/' + this.themeBasePath + '/logo-comune.png';
  }

}
