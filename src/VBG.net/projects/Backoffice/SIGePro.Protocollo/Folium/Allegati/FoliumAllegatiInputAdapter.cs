using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Folium.Allegati
{
    public class FoliumAllegatiInputAdapter
    {
        public readonly List<ProtocolloFoliumService.Allegato> ListAllegati;
        List<ProtocolloAllegati> _allegati;
        long? _idProtocollo;

        public FoliumAllegatiInputAdapter(List<ProtocolloAllegati> allegati, long? idProtocollo)
        {
            _allegati = allegati;
            _idProtocollo = idProtocollo;

            ListAllegati = CreaAllegati();
        }

        private List<ProtocolloFoliumService.Allegato> CreaAllegati()
        {
            try
            {
                var retVal = new List<ProtocolloFoliumService.Allegato>();

                _allegati.ToList().ForEach(x => retVal.Add(new ProtocolloFoliumService.Allegato
                {
                    nomeFile = x.NOMEFILE,
                    idProfilo = _idProtocollo,
                    descrizione = x.Descrizione,
                    contenuto = x.OGGETTO
                }));

                return retVal;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA MAPPATURA DEGLI ALLEGATI", ex);
            }
        }
    }
}
