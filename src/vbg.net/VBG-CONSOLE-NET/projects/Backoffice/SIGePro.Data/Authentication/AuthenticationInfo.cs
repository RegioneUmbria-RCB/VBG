using System;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Authentication
{

    /// <summary>
    /// Informazioni sull'autenticazione di un utente.
    /// </summary>
    [Serializable]
    public class AuthenticationInfo : BaseDataClass
    {
        #region 
        public int ErrorCode { get; set; }
        public string ErrorMessage { get; set; }
        #endregion

        
        public AuthenticationInfo()
        {
        }

        /// <summary>
        /// Il token (univoco in tutto SIGePro) che identifica la sessione
        /// </summary>
        public string Token { get; set; }

        /// <summary>
        /// Campo IdComune da utilizzare per le query
        /// </summary>
        public string IdComune { get; set; }

        /// <summary>
        /// Data dell'ultima richiesta effettuata
        /// </summary>
        public DateTime? LastRequest { get; set; }

        /// <summary>
        /// Connection string da utilizzare per il db
        /// </summary>
        public string ConnectionString { get; set; }
        
        /// <summary>
        /// Codice del responsabile
        /// </summary>
        public int? CodiceResponsabile { get; set; }

        /// <summary>
        /// Provider da utilizzare per il db
        /// </summary>
        public string Provider { get; set; }

        /// <summary>
        /// Ambiente
        /// </summary>
        public string Ambiente { get; set; }

        /// <summary>
        /// User per il db
        /// </summary>
        public string DBUser { get; set; }

        /// <summary>
        /// Password per il db
        /// </summary>
        public string DBPassword { get; set; }

        /// <summary>
        /// Owner per il db
        /// </summary>
        public string DBOwner { get; set; }

        /// <summary>
        /// DBMSName per il db
        /// </summary>
        public string DBMSName { get; set; }

        /// <summary>
        /// Software attivi per il BO
        /// </summary>
        public string SoftwareAttivi { get; set; }

        /// <summary>
        /// Software attivi per il FO
        /// </summary>
        public string SoftwareAttiviFO { get; set; }

        public string Alias { get; set; }

		public ContestoTokenEnum Contesto { get; set; }

        public DataBase CreateDatabase()
        {
            //if (m_database == null)
            //{
                ProviderType initialProviderType = (ProviderType)Enum.Parse(typeof(ProviderType), this.Provider, true);

                string connectionString = this.ConnectionString;

                if (!connectionString.EndsWith(";"))
                    connectionString += ";";

                connectionString += "User Id=" + this.DBUser + ";Password=" + this.DBPassword;

                var rVal = new DataBase(connectionString, initialProviderType);
                rVal.ConnectionDetails.Token = this.Token;

				return rVal;
				//m_database.Disposed += (sender, e) => { m_database = null; };
            //}
            //return m_database;
        }

        /// <summary>
        /// Verifica se un utente è un amministratore globale di Sigepro
        /// </summary>
        /// <remarks>
        /// Attualmente verifica solamente che l'utente non abbia un codice responsabile.
        /// Non è il controllo migliore ma finora è l'unico caso possibile in cui il campo è vuoto
        /// </remarks>
        public bool IsAdmin
        {
            get
            {
                return !String.IsNullOrEmpty(Token) && CodiceResponsabile.GetValueOrDefault(int.MinValue) == int.MinValue;
            }
        }
    }

}