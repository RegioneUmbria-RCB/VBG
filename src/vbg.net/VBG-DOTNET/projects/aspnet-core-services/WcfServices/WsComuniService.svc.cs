using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Comuni;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "ComuniService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select ComuniService.svc or ComuniService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsComuniService : WcfServiceBase, IWsComuniService
    {
        public WsComuniService(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
        }

        public List<DatiComuneCompatto> FindComuniDaMatchParziale(string token, string matchComune)
        {
            var authResult = this.CheckToken(token);

            if (authResult == null)
                throw new ArgumentException("Token non valido: " + token);

            using (var db = authResult.CreateDatabase())
                return new ComuniMgr(db).FindComuniDaMatchParziale(matchComune);
        }


        public List<DatiComuneCompatto> GetListaComuni(string token, string siglaProvincia)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
                return new ComuniMgr(db).GetListaComuni(siglaProvincia);
        }


        public DatiComuneCompatto GetDatiComune(string token, string codiceComune)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
                return new ComuniMgr(db).GetDaticomune(codiceComune);
        }


        public DatiProvinciaCompatto GetDatiProvincia(string token, string siglaProvincia)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
                return new ComuniMgr(db).GetDatiProvincia(siglaProvincia);
        }


        public DatiProvinciaCompatto GetDatiProvinciaDaCodiceComune(string token, string codiceComune)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
                return new ComuniMgr(db).GetProvinciaDaCodiceComune(codiceComune);
        }


        public List<DatiProvinciaCompatto> GetListaProvincie(string token)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
                return new ComuniMgr(db).GetListaProvincie();
        }


        public List<DatiComuneCompatto> GetComuniAssociati(string token, string software)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
                return new ComuniMgr(db).GetComuniAssociatiFrontoffice(authResult.IdComune, software).ToList();
        }


        public string GetPecComuneAssociato(string token, string codiceComuneAssociato, string software)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
                return new ComuniAssociatiSoftwareMgr(db).GetIndirizzoPecComuneAssociato(authResult.IdComune, codiceComuneAssociato, software);
        }

        public DatiComuneCompatto GetComuneDaCodiceIstat(string token, string codiceIstat)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var comune = new ComuniMgr(db).GetByCodiceIstat(codiceIstat);

                if (comune == null)
                    return null;

                return new DatiComuneCompatto
                {
                    Cf = comune.CF,
                    CodiceComune = comune.CODICECOMUNE,
                    Comune = comune.COMUNE,
                    Provincia = comune.PROVINCIA,
                    SiglaProvincia = comune.SIGLAPROVINCIA
                };
            }
        }



        public List<CittadinanzaCompatto> GetCittadinanze(string token)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
            {
                var filtro = new Cittadinanza
                {
                    Disabilitato = 0,
                    OrderBy = "CITTADINANZA ASC"
                };

                return new CittadinanzaMgr(db).GetList(filtro)
                                                .Select(x => new CittadinanzaCompatto
                                                {
                                                    Codice = x.Codice.Value,
                                                    Descrizione = x.Descrizione,
                                                    Cf = x.Cf,
                                                    Disabilitato = x.Disabilitato.GetValueOrDefault(0) == 1,
                                                    FlgPaeseComunitario = x.FlgPaeseComunitario.GetValueOrDefault(0) == 1
                                                })
                                                .ToList();
            }
        }

        public CittadinanzaCompatto GetCittadinanzaById(string token, int id)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
            {
                var x = new CittadinanzaMgr(db).GetById(id);

                return x == null ? null : new CittadinanzaCompatto
                {
                    Codice = x.Codice.Value,
                    Descrizione = x.Descrizione,
                    Cf = x.Cf,
                    Disabilitato = x.Disabilitato.GetValueOrDefault(0) == 1,
                    FlgPaeseComunitario = x.FlgPaeseComunitario.GetValueOrDefault(0) == 1
                };
            }
        }
    }
}
