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
    public class AnagraficheRICERCACF : AnagraficheBase, IGestioneAnagrafiche
    {
        ProtocolloLogs _logs;
        TipoGestioneAnagraficaEnum.TipoAggiornamento _tipoAggiornamento;

        public AnagraficheRICERCACF(ProtocolloLogs logs, TipoGestioneAnagraficaEnum.TipoAggiornamento tipoAggiornamento)
        {
            this._logs = logs;
            this._tipoAggiornamento = tipoAggiornamento;
        }

        public string Nominativo { get; private set; }

        public void Gestisci(IAnagraficaAmministrazione anagrafica, ProtocolloService srv)
        {
            this.Nominativo = anagrafica.Denominazione;

            if (String.IsNullOrEmpty(anagrafica.CodiceFiscale) && String.IsNullOrEmpty(anagrafica.PartitaIva))
            {
                throw new Exception($"L'ANAGRAFICA {anagrafica.Denominazione} NON HA VALORIZZATO NE' IL CODICE FISCALE NE' LA PARTITA IVA");
            }

            if (!String.IsNullOrEmpty(anagrafica.PartitaIva))
            {
                this._logs.Info($"RICERCA PER PARTITA IVA, {anagrafica.PartitaIva}");
                //Ricerca per partita Iva
                var responseLeggi = srv.LeggiAnagrafiche(new InterrogaAnagraficaRequest
                {
                    anagrafica = new AnagraficaRicerca
                    {
                        piva = anagrafica.PartitaIva,
                    }
                });

                if (responseLeggi != null)
                {
                    var response = responseLeggi.First();
                    this._logs.Info($"TROVATA ANAGRAFICA {response.descAna}");

                    this.Nominativo = response.descAna;
                    base.AggiornaAnagrafica(anagrafica, srv, response.descAna, response, this._tipoAggiornamento, _logs);

                    return;

                }

                this._logs.Info($"NESSUNA ANAGRAFICA TROVATA PER PARTITA IVA {anagrafica.PartitaIva}");
            }

            if (!String.IsNullOrEmpty(anagrafica.CodiceFiscale))
            {
                this._logs.Info($"RICERCA PER CODICE FISCALE, {anagrafica.CodiceFiscale}");
                //Ricerca per codice fiscale
                var responseLeggi = srv.LeggiAnagrafiche(new InterrogaAnagraficaRequest
                {
                    anagrafica = new AnagraficaRicerca
                    {
                        codfis = anagrafica.CodiceFiscale,
                    }
                });

                if (responseLeggi != null)
                {
                    var response = responseLeggi.First();
                    this._logs.Info($"TROVATA ANAGRAFICA {response.descAna}");

                    this.Nominativo = response.descAna;
                    base.AggiornaAnagrafica(anagrafica, srv, response.descAna, response, this._tipoAggiornamento, _logs);

                    return;
                }
                this._logs.Info($"NESSUNA ANAGRAFICA TROVATA PER CODICE FISCALE {anagrafica.CodiceFiscale}");
            }

            base.InserisciAnagraficaDefault(anagrafica, srv, anagrafica.Denominazione);
        }
    }
}
