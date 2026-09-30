using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Request;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Anagrafiche
{
    public interface IPersonaFisicaGiuridica
    {
        Firm GetFirm();
        EsibDest GetEsibDest();
    }
}
