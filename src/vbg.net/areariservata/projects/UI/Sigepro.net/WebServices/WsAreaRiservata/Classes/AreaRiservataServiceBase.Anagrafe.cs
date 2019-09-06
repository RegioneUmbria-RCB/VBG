using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Data;
using SIGePro.Net.WebServices.WsSIGeProAnagrafe;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
		[WebMethod]
		public Anagrafe GetAnagraficaById(string token, int codiceanagrafe)
		{
			return new WsAnagrafe().GetDaCodiceAnagrafe(token, codiceanagrafe);
		}

		[WebMethod]
		public Anagrafe GetAnagrafeByUserId(string token, string userId, TipoPersona tipoPersona)
		{
            return new WsAnagrafe().GetByUserId(token, userId, tipoPersona);
		}

		[WebMethod]
		public void ModificaPasswordAnagrafica(string token, int codiceAnagrafe, string vecchiaPassword, string nuovaPassword)
		{
			new WsAnagrafe().ModificaPasswordAnagrafe(token, codiceAnagrafe, vecchiaPassword, nuovaPassword);
		}


		[WebMethod]
		public void RichiediModificaDatiAnagrafica(string token, Anagrafe nuoviDatianagrafici)
		{
			new WsAnagrafe().RichiediModificaDati(token, nuoviDatianagrafici);
		}

        //[WebMethod]
        //public void RichiediNuovaRegistrazione(string token, Anagrafe anagrafe)
        //{
        //    new WsAnagrafe().RichiediNuovaRegistrazione(token, anagrafe);
        //}
		/*
		[WebMethod]
		public Anagrafe GetAnagrafeByCodiceFiscale(string authenticationToken, string codiceFiscale)
		{
			return new WsAnagrafe().ByCodiceFiscale(authenticationToken, codiceFiscale);
		}

		[WebMethod]
		public Anagrafe GetAnagrafeByCodiceFiscaleETipoPersona(string authenticationToken, TipoPersona tipoPersona, string codiceFiscale)
		{
			return new WsAnagrafe().ByCodiceFiscale(authenticationToken, tipoPersona, codiceFiscale);
		}


		[WebMethod]
		public Anagrafe GetAnagrafeByPartitaIva(string authenticationToken, string partitaIva)
		{
			return new WsAnagrafe().ByPartitaIva(authenticationToken, partitaIva);
		}
		*/
		//[WebMethod]
		//public Anagrafe CreaAnagrafe(string authenticationToken, Anagrafe anagrafe)
		//{
		//    return new WsAnagrafe().CreaAnagrafe(authenticationToken, anagrafe);
		//}
	}
}
