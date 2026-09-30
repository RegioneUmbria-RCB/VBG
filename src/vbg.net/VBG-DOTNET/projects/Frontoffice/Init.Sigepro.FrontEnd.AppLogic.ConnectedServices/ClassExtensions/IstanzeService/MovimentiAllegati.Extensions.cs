using System;

namespace Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService
{
    public partial class MovimentiAllegati : IDocumentoIstanzaOggettoDiVerifica
    {
        public StatoVerificaDocumentoEnum EsitoVerifica
        {
            get
            {
                switch (this.ControlloOK.GetValueOrDefault(-1))
                {
                    case 0:
                        return StatoVerificaDocumentoEnum.NonValido;

                    case 1:
                        return StatoVerificaDocumentoEnum.Valido;
                }

                return StatoVerificaDocumentoEnum.DaVerificare;
            }
        }

        public bool ContieneOggetto => !String.IsNullOrEmpty(this.CODICEOGGETTO);

        public bool ContieneDatiSensibili => this.Oggetto?.ContieneDatiSensibili ?? false;

        public string CodiceOggetto => this.CODICEOGGETTO;
    }
}
