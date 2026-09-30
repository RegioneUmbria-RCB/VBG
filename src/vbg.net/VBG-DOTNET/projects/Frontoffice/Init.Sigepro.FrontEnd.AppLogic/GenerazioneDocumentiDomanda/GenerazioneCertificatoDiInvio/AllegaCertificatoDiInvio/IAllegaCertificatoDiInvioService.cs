using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.AllegaCertificatoDiInvio
{
    public interface IAllegaCertificatoDiInvioService
    {
        void AllegaSeNonEsiste(int idDomandaBackoffice, BinaryFile certificatoDiInvio);
    }
}
