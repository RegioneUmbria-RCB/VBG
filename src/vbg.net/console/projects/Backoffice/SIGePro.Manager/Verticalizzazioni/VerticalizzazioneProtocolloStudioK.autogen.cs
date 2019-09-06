using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public partial class VerticalizzazioneProtocolloStudioK : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_STUDIOK";

        public VerticalizzazioneProtocolloStudioK()
        {
                
        }

        public VerticalizzazioneProtocolloStudioK(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune)
        {

        }

        public string ConnectionString
        {
            get { return GetString("CONNECTIONSTRING"); }
            set { SetString("CONNECTIONSTRING", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string Url
        {
            get { return GetString("URL"); }
            set { SetString("URL", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string CodiceAmministrazione
        {
            get { return GetString("CODICEAMMINISTRAZIONE"); }
            set { SetString("CODICEAMMINISTRAZIONE", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string CodiceAoo
        {
            get { return GetString("CODICEAOO"); }
            set { SetString("CODICEAOO", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string InvioPec
        {
            get { return GetString("INVIOPEC"); }
            set { SetString("INVIOPEC", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string DenominazioneEnte
        {
            get { return GetString("DENOMINAZIONE_ENTE"); }
            set { SetString("DENOMINAZIONE_ENTE", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string AssegnatoDa
        {
            get { return GetString("ASSEGNATO_DA"); }
            set { SetString("ASSEGNATO_DA", value); }
        }

    }
}
