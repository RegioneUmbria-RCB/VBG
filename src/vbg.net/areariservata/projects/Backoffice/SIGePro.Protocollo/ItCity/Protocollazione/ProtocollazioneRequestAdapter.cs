using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloItCityService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity.Protocollazione
{
    public class ProtocollazioneRequestAdapter
    {
        public ProtocollazioneRequestAdapter()
        {

        }

        public IProtocollazioneRequest Adatta(IDatiProtocollo datiProtocollo, IEnumerable<IAnagraficaAmministrazione> anagrafiche, int idFascicolo, int? numeroSottoFascicolo)
        {
            return ProtocollazioneRequestFactory.Create(datiProtocollo, anagrafiche, idFascicolo, numeroSottoFascicolo);
        }

        //lato ITCity alcune volte deve essere passato ID della classifica e altre il CODICE ( VIII.2.1 ) per poter poi ricavare Titolario, Classe e Sottoclasse
        //questa implementazione permette di sostituire la classifica che arriva dalla chiamata al WS con quanto serve allo specifico metodo
        public IProtocollazioneRequest Adatta(IDatiProtocollo datiProtocollo, IEnumerable<IAnagraficaAmministrazione> anagrafiche, int idFascicolo, int? numeroSottoFascicolo, string classifica)
        {
            datiProtocollo.ProtoIn.Classifica = classifica;
            return ProtocollazioneRequestFactory.Create(datiProtocollo, anagrafiche, idFascicolo, numeroSottoFascicolo);
        }
    }
}
