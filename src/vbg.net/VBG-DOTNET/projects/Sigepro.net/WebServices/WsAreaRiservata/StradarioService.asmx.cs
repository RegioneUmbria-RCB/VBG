using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.StradarioComune;
using Init.SIGePro.Manager.Logic.GestioneStradario;
using Init.SIGePro.Manager.Logic.GestioneStradario.RicercaStradario;
using PersonalLib2.Data;
using Sigepro.net.WebServices.WsSIGePro;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
    /// <summary>
    /// Summary description for StradarioService
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class StradarioService : SigeproWebService
    {

        [WebMethod]
        [Obsolete]
        public Stradario GetByCodiceStradario(string token, int codiceStradario)
        {
            var authResult = this.CheckToken(token);

            if (authResult == null)
                throw new ArgumentException("Token non valido: " + token);

            using (var db = authResult.CreateDatabase())
                return new StradarioMgr(db).GetById(authResult.IdComune, codiceStradario);
        }

        [WebMethod]
        [Obsolete]

        public Stradario GetByIndirizzo(string token, string codiceComune, string indirizzo)
        {
            var authResult = this.CheckToken(token);

            if (authResult == null)
                throw new ArgumentException("Token non valido: " + token);

            using (var db = authResult.CreateDatabase())
            {
                var matches = new StradarioMgr(db).GetByIndirizzo(authResult.IdComune, codiceComune, indirizzo);

                if (matches.Count != 1)
                    return null;

                return matches[0];
            }
        }

        [WebMethod]
        [Obsolete]

        public List<StradarioDto> GetByMatchParziale(string token, string codiceComune, string comuneLocalizzazione, string indirizzo)
        {
            var authResult = this.CheckToken(token);

            if (authResult == null)
                throw new ArgumentException("Token non valido: " + token);

            using (var db = authResult.CreateDatabase())
            {
                var repository = new StradarioRepository(db, authResult.IdComune);

                return new GestioneStradarioService(repository).FindByMatchParziale(codiceComune, comuneLocalizzazione, indirizzo, true).ToList();
            }
        }
    }
}
