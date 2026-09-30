using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.Interventi;
using Init.SIGePro.Manager.Logic.GestioneSequenceTable;
using PersonalLib2.Data;
using SIGePro.Data.Data.Rating;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Manager
{
    public class RatingMgr : BaseManager
    {
        public RatingMgr(DataBase db) : base(db) { }

        // ================================
        // Rating Main
        // ================================
        public IEnumerable<RatingMain> GetMainList(string idComune)
        {
            var sql = $@"
                    SELECT idComune, id, tipo, testo
                    FROM fo_rating_main
                    WHERE idComune = {this.db.QueryParameter("idComune")}
                      ORDER BY tipo ASC";

            return db.ExecuteReader(
                sql,
                mp => mp.Add("idComune", idComune),
                x => new RatingMain
                {
                    IdComune = x.GetString("idComune"),
                    Id = x.GetInt("id"),
                    Tipo = x.GetString("tipo"),
                    Testo = x.GetString("testo")
                });
        }

        public RatingMain GetMainListByType(string idComune, string tipo)
        {
            var sql = $@"
                    SELECT idComune, id, tipo, testo
                    FROM fo_rating_main
                    WHERE idComune = {this.db.QueryParameter("idComune")}
                      AND tipo = {this.db.QueryParameter("tipo")}";

            return db.ExecuteReader(
                sql,
                mp => mp.Add("idComune", idComune)
                        .Add("tipo", tipo),
                x => new RatingMain
                {
                    IdComune = x.GetString("idComune"),
                    Id = x.GetInt("id"),
                    Tipo = x.GetString("tipo"),
                    Testo = x.GetString("testo")
                }).FirstOrDefault();
        }

        // ================================
        // Rating Sub Questions
        // ================================
        public IEnumerable<RatingSubSection> GetSubSectionsByMainId(string idComune, int idRatingMain)
        {
            var sql = $@"
                    SELECT idComune, id, idRatingMain, isPositive, testo
                    FROM fo_rating_sub_question
                    WHERE idComune = {this.db.QueryParameter("idComune")}
                      AND idRatingMain = {this.db.QueryParameter("idRatingMain")}
                    ORDER BY id ASC";

            return this.db.ExecuteReader(sql,
                mp => mp.Add("idComune", idComune)
                        .Add("idRatingMain", idRatingMain),
                x => new RatingSubSection
                {
                    IdComune = idComune,
                    Id = x.GetInt("id").Value,
                    IdRatingMain = idRatingMain,
                    IsPositive = x.GetInt("isPositive").GetValueOrDefault(0),
                    Testo = x.GetString("testo")
                });
        }

        // ================================
        // Rating Choices
        // ================================
        public IEnumerable<RatingAnswer> GetChoicesBySubSectionId(string idComune, int subSectionId)
        {
            var sql = $@"
                    SELECT idComune, id, idRatingSubQuestion, testo, ordine
                    FROM fo_rating_choice
                    WHERE idComune = {this.db.QueryParameter("idComune")}
                      AND idRatingSubQuestion = {this.db.QueryParameter("idRatingSubQuestion")}
                    ORDER BY ordine ASC";

            return this.db.ExecuteReader(
                sql,
                mp => mp.Add("idComune", idComune)
                        .Add("idRatingSubQuestion", subSectionId),
                dr => new RatingAnswer
                {
                    IdComune = idComune,
                    Id = dr.GetInt("id"),
                    IdRatingSubQuestion = subSectionId,
                    Testo = dr.GetString("testo"),
                    Ordine = dr.GetInt("ordine").GetValueOrDefault(0)
                });
        }

        // ================================
        // Rating Results - INSERT
        // ================================
        public void SaveRatingResult(RatingResult result)
        {

            var newId = new SequenceTableService(this.db, result.IdComune).NextId("FO_RATING_RESULT.ID");

            var sql = $@"
            INSERT INTO fo_rating_result
            (idComune, id, identifier, stars, fkIdChoice, ratingComment)
            VALUES
                ({this.db.QueryParameter("idComune")},
                 {this.db.QueryParameter("id")},
                 {this.db.QueryParameter("identifier")},
                 {this.db.QueryParameter("stars")},
                 {this.db.QueryParameter("fkIdChoice")},
                 {this.db.QueryParameter("ratingComment")})
            ";

            this.db.ExecuteNonQuery(sql, mp =>
            {
                mp.Add("idComune", result.IdComune);
                mp.Add("id", newId);
                mp.Add("identifier", result.Identifier);
                mp.Add("stars", result.Stars);
                mp.Add("fkIdChoice", result.FkIdChoice);
                mp.Add("ratingComment", string.IsNullOrWhiteSpace(result.RatingComment) ? (object)DBNull.Value : result.RatingComment);
            });
        }

    }
}
