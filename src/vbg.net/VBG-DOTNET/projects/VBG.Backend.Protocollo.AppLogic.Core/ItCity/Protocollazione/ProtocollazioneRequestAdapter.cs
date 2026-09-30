using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Protocollazione
{
    public class ProtocollazioneRequestAdapter
    {
        ParametriRegoleInfo _parametri;

        public ProtocollazioneRequestAdapter(ParametriRegoleInfo parametri)
        {
            this._parametri = parametri;
        }

        public IProtocollazioneRequest Adatta(IDatiProtocollo datiProtocollo, IEnumerable<IAnagraficaAmministrazione> anagrafiche, int idFascicolo, int? numeroSottoFascicolo)
        {
            return ProtocollazioneRequestFactory.Create(this._parametri, datiProtocollo, anagrafiche, idFascicolo, numeroSottoFascicolo);
        }

        //lato ITCity alcune volte deve essere passato ID della classifica e altre il CODICE ( VIII.2.1 ) per poter poi ricavare Titolario, Classe e Sottoclasse
        //questa implementazione permette di sostituire la classifica che arriva dalla chiamata al WS con quanto serve allo specifico metodo
        public IProtocollazioneRequest Adatta(IDatiProtocollo datiProtocollo, IEnumerable<IAnagraficaAmministrazione> anagrafiche, int idFascicolo, int? numeroSottoFascicolo, string classifica)
        {
            datiProtocollo.ProtoIn.Classifica = classifica;
            return ProtocollazioneRequestFactory.Create(this._parametri, datiProtocollo, anagrafiche, idFascicolo, numeroSottoFascicolo);
        }
    }
}
