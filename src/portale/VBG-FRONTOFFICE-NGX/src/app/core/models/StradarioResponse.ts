/* eslint-disable @typescript-eslint/naming-convention */
import { StradarioResultItem } from "./StradarioResultItem";
export interface StradarioResponseType {
    Items: StradarioResultItem[];
    ItemCount: number;
}

export interface StradarioReponse {
    d: StradarioResponseType;
}
/* Non usata?
export interface ComuniAssociatiResponse {

}*/
