export class BackgroundImagePath {

  private readonly _defaultImage: string;
  private readonly _srcSet = new Array<string>();

  constructor(resourcePath: string) {
    const partial = 'assets/themes/' + resourcePath + '/images/sfondi/sfondo-home';

    this._defaultImage = partial + '-s.jpg';

    this._srcSet.push(partial + '-xl.jpg 1920w');
    this._srcSet.push(partial + '-l.jpg 1280w');
    this._srcSet.push(partial + '-m.jpg 1024w');
    this._srcSet.push(partial + '-s.jpg 800w');
    this._srcSet.push(partial + '-xs.jpg 400w');
  }

  public GetDefaultImage(): string {
    return this._defaultImage;
  }

  public getSrcSet():  string {
    return this._srcSet.join(', ');
  }
}
