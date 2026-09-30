using Init.SIGePro.Verticalizzazioni;
using PersonalLib2.Data;
using System;

namespace Init.SIGePro.Sit.Ravenna2
{
    public class VerticalizzazioneSitRavenna2 : Verticalizzazione
    {
        public static class Constants
        {
            public const string NomeVerticalizzazione = "SIT_RAVENNA2";
            public const string ConnectionString = "CONNECTION_STRING";
            public const string PrefissoTabelle = "PREFISSO_TABELLE";
            public const string UrlCartografiaDaCivico = "URL_CARTOGRAFIA_DA_CIVICO";
            public const string UrlCartografiaDaMappale = "URL_CARTOGRAFIA_DA_MAPPALE";
            public const string DatabaseClientType = "DATABASE_CLIENT_TYPE";
            public const string UrlWsVbgRA012 = "URL_WS_VBGRA012";
            public const string UrlWsVbgParticelleCatastali = "URL_WS_VBGPARTICELLECATASTALI";
            public const string UrlWsVbgRA147 = "URL_WS_VBGRA147";
        }

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;

        public VerticalizzazioneSitRavenna2()
            : base()
        {
        }

        public VerticalizzazioneSitRavenna2(bool attiva)
            : base()
        {
            base.Attiva = attiva;
        }

        public VerticalizzazioneSitRavenna2(string idComuneAlias, string software)
            : base(idComuneAlias, Constants.NomeVerticalizzazione, software)
        {
        }

        public string ConnectionString
        {
            get { return this.GetString(Constants.ConnectionString); }
            set { this.SetString(Constants.ConnectionString, value); }
        }

        public string PrefissoTabelle
        {
            get { return this.GetString(Constants.PrefissoTabelle); }
            set { this.SetString(Constants.PrefissoTabelle, value); }
        }

        public string UrlCartografiaDaCivico
        {
            get { return this.GetString(Constants.UrlCartografiaDaCivico); }
            set { this.SetString(Constants.UrlCartografiaDaCivico, value); }
        }

        public string UrlCartografiaDaMappale
        {
            get { return this.GetString(Constants.UrlCartografiaDaMappale); }
            set { this.SetString(Constants.UrlCartografiaDaMappale, value); }
        }

        public string UrlWsVbgRA012
        {
            get { return this.GetString(Constants.UrlWsVbgRA012); }
            set { this.SetString(Constants.UrlWsVbgRA012, value); }
        }

        public string UrlWsVbgParticelleCatastali
        {
            get { return this.GetString(Constants.UrlWsVbgParticelleCatastali); }
            set { this.SetString(Constants.UrlWsVbgParticelleCatastali, value); }
        }

        public string UrlWsVbgRA147
        {
            get { return this.GetString(Constants.UrlWsVbgRA147); }
            set { this.SetString(Constants.UrlWsVbgRA147, value); }
        }

        public string DatabaseClientTypeString
        {
            get => this.GetString(Constants.DatabaseClientType);
            set => this.SetString(Constants.DatabaseClientType, value);
        }

        public ProviderType DatabaseClientType
        {
            get
            {
                var client = this.DatabaseClientTypeString;

                if (string.IsNullOrEmpty(client))
                {
                    return ProviderType.OracleClient;
                }

                return (ProviderType)Enum.Parse(typeof(ProviderType), client);
            }
        }
    }
}
