using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.MittentiDestinatari.GestioneAnagrafiche
{
    public class AnagraficheMONFALCONE : AnagraficheBase, IGestioneAnagrafiche
    {
        public string Nominativo { get; private set; }
        //TipoGestioneAnagraficaEnum.TipoAggiornamento _tipoAggiornamento;

        public AnagraficheMONFALCONE(ProtocolloLogs logs, InsielVerticalizzazioniConfiguration vert) : base(logs, vert)
        {
            //this._tipoAggiornamento = vert.TipoAggiornamentoAnagrafica;
        }

        public void Gestisci(IAnagraficaAmministrazione anagrafica, ProtocolloService srv)
        {
            this.Nominativo = anagrafica.Denominazione.Replace("  ", " ");

            if (anagrafica.ComuneResidenza == null && String.IsNullOrEmpty(anagrafica.Localita))
            {
                return;
            }
            else if (anagrafica.ComuneResidenza == null && !String.IsNullOrEmpty(anagrafica.Localita))
            {
                this.Nominativo += anagrafica.Localita.ToUpper() == "MONFALCONE" ? " CITTA'" : String.Format(" {0}", anagrafica.Localita.ToUpper());
            }
            else
            {
                if (!String.IsNullOrEmpty(anagrafica.Localita) && anagrafica.Localita != anagrafica.ComuneResidenza.DenominazioneComune)
                    this.Nominativo += String.Format(" {0}", anagrafica.Localita.ToUpper());
                else
                {
                    if (anagrafica.ComuneResidenza.DenominazioneComune == "MONFALCONE")
                        this.Nominativo += " CITTA'";
                    else if (anagrafica.ComuneResidenza.DenominazioneComune == anagrafica.ComuneResidenza.Provincia)
                        this.Nominativo += String.Format(" {0}", anagrafica.ComuneResidenza.SiglaProvincia);
                    else
                        this.Nominativo += String.Format(" {0}", anagrafica.ComuneResidenza.DenominazioneComune);
                }
            }

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
                        Valore = Nominativo,
                        Relazione = Enum.OperatoreRelazionaleUIC.uguale
                    }
                });

                if (responseLeggi == null || responseLeggi.Anagrafiche.Count() == 0)
                {
                    base.InserisciAnagraficaDefault(anagrafica, srv, this.Nominativo);
                }
                else
                {
                    base.AggiornaAnagrafica(anagrafica, srv, this.Nominativo, responseLeggi.Anagrafiche.First());
                }
            }
        }
    }
}
