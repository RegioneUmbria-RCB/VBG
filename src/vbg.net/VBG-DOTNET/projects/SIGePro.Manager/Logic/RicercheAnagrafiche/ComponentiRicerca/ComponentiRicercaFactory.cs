using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca
{
    public class ComponentiRicercaFactory
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(ComponentiRicercaFactory));
        private readonly Dictionary<ComponentiRicercaEnum, Func<IVerticalizzazioniFactory, IAnagrafeSearcher>> _dictionary;
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public ComponentiRicercaFactory(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;

            this._dictionary = new Dictionary<ComponentiRicercaEnum, Func<IVerticalizzazioniFactory, IAnagrafeSearcher>>
            {
                {ComponentiRicercaEnum.Adrier, (f) => new Adrier.AdrierAnagrafeSearcher(f, bindingFactory)},
                {ComponentiRicercaEnum.Cesena, (f) => new Cesena.CesenaAnagrafeSearcher(f, bindingFactory)},
                {ComponentiRicercaEnum.Maggioli, (f) => new Maggioli.MaggioliAnagrafeSearcher(f, bindingFactory)},
                {ComponentiRicercaEnum.ParixStandard, (f) => new ParixStandard.ParixStandardAnagrafeSearcher(f, bindingFactory)},
                {ComponentiRicercaEnum.ParixCloud, (f) => new ParixUmbria.ParixCloudAnagrafeSearcher(f, bindingFactory)},
                {ComponentiRicercaEnum.Parma, (f) => new Parma.ParmaAnagrafeSearcher(f, bindingFactory)},
                {ComponentiRicercaEnum.Piacenza, (f) => new Piacenza.PiacenzaAnagrafeSearcher(f, bindingFactory)},
                {ComponentiRicercaEnum.Ravenna, (f) => new Ravenna.RavennaAnagrafeSearcher(f)},
                {ComponentiRicercaEnum.RomagnaForlivese, (f) => new RomagnaForlivese.RomagnaForliveseAnagrafeSearcher(f, bindingFactory)},
                {ComponentiRicercaEnum.Terni, (f) => new Terni.TerniAnagrafeSearcher(f)},
            };

        }

        public IAnagrafeSearcher? CreaIstanzaSearcher(VerticalizzazioneWsanagrafe vert)
        {
            var searchComponent = vert.SearchComponent;

            this._log.DebugFormat("Caricamento del componente di ricerca {0}", searchComponent);

            if (!Enum.TryParse(searchComponent, out ComponentiRicercaEnum enumVal))
            {
                this._log.ErrorFormat("Componente di ricerca {0} non trovato o valore di configurazione non valido", searchComponent);

                return null;
            }

            if (!this._dictionary.TryGetValue(enumVal, out var searcherConstructor))
            {
                this._log.ErrorFormat("Componente di ricerca {0} non supportato o non implementato", searchComponent);

                return null;
            }

            return searcherConstructor(this._verticalizzazioniFactory);
        }
    }
}
