using Init.SIGePro.Manager.Authentication;
using System;

namespace Init.SIGePro.Manager.Logic.GestioneStradario
{
    public class IstanzaStradarioService
    {
        private readonly AuthenticationInfo _authInfo;

        public IstanzaStradarioService(AuthenticationInfo authInfo)
        {
            this._authInfo = authInfo;
        }

        //public IstanzaStradarioService(string tokenUtente, string idComune)
        //      {
        //          this._tokenUtente = tokenUtente;
        //          this._idComune = idComune;
        //      }

        public bool StradarioPresenteSuIstanza(int codiceIstanza, int codiceStradario)
        {
            return this.StradarioPresenteSuIstanza(codiceIstanza, codiceStradario, null);
        }

        public bool StradarioPresenteSuIstanza(int codiceIstanza, int codiceStradario, string km)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var sql = $@"select 
								count(*) 
							from 
								istanzestradario 
							where 
								idcomune={db.Specifics.QueryParameterName("idComune")} and 
								codiceistanza={db.Specifics.QueryParameterName("codiceIstanza")} and 
								codicestradario={db.Specifics.QueryParameterName("codiceStradario")}";
                if (!String.IsNullOrEmpty(km))
                {
                    sql += $" and km ={db.Specifics.QueryParameterName("km")}";
                }


                var cnt = db.ExecuteScalar(sql, 0, mp =>
                {
                    mp.AddParameter("idComune", this._authInfo.IdComune);
                    mp.AddParameter("codiceIstanza", codiceIstanza);
                    mp.AddParameter("codiceStradario", codiceStradario);
                    if (!String.IsNullOrEmpty(km))
                    {
                        mp.AddParameter("km", km);
                    }
                });

                if (cnt > 0)
                {
                    return true;
                }


                return false;
            }
        }
    }
}
