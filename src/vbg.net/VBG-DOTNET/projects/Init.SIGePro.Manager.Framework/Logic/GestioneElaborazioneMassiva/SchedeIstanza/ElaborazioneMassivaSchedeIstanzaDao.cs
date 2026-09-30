using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneElaborazioneMassiva.SchedeIstanza
{
    public class ElaborazioneMassivaSchedeIstanzaDao : IElaborazioneMassivaSchedeIstanzaDao
    {

        private readonly ILog _log = LogManager.GetLogger(typeof(ElaborazioneMassivaSchedeIstanzaDao));
        private readonly DataBase _db;
        private readonly string _idComune;

        public ElaborazioneMassivaSchedeIstanzaDao(DataBase db, string idComune)
        {
            if (string.IsNullOrEmpty(idComune))
            {
                throw new ArgumentException($"'{nameof(idComune)}' cannot be null or empty.", nameof(idComune));
            }
            _db = db ?? throw new ArgumentNullException(nameof(db));
            _idComune = idComune;
        }

        public void AvviaElaborazione(int idElaborazione)
        {
            this._log.Debug($"Avvio dell'elaborazione con id {idElaborazione} e idcomune {this._idComune}");

            try
            {
                var sql = $"update dyn2_massive set data_inizio={_db.QueryParameter("dataInizio")} " +
                          $"where idcomune={_db.QueryParameter("idComune")} and id={_db.QueryParameter("id")}";

                this._db.ExecuteNonQuery(sql,
                    mp => mp.Add("dataInizio", DateTime.Now)
                            .Add("idComune", this._idComune)
                            .Add("id", idElaborazione));
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nell'avvio dell'elaborazione con id {idElaborazione} e idcomune {this._idComune}: {ex}");

                throw;
            }
            finally
            {
                this._log.Debug($"Avvio dell'elaborazione con id {idElaborazione} e idcomune {this._idComune} terminato");
            }
        }

        public IEnumerable<PraticaDaElaborare> GetPraticheDaElaborare(int idElaborazione)
        {
            this._log.Debug($"Lettura delle pratiche da elaborare per id {idElaborazione} e idcomune {this._idComune}");

            try
            {
                var sql = $@"SELECT 
                            codiceistanza 
                        FROM 
                            dyn2_massiveistanze
                        WHERE 
                            idcomune = {_db.QueryParameter("idcomune")} AND 
                            fkidelaborazione = {_db.QueryParameter("fkidelaborazione")} AND
                            statoesecuzione = {_db.QueryParameter("statoesecuzione")}";

                var pratiche = this._db.ExecuteReader(sql,
                    mp => mp.Add("idcomune", this._idComune)
                            .Add("fkidelaborazione", idElaborazione)
                            .Add("statoesecuzione", EsitoElaborazioneMassivaSchedeEnum.ProntaPerElaborazione.ToString()),
                    dr => new PraticaDaElaborare(idElaborazione, dr.GetInt("codiceistanza").Value)
                );

                this._log.Debug($"{pratiche.Count()} pratiche trovate");

                return pratiche;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nella lettura delle pratiche da elaborare con id {idElaborazione} e idcomune {this._idComune}: {ex}");

                throw;
            }
            finally
            {
                this._log.Debug($"Lettura delle pratiche da elaborare con id {idElaborazione} e idcomune {this._idComune} terminata");
            }
        }

        public IEnumerable<SchedaDaElaborare> GetSchedeDaElaborare(int idElaborazione)
        {
            this._log.Debug($"Lettura delle schede da elaborare per id {idElaborazione} e idcomune {this._idComune}");

            try
            {
                var sql = $@"SELECT 
                            fk_d2mt_id
                        FROM
                            dyn2_massiveschede
                        WHERE
                            idcomune = {_db.QueryParameter("idComune")} AND
                            fkidelaborazione = {_db.QueryParameter("fkidelaborazione")}
                        ORDER BY
                            ordine asc";

                var schede = this._db.ExecuteReader(sql,
                    mp => mp.Add("idComune", this._idComune)
                            .Add("fkidelaborazione", idElaborazione),
                    dr => new SchedaDaElaborare(dr.GetInt("fk_d2mt_id").Value)
                );

                this._log.Debug($"{schede.Count()} schede trovate");

                return schede;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nella lettura delle schede da elaborare con id {idElaborazione} e idcomune {this._idComune}: {ex}");

                throw;
            }
            finally
            {
                this._log.Debug($"Lettura delle schede da elaborare con id {idElaborazione} e idcomune {this._idComune} terminata");
            }
        }

        public bool IsElaborazioneInCorso(int idElaborazione)
        {
            this._log.Debug($"Verifico se l'elaborazione con id {idElaborazione} e idcomune {this._idComune} è in corso");

            try
            {
                var sql = $"SELECT Count(*) FROM dyn2_massive " +
                          $"WHERE idcomune={_db.QueryParameter("idComune")} AND id={_db.QueryParameter("id")} " +
                          $"AND data_inizio IS NOT NULL AND data_fine IS null";

                var result = this._db.ExecuteScalar(sql, 0,
                    mp => mp.Add("idComune", this._idComune)
                            .Add("id", idElaborazione)
                ) != 0;

                this._log.Debug($"L'elaborazione {(result ? "è" : " non è ")} in corso");

                return result;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nella verifica dello stato dell'elaborazione con id {idElaborazione} e idcomune {this._idComune}: {ex}");

                throw;
            }
            finally
            {
                this._log.Debug($"Verifica dello stato dell'elaborazione con id {idElaborazione} e idcomune {this._idComune} terminata");
            }
        }

        public bool PraticaContieneScheda(int codiceIstanza, int idSchedaDinamica)
        {
            this._log.Debug($"Verifico se la pratica {codiceIstanza} e idcomune {this._idComune} contiene la scheda {idSchedaDinamica}");

            try
            {
                var sql = $"SELECT Count(*) FROM istanzedyn2modellit " +
                      $"WHERE idcomune={_db.QueryParameter("idComune")} " +
                      $"AND codiceistanza={_db.QueryParameter("codiceIstanza")} " +
                      $"AND fk_d2mt_id={_db.QueryParameter("idModello")}";

                var result = this._db.ExecuteScalar(sql, 0,
                    mp => mp.Add("idComune", _idComune)
                            .Add("codiceIstanza", codiceIstanza)
                            .Add("idModello", idSchedaDinamica)
                ) > 0;

                this._log.Debug($"La pratica {(result? "contiene" : "non contiene")} la scheda");

                return result;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nella verifica della scheda {idSchedaDinamica} per la pratica {codiceIstanza} e idcomune {this._idComune}: {ex}");

                throw;
            }
        }

        public void TerminaElaborazione(int idElaborazione)
        {
            this._log.Debug($"Termino l'elaborazione {idElaborazione} e idcomune {this._idComune}");

            try
            {
                var sql = $"UPDATE dyn2_massive SET data_fine = {_db.QueryParameter("dataFine")} " +
                              $"WHERE idcomune={_db.QueryParameter("idComune")} AND id = {_db.QueryParameter("idElaborazione")}";

                this._db.ExecuteNonQuery(sql,
                    mp => mp.Add("dataFine", DateTime.Now)
                            .Add("idComune", this._idComune)
                            .Add("idElaborazione", idElaborazione));

                this._log.Debug($"Elaborazione {idElaborazione} e idcomune {this._idComune} terminata");
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore terminando l'elaborazione {idElaborazione} e idcomune {this._idComune}: {ex}");

                throw;
            }
}


        public void ImpostaErroriElaborazione(int idElaborazione, int codiceIstanza, IEnumerable<string> messaggi)
        {
            var messaggioErrore = String.Join(Environment.NewLine, messaggi);

            if (messaggioErrore.Length > 4000)
            {
                messaggioErrore = messaggioErrore.Substring(0, 4000);
            }

            this.AggiornaStatoElaborazione(idElaborazione, codiceIstanza, EsitoElaborazioneMassivaSchedeEnum.ElaborazioneCompletataConErrori.ToString(), messaggioErrore);
        }

        public void AvviaElaborazioneIstanza(int idElaborazione, int codiceIstanza)
        {
            this.AggiornaStatoElaborazione(idElaborazione, codiceIstanza, EsitoElaborazioneMassivaSchedeEnum.ElaborazioneInCorso.ToString());
        }

        public void TerminaElaborazioneIstanza(int idElaborazione, int codiceIstanza)
        {
            this.AggiornaStatoElaborazione(idElaborazione, codiceIstanza, EsitoElaborazioneMassivaSchedeEnum.ElaborazioneCompletataConSuccesso.ToString());
        }

        private void AggiornaStatoElaborazione(int idElaborazione, int codiceIstanza, string nuovoStato, string log = null)
        {

            this._log.Debug($"Aggiornamento dello stato di elaborazione per la pratica {codiceIstanza} dell'elaborazione {idElaborazione} e idcomune {this._idComune}." +
                            $"Nuovo stato: {nuovoStato}, log: {log}");

            try
            {
                var sql = $@"UPDATE 
                            dyn2_massiveistanze
                        SET 
                            statoesecuzione = {_db.QueryParameter("stato")},
                            log = {_db.QueryParameter("log")}
                        WHERE
                            idcomune = {_db.QueryParameter("idComune")} AND
                            fkidelaborazione = {_db.QueryParameter("idElaborazione")} AND
                            codiceistanza = {_db.QueryParameter("codiceIstanza")}";

                this._db.ExecuteNonQuery(sql,
                    mp => mp.Add("stato", nuovoStato)
                            .Add("log", log)
                            .Add("idComune", this._idComune)
                            .Add("idElaborazione", idElaborazione)
                            .Add("codiceIstanza", codiceIstanza));

                this._log.Debug($"Nuovo stato impostato con successo");
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante l'aggiornamento dello stato della pratica {codiceIstanza} " +
                                $"nell'elaborazione {idElaborazione} e idcomune {this._idComune}: {ex}");

                throw;
            }
        }
    }
}
