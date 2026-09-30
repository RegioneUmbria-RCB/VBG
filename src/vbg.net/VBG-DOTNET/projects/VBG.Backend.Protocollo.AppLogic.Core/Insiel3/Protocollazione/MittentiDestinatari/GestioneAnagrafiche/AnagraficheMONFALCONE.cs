using ProtocolloInsielService3;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;


namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Protocollazione.MittentiDestinatari.GestioneAnagrafiche
{
    public class AnagraficheMONFALCONE : AnagraficheBase, IGestioneAnagrafiche
    {
        public string Nominativo { get; private set; }

        ProtocolloLogs _logs;
        //TipoGestioneAnagraficaEnum.TipoAggiornamento _tipoAggiornamento;

        public AnagraficheMONFALCONE(ProtocolloLogs logs, InsielVerticalizzazioniConfiguration vert) : base(vert)
        {
            this._logs = logs;
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

            var responseLeggi = srv.LeggiAnagrafiche(new InterrogaAnagraficaRequest
            {
                anagrafica = new AnagraficaRicerca
                {
                    descAna = new AnagraficaRicercaDescAna
                    {
                        valore = Nominativo,
                        relazione = operatoreRelazionaleUIC.uguale,
                        relazioneSpecified = true
                    }
                }
            });

            if (responseLeggi == null || responseLeggi.Count() == 0)
            {
                base.InserisciAnagraficaDefault(anagrafica, srv, this.Nominativo);
            }
            else
            {
                base.AggiornaAnagrafica(anagrafica, srv, this.Nominativo, responseLeggi.First(), this._logs);
            }
        }
    }
}
