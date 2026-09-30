
using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Legacy
{
    public class VerticalizzazioneProtocolloMicrosis : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_MICROSIS";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloMicrosis()
        {
            
        }

        public VerticalizzazioneProtocolloMicrosis(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune)
        {

        }

        /// <summary>
        /// Indicare in questo parametro la url relativa all'endpoit del web service di protocollazione MICROSIS.
        /// </summary>
        public string Url
        {
            get { return this.GetString("URL"); }
            set { this.SetString("URL", value); }
        }

        /// <summary>
        /// Indicare in questo parametro lo username facente parte delle credenziali di accesso per creare un protocollo, più in generale per poter utilizzare i metodi esposti dal web service MICROSIS.
        /// </summary>
        public string Username
        {
            get { return this.GetString("USERNAME"); }
            set { this.SetString("USERNAME", value); }
        }

        /// <summary>
        /// Indicare in questo parametro la password relativa all'utente specificato nel parametro USERNAME facente parte delle credenziali di accesso per creare un protocollo, più in generale per poter utilizzare i metodi esposti dal web service MICROSIS.
        /// </summary>
        public string Password
        {
            get { return this.GetString("PASSWORD"); }
            set { this.SetString("PASSWORD", value); }
        }

    }
}
