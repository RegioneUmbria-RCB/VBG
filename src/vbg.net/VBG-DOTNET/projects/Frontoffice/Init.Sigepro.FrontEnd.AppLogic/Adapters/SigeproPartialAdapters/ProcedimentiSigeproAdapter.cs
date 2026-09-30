using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.SigeproPartialAdapters
{
    public class ProcedimentiSigeproAdapter : IIstanzaSigeproPartialAdapter
    {
        public void Adatta(IDomandaOnlineReadInterface src, Istanze istanza, IstanzaSigeproAdapterFlags flags)
        {
            var listaEndo = new List<IstanzeProcedimenti>();
            var endoDomanda = src.Endoprocedimenti
                                            .Endoprocedimenti
                                            .OrderBy(x => x.Ordine.OrdineFamiglia)
                                            .OrderBy(x => x.Ordine.OrdineTipo)
                                            .OrderBy(x => x.Ordine.OrdineEndo);

            foreach (var endo in endoDomanda)
            {
                var pro = new IstanzeProcedimenti
                {
                    CODICEINVENTARIO = endo.Codice.ToString(),
                    DATAATTIVAZIONE = DateTime.Now.Date,
                    Endoprocedimento = new InventarioProcedimenti
                    {
                        Codiceinventario = endo.Codice,
                        Procedimento = endo.Descrizione,
                        Codicenatura = endo.Natura?.Codice
                    },
                    IstanzeAllegati = src
                                        .Documenti
                                        .Endo
                                        .GetByIdEndo(endo.Codice)
                                        .Where(x => x.AllegatoDellUtente != null)
                                        .Select(x => new IstanzeAllegati
                                        {
                                            PRESENTE = "1",
                                            ALLEGATOEXTRA = x.Descrizione,
                                            CODICEINVENTARIO = endo.Codice.ToString(),
                                            CODICEOGGETTO = x.AllegatoDellUtente.CodiceOggetto.ToString(),
                                            Oggetto = new IstanzeAllegatiOggetto
                                            {
                                                NOMEFILE = x.AllegatoDellUtente.NomeFile
                                            }
                                        })
                                        .Union(
                                            src
                                                .RiepiloghiSchedeDinamiche
                                                .GetByCodiceEndo(endo.Codice)
                                                .Where(x => x.AllegatoDellUtente != null)
                                                .Select(x => new IstanzeAllegati
                                                {
                                                    PRESENTE = "1",
                                                    ALLEGATOEXTRA = x.Descrizione,
                                                    CODICEINVENTARIO = endo.Codice.ToString(),
                                                    CODICEOGGETTO = x.AllegatoDellUtente.CodiceOggetto.ToString(),
                                                    Oggetto = new IstanzeAllegatiOggetto
                                                    {
                                                        NOMEFILE = x.AllegatoDellUtente.NomeFile
                                                    }
                                                })
                                        )
                                        .Union(
                                            src
                                                .Endoprocedimenti
                                                .Acquisiti
                                                .Where(x => x.Codice == endo.Codice && endo.Riferimenti?.Allegato != null)
                                                .Select(x => new IstanzeAllegati
                                                {
                                                    PRESENTE = "1",
                                                    ALLEGATOEXTRA = x.Descrizione,
                                                    CODICEINVENTARIO = x.Codice.ToString(),
                                                    CODICEOGGETTO = x.Riferimenti.Allegato.CodiceOggetto.ToString(),
                                                    Oggetto = new IstanzeAllegatiOggetto
                                                    {
                                                        NOMEFILE = x.Riferimenti.Allegato.NomeFile
                                                    }

                                                })
                                        )
                                        .ToArray()
                };

                if (endo.Riferimenti != null)
                {
                    pro.PROT_NUM = endo.Riferimenti.NumeroAtto;
                    pro.PROT_DEL = endo.Riferimenti.DataAtto;
                    pro.TipoAtto = endo.Riferimenti.TipoTitolo != null ? endo.Riferimenti.TipoTitolo.Descrizione : String.Empty;
                    pro.Note = endo.Riferimenti.Note;
                    pro.RilasciatoDa = endo.Riferimenti.RilasciatoDa;
                }

                listaEndo.Add(pro);
            }

            istanza.EndoProcedimenti = listaEndo.ToArray();

        }
    }
}
