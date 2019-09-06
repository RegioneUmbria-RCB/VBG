export class ImageSetPathBuilder {

  public readonly defaultImage:string;
  private srcSet = new Array<string>();

  constructor(resourcePath: string) {
    const partial = "/assets/immagini/" + resourcePath + "/sfondi/sfondo-home";

    this.defaultImage = partial + "-m.jpg";

    this.srcSet.push(partial + "-xl.jpg 1920w");
    this.srcSet.push(partial + "-l.jpg 1280w");
    this.srcSet.push(partial + "-m.jpg 1024w");
    this.srcSet.push(partial + "-s.jpg 800w");
    this.srcSet.push(partial + "-xs.jpg 400w");
  }

  public getSrcSet():string{
    return this.srcSet.join(", ");
  }
}
