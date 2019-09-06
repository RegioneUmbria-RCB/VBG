using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Sigepro.net.WebServices.WsAreaRiservata.Classes;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.Data;
using Init.SIGePro.DatiDinamici.Scripts;
using Init.SIGePro.DatiDinamici.Utils;
using Sigepro.net.WebServices.WsSIGePro;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using Init.SIGePro.Manager.DTO.Interventi;
using Init.SIGePro.Manager.Logic.DatiDinamici.RicercheSigepro;
using System.Xml.Serialization;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Manager.Logic.GestioneDecodifiche;
using Init.SIGePro.Manager.Logic.GestioneDomandaOnLine;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
	/// <summary>
	/// Summary description for DatiDinamiciService
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class DatiDinamiciService : System.Web.Services.WebService
	{
		public class GetModelliDinamiciDaInterventoEEndoRequest
		{
			[XmlElement(Order=0)]
			public int CodiceIntervento { get; set; }
			[XmlElement(Order = 1)]
			public List<int> ListaEndo { get; set; }
			[XmlElement(Order = 2)]
			public List<string> ListaTipiLocalizzazioni { get; set; }
			[XmlElement(Order = 3)]
			public bool IgnoraTipiLocalizzazione { get; set; }

			public GetModelliDinamiciDaInterventoEEndoRequest()
			{
				ListaEndo = new List<int>();
				ListaTipiLocalizzazioni = new List<string>();
			}

			
		}

		public class StrutturaModelloDinamico
		{
			public Dyn2ModelliT Modello { get; set; }
			public List<Dyn2ModelliD> Struttura { get; set; }
			public List<Dyn2ModelliScript> ScriptsModello { get;set; }
			public List<Dyn2CampiScript> ScriptsCampi { get; set; }
			public List<Dyn2Campi> CampiDinamici { get; set; }
			public List<Dyn2CampiProprieta> ProprietaCampiDinamici { get; set; }
			public List<Dyn2TestoModello> Testi { get; set; }
		}

        internal DecodificaDTO[] GetDecodificheAttive(string token, string tabella)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new DecodificheService(db, authInfo.IdComune).GetDecodificheAttive(tabella).ToArray();
            }
        }

        [WebMethod]
		public WsListaModelliDinamiciDomanda GetModelliDinamiciDaInterventoEEndo(string token , GetModelliDinamiciDaInterventoEEndoRequest request)
		{
			var authInfo = CheckToken( token );

			// Leggo l'albero degli interventi
			var db = authInfo.CreateDatabase();

			var alberoProcMgr			= new AlberoProcMgr(db);
			var inventarioProcMgr		= new InventarioProcedimentiMgr( db );
			var foArConfigurazioneMgr	= new FoArConfigurazioneMgr( db );
			
			var rVal = new WsListaModelliDinamiciDomanda();

            if (request.CodiceIntervento <= 0)
            {
                rVal.SchedeIntervento = Enumerable.Empty<SchedaDinamicaInterventoDto>().ToList();
            } else
            {
                rVal.SchedeIntervento = alberoProcMgr.GetSchedeDinamicheFoDaIdIntervento(authInfo.IdComune, request.CodiceIntervento);
            }
			
			rVal.SchedeEndoprocedimenti = inventarioProcMgr.GetSchedeDinamicheDaEndoprocedimentiList(authInfo.IdComune, request.ListaEndo, request.ListaTipiLocalizzazioni, request.IgnoraTipiLocalizzazione);

			return rVal;
		}

		[WebMethod]
		public StrutturaModelloDinamico GetStrutturaModelloDinamico(string token, int idModello)
		{
			var authInfo = CheckToken(token);

			var rVal = new StrutturaModelloDinamico();

			using(var db = authInfo.CreateDatabase())
			{
				try
				{
					db.Connection.Open();

					string idComune = authInfo.IdComune;

					rVal.Modello = new Dyn2ModelliTMgr(db).GetById(idComune, idModello);
					rVal.Struttura = new Dyn2ModelliDMgr(db).GetListImpl(idComune, idModello);
					rVal.ScriptsModello = new Dyn2ModelliScriptMgr(db).GetList(idComune, idModello);
					rVal.ScriptsCampi = new Dyn2CampiScriptMgr(db).GetListDaIdModello( idComune , idModello );
					rVal.CampiDinamici = new Dyn2CampiMgr(db).GetList(idComune, idModello);
					rVal.ProprietaCampiDinamici = new Dyn2CampiProprietaMgr(db).GetListDaIdModello(idComune, idModello);
					rVal.Testi = new Dyn2ModelliDTestiMgr(db).GetList(idComune, idModello);

					return rVal;
				}
				finally
				{
					db.Connection.Close();
				}
			}
		}

		[WebMethod]
		public RicercheDatiDinamiciService.RisultatoRicercaDatiDinamici[] GetCompletionListRicerchePlus(string token, int idCampo, string partial, List<ValoreFiltroRicerca> filtri)
		{
            return new RicercheDatiDinamiciService(token).GetCompletionList(idCampo, partial, filtri).ToArray();
		}

		[WebMethod]
		public RicercheDatiDinamiciService.RisultatoRicercaDatiDinamici InitializeControlRicerchePlus(string token, int idCampo, string valore)
		{
			return new RicercheDatiDinamiciService(token).InitializeControl(idCampo, valore);
		}

        public IstanzeDyn2Dati[] GetDyn2DatiByIdModello(string token, int codiceIstanza, int idModello, int indiceCampo)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new IstanzeDyn2DatiMgr(db).GetDyn2DatiByIdModello(authInfo.IdComune, codiceIstanza, idModello, indiceCampo);
            }
                
        }

        public IstanzeDyn2Dati[] GetDyn2DatiByCodiceIstanza(string token, int codiceIstanza)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new IstanzeDyn2DatiMgr(db).GetListByCodiceIstanza(authInfo.IdComune, codiceIstanza);
            }

        }

        public void RecuperaDocumentiIstanzaCollegata(string token, int codiceIstanzaOrigine, int idDomandaDestinazione) {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                new DomandaOnlineService(db, authInfo.IdComune).RecuperaDocumentiIstanzaCollegata( codiceIstanzaOrigine, idDomandaDestinazione );
            }
        }

        private AuthenticationInfo CheckToken(string token)
		{
			var authInfo = AuthenticationManager.CheckToken( token );

			if(authInfo == null)
				throw new InvalidTokenException( token );

			return authInfo;
		}
	}
}
