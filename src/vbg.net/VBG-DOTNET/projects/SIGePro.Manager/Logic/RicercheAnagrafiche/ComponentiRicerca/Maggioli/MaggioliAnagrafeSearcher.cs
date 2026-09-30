using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier;
using log4net;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Maggioli
{
    public class MaggioliAnagrafeSearcher : AnagrafeSearcherAdrierBase
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(MaggioliAnagrafeSearcher));

        public MaggioliAnagrafeSearcher(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory) : base(verticalizzazioniFactory, bindingFactory, "MAGGIOLI")
        {

        }

        // Usare solo per i test
        protected void SetConfiguration(Dictionary<string, string> forcedConfig)
        {
            this._configuration = forcedConfig;
        }

        public override Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
        {
            this._log.Debug($"Passato per ByCodiceFiscaleImp: {codiceFiscale}");
            var anag = this.GetAnagrafe(codiceFiscale);
            if (anag == null)
            {
                return null;
            }

            var personaFisica = anag.Where(x => x.TIPOANAGRAFE == "F").FirstOrDefault();

            if (personaFisica == null)
            {
                this._log.Error($"L'Anagrafica trovata con identificativo {codiceFiscale} risulta essere una persona giuridica");
                throw new Exception($"L'Anagrafica trovata con identificativo {codiceFiscale} risulta essere una persona giuridica, riprovare la ricerca indicando tale tipologia");
            }
            return personaFisica;
        }

        private IEnumerable<Anagrafe> GetAnagrafe(string codFiscalePartitaIva)
        {
            var url = this.Configuration["URL"];
            var alias = this.Configuration["ALIAS"];

            this._log.Debug($"URL: {url}");
            this._log.Debug($"ALIAS: {alias}");

            var adapter = new RequestAdapter();
            var request = adapter.Adatta(codFiscalePartitaIva, alias);
            this._log.Debug($"xml request: {request[3]}");
            var response = this.ChiamaServizioGetAnagrafica(url, request);
            if (response == null)
            {
                return null;
            }
            var responseAdapter = new ResponseAdapter();
            return responseAdapter.Adatta(response);
        }

        protected virtual LeggiAnagraficaSikuelRisposta ChiamaServizioGetAnagrafica(string url, string[] request)
        {
            var service = new ServiceWrapper(url);
            return service.GetAnagrafica(request);
        }

        public override Anagrafe ByCodiceFiscaleImp(TipoPersona tipoPersona, string codiceFiscale)
        {
            this._log.Debug($"PASSATO PER ByCodiceFiscaleImp con TipoPersona valorizzato a :{tipoPersona}, codiceFiscale: {codiceFiscale}");

            if (String.IsNullOrEmpty(codiceFiscale))
            {
                throw new Exception("CODICE FISCALE NON INSERITO");
            }

            return this.ByCodiceFiscaleImp(codiceFiscale);
        }

        public override List<Init.SIGePro.Data.Anagrafe> ByNomeCognomeImp(string nome, string cognome)
        {
            throw new NotImplementedException();
        }

        public override Anagrafe ByPartitaIvaImp(string partitaIva)
        {
            this._log.Debug($"PASSATO PER ByPartitaIvaImp: {partitaIva}");

            if (String.IsNullOrEmpty(partitaIva))
            {
                throw new Exception("PARTITA IVA NON INSERITA");
            }
            var anag = this.GetAnagrafe(partitaIva);
            if (anag == null)
            {
                return null;
            }

            var personaGiuridica = anag.Where(x => x.TIPOANAGRAFE == "G").FirstOrDefault();

            if (personaGiuridica == null)
            {
                this._log.Error($"L'Anagrafica trovata con identificativo {partitaIva} risulta essere una persona fisica");
                throw new Exception($"L'Anagrafica trovata con identificativo {partitaIva} risulta essere una persona fisica, riprovare la ricerca indicando tale tipologia");
            }

            return personaGiuridica;
        }
    }

}
