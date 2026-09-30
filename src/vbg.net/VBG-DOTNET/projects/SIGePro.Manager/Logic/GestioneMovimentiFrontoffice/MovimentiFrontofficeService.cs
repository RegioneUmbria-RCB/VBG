using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.Scadenzario;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice
{
    public static class MovimentiAllegatiExtensions
    {
        public static MovimentiAllegatiDto ToMovimentiAllegatiDto(this MovimentiAllegati mov)
        {
            return new MovimentiAllegatiDto
            {
                CodiceOggetto = String.IsNullOrEmpty(mov.CODICEOGGETTO) ? (int?)null : Convert.ToInt32(mov.CODICEOGGETTO),
                Descrizione = mov.DESCRIZIONE,
                Note = mov.NOTE
            };
        }
    }

    public class MovimentiFrontofficeService
    {
        private readonly DataBase _db;
        private readonly string _idComune;

        public MovimentiFrontofficeService(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public DatiMovimentoDaEffettuareDto GetById(int idMovimento)
        {

            var codiceMovimento = idMovimento;

            var movimentoSigepro = new MovimentiMgr(this._db).GetById(this._idComune, codiceMovimento);

            if (movimentoSigepro == null)
                return null;

            var tipiMovimentoMgr = new TipiMovimentoMgr(this._db);
            var tipoMovimento = tipiMovimentoMgr.GetById(movimentoSigepro.TIPOMOVIMENTO, this._idComune);

            var istanza = new IstanzeMgr(this._db).GetById(this._idComune, Convert.ToInt32(movimentoSigepro.CODICEISTANZA));

            var movimentiDyn2Mgr = new MovimentiDyn2ModelliTMgr(this._db);
            var istanzeDyn2DatiMgr = new IstanzeDyn2DatiMgr(this._db);

            var schedeDinamicheSource = new SchedeDinamicheMovimentoSource(movimentiDyn2Mgr, tipiMovimentoMgr, this._idComune);
            var logicaRisoluzioneSchedeDinamiche = new RisolviSchedeDinamicheMovimento(schedeDinamicheSource, tipoMovimento);

            var codiceIstanza = Convert.ToInt32(istanza.CODICEISTANZA);

            var allegati = new MovimentiAllegatiMgr(this._db).GetList(new MovimentiAllegati
            {
                IDCOMUNE = movimentoSigepro.IDCOMUNE,
                CODICEMOVIMENTO = movimentoSigepro.CODICEMOVIMENTO,
                FlagPubblica = 1
            })
                                                        .Select(x => x.ToMovimentiAllegatiDto())
                                                        .ToList();

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
                Amministrazione = !string.IsNullOrEmpty(movimentoSigepro.CODICEAMMINISTRAZIONE) ? new AmministrazioniMgr(this._db).GetById(this._idComune, Convert.ToInt32(movimentoSigepro.CODICEAMMINISTRAZIONE)).AMMINISTRAZIONE : String.Empty,
                CodiceInventario = movimentoSigepro.CODICEINVENTARIO,
                DescInventario = !string.IsNullOrEmpty(movimentoSigepro.CODICEINVENTARIO) ? new InventarioProcedimentiMgr(this._db).GetById(this._idComune, Convert.ToInt32(movimentoSigepro.CODICEINVENTARIO)).Procedimento : String.Empty,
                Esito = movimentoSigepro.ESITO != "0" ? "Positivo" : "Negativo",
                Note = movimentoSigepro.NOTE,
                Parere = movimentoSigepro.PARERE,
                Descrizione = movimentoSigepro.MOVIMENTO,
                Pubblica = movimentoSigepro.PUBBLICA != "0",
                DataMovimento = movimentoSigepro.DATA,
                VisualizzaParere = movimentoSigepro.PUBBLICAPARERE != "0",
                VisualizzaEsito = tipoMovimento.Tipologiaesito.GetValueOrDefault(0) != 0,
                PubblicaSchede = tipoMovimento.FlagPubblicaSchede.GetValueOrDefault(0) == 1,
                Allegati = allegati,
                SchedeDinamiche = logicaRisoluzioneSchedeDinamiche.GetSchedeDelMovimento(codiceMovimento)
                                                  .Select(x => new SchedaDinamicaMovimentoDto
                                                  {
                                                      Id = x.Id.Value,
                                                      Titolo = x.Descrizione,
                                                      Valori = istanzeDyn2DatiMgr.GetListByCodiceIstanzaIdModello(this._idComune, codiceIstanza, x.Id.Value)
                                                                                  .Select(y => new ValoreDatoDinamicoMovimentoDto
                                                                                  {
                                                                                      Id = y.FkD2cId.Value,
                                                                                      Indice = y.IndiceMolteplicita.Value,
                                                                                      Valore = y.Valore,
                                                                                      ValoreDecodificato = y.Valoredecodificato
                                                                                  }).ToList(),
                                                      IdCampiContenuti = new Dyn2ModelliDMgr(this._db).GetSoloCampiDinamiciModello(this._idComune, x.Id.Value)
                                                                                                        .Select(cd => cd.FkD2cId.Value).ToList()

                                                  }).ToList()
            };

            return rVal;
        }

        public DocumentiIstanzaSostituibiliDto GetDocumentiSostituibili(int idMovimento)
        {

            var codiceMovimento = idMovimento;

            var movimentoSigepro = new MovimentiMgr(this._db).GetById(this._idComune, codiceMovimento);
            var codiceIstanza = Convert.ToInt32(movimentoSigepro.CODICEISTANZA);
            var configurazioneMovimento = this.GetFlagsConfigurazioneDaidMovimento(idMovimento);


            var rVal = new DocumentiIstanzaSostituibiliDto();

            rVal.DocumentiIntervento = new ListaDocumentiSostituibiliDto
            {
                Descrizione = "Allegati dell'intervento",
                Documenti = this.GetAllegatiIntervento(this._db, codiceIstanza, configurazioneMovimento.TipoSostituzioneDocumentale).ToList()
            };

            rVal.DocumentiEndo = this.GetAllegatiEndo(this._db, codiceIstanza, configurazioneMovimento.TipoSostituzioneDocumentale).ToList();

            return rVal;

        }

        private IEnumerable<ListaDocumentiSostituibiliDto> GetAllegatiEndo(DataBase db, int codiceIstanza, TipoSostituzioneDocumentaleEnum tipoSostituzione)
        {
            if (tipoSostituzione == TipoSostituzioneDocumentaleEnum.NessunaSostituzione)
            {
                return Enumerable.Empty<ListaDocumentiSostituibiliDto>();
            }

            var sostituisciNonValidi = tipoSostituzione == TipoSostituzioneDocumentaleEnum.DocumentiNonValidi ||
                                        tipoSostituzione == TipoSostituzioneDocumentaleEnum.DocumentiNonValidiENonVerificati;
            var sostituisciNonVerificati = tipoSostituzione == TipoSostituzioneDocumentaleEnum.DocumentiNonValidiENonVerificati;

            var documenti = new IstanzeAllegatiMgr(db).GetListDocumentiSostituibili(this._idComune, codiceIstanza, sostituisciNonValidi, sostituisciNonVerificati);
            var oggettiMgr = new OggettiMgr(db);
            var rVal = new List<ListaDocumentiSostituibiliDto>();

            foreach (var key in documenti.Keys)
            {
                var allegati = documenti[key];
                rVal.Add(new ListaDocumentiSostituibiliDto
                {
                    Descrizione = key,
                    Documenti = allegati.Select(doc =>
                    {
                        var codiceOggetto = String.IsNullOrEmpty(doc.CODICEOGGETTO) ? (int?)null : Convert.ToInt32(doc.CODICEOGGETTO);
                        var descrizione = doc.ALLEGATOEXTRA;
                        var idDocumento = Convert.ToInt32(doc.Id);
                        var nomeFile = codiceOggetto.HasValue ? oggettiMgr.GetNomeFile(this._idComune, codiceOggetto.Value) : String.Empty;
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

            var documenti = new DocumentiIstanzaMgr(db).GetListDocumentiSostituibili(this._idComune, codiceIstanza, sostituisciNonValidi, sostituisciNonVerificati);
            var oggettiMgr = new OggettiMgr(db);

            return documenti.Select(doc =>
            {
                var codiceOggetto = String.IsNullOrEmpty(doc.CODICEOGGETTO) ? (int?)null : Convert.ToInt32(doc.CODICEOGGETTO);
                var descrizione = doc.DOCUMENTO;
                var idDocumento = doc.Id.Value;
                var nomeFile = codiceOggetto.HasValue ? oggettiMgr.GetNomeFile(this._idComune, codiceOggetto.Value) : String.Empty;
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
            var movimento = new MovimentiMgr(this._db).GetById(this._idComune, idMovimento);
            var tipoMovimento = new TipiMovimentoMgr(this._db).GetById(movimento.TIPOMOVIMENTO, movimento.IDCOMUNE);
            var flgSostituzione = (TipoSostituzioneDocumentaleEnum)tipoMovimento.FlagSostDocumentale.GetValueOrDefault(0);
            var flgFirmaDocumenti = tipoMovimento.FlagVerificaFirmaNelleIntegrazioni.GetValueOrDefault(0) == 1;
            var flgInterazioneConSIT = tipoMovimento.FlagFoRichiamaSIT.GetValueOrDefault(0) == 1;

            return new FlagsMovimento
            {
                RichiedeFirmaDigitale = flgFirmaDocumenti,
                TipoSostituzioneDocumentale = flgSostituzione,
                RichiedeInterazioneConSIT = flgInterazioneConSIT
            };
        }

        public DatiMovimentoDaEffettuareDto GetByUUID(string uuidMovimento)
        {
            var id = this.GetIdMovimentoByUUID(uuidMovimento);

            if (!id.HasValue)
            {
                return null;
            }

            return this.GetById(id.Value);
        }

        public int? GetIdMovimentoByUUID(string uuidMovimento)
        {
            return new MovimentiMgr(this._db).GetIdMovimentoByUUID(this._idComune, uuidMovimento);
        }
    }
}
