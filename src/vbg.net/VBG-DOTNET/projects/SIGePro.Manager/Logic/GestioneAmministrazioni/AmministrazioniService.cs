using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Security.Cryptography;
using System.Text;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager.Logic.GestioneAmministrazioni
{
    public class AmministrazioniService
    {
        private readonly AuthenticationInfo _authInfo;
        public AmministrazioniService(AuthenticationInfo authInfo)
        {
            this._authInfo = authInfo;
        }

        public IEnumerable<AmministrazioneBean> GetAmministrazioniCollegate(int codiceAmministrazione)
        {
            using (var db = this._authInfo.CreateDatabase() )
            {
                var sql = $@"select 
                            amministrazioni_collegate.codicecomune, amministrazioni.codiceamministrazione, amministrazioni.amministrazione
                        from 
                            amministrazioni_collegate 
                                inner join amministrazioni on 
                                    amministrazioni_collegate.idcomune = amministrazioni.idcomune and 
                                    amministrazioni_collegate.codicesottoamministrazione = amministrazioni.codiceamministrazione 
                        where
                            amministrazioni_collegate.idcomune={db.Specifics.QueryParameterName("idcomune")} and 
                            amministrazioni_collegate.codiceamministrazione = {db.Specifics.QueryParameterName("codiceamministrazione")}";

                return db.ExecuteReader(sql, mapParameters =>
                {
                    mapParameters.AddParameter("idcomune", this._authInfo.IdComune);
                    mapParameters.AddParameter("codiceamministrazione", codiceAmministrazione);
                }, dr => new AmministrazioneBean
                {
                    CodiceComune = dr.GetString("codicecomune"),
                    CodiceAmminisrtazione = dr.GetInt("codiceamministrazione").Value,
                    Amministrazione = dr.GetString("amministrazione")
                });
            }
        }
    }
}
