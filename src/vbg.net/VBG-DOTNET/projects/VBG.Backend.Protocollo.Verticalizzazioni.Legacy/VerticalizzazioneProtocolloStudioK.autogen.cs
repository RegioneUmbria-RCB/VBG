
using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Legacy
{
    public partial class VerticalizzazioneProtocolloStudioK : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_STUDIOK";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloStudioK()
        {

        }

        public VerticalizzazioneProtocolloStudioK(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune)
        {

        }

        public string ConnectionString
        {
            get { return this.GetString("CONNECTIONSTRING"); }
            set { this.SetString("CONNECTIONSTRING", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string Url
        {
            get { return this.GetString("URL"); }
            set { this.SetString("URL", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string CodiceAmministrazione
        {
            get { return this.GetString("CODICEAMMINISTRAZIONE"); }
            set { this.SetString("CODICEAMMINISTRAZIONE", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string CodiceAoo
        {
            get { return this.GetString("CODICEAOO"); }
            set { this.SetString("CODICEAOO", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string InvioPec
        {
            get { return this.GetString("INVIOPEC"); }
            set { this.SetString("INVIOPEC", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string DenominazioneEnte
        {
            get { return this.GetString("DENOMINAZIONE_ENTE"); }
            set { this.SetString("DENOMINAZIONE_ENTE", value); }
        }

        /// <summary>
        /// 
        /// </summary>
        public string AssegnatoDa
        {
            get { return this.GetString("ASSEGNATO_DA"); }
            set { this.SetString("ASSEGNATO_DA", value); }
        }

    }
}
