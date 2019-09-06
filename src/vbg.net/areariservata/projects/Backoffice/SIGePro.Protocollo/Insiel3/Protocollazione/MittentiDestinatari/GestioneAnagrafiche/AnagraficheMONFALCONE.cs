using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari.GestioneAnagrafiche
{
    public class AnagraficheMONFALCONE : AnagraficheBase, IGestioneAnagrafiche
    {
        public string Nominativo { get; private set; }

        ProtocolloLogs _logs;
        TipoGestioneAnagraficaEnum.TipoAggiornamento _tipoAggiornamento;

        public AnagraficheMONFALCONE(ProtocolloLogs logs, TipoGestioneAnagraficaEnum.TipoAggiornamento tipoAggiornamento)
        {
            this._logs = logs;
            this._tipoAggiornamento = tipoAggiornamento;
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
                if (!String.IsNullOrEmpty(anagrafica.Localita) && anagrafica.Localita != anagrafica.ComuneResidenza.COMUNE)
                    this.Nominativo += String.Format(" {0}", anagrafica.Localita.ToUpper());
                else
                {
                    if (anagrafica.ComuneResidenza.COMUNE == "MONFALCONE")
                        this.Nominativo += " CITTA'";
                    else if (anagrafica.ComuneResidenza.COMUNE == anagrafica.ComuneResidenza.PROVINCIA)
                        this.Nominativo += String.Format(" {0}", anagrafica.ComuneResidenza.SIGLAPROVINCIA);
                    else
                        this.Nominativo += String.Format(" {0}", anagrafica.ComuneResidenza.COMUNE);
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
                base.AggiornaAnagrafica(anagrafica, srv, this.Nominativo, responseLeggi.First(), this._tipoAggiornamento, this._logs);
            }
        }
    }
}
