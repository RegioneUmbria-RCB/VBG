using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.RicercaPraticheWs;
using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneRicercaPratiche
{
    public class CopiaDatiDomandaFlags
    {
        public bool CopiaAnagrafiche { get; set; } = true;
        public bool CopiaLocalizzazioni { get; set; } = true;
    }

    public class CopiaDatiDomandaDaRisultatoRicercaService
    {
        private readonly ILogicaSincronizzazioneTipiSoggetto _logicaSincronizzazioneTipiSoggetto;

        public CopiaDatiDomandaDaRisultatoRicercaService(ILogicaSincronizzazioneTipiSoggetto logicaSincronizzazioneTipiSoggetto)
        {
            this._logicaSincronizzazioneTipiSoggetto = logicaSincronizzazioneTipiSoggetto;
        }

        internal void ImpostaDatiDomandaDaRicerca(DomandaOnline domanda, RisultatoRicercaPratiche risultatoRicerca, CopiaDatiDomandaFlags flags)
        {
            if (flags.CopiaAnagrafiche && risultatoRicerca.Anagrafiche != null)
            {
                this.CopiaAnagrafiche(domanda, risultatoRicerca);
            }

            if (flags.CopiaLocalizzazioni && risultatoRicerca.Localizzazioni != null)
            {
                this.CopiaLocalizzazioni(domanda, risultatoRicerca.Localizzazioni);
            }
        }

        private void CopiaLocalizzazioni(DomandaOnline domanda, StradarioIstanzaTrovata[] localizzazioni)
        {
            domanda.WriteInterface.Localizzazioni.EliminaTutte();

            foreach (var l in localizzazioni)
            {
                var dst = new NuovaLocalizzazione(Convert.ToInt32(l.CodiceStradario), l.Stradario, l.Civico, l.Esponente)
                {
                    Cap = l.Cap,
                    Circoscrizione = l.Circoscrizione,
                    CodiceViario = l.CodViario,
                    Colore = l.Colore,
                    EsponenteInterno = l.EsponenteInterno,
                    Fabbricato = l.Fabbricato,
                    Interno = l.Interno,
                    Km = l.Km,
                    Latitudine = l.Latitudine,
                    Longitudine = l.Longitudine,
                    Note = l.Note,
                    Piano = l.Piano,
                    Scala = l.Scala,
                    TipoLocalizzazione = l.TipoLocalizzazione
                };

                if (l.Mappali != null)
                {
                    foreach (var m in l.Mappali)
                    {
                        var mappale = new NuovoRiferimentoCatastale(m.CodiceTipoCatasto, m.TipoCatasto, m.Foglio, m.Particella, m.Sub, m.Sezione);

                        domanda.WriteInterface.Localizzazioni.AggiungiLocalizzazioneConRiferimentiCatastali(dst, mappale);
                    }
                }
                else
                {
                    domanda.WriteInterface.Localizzazioni.AggiungiLocalizzazione(dst);
                }
            }
        }

        private void CopiaAnagrafiche(DomandaOnline domanda, RisultatoRicercaPratiche risultatoRicerca)
        {
            // domanda.WriteInterface.Anagrafiche.EliminaTutto();

            foreach (var a in risultatoRicerca.Anagrafiche)
            {
                var anagraficaDomanda = this.CopyPropertiesToClass(a);

                domanda.WriteInterface.Anagrafiche.AggiungiOAggiorna(anagraficaDomanda, this._logicaSincronizzazioneTipiSoggetto);
            }
        }

        private AnagraficaDomanda CopyPropertiesToClass(AnagraficaIstanzaTrovata a)
        {
            // è un po'una schifezza perché copia tutte le proprietà dell'anagrafica trovata nelle proprietà
            // di un'istanza della classe Anagrafe. In teoria sono lo stesso tipo di classe ma la classe non
            // può essere condivisa tra i due servizidato che il servizio che le recupera non è wcf 

            AreaRiservataService.Anagrafe dst = new AreaRiservataService.Anagrafe();
            var anagDictionary = a.Proprieta.ToDictionary(x => x.Chiave);

            foreach (var prop in dst.GetType().GetProperties())
            {
                if (anagDictionary.TryGetValue(prop.Name, out var val))
                {
                    if (val.IsDateTime)
                    {
                        prop.SetValue(dst, DateTime.ParseExact(val.Valore, "yyyyMMdd", null), null);
                    }
                    else
                    {
                        var destType = prop.PropertyType;

                        var nullableType = Nullable.GetUnderlyingType(prop.PropertyType);

                        if (nullableType != null)
                        {
                            destType = nullableType;
                        }

                        prop.SetValue(dst, Convert.ChangeType(val.Valore, destType), null);
                    }
                }
            }

            return new AnagrafeAdapter(dst).ToAnagraficaDomanda();
        }
    }
}
