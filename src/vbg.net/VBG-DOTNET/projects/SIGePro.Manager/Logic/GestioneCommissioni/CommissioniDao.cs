using Init.SIGePro.Manager.DTO.Commissioni;
using Init.SIGePro.Manager.Logic.GestioneSequenceTable;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni
{
    public class CommissioniDao : ICommissioniDao
    {
        private static class Constants
        {
            public const string CategoriaAllegatiGenerali = "Documenti generali";
            public const string CommedilizieAllFirmaSequence = "COMMEDILIZIE_ALL_FIRMA.ID";
        }

        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly ILog _log = LogManager.GetLogger(typeof(CommissioniDao));

        public CommissioniDao(DataBase db, string idComune)
        {
            _db = db;
            _idComune = idComune;
        }

        public IEnumerable<CommissioneTestataDto> GetCommissioniAperteByCodiceAnagrafe(int codiceAnagrafe)
        {
            // ATTENZIONE, se si modifica questa query probabilmente va modificata anche la query di
            // VerificaAccessoACommissione
            var sql = $@"
                                             
        SELECT 
            commissioniedilizie_t.codicecommissione as id,
            commissioniedilizie_t.data,
            commissioniedilizie_t.numprotocollo,
            commissioniedilizie_t.descrizione,
            commissioniedilizie_t.note,
            commedilizie_tipologie.descrizione AS tipologia,
            commedilizie_convocazioni.id as idConvocazione,
            commedilizie_convocazioni.dataconvocazione,
            commedilizie_convocazioni.oraconvocazione,
            commissioniedilizie_t.OraInizio,
            commissioniedilizie_t.OraFine,
            commissioniedilizie_t.flag_sincrona
        FROM 
            commedilizie_appello 
 
                inner JOIN commissioniedilizie_t ON 
                    commissioniedilizie_t.idcomune = commedilizie_appello.idcomune AND
                    commissioniedilizie_t.codicecommissione = commedilizie_appello.codicecommissione
        
                inner JOIN commedilizie_convocazioni ON 
                    commedilizie_convocazioni.idcomune = commissioniedilizie_t.idcomune AND
                    commedilizie_convocazioni.id = commissioniedilizie_t.idconvocazione

                INNER JOIN commedilizie_tipologie ON
                    commedilizie_tipologie.idcomune = commissioniedilizie_t.idcomune AND 
                    commedilizie_tipologie.codcommtipologia = commissioniedilizie_t.codcommtipologia             
   
        WHERE
            commedilizie_appello.idcomune = {this._db.QueryParameter("idComune")} AND
            commedilizie_appello.codiceanagrafe = {this._db.QueryParameter("codiceAnagrafe")} AND
            (commissioniedilizie_t.data_fine IS NULL OR data_fine > {this._db.QueryParameter("data")}) AND
            commissioniedilizie_t.flagaperta = 1 
                             
        ORDER BY commissioniedilizie_t.data, commissioniedilizie_t.orainizio ASC
";

            return this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("codiceAnagrafe", codiceAnagrafe);
                    mp.AddParameter("data", DateTime.Now);

                },
                dr => new CommissioneTestataDto
                {
                    Id = dr.GetInt("id").Value,
                    Data = dr.GetDateTime("data")?.ToString("dd/MM/yyyy"),
                    Numero = dr.GetString("numprotocollo"),
                    Descrizione = dr.GetString("descrizione"),
                    Note = dr.GetString("note"),
                    Asincrona = dr.GetInt("flag_sincrona").GetValueOrDefault(1) == 0,
                    Tipologia = dr.GetString("tipologia"),
                    OraInizio = dr.GetString("OraInizio"),
                    OraFine = dr.GetString("OraFine"),
                    Convocazione = dr.GetInt("idConvocazione").HasValue ? new ConvocazioneCommissioneDto
                    {
                        Id = dr.GetInt("idConvocazione").Value,
                        Data = dr.GetDateTime("dataconvocazione").Value.ToString("dd/MM/yyyy"),
                        Ora = dr.GetString("oraconvocazione")
                    } : null
                });
        }

        /// <summary>
        /// Verifica se un utente di frontend può accedere ad una commissione
        /// </summary>
        /// <param name="idCommissione"></param>
        /// <param name="codiceAnagrafe"></param>
        /// <returns></returns>
        public bool VerificaAccessoACommissione(int idCommissione, int codiceAnagrafe)
        {
            var sql = $@"
        SELECT 
            Count(commissioniedilizie_t.idcomune)
        FROM
            commedilizie_appello
                inner JOIN commissioniedilizie_t  ON 
                    commissioniedilizie_t.idcomune = commedilizie_appello.idcomune AND
                    commissioniedilizie_t.codicecommissione = commedilizie_appello.codicecommissione
        
            WHERE 
                commissioniedilizie_t.idcomune = {this._db.QueryParameter("idComune")} AND
                commedilizie_appello.codiceanagrafe = {this._db.QueryParameter("codiceAnagrafe")} AND
                (commissioniedilizie_t.data_fine IS NULL OR data_fine > {this._db.QueryParameter("data")}) AND
                commissioniedilizie_t.flagaperta = 1 and
                commissioniedilizie_t.codicecommissione = {this._db.QueryParameter("idCommissione")}";

            return this._db.ExecuteScalar(sql, 0, mp =>
            {
                mp.AddParameter("idComune", this._idComune);
                mp.AddParameter("codiceAnagrafe", codiceAnagrafe);
                mp.AddParameter("data", DateTime.Now);
                mp.AddParameter("idCommissione", idCommissione);
            }) > 0;
        }


        /// <summary>
        /// Carica i dati di testata di una commissione.
        /// ATTENZIONE!!! Non verifica gli accessi a tale commissione
        /// </summary>
        /// <param name="idCommissione"></param>
        /// <returns></returns>
        public CommissioneTestataDto CaricaTestata(int idCommissione)
        {
            // Quando si arriva qui dovrebbero essere già stati effettuati tutti i controlli sui permessi di accesso
            var sql = $@"
SELECT
    testata.codicecommissione as id,
    testata.data,
    testata.numprotocollo,
    testata.descrizione,
    testata.note,
    commedilizie_tipologie.descrizione AS tipologia,
    commedilizie_convocazioni.id as idConvocazione,
    commedilizie_convocazioni.dataconvocazione,
    commedilizie_convocazioni.oraconvocazione,
    testata.OraInizio,
    testata.OraFine,
    testata.flag_sincrona

from
    commissioniedilizie_t testata 
        
        left OUTER JOIN commedilizie_convocazioni ON 
            testata.idcomune = commedilizie_convocazioni.idcomune AND
            testata.idconvocazione = commedilizie_convocazioni.id

        INNER JOIN commedilizie_tipologie ON
            commedilizie_tipologie.idcomune = testata.idcomune AND 
            commedilizie_tipologie.codcommtipologia = testata.codcommtipologia

WHERE 
    testata.idcomune = {this._db.QueryParameter("idComune")} AND 
    testata.codicecommissione = {this._db.QueryParameter("idCommissione")}
";

            return this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("idCommissione", idCommissione);
                },
                dr => new CommissioneTestataDto
                {
                    Id = dr.GetInt("id").Value,
                    Data = dr.GetDateTime("data")?.ToString("dd/MM/yyyy"),
                    Numero = dr.GetString("numprotocollo"),
                    Descrizione = dr.GetString("descrizione"),
                    Note = dr.GetString("note"),
                    Asincrona = dr.GetInt("flag_sincrona").GetValueOrDefault(1) == 0,
                    Tipologia = dr.GetString("tipologia"),
                    OraInizio = dr.GetString("OraInizio"),
                    OraFine = dr.GetString("OraFine"),
                    Convocazione = dr.GetDateTime("dataconvocazione").HasValue ? new ConvocazioneCommissioneDto
                    {
                        Id = dr.GetInt("idConvocazione").Value,
                        Data = dr.GetDateTime("dataconvocazione").Value.ToString("dd/MM/yyyy"),
                        Ora = dr.GetString("oraconvocazione")
                    } : null
                }).FirstOrDefault();
        }


        /// <summary>
        /// Restituisce la lista delle convocazioni a partire da un id di testata
        /// </summary>
        /// <param name="idCommissione"></param>
        /// <returns></returns>
        public IEnumerable<ConvocazioneCommissioneDto> CaricaConvocazioniDaIdTestata(int idCommissione)
        {
            var sql = $@"
SELECT
    id,
    dataconvocazione, 
    oraconvocazione
FROM 
    commedilizie_convocazioni 
WHERE 
    idcomune = {this._db.QueryParameter("idComune")} AND 
    codicecommissione = {this._db.QueryParameter("idCommissione")} 
ORDER BY 
    dataconvocazione ASC, 
    oraconvocazione asc
";

            return this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("idCommissione", idCommissione);
                },
                dr => new ConvocazioneCommissioneDto
                {
                    Id = dr.GetInt("Id").Value,
                    Data = dr.GetDateTime("dataconvocazione").Value.ToString("dd/MM/yyyy"),
                    Ora = dr.GetString("oraconvocazione")
                });
        }

        /// <summary>
        /// Carica la lista di pratiche per la commissione passata a cui l'utente ha accesso
        /// </summary>
        /// <param name="idCommissione"></param>
        /// <param name="codiceAnagrafe"></param>
        /// <returns></returns>
        public IEnumerable<PraticaCommissioneBreveDto> CaricaPraticheCommissione(int idCommissione, int codiceAnagrafe)
        {
            var sql = $@"
SELECT
    commissioniedilizie_r.id AS idCommissioniR,
    istanze.codiceistanza as id,
    istanze.uuid as uuid,
    comuni.comune,
    istanze.numeroistanza,
    istanze.data,
    istanze.numeroprotocollo,
    istanze.dataprotocollo,
    anagrafe.nominativo,
    anagrafe.nome,
    anagrafe.codicefiscale,                                                        
    anagrafe.tipoanagrafe,
    anagrafe.partitaiva,
    istanze.lavori,
    alberoproc.descrizione_completa as descrizione_intervento,
    commedilizie_tipopareri.descrizione as descrizione_parere,
    mov2.parere as parere_esteso
    
FROM                                           
    commissioniedilizie_r

        INNER JOIN movimenti ON
            movimenti.idcomune = commissioniedilizie_r.idcomune AND
            movimenti.codicemovimento = commissioniedilizie_r.codicemovimento

        INNER JOIN istanze ON 
            istanze.idcomune = movimenti.idcomune AND
            istanze.codiceistanza = movimenti.codiceistanza

        INNER JOIN comuni ON
            istanze.codicecomune = comuni.codicecomune

        INNER JOIN anagrafe ON                                       
            anagrafe.idcomune = istanze.idcomune and
            anagrafe.codiceanagrafe = istanze.codicerichiedente

        INNER JOIN alberoproc ON
            alberoproc.idcomune = istanze.idcomune AND
            alberoproc.sc_id = istanze.codiceinterventoproc

        left outer join commedilizie_tipopareri on 
            commedilizie_tipopareri.idcomune = commissioniedilizie_r.idcomune and
            commedilizie_tipopareri.codice = commissioniedilizie_r.tipoparere

        left OUTER JOIN movimenti mov2 ON
            mov2.idcomune = commissioniedilizie_r.idcomune AND 
            mov2.codicemovimento = commissioniedilizie_r.codicemovimentorientro

WHERE 
    commissioniedilizie_r.idcomune = {this._db.QueryParameter("idComune")} AND
    commissioniedilizie_r.codicecommissione = {this._db.QueryParameter("idCommissione")}

ORDER BY
    commissioniedilizie_r.ordine
";

            var pratiche = this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("idCommissione", idCommissione);
                },
                dr => new PraticaCommissioneBreveDto
                {
                    Id = dr.GetInt("id").Value,
                    Uuid = dr.GetString("uuid"),
                    IdCommissioniR = dr.GetInt("idCommissioniR").Value,
                    Comune = dr.GetString("comune"),
                    NumeroData = $"Numero {dr.GetString("numeroistanza")} del {dr.GetDateTime("data").Value.ToString("dd/MM/yyyy")}",
                    DatiProtocollo = String.IsNullOrEmpty(dr.GetString("numeroprotocollo")) ? "Istanza non protocollata" :
                                        $"Numero {dr.GetString("numeroprotocollo")} del {dr.GetDateTime("dataprotocollo").Value.ToString("dd/MM/yyyy")}",
                    DescrizioneLavori = dr.GetString("lavori"),
                    Intervento = dr.GetString("descrizione_intervento"),
                    Richiedente = this.DatiAnagrafica(
                                        dr.GetString("nominativo"),
                                        dr.GetString("nome"),
                                        dr.GetString("codicefiscale"),
                                        dr.GetString("partitaiva"),
                                        dr.GetString("tipoanagrafe")),
                    DescrizioneParere = dr.GetString("descrizione_parere"),
                    ParereEsteso = dr.GetString("parere_esteso"),
                    DocumentiSelezionati = this.ContaDocumentiPratica(dr.GetInt("idCommissioniR").Value)
                });

            // Si potrebbe utilizzare la lista degli id come condizione where in della query
            // qui sopra ma siccome le pratiche che normalmente vengono discusse sono poche
            // per comodità applico il filtro lato codice
            var idPraticheConAccesso = GetFiltroIdIstanzeDaIdAnagrafe(idCommissione, codiceAnagrafe);

            return pratiche.Where(x => idPraticheConAccesso.Contains(x.Id)).ToList();
        }

        /// <summary>
        /// Ottiene la lista delle istanze di una commissione che un anagrafica può vedere
        /// </summary>
        /// <param name="idCommissione"></param>
        /// <param name="codiceAnagrafe"></param>
        /// <returns></returns>
        private IEnumerable<int> GetFiltroIdIstanzeDaIdAnagrafe(int idCommissione, int codiceAnagrafe)
        {
            var sql = $@"
SELECT distinct
    movimenti.codiceistanza
FROM          

    commissioniedilizie_r

        INNER JOIN movimenti ON
            movimenti.idcomune = commissioniedilizie_r.idcomune AND
            movimenti.codicemovimento = commissioniedilizie_r.codicemovimento                                         
                                        
        INNER JOIN commedilizie_appello_pratiche ON
            commedilizie_appello_pratiche.idcomune = commissioniedilizie_r.idcomune AND
            commedilizie_appello_pratiche.fk_commedilizier = commissioniedilizie_r.id

        INNER JOIN commedilizie_appello ON 
            commedilizie_appello.idcomune =  commedilizie_appello_pratiche.idcomune AND
            commedilizie_appello.id =  commedilizie_appello_pratiche.fk_appello

WHERE
    commissioniedilizie_r.idcomune = {this._db.QueryParameter("idcomune")} AND
    commissioniedilizie_r.codicecommissione = {this._db.QueryParameter("idCommissione")} AND
    commedilizie_appello.codiceanagrafe = {this._db.QueryParameter("codiceAnagrafe")}
";

            return this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idcomune", _idComune);
                    mp.AddParameter("idCommissione", idCommissione);
                    mp.AddParameter("codiceAnagrafe", codiceAnagrafe);
                },
                dr => dr.GetInt32(0));
        }

        private int ContaDocumentiPratica(int idCommissioniR)
        {
            var sql = $@"
SELECT Sum(cnt) FROM 
(
    SELECT 
        Count(*) AS cnt 
    FROM
        commedr_docist
    WHERE 
        commedr_docist.idcomune = {_db.QueryParameter("idComune1")} AND
        commedr_docist.fk_commedilizier = {_db.QueryParameter("idCommissioniR1")}
    UNION ALL
    SELECT 
        Count(*)  AS cnt
    FROM                                                  
        commedr_istall
    WHERE 
        commedr_istall.idcomune = {_db.QueryParameter("idComune2")} AND
        commedr_istall.fk_commedilizier = {_db.QueryParameter("idCommissioniR2")}
    UNION ALL
    SELECT 
        Count(*)  AS cnt                                   
    FROM                                                  
        commedr_movall
    WHERE 
        commedr_movall.idcomune = {_db.QueryParameter("idComune3")} AND
        commedr_movall.fk_commedilizier = {_db.QueryParameter("idCommissioniR3")}
) A
";
            var tempDb = new DataBase(this._db.ConnectionDetails.ConnectionString, this._db.ConnectionDetails.ProviderType);
            return tempDb.ExecuteScalar(sql, 0, mp =>
            {
                mp.AddParameter("idComune1", this._idComune);
                mp.AddParameter("idCommissioniR1", idCommissioniR);
                mp.AddParameter("idComune2", this._idComune);
                mp.AddParameter("idCommissioniR2", idCommissioniR);
                mp.AddParameter("idComune3", this._idComune);
                mp.AddParameter("idCommissioniR3", idCommissioniR);
            });
        }

        private string DatiAnagrafica(string nominativo, string nome, string codicefiscale, string partitaiva, string tipoanagrafe)
        {
            var sb = new StringBuilder();

            if (!String.IsNullOrEmpty(nome))
            {
                sb.Append(nome).Append(" ");
            }

            sb.Append(nominativo);

            if (!String.IsNullOrEmpty(codicefiscale))
            {
                sb.Append(" CF: ").Append(codicefiscale);
            }

            if (!String.IsNullOrEmpty(partitaiva))
            {
                sb.Append(" PI: ").Append(partitaiva);
            }

            sb.Append(" [P.").Append(tipoanagrafe).Append(".]");

            return sb.ToString();
        }

        public PraticaCommissioneEstesaDto CaricaPratica(int idCommissione, int codiceAnagrafe, int codiceIstanza)
        {
            // Faccio più velocemente così perché la query sarebbe complessa da manutenere.
            // Siccome il numero di pratiche discusse in commissione è limitato (<10)
            // Non dovrebbe creare rallentamenti significativi. Se dovesse diventare lenta allora va 
            // creata una query ad hoc
            var datiPratica = this.CaricaPraticheCommissione(idCommissione, codiceAnagrafe)
                                        .Where(x => x.Id == codiceIstanza)
                                        .FirstOrDefault();

            if (datiPratica == null)
            {
                return null;
            }


            var pratica = new PraticaCommissioneEstesaDto();
            pratica.DatiPratica = datiPratica;
            pratica.Localizzazioni = this.CaricaLocalizzazioni(codiceIstanza);
            pratica.Documenti = this.CaricaDocumenti(idCommissione, codiceIstanza);

            return pratica;
        }

        private ListaDocumentiPraticaCommissioneDto CaricaDocumenti(int idCommissione, int codiceIstanza)
        {
            return new ListaDocumentiPraticaCommissioneDto
            {
                Istanza = CaricaDocumentiIstanza(idCommissione, codiceIstanza),
                Endoprocedimenti = CaricaDocumentiEndo(idCommissione, codiceIstanza),
                Movimenti = CaricaDocumentiMovimenti(idCommissione, codiceIstanza)
            };
        }

        private IEnumerable<DocumentoPraticaCommissioneDto> CaricaDocumentiMovimenti(int idCommissione, int codiceIstanza)
        {
            var sql = $@"
SELECT
    movimenti.movimento AS categoria, 
    movimentiallegati.descrizione AS descrizione,
    oggetti.nomefile,
    oggetti.codiceoggetto,
    uuid.valore AS uid_file,
    firma_digitale_presente.valore AS firmato_digitalmente
FROM                                                                                                                            
    commissioniedilizie_r
                                                                                  
        INNER JOIN commedr_movall ON                                                                                        
            commedr_movall.idcomune = commissioniedilizie_r.idcomune AND
            commedr_movall.fk_commedilizier = commissioniedilizie_r.id
                                                                                                          
        INNER JOIN movimentiallegati ON
            movimentiallegati.idcomune = commedr_movall.idcomune AND     
            movimentiallegati.id = commedr_movall.fk_movimentiallegati                                                     
                                                                                                                 
        INNER JOIN movimenti ON
            movimenti.idcomune = movimentiallegati.idcomune AND
            movimenti.codicemovimento = movimentiallegati.codicemovimento 
                                                                                                         
        INNER JOIN oggetti ON                                                                                                
            oggetti.idcomune = movimentiallegati.idcomune AND
            oggetti.codiceoggetto = movimentiallegati.codiceoggetto

        LEFT OUTER JOIN oggetti_metadati uuid ON                                                                                              
            uuid.idcomune = oggetti.idcomune AND                                               
            uuid.codiceoggetto = oggetti.codiceoggetto AND
            uuid.chiave = 'UID'

        LEFT OUTER JOIN oggetti_metadati firma_digitale_presente ON
            firma_digitale_presente.idcomune = oggetti.idcomune AND
            firma_digitale_presente.codiceoggetto = oggetti.codiceoggetto AND
            firma_digitale_presente.chiave = 'FIRMA_DIGITALE_PRESENTE'
WHERE                                                                            
    commissioniedilizie_r.idcomune = {this._db.QueryParameter("idComune")} AND
    commissioniedilizie_r.codicecommissione = {this._db.QueryParameter("idCommissione")} and
    movimenti.codiceistanza = {this._db.QueryParameter("codiceIstanza")} 
ORDER BY 
    movimenti.data
";

            return this._db.ExecuteReader(sql,
                            mp =>
                            {
                                mp.AddParameter("idComune", _idComune);
                                mp.AddParameter("idCommissione", idCommissione);
                                mp.AddParameter("codiceIstanza", codiceIstanza);
                            },
                            dr => new DocumentoPraticaCommissioneDto
                            {
                                Categoria = dr.GetString("categoria"),
                                Descrizione = dr.GetString("descrizione"),
                                NomeFile = dr.GetString("nomefile"),
                                CodiceOggetto = dr.GetInt("codiceoggetto"),
                                FirmatoDigitalmente = dr.GetString("firmato_digitalmente") == "S",
                                Uid = dr.GetString("uid_file")
                            });
        }

        private IEnumerable<DocumentoPraticaCommissioneDto> CaricaDocumentiEndo(int idCommissione, int codiceIstanza)
        {
            var sql = $@"
SELECT
    inventarioprocedimenti.procedimento AS categoria, 
    istanzeallegati.allegatoextra AS descrizione,
    oggetti.nomefile,
    oggetti.codiceoggetto,
    uuid.valore AS uid_file,
    firma_digitale_presente.valore AS firmato_digitalmente
FROM 
    commissioniedilizie_r

        INNER JOIN commedr_istall ON                                                
            commedr_istall.idcomune = commissioniedilizie_r.idcomune AND
            commedr_istall.fk_commedilizier = commissioniedilizie_r.id

        INNER JOIN istanzeallegati ON
            istanzeallegati.idcomune = commedr_istall.idcomune AND
            istanzeallegati.id = commedr_istall.fk_istanzeallegati 

        INNER JOIN inventarioprocedimenti ON
            inventarioprocedimenti.idcomune = istanzeallegati.idcomune AND
            inventarioprocedimenti.codiceinventario = istanzeallegati.codiceinventario 
                                                                                                         
        INNER JOIN oggetti ON                                                                                                
            oggetti.idcomune = istanzeallegati.idcomune AND
            oggetti.codiceoggetto = istanzeallegati.codiceoggetto

        LEFT OUTER JOIN oggetti_metadati uuid ON                                                                                              
            uuid.idcomune = oggetti.idcomune AND                                               
            uuid.codiceoggetto = oggetti.codiceoggetto AND
            uuid.chiave = 'UID'

        LEFT OUTER JOIN oggetti_metadati firma_digitale_presente ON
            firma_digitale_presente.idcomune = oggetti.idcomune AND
            firma_digitale_presente.codiceoggetto = oggetti.codiceoggetto AND
            firma_digitale_presente.chiave = 'FIRMA_DIGITALE_PRESENTE'
WHERE                                                                            
    commissioniedilizie_r.idcomune = {this._db.QueryParameter("idComune")} AND
    commissioniedilizie_r.codicecommissione = {this._db.QueryParameter("idCommissione")} and
    istanzeallegati.codiceistanza = {this._db.QueryParameter("codiceIstanza")} 
ORDER BY 
    inventarioprocedimenti.ordine

";

            return this._db.ExecuteReader(sql,
                            mp =>
                            {
                                mp.AddParameter("idComune", _idComune);
                                mp.AddParameter("idCommissione", idCommissione);
                                mp.AddParameter("codiceIstanza", codiceIstanza);
                            },
                            dr => new DocumentoPraticaCommissioneDto
                            {
                                Categoria = dr.GetString("categoria"),
                                Descrizione = dr.GetString("descrizione"),
                                NomeFile = dr.GetString("nomefile"),
                                CodiceOggetto = dr.GetInt("codiceoggetto"),
                                FirmatoDigitalmente = dr.GetString("firmato_digitalmente") == "S",
                                Uid = dr.GetString("uid_file")
                            });
        }

        private IEnumerable<DocumentoPraticaCommissioneDto> CaricaDocumentiIstanza(int idCommissione, int codiceIstanza)
        {
            var sql = $@"
SELECT 
    documentiistanza.documento as descrizione,
    oggetti.nomefile,
    oggetti.codiceoggetto,
    uuid.valore AS uid_file,
    firma_digitale_presente.valore AS firmato_digitalmente
FROM 
    commissioniedilizie_r

        INNER JOIN commedr_docist ON
            commedr_docist.idcomune = commissioniedilizie_r.idcomune AND
            commedr_docist.fk_commedilizier = commissioniedilizie_r.id

        INNER JOIN documentiistanza ON
            documentiistanza.idcomune = commedr_docist.idcomune AND
            documentiistanza.id = commedr_docist.fk_documentiistanza 
                                                             
        INNER JOIN oggetti ON
            oggetti.idcomune = documentiistanza.idcomune AND
            oggetti.codiceoggetto = documentiistanza.codiceoggetto

        LEFT OUTER JOIN oggetti_metadati uuid ON
            uuid.idcomune = oggetti.idcomune AND                                               
            uuid.codiceoggetto = oggetti.codiceoggetto AND
            uuid.chiave = 'UID'

        LEFT OUTER JOIN oggetti_metadati firma_digitale_presente ON
            firma_digitale_presente.idcomune = oggetti.idcomune AND
            firma_digitale_presente.codiceoggetto = oggetti.codiceoggetto AND
            firma_digitale_presente.chiave = 'FIRMA_DIGITALE_PRESENTE'
WHERE                                                                            
    commissioniedilizie_r.idcomune = {this._db.QueryParameter("idComune")} AND
    commissioniedilizie_r.codicecommissione = {this._db.QueryParameter("idCommissione")} and
    documentiistanza.codiceistanza = {this._db.QueryParameter("codiceIstanza")}";

            return this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", _idComune);
                    mp.AddParameter("idCommissione", idCommissione);
                    mp.AddParameter("codiceIstanza", codiceIstanza);
                },
                dr => new DocumentoPraticaCommissioneDto
                {
                    Categoria = Constants.CategoriaAllegatiGenerali,
                    Descrizione = dr.GetString("descrizione"),
                    NomeFile = dr.GetString("nomefile"),
                    CodiceOggetto = dr.GetInt("codiceoggetto"),
                    FirmatoDigitalmente = dr.GetString("firmato_digitalmente") == "S",
                    Uid = dr.GetString("uid_file")
                });
        }

        private IEnumerable<LocalizzazionePraticaCommissioneDto> CaricaLocalizzazioni(int codiceIstanza)
        {
            var sql = $@"
SELECT 
    stradario.prefisso,
    stradario.descrizione,
    istanzestradario.civico,
    istanzestradario.esponente,
    istanzestradario.colore,
    istanzestradario.km,
    istanzemappali.codicecatasto,
    istanzemappali.sezione,
    istanzemappali.foglio,
    istanzemappali.particella,
    istanzemappali.sub
FROM 
    istanzestradario                                    
        INNER JOIN stradario ON
            stradario.idcomune = istanzestradario.idcomune AND
            stradario.codicestradario = istanzestradario.codicestradario
        left outer JOIN istanzemappali ON
            istanzemappali.idcomune = istanzestradario.idcomune AND
            istanzemappali.fkidistanzestradario = istanzestradario.id AND
            istanzemappali.primario = 1
WHERE                                                                            
    istanzestradario.idcomune = {this._db.QueryParameter("idComune")} AND
    istanzestradario.codiceistanza = {this._db.QueryParameter("codiceIstanza")}
";

            var localizzazioni = this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", _idComune);
                    mp.AddParameter("codiceIstanza", codiceIstanza);
                },
                dr => new
                {
                    Prefisso = dr.GetString("prefisso"),
                    Descrizione = dr.GetString("descrizione"),
                    Civico = dr.GetString("civico"),
                    Esponente = dr.GetString("esponente"),
                    Colore = dr.GetString("colore"),
                    Km = dr.GetString("km"),
                    Codicecatasto = dr.GetString("codicecatasto"),
                    Sezione = dr.GetString("sezione"),
                    Foglio = dr.GetString("foglio"),
                    Particella = dr.GetString("particella"),
                    Sub = dr.GetString("sub")
                });

            return localizzazioni.Select(x =>
            {

                var sb = new StringBuilder($"{x.Prefisso} {x.Descrizione} {x.Civico}");

                if (!String.IsNullOrEmpty(x.Esponente)) sb.Append("/").Append(x.Esponente);
                if (!String.IsNullOrEmpty(x.Colore)) sb.Append("/").Append(x.Colore);
                if (!String.IsNullOrEmpty(x.Km)) sb.Append(" (Km").Append(x.Km).Append(")");

                var toponimo = sb.ToString();

                sb.Clear();

                if (!String.IsNullOrEmpty(x.Codicecatasto)) sb.Append(x.Codicecatasto == "T" ? "Terreni" : "Fabbricati").Append("");
                if (!String.IsNullOrEmpty(x.Sezione)) sb.Append(", s: ").Append(x.Sezione);
                if (!String.IsNullOrEmpty(x.Foglio)) sb.Append(", f: ").Append(x.Foglio);
                if (!String.IsNullOrEmpty(x.Particella)) sb.Append(", p: ").Append(x.Particella);
                if (!String.IsNullOrEmpty(x.Sub)) sb.Append(", s: ").Append(x.Sub);

                var mappali = sb.ToString();

                return new LocalizzazionePraticaCommissioneDto
                {
                    Toponimo = toponimo,
                    Mappali = mappali
                };
            });
        }

        public bool PraticaAperta(int idCommissione, int codiceIstanza)
        {
            var sql = $@"SELECT count(*) 
FROM 
    movimenti          

    INNER JOIN commissioniedilizie_r ON
        commissioniedilizie_r.idcomune = movimenti.idcomune AND
        commissioniedilizie_r.codicemovimento = movimenti.codicemovimento AND
        commissioniedilizie_r.tipoparere IS null

WHERE 
    movimenti.idcomune = {this._db.QueryParameter("idcomune")} AND
    movimenti.codiceistanza = {this._db.QueryParameter("codiceIstanza")}
";

            return this._db.ExecuteScalar(sql, 0, mp =>
            {
                mp.Add("idcomune", _idComune);
                mp.Add("codiceIstanza", codiceIstanza);
            }) == 1;
        }

        public bool CommissioneAperta(int idCommissione)
        {
            var sql = $"SELECT flagaperta FROM commissioniedilizie_t WHERE idcomune={this._db.QueryParameter("idcomune")} AND codicecommissione={this._db.QueryParameter("idCommissione")}";

            return this._db.ExecuteScalar(sql, 0, mp =>
            {
                mp.Add("idcomune", _idComune);
                mp.Add("idCommissione", idCommissione);
            }) == 1;
        }

        public IEnumerable<AllegatoCommissioneDao> CaricaDocumentiCommissione(int idCommissione, int idAppello)
        {
            var sql = $@"
SELECT 
    commedilizie_allegati.id,
    commedilizie_allegati.descrizione,
    commedilizie_allegati.dataregistrazione,
    oggetti.codiceoggetto,
    oggetti.nomefile,
    oggetti_metadati.valore AS hash_sha_256,
    commedilizie_all_firma.hash_sha_256 hash_controllo,
    commedilizie_all_firma.data_firma

FROM 
    commedilizie_allegati

    INNER JOIN oggetti ON
        oggetti.idcomune = commedilizie_allegati.idcomune AND
        oggetti.codiceoggetto = commedilizie_allegati.codiceoggetto

    INNER JOIN oggetti_metadati ON 
        oggetti_metadati.idcomune = oggetti.idcomune AND
        oggetti_metadati.codiceoggetto = oggetti.codiceoggetto and
        oggetti_metadati.chiave = 'FILE_SHA256_HASH'

    left OUTER JOIN commedilizie_all_firma ON
        commedilizie_all_firma.idcomune = commedilizie_allegati.idcomune AND 
        commedilizie_all_firma.fk_comm_allegati_id = commedilizie_allegati.id AND
        commedilizie_all_firma.fk_commedilizie_appello_id = {this._db.QueryParameter("idAppello")} 

WHERE 
    commedilizie_allegati.idcomune = {this._db.QueryParameter("idComune")} AND
    commedilizie_allegati.codicecommissione = {this._db.QueryParameter("idCommissione")} and 
    commedilizie_allegati.flag_pubblica = 1";

            return this._db.ExecuteReader(sql, 
                mp => mp.Add("idAppello", idAppello)
                        .Add("idComune", _idComune)
                        .Add("idCommissione", idCommissione),
                dr => new AllegatoCommissioneDao
                {
                    Id = dr.GetInt("id").Value,
                    Descrizione = dr.GetString("descrizione"),
                    CodiceOggetto = dr.GetInt("codiceoggetto").Value,
                    Nomefile = dr.GetString("nomefile"),
                    DataRegistrazione = dr.GetDateTime("dataregistrazione"),
                    Sha256 = dr.GetString("hash_sha_256"),
                    DataApprovazione = dr.GetDateTime("data_firma"),
                    Sha256Controllo = dr.GetString("hash_controllo")
                });
        }


        public int GetIdAppelloByCodiceAnagrafe(int idCommissione, int codiceAnagrafe)
        {
            if (_log.IsDebugEnabled) this._log.Debug($"Recupero l'id appello corrispondente a idCommissione={idCommissione} e codiceAnagrafe={codiceAnagrafe} cercando direttamente per codice anagrafe");

            int? idAppello = this.GetIdAppelloByCodiceAnagrafeInvitato(idCommissione, codiceAnagrafe);

            if (!idAppello.HasValue)
            {
                throw new Exception($"Non è stato possibile recuperare l'id appello per l'id commissione {idCommissione} e codice anagrafe {codiceAnagrafe}.");
            }

            this._log.Debug($"Id appello={idAppello.Value}");

            return idAppello.Value;
        }
        
        public int? GetIdAppelloByCodiceAnagrafeInvitato(int idCommissione, int codiceAnagrafe)
        {
            var sql = $@"SELECT id FROM commedilizie_appello 
                        WHERE 
                            idcomune={_db.QueryParameter("idcomune")} AND 
                            codicecommissione={_db.QueryParameter("idCommissione")} AND 
                            codiceanagrafe = {_db.QueryParameter("codiceAnagrafe")}";

            var id = this._db.ExecuteScalar(sql, -1, mp =>
            {
                mp.Add("idcomune", _idComune)
                  .Add("idCommissione", idCommissione)
                  .Add("codiceAnagrafe", codiceAnagrafe);
            });

            return id == -1 ? (int?)null : id;
        }

        private int NextCommedilizieAllFirmaId()
        {
            return new SequenceTableService(this._db, this._idComune).NextId(Constants.CommedilizieAllFirmaSequence);
        }

        public void ApprovaAllegato(int idAppello, int idAllegato, string sha256)
        {
            var sql = $@"INSERT INTO commedilizie_all_firma 
                            (idcomune, id, fk_commedilizie_appello_id,fk_comm_allegati_id, hash_sha_256, data_firma) 
                        VALUES (
                            {this._db.QueryParameter("idComune")}, 
                            {this._db.QueryParameter("id")}, 
                            {this._db.QueryParameter("idAppello")},
                            {this._db.QueryParameter("idAllegato")}, 
                            {this._db.QueryParameter("sha256")}, 
                            {this._db.QueryParameter("data")}
                        )";

            this._db.ExecuteNonQuery(sql,
                mp => mp.Add("idComune", this._idComune)
                        .Add("id", NextCommedilizieAllFirmaId())
                        .Add("idAppello", idAppello)
                        .Add("idAllegato", idAllegato)
                        .Add("sha256", sha256)
                        .Add("data", DateTime.Now));
        }
    }
}
