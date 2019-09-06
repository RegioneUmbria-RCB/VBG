using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneAccessoAtti.Siena
{
    public class SienaAccessoAttiService
    {
        private readonly DataBase _database;
        private readonly string _idComune;

        public SienaAccessoAttiService(DataBase database, string idComune)
        {
            _database = database;
            _idComune = idComune;
        }

        public IEnumerable<PraticaAccessoAtti> GetListaAtti(int codiceAnagrafe, string software)
        {
            var sql = $@"SELECT 
                          vw_listapratiche.uuid,
                          vw_listapratiche.idpratica,
                          vw_listapratiche.numeropratica,
                          vw_listapratiche.software,
                          vw_listapratiche.descsoftware,
                          vw_listapratiche.numeropratica,
                          vw_listapratiche.datapresentazione,
                          vw_listapratiche.numeroprotocollo,
                          vw_listapratiche.dataprotocollo,
                          vw_listapratiche.pr_indirizzo,
                          vw_listapratiche.pr_civico,
                          vw_listapratiche.nominativo,
                          vw_listapratiche.codicefiscale,
                          vw_listapratiche.descrizioneintervento,                    
                          vw_listapratiche.oggetto,
                          vw_listapratiche.statopratica,
                          istanze_accesso_atti_d.flg_visualizza_doc,
                          ISTANZE_ACCESSO_ATTI_T.id IdAccessoAtti,
                          istanze.numeroistanza codice_istanza_accesso_atti,
                          istanze.data data_istanza_accesso_atti,
                          ISTANZE_ACCESSO_ATTI_T.descrizione_fascicolo descrizione
                        FROM 
                          ISTANZE_ACCESSO_ATTI_T 
                            INNER JOIN istanze ON 
                              istanze.idcomune = ISTANZE_ACCESSO_ATTI_T.idcomune AND
                              istanze.codiceistanza = ISTANZE_ACCESSO_ATTI_T.codiceistanza

                            INNER JOIN ISTANZE_ACCESSO_ATTI_ANAGRAFE ON
                              ISTANZE_ACCESSO_ATTI_ANAGRAFE.idcomune = ISTANZE_ACCESSO_ATTI_T.idcomune AND
                              ISTANZE_ACCESSO_ATTI_ANAGRAFE.fk_idaccesso_atti = ISTANZE_ACCESSO_ATTI_T.id

                              INNER JOIN istanze_accesso_atti_d ON
                                istanze_accesso_atti_d.idcomune = ISTANZE_ACCESSO_ATTI_T.idcomune AND 
                                istanze_accesso_atti_d.fk_idaccesso_atti = ISTANZE_ACCESSO_ATTI_T.id

                              INNER JOIN vw_listapratiche ON
                                vw_listapratiche.idcomune = istanze_accesso_atti_d.idcomune AND 
                                vw_listapratiche.idpratica = istanze_accesso_atti_d.codiceistanza
                        WHERE
                          ISTANZE_ACCESSO_ATTI_T.idcomune = {_database.Specifics.QueryParameterName("idComune")} AND
                          ISTANZE_ACCESSO_ATTI_T.flg_pubblica = 1  AND
                          ISTANZE_ACCESSO_ATTI_ANAGRAFE.codiceanagrafe = {_database.Specifics.QueryParameterName("codiceanagrafe")} and
                          {(this._database.Specifics.DBMSName() == Provider.ORACLE ? "SYSDATE" : "SYSDATE()")} BETWEEN ISTANZE_ACCESSO_ATTI_T.DATAINIZIO AND ISTANZE_ACCESSO_ATTI_T.DATAFINE";

            return _database.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", _idComune);
                    mp.AddParameter("codiceanagrafe", codiceAnagrafe);
                    //mp.AddParameter("dataAttuale", DateTime.Now);
                },
                x => new PraticaAccessoAtti
                {
                    CodiceIstanza = x.GetInt("idpratica").Value,
                    NumeroIstanza = x.GetString("numeropratica"),
                    DataPresentazione = x.GetDateTime("datapresentazione").Value,
                    DataProtocollo = x.GetDateTime("dataprotocollo"),
                    Localizzazione = (x.GetString("pr_indirizzo") ?? "" + x.GetString("pr_civico") ?? "").Trim(),
                    NumeroProtocollo = x.GetString("numeroprotocollo"),
                    Oggetto = x.GetString("oggetto"),
                    Richiedente = x.GetString("nominativo"),
                    StatoLavorazione = x.GetString("statopratica"),
                    TipoIntervento = x.GetString("descrizioneintervento"),
                    UUID = x.GetString("UUID"),
                    SoftwareCodice = x.GetString("software"),
                    SoftwareDescrizione = x.GetString("descsoftware"),
                    MostraDocumentiNonValidi = x.GetInt("flg_visualizza_doc").GetValueOrDefault(0) == 1,
                    IdAccessoAtti = x.GetInt("IdAccessoAtti").Value,
                    CodiceIstanzaAccessoAtti = x.GetString("codice_istanza_accesso_atti"),
                    DataIstanzaAccessoAtti = x.GetDateTime("data_istanza_accesso_atti"),
                    DescrizioneAccessoAtti = x.GetString("descrizione")
                });
        }

        public void LogAccessoPratica(int idAccessoAtti, int codiceAnagrafe, int codiceIstanza)
        {
            var sql = $"INSERT INTO istanze_accesso_atti_log (idcomune, uuid, fk_idaccesso_atti, dataora, codiceanagrafe, codiceistanza) VALUES " +
                      $"({_database.Specifics.QueryParameterName("idcomune")}, " +
                      $"{_database.Specifics.QueryParameterName("uuid")}, " +
                      $"{_database.Specifics.QueryParameterName("fk_idaccesso_atti")}, " +
                      // $"{_database.Specifics.QueryParameterName("dataora")}, " +
                      $"{ (this._database.Specifics.DBMSName() == Provider.ORACLE ? "SYSDATE" : "SYSDATE()")}, " +
                      $"{_database.Specifics.QueryParameterName("codiceanagrafe")}," +
                      $"{_database.Specifics.QueryParameterName("codiceistanza")})";

            this._database.ExecuteNonQuery(sql, x =>
            {
                x.AddParameter("idcomune", this._idComune);
                x.AddParameter("uuid", Guid.NewGuid().ToString());
                x.AddParameter("fk_idaccesso_atti", idAccessoAtti);
                // x.AddParameter("dataora", DateTime.Now);
                x.AddParameter("codiceanagrafe", codiceAnagrafe);
                x.AddParameter("codiceistanza", codiceIstanza);
            });
        }
    }
}
