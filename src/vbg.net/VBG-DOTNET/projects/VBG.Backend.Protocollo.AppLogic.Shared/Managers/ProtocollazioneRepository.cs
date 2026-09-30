using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Manager;
using log4net;
using PersonalLib2.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Metadati;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{
    public class ProtocollazioneRepository : IComuniRepository
    {
        private readonly DataBase _dataBase;
        private readonly string _idComune;
        private readonly string _software;
        private readonly string _codiceComune;
        private readonly ILog _log;

        public ProtocollazioneRepository(DataBase dataBase, string idComune, string software, string codiceComune, ILog log)
        {
            this._dataBase = dataBase;
            this._idComune = idComune;
            this._software = software;
            this._codiceComune = codiceComune;
            this._log = log;
        }

        public void RepositoryAggiornaRiferimentoProtocolloMovimentoAvvio(int codiceIstanza, string tipoMovimento, DatiProtocolloResponseType datiProtocollo, DateTime dataProtocollo)
        {
            var mov = this.RepositoryGetMovimentoByTipoMovimento(codiceIstanza, tipoMovimento);

            if (mov != null)
            {
                //Prima con il protocollo Sigepro non veniva aggiornato il campo FKIDPROTOCOLLO per il movimento di avvio
                mov.FKIDPROTOCOLLO = string.IsNullOrEmpty(datiProtocollo.IdProtocollo) ? null : datiProtocollo.IdProtocollo;

                if (!String.IsNullOrEmpty(datiProtocollo.NumeroProtocollo) && datiProtocollo.NumeroProtocollo != "0")
                {
                    mov.NUMEROPROTOCOLLO = datiProtocollo.NumeroProtocollo;
                    mov.DATAPROTOCOLLO = dataProtocollo;
                }

                this.RepositoryMovimentiUpdateDatiProtocollo(Convert.ToInt32(mov.CODICEMOVIMENTO), mov.FKIDPROTOCOLLO, mov.NUMEROPROTOCOLLO, mov.DATAPROTOCOLLO.Value);
            }
        }

        public void RepositoryAggiornaRiferimentoProtocolloIstanza(int codiceIstanza, string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo)
        {
            var istanzeMgr = new IstanzeMgr(this._dataBase);
            istanzeMgr.UpdateDatiProtocollo(idProtocollo, numeroProtocollo, dataProtocollo, this._idComune, codiceIstanza);
        }

        public void RepositoryProtocolloMetadatiInsert(string idProtocollo, List<ProtocolloMetadati> metadati)
        {
            if (metadati != null)
            {
                var mgr = new ProtocolloMetadatiMgr(this._dataBase);
                mgr.Insert(this._idComune, idProtocollo, metadati);
            }
        }


        #region Metodi da spostare su un repository
        public AllegatoType[] RepositoryGetAllegatiMovimento(string codiceMovimento)
        {
            //Protocollo un movimento
            var pMovAllMgr = new MovimentiAllegatiMgr(this._dataBase);
            var pMovAll = new MovimentiAllegati
            {
                CODICEMOVIMENTO = codiceMovimento,
                IDCOMUNE = this._idComune
            };
            pMovAll.OthersWhereClause.Add("MOVIMENTIALLEGATI.CODICEOGGETTO is not null");
            var pListMovAll = pMovAllMgr.GetList(pMovAll);

            if (pListMovAll.Count == 0)
            {
                return Array.Empty<AllegatoType>();
            }

            return pListMovAll.Select(obj => new AllegatoType
            {
                Cod = obj.CODICEOGGETTO,
                Descrizione = obj.DESCRIZIONE
            }).ToArray();
        }

        public void RepositoryUpdateMovimentoNonElaborare(Movimenti movimento)
        {
            var movimentiMgr = new MovimentiMgr(this._dataBase);
            movimentiMgr.Update(movimento, ComportamentoElaborazioneEnum.NonElaborare);
        }

        public bool RepositoryVerificaFascicolazioneAutomaticaDaCodiceIntervento(int codiceIntervento, int source)
        {
            var alberoMgr = new AlberoProcMgr(this._dataBase);
            return alberoMgr.IsFascicolazioneAutomatica(codiceIntervento, source, this._idComune, this._software, this._codiceComune);
        }

        public bool RepositoryVerificaProtocollazioneAutomaticaDaCodiceIntervento(int codiceIntervento, Source tipoInserimento)
        {
            var alberoMgr = new AlberoProcMgr(this._dataBase);
            return alberoMgr.IsProtocollazioneAutomatica(codiceIntervento, (int)tipoInserimento, this._idComune, this._software, this._codiceComune);
        }

        public int? RepositoryGetCodiceOggettoRiepilogoDaMetadati(int codiceIstanza, string metadatoRiepilogoDomanda)
        {
            var metadato = String.IsNullOrEmpty(metadatoRiepilogoDomanda) ? Constants.RIEPILOGO_DOMANDA : metadatoRiepilogoDomanda;

            return new OggettiMetadatiMgr(this._dataBase).TrovaRiepilogoDomanda(codiceIstanza, this._idComune, Constants.CHIAVE_TIPODOCUMENTO, metadato);
        }

        public IEnumerable<DomandeStc> RepositoryGetDomandeStcByCodiceIstanza(int codiceIstanza)
        {
            var mgrDomandeStc = new DomandeStcMgr(this._dataBase);
            //_protocolloLogs.Info($"RICERCA DELLA DOMANDA STC DELL'ISTANZA NUMERO: {this._datiProtocollazione.NumeroIstanza}, CODICE: {this._datiProtocollazione.CodiceIstanza}");
            return mgrDomandeStc.GetDomandeByIstanza(this._idComune, codiceIstanza);
        }

        public Oggetti RepositoryGetOggettoById(int codiceOggetto)
        {
            var protoAllegatiMgr = new ProtocolloAllegatiMgr(this._dataBase);
            return protoAllegatiMgr.GetById(this._idComune, codiceOggetto);
        }

        public string RepositoryGetPercorsoFile(int codiceOggetto)
        {
            var protoAllegatiMgr = new ProtocolloAllegatiMgr(this._dataBase);
            return protoAllegatiMgr.GetPercorsoOggetto(this._idComune, codiceOggetto);
        }

        public string RepositoryGetContentType(Oggetti oggetto)
        {
            var protoAllegatiMgr = new ProtocolloAllegatiMgr(this._dataBase);
            return protoAllegatiMgr.GetContentType(oggetto);
        }

        private string RepositoryGetNomeFile(int codiceOggetto)
        {
            var oggMgr = new OggettiMgr(this._dataBase);
            return oggMgr.GetNomeFile(this._idComune, codiceOggetto);
        }

        private List<IstanzeRichiedenti> RepositoryGetProcureSoggettiCollegati(int codiceIstanza)
        {
            return new IstanzeRichiedentiMgr(this._dataBase).GetList(new IstanzeRichiedenti
            {
                IDCOMUNE = this._idComune,
                CODICEISTANZA = codiceIstanza.ToString(),
                OthersWhereClause = new List<string> { "codiceoggetto_procura is not null" }
            }).ToList();
        }

        private List<IstanzeProcure> RepositoryGetProcureDaCodiceIstanza(int codiceIstanza)
        {
            var istanzeProcureMgr = new IstanzeProcureMgr(this._dataBase);
            var istanzeProcure = new IstanzeProcure
            {
                CodiceIstanza = codiceIstanza,
                IdComune = this._idComune
            };
            istanzeProcure.OthersWhereClause.Add("ISTANZEPROCURE.CODICEOGGETTOPROCURA is not null");

            return istanzeProcureMgr.GetList(istanzeProcure);
        }

        public Movimenti RepositoryGetMovimentoByTipoMovimento(int codiceIstanza, string tipoMovimento)
        {
            return new MovimentiMgr(this._dataBase).GetByClass(new Movimenti
            {
                IDCOMUNE = this._idComune,
                TIPOMOVIMENTO = tipoMovimento,
                CODICEISTANZA = codiceIstanza.ToString()
            });
        }

        public void RepositoryMovimentiUpdateDatiProtocollo(int idMovimento, string idProtocollo, string numeroProtocollo, DateTime? dataProtocollo)
        {
            var movMgr = new MovimentiMgr(this._dataBase);
            movMgr.UpdateDatiProtocollo(idProtocollo, numeroProtocollo, dataProtocollo, this._idComune, idMovimento);
        }

        public string RepositoryGetNumeroFascicoloFromAlberoProcProtocollo(int codiceIntervento)
        {
            var alberoMgr = new AlberoProcMgr(this._dataBase);
            return alberoMgr.GetNumeroFascicoloFromAlberoProcProtocollo(codiceIntervento, this._idComune, this._software, this._codiceComune);
        }

        public AllegatoType[] RisolviDocumentiIstanza(int codiceIstanza)
        {
            var pListDocIstanza = this.RepositoryGetDocumentiIstanzaConAllegato(codiceIstanza);
            var iCountDocIstanza = pListDocIstanza.Count;

            this._log.DebugFormat("NUMERO ALLEGATI TROVATI SU DOCUMENTIISTANZA: {0}", iCountDocIstanza);

            var pListIstanzeAll = this.RepositoryGetAllegatiDaCodiceIstanza(codiceIstanza);
            var iCountIstanzeAll = pListIstanzeAll.Count;

            this._log.DebugFormat("NUMERO ALLEGATI TROVATI SU ISTANZEALLEGATI: {0}", iCountIstanzeAll);

            var listIstanzeProcure = this.RepositoryGetProcureDaCodiceIstanza(codiceIstanza);
            var iCountIstanzeProcure = listIstanzeProcure.Count;

            this._log.DebugFormat("NUMERO ALLEGATI TROVATI SU ISTANZEPROCURE: {0}", iCountIstanzeProcure);

            var listaProcureSoggettiCollegati = this.RepositoryGetProcureSoggettiCollegati(codiceIstanza);
            var numeroProcureSoggettiCollegati = listaProcureSoggettiCollegati.Count;

            this._log.DebugFormat("NUMERO ALLEGATI TROVATI SU ISTANZERICHIEDENTI: {0}", numeroProcureSoggettiCollegati);

            var iCount = iCountDocIstanza + iCountIstanzeAll + iCountIstanzeProcure + numeroProcureSoggettiCollegati;

            this._log.DebugFormat("NUMERO ALLEGATI TOTALI: {0}", iCount);

            var listaAllegati = new List<AllegatoType>();

            listaAllegati.AddRange(pListDocIstanza.Select(x => new AllegatoType
            {
                Cod = x.CODICEOGGETTO,
                Descrizione = x.DOCUMENTO
            }));

            this._log.DebugFormat("INSERITI GLI ALLEGATI SU _dati.Allegati dopo il ciclo su DocumentiIstanza, numero Allegati: {0}", listaAllegati.Count);

            listaAllegati.AddRange(pListIstanzeAll.Select(x => new AllegatoType
            {
                Cod = x.CODICEOGGETTO,
                Descrizione = x.ALLEGATOEXTRA
            }));

            this._log.DebugFormat("INSERITI GLI ALLEGATI SU _dati.Allegati dopo il ciclo su IstanzeAllegati, numero Allegati: {0}", listaAllegati.Count);

            listaAllegati.AddRange(listIstanzeProcure.Where(y => y.CodiceOggettoProcura.HasValue).Select(x => new AllegatoType
            {
                Cod = x.CodiceOggettoProcura.Value.ToString(),
                Descrizione = this.RepositoryGetNomeFile(x.CodiceOggettoProcura.Value)
            }));

            this._log.DebugFormat("INSERITI GLI ALLEGATI SU _dati.Allegati dopo il ciclo su IstanzeProcure, numero Allegati: {0}", listaAllegati.Count);

            listaAllegati.AddRange(listaProcureSoggettiCollegati.Where(y => y.CodiceoggettoProcura.HasValue).Select(x => new AllegatoType
            {
                Cod = x.CodiceoggettoProcura.Value.ToString(),
                Descrizione = this.RepositoryGetNomeFile(x.CodiceoggettoProcura.Value)
            }));

            this._log.DebugFormat("INSERITI GLI ALLEGATI SU _dati.Allegati dopo il ciclo su IstanzeRichiedenti, numero Allegati: {0}", listaAllegati.Count);

            return listaAllegati.ToArray();
        }



        private List<DocumentiIstanza> RepositoryGetDocumentiIstanzaConAllegato(int codiceIstanza)
        {
            var docIstanzaMgr = new DocumentiIstanzaMgr(this._dataBase);
            var docIstanza = new DocumentiIstanza
            {
                CODICEISTANZA = codiceIstanza.ToString(),
                IDCOMUNE = this._idComune
            };
            docIstanza.OthersWhereClause.Add("DOCUMENTIISTANZA.CODICEOGGETTO is not null");

            return docIstanzaMgr.GetList(docIstanza);
        }

        private List<IstanzeAllegati> RepositoryGetAllegatiDaCodiceIstanza(int codiceIstanza)
        {
            var istanzeAllMgr = new IstanzeAllegatiMgr(this._dataBase);
            var istanzeAll = new IstanzeAllegati
            {
                CODICEISTANZA = codiceIstanza.ToString(),
                IDCOMUNE = this._idComune
            };
            istanzeAll.OthersWhereClause.Add("ISTANZEALLEGATI.CODICEOGGETTO is not null");

            return istanzeAllMgr.GetList(istanzeAll);
        }

        public string RepositoryGetClassificaProtocolloByCodiceIntervento(int codiceIntervento)
        {
            var alberoProcMgr = new AlberoProcMgr(this._dataBase);
            return alberoProcMgr.GetClassificaProtocolloFromAlberoProcProtocollo(codiceIntervento, this._idComune, this._software, this._codiceComune);
        }

        public string RepositoryGetClassificaFascicoloByCodiceIntervento(int codiceIntervento)
        {
            var alberoProcMgr = new AlberoProcMgr(this._dataBase);
            return alberoProcMgr.GetClassificaFascicoloFromAlberoProcProtocollo(codiceIntervento, this._idComune, this._software, this._codiceComune);
        }

        public TipiMovimentoStcMappingProtocollo RepositoryGetDatiProtocolloByTipoMovimento(string tipoMovimento)
        {
            var mgrMapping = new TipiMovimentoStcMappingProtocolloMgr(this._dataBase, this._idComune);
            return mgrMapping.GetDatiProtocollo(tipoMovimento);
        }

        public TipiMovimento RepositoryGetTipoMovimentoById(string tipoMoviemnto)
        {
            var tipiMovimentoMgr = new TipiMovimentoMgr(this._dataBase);
            return tipiMovimentoMgr.GetById(tipoMoviemnto, this._idComune);
        }

        public ProtocolloAmministrazioni RepositoryGetAmministrazioneFromAlberoProcProtocollo(int codiceIntervento)
        {
            var ammMgr = new AmministrazioniMgr(this._dataBase);
            var amm = ammMgr.GetFromAlberoProcProtocollo(codiceIntervento, this._idComune, this._software, this._codiceComune);

            return ProtocolloAmministrazioni.FromAmministrazione(amm);
        }

        public string RepositoryGetCodiceAmministrazioneFromAlberoProcProtocollo(int? codiceIntervento, string codiceDefault)
        {
            var ammMgr = new AmministrazioniMgr(this._dataBase);

            if (codiceIntervento.HasValue)
            {
                var codiceAmministrazione = ammMgr.GetCodiceFromAlberoProcProtocollo(codiceIntervento.Value, this._idComune, this._software, this._codiceComune);

                return string.IsNullOrEmpty(codiceAmministrazione) ? codiceDefault : codiceAmministrazione;
            }

            return codiceDefault;
        }

        public string RepositoryGetCodiceTipoDocumentoFromAlberoProcProtocollo(int codiceIntervento)
        {
            var mgr = new ProtocolloTipiDocumentoMgr(this._dataBase);
            return mgr.GetCodiceFromAlberoProcProtocollo(codiceIntervento, this._idComune, this._software, this._codiceComune);
        }

        public ProtocolloAmministrazioni RepositoryGetAmministrazioneByIdProtocollo(int codiceAmministrazione)
        {
            var ammMgr = new AmministrazioniMgr(this._dataBase);
            var amm = ammMgr.GetByIdProtocollo(this._idComune, codiceAmministrazione, this._software, this._codiceComune);

            return ProtocolloAmministrazioni.FromAmministrazione(amm);
        }

        public ProtocolloAnagrafe? RepositoryGetAnagrafeById(string codiceAnagrafe)
        {
            if (string.IsNullOrEmpty(codiceAnagrafe))
            {
                return null;
            }

            return this.RepositoryGetAnagrafeById(Convert.ToInt32(codiceAnagrafe));
        }

        public ProtocolloAnagrafe? RepositoryGetAnagrafeById(int codiceAnagrafe)
        {
            var anagMgr = new ProtocolloAnagrafeMgr(this._dataBase);
            return ProtocolloAnagrafe.FromAnagrafe(anagMgr.GetById(this._idComune, codiceAnagrafe), String.Empty, this);
        }

        public string RepositoryGetCodiceIstat(string codiceComune)
        {
            var anagMgr = new ProtocolloAnagrafeMgr(this._dataBase);
            return anagMgr.GetCodiceIstat(codiceComune);
        }


        public ProtocolloComune RepositoryGetComuneById(string codiceComune)
        {
            var comune = new ComuniMgr(this._dataBase).GetById(codiceComune);

            return ProtocolloComune.FromComuni(comune);
        }

        internal void AggiornaRiferimentiProtocolloSuMovimento(int codiceMovimento, string idProtocollo)
        {
            throw new NotImplementedException();
        }

        #endregion
    }
}
