using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.AppLogic.Wrappers;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.SigeproPartialAdapters
{
    public class DocumentiSigeproAdapter : IIstanzaSigeproPartialAdapter
    {
        private readonly IConfigurazione<ParametriAllegati> _parametriAllegati;
        private readonly IDateTimeServiceWrapper _dateTimeServiceWrapper;
        private readonly AllegatiDomandaService _allegatiDomandaService;

        public DocumentiSigeproAdapter(IConfigurazione<ParametriAllegati> parametriAllegati, IDateTimeServiceWrapper dateTimeServiceWrapper, AllegatiDomandaService allegatiDomandaService)
        {
            this._parametriAllegati = parametriAllegati;
            this._dateTimeServiceWrapper = dateTimeServiceWrapper;
            this._allegatiDomandaService = allegatiDomandaService;
        }

        public void Adatta(IDomandaOnlineReadInterface origine, Istanze dst, IstanzaSigeproAdapterFlags flags)
        {
            List<DocumentiIstanza> documenti = new List<DocumentiIstanza>();

            // Delega a trasmettere
            if (origine.DelegaATrasmettere.Allegato != null)
            {
                var descrizioneDelega = this._parametriAllegati.Parametri.DescrizioneDelegaATrasmettere;
                descrizioneDelega = String.IsNullOrEmpty(descrizioneDelega) ? "Delega a trasmettere" : descrizioneDelega;

                documenti.Add(origine.DelegaATrasmettere.Allegato.ToDocumentiIstanza(descrizioneDelega, this._dateTimeServiceWrapper));
            }

            if (origine.DelegaATrasmettere.DocumentoIdentita != null)
            {
                var descrizione = this._parametriAllegati.Parametri.DescrizioneDelegaATrasmettere;

                descrizione = (String.IsNullOrEmpty(descrizione) ? "Delega a trasmettere" : descrizione) + " - documento d'identità";

                documenti.Add(origine.DelegaATrasmettere.DocumentoIdentita.ToDocumentiIstanza(descrizione, this._dateTimeServiceWrapper));
            }

            // Allegati dell'intervento
            documenti.AddRange(origine.Documenti
                                              .Intervento
                                              .GetAllegatiPresenti()
                                              .Select(documento => new DocumentiIstanza
                                              {
                                                  DOCUMENTO = documento.Descrizione,
                                                  DATA = this._dateTimeServiceWrapper.NewDate(),
                                                  CODICEOGGETTO = documento.AllegatoDellUtente.CodiceOggetto.ToString(),
                                                  NECESSARIO = documento.Richiesto ? "1" : "0",
                                                  Oggetto = new DocumentiIstanzaOggetti
                                                  {
                                                      NOMEFILE = documento.AllegatoDellUtente.NomeFile,
                                                      Md5 = documento.AllegatoDellUtente.Md5
                                                  }
                                                  /*
												  // L'allegato fa parte di una categoria?
												  if (!row.IsCategoriaNull() && !String.IsNullOrEmpty(row.CodiceCategoria) && row.CodiceCategoria != "-1")
												  {
													  doc.FKIDCATEGORIA = Convert.ToInt32(row.CodiceCategoria);
													  doc.Categoria = new AlberoProcDocumentiCat();
													  doc.Categoria.Id = Convert.ToInt32(row.CodiceCategoria);
													  doc.Categoria.Descrizione = row.Categoria;
												  }
												  */
                                              }));


            foreach (var riepilogodd in origine.RiepiloghiSchedeDinamiche.GetRiepiloghiInterventoConAllegatoUtente())
            {
                var rigaModello = origine.DatiDinamici.Modelli.Where(x => x.IdModello == riepilogodd.IdModello).FirstOrDefault();

                if (rigaModello == null)
                {
                    continue;
                }

                if (rigaModello.TipoFirma == ModelloDinamico.TipoFirmaEnum.Nessuna && !flags.AggiungiPdfSchedeAListaAllegati)
                {
                    continue;
                }

                documenti.Add(new DocumentiIstanza
                {
                    DOCUMENTO = riepilogodd.Descrizione,
                    DATA = this._dateTimeServiceWrapper.NewDate(),
                    CODICEOGGETTO = riepilogodd.AllegatoDellUtente.CodiceOggetto.ToString(),
                    Oggetto = new DocumentiIstanzaOggetti
                    {
                        NOMEFILE = riepilogodd.AllegatoDellUtente.NomeFile,
                        Md5 = riepilogodd.AllegatoDellUtente.Md5
                    }
                });
            }

            // Se presente aggiungo ila copia del bollettino che attesta l'avvenuto pagamento
            if (origine.Oneri.AttestazioneDiPagamento.Presente)
            {
                documenti.Add(new DocumentiIstanza
                {
                    DOCUMENTO = "Copia della ricevuta attestante l'avvenuto pagamento degli oneri",
                    DATA = this._dateTimeServiceWrapper.NewDate(),
                    CODICEOGGETTO = origine.Oneri.AttestazioneDiPagamento.CodiceOggetto.ToString(),
                    Oggetto = new DocumentiIstanzaOggetti
                    {
                        NOMEFILE = origine.Oneri.AttestazioneDiPagamento.NomeFile
                    }
                });
            }

            if (flags.CalcolaMD5Oggetti)
            {
                documenti.Where(x => x.Oggetto != null && String.IsNullOrEmpty(x.Oggetto.Md5)).ToList().ForEach(doc =>
                {
                    doc.Oggetto.Md5 = this._allegatiDomandaService.GetChecksumOggetto(Convert.ToInt32(doc.CODICEOGGETTO));
                });//.Select(x => Convert.ToInt32(x.CODICEOGGETTO));
            }


            dst.DocumentiIstanza = documenti.ToArray();
        }
    }
}
