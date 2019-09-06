using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Segnatura;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.MittentiDestinatari
{
    public class MezzoValidator
    {
        string _mezzo;

        public MezzoValidator(string mezzo)
        {
            _mezzo = mezzo;
        }

        public void Validate()
        {
            if (!String.IsNullOrEmpty(_mezzo) && Enum.GetNames(typeof(ModalitaInvio)).Contains(_mezzo))
                throw new Exception("IL MEZZO SPECIFICATO NON E' COMPRESO NEI VALORI PREDEFINITI DAL SISTEMA DI PROTOCOLLO, CHE SONO: Cartaceo, PEC, Interoperabile, InterPRO");
        }

    }
}
