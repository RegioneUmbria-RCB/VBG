using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriFvgSol : IParametriConfigurazione
    {
        public readonly string ServiceUrl;
        public readonly string Username;
        public readonly string Password;
        public readonly bool VerticalizzazioneAttiva;

        private ParametriFvgSol(bool verticalizzazioneAttiva, string serviceUrl = "", string username = "", string password = "")
        {
            this.VerticalizzazioneAttiva = verticalizzazioneAttiva;
            this.ServiceUrl = serviceUrl;
            this.Username = username;
            this.Password = password;
        }

        internal ParametriFvgSol(string serviceUrl, string username, string password)
            :this(true, serviceUrl, username, password)
        {
            
        }

        public static ParametriFvgSol NonAttiva { get => new ParametriFvgSol(false); }
    }
}
