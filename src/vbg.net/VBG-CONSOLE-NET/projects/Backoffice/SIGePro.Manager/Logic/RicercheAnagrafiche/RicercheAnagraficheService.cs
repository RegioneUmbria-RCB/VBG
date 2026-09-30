using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca;
using Init.SIGePro.Verticalizzazioni;
using log4net;
using PersonalLib2.Data;
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

        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly string _alias;
        private readonly ILog _log = LogManager.GetLogger(typeof(RicercheAnagraficheService));
        private readonly ContestoRicercaAnagraficaEnum _contesto;
        private readonly ComponentiRicercaFactory _componentiRicercaFactory;

        public RicercheAnagraficheService(DataBase db, string idComune, string alias, ContestoRicercaAnagraficaEnum contesto)
        {
            this._db = db;
            this._idComune = idComune;
            this._alias = alias;
            this._contesto = contesto;
            this._componentiRicercaFactory = new ComponentiRicercaFactory();
        }


        public Anagrafe GetByCodicefiscale(string codiceFiscale)
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
            IAnagrafeSearcher searcher = this.GetSearcher();

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

        public Anagrafe GetByPartitaIva(string partitaivaOCodiceFiscale)
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
            VerticalizzazioneWsanagrafe vert = new VerticalizzazioneWsanagrafe(this._alias, "TT");

            string assemblyName = vert.SearchComponent;

            this._log.DebugFormat("Caricamento del componente di ricerca dall'assembly {0}", assemblyName);

            AnagrafeSearcher defaultSigeproSearcher = new AnagrafeSearcher(Constants.DefaultAnagrafeSearcherName);
            defaultSigeproSearcher.InitParams(this._idComune, this._alias, this._db);

            if (!vert.Attiva || assemblyName == "" || assemblyName.ToUpper() == Constants.DefaultAnagrafeSearcherName)
                return defaultSigeproSearcher;

            try
            {
                IAnagrafeSearcher searcher = this.CreaIstanzaSearcher(vert);

                if (searcher == null)
                {
                    throw new RicercheAnagraficheException($"Componente di ricerca non supportato {vert.SearchComponent}");
                }

                SigeproWrappedAnagrafeSearcher wrappedSearcher = new SigeproWrappedAnagrafeSearcher(defaultSigeproSearcher, searcher);

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

        private IAnagrafeSearcher CreaIstanzaSearcher(VerticalizzazioneWsanagrafe vert)
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
