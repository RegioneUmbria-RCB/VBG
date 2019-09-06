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
    public class AnagraficheRICERCADESC : AnagraficheBase, IGestioneAnagrafiche
    {
        TipoGestioneAnagraficaEnum.TipoGestione _tipo;
        TipoGestioneAnagraficaEnum.TipoAggiornamento _tipoAggiornamento;
        ProtocolloLogs _logs;

        public AnagraficheRICERCADESC(TipoGestioneAnagraficaEnum.TipoGestione tipo, TipoGestioneAnagraficaEnum.TipoAggiornamento tipoAggiornamento, ProtocolloLogs logs)
        {
            this._tipo = tipo;
            this._tipoAggiornamento = tipoAggiornamento;
            this._logs = logs;
        }

        public string Nominativo { get; private set; }

        public void Gestisci(IAnagraficaAmministrazione anagrafica, ProtocolloService srv)
        {
            string nominativo = anagrafica.Denominazione.Replace("  ", " ");

            if (this._tipo == TipoGestioneAnagraficaEnum.TipoGestione.PEC)
            {
                nominativo = String.Format("{0} ({1})", anagrafica.Denominazione.Replace("  ", " "), anagrafica.Pec);
            }
            else if (this._tipo == TipoGestioneAnagraficaEnum.TipoGestione.CODICE_FISCALE)
            {
                string cfPiva = String.IsNullOrEmpty(anagrafica.CodiceFiscale) ? anagrafica.PartitaIva : anagrafica.CodiceFiscale;

                if (!String.IsNullOrEmpty(cfPiva))
                {
                    nominativo = String.Format("{0} ({1})", anagrafica.Denominazione.Replace("  ", " "), cfPiva);
                }
            }

            this.Nominativo = nominativo;

            var responseLeggi = srv.LeggiAnagrafiche(new InterrogaAnagraficaRequest
            {
                anagrafica = new AnagraficaRicerca
                {
                    descAna = new AnagraficaRicercaDescAna
                    {
                        valore = nominativo,
                        relazione = operatoreRelazionaleUIC.uguale,
                        relazioneSpecified = true
                    }
                }
            });

            if (responseLeggi == null || responseLeggi.Count() == 0)
            {
                base.InserisciAnagraficaDefault(anagrafica, srv, nominativo);
            }
            else
            {
                base.AggiornaAnagrafica(anagrafica, srv, nominativo, responseLeggi.First(), this._tipoAggiornamento, this._logs);
            }
        }
    }
}
