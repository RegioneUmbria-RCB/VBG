using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca;
using log4net;
using PersonalLib2.Data;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche
{
    public class RicercheAnagraficheService
    {
        private static class Constants
        {
            public const string DefaultAnagrafeSearcherName = "DEFAULTANAGRAFESEARCHER";
        }

        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly string _alias;
        private readonly ILog _log = LogManager.GetLogger(typeof(RicercheAnagraficheService));
        private readonly ContestoRicercaAnagraficaEnum _contesto;
        private readonly ComponentiRicercaFactory _componentiRicercaFactory;

        public RicercheAnagraficheService(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory, DataBase db, string idComune, string alias, ContestoRicercaAnagraficaEnum contesto)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._db = db;
            this._idComune = idComune;
            this._alias = alias;
            this._contesto = contesto;
            this._componentiRicercaFactory = new ComponentiRicercaFactory(verticalizzazioniFactory, bindingFactory);
        }


        public Anagrafe? GetByCodicefiscale(string codiceFiscale)
        {
            try
            {
                return this.CallSearcher(se => se.ByCodiceFiscaleImp(TipoPersona.PersonaFisica, codiceFiscale));
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in ByCodiceFiscale: {0}", ex.ToString());

                throw;
            }
        }

        public IEnumerable<Anagrafe> GetVariazioniPersoneFisiche(DateTime from, DateTime to)
        {
            try
            {
                return this.CallSearcher(searcher => searcher.GetVariazioni(from, to));
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in GetVariazioniPersoneFisiche: {0}", ex.ToString());

                throw;
            }
        }

        private T CallSearcher<T>(Func<IAnagrafeSearcher, T> action)
        {
            var searcher = this.GetSearcher();

            try
            {
                searcher.Init();

                return action(searcher);
            }
            finally
            {
                searcher?.CleanUp();
            }
        }

        public Anagrafe? GetByPartitaIva(string partitaivaOCodiceFiscale)
        {
            try
            {
                return this.CallSearcher(searcher => searcher.ByPartitaIvaImp(partitaivaOCodiceFiscale));
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in GetByPartitaIva: {0}", ex.ToString());

                throw;
            }
        }


        private IAnagrafeSearcher GetSearcher()
        {
            var vert = this._verticalizzazioniFactory.Create<VerticalizzazioneWsanagrafe>(this._alias, "TT");

            var assemblyName = vert.SearchComponent;

            this._log.DebugFormat("Caricamento del componente di ricerca dall'assembly {0}", assemblyName);

            var defaultSigeproSearcher = new AnagrafeSearcher(this._verticalizzazioniFactory, Constants.DefaultAnagrafeSearcherName);
            defaultSigeproSearcher.InitParams(this._idComune, this._alias, this._db);

            if (!vert.Attiva || assemblyName == "" || assemblyName.ToUpper() == Constants.DefaultAnagrafeSearcherName)
                return defaultSigeproSearcher;

            try
            {
                var searcher = this.CreaIstanzaSearcher(vert);

                if (searcher == null)
                {
                    throw new RicercheAnagraficheException($"Componente di ricerca non supportato {vert.SearchComponent}");
                }

                var wrappedSearcher = new SigeproWrappedAnagrafeSearcher(defaultSigeproSearcher, searcher);

                if (this._contesto == ContestoRicercaAnagraficaEnum.Backoffice)
                    wrappedSearcher.RestituisciAnagraficaSigeproSeNonTrovato = false;

                wrappedSearcher.InitParams(this._idComune, this._alias, this._db);

                return wrappedSearcher;
            }
            catch (Exception ex)
            {
                this._log.Error(ex);

                throw;
            }
        }

        private IAnagrafeSearcher? CreaIstanzaSearcher(VerticalizzazioneWsanagrafe vert)
        {
            try
            {
                return this._componentiRicercaFactory.CreaIstanzaSearcher(vert);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("CreaIstanzaSearcher: {0}", ex.ToString());

                throw;
            }
        }
    }
}
