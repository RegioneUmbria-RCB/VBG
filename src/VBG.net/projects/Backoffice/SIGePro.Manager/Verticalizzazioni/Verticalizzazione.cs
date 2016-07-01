using System;
using System.Collections;
using System.Collections.Specialized;
using System.Data;
using System.Xml.Serialization;
using Init.SIGePro.Data;
using Init.Utils;
using PersonalLib2.Data;
using Init.SIGePro.Manager.Properties;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Authentication;
using System.IO;
using Init.SIGePro.Manager.RegoleService;
using System.ServiceModel;
using System.Linq;
using System.Collections.Generic;

namespace Init.SIGePro.Verticalizzazioni
{
	/// <summary>
	/// Rappresenta una verticalizzazione del sistema SIGePro
	/// </summary>
	public class Verticalizzazione
	{
		/// <summary>
		/// True se la verticalizzazione è attiva
		/// </summary>
        [XmlIgnore]
        public bool Attiva { get; protected set; }
		/// <summary>
		/// Hashtable di coppie chiave / valore di tutti i parametri della verticalizzazione
		/// <code>string mioValore = Varticalizzazione.Parametri["NOMEPARAMETRO"]</code>
		/// </summary>
        [XmlIgnore]
        public Dictionary<string, string> Parametri { get; private set; }

        string _nomeModulo;
        string _idComuneAlias;
        string _software;
        string _codiceComune;
		/// <summary>
		/// Istanzia una nuova verticalizzazione. Utilizzando il codice software passato
		/// </summary>
		public Verticalizzazione(string idComuneAlias , string nome, string software = "TT", string codiceComune = "")
		{
            _software = String.IsNullOrEmpty(software) ? "TT" : software.ToUpper();
			_nomeModulo	= nome;
			_idComuneAlias	= idComuneAlias;
            _codiceComune = codiceComune;

			Read();
		}

        public Verticalizzazione()
        {
			Parametri = new Dictionary<string, string>();
        }

        private void Read()
        {
            var authInfo = AuthenticationManager.Login(_idComuneAlias, Settings.Default.SigeproSecurityUsername, Settings.Default.SigeproSecurityPassword, ContextType.ExternalUsers);

            if (authInfo == null)
                throw new Exception(String.Format("AUTHENTICATION INFO NON VALORIZZATO, IdComuneAlias: {0}", _idComuneAlias));
            
            var urlBackend = AuthenticationManager.GetApplicationInfoValue("WSHOSTURL_JAVA");
            var wsUrl = urlBackend + "/services/regole?wsdl";

            using (var ws = CreaWebService(wsUrl))
            {
                var response = ws.GetRegola(new RegolaRequest
                {
                    codiceComune = _codiceComune,
                    software = _software,
                    token = authInfo.Token,
                    nomeRegola = _nomeModulo,
                    recuperaParametri = true,
                    recuperaParametriSpecified = true
                });

                if (response == null)
                    throw new Exception("LA RISPOSTA DELLA RICERCA DELLA VERTICALIZZAZIONE RISULTA ESSERE NULL");

                Attiva = response.attiva;

                if (response.listaParametri == null)
                    Parametri = new Dictionary<string, string>();
                else
                    Parametri = response.listaParametri.ToDictionary(x => x.descrizione.ToString(), y => y.valore);
            }
        }

        private RegoleClient CreaWebService(string wsUrl)
        {
            try
            {
                var endPointAddress = new EndpointAddress(wsUrl);
                var binding = new BasicHttpBinding("defaultHttpBinding");

                var ws = new RegoleClient(binding, endPointAddress);

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE DELLE VERTICALIZZAZIONI, {0}", ex.Message));
            }
        }

		/// <summary>
		/// Ottiene un valore bool da un parametro. Se il parametro è null o vuoto ritorna false
		/// </summary>
		/// <param name="paramName">Nome del parametro</param>
		/// <returns>Valore del parametro</returns>
		public bool GetBool(string paramName, bool defaultValue = false)
		{
			if (!Parametri.ContainsKey(paramName)) return defaultValue;
			if (Parametri[paramName] == null) return defaultValue;
			if (Parametri[paramName] == String.Empty) return defaultValue;

			return (Parametri[paramName] == "1");
		}

		/// <summary>
		/// Ottiene un valore string da un parametro. Se il parametro è null ritorna String.Empty
		/// </summary>
		/// <param name="paramName">Nome del parametro</param>
		/// <returns>Valore del parametro</returns>
		public string GetString(string paramName)
		{
			if (Parametri == null) return String.Empty;
            if (!Parametri.ContainsKey(paramName)) return String.Empty;
			if (Parametri[paramName] == null) return String.Empty;
			return Parametri[paramName];
		}

		public void SetString( string paramName , string value )
		{
			if (Parametri == null) return;
			Parametri[paramName] = value;
		}

		/// <summary>
		/// Ottiene un valore int da un parametro. Se il parametro è null ritorna 0
		/// </summary>
		/// <param name="paramName">Nome del parametro</param>
		/// <returns>Valore del parametro</returns>
		public int? GetInt(string paramName)
		{
            if (!Parametri.ContainsKey(paramName) || String.IsNullOrEmpty(Parametri[paramName]) )
            {
                return (int?)null;
            }
            
			return Convert.ToInt32(Parametri[paramName]);
		}


        /*private Data.Verticalizzazioni ReadCodiceComuneVuoto()
        {
            var verticalizzazioneSearch = new Data.Verticalizzazioni
            {
                IdComune = _idComune,
                Modulo = _nomeModulo,
                Software = _software
            };

            verticalizzazioneSearch.OthersWhereClause.Add("CODICECOMUNE is null");

            var vertResult = (Init.SIGePro.Data.Verticalizzazioni)_db.GetClass(verticalizzazioneSearch);
            if (vertResult == null && _software != "TT")
            {
                verticalizzazioneSearch.Software = "TT";
                return (Init.SIGePro.Data.Verticalizzazioni)_db.GetClass(verticalizzazioneSearch);
            }
            else
                return vertResult;
        }

        private Data.Verticalizzazioni ReadCodiceComuneValorizzato()
        {
            var verticalizzazioneSearch = new Data.Verticalizzazioni
            {
                IdComune = _idComune,
                Modulo = _nomeModulo,
                Software = _software,
                CodiceComune = _codiceComune
            };

            var vertResult = (Init.SIGePro.Data.Verticalizzazioni)_db.GetClass(verticalizzazioneSearch);

            if (vertResult != null)
                return vertResult;
            
            if (_software != "TT")
            {
                verticalizzazioneSearch.Software = "TT";
                vertResult = (Init.SIGePro.Data.Verticalizzazioni)_db.GetClass(verticalizzazioneSearch);
            }

            return vertResult != null ? vertResult : ReadCodiceComuneVuoto();
                
        }*/

		/*/// <summary>
		/// Legge i parametri della verticalizzazione
		/// </summary>
		private void Read()
		{
            var verticalizzazione = String.IsNullOrEmpty(_codiceComune) ? ReadCodiceComuneVuoto() : ReadCodiceComuneValorizzato();

            if (verticalizzazione == null || String.IsNullOrEmpty(verticalizzazione.Attivo))
            {
                Attiva = false;
                Parametri = null;

                return;
            }
            else
                Attiva = (Convert.ToInt32(verticalizzazione.Attivo) == 1);

			ValorizzaParametri(verticalizzazione.Software, verticalizzazione.CodiceComune);
		}

		private void ValorizzaParametri(string software, string codiceComune)
		{
            var filtriParametri = new VerticalizzazioniParametri
            {
                IdComune = _idComune,
                Modulo = _nomeModulo,
                Software = software
            };

            if (String.IsNullOrEmpty(codiceComune))
            {
                var arrFiltriParametriCodiceComune = new ArrayList();
                filtriParametri.OthersWhereClause.Add("CODICECOMUNE is null");
            }

            filtriParametri.CodiceComune = codiceComune;

			var listaParametri = _db.GetClassList(filtriParametri);
            
			foreach(VerticalizzazioniParametri parametro in listaParametri)
                Parametri[parametro.Parametro] = parametro.Valore;
		}*/
	}
}
