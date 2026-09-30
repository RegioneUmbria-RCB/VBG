using VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Protocollazione.DocumentiAllegati;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Protocollazione
{
    public class MetadatiAdapter
    {
        VerticalizzazioniConfiguration _vert;
        IDatiProtocollo _datiProto;
        ProtocolloSerializer _serializer;

        public MetadatiAdapter(VerticalizzazioniConfiguration vert, IDatiProtocollo datiProto, ProtocolloSerializer serializer)
        {
            _vert = vert;
            _datiProto = datiProto;
            _serializer = serializer;
        }

        public string Adatta(string segnatura, IProtocollazionePaDoc proto, string idComuneAlias, string software, IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            var classificaAdapter = new ClassificaAdapter(_datiProto.ProtoIn.Classifica);
            var classifica = classificaAdapter.Adatta();
            var allegatiAdapter = new AllegatiAdapter(_datiProto.ProtoIn.RecuperaAllegati().ToList());
            var allegati = allegatiAdapter.Adatta(proto.Codice, _vert.UrlResponseService, idComuneAlias, software, verticalizzazioniFactory);

            var da = new desktop_assignment
            {
                type = "1",
                owners = new desktop_assignmentOwners[] { new desktop_assignmentOwners { owner = _datiProto.Uo } },
                external_action_uri = new desktop_assignmentExternal_action_uri { update = proto.UrlUpdate, error = proto.UrlError },
                documentary_unit = new documentary_unit
                {
                    metadata = new documentary_unitMetadata
                    {
                        classification = new documentary_unitMetadataClassificationLevel[]
                        { 
                            new documentary_unitMetadataClassificationLevel{ Value = classifica.Titolo },
                            new documentary_unitMetadataClassificationLevel{ Value = classifica.Classe }
                        },
                        owners = new documentary_unitMetadataOwners[] { new documentary_unitMetadataOwners { owner = _datiProto.Uo } },
                        xml = new documentary_unitMetadataXml { type = "REG", Value = segnatura }
                    },
                    metadigit = new metadigit
                    {
                        gen = new metadigitGen
                        {
                            creation = DateTime.Now.ToString("yyyy-MM-ddTHH:mm:ssK"),
                            last_update = DateTime.Now.ToString("yyyy-MM-ddTHH:mm:ssK"),
                            stprog = _vert.Dominio,
                            agency = _vert.CodiceAmministrazione
                        },
                        bib = new metadigitBib
                        {
                            level = "s",
                            identifier = proto.Codice,
                            description = "",
                            subject = _datiProto.ProtoIn.Classifica,
                            language = "IT",
                            title = _datiProto.ProtoIn.Oggetto,
                        },
                        stru = allegati.Stru,
                        doc = allegati.Doc
                    }
                }
            };

            var res = _serializer.Serialize(ProtocolloLogsConstants.MetadatiProtocolloInputFileName, da);

            return res;
        }
    }
}
