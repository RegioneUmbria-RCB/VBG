using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloFoliumService;

namespace Init.SIGePro.Protocollo.Folium.Protocollazione
{
    public class FoliumProtocollazioneInputAdapter
    {
        public readonly DocumentoProtocollato Request;

        public FoliumProtocollazioneInputAdapter(FoliumProtocollazioneInputConfiguration conf)
        {
            Request = CreaRequest(conf);
        }

        private DocumentoProtocollato CreaRequest(FoliumProtocollazioneInputConfiguration conf)
        {
            var request = new DocumentoProtocollato
            {
                mittentiDestinatari = conf.MittenteDestinatario,
                oggetto = conf.Oggetto,
                registro = conf.Registro,
                tipoProtocollo = conf.Flusso,
                ufficioCompetente = conf.UfficioCompetente,
                vociTitolario = conf.VociTitolario/*,
                isContenuto = conf.IsContenuto*/
            };

            /*if (conf.IsContenuto)
            {
                request.contenuto = conf.Contenuto;
                request.nomeFileContenuto = conf.NomeFileContenuto;
            }*/

            return request;
        }
    }
}
