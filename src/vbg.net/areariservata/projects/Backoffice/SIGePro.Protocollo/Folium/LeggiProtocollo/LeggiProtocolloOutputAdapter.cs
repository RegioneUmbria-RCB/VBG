using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloFoliumService;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Folium.Allegati;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Folium.ServiceWrapper;

namespace Init.SIGePro.Protocollo.Folium.LeggiProtocollo
{
    public class LeggiProtocolloOutputAdapter
    {
        ProtocollazioneServiceWrapper _wsWrapper;


        public LeggiProtocolloOutputAdapter(ProtocollazioneServiceWrapper wsWrapper)
        {
            _wsWrapper = wsWrapper;
        }

        public DatiProtocolloLetto Adatta(Ricerca request)
        {
            var response = _wsWrapper.LeggiProtocollo(request);

            var retVal = new DatiProtocolloLetto();

            retVal.NumeroProtocollo = response.numeroProtocollo;
            retVal.AnnoProtocollo = response.dataProtocollo.Value.ToString("yyyy");
            retVal.DataProtocollo = response.dataProtocollo.Value.ToString("dd/MM/yyyy");
            retVal.Origine = Flusso.FlussoAdapter.FromWsToVbg(response.tipoProtocollo);
            retVal.Classifica = response.vociTitolario != null ? String.Join(",", response.vociTitolario) : String.Empty;
            retVal.InCaricoA_Descrizione = response.ufficioCompetente;
            retVal.MittentiDestinatari = GetMittentiDestintari(response);
            retVal.Oggetto = response.oggetto;
            retVal.IdProtocollo = response.id.GetValueOrDefault(-1).ToString();
            var allegati = GetAllegati(response);
            retVal.Allegati = allegati;
            retVal.Classifica_Descrizione = response.vociTitolario != null && response.vociTitolario.Length > 0 ? String.Join(" ", response.vociTitolario) : String.Empty;

            return retVal;
        }

        private AllOut[] GetAllegati(DocumentoProtocollato response)
        {
            var idProtocollo = response.id.Value;

            var allegatoPrincipale = new AllegatoPrincipaleInputAdapter(idProtocollo, response.nomeFileContenuto);
            var allegatiSecondari = new AltriAllegatiOutputAdapter(_wsWrapper, idProtocollo);

            var adapterAllegatiOutput = new AllegatiProtocolloOutputAdapter(new IAllegatiAdapter[] { allegatoPrincipale, allegatiSecondari });
            return adapterAllegatiOutput.Adatta().ToArray();
        }

        private MittDestOut[] GetMittentiDestintari(DocumentoProtocollato response)
        {
            var list = new List<MittDestOut>();

            if (response.mittentiDestinatari != null)
            {
                response.mittentiDestinatari.ToList().ForEach(x => list.Add(new MittDestOut
                {
                    CognomeNome = String.IsNullOrEmpty(x.denominazione) ? $"{x.nome} {x.cognome} ({x.codiceMezzoSpedizione})" : $"{x.denominazione} ({x.codiceMezzoSpedizione})"
                }));
            }

            return list.ToArray();

        }
    }
}
