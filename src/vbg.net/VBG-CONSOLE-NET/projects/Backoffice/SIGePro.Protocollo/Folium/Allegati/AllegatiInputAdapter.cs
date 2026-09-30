using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Folium.Allegati
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
