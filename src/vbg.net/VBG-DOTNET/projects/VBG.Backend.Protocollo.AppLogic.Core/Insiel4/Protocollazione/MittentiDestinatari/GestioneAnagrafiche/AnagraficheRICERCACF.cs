using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.MittentiDestinatari.GestioneAnagrafiche
{
    public class AnagraficheRICERCACF : AnagraficheBase, IGestioneAnagrafiche
    {
        //TipoGestioneAnagraficaEnum.TipoAggiornamento _tipoAggiornamento;

        public AnagraficheRICERCACF(ProtocolloLogs logs, InsielVerticalizzazioniConfiguration vert) : base(logs, vert)
        {
            //this._tipoAggiornamento = vert.TipoAggiornamentoAnagrafica;
        }

        public string Nominativo { get; private set; }

        public void Gestisci(IAnagraficaAmministrazione anagrafica, ProtocolloService srv)
        {
            this.Nominativo = anagrafica.Denominazione;

            
            // sembra che il metodo PredisponiAnagrafica riesca a trovare l'anagrafica anche soltanto col nominativo
            if (this._vert.UsaPredisponiAnagrafica)
            {
                this.Nominativo = CercaInserisciAggiornaAnagrafica(anagrafica, srv, this.Nominativo);
            }
            else
            {
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
                        PartitaIva = anagrafica.PartitaIva
                    });

                    if (responseLeggi != null)
                    {
                        var response = responseLeggi.Anagrafiche.First();
                        this._logs.Info($"TROVATA ANAGRAFICA {response.DescrizioneAnagrafica}");

                        this.Nominativo = response.DescrizioneAnagrafica;
                        base.AggiornaAnagrafica(anagrafica, srv, response.DescrizioneAnagrafica, response);

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
                        CodiceFiscale = anagrafica.CodiceFiscale
                    });

                    if (responseLeggi != null)
                    {
                        var response = responseLeggi.Anagrafiche.First();
                        this._logs.Info($"TROVATA ANAGRAFICA {response.DescrizioneAnagrafica}");

                        this.Nominativo = response.DescrizioneAnagrafica;
                        base.AggiornaAnagrafica(anagrafica, srv, response.DescrizioneAnagrafica, response);

                        return;
                    }
                    this._logs.Info($"NESSUNA ANAGRAFICA TROVATA PER CODICE FISCALE {anagrafica.CodiceFiscale}");
                }
            }
        }
    }
}
