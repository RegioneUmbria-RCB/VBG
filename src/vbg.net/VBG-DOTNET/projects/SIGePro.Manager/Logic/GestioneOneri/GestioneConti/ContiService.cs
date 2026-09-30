using Init.SIGePro.Manager.DTO.Oneri;
using log4net;
using PersonalLib2.Data;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestioneOneri.GestioneConti
{
    public class ContiService : IContiService
    {
        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly ILog _log = LogManager.GetLogger(typeof(ContiService));

        public ContiService(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public ContoDto GetContoDaIdCausaleOnere(int idCausaleOnere)
        {
            var sql = $@"SELECT
                              conti.mappaturanodopag,
                              conti.* 

                            FROM 
                              tipicausalioneridettaglio
                                
                                INNER JOIN TIPICAUSALIONERI ON
                                  TIPICAUSALIONERI.idcomune = tipicausalioneridettaglio.idcomune AND
                                  TIPICAUSALIONERI.co_id = tipicausalioneridettaglio.fkcausale
                                   
                                INNER JOIN conti ON
                                   conti.idcomune = tipicausalioneridettaglio.idcomune AND
                                   conti.id = tipicausalioneridettaglio.fkconto

                            WHERE
                              tipicausalioneridettaglio.idcomune = {this._db.Specifics.QueryParameterName("idcomune")} AND 
                              tipicausalioneridettaglio.fkcausale = {this._db.Specifics.QueryParameterName("idCausaleOnere")} AND 
                              tipicausalioneridettaglio.flag_attivo = 1";

            var results = this._db.ExecuteReader(sql,
                mp =>
                {
                    mp.AddParameter("idcomune", this._idComune);
                    mp.AddParameter("idCausaleOnere", idCausaleOnere);
                },
                dr => new ContoDto
                {
                    CodiceSottoConto = dr.GetString("codicesottoconto"),
                    Conto = dr.GetString("descrizione"),
                    Id = dr.GetInt("id").Value,
                    Iva = dr.GetInt("iva"),
                    NumeroAccertamento = dr.GetString("numero_accertamento"),
                    CodiceMappaturaNodoPagamenti = dr.GetString("mappaturanodopag"),
                });

            if (!results.Any())
            {
                this._log.Error($"Impossibile trovare un conto associato alla causale onere {idCausaleOnere} per l'id comune {this._idComune}");

                return null;
            }

            return results.FirstOrDefault();
        }
    }
}
