using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using System.Web.Services;
using PersonalLib2.Sql;
using Init.SIGePro.Manager.DTO.Oggetti;


namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		/*
		/// <summary>
		/// Crea una nuova domanda frontoffice nel database
		/// </summary>
		/// <param name="token"></param>
		/// <param name="software"></param>
		/// <param name="codiceFiscaleUtente"></param>
		/// <param name="datiDomanda"></param>
		/// <returns></returns>
		[WebMethod]
		public int CreaDomanda(string token, string software, int idDomanda, string codiceFiscaleUtente, byte[] datiDomanda)
		{
			return new DatiDomandaFrontofficeService().CreaDomanda(token, software, idDomanda, codiceFiscaleUtente, datiDomanda);
		}
		*/
		[WebMethod]
		public int GetProssimoIdDomanda(string token)
		{
			var authInfo = CheckToken( token );

			return new FoDomandeMgr(authInfo.CreateDatabase()).GetProssimoIdDomanda(authInfo.IdComune);
		}

		/// <summary>
		/// Effettua il salvataggio di una domanda del frontoffice
		/// </summary>
		/// <param name="token"></param>
		/// <param name="idDomanda"></param>
		/// <param name="datiDomanda"></param>
		[WebMethod]
		public FoDomandeMgr.EsitoSalvataggioDomandaOnline SalvaDomanda(string token, string software, int idDomanda, int codiceAnagrafe, byte[] datiDomanda, string identificativoDomanda, bool flagTrasferita, bool flagPresentata)
		{
			return new DatiDomandaFrontofficeService().SalvaDomanda(token,software, idDomanda,codiceAnagrafe, datiDomanda, identificativoDomanda,flagTrasferita,flagPresentata);
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
			return new DatiDomandaFrontofficeService().LeggiDomanda(token, idDomanda);
		}


		[WebMethod]
		public FoDomande LeggiDatiDomanda(string token, int idDomanda)
		{
			return new DatiDomandaFrontofficeService().LeggiDatiDomanda(token, idDomanda);
		}

		/// <summary>
		/// Elimina una domanda del frontoffice
		/// </summary>
		/// <param name="token"></param>
		/// <param name="idDomanda"></param>
		[WebMethod]
		public void EliminaDomanda(string token, int idDomanda)
		{
			new DatiDomandaFrontofficeService().EliminaDomanda(token, idDomanda);
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
			return new DatiDomandaFrontofficeService().SalvaAllegatoDomanda(token, idDomanda, codiceOggetto);
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
			return new DatiDomandaFrontofficeService().VerificaStatoInvio(token, idDomanda);
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
		//    return new DatiDomandaFrontofficeService().VerificaPermessiAccesso(token, idDomanda, codicefiscale, verificaSottoscriventi);

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
			new DatiDomandaFrontofficeService().EliminaAllegatoDomanda(token, idDomanda, codiceOggetto);
		}

		[WebMethod]
		public bool OggettoAppartieneADomanda(string token, int idDomanda, int codiceOggetto)
		{
			return new DatiDomandaFrontofficeService().OggettoAppartieneADomanda(token, idDomanda, codiceOggetto);
		}

		/// <summary>
		/// Segna una domanda come presentata
		/// </summary>
		/// <param name="token"></param>
		/// <param name="idDomanda"></param>
		[WebMethod]
		public void MarcaDomandaComePresentata(string token, int idDomanda, int codiceIstanza)
		{
			new DatiDomandaFrontofficeService().MarcaDomandaComePresentata(token, idDomanda, codiceIstanza);
		}

		/// <summary>
		/// Segna una domanda come trasferita
		/// </summary>
		/// <param name="token"></param>
		/// <param name="idDomanda"></param>
		[WebMethod]
		public void MarcaDomandaComeTrasferita(string token, int idDomanda, List<FoSottoscrizioniMgr.DatiSottoscrizione> datiSottoscrizioni)
		{
			new DatiDomandaFrontofficeService().MarcaDomandaComeTrasferita(token, idDomanda, datiSottoscrizioni);
		}

		[WebMethod]
		public void AnnullaTrasferimento(string token, int idDomanda)
		{
			new DatiDomandaFrontofficeService().AnnullaTrasferimento(token, idDomanda);
		}

		[WebMethod]
		public void SottoscriviDomanda(string token, int idDomanda, string codiceFiscale)
		{
			new DatiDomandaFrontofficeService().SottoscriviDomanda(token, idDomanda, codiceFiscale);
		}


		[WebMethod]
		public List<FoSottoscrizioni> GetListaSottoscrizioniUtente(string token, int idDomanda, string codiceFiscaleSottoscrivente)
		{
			return new DatiDomandaFrontofficeService().GetListaSottoscrizioniUtente(token, idDomanda, codiceFiscaleSottoscrivente);
		}


		[WebMethod]
		public List<FoDomande> GetListaDomandeInSospeso(string token, string software, int codiceAnagrafe)
		{
			return new DatiDomandaFrontofficeService().GetListaDomandeInSospeso(token, software, codiceAnagrafe);
		}


		[WebMethod]
		public List<FoDomande> GetListaDomandeDaSottoscrivere(string token, string software, string codiceFiscaleSottoscrivente)
		{
			return new DatiDomandaFrontofficeService().GetListaDomandeDaSottoscrivere(token, software, codiceFiscaleSottoscrivente);
		}

		[WebMethod]
		public List<FoSottoscrizioni> GetListaSottoscriventi(string token, int codicedomanda)
		{
			return new DatiDomandaFrontofficeService().GetListaSottoscriventi(token, codicedomanda);
		}

        [WebMethod]
        public void ImpostaIdIstanzaOrigine(string token, int idDomanda, int? idDomandaOrigine)
        {
            new DatiDomandaFrontofficeService().ImpostaIdIstanzaOrigine(token, idDomanda, idDomandaOrigine);
        }

    }
}
