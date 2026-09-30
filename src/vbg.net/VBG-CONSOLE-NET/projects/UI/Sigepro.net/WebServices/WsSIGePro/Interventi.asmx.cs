using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager.DTO.AllegatiDomandaOnline;
using log4net;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Linq;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsSIGePro
{
    /// <summary>
    /// Summary description for Interventi
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [ToolboxItem(false)]
    public class Interventi : SigeproWebService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(Interventi));
        [WebMethod]
        public List<AllegatoInterventoDomandaOnlineDto> GetDocumentiDaCodiceIntervento(string token, int codiceIntervento, AmbitoRicerca ambitoRicercaDocumenti)
        {
            this._log.Debug($"GetDocumentiDaCodiceIntervento {codiceIntervento}");

            var authInfo = this.CheckToken(token);
            var idcomune = authInfo.IdComune;

            using (var db = authInfo.CreateDatabase())
            {
                var risoluzioneNomeFile = new Func<int, string>((x) => { return new OggettiMgr(db).GetNomeFile(idcomune, x); });

                // Documenti dell'intervento
                var alberoProcDocumentiMgr = new AlberoProcDocumentiMgr(db);
                var allegatiIntervento = alberoProcDocumentiMgr.GetListDaCodiceIntervento(authInfo.IdComune, codiceIntervento, ambitoRicercaDocumenti)
                                                               .Select(doc => AllegatoInterventoDomandaOnlineDto.FromAllegatoIntervento(doc, risoluzioneNomeFile));

                if (this._log.IsDebugEnabled)
                {
                    var debugAll = allegatiIntervento.Select(x => $"{x.Codice}-{x.Descrizione}:codiceogg={x.CodiceOggettoModello}, RichiedeFirma={x.RichiedeFirma}, RiepilogoDomanda={x.RiepilogoDomanda}\r\n");
                    this._log.Debug($"Lista oggetti per intervento {codiceIntervento}: \r\n{debugAll}");
                }

                // Documenti della procedura
                var codiceProcedura = new AlberoProcMgr(db).CodiceProceduraDaIdIntervento(authInfo.IdComune, codiceIntervento);

                if (!codiceProcedura.HasValue)
                    return allegatiIntervento.ToList();

                var allegatiProcedura = new TipiProcedureDocumentiMgr(db).GetByIdProcedura(authInfo.IdComune, codiceProcedura.Value, ambitoRicercaDocumenti)
                                                                         .Select(doc => AllegatoInterventoDomandaOnlineDto.FromAllegatoProcedura(doc, risoluzioneNomeFile));

                var listaAllegatiCompleta = allegatiIntervento.Union(allegatiProcedura).ToList();

                return listaAllegatiCompleta;
            }
        }


        [WebMethod]
        public List<AlberoProcDocumentiCat> GetCategorieAllegatiChePermettonoUpload(string token, string software)
        {
            var authInfo = this.CheckToken(token);

            AlberoProcDocumentiCat filtro = new AlberoProcDocumentiCat();
            filtro.Idcomune = authInfo.IdComune;
            filtro.Software = software;
            filtro.OthersWhereClause.Add("FO_NONPERMETTEUPLOAD<>1");
            filtro.OrderBy = "descrizione asc";

            return new AlberoProcDocumentiCatMgr(authInfo.CreateDatabase()).GetList(filtro);
        }

        [WebMethod]
        public List<BaseDto<int, string>> RicercaTestualeInterventi(string token, string software, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca, AmbitoRicerca ambitoRicerca)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new AlberoProcMgr(db).RicercaTestualeInterventi(authInfo.IdComune, software, matchParziale, matchCount, modoRicerca, tipoRicerca, ambitoRicerca);
            }
        }


        [WebMethod]
        public List<int> GetListaIdNodiPadre(string token, int codiceIntervento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new AlberoProcMgr(db).GetListaIdNodiPadre(authInfo.IdComune, codiceIntervento);
            }
        }

    }
}
