using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using System.Text.RegularExpressions;

namespace Init.Sigepro.FrontEnd.CoreServices.Configurazione
{
    public class ConfigurazioneStc : IConfigurazioneStc
    {
        public const string SectionName = "STC";

        public string? Software { get; set; }

        public string IdNodoMittente { get; set; } = "";

        public string IdEnteMittente { get; set; } = "";

        public string IdSportelloMittente { get; set; } = "";

        public string IdNodoDestinatario { get; set; } = "";

        public string IdEnteDestinatario { get; set; } = "";

        public string IdSportelloDestinatario { get; set; } = "";

        public string UrlInvio { get; set; } = "";

        public string Username { get; set; } = "";

        public string Password { get; set; } = "";

        public ConfigurazioneStc[] Specializzazioni { get; set; } = new ConfigurazioneStc[0];

        public IConfigurazioneStc GetSpecializzazionePerSoftware(IAliasSoftwareResolver aliasSoftwareResolver)
        {
            var specializzazioni = this.Specializzazioni.FirstOrDefault(x => x.Software == aliasSoftwareResolver.Software);

            var rVal = new ConfigurazioneStc
            {
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
