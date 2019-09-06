using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriStc : IParametriConfigurazione
	{
		public readonly string UrlInvio;
		public readonly string Username;
		public readonly string Password;
		public readonly NodoStc NodoMittente;
		public readonly NodoStc NodoDestinatario;
        public readonly bool IncludiTecnicoInSoggettiCollegati;

		internal ParametriStc(string urlinvio, string username, string password , NodoStc nodoMittente , NodoStc nodoDestinatario, bool includiTecnicoInSoggettiCollegati)
		{
			if (String.IsNullOrEmpty(urlinvio))
				throw new ArgumentNullException("urlInvio");

			if (String.IsNullOrEmpty(username))
				throw new ArgumentNullException("username");

			if (String.IsNullOrEmpty(password))
				throw new ArgumentNullException("password");

			if (nodoMittente == null)
				throw new ArgumentNullException("nodoMittente");

			if (nodoDestinatario == null)
				throw new ArgumentNullException("nodoDestinatario");

			this.UrlInvio = urlinvio;
			this.Username = username;
			this.Password = password;
			this.NodoMittente = nodoMittente;
			this.NodoDestinatario = nodoDestinatario;
            this.IncludiTecnicoInSoggettiCollegati = includiTecnicoInSoggettiCollegati;
		}
	}
}
