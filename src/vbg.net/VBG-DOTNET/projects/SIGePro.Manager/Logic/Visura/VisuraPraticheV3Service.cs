using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.Visura.QueryRicerca;
using log4net;
using PersonalLib2.Data;
using PersonalLib2.Data.Providers;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Visura
{
    public class VisuraPraticheV3Service
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly AuthenticationInfo _authInfo;
        private readonly ILog _log = LogManager.GetLogger(typeof(VisuraPraticheV3Service));

        public VisuraPraticheV3Service(IVerticalizzazioniFactory verticalizzazioniFactory, AuthenticationInfo authInfo)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._authInfo = authInfo;
        }


        public RisultatoVisuraPraticaV3 GetListaPratiche(RichiestaListaPraticheV3 richiesta, QueryPaginationRequest paginationRequest = null)
        {
            var vert = this._verticalizzazioniFactory.Create<VerticalizzazioneAreaRiservata>(this._authInfo.Alias, richiesta.Software);

            var limiteRecords = vert?.MaxRecordsRicercaPratiche ?? 200;

            using (var db = this._authInfo.CreateDatabase())
            {
                var query = new QueryRicercaPratiche(db, this._authInfo.IdComune);

                query.SetFiltroAnagrafiche(richiesta.Software, richiesta.PersonaAventeTitolo);

                query.AddCondition(new SoftwareAlberoprocCondition(db, richiesta.Software));
                query.AddCondition(new NomeOCfRichiedenteCondition(db, richiesta.NomeOCfRichiedente));
                query.AddCondition(new NumeroPraticaCondition(db, richiesta.NumeroIstanza));
                query.AddCondition(new MappaliCondition(db, richiesta.DatiCatastali));
                query.AddCondition(new DataIstanzaCondition(db, richiesta.PeriodoPresentazione));
                query.AddCondition(new IndirizzoCondition(db, richiesta.Indirizzo));
                query.AddCondition(new DatiProtocolloCondition(db, richiesta.DatiProtocollo));
                query.AddCondition(new ScCodiceInterventoCondition(db, this.GetScCodiceDaCodiceIntervento(db, richiesta.CodiceIntervento)));
                query.AddCondition(new NumeroAutorizzazioneCondition(db, richiesta.NumeroAutorizzazione));
                query.AddCondition(new OggettoPraticaCondition(db, richiesta.Oggetto));
                query.AddCondition(new StatoPraticaCondition(db, richiesta.StatoPratica));
                query.AddCondition(new EscludiInterventiCondition(db));
                query.AddCondition(new FabbricatoCondition(db, richiesta.Fabbricato));
                query.AddCondition(new PosizioneArchivioCondition(db, richiesta.PosizioneArchivio));

                var queryVisura = query.GetQuery();

                this._log.Debug($"Query visura pratiche: {queryVisura}");

                var queryConta = $"select count(*) from ({queryVisura}) cntTbl";

                var stringaParametri = new StringBuilder("Parametri: \r\n");

                query.GetParameters().ToList().ForEach(x => stringaParametri.Append($"{x.ParameterName} = {x.Value}\r\n"));

                this._log.Debug(stringaParametri.ToString());

                var recordCount = db.ExecuteScalar(queryConta, 0, mp =>
                {
                    query.GetParameters().ToList().ForEach(x => mp.AddParameter(x.ParameterName, x.Value));
                });

                if (recordCount == 0)
                {
                    this._log.Debug("Non sono state trovate pratiche");

                    return new RisultatoVisuraPraticaV3
                    {
                        LimiteRecordsSuperato = false,
                        RecordCount = 0,
                        PageSize = limiteRecords,
                        CurrentPage = 0,
                        TotalPages = 0
                    };
                }

                var pageSize = paginationRequest?.PageSize ?? recordCount;
                var totalPages = recordCount / pageSize;
                var currentPage = 0;

                if ((recordCount % pageSize) > 0)
                {
                    totalPages++;
                }

                if (paginationRequest == null && recordCount > limiteRecords)
                {
                    this._log.DebugFormat("Limite record pratiche in visura superato (max={0}, attuale={1})", limiteRecords, recordCount);

                    return new RisultatoVisuraPraticaV3
                    {
                        LimiteRecordsSuperato = true,
                        RecordCount = recordCount,
                        PageSize = limiteRecords,
                        CurrentPage = currentPage,
                        TotalPages = totalPages
                    };
                }

                var orderByCondition = richiesta.OrderByDate == OrderByDateEnum.Descending ? "DESC" : "ASC";
                var orderBy = $" order by ISTANZE.DATA {orderByCondition}";

                queryVisura += orderBy;

                if (paginationRequest != null)
                {
                    if (paginationRequest.CurrentPage >= totalPages)
                    {
                        paginationRequest.CurrentPage = totalPages - 1;
                    }

                    currentPage = paginationRequest.CurrentPage;

                    queryVisura = db.Specifics.PaginateQuery(queryVisura, paginationRequest);
                }

                var pratiche = db.ExecuteReader(queryVisura,
                    mp =>
                    {
                        query.GetParameters().ToList().ForEach(x => mp.AddParameter(x.ParameterName, x.Value));
                    },
                    dr => new VisuraListItemV3
                    {
                        Azienda = (dr.GetString("AZ_NOMINATIVO") + " " + dr.GetString("AZ_NOME")).Trim(),
                        Civico = dr.GetString("PR_CIVICO"),
                        CodiceIstanza = dr.GetInt("IDPRATICA").Value,
                        DataPresentazione = dr.GetDateTime("DATAPRESENTAZIONE").Value,
                        DataProtocollo = dr.GetDateTime("DATAPROTOCOLLO"),
                        Foglio = dr.GetString("FOGLIO"),
                        LocalizzazioneConCivico = dr.GetString("INDIRIZZO_PREFISSO") + " " + dr.GetString("INDIRIZZO_DESCRIZIONE") + " " + dr.GetString("PR_CIVICO"),
                        NumeroIstanza = dr.GetString("NUMEROPRATICA"),
                        NumeroProtocollo = dr.GetString("NUMEROPROTOCOLLO"),
                        Oggetto = dr.GetString("OGGETTO"),
                        Particella = dr.GetString("PARTICELLA"),
                        PosizioneArchivio = dr.GetString("POSIZIONEARCHIVIO"),
                        CodiceArea = "-",
                        Operatore = dr.GetString("RESPONSABILE"),
                        Progressivo = "-",
                        Richiedente = (dr.GetString("RIC_NOMINATIVO") + " " + dr.GetString("RIC_NOME")).Trim(),
                        Software = dr.GetString("DESCSOFTWARE"),
                        Stato = dr.GetString("STATOPRATICA"),
                        Subalterno = dr.GetString("SUB"),
                        TipoCatasto = dr.GetString("TIPOCATASTO"),
                        TipoIntervento = dr.GetString("DESCRIZIONEINTERVENTO"),
                        TipoProcedura = dr.GetString("PROCEDURA"),
                        Uuid = dr.GetString("UUID")
                    });

                this._log.DebugFormat("Trovate {0} pratiche", pratiche?.Count() ?? 0);

                return new RisultatoVisuraPraticaV3
                {
                    LimiteRecordsSuperato = false,
                    Pratiche = pratiche.ToArray(),
                    RecordCount = recordCount,
                    PageSize = limiteRecords,
                    CurrentPage = currentPage,
                    TotalPages = totalPages
                };
            }
        }

        private string GetScCodiceDaCodiceIntervento(DataBase db, int? codiceIntervento)
        {
            if (codiceIntervento.HasValue)
            {
                var query = $"select sc_codice from alberoproc where idcomune = {db.Specifics.QueryParameterName("idcomune")} and sc_id = {db.Specifics.QueryParameterName("codiceIntervento")}";
                var parameters = new List<ParameterDefinition>
                {
                    new ParameterDefinition("idcomune", this._authInfo.IdComune),
                    new ParameterDefinition("codiceIntervento", codiceIntervento)
                };

                var scCodice = db.ExecuteScalar<object>(query, null, mp =>
                {
                    parameters.ToList().ForEach(x => mp.AddParameter(x.ParameterName, x.Value));
                });

                return (scCodice == DBNull.Value) ? null : scCodice.ToString();
            }
            return null;
        }
    }
}
