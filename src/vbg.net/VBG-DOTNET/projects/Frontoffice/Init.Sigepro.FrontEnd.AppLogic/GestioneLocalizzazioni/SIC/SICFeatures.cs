using Init.Sigepro.FrontEnd.AppLogic.ConnectedServices.SIC;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC
{
    public class SICFeatures
    {
        public bool MostraMappaListaIstanze { get; }
        public bool MostraMappaPresentazioneIstanza { get; }
        public bool MostraMappaDettaglioIstanza { get; }

        internal SICFeatures(Utilizzo utilizzo)
        {
            this.MostraMappaListaIstanze = utilizzo.Istanze?.Elenco ?? false;
            this.MostraMappaPresentazioneIstanza = utilizzo.Istanze?.Inserimento ?? false;
            this.MostraMappaDettaglioIstanza = utilizzo.Istanze?.Elenco ?? false;
        }
    }


}
