import { BaseUrlservice } from "../url";
import { LinkModel } from "./configuration.model";

const DEFAULT_MENU = [
    { titolo: "HOME", link: "/index" },
    { titolo: "INFO", link: "/info" },
    { titolo: "NORMATIVA", link: "/normativa" },
    { titolo: "ATTIVITÀ E PROCEDIMENTI", link: "/interventi-selezione-area" },
    { titolo: "MODULISTICA", link: "/modulistica" },
    { titolo: "NEWS", link: "/news" },
    { titolo: "FAQ", link: "/faq" },
    { titolo: "TRASPARENZA", link: "/archivio-pratiche" },
];

export const buildMenuUrls = (
    menuItems: LinkModel[],
    urlService: BaseUrlservice
): LinkModel[] => {
    if (!menuItems) {
        menuItems = DEFAULT_MENU;
    }
    // in caso di url esterne la modifica viene gestita anche nella pagina page-header.component.html
    menuItems.filter(x => !x.link.startsWith("http"))
        .forEach((x) =>
            (x.link = urlService.url(x.link))
        );

    return menuItems;
};
