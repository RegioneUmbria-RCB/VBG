using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.MittentiDestinatari.GestioneAnagrafiche
{
    public class AnagraficheRICERCADESC : AnagraficheBase, IGestioneAnagrafiche
    {
        //TipoGestioneAnagraficaEnum.TipoGestione _tipo;
        //TipoGestioneAnagraficaEnum.TipoAggiornamento _tipoAggiornamento;

        public AnagraficheRICERCADESC(ProtocolloLogs logs, InsielVerticalizzazioniConfiguration vert) : base(logs, vert)
        {
            //this._tipo = vert.TipoGestionePec;
            //this._tipoAggiornamento = vert.TipoAggiornamentoAnagrafica;
        }

        public string Nominativo { get; private set; }

        public void Gestisci(IAnagraficaAmministrazione anagrafica, ProtocolloService srv)
        {
            string nominativo = anagrafica.Denominazione.Replace("  ", " ");

            if (base._vert.TipoGestionePec == TipoGestioneAnagraficaEnum.TipoGestione.PEC)
            {
                nominativo = String.Format("{0} ({1})", anagrafica.Denominazione.Replace("  ", " "), anagrafica.Pec);
            }
            else if (base._vert.TipoGestionePec == TipoGestioneAnagraficaEnum.TipoGestione.CODICE_FISCALE)
            {
                string cfPiva = String.IsNullOrEmpty(anagrafica.CodiceFiscale) ? anagrafica.PartitaIva : anagrafica.CodiceFiscale;

                if (!String.IsNullOrEmpty(cfPiva))
                {
                    nominativo = String.Format("{0} ({1})", anagrafica.Denominazione.Replace("  ", " "), cfPiva);
                }
            }

            this.Nominativo = nominativo;

            if (this._vert.UsaPredisponiAnagrafica)
            {
                this.Nominativo = CercaInserisciAggiornaAnagrafica(anagrafica, srv, this.Nominativo);
            }
            else
            {
                var responseLeggi = srv.LeggiAnagrafiche(new InterrogaAnagraficaRequest
                {
                    DescrizioneAnagrafica = new AnagraficaRicercaDescAna
                    {
                        Valore = nominativo,
                        Relazione = OperatoreRelazionaleUIC.uguale
                    }
                });

                if (responseLeggi == null || responseLeggi.Anagrafiche.Count == 0)
                {
                    base.InserisciAnagraficaDefault(anagrafica, srv, nominativo);
                }
                else
                {
                    base.AggiornaAnagrafica(anagrafica, srv, nominativo, responseLeggi.Anagrafiche.First());
                }
            }
        }
    }
}
