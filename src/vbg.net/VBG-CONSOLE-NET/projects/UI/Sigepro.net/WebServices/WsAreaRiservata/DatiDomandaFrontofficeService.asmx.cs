using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using System.ComponentModel;
using Init.SIGePro.Authentication;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using System.Collections.Generic;
using PersonalLib2.Sql;
using Sigepro.net.WebServices.WsAreaRiservata.Classes;
using Init.SIGePro.Manager.DTO.Oggetti;
using Sigepro.net.WebServices.WsSIGePro;

namespace Sigepro.net.WebServices.WsAreaRiservata
{
	/// <summary>
	/// Summary description for DatiDomandaFrontoffice
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[ToolboxItem(false)]
	public class DatiDomandaFrontofficeService : SigeproWebService
	{
		/// <summary>
		/// Effettua il salvataggio di una domanda del frontoffice
		/// </summary>
		/// <param name="token"></param>
		/// <param name="idDomanda"></param>
		/// <param name="datiDomanda"></param>
		[WebMethod]
		public FoDomandeMgr.EsitoSalvataggioDomandaOnline SalvaDomanda(string token, string software, int idDomanda, int codiceAnagrafe, byte[] datiDomanda, string identificativoDomanda, bool flagTrasferita, bool flagPresentata)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			FoDomandeMgr domMgr = new FoDomandeMgr(authInfo.CreateDatabase());

			return domMgr.SalvaOAggiorna(authInfo.IdComune, token, software,idDomanda, codiceAnagrafe, datiDomanda, identificativoDomanda , flagTrasferita, flagPresentata);
		}

		/// <summary>
		/// Legge i dati di una domanda del frontoffice
		/// </summary>
		/// <param name="token"></param>
		/// <param name="idDomanda"></param>
		/// <param name="datiDomanda"></param>
		[WebMethod]
		public byte[] LeggiDomanda(string token, int idDomanda)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			FoDomandeMgr domMgr = new FoDomandeMgr(authInfo.CreateDatabase());

			return domMgr.LeggiDomanda(authInfo.IdComune, idDomanda);
		}


		[WebMethod]
		public FoDomande LeggiDatiDomanda(string token, int idDomanda)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			FoDomandeMgr domMgr = new FoDomandeMgr(authInfo.CreateDatabase());

			return domMgr.GetById(authInfo.IdComune, idDomanda,true);
		}

		/// <summary>
		/// Elimina una domanda del frontoffice
		/// </summary>
		/// <param name="token"></param>
		/// <param name="idDomanda"></param>
		[WebMethod]
		public void EliminaDomanda(string token, int idDomanda)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			FoDomandeMgr domMgr = new FoDomandeMgr(authInfo.CreateDatabase());

			domMgr.EliminaDomanda(authInfo.IdComune, idDomanda);
		}

		/// <summary>
		/// Salva un allegato di una domanda del frontoffice
		/// </summary>
		/// <param name="token"></param>
		/// <param name="idDomanda"></param>
		/// <param name="file"></param>
		/// <returns></returns>
		[WebMethod]
		public int SalvaAllegatoDomanda(string token, int idDomanda, int codiceOggetto)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			FoDomandeOggettiMgr mgr = new FoDomandeOggettiMgr(authInfo.CreateDatabase());

			return mgr.SalvaAllegatoDomanda(authInfo.IdComune, idDomanda, codiceOggetto);
		}


		/// <summary>
		/// Verifica se un'istanza è stata inviata
		/// </summary>
		/// <param name="token"></param>
		/// <param name="idDomanda"></param>
		/// <returns></returns>
		[WebMethod]
		public bool VerificaStatoInvio(string token, int idDomanda)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
			{
				return new FoDomandeMgr(db).VerificaSeInviata(authInfo.IdComune, idDomanda);
			}
		}

		///// <summary>
		///// Verifica se un utente può accedere ad una domanda
		///// </summary>
		///// <param name="token"></param>
		///// <param name="idDomanda"></param>
		///// <param name="codicefiscale"></param>
		///// <param name="verificaSottoscriventi"></param>
		///// <returns></returns>
		//[WebMethod]
		//public bool VerificaPermessiAccesso(string token, int idDomanda, string codicefiscale, bool verificaSottoscriventi)
		//{
		//    AuthenticationInfo authInfo = Autentica(token);

		//    Anagrafe anagrafe = TrovaAnagrafeDaCodiceFiscale(authInfo, codicefiscale);

		//    // Verifico
		//    FoDomande d = new FoDomandeMgr(authInfo.CreateDatabase()).GetById(authInfo.IdComune, idDomanda);

		//    if (d.Codiceanagrafe.GetValueOrDefault(-1) == Convert.ToInt32(anagrafe.CODICEANAGRAFE))
		//        return true;

		//    if (!verificaSottoscriventi)
		//        return false;

		//    FoSottoscrizioni filtro = new FoSottoscrizioni();
		//    filtro.Idcomune = authInfo.IdComune;
		//    filtro.Codicedomanda = idDomanda;
		//    filtro.Codicefiscalesottoscrivente = codicefiscale.ToUpper();

		//    List<FoSottoscrizioni> res = new FoSottoscrizioniMgr(authInfo.CreateDatabase()).GetList(filtro);

		//    return res.Count > 0;

		//}

		/// <summary>
		/// Elimina un allegato di una domanda del frontoffice
		/// </summary>
		/// <param name="token"></param>
		/// <param name="idDomanda"></param>
		/// <param name="codiceOggetto"></param>
		[WebMethod]
		public void EliminaAllegatoDomanda(string token, int idDomanda, int codiceOggetto)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			FoDomandeOggettiMgr mgr = new FoDomandeOggettiMgr(authInfo.CreateDatabase());

			mgr.EliminaAllegatoDomanda(authInfo.IdComune, idDomanda, codiceOggetto);
		}

		[WebMethod]
		public bool OggettoAppartieneADomanda(string token, int idDomanda, int codiceOggetto)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			FoDomandeOggetti oggDom = new FoDomandeOggettiMgr(authInfo.CreateDatabase()).GetById(authInfo.IdComune, idDomanda, codiceOggetto);

			return oggDom != null;
		}

		/// <summary>
		/// Senga una domanda come presentata
		/// </summary>
		/// <param name="token"></param>
		/// <param name="idDomanda"></param>
		[WebMethod]
		public void MarcaDomandaComePresentata(string token, int idDomanda, int codiceIstanza)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			FoDomandeMgr mgr = new FoDomandeMgr(authInfo.CreateDatabase());

			FoDomande dom = mgr.GetById(authInfo.IdComune, idDomanda);

			if (dom.FlgPresentata.GetValueOrDefault(0) == 1)
				throw new InvalidOperationException("La domanda " + idDomanda.ToString() + " è già stata presentata");

			dom.FlgPresentata = 1;
			dom.Codiceistanza = codiceIstanza;
			dom.Datainvio = DateTime.Now;

			mgr.Update(dom);
		}

		/// <summary>
		/// Segna una domanda come trasferita
		/// </summary>
		/// <param name="token"></param>
		/// <param name="idDomanda"></param>
		[WebMethod]
		public void MarcaDomandaComeTrasferita(string token, int idDomanda, List<FoSottoscrizioniMgr.DatiSottoscrizione> datiSottoscrizioni)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			FoDomandeMgr mgr = new FoDomandeMgr(authInfo.CreateDatabase());

			mgr.SegnaDomandaComeTrasferita(authInfo.IdComune, idDomanda, datiSottoscrizioni);
		}
        
        internal void ImpostaIdIstanzaOrigine(string token, int idDomanda, int? idDomandaOrigine)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                new FoDomandeMgr(db).ImpostaIdIstanzaOrigine(authInfo.IdComune, idDomanda, idDomandaOrigine);
            }
                
        }

        [WebMethod]
		public void AnnullaTrasferimento(string token, int idDomanda)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			FoDomandeMgr mgr = new FoDomandeMgr(authInfo.CreateDatabase());

			mgr.AnnullaTrasferimento(authInfo.IdComune, idDomanda);
		}

		[WebMethod]
		public void SottoscriviDomanda(string token, int idDomanda, string codiceFiscale)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			new FoSottoscrizioniMgr(authInfo.CreateDatabase()).SottoscriviDomanda(authInfo.IdComune, idDomanda, codiceFiscale);
		}


		[WebMethod]
		public List<FoSottoscrizioni> GetListaSottoscrizioniUtente(string token, int idDomanda, string codiceFiscaleSottoscrivente)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			FoSottoscrizioni filtro = new FoSottoscrizioni();
			filtro.Idcomune = authInfo.IdComune;
			filtro.Codicedomanda = idDomanda;
			filtro.Codicefiscalesottoscrivente = codiceFiscaleSottoscrivente;

			return new FoSottoscrizioniMgr(authInfo.CreateDatabase()).GetList(filtro);
		}


		[WebMethod]
		public List<FoDomande> GetListaDomandeInSospeso(string token, string software, int codiceAnagrafe)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			//var codiceAnagrafe = TrovaAnagrafeDaCodiceFiscale(authInfo, codiceFiscaleUtente);

			FoDomandeMgr mgr = new FoDomandeMgr(authInfo.CreateDatabase());
			FoDomande dom = new FoDomande();
			dom.Idcomune = authInfo.IdComune;
			dom.Software = software;
			dom.Codiceanagrafe = codiceAnagrafe;
			dom.OthersWhereClause.Add("(FLG_PRESENTATA is null or FLG_PRESENTATA = 0) and (FLG_TRASFERITA is null OR FLG_TRASFERITA = 0)");
			dom.UseForeign = useForeignEnum.Yes;
			dom.OrderBy = "DATA_ULTIMA_MODIFICA desc";

			return mgr.GetList(dom);
		}


		[WebMethod]
		public List<FoDomande> GetListaDomandeDaSottoscrivere(string token, string software, string codiceFiscaleSottoscrivente)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			FoSottoscrizioniMgr mgr = new FoSottoscrizioniMgr(authInfo.CreateDatabase());

			return mgr.GetListaDomandeDaSottoscrivere(authInfo.IdComune, authInfo.Alias, software, codiceFiscaleSottoscrivente);
		}

		[WebMethod]
		public List<FoSottoscrizioni> GetListaSottoscriventi(string token, int codicedomanda)
		{
			AuthenticationInfo authInfo = CheckToken(token);

			FoSottoscrizioniMgr mgr = new FoSottoscrizioniMgr(authInfo.CreateDatabase());

			return mgr.GetListaSottoscriventi(authInfo.IdComune, codicedomanda);
		}
	}
}
