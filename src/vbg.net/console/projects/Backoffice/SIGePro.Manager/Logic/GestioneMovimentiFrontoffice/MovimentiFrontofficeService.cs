using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice
{
    public class MovimentiFrontofficeService
    {
        AuthenticationInfo _authInfo;

        public MovimentiFrontofficeService(AuthenticationInfo authInfo)
        {
            this._authInfo = authInfo;
        }

        public DatiMovimentoDaEffettuareDto GetById(int idMovimento)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var idComune = this._authInfo.IdComune;
                var codiceMovimento = idMovimento;

                var movimentoSigepro = new MovimentiMgr(db).GetById(this._authInfo.IdComune, codiceMovimento);

                if (movimentoSigepro == null)
                    return null;

                var tipiMovimentoMgr = new TipiMovimentoMgr(db);
                var tipoMovimento = tipiMovimentoMgr.GetById(movimentoSigepro.TIPOMOVIMENTO, idComune);

                var istanza = new IstanzeMgr(db).GetById(idComune, Convert.ToInt32(movimentoSigepro.CODICEISTANZA));

                var movimentiDyn2Mgr = new MovimentiDyn2ModelliTMgr(db);
                var istanzeDyn2DatiMgr = new IstanzeDyn2DatiMgr(db);

                var schedeDinamicheSource = new SchedeDinamicheMovimentoSource(movimentiDyn2Mgr, tipiMovimentoMgr, idComune);
                var logicaRisoluzioneSchedeDinamiche = new RisolviSchedeDinamicheMovimento(schedeDinamicheSource, tipoMovimento);

                var codiceIstanza = Convert.ToInt32(istanza.CODICEISTANZA);

                DatiMovimentoDaEffettuareDto rVal = new DatiMovimentoDaEffettuareDto
                {
                    IdComune = movimentoSigepro.IDCOMUNE,
                    Software = istanza.SOFTWARE,
                    CodiceIstanza = codiceIstanza,
                    NumeroIstanza = istanza.NUMEROISTANZA,
                    NumeroProtocolloIstanza = istanza.NUMEROPROTOCOLLO,
                    DataProtocolloIstanza = istanza.DATAPROTOCOLLO,
                    CodiceMovimento = codiceMovimento,
                    NumeroProtocollo = movimentoSigepro.NUMEROPROTOCOLLO,
                    DataProtocollo = movimentoSigepro.DATAPROTOCOLLO,
                    DataIstanza = istanza.DATA.Value,
                    Amministrazione = !string.IsNullOrEmpty(movimentoSigepro.CODICEAMMINISTRAZIONE) ? new AmministrazioniMgr(db).GetById(idComune, Convert.ToInt32(movimentoSigepro.CODICEAMMINISTRAZIONE)).AMMINISTRAZIONE : String.Empty,
                    CodiceInventario = movimentoSigepro.CODICEINVENTARIO,
                    DescInventario = !string.IsNullOrEmpty(movimentoSigepro.CODICEINVENTARIO) ? new InventarioProcedimentiMgr(db).GetById(idComune, Convert.ToInt32(movimentoSigepro.CODICEINVENTARIO)).Procedimento : String.Empty,
                    Esito = movimentoSigepro.ESITO != "0" ? "Positivo" : "Negativo",
                    Note = movimentoSigepro.NOTE,
                    Parere = movimentoSigepro.PARERE,
                    Descrizione = movimentoSigepro.MOVIMENTO,
                    Pubblica = movimentoSigepro.PUBBLICA != "0",
                    DataMovimento = movimentoSigepro.DATA,
                    VisualizzaParere = movimentoSigepro.PUBBLICAPARERE != "0",
                    VisualizzaEsito = tipoMovimento.Tipologiaesito.GetValueOrDefault(0) != 0,
                    PubblicaSchede = tipoMovimento.FlagPubblicaSchede.GetValueOrDefault(0) == 1,
                    Allegati = new MovimentiAllegatiMgr(db).GetList(new MovimentiAllegati
                    {
                        IDCOMUNE = movimentoSigepro.IDCOMUNE,
                        CODICEMOVIMENTO = movimentoSigepro.CODICEMOVIMENTO,
                        FlagPubblica = 1
                    }),
                    SchedeDinamiche = logicaRisoluzioneSchedeDinamiche.GetSchedeDelMovimento(codiceMovimento)
                                                      .Select(x => new SchedaDinamicaMovimentoDto
                                                      {
                                                          Id = x.Id.Value,
                                                          Titolo = x.Descrizione,
                                                          Valori = istanzeDyn2DatiMgr.GetListByCodiceIstanzaIdModello(idComune, codiceIstanza, x.Id.Value)
                                                                                      .Select(y => new ValoreDatoDinamicoMovimentoDto
                                                                                      {
                                                                                          Id = y.FkD2cId.Value,
                                                                                          Indice = y.IndiceMolteplicita.Value,
                                                                                          Valore = y.Valore,
                                                                                          ValoreDecodificato = y.Valoredecodificato
                                                                                      }).ToList(),
                                                          IdCampiContenuti = new Dyn2ModelliDMgr(db).GetCampiDinamiciModello(idComune, x.Id.Value)
                                                                                                            .Select(cd => cd.FkD2cId.Value).ToList()

                                                      }).ToList()
                };
                                
                return rVal;
            }
        }

        public DocumentiIstanzaSostituibili GetDocumentiSostituibili(int idMovimento)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var idComune = this._authInfo.IdComune;
                var codiceMovimento = idMovimento;

                var movimentoSigepro = new MovimentiMgr(db).GetById(this._authInfo.IdComune, codiceMovimento);
                var codiceIstanza = Convert.ToInt32(movimentoSigepro.CODICEISTANZA);
                var configurazioneMovimento = GetFlagsConfigurazioneDaidMovimento(idMovimento);
                

                var rVal = new DocumentiIstanzaSostituibili();
                
                rVal.DocumentiIntervento = new ListaDocumentiSostituibili
                {
                    Descrizione = "Allegati dell'intervento",
                    Documenti = GetAllegatiIntervento(db, codiceIstanza, configurazioneMovimento.TipoSostituzioneDocumentale).ToList()
                };

                rVal.DocumentiEndo = GetAllegatiEndo(db, codiceIstanza, configurazioneMovimento.TipoSostituzioneDocumentale).ToList();

                return rVal;
            }
        }

        private IEnumerable<ListaDocumentiSostituibili> GetAllegatiEndo(DataBase db, int codiceIstanza, TipoSostituzioneDocumentaleEnum tipoSostituzione)
        {
            if (tipoSostituzione == TipoSostituzioneDocumentaleEnum.NessunaSostituzione)
            {
                return Enumerable.Empty<ListaDocumentiSostituibili>();
            }

            var sostituisciNonValidi = tipoSostituzione == TipoSostituzioneDocumentaleEnum.DocumentiNonValidi ||
                                        tipoSostituzione == TipoSostituzioneDocumentaleEnum.DocumentiNonValidiENonVerificati;
            var sostituisciNonVerificati = tipoSostituzione == TipoSostituzioneDocumentaleEnum.DocumentiNonValidiENonVerificati;

            var idComune = this._authInfo.IdComune;
            var documenti = new IstanzeAllegatiMgr(db).GetListDocumentiSostituibili(idComune, codiceIstanza, sostituisciNonValidi, sostituisciNonVerificati);
            var oggettiMgr = new OggettiMgr(db);
            var rVal = new List<ListaDocumentiSostituibili>();

            foreach (var key in documenti.Keys)
            {
                var allegati = documenti[key];
                rVal.Add(new ListaDocumentiSostituibili
                {
                    Descrizione = key,
                    Documenti = allegati.Select(doc =>
                    {
                        var codiceOggetto = String.IsNullOrEmpty(doc.CODICEOGGETTO) ? (int?)null : Convert.ToInt32(doc.CODICEOGGETTO);
                        var descrizione = doc.ALLEGATOEXTRA;
                        var idDocumento = Convert.ToInt32(doc.Id);
                        var nomeFile = codiceOggetto.HasValue ? oggettiMgr.GetNomeFile(idComune, codiceOggetto.Value) : String.Empty;
                        var origine = DocumentoSostituibileMovimentoDto.OrigineDocumentoEnum.Endoprocedimento;

                        return new DocumentoSostituibileMovimentoDto
                        {
                            CodiceOggetto = codiceOggetto,
                            Descrizione = descrizione,
                            IdDocumento = idDocumento,
                            NomeFile = nomeFile,
                            Origine = origine
                        };
                    }).ToList()
                });
            }

            return rVal;
        }

        private IEnumerable<DocumentoSostituibileMovimentoDto> GetAllegatiIntervento(DataBase db, int codiceIstanza, TipoSostituzioneDocumentaleEnum tipoSostituzione)
        {
            if (tipoSostituzione == TipoSostituzioneDocumentaleEnum.NessunaSostituzione)
            {
                return Enumerable.Empty<DocumentoSostituibileMovimentoDto>();
            }

            var sostituisciNonValidi = tipoSostituzione == TipoSostituzioneDocumentaleEnum.DocumentiNonValidi ||
                            tipoSostituzione == TipoSostituzioneDocumentaleEnum.DocumentiNonValidiENonVerificati;
            var sostituisciNonVerificati = tipoSostituzione == TipoSostituzioneDocumentaleEnum.DocumentiNonValidiENonVerificati;

            var idComune = this._authInfo.IdComune;
            var documenti = new DocumentiIstanzaMgr(db).GetListDocumentiSostituibili(idComune, codiceIstanza, sostituisciNonValidi, sostituisciNonVerificati);
            var oggettiMgr = new OggettiMgr(db);
            
            return documenti.Select(doc =>
            {
                var codiceOggetto = String.IsNullOrEmpty(doc.CODICEOGGETTO) ? (int?)null : Convert.ToInt32(doc.CODICEOGGETTO);
                var descrizione = doc.DOCUMENTO;
                var idDocumento = doc.Id.Value;
                var nomeFile = codiceOggetto.HasValue ? oggettiMgr.GetNomeFile(idComune, codiceOggetto.Value) : String.Empty;
                var origine = DocumentoSostituibileMovimentoDto.OrigineDocumentoEnum.Intervento;

                return new DocumentoSostituibileMovimentoDto
                {
                    CodiceOggetto = codiceOggetto,
                    Descrizione = descrizione,
                    IdDocumento = idDocumento,
                    NomeFile = nomeFile,
                    Origine = origine
                };
            });

        }

        public FlagsMovimento GetFlagsConfigurazioneDaidMovimento(int idMovimento)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var movimento = new MovimentiMgr(db).GetById(this._authInfo.IdComune, idMovimento);
                var tipoMovimento = new TipiMovimentoMgr(db).GetById(movimento.TIPOMOVIMENTO, movimento.IDCOMUNE);
                var flgSostituzione = (TipoSostituzioneDocumentaleEnum)tipoMovimento.FlagSostDocumentale.GetValueOrDefault(0);
                var flgFirmaDocumenti = tipoMovimento.FlagVerificaFirmaNelleIntegrazioni.GetValueOrDefault(0) == 1;

                return new FlagsMovimento
                {
                    RichiedeFirmaDigitale = flgFirmaDocumenti,
                    TipoSostituzioneDocumentale = flgSostituzione
                };
            }
        }
    }
}
