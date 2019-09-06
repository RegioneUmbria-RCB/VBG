import { Injectable } from '@angular/core';
import { BaseUrlservice } from '../url';
import { LinkModel } from './configuration.model';

const DEFAULT_MENU = [
  { titolo: 'HOME', link: '/index' },
  { titolo: 'INFO', link: '/info' },
  { titolo: 'NORMATIVA', link: '/normativa' },
  { titolo: 'ATTIVITÀ E PROCEDIMENTI', link: '/interventi-selezione-area' },
  { titolo: 'MODULISTICA', link: '/modulistica' },
  { titolo: 'NEWS', link: '/news' },
  { titolo: 'FAQ', link: '/faq' },
  { titolo: 'TRASPARENZA', link: '/archivio-pratiche' }
];

export function buildMenuUrls(menuItems: LinkModel[], urlService: BaseUrlservice): LinkModel[] {
    if (!menuItems) {
      menuItems = DEFAULT_MENU;
    }

    menuItems.forEach(x => x.link = urlService.url(x.link));

    return menuItems;
  }
