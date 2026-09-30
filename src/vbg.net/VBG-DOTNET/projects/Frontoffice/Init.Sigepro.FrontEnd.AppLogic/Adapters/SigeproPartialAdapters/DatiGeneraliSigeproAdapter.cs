using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TraduzioneIdComune;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using VBG.Shared.Infrastructure.Caching;
using Init.SIGePro.Manager.DTO.Interventi;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.SigeproPartialAdapters
{
    public class DatiGeneraliSigeproAdapter : IIstanzaSigeproPartialAdapter
    {
        private readonly IInterventiRepository _alberoprocRepository;
        private readonly IComuniService _comuniService;
        private readonly IConfigurazioneAreaRiservataRepository _configurazioneAreaRiservataRepository;
        private readonly IStatiIstanzaRepository _statiIstanzaRepository;
        private readonly IAliasToIdComuneTranslator _aliasToIdComuneTranslator;
        private readonly IApplicationCache _applicationCache;

        public DatiGeneraliSigeproAdapter(IInterventiRepository alberoprocRepository, IComuniService comuniService, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository,
            IStatiIstanzaRepository statiIstanzaRepository, IAliasToIdComuneTranslator aliasToIdComuneTranslator, IApplicationCache applicationCache)
        {
            this._alberoprocRepository = alberoprocRepository;
            this._comuniService = comuniService;
            this._configurazioneAreaRiservataRepository = configurazioneAreaRiservataRepository;
            this._statiIstanzaRepository = statiIstanzaRepository;
            this._aliasToIdComuneTranslator = aliasToIdComuneTranslator;
            this._applicationCache = applicationCache;
        }

        public void Adatta(IDomandaOnlineReadInterface src, Istanze dst, IstanzaSigeproAdapterFlags flags)
        {
            var aliasComune = src.AltriDati.AliasComune;
            var software = src.AltriDati.Software;

            dst.IDCOMUNE = this._aliasToIdComuneTranslator.Translate(aliasComune);
            dst.SOFTWARE = software;
            dst.CODICEPRATICATEL = src.AltriDati.IdentificativoDomanda;

            if (src.AltriDati.Intervento != null)
            {
                var codiceIntervento = src.AltriDati.Intervento.Codice;

                dst.CODICEINTERVENTOPROC = codiceIntervento.ToString();
                var datiIntervento = this.GetDettagliIntervento(aliasComune, codiceIntervento);

                // Risolvo la descrizione dell'intervento
                dst.Intervento = new Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService.AlberoProc
                {
                    SC_DESCRIZIONE = src.AltriDati.Intervento.Descrizione,
                    SC_NOTE = datiIntervento.Note
                };
            }

            if (!String.IsNullOrEmpty(src.AltriDati.CodiceComune))
            {
                dst.CODICECOMUNE = src.AltriDati.CodiceComune;

                var com = this._comuniService.GetByCodiceComune(dst.CODICECOMUNE);

                if (com != null)
                {
                    dst.ComuneIstanza = new Comuni
                    {
                        CODICECOMUNE = com.CodiceComune,
                        COMUNE = com.Comune,
                        PROVINCIA = com.Provincia,
                        SIGLAPROVINCIA = com.SiglaProvincia
                    };
                }
            }

            dst.LAVORIESTESA = src.AltriDati.Note;
            dst.LAVORI = src.AltriDati.DescrizioneLavori;
            dst.DATA = DateTime.Now.Date;
            dst.CHIUSURA = this._configurazioneAreaRiservataRepository.DatiConfigurazione(aliasComune, software).StatoInizialeIstanza;

            if (!String.IsNullOrEmpty(dst.CHIUSURA))
            {
                dst.Stato = new StatiIstanza
                {
                    Codicestato = dst.CHIUSURA,
                    Stato = this._statiIstanzaRepository.GetById(software, dst.CHIUSURA).Stato
                };
            }
            ;
        }

        private InterventoDto GetDettagliIntervento(string aliasComune, int codiceIntervento)
        {
            var cacheKey = $"IstanzeAdapter.{aliasComune}.{codiceIntervento}";

            return this._applicationCache.GetOrAdd(
                cacheKey,
                () => this._alberoprocRepository.GetDettagliIntervento(codiceIntervento, new AmbitoRicercaAreaRiservata(true), false)
            );
        }
    }
}
