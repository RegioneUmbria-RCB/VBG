using ProtocolloFoliumService;
using VBG.Backend.Protocollo.AppLogic.Core.Folium.Allegati;
using VBG.Backend.Protocollo.AppLogic.Core.Folium.ServiceWrapper;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Folium.LeggiProtocollo
{
    public class LeggiProtocolloOutputAdapter
    {
        ProtocollazioneServiceWrapper _wsWrapper;


        public LeggiProtocolloOutputAdapter(ProtocollazioneServiceWrapper wsWrapper)
        {
            _wsWrapper = wsWrapper;
        }

        public DatiProtocolloLettoResponseType Adatta(Ricerca request)
        {
            var response = _wsWrapper.LeggiProtocollo(request);

            var retVal = new DatiProtocolloLettoResponseType();

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

        private AllegatoResponseType[] GetAllegati(DocumentoProtocollato response)
        {
            var idProtocollo = response.id.Value;

            var allegatoPrincipale = new AllegatoPrincipaleInputAdapter(idProtocollo, response.nomeFileContenuto);
            var allegatiSecondari = new AltriAllegatiOutputAdapter(_wsWrapper, idProtocollo);

            var adapterAllegatiOutput = new AllegatiProtocolloOutputAdapter(new IAllegatiAdapter[] { allegatoPrincipale, allegatiSecondari });
            return adapterAllegatiOutput.Adatta().ToArray();
        }

        private MittDestOutType[] GetMittentiDestintari(DocumentoProtocollato response)
        {
            var list = new List<MittDestOutType>();

            if (response.mittentiDestinatari != null)
            {
                response.mittentiDestinatari.ToList().ForEach(x => list.Add(new MittDestOutType
                {
                    CognomeNome = String.IsNullOrEmpty(x.denominazione) ? $"{x.nome} {x.cognome} ({x.codiceMezzoSpedizione})" : $"{x.denominazione} ({x.codiceMezzoSpedizione})"
                }));
            }

            return list.ToArray();

        }
    }
}
