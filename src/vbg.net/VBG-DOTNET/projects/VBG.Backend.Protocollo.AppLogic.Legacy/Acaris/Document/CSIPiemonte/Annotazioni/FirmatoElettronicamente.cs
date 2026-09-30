namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Document.CSIPiemonte.Annotazioni
{
    public class FirmatoElettronicamente : IAnnotazione
    {
        private readonly string _firmatario;

        public FirmatoElettronicamente(string firmatario)
        {
            this._firmatario = firmatario;
        }

        public Annotazione ToAnnotazione()
        {
            return new Annotazione
            {
                Testo = $"Firmato elettronicamente tramite autenticazione SPID/CIE/CNS da {this._firmatario}",
                Formale = true,
                AnnotaInteroDocumento = true,
                AnnotaClassificazioneCorrente = false
            };
        }
    }
}
