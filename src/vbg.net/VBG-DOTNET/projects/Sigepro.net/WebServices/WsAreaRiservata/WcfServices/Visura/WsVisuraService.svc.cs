using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using log4net;
using PersonalLib2.Sql;
using System;
using System.Linq;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Visura
{
    // NOTA: è possibile utilizzare il comando "Rinomina" del menu "Refactoring" per modificare il nome di classe "WsVisuraService" nel codice, nel file svc e nel file di configurazione contemporaneamente.
    // NOTA: per avviare il client di prova WCF per testare il servizio, selezionare WsVisuraService.svc o WsVisuraService.svc.cs in Esplora soluzioni e avviare il debug.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsVisuraService : WcfServiceBase, IWsVisuraService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WsVisuraService));

        public Visura GetDettaglioPratica(string token, int codiceIstanza)
        {
            AuthenticationInfo ai = this.CheckToken(token);

            if (ai == null)
                throw new InvalidTokenException(token);


            try
            {
                Init.SIGePro.Data.Istanze istanza = new IstanzeMgr(ai.CreateDatabase()).GetById(ai.IdComune, codiceIstanza, useForeignEnum.Recoursive);

                return this.FromIstanza(istanza);

            }
            catch (Exception ex)
            {
                this._log.Error($"GetDettaglioPratica: {ex}");
                throw new Exception(ex.Message);
            }
        }

        public Visura GetDettaglioPraticaByUuid(string token, string uuid)
        {
            AuthenticationInfo ai = this.CheckToken(token);

            if (ai == null)
                throw new InvalidTokenException(token);

            try
            {
                using (var db = ai.CreateDatabase())
                {
                    var mgr = new IstanzeMgr(db);
                    var riferimentiIstanza = mgr.GetCodiceIstanzaDaUuid(ai.IdComune, uuid);

                    var istanza = mgr.GetById(riferimentiIstanza.IdComune, riferimentiIstanza.CodiceIstanza, useForeignEnum.Recoursive);
                    istanza.Movimenti = istanza.Movimenti?.OrderBy(x => x.DATA.GetValueOrDefault(new DateTime(1970, 1, 1))).ToList();


                    var alberoProcMgr = new AlberoProcMgr(db);

                    var datiAlbero = alberoProcMgr.GetTitoloInterventoDaMetadati(ai.IdComune, istanza.Intervento.Sc_id.Value);

                    if (datiAlbero != null && datiAlbero.Titolo != istanza.Intervento.SC_DESCRIZIONE)
                    {
                        istanza.Intervento.DescrizioneCompleta = datiAlbero.Titolo;
                    }

                    return this.FromIstanza(istanza);
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat($"Errore nella visura della pratica {uuid}: {ex}");
                throw;
            }
        }

        private Visura FromIstanza(Init.SIGePro.Data.Istanze istanza)
        {
            Visura visura = new Visura()
            {
                Codice = istanza.CODICEISTANZA,
                Software = istanza.SOFTWARE,
                IdComune = istanza.IDCOMUNE,
                Data = istanza.DATA,
                DataProtocollo = istanza.DATAPROTOCOLLO,
                NumeroIstanza = istanza.NUMEROISTANZA,
                NumeroProtocollo = istanza.NUMEROPROTOCOLLO,
                Lavori = istanza.LAVORI,
                PosizioneArchivio = istanza.POSIZIONEARCHIVIO,
                Uuid = istanza.UUID
            };

            if (istanza.Allegati.Any())
            {
                visura.IstanzeAllegati = new System.Collections.Generic.List<IstanzeAllegati>();

                foreach (var item in istanza.Allegati)
                {
                    var istanzeAllegati = new IstanzeAllegati()
                    {
                        Codice = item.Id,
                        AllegatoExtra = item.ALLEGATOEXTRA,
                        CodiceOggetto = item.CODICEOGGETTO,
                        ControlloOk = item.CONTROLLOOK
                    };

                    if (item.Oggetto != null)
                    {
                        istanzeAllegati.Oggetto = new IstanzeAllegatiOggetto()
                        {
                            Codice = item.Oggetto.CODICEOGGETTO,
                            IdComune = item.Oggetto.IDCOMUNE,
                            NomeFile = item.Oggetto.NOMEFILE
                        };

                        if (item.Oggetto.Metadati.Any())
                        {
                            istanzeAllegati.Oggetto.Metadati = new System.Collections.Generic.List<OggettiMetadati>();

                            foreach (var metadato in item.Oggetto.Metadati)
                            {
                                istanzeAllegati.Oggetto.Metadati.Add(new OggettiMetadati()
                                {
                                    Codiceoggetto = metadato.Codiceoggetto,
                                    Idcomune = metadato.Idcomune,
                                    Chiave = metadato.Chiave,
                                    Valore = metadato.Valore
                                });
                            }
                        }
                    }

                    visura.IstanzeAllegati.Add(istanzeAllegati);
                }
            }

            if (istanza.Autorizzazioni.Any())
            {
                visura.Autorizzazioni = new System.Collections.Generic.List<Autorizzazioni>();

                foreach (var item in istanza.Autorizzazioni)
                {
                    var autorizzazione = new Autorizzazioni()
                    {
                        Codice = item.ID,
                        Data = item.AUTORIZDATA,
                        Numero = item.AUTORIZNUMERO,
                        Responsabile = item.AUTORIZRESPONSABILE
                    };

                    if (item.Registro != null)
                    {
                        autorizzazione.Registro = new TipologiaRegistri()
                        {
                            Codice = item.Registro.TR_ID,
                            Descrizione = item.Registro.TR_DESCRIZIONE
                        };
                    }

                    visura.Autorizzazioni.Add(autorizzazione);
                }
            }

            if (istanza.Oneri.Any())
            {
                visura.IstanzeOneri = new System.Collections.Generic.List<IstanzeOneri>();

                foreach (var item in istanza.Oneri)
                {
                    var onere = new IstanzeOneri()
                    {
                        Codice = item.ID,
                        DataPagamento = item.DATAPAGAMENTO,
                        DataScadenza = item.DATASCADENZA,
                        ImportoPagato = item.ImportoPagato
                    };

                    if (item.CausaleOnere != null)
                    {
                        onere.CausaleOnere = new TipiCausaliOneri()
                        {
                            Codice = item.CausaleOnere.CoId.HasValue ? item.CausaleOnere.CoId.ToString() : string.Empty,
                            Descrizione = item.CausaleOnere.CoDescrizione
                        };
                    }

                    visura.IstanzeOneri.Add(onere);
                }
            }

            if (istanza.EndoProcedimenti.Any())
            {
                visura.IstanzeEndoProcedimenti = new System.Collections.Generic.List<IstanzeProcedimenti>();

                foreach (var item in istanza.EndoProcedimenti)
                {
                    var endoProcedimento = new IstanzeProcedimenti()
                    {
                        CodiceIstanza = item.CODICEISTANZA,
                        CodiceInventario = item.CODICEINVENTARIO,
                        IdComune = item.IDCOMUNE
                    };

                    if (item.Endoprocedimento != null)
                    {
                        endoProcedimento.Endoprocedimento = new InventarioProcedimenti()
                        {
                            CodiceInventario = item.Endoprocedimento.Codiceinventario.HasValue ? item.Endoprocedimento.Codiceinventario.ToString() : string.Empty,
                            Procedimento = item.Endoprocedimento.Procedimento
                        };

                        if (item.Endoprocedimento.Allegati.Any())
                        {
                            endoProcedimento.Endoprocedimento.Allegati = new System.Collections.Generic.List<Allegati>();

                            foreach (var allegato in item.Endoprocedimento.Allegati)
                            {
                                endoProcedimento.Endoprocedimento.Allegati.Add(new Allegati()
                                {
                                    Codice = allegato.Id.HasValue ? allegato.Id.ToString() : string.Empty,
                                    Allegato = allegato.Allegato,
                                    Codiceoggetto = allegato.Codiceoggetto
                                });
                            }
                        }
                    }

                    if (item.IstanzeAllegati.Any())
                    {
                        endoProcedimento.IstanzeAllegati = new System.Collections.Generic.List<IstanzeAllegati>();

                        foreach (var itemAllegato in item.IstanzeAllegati)
                        {
                            var allegato = new IstanzeAllegati()
                            {
                                Codice = itemAllegato.Id,
                                AllegatoExtra = itemAllegato.ALLEGATOEXTRA,
                                CodiceOggetto = itemAllegato.CODICEOGGETTO,
                                ControlloOk = itemAllegato.CONTROLLOOK
                            };

                            if (itemAllegato.Oggetto != null)
                            {
                                allegato.Oggetto = new IstanzeAllegatiOggetto()
                                {
                                    Codice = itemAllegato.Oggetto.CODICEOGGETTO,
                                    IdComune = itemAllegato.Oggetto.IDCOMUNE,
                                    NomeFile = itemAllegato.Oggetto.NOMEFILE
                                };

                                if (itemAllegato.Oggetto.Metadati.Any())
                                {
                                    allegato.Oggetto.Metadati = new System.Collections.Generic.List<OggettiMetadati>();

                                    foreach (var meta in itemAllegato.Oggetto.Metadati)
                                    {
                                        allegato.Oggetto.Metadati.Add(new OggettiMetadati()
                                        {
                                            Chiave = meta.Chiave,
                                            Codiceoggetto = meta.Codiceoggetto,
                                            Idcomune = meta.Idcomune,
                                            Valore = meta.Valore
                                        });
                                    }
                                }
                            }

                            endoProcedimento.IstanzeAllegati.Add(allegato);
                        }
                    }

                    visura.IstanzeEndoProcedimenti.Add(endoProcedimento);
                }
            }

            if (istanza.DocumentiIstanza.Any())
            {
                visura.DocumentiIstanza = new System.Collections.Generic.List<DocumentiIstanza>();

                foreach (var item in istanza.DocumentiIstanza)
                {
                    var documentoIstanza = new DocumentiIstanza()
                    {
                        Codice = item.Id.HasValue ? item.Id.Value.ToString() : string.Empty,
                        CodiceOggetto = item.CODICEOGGETTO,
                        ControlloOk = item.ControlloOk,
                        Data = item.DATA,
                        Documento = item.DOCUMENTO,
                        IdComune = item.IDCOMUNE
                    };

                    if (item.Oggetto != null)
                    {
                        documentoIstanza.Oggetto = new DocumentiIstanzaOggetti()
                        {
                            Codice = item.Oggetto.CODICEOGGETTO,
                            NomeFile = item.Oggetto.NOMEFILE
                        };

                        if (item.Oggetto.Metadati.Any())
                        {
                            documentoIstanza.Oggetto.Metadati = new System.Collections.Generic.List<OggettiMetadati>();

                            foreach (var meta in item.Oggetto.Metadati)
                            {
                                documentoIstanza.Oggetto.Metadati.Add(new OggettiMetadati()
                                {
                                    Chiave = meta.Chiave,
                                    Codiceoggetto = meta.Codiceoggetto,
                                    Idcomune = meta.Idcomune,
                                    Valore = meta.Valore
                                });
                            }
                        }
                    }

                    visura.DocumentiIstanza.Add(documentoIstanza);
                }
            }

            if (istanza.Mappali.Any())
            {
                visura.IstanzeMappali = new System.Collections.Generic.List<IstanzeMappali>();

                foreach (var item in istanza.Mappali)
                {
                    var mappale = new IstanzeMappali()
                    {
                        Codice = item.Idmappale.HasValue ? item.Idmappale.Value.ToString() : string.Empty,
                        Foglio = item.Foglio,
                        Particella = item.Particella,
                        Sub = item.Sub
                    };

                    if (item.Catasto != null)
                    {
                        mappale.Catasto = new Catasto()
                        {
                            Codice = item.Catasto.CODICE,
                            Descrizione = item.Catasto.DESCRIZIONE
                        };
                    }

                    visura.IstanzeMappali.Add(mappale);
                }
            }

            if (istanza.Stradario.Any())
            {
                visura.IstanzeStradario = new System.Collections.Generic.List<IstanzeStradario>();

                foreach (var item in istanza.Stradario)
                {
                    var stradario = new IstanzeStradario()
                    {
                        Codice = item.ID,
                        Civico = item.CIVICO,
                        Colore = item.COLORE,
                        Esponente = item.ESPONENTE,
                        EsponenteInterno = item.ESPONENTEINTERNO,
                        Fabbricato = item.FABBRICATO,
                        Interno = item.INTERNO,
                        Km = item.Km,
                        Piano = item.Piano,
                        Scala = item.SCALA
                    };

                    if (item.Stradario != null)
                    {
                        stradario.Stradario = new Stradario()
                        {
                            Codice = item.Stradario.CODICESTRADARIO,
                            Descrizione = item.Stradario.DESCRIZIONE,
                            LocalitaFrazione = item.Stradario.LOCFRAZ,
                            Prefisso = item.Stradario.PREFISSO
                        };
                    }

                    visura.IstanzeStradario.Add(stradario);
                }
            }

            if (istanza.Richiedenti.Any())
            {
                visura.IstanzeRichiedenti = new System.Collections.Generic.List<IstanzeRichiedenti>();

                foreach (var item in istanza.Richiedenti)
                {
                    var richiedente = new IstanzeRichiedenti()
                    {
                        Codice = item.CODICEINVITATO,
                        AnagrafeCollegata = null,
                        Procuratore = null,
                        Richiedente = null,
                        TipoSoggetto = null
                    };

                    if (item.AnagrafeCollegata != null)
                    {
                        richiedente.AnagrafeCollegata = new Anagrafe()
                        {
                            Codice = item.AnagrafeCollegata.CODICEANAGRAFE,
                            CodiceFiscale = item.AnagrafeCollegata.CODICEFISCALE,
                            Nome = item.AnagrafeCollegata.NOME,
                            Nominativo = item.AnagrafeCollegata.NOMINATIVO,
                            PartitaIva = item.AnagrafeCollegata.PARTITAIVA
                        };
                    }

                    if (item.Procuratore != null)
                    {
                        richiedente.Procuratore = new Anagrafe()
                        {
                            Codice = item.Procuratore.CODICEANAGRAFE,
                            CodiceFiscale = item.Procuratore.CODICEFISCALE,
                            Nome = item.Procuratore.NOME,
                            Nominativo = item.Procuratore.NOMINATIVO,
                            PartitaIva = item.Procuratore.PARTITAIVA
                        };
                    }

                    if (item.Richiedente != null)
                    {
                        richiedente.Richiedente = new Anagrafe()
                        {
                            Codice = item.Richiedente.CODICEANAGRAFE,
                            CodiceFiscale = item.Richiedente.CODICEFISCALE,
                            Nome = item.Richiedente.NOME,
                            Nominativo = item.Richiedente.NOMINATIVO,
                            PartitaIva = item.Richiedente.PARTITAIVA
                        };
                    }

                    if (item.TipoSoggetto != null)
                    {
                        richiedente.TipoSoggetto = new TipiSoggetto()
                        {
                            Codice = item.TipoSoggetto.CODICETIPOSOGGETTO,
                            FlagLivelliVisuraPratica = item.TipoSoggetto.FlagLivelliVisuraPratica,
                            Descrizione = item.TipoSoggetto.TIPOSOGGETTO
                        };
                    }

                    visura.IstanzeRichiedenti.Add(richiedente);
                }
            }

            if (istanza.Movimenti.Any())
            {
                visura.Movimenti = new System.Collections.Generic.List<Movimenti>();

                foreach (var item in istanza.Movimenti)
                {
                    var movimento = new Movimenti()
                    {
                        Codice = item.CODICEMOVIMENTO,
                        Data = item.DATA,
                        DataProtocollo = item.DATAPROTOCOLLO,
                        Movimento = item.MOVIMENTO,
                        Pubblicare = !string.IsNullOrEmpty(item.PUBBLICA) && item.PUBBLICA != "0",
                        PubblicareParere = !string.IsNullOrEmpty(item.PUBBLICAPARERE) && item.PUBBLICAPARERE != "0",
                        Parere = item.PARERE,
                        NumeroProtocollo = item.NUMEROPROTOCOLLO,
                        UuidPraticaCollegata = item.UuidPraticaCollegata
                    };

                    if (item.MovimentiAllegati.Any())
                    {
                        movimento.MovimentiAllegati = new System.Collections.Generic.List<MovimentiAllegati>();

                        foreach (var itemMovAllegato in item.MovimentiAllegati)
                        {
                            var movAllegato = new MovimentiAllegati()
                            {
                                Codice = itemMovAllegato.Id,
                                CodiceOggetto = itemMovAllegato.CODICEOGGETTO,
                                ControlloOK = itemMovAllegato.ControlloOK,
                                Descrizione = itemMovAllegato.DESCRIZIONE,
                                Pubblicare = itemMovAllegato.FlagPubblica.HasValue && itemMovAllegato.FlagPubblica.Value != 0
                            };

                            if (itemMovAllegato.Oggetto != null)
                            {
                                movAllegato.Oggetto = new MovimentiAllegatiOggetti()
                                {
                                    Codice = itemMovAllegato.Oggetto.CODICEOGGETTO,
                                    IdComune = itemMovAllegato.Oggetto.IDCOMUNE,
                                    NomeFile = itemMovAllegato.Oggetto.NOMEFILE
                                };

                                if (itemMovAllegato.Oggetto.Metadati.Any())
                                {
                                    movAllegato.Oggetto.Metadati = new System.Collections.Generic.List<OggettiMetadati>();

                                    foreach (var meta in movAllegato.Oggetto.Metadati)
                                    {
                                        movAllegato.Oggetto.Metadati.Add(new OggettiMetadati()
                                        {
                                            Chiave = meta.Chiave,
                                            Codiceoggetto = meta.Codiceoggetto,
                                            Idcomune = meta.Idcomune,
                                            Valore = meta.Valore
                                        });
                                    }
                                }
                            }

                            movimento.MovimentiAllegati.Add(movAllegato);
                        }
                    }

                    visura.Movimenti.Add(movimento);
                }
            }

            if (istanza.Professionista != null)
            {
                var professionista = new Anagrafe()
                {
                    Codice = istanza.Professionista.CODICEANAGRAFE,
                    CodiceFiscale = istanza.Professionista.CODICEFISCALE,
                    Nome = istanza.Professionista.NOME,
                    Nominativo = istanza.Professionista.NOMINATIVO,
                    PartitaIva = istanza.Professionista.PARTITAIVA
                };

                visura.Professionista = professionista;
            }

            if (istanza.AziendaRichiedente != null)
            {
                var aziendaRichiedente = new Anagrafe()
                {
                    Codice = istanza.AziendaRichiedente.CODICEANAGRAFE,
                    CodiceFiscale = istanza.AziendaRichiedente.CODICEFISCALE,
                    Nome = istanza.AziendaRichiedente.NOME,
                    Nominativo = istanza.AziendaRichiedente.NOMINATIVO,
                    PartitaIva = istanza.AziendaRichiedente.PARTITAIVA
                };

                visura.AziendaRichiedente = aziendaRichiedente;
            }

            if (istanza.Richiedente != null)
            {
                var richiedente = new Anagrafe()
                {
                    Codice = istanza.Richiedente.CODICEANAGRAFE,
                    CodiceFiscale = istanza.Richiedente.CODICEFISCALE,
                    Nome = istanza.Richiedente.NOME,
                    Nominativo = istanza.Richiedente.NOMINATIVO,
                    PartitaIva = istanza.Richiedente.PARTITAIVA
                };

                visura.Richiedente = richiedente;
            }

            if (istanza.TipoSoggetto != null)
            {
                var tipo = new TipiSoggetto()
                {
                    Codice = istanza.TipoSoggetto.CODICETIPOSOGGETTO,
                    FlagLivelliVisuraPratica = istanza.TipoSoggetto.FlagLivelliVisuraPratica,
                    Descrizione = istanza.TipoSoggetto.TIPOSOGGETTO
                };

                visura.TipoSoggetto = tipo;
            }

            if (istanza.Stato != null)
            {
                var stato = new StatiIstanza()
                {
                    Codice = istanza.Stato.Codicestato,
                    Stato = istanza.Stato.Stato
                };

                visura.Stato = stato;
            }

            if (istanza.ResponsabileProc != null)
            {
                var responsabileProc = new Responsabili()
                {
                    Codice = istanza.ResponsabileProc.CODICERESPONSABILE,
                    Nome = istanza.ResponsabileProc.RESPONSABILE
                };

                visura.ResponsabileProc = responsabileProc;
            }

            if (istanza.Operatore != null)
            {
                var operatore = new Responsabili()
                {
                    Codice = istanza.Operatore.CODICERESPONSABILE,
                    Nome = istanza.Operatore.RESPONSABILE
                };

                visura.Operatore = operatore;
            }

            if (istanza.Istruttore != null)
            {
                var istruttore = new Responsabili()
                {
                    Nome = istanza.Istruttore.RESPONSABILE
                };

                visura.Istruttore = istruttore;
            }

            if (istanza.Intervento != null)
            {
                var intervento = new AlberoProcedimenti()
                {
                    Codice = istanza.Intervento.Sc_id.HasValue ? istanza.Intervento.Sc_id.ToString() : string.Empty,
                    DescrizioneCompleta = istanza.Intervento.DescrizioneCompleta,
                    Descrizione = istanza.Intervento.SC_DESCRIZIONE
                };

                visura.Intervento = intervento;
            }

            if (istanza.ComuneIstanza != null)
            {
                var comuneIstanza = new Comuni()
                {
                    Codice = istanza.ComuneIstanza.CODICECOMUNE,
                    Nome = istanza.ComuneIstanza.COMUNE
                };

                visura.ComuneIstanza = comuneIstanza;
            }

            return visura;
        }



    }
}
