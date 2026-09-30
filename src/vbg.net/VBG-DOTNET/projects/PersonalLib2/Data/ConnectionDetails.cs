using System;

namespace PersonalLib2.Data
{
    /// <summary>
    /// Espone i dettagli della connessione come il tipo di provider e la connectionstring e le funzioni specifiche del provider.
    /// </summary>
    public class ConnectionDetails
    {
        private string m_token = null;

        public string ConnectionString { get; }

        public ProviderType ProviderType { get; }

        public string Token
        {
            get { return this.m_token == null ? String.Empty : this.m_token; }
            set { this.m_token = value; }
        }

        internal ConnectionDetails(string connectionString, ProviderType providertype)
        {
            this.ConnectionString = connectionString;
            this.ProviderType = providertype;
        }
    }
}