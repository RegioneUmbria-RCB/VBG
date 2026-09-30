using Init.SIGePro.Manager.Authentication;
using System.Data;
using VBG.DatiDinamici.Interfaces.WebControls;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess
{
    public class QueryDatiDinamiciManager : IDyn2QueryDatiDinamiciManager
    {
        private readonly IAuthenticationInfoResolver _authenticationInfoResolver;

        public QueryDatiDinamiciManager(IAuthenticationInfoResolver authenticationInfoResolver)
        {
            this._authenticationInfoResolver = authenticationInfoResolver;
        }

        public DataSet EseguiQuery(string idComune, string campiSelect, string tabelleSelect, string condizioneJoin, string condizioniWhere, string nomeCampoTesto, string nomeCampoValore)
        {
            var authInfo = this._authenticationInfoResolver.Resolve();
            using (var database = authInfo.CreateDatabase())
            {
                var query = new QuerySigepro(database);

                return query.EseguiQuery(idComune, campiSelect, tabelleSelect, condizioneJoin, condizioniWhere, nomeCampoTesto, nomeCampoValore);
            }
        }
    }
}
