using Init.SIGePro.Manager.Logic.GestioneSequenceTable;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneQuestionario
{
    public class QuestionarioGradimentoService
    {
        private static class Constants
        {
            public const string SequenceName = "FO_QUESTIONARIO_GRADIMENTO.ID";
        }

        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly ILog _log = LogManager.GetLogger(typeof(QuestionarioGradimentoService));

        public QuestionarioGradimentoService(DataBase db, string idComune)
        {
            if (string.IsNullOrEmpty(idComune))
            {
                throw new ArgumentException($"'{nameof(idComune)}' cannot be null or empty.", nameof(idComune));
            }

            _db = db ?? throw new ArgumentNullException(nameof(db));
            _idComune = idComune;
        }

        public bool QuestionarioCompilato(int codiceIstanza)
        {
            var sql = $@"select count(*) from FO_QUESTIONARIO_GRADIMENTO where idcomune={this._db.QueryParameter("idComune")} and fk_codiceistanza={_db.QueryParameter("codiceIstanza")}";

            return this._db.ExecuteScalar(sql, 0,
                mp => mp.Add("idComune", this._idComune)
                        .Add("codiceIstanza", codiceIstanza)) > 0;
        }

        public void SalvaQuestionario(int codiceIstanza, int valutazione, string note)
        {
            try
            {
                if (valutazione < 0 || valutazione > 5)
                {
                    throw new ArgumentException("Il valore impostato per la valutazione non è valido");
                }

                if (note == null)
                {
                    note = "";
                }

                if (note.Length > 4000)
                {
                    note = note.Substring(4000);
                }

                if (QuestionarioCompilato(codiceIstanza))
                {
                    throw new InvalidOperationException("E'già stato compilato un questionario per l'istanza in oggetto");
                }

                var sql = $@"INSERT INTO fo_questionario_gradimento (idcomune, id, fk_codiceistanza, valutazione, note, data) VALUES (
                        {_db.QueryParameter("idcomune")},
                        {_db.QueryParameter("id")}, 
                        {_db.QueryParameter("fk_codiceistanza")}, 
                        {_db.QueryParameter("valutazione")}, 
                        {_db.QueryParameter("note")}, 
                        {_db.QueryParameter("data")}
                        )";

                this._db.ExecuteNonQuery(sql,
                    mp => mp.Add("idcomune", this._idComune)
                            .Add("id", this.NextId())
                            .Add("fk_codiceistanza", codiceIstanza)
                            .Add("valutazione", valutazione)
                            .Add("note", note)
                            .Add("data", DateTime.Now));
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante l'inserimento della valutazione per codiceIstanza={codiceIstanza}, valutazione={valutazione}, note={note}: {ex}");

                throw;
            }
        }

        private int NextId() => new SequenceTableService(this._db, this._idComune).NextId(Constants.SequenceName);
    }
}
