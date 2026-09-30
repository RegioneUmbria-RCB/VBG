using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.TabelleDiBase;
using Init.SIGePro.Manager.Manager;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.TabelleDiBase
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsTabelleDiBaseService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsTabelleDiBaseService.svc or WsTabelleDiBaseService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsTabelleDiBaseService : WcfServiceBase, IWsTabelleDiBaseService
    {
        public List<ElencoProfessionaleDto> GetElenchiProfessionali(string token)
        {
            var authResult = this.CheckToken(token);

            if (authResult == null)
                throw new ArgumentException("Token non valido: " + token);

            using (var db = authResult.CreateDatabase())
            {
                var l = new ElenchiProfessionaliBaseMgr(db).GetList(new ElenchiProfessionaliBase());

                return l?.Select(x => new ElencoProfessionaleDto
                {
                    EpId = x.EpId,
                    EpDescrizione = x.EpDescrizione,
                    EpRegionale = x.EpRegionale
                })?.ToList() ?? new List<ElencoProfessionaleDto>();
            }
        }

        public List<FormaGiuridicaDto> GetListaFormeGiuridiche(string token)
        {
            var ai = this.CheckToken(token);

            if (ai == null)
                throw new InvalidTokenException(token);

            var filtro = new FormeGiuridiche
            {
                IDCOMUNE = ai.IdComune,
                OrderBy = "FORMAGIURIDICA asc"
            };

            using (var db = ai.CreateDatabase())
            {
                var l = new FormeGiuridicheMgr(db).GetList(filtro);

                return l?.Select(x => new FormaGiuridicaDto
                {
                    CodiceFormaGiuridica = x.CODICEFORMAGIURIDICA,
                    FormaGiuridica = x.FORMAGIURIDICA
                })?.ToList() ?? new List<FormaGiuridicaDto>();
            }
        }

        public List<SedeInpsDto> GetElencoSediInps(string token)
        {
            var auth = this.CheckToken(token);

            using (var db = auth.CreateDatabase())
            {
                var l = new InpsInailMgr(auth.CreateDatabase(), auth.IdComune).GetElencoSediInps();

                return l?.Select(x => new SedeInpsDto
                {
                    Codice = x.Codice,
                    Descrizione = x.Descrizione
                })?.ToList() ?? new List<SedeInpsDto>();
            }

        }

        public List<SedeInailDto> GetElencoSediInail(string token)
        {
            var auth = this.CheckToken(token);

            using (var db = auth.CreateDatabase())
            {
                var l = new InpsInailMgr(auth.CreateDatabase(), auth.IdComune).GetElencoSediInail();

                return l?.Select(x => new SedeInailDto
                {
                    Codice = x.Codice,
                    Descrizione = x.Descrizione
                })?.ToList() ?? new List<SedeInailDto>();
            }
        }

        public List<ModalitaPagamentoDto> GetModalitaPagamento(string token)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return
                    new TipiModalitaPagamentoMgr(db).GetList(authInfo.IdComune)
                                                    .Where(x => x.FLAG_DISABILITATO.GetValueOrDefault(0) != 1)
                                                    .Select(x => new ModalitaPagamentoDto
                                                    {
                                                        Codice = x.MP_ID,
                                                        Descrizione = x.MP_DESCRESTESA
                                                    })
                                                    .ToList();

            }
        }

        public List<TitoloDto> GetListaTitoli(string token)
        {
            var ai = this.CheckToken(token);

            if (ai == null)
                throw new InvalidTokenException(token);

            var filtro = new Titoli
            {
                IDCOMUNE = ai.IdComune,
                OrderBy = "TITOLO asc"
            };

            using (var db = ai.CreateDatabase())
            {
                var l = new TitoliMgr(ai.CreateDatabase()).GetList(filtro);

                return l.Select(x => new TitoloDto
                {
                    CodiceTitolo = x.CODICETITOLO,
                    Titolo = x.TITOLO
                }).ToList();
            }
        }
    }
}
