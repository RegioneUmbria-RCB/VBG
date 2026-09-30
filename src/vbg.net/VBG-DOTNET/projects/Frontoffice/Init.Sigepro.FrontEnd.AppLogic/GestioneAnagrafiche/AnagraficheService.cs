// -----------------------------------------------------------------------
// <copyright file="AnagraficheService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.Backend;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using System.Threading.Tasks;
using VBG.Frontend.AppLogic.WsAnagraficheService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche
{
    public class AnagraficheService : IAnagraficheService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioStrategy;
        private readonly ICittadinanzeService _cittadinanzeService;
        private readonly ILogicaSincronizzazioneTipiSoggetto _logicaSincronizzazioneTipiSoggetto;
        private readonly ITipiSoggettoService _tipiSoggettoService;
        private readonly IComuniService _comuniService;
        private readonly ILogicaRisoluzioneTecnico _logicaRisoluzioneTecnico;
        private readonly IAnagraficheBackendService _anagraficheBackendService;

        public AnagraficheService(ISalvataggioDomandaStrategy salvataggioStrategy,

                                    ICittadinanzeService cittadinanzeService,
                                    ILogicaSincronizzazioneTipiSoggetto logicaSincronizzazioneTipiSoggetto,
                                    ITipiSoggettoService tipiSoggettoService,
                                    IComuniService comuniService,
                                    ILogicaRisoluzioneTecnico logicaRisoluzioneTecnico,
                                    IAnagraficheBackendService anagraficheBackendService)
        {
            this._salvataggioStrategy = salvataggioStrategy ?? throw new System.ArgumentNullException(nameof(salvataggioStrategy));
            this._cittadinanzeService = cittadinanzeService ?? throw new System.ArgumentNullException(nameof(cittadinanzeService));
            this._logicaSincronizzazioneTipiSoggetto = logicaSincronizzazioneTipiSoggetto ?? throw new System.ArgumentNullException(nameof(logicaSincronizzazioneTipiSoggetto));
            this._tipiSoggettoService = tipiSoggettoService ?? throw new System.ArgumentNullException(nameof(tipiSoggettoService));
            this._comuniService = comuniService ?? throw new System.ArgumentNullException(nameof(comuniService));
            this._logicaRisoluzioneTecnico = logicaRisoluzioneTecnico ?? throw new System.ArgumentNullException(nameof(logicaRisoluzioneTecnico));
            this._anagraficheBackendService = anagraficheBackendService ?? throw new System.ArgumentNullException(nameof(anagraficheBackendService));
        }


        public void CollegaAziendaAdAnagrafica(int idDomanda, int idAnagrafica, int idAziendaCollegata)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.Anagrafiche.CollegaAziendaAdAnagrafica(idAnagrafica, idAziendaCollegata);

            this._salvataggioStrategy.Salva(domanda);
        }

        public void AggiungiAnagraficaConSoggettoCollegato(int idDomanda, string cfAnagrafica, int codiceTipoSoggettoAnagrafica, string cfAnagraficaCollegata, int codiceTipoSoggettoAnagraficaCollegata)
        {
            var anagrafica = this._anagraficheBackendService.RicercaAnagraficaBackoffice(TipoPersonaEnum.Fisica, cfAnagrafica);
            var anagraficaCollegata = this._anagraficheBackendService.RicercaAnagraficaBackoffice(TipoPersonaEnum.Giuridica, cfAnagraficaCollegata);

            var anagraficaDomanda = this.AnagrafeToAnagraficaDomanda(anagrafica, codiceTipoSoggettoAnagrafica);
            var anagraficaCollegataDomanda = this.AnagrafeToAnagraficaDomanda(anagraficaCollegata, codiceTipoSoggettoAnagraficaCollegata);

            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.Anagrafiche.AggiungiAnagraficaConSoggettoCollegato(anagraficaDomanda, anagraficaCollegataDomanda, this._logicaSincronizzazioneTipiSoggetto);

            this._salvataggioStrategy.Salva(domanda);
        }

        private AnagraficaDomanda AnagrafeToAnagraficaDomanda(Anagrafe anagrafica, int codiceTipoSoggetto)
        {
            var anagraficaAdapter = new AnagrafeAdapter(anagrafica, this._comuniService);
            var anagraficaDomanda = anagraficaAdapter.ToAnagraficaDomanda();
            var tipoSoggettoAnagrafica = this._tipiSoggettoService.GetById(codiceTipoSoggetto);

            anagraficaDomanda.TipoSoggetto = tipoSoggettoAnagrafica.ToTipoSoggettoDomanda();

            return anagraficaDomanda;
        }

        public void RimuoviAnagrafica(int idDomanda, int idAnagrafica)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            this.RimuoviAnagrafica(domanda, idAnagrafica);

            this._salvataggioStrategy.Salva(domanda);
        }

        public void RimuoviAnagrafica(DomandaOnline domanda, int idAnagrafica)
        {
            domanda.WriteInterface.Anagrafiche.Elimina(idAnagrafica);
        }

        public int SalvaAnagrafica(int idDomanda, AnagraficaDomanda row)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            var idAna = this.SalvaAnagrafica(domanda, row);

            this._salvataggioStrategy.Salva(domanda);

            return idAna;
        }

        public async Task<int> SalvaAnagraficaAsync(int idDomanda, AnagraficaDomanda row, ILogicaSincronizzazioneTipiSoggetto? logicaSincronizzazioneTipiSoggetto = null)
        {
            var domanda = await this._salvataggioStrategy.GetByIdAsync(idDomanda);

            var idAna = this.SalvaAnagrafica(domanda, row, logicaSincronizzazioneTipiSoggetto);

            await this._salvataggioStrategy.SalvaAsync(domanda);

            return idAna;
        }

        public int SalvaAnagrafica(DomandaOnline domanda, AnagraficaDomanda row, ILogicaSincronizzazioneTipiSoggetto? logicaSincronizzazioneTipiSoggetto = null)
        {
            return domanda.WriteInterface.Anagrafiche.AggiungiOAggiorna(row, logicaSincronizzazioneTipiSoggetto ?? this._logicaSincronizzazioneTipiSoggetto);
        }


        public void SincronizzaFlagsTipiSoggetto(int idDomanda, ILogicaSincronizzazioneTipiSoggetto? logicaSincronizzazioneTipiSoggetto = null)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.Anagrafiche.Sincronizza(logicaSincronizzazioneTipiSoggetto ?? this._logicaSincronizzazioneTipiSoggetto);

            this._salvataggioStrategy.Salva(domanda);
        }

        public void VerificaFlagsCittadiniExtracomunitari(int idDomanda)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.Anagrafiche.VerificaFlagsCittadiniExtracomunitari(this._cittadinanzeService);

            this._salvataggioStrategy.Salva(domanda);
        }


        #region IAnagraficheService Members




        //public void NuovaRegistrazione(string idComune, Anagrafe anagrafe)
        //{
        //    this._anagrafeRepository.NuovaRegistrazione(idComune, anagrafe);
        //}



        //public void ModificaDatianagrafici(string idComune, AnagraficaUtente anagrafica)
        //{
        //    this._anagrafeVbgService.ModificaDatianagrafici(anagrafica);
        //}


        //public void ModificaPassword(string idComune, int codiceAnagrafe, string vecchiaPassword, string nuovaPassword, string confermaPassword)
        //{
        //    this._anagrafeVbgService.ModificaPassword(codiceAnagrafe, vecchiaPassword, nuovaPassword, confermaPassword);
        //}

        #endregion

        #region IAnagraficheService Members


        //public Anagrafe RicercaAnagraficaBackoffice(TipoPersonaEnum tipoPersona, string codiceFiscale)
        //{
        //    return this._anagrafeRepo.RicercaAnagrafica(tipoPersona, codiceFiscale);
        //}

        public AnagraficaDomanda GetById(int idDomanda, int idAnagrafica)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            return domanda.ReadInterface.Anagrafiche.GetById(idAnagrafica);
        }

        public AnagraficaDomanda GetRichiedente(int idDomanda)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            return domanda.ReadInterface.Anagrafiche.GetRichiedente();
        }

        public AnagraficaDomanda GetTecnico(int idDomanda)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            return domanda.ReadInterface.Anagrafiche.GetTecnico(this._logicaRisoluzioneTecnico);

        }

        #endregion

    }
}
