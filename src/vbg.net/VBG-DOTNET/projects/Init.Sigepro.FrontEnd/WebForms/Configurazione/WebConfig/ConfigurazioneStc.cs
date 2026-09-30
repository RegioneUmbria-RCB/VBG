using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using System;
using System.Configuration;
using System.Text.RegularExpressions;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Configurazione.WebConfig
{
    public class ConfigurazioneStc : ConfigurationSection, IConfigurazioneStc
    {
        [ConfigurationProperty("idComune", DefaultValue = "default", IsRequired = true, IsKey = true)]
        public string IdComune
        {
            get { return (string)this["idComune"]; }
            set { this["idComune"] = value; }
        }

        [ConfigurationProperty("urlInvio", IsRequired = true)]
        public string UrlInvio
        {
            get { return (string)this["urlInvio"]; }
            set { this["urlInvio"] = value; }
        }

        [ConfigurationProperty("idNodoMittente", IsRequired = true)]
        public string IdNodoMittente
        {
            get { return (string)this["idNodoMittente"]; }
            set { this["idNodoMittente"] = value; }
        }

        [ConfigurationProperty("idEnteMittente", IsRequired = true)]
        public string IdEnteMittente
        {
            get { return (string)this["idEnteMittente"]; }
            set { this["idEnteMittente"] = value; }
        }

        [ConfigurationProperty("idSportelloMittente", IsRequired = true)]
        public string IdSportelloMittente
        {
            get { return (string)this["idSportelloMittente"]; }
            set { this["idSportelloMittente"] = value; }
        }

        [ConfigurationProperty("idNodoDestinatario", IsRequired = true)]
        public string IdNodoDestinatario
        {
            get { return (string)this["idNodoDestinatario"]; }
            set { this["idNodoDestinatario"] = value; }
        }

        [ConfigurationProperty("idEnteDestinatario", IsRequired = true)]
        public string IdEnteDestinatario
        {
            get { return (string)this["idEnteDestinatario"]; }
            set { this["idEnteDestinatario"] = value; }
        }

        [ConfigurationProperty("idSportelloDestinatario", IsRequired = true)]
        public string IdSportelloDestinatario
        {
            get { return (string)this["idSportelloDestinatario"]; }
            set { this["idSportelloDestinatario"] = value; }
        }



        [ConfigurationProperty("username", IsRequired = true)]
        public string Username
        {
            get { return (string)this["username"]; }
            set { this["username"] = value; }
        }

        [ConfigurationProperty("password", IsRequired = true)]
        public string Password
        {
            get { return (string)this["password"]; }
            set { this["password"] = value; }
        }

        [ConfigurationProperty("specializzazioni", IsRequired = false)]
        public ConfigurazioneSpecializzazioneStcCollection Specializzazioni
        {
            get
            {
                return this["specializzazioni"] as ConfigurazioneSpecializzazioneStcCollection;
            }
        }

        public IConfigurazioneStc GetSpecializzazionePerSoftware(IAliasSoftwareResolver aliasSoftwareResolver)
        {
            var specializzazioni = this.Specializzazioni[aliasSoftwareResolver.Software];

            var rVal = new ConfigurazioneStc
            {
                IdComune = this.IdComune,

                IdNodoMittente = this.IdNodoMittente,
                IdEnteMittente = this.IdEnteMittente,
                IdSportelloMittente = this.IdSportelloMittente,

                IdNodoDestinatario = this.IdNodoDestinatario,
                IdEnteDestinatario = this.IdEnteDestinatario,
                IdSportelloDestinatario = this.IdSportelloDestinatario,

                UrlInvio = this.UrlInvio,

                Username = this.Username,
                Password = this.Password
            };

            if (specializzazioni != null)
            {
                // dati del nodo mittente
                if (!String.IsNullOrEmpty(specializzazioni.IdNodoMittente))
                    rVal.IdNodoMittente = specializzazioni.IdNodoMittente;

                if (!String.IsNullOrEmpty(specializzazioni.IdEnteMittente))
                    rVal.IdEnteMittente = specializzazioni.IdEnteMittente;

                if (!String.IsNullOrEmpty(specializzazioni.IdSportelloMittente))
                    rVal.IdSportelloMittente = specializzazioni.IdSportelloMittente;

                // dati del nodo destinatario
                if (!String.IsNullOrEmpty(specializzazioni.IdNodoDestinatario))
                    rVal.IdNodoDestinatario = specializzazioni.IdNodoDestinatario;

                if (!String.IsNullOrEmpty(specializzazioni.IdEnteDestinatario))
                    rVal.IdEnteDestinatario = specializzazioni.IdEnteDestinatario;

                if (!String.IsNullOrEmpty(specializzazioni.IdSportelloDestinatario))
                    rVal.IdSportelloDestinatario = specializzazioni.IdSportelloDestinatario;


                // Username e password
                if (!String.IsNullOrEmpty(specializzazioni.Username))
                    rVal.Username = specializzazioni.Username;

                if (!String.IsNullOrEmpty(specializzazioni.Password))
                    rVal.Password = specializzazioni.Password;
            }

            rVal.IdNodoMittente = this.ReplaceIdComuneSoftware(aliasSoftwareResolver, rVal.IdNodoMittente);
            rVal.IdEnteMittente = this.ReplaceIdComuneSoftware(aliasSoftwareResolver, rVal.IdEnteMittente);
            rVal.IdSportelloMittente = this.ReplaceIdComuneSoftware(aliasSoftwareResolver, rVal.IdSportelloMittente);

            rVal.IdNodoDestinatario = this.ReplaceIdComuneSoftware(aliasSoftwareResolver, rVal.IdNodoDestinatario);
            rVal.IdEnteDestinatario = this.ReplaceIdComuneSoftware(aliasSoftwareResolver, rVal.IdEnteDestinatario);
            rVal.IdSportelloDestinatario = this.ReplaceIdComuneSoftware(aliasSoftwareResolver, rVal.IdSportelloDestinatario);

            rVal.Username = this.ReplaceIdComuneSoftware(aliasSoftwareResolver, rVal.Username);
            rVal.Password = this.ReplaceIdComuneSoftware(aliasSoftwareResolver, rVal.Password);

            return rVal;
        }

        private string ReplaceIdComuneSoftware(IAliasSoftwareResolver aliasSoftwareResolver, string parametro)
        {
            string idComune = aliasSoftwareResolver.AliasComune;
            string software = aliasSoftwareResolver.Software;

            var str = Regex.Replace(parametro, "\\{[Ii][Dd][Cc][Oo][Mm][Uu][Nn][Ee]\\}", idComune);
            str = Regex.Replace(str, "\\{[Ss][Oo][Ff][Tt][Ww][Aa][Rr][Ee]\\}", software);

            return str;
        }
    }
}
