using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Manager.DTO.Interventi;
using Init.SIGePro.Data;
using Init.Utils;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager.DTO.AllegatiDomandaOnline;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public NodoAlberoInterventiDto GetAlberaturaDaIdNodo(string token, int codiceIntervento)
		{
			return new AlberoprocService().GetAlberaturaDaIdNodo(token, codiceIntervento);
		}

		[WebMethod]
		public NodoAlberoInterventiDto GetStrutturaAlberoInterventi(string token, string software)
		{
			return new AlberoprocService().GetStrutturaAlberoInterventi(token, software);
		}

		[WebMethod]
        public List<InterventoDto> GetSottonodiIntervento(string token, string software, int idNodo, AmbitoRicerca ambitoRicerca)
		{
            return new AlberoprocService().GetSottonodiIntervento(token, software, idNodo, ambitoRicerca);
		}

		[WebMethod]
        public List<InterventoDto> GetSottonodiInterventoDaIdAteco(string token, string software, int idNodoPadre, int idAteco, AmbitoRicerca ambitoRicerca)
		{
			return new AlberoprocService().GetSottonodiInterventoDaIdAteco(token, software, idNodoPadre, idAteco,ambitoRicerca);
		}

		[WebMethod]
		public InterventoDto GetDettagliIntervento(string token, int codiceIntervento, AmbitoRicerca ambitoRicercaDocumenti)
		{
			return new AlberoprocService().GetDettagliIntervento(token, codiceIntervento, ambitoRicercaDocumenti);
		}

		[WebMethod]
		public List<AllegatoInterventoDomandaOnlineDto> GetDocumentiDaCodiceIntervento(string token, int codiceIntervento, AmbitoRicerca ambitoRicercaDocumenti)
		{
			return new WsSIGePro.Interventi().GetDocumentiDaCodiceIntervento(token, codiceIntervento,ambitoRicercaDocumenti);
		}

        [WebMethod]
        public string GetIdDrupalDaCodiceIntervento(string token, int codiceIntervento)
        {
            return new WsSIGePro.Interventi().GetIdDrupalDaCodiceIntervento(token, codiceIntervento);
        }


		[WebMethod]
		public List<AlberoProcDocumentiCat> GetCategorieAllegatiInterventoChePermettonoUpload(string token, string software)
		{
			return new WsSIGePro.Interventi().GetCategorieAllegatiChePermettonoUpload(token, software);
		}

		[WebMethod]
		public List<BaseDto<int, string>> RicercaTestualeInterventi(string token, string software, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca, AmbitoRicerca ambitoRicerca)
		{
			return new WsSIGePro.Interventi().RicercaTestualeInterventi(token, software,matchParziale, matchCount, modoRicerca, tipoRicerca, ambitoRicerca);
		}

		[WebMethod]
		public List<int> GetListaIdNodiPadreIntervento(string token, int codiceIntervento)
		{
			return new WsSIGePro.Interventi().GetListaIdNodiPadre(token, codiceIntervento);
		}

		[WebMethod]
		public int? GetIdCertificatoDiInvioDomandaDaIdIntervento(string token, int idIntervento)
		{
			var authInfo = CheckToken(token);

			return new AlberoProcMgr(authInfo.CreateDatabase()).GetIdCertificatoDiInvioDomandaDaIdIntervento(authInfo.IdComune, idIntervento);
		}

		[WebMethod]
		public int? GetIdRiepilogoDomandaDaIdIntervento(string token, int idIntervento)
		{
			var authInfo = CheckToken(token);

			return new AlberoProcMgr(authInfo.CreateDatabase()).GetIdRiepilogoDomandaDaIdIntervento(authInfo.IdComune, idIntervento);
		}

		[WebMethod]
		public int? GetCodiceOggettoWorkflowDaCodiceIntervento(string token, int idIntervento)
		{
			var authInfo = CheckToken(token);

			return new AlberoProcMgr(authInfo.CreateDatabase()).GetCodiceOggettoWorkflowDaIdIntervento(authInfo.IdComune, idIntervento);
		}

        [WebMethod]
        public bool InterventoSupportaRedirect(string token, int idIntervento)
        {
            var authInfo = CheckToken(token);

            return new AlberoProcMgr(authInfo.CreateDatabase()).InterventoSupportaRedirect(authInfo.IdComune, idIntervento);
        }

        [WebMethod]
        public bool HaPresentatoDomandePerIntervento(string token, int idIntervento, string codiceFiscaleRichiedente)
        {
            var authInfo = CheckToken(token);

            return new AlberoProcMgr(authInfo.CreateDatabase()).HaPresentatoDomandePerIntervento(authInfo.IdComune, idIntervento, codiceFiscaleRichiedente);
        }
    }
}
