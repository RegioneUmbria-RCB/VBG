
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Folium.Allegati
{
    public class AllegatiInputAdapter
    {
        IEnumerable<ProtocolloAllegati> _allegati;
        long? _idProtocollo;

        public AllegatiInputAdapter(IEnumerable<ProtocolloAllegati> allegati, long? idProtocollo)
        {
            _allegati = allegati;
            _idProtocollo = idProtocollo;
        }

        public IEnumerable<ProtocolloFoliumService.Allegato> Adatta()
        {
            try
            {
                return _allegati.Select(x => new ProtocolloFoliumService.Allegato
                {
                    nomeFile = x.NOMEFILE,
                    idProfilo = _idProtocollo,
                    descrizione = x.Descrizione,
                    contenuto = x.OGGETTO
                });

            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA MAPPATURA DEGLI ALLEGATI, {0}", ex.Message), ex);
            }
        }
    }
}
