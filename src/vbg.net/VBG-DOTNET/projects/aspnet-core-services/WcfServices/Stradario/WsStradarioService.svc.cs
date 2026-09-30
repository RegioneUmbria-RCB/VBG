using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Comuni;
using Init.SIGePro.Manager.DTO.StradarioComune;
using Init.SIGePro.Manager.Logic.GestioneStradario;
using Init.SIGePro.Manager.Logic.GestioneStradario.RicercaStradario;
using log4net;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Stradario
{
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "IWsStradarioService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select IWsStradarioService.svc or IWsStradarioService.svc.cs at the Solution Explorer and start debugging.
    public class WsStradarioService : WcfServiceBase, IWsStradarioService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WsStradarioService));

        public WsStradarioService(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
        }

        public StradarioEstesoDto GetByCodiceStradario(string token, int codiceStradario)
        {
            var authResult = this.CheckToken(token);

            if (authResult == null)
                throw new ArgumentException("Token non valido: " + token);

            using (var db = authResult.CreateDatabase())
            {
                var s = new StradarioMgr(db).GetById(authResult.IdComune, codiceStradario);

                if (s == null)
                {
                    return null;
                }

                return new StradarioEstesoDto
                {
                    CodiceStradario = Convert.ToInt32(s.CODICESTRADARIO),
                    Cap = s.CAP,
                    CodViario = s.CODVIARIO,
                    Descrizione = s.DESCRIZIONE,
                    IdComune = s.IDCOMUNE,
                    LocFraz = s.LOCFRAZ,
                    Prefisso = s.PREFISSO,
                    ComuneLocalizzazione = s.ComuneLocalizzazione == null ? null : new DatiComuneCompatto
                    {
                        Cf = s.ComuneLocalizzazione.CF,
                        CodiceComune = s.ComuneLocalizzazione.CODICECOMUNE,
                        Comune = s.ComuneLocalizzazione.COMUNE,
                        Provincia = s.ComuneLocalizzazione.PROVINCIA,
                        SiglaProvincia = s.ComuneLocalizzazione.SIGLAPROVINCIA
                    }
                };
            }
        }

        public StradarioEstesoDto GetByIndirizzo(string token, string codiceComune, string indirizzo)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
            {
                var matches = new StradarioMgr(db).GetByIndirizzo(authResult.IdComune, codiceComune, indirizzo);

                if (matches.Count != 1)
                    return null;

                var s = matches[0];

                return new StradarioEstesoDto
                {
                    CodiceStradario = Convert.ToInt32(s.CODICESTRADARIO),
                    Cap = s.CAP,
                    CodViario = s.CODVIARIO,
                    Descrizione = s.DESCRIZIONE,
                    IdComune = s.IDCOMUNE,
                    LocFraz = s.LOCFRAZ,
                    Prefisso = s.PREFISSO,
                    ComuneLocalizzazione = s.ComuneLocalizzazione == null ? null : new DatiComuneCompatto
                    {
                        Cf = s.ComuneLocalizzazione.CF,
                        CodiceComune = s.ComuneLocalizzazione.CODICECOMUNE,
                        Comune = s.ComuneLocalizzazione.COMUNE,
                        Provincia = s.ComuneLocalizzazione.PROVINCIA,
                        SiglaProvincia = s.ComuneLocalizzazione.SIGLAPROVINCIA
                    }
                };
            }
        }

        public List<StradarioDto> GetByMatchParziale(string token, string codiceComune, string comuneLocalizzazione, string indirizzo)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
            {
                var repository = new StradarioRepository(db, authResult.IdComune);

                return new GestioneStradarioService(repository).FindByMatchParziale(codiceComune, comuneLocalizzazione, indirizzo, true).ToList();
            }
        }

        public List<StradarioDto> GetByMatchParzialeIncludiDisabilitate(string token, string codiceComune, string comuneLocalizzazione, string indirizzo)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
            {
                var repository = new StradarioRepository(db, authResult.IdComune);

                return new GestioneStradarioService(repository).FindByMatchParziale(codiceComune, comuneLocalizzazione, indirizzo, false).ToList();
            }
        }


        public List<ColoreStradarioDto> GetListaColori(string token)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new StradarioColoreMgr(db).GetList(new StradarioColore { IDCOMUNE = authInfo.IdComune })
                                                 .OrderBy(x => x.COLORE)
                                                 .Select(x => new ColoreStradarioDto
                                                 {
                                                     CodiceColore = x.CODICECOLORE,
                                                     Colore = x.COLORE
                                                 })
                                                 .ToList();
            }
        }

        public StradarioDto GetStradarioByCodViario(string token, string codViario)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var stradario = new StradarioMgr(db).GetByCodViario(authInfo.IdComune, codViario);

                if (stradario == null)
                {
                    return null;
                }

                return this.CreateStradarioDto(stradario);
            }
        }

        public StradarioDto CreateStradarioDto(Init.SIGePro.Data.Stradario stradario)
        {
            var toponimo = stradario.PREFISSO;
            var via = stradario.DESCRIZIONE;

            if (!String.IsNullOrEmpty(stradario.LOCFRAZ))
                via += " (" + stradario.LOCFRAZ + ")";

            return new StradarioDto
            {
                CodiceStradario = Convert.ToInt32(stradario.CODICESTRADARIO),
                NomeVia = String.IsNullOrEmpty(toponimo) ? via : toponimo + " " + via,
                CodViario = stradario.CODVIARIO
            };
        }

        public DatiComuneCompatto[] GetComuniLocalizzazioni(string token, string codiceComune)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                try
                {
                    return new StradarioMgr(db).GetComuniLocalizzazioni(authInfo.IdComune, codiceComune);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore durante la lettura della lista di comuni delle localizzazioni per il codce comune {0}: {1}", codiceComune, ex.ToString());
                }

                return new DatiComuneCompatto[0];
            }
        }
    }
}
