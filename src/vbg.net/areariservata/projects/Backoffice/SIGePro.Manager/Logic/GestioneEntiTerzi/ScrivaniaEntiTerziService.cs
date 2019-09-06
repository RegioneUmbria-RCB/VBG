using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestioneEntiTerzi
{
    public class ScrivaniaEntiTerziService
    {
        private readonly string _idComune;
        private readonly DataBase _database;
        private readonly ILog _log = LogManager.GetLogger(typeof(ScrivaniaEntiTerziService));


        public ScrivaniaEntiTerziService(DataBase database, string idComune)
        {
            _idComune = idComune;
            _database = database;
        }

        public bool PuoEffettuareMovimenti(int codiceAnagrafe)
        {
            var sql = $"SELECT inoltra_comunicazione FROM amministrazioni_anagrafe WHERE idcomune={_database.Specifics.QueryParameterName("idcomune")} AND fk_anagrafe={_database.Specifics.QueryParameterName("codiceAnagrafe")}";

            return this._database.ExecuteScalar(sql, 0, mp =>
            {
                mp.AddParameter("idcomune", this._idComune);
                mp.AddParameter("codiceAnagrafe", codiceAnagrafe);
            }) == 1;
        }

        public ETDatiAmministrazione GetDatiAmministrazione(int codiceAnagrafe)
        {
            var sql = $@"select
                            amministrazioni.codiceamministrazione,
                            amministrazioni.amministrazione,
                            amministrazioni.partitaiva
                        FROM
                          amministrazioni_anagrafe
                            INNER JOIN amministrazioni ON
                              amministrazioni.idcomune = amministrazioni_anagrafe.idcomune AND
                              amministrazioni.codiceamministrazione = amministrazioni_anagrafe.fk_amministrazione
                        WHERE
                          amministrazioni_anagrafe.idcomune = {_database.Specifics.QueryParameterName("idcomune")} AND
                          amministrazioni_anagrafe.fk_anagrafe = {_database.Specifics.QueryParameterName("codiceAnagrafe")}";

            return _database.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idcomune", _idComune);
                    mp.AddParameter("codiceAnagrafe", codiceAnagrafe);
                },
                x => new ETDatiAmministrazione
                {
                    Codice = x.GetInt("codiceamministrazione"),
                    Descrizione = x.GetString("amministrazione"),
                    PartitaIva = x.GetString("partitaiva")
                }).FirstOrDefault();
        }

        public IEnumerable<ETPraticaEnteTerzo> GetListaPratiche(ETFiltriPraticheEntiTerzi filtri)
        {
            var sql = $@"SELECT DISTINCT
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
                        vw_listapratiche.statopratica     
                    FROM                                  
                        amministrazioni_anagrafe 

                            INNER JOIN movimenti ON
                                movimenti.idcomune = amministrazioni_anagrafe.idcomune AND
                                movimenti.codiceamministrazione = amministrazioni_anagrafe.fk_amministrazione
  
                            INNER JOIN vw_listapratiche ON
                                vw_listapratiche.idcomune = movimenti.idcomune AND
                                vw_listapratiche.idpratica = movimenti.codiceistanza   
                            
                            inner join istanze on
                                istanze.idcomune = vw_listapratiche.idcomune and
                                istanze.codiceistanza = vw_listapratiche.idpratica

                            left join ISTANZE_ET_ELABORATE on
                                ISTANZE_ET_ELABORATE.idcomune = movimenti.idcomune and
                                ISTANZE_ET_ELABORATE.codiceistanza = movimenti.codiceistanza and
                                ISTANZE_ET_ELABORATE.codiceamministrazione = movimenti.codiceamministrazione

                    WHERE
                        amministrazioni_anagrafe.idcomune = {_database.Specifics.QueryParameterName("idcomune")} and
                        amministrazioni_anagrafe.fk_anagrafe = {_database.Specifics.QueryParameterName("codiceanagrafe")} AND
                    
                        istanze.data between {_database.Specifics.QueryParameterName("dallaData")} and {_database.Specifics.QueryParameterName("allaData")} and

                        movimenti.data IS NOT null";

            if (filtri.Elaborata.HasValue && filtri.Elaborata.Value)
            {
                sql += " and ISTANZE_ET_ELABORATE.flg_elaborata = 1";
            }

            if (filtri.Elaborata.HasValue && !filtri.Elaborata.Value)
            {
                sql += " and (ISTANZE_ET_ELABORATE.flg_elaborata = 0 or ISTANZE_ET_ELABORATE.flg_elaborata is null)";
            }

            if (!string.IsNullOrEmpty(filtri.NumeroProtocollo))
            {
                sql += $" and vw_listapratiche.numeroprotocollo = {_database.Specifics.QueryParameterName("numeroProtocollo")} ";
            }

            if (!string.IsNullOrEmpty(filtri.NumeroIstanza))
            {
                sql += $" and vw_listapratiche.numeropratica = {_database.Specifics.QueryParameterName("numeroIstanza")} ";
            }

            if (!string.IsNullOrEmpty(filtri.Modulo))
            {
                sql += $" and vw_listapratiche.software = {_database.Specifics.QueryParameterName("modulo")} ";
            }

            return _database.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", _idComune);
                    mp.AddParameter("codiceanagrafe", filtri.CodiceAnagrafe);
                    mp.AddParameter("dallaData", filtri.DallaData);
                    mp.AddParameter("allaData", filtri.AllaData);

                    if (!string.IsNullOrEmpty(filtri.NumeroProtocollo))
                    {
                        mp.AddParameter("numeroProtocollo", filtri.NumeroProtocollo);
                    }

                    if (!string.IsNullOrEmpty(filtri.NumeroIstanza))
                    {
                        mp.AddParameter("numeroIstanza", filtri.NumeroIstanza);
                    }

                    if (!string.IsNullOrEmpty(filtri.Modulo))
                    {
                        mp.AddParameter("modulo", filtri.Modulo);
                    }
                },
                x => new ETPraticaEnteTerzo
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
                    SoftwareDescrizione = x.GetString("descsoftware")
                });
        }

        public IEnumerable<ETSoftware> GetListaSoftwareConPratiche(int codiceAnagrafe)
        {
            var sql = $@"SELECT DISTINCT
                            software.codice,
                            software.descrizione
                        FROM                                  
                            amministrazioni_anagrafe 

                                INNER JOIN movimenti ON
                                    movimenti.idcomune = amministrazioni_anagrafe.idcomune AND
                                    movimenti.codiceamministrazione = amministrazioni_anagrafe.fk_amministrazione
  
                                inner join istanze on
                                    istanze.idcomune = movimenti.idcomune and
                                    istanze.codiceistanza = movimenti.codiceistanza

                                INNER JOIN software ON
                                    software.codice = istanze.software    
                        WHERE
                            amministrazioni_anagrafe.idcomune = {_database.Specifics.QueryParameterName("idcomune")} and
                            amministrazioni_anagrafe.fk_anagrafe = {_database.Specifics.QueryParameterName("codiceanagrafe")} AND
                            movimenti.data IS NOT null
                        ORDER by 
                            software.descrizione";

            return _database.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idComune", _idComune);
                    mp.AddParameter("codiceanagrafe", codiceAnagrafe);
                },
                x => new ETSoftware
                {
                    Codice = x.GetString("codice"),
                    Descrizione = x.GetString("descrizione")
                });
        }

        public bool PraticaElaborata(int codiceIstanza, int codiceAnagrafe)
        {
            var amministrazione = GetDatiAmministrazione(codiceAnagrafe);

            if (amministrazione == null)
            {
                throw new InvalidOperationException($"L'anagrafica {codiceAnagrafe} non è collegata ad una amministrazione");
            }

            var flgElaborata = _database.ExecuteScalar("select flg_elaborata from ISTANZE_ET_ELABORATE where idcomune={0} and codiceistanza={1} and codiceamministrazione={2}", 0, mp =>
            {
                mp.AddParameter("idcomune", _idComune);
                mp.AddParameter("codiceistanza", codiceIstanza);
                mp.AddParameter("codiceamministrazione", amministrazione.Codice.Value);
            });

            return flgElaborata > 0;
        }

        public void MarcaPraticaComeElaborata(int codiceIstanza, int codiceAnagrafe)
        {
            ImpostaStatoElaborazionePratica(codiceIstanza, codiceAnagrafe, true);

        }

        public void MarcaPraticaComeNonElaborata(int codiceIstanza, int codiceAnagrafe)
        {
            ImpostaStatoElaborazionePratica(codiceIstanza, codiceAnagrafe, false);
        }

        private void ImpostaStatoElaborazionePratica(int codiceIstanza, int codiceAnagrafe, bool elaborata)
        {
            var amministrazione = GetDatiAmministrazione(codiceAnagrafe);

            if (amministrazione == null)
            {
                throw new InvalidOperationException($"L'anagrafica {codiceAnagrafe} non è collegata ad una amministrazione");
            }

            var count = _database.ExecuteScalar("select count(*) from ISTANZE_ET_ELABORATE where idcomune={0} and codiceistanza={1} and codiceamministrazione={2}", 0, mp =>
            {
                mp.AddParameter("idcomune", _idComune);
                mp.AddParameter("codiceistanza", codiceIstanza);
                mp.AddParameter("codiceamministrazione", amministrazione.Codice.Value);
            });

            if (count == 0)
            {
                InsertPraticaElaborata(codiceIstanza, amministrazione.Codice.Value, elaborata);
            }
            else
            {
                UpdatePraticaElaborata(codiceIstanza, amministrazione.Codice.Value, elaborata);
            }
        }

        private void UpdatePraticaElaborata(int codiceIstanza, int codiceAmministrazione, bool elaborata)
        {
            this._database.ExecuteNonQuery("update ISTANZE_ET_ELABORATE set flg_elaborata=" + (elaborata?"1":"0") + " where idcomune={0} and codiceistanza={1} and codiceamministrazione={2}", mp =>
            {
                mp.AddParameter("idcomune", _idComune);
                mp.AddParameter("codiceistanza", codiceIstanza);
                mp.AddParameter("codiceamministrazione", codiceAmministrazione);
            });
        }

        private void InsertPraticaElaborata(int codiceIstanza, int codiceAmministrazione, bool elaborata)
        {
            this._database.ExecuteNonQuery("insert into ISTANZE_ET_ELABORATE (idcomune, codiceistanza, codiceamministrazione, flg_elaborata) values ({0},{1},{2}," + (elaborata ? "1" : "0") + ")", mp =>
            {
                mp.AddParameter("idcomune", _idComune);
                mp.AddParameter("codiceistanza", codiceIstanza);
                mp.AddParameter("codiceamministrazione", codiceAmministrazione);
            });
        }
    }
}
