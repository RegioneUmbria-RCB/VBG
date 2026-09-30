using Init.SIGePro.Manager.Authentication;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.GestioneStradario
{
    public class GestioneAreeService
    {
        private readonly AuthenticationInfo _authInfo;

        public GestioneAreeService(AuthenticationInfo authInfo)
        {
            this._authInfo = authInfo;
        }

        public IEnumerable<DettaglioArea> TrovaArea(TrovaAreaDaKmRequest request)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var sql = $@"select 
                                aree.codicearea, aree.denominazione 
                            from
                                areedettagli
                                    inner join aree on areedettagli.idcomune = aree.idcomune and areedettagli.codicearea = aree.codicearea and aree.codicetipoarea = {db.Specifics.QueryParameterName("codicetipoarea")} 
                            where 
                                areedettagli.idcomune = {db.Specifics.QueryParameterName("idcomune")} and 
                                areedettagli.codicestradario = {db.Specifics.QueryParameterName("codicestradario")} and 
                                areedettagli.km_da <= {db.Specifics.QueryParameterName("km_da")} and 
                                areedettagli.km_a >= { db.Specifics.QueryParameterName("km_a")}";

                return db.ExecuteReader(sql, mp =>
                {
                    mp.AddParameter("codicetipoarea", request.CodiceTipoArea);
                    mp.AddParameter("idcomune", this._authInfo.IdComune);
                    mp.AddParameter("codicestradario", request.CodiceStradario);
                    mp.AddParameter("km_da", request.Km);
                    mp.AddParameter("km_a", request.Km);
                }, dr => new DettaglioArea
                {
                    CodiceArea = dr.GetInt("codicearea").Value,
                    Denominazione = dr.GetString("denominazione")
                });

            }
        }

        public IEnumerable<DettaglioArea> TrovaArea(TrovaAreaDaKmAKmRequest request)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var sql = $@"select 
                                aree.codicearea, aree.denominazione 
                            from
                                areedettagli
                                    inner join aree on areedettagli.idcomune = aree.idcomune and areedettagli.codicearea = aree.codicearea and aree.codicetipoarea = {db.Specifics.QueryParameterName("codicetipoarea")} 
                            where 
                                areedettagli.idcomune = {db.Specifics.QueryParameterName("idcomune")} and 
                                areedettagli.codicestradario = {db.Specifics.QueryParameterName("codicestradario")} and 
                                areedettagli.km_da <= {db.Specifics.QueryParameterName("kmFInale")} and 
                                areedettagli.km_a >= { db.Specifics.QueryParameterName("kmIniziale")}";

                return db.ExecuteReader(sql, mp =>
                {
                    mp.AddParameter("codicetipoarea", request.CodiceTipoArea);
                    mp.AddParameter("idcomune", this._authInfo.IdComune);
                    mp.AddParameter("codicestradario", request.CodiceStradario);
                    mp.AddParameter("kmFInale", request.KmA);
                    mp.AddParameter("kmIniziale", request.KmDa);
                }, dr => new DettaglioArea
                {
                    CodiceArea = dr.GetInt("codicearea").Value,
                    Denominazione = dr.GetString("denominazione")
                });

            }
        }
    }
}
