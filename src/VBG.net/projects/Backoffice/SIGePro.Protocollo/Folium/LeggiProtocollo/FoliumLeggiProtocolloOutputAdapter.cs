using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloFoliumService;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Folium.Services;
using Init.SIGePro.Protocollo.Folium.Allegati;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.Folium.LeggiProtocollo
{
    public class FoliumLeggiProtocolloOutputAdapter
    {
        FoliumProtocollazioneService _wsWrapper;


        public FoliumLeggiProtocolloOutputAdapter(FoliumProtocollazioneService wsWrapper)
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
            retVal.Origine = Flusso.FoliumFlussoAdapter.FromWsToVbg(response.tipoProtocollo);
            retVal.Classifica = response.vociTitolario != null ? String.Join(",", response.vociTitolario) : String.Empty;
            retVal.InCaricoA_Descrizione = response.ufficioCompetente;
            retVal.MittentiDestinatari = GetMittentiDestintari(response);
            retVal.Oggetto = response.oggetto;
            retVal.IdProtocollo = response.id.GetValueOrDefault(-1).ToString();
            retVal.Allegati = GetAllegato(response.id.Value);
            retVal.Classifica_Descrizione = response.vociTitolario != null && response.vociTitolario.Length > 0 ? String.Join(" ", response.vociTitolario) : String.Empty;

            return retVal;
        }

        private AllOut[] GetAllegato(long idProtocollo)
        {
            /*var allegatoPrincipale = new FoliumAllegatoOutputPrincipaleAdapter(_wsWrapper, idProtocollo);*/
            var allegatiSecondari = new FoliumAltriAllegatiOutputAdapter(_wsWrapper, idProtocollo);

            var adapterAllegatiOutput = new FoliumAllegatiProtocolloOutputAdapter(new IAllegatiAdapter[] { allegatiSecondari });
            var allegati = adapterAllegatiOutput.Adatta().ToList();

            return (allegati == null || allegati.Count == 0 || String.IsNullOrEmpty(allegati[0].IDBase)) ? null : allegati.ToArray();
        }

        private MittDestOut[] GetMittentiDestintari(DocumentoProtocollato response)
        { 
            var list = new List<MittDestOut>();

            if (response.mittentiDestinatari != null)
            {
                response.mittentiDestinatari.ToList().ForEach(x => list.Add(new MittDestOut
                {
                    CognomeNome = String.IsNullOrEmpty(x.denominazione) ? String.Concat(x.nome, " ", x.cognome) : x.denominazione,
                    IdSoggetto = x.codiceMittenteDestinatario
                }));
            }

            return list.ToArray();

        }
    }
}
