using Init.SIGePro.Verticalizzazioni;
using log4net;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca
{
    public class ComponentiRicercaFactory
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(ComponentiRicercaFactory));
        private readonly Dictionary<ComponentiRicercaEnum, Func<IAnagrafeSearcher>> _dictionary;

        public ComponentiRicercaFactory()
        {

            this._dictionary = new Dictionary<ComponentiRicercaEnum, Func<IAnagrafeSearcher>>
            {
                {ComponentiRicercaEnum.Adrier, () => new Adrier.AdrierAnagrafeSearcher()},
                {ComponentiRicercaEnum.ParixStandard, () => new ParixStandard.ParixStandardAnagrafeSearcher()},
                {ComponentiRicercaEnum.ParixCloud, () => new ParixUmbria.ParixCloudAnagrafeSearcher()},
            };

        }

        public IAnagrafeSearcher CreaIstanzaSearcher(VerticalizzazioneWsanagrafe vert)
        {
            string searchComponent = vert.SearchComponent;

            this._log.DebugFormat("Caricamento del componente di ricerca {0}", searchComponent);

            if (!Enum.TryParse(searchComponent, out ComponentiRicercaEnum enumVal))
            {
                this._log.ErrorFormat("Componente di ricerca {0} non trovato o valore di configurazione non valido", searchComponent);

                return null;
            }

            if (!this._dictionary.TryGetValue(enumVal, out Func<IAnagrafeSearcher> searcherConstructor))
            {
                this._log.ErrorFormat("Componente di ricerca {0} non supportato o non implementato", searchComponent);

                return null;
            }

            return searcherConstructor();
        }
    }
}
