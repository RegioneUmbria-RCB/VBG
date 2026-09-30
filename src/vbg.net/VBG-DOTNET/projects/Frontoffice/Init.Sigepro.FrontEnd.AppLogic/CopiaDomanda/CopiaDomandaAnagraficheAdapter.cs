using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.CopiaDomanda
{
    public class CopiaDomandaAnagraficheAdapter : ICopiaDomandaDatiAdapter
    {

        private Istanze? _istanzaTemplate { get; set; }


        protected ILogicaSincronizzazioneTipiSoggetto _logicaSincronizzazioneTipiSoggetto { get; set; }

        public CopiaDomandaAnagraficheAdapter(ILogicaSincronizzazioneTipiSoggetto logicaSincronizzazioneTipiSoggetto)
        {
            this._logicaSincronizzazioneTipiSoggetto = logicaSincronizzazioneTipiSoggetto;
        }

        public void Adatta(Istanze istanzaTemplate, DomandaOnline domanda)
        {
            this._istanzaTemplate = istanzaTemplate;

            this.AdattaRichiedente(domanda);

            this.AdattaAzienda(domanda);

            this.AdattaIntermediario(domanda);

            this.AdattaAltriSoggetti(domanda);

        }

        private void AdattaRichiedente(DomandaOnline domanda)
        {
            if (this._istanzaTemplate is null)
            {
                throw new Exception($"{nameof(this._istanzaTemplate)} è null!");
            }

            var richiedente = this._istanzaTemplate.Richiedente.ToAnagraficaDomanda();
            richiedente.TipoSoggetto = TipoSoggettoDomanda.NonDefinito;

            if (!String.IsNullOrEmpty(this._istanzaTemplate.FKCODICESOGGETTO))
            {
                richiedente.TipoSoggetto = new TipoSoggettoDomanda();
                richiedente.TipoSoggetto.Id = Convert.ToInt32(this._istanzaTemplate.FKCODICESOGGETTO);
                richiedente.TipoSoggetto.Descrizione = this._istanzaTemplate.TipoSoggetto?.TIPOSOGGETTO ?? "";
                richiedente.TipoSoggetto.Ruolo = TipoSoggettoDomanda.RuoloDaCodiceBackoffice(this._istanzaTemplate.FKCODICESOGGETTO);
            }

            domanda.WriteInterface.Anagrafiche.AggiungiOAggiorna(richiedente, this._logicaSincronizzazioneTipiSoggetto);
        }

        private void AdattaAzienda(DomandaOnline domanda)
        {
            if (this._istanzaTemplate is null)
            {
                throw new Exception($"{nameof(this._istanzaTemplate)} è null!");
            }

            if (this._istanzaTemplate.AziendaRichiedente == null)
                return;

            var azienda = this._istanzaTemplate.AziendaRichiedente.ToAnagraficaDomanda();
            azienda.TipoSoggetto = TipoSoggettoDomanda.NonDefinito;

            domanda.WriteInterface.Anagrafiche.AggiungiOAggiorna(azienda, this._logicaSincronizzazioneTipiSoggetto);
        }

        private void AdattaIntermediario(DomandaOnline domanda)
        {
            if (this._istanzaTemplate is null)
            {
                throw new Exception($"{nameof(this._istanzaTemplate)} è null!");
            }

            if (this._istanzaTemplate.Professionista == null)
                return;

            var professionista = this._istanzaTemplate.Professionista.ToAnagraficaDomanda();
            professionista.TipoSoggetto = TipoSoggettoDomanda.NonDefinito;

            domanda.WriteInterface.Anagrafiche.AggiungiOAggiorna(professionista, this._logicaSincronizzazioneTipiSoggetto);
        }

        private void AdattaAltriSoggetti(DomandaOnline domanda)
        {
            if (this._istanzaTemplate is null)
            {
                throw new Exception($"{nameof(this._istanzaTemplate)} è null!");
            }

            if (this._istanzaTemplate.Richiedenti == null)
                return;

            foreach (var istanzaRichiedente in this._istanzaTemplate.Richiedenti)
            {
                var richiedente = istanzaRichiedente.Richiedente.ToAnagraficaDomanda();
                richiedente.TipoSoggetto = TipoSoggettoDomanda.NonDefinito;

                if (istanzaRichiedente.TipoSoggetto != null)
                {
                    richiedente.TipoSoggetto = new TipoSoggettoDomanda();
                    richiedente.TipoSoggetto.Id = Convert.ToInt32(istanzaRichiedente.CODICETIPOSOGGETTO);
                    richiedente.TipoSoggetto.Descrizione = (istanzaRichiedente.TipoSoggetto != null) ? istanzaRichiedente.TipoSoggetto.TIPOSOGGETTO : null;
                    richiedente.TipoSoggetto.Ruolo = TipoSoggettoDomanda.RuoloDaCodiceBackoffice(istanzaRichiedente.CODICETIPOSOGGETTO);
                }

                if (istanzaRichiedente.AnagrafeCollegata != null)
                {
                    var azienda = istanzaRichiedente.AnagrafeCollegata.ToAnagraficaDomanda();
                    richiedente.CollegaAnagrafica(azienda);
                }

                domanda.WriteInterface.Anagrafiche.AggiungiOAggiorna(richiedente, this._logicaSincronizzazioneTipiSoggetto);
            }
        }
    }
}
