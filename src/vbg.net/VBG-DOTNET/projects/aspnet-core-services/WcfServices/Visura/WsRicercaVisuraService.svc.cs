using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Visura;
using Init.SIGePro.Manager.DTO.Visura.V1;
using Init.SIGePro.Manager.DTO.Visura.V2;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Visura
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsRicercaVisuraService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsRicercaVisuraService.svc or WsRicercaVisuraService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsRicercaVisuraService : WcfServiceBase, IWsRicercaVisuraService
    {

        #region Visura versione V1

        public List<CampoVisuraFrontofficeDto> GetFiltriVisuraFrontoffice(string token, string software)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
            {
                var l = new FoConfigurazioneMgr(db).GetFiltriVisuraFrontoffice(authResult.IdComune, software);

                return l?.Select(x => new CampoVisuraFrontofficeDto
                {
                    Codice = x.Codice,
                    Etichetta = x.Etichetta,
                    IdRisorsa = x.IdRisorsa,
                    Valore = x.Valore
                })?.ToList() ?? new List<CampoVisuraFrontofficeDto>();
            }
        }


        public List<CampoVisuraFrontofficeDto> GetCampiTabellaVisura(string token, string software)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
            {
                var l = new FoConfigurazioneMgr(db).GetCampiTabellaVisura(authResult.IdComune, software);

                return l?.Select(x => new CampoVisuraFrontofficeDto
                {
                    Codice = x.Codice,
                    Etichetta = x.Etichetta,
                    IdRisorsa = x.IdRisorsa,
                    Valore = x.Valore
                })?.ToList() ?? new List<CampoVisuraFrontofficeDto>();
            }
        }

        public List<CampoVisuraFrontofficeDto> GetFiltriArchivioIstanzeFrontoffice(string token, string software)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
            {
                var l = new FoConfigurazioneMgr(db).GetFiltriArchivioIstanzeFrontoffice(authResult.IdComune, software);

                return l?.Select(x => new CampoVisuraFrontofficeDto
                {
                    Codice = x.Codice,
                    Etichetta = x.Etichetta,
                    IdRisorsa = x.IdRisorsa,
                    Valore = x.Valore
                })?.ToList() ?? new List<CampoVisuraFrontofficeDto>();
            }
        }

        public List<CampoVisuraFrontofficeDto> GetCampiTabellaArchivioIstanze(string token, string software)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
            {
                var l = new FoConfigurazioneMgr(db).GetCampiTabellaArchivioIstanze(authResult.IdComune, software);

                return l?.Select(x => new CampoVisuraFrontofficeDto
                {
                    Codice = x.Codice,
                    Etichetta = x.Etichetta,
                    IdRisorsa = x.IdRisorsa,
                    Valore = x.Valore
                })?.ToList() ?? new List<CampoVisuraFrontofficeDto>();
            }
        }


        public int GetRecordPerPagina(string token, string software)
        {
            var authResult = this.CheckToken(token);

            using (var db = authResult.CreateDatabase())
                return new FoConfigurazioneMgr(db).GetRecordPerPagina(authResult.IdComune, software);
        }

        #endregion

        #region Visura versione V2

        private const string CONTESTO_ARCHIVIO_LISTA = "ARCHIVIO_LISTA";
        private const string CONTESTO_ARCHIVIO_FILTRI = "ARCHIVIO_FILTRI";
        private const string CONTESTO_VISURA_LISTA = "VISURA_LISTA";
        private const string CONTESTO_VISURA_FILTRI = "VISURA_FILTRI";

        public WsRicercaVisuraService(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
        }

        public List<FoVisuraCampiDto> GetFiltriVisuraV2(string token, string software)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var l = new FoVisuraCampiMgr(db).GetList(ai.IdComune, software, CONTESTO_VISURA_FILTRI);

                return l?.Select(x => new FoVisuraCampiDto
                {
                    Fkidcampo = x.Fkidcampo
                })?.ToList() ?? new List<FoVisuraCampiDto>();
            }
        }


        public List<FoVisuraCampiDto> GetCampiListaVisuraV2(string token, string software)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var l = new FoVisuraCampiMgr(db).GetList(ai.IdComune, software, CONTESTO_VISURA_LISTA);

                return l?.Select(x => new FoVisuraCampiDto
                {
                    Fkidcampo = x.Fkidcampo
                })?.ToList() ?? new List<FoVisuraCampiDto>();
            }
        }

        public List<FoVisuraCampiDto> GetFiltriArchivioV2(string token, string software)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var l = new FoVisuraCampiMgr(db).GetList(ai.IdComune, software, CONTESTO_ARCHIVIO_FILTRI);

                return l?.Select(x => new FoVisuraCampiDto
                {
                    Fkidcampo = x.Fkidcampo
                })?.ToList() ?? new List<FoVisuraCampiDto>();
            }
        }

        public List<FoVisuraCampiDto> GetCampiListaArchivioV2(string token, string software)
        {
            var ai = this.CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var l = new FoVisuraCampiMgr(db).GetList(ai.IdComune, software, CONTESTO_ARCHIVIO_LISTA);

                return l?.Select(x => new FoVisuraCampiDto
                {
                    Fkidcampo = x.Fkidcampo
                })?.ToList() ?? new List<FoVisuraCampiDto>();
            }
        }

        #endregion

        #region Stati istanza
        public List<StatoIstanzaDto> GetStatiIstanza(string token, string software)
        {
            var authResult = this.CheckToken(token);


            using (var db = authResult.CreateDatabase())
            {
                var filtro = new Init.SIGePro.Data.StatiIstanza
                {
                    Idcomune = authResult.IdComune,
                    Software = software,
                    OrderBy = "ordine asc"
                };

                var l = new StatiIstanzaMgr(db).GetList(filtro);

                return l?.Select(x => new StatoIstanzaDto
                {
                    CodiceStato = x.Codicestato,
                    Stato = x.Stato
                })?.ToList() ?? new List<StatoIstanzaDto>();
            }
        }

        public StatoIstanzaDto GetStatoIstanza(string token, string software, string codiceStato)
        {
            var authResult = this.CheckToken(token);

            if (authResult == null)
                throw new ArgumentException("Token non valido: " + token);

            using (var db = authResult.CreateDatabase())
            {
                var s = new StatiIstanzaMgr(db).GetById(authResult.IdComune, software, codiceStato);

                if (s == null)
                {
                    return null;
                }

                return new StatoIstanzaDto
                {
                    CodiceStato = s.Codicestato,
                    Stato = s.Stato
                };
            }
        }
        #endregion
    }
}
