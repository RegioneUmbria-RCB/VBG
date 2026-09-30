using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.AllegatiDomanda
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsCommissioni" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsCommissioni.svc or WsCommissioni.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsAllegatiDomanda : WcfServiceBase, IWsAllegatiDomanda
    {
        public WsAllegatiDomanda(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
        }

        private static class Constants
        {
            public const string ChiaveMD5 = "MD5_SUM";
            public const string ChiaveSHA1 = "FILE_SHA1_HASH";
        }

        /// <summary>
        /// Salva un allegato di una domanda del frontoffice
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idDomanda"></param>
        /// <param name="file"></param>
        /// <returns></returns>
        public int SalvaAllegatoDomanda(string token, int idDomanda, int codiceOggetto)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new FoDomandeOggettiMgr(db);

                return mgr.SalvaAllegatoDomanda(authInfo.IdComune, idDomanda, codiceOggetto);
            }
        }

        /// <summary>
        /// Elimina un allegato di una domanda del frontoffice
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idDomanda"></param>
        /// <param name="codiceOggetto"></param>
        public void EliminaAllegatoDomanda(string token, int idDomanda, int codiceOggetto)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new FoDomandeOggettiMgr(db);

                mgr.EliminaAllegatoDomanda(authInfo.IdComune, idDomanda, codiceOggetto);
            }
        }

        public bool OggettoAppartieneADomanda(string token, int idDomanda, int codiceOggetto)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var oggDom = new FoDomandeOggettiMgr(db).GetById(authInfo.IdComune, idDomanda, codiceOggetto);

                return oggDom != null;
            }
        }

        public string GetChecksumOggetto(string token, int codiceOggetto)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var mm = new OggettiMetadatiMgr(db);
                var metadato = mm.GetById(authInfo.IdComune, codiceOggetto, Constants.ChiaveMD5);

                if (metadato != null)
                {
                    return $"MD5: {metadato.Valore}";
                }

                metadato = mm.GetById(authInfo.IdComune, codiceOggetto, Constants.ChiaveSHA1);

                return metadato == null ? "" : $"SHA1: {metadato.Valore}";
            }

        }
    }
}