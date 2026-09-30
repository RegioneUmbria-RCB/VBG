using VBG.Backend.Protocollo.AppLogic.Core.Pal.Classificazione;
using VBG.Backend.Protocollo.AppLogic.Core.Pal.Organigramma;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Pal.LeggiProtocollo
{
    public class LeggiProtocolloAdapter
    {
        public LeggiProtocolloAdapter()
        {

        }

        
        public DatiProtocolloLettoResponseType Adatta(ProtocollazioneType response, RootObject responseClassifiche, string anno, string numero, DateTime? dataProtocollo, OrganigrammaServiceWrapper organigrammaService)
        {
            var classifica = responseClassifiche.results.Where(x => x.id == Convert.ToInt32(response.Intestazione.Classifica)).ToList();
            var factory = LeggiProtocolloFactory.Create(response, organigrammaService);

            var retVal = new DatiProtocolloLettoResponseType
            {
                AnnoProtocollo = anno,
                DataProtocollo = anno,
                NumeroProtocollo = numero,
                Oggetto = response.Intestazione.Oggetto,
                Classifica_Descrizione = classifica[0].descrizione,
                MittentiDestinatari = factory.GetMittenteDestinatario(),
                InCaricoA = factory.InCaricoA,
                InCaricoA_Descrizione = factory.InCaricoADescrizione,
                Origine = factory.Flusso
            };

            if (response.Documenti.Documento != null)
            {
                var allegati = new List<AllegatoResponseType>();
                allegati.Add(new AllegatoResponseType
                {
                    IDBase = response.Documenti.Documento.Id,
                    Serial = response.Documenti.Documento.Nome
                });

                if (response.Documenti.Allegati != null && response.Documenti.Allegati.Documento != null && response.Documenti.Allegati.Documento.Length > 0)
                {
                    foreach (var item in response.Documenti.Allegati.Documento)
                    {
                        allegati.Add(new AllegatoResponseType
                        {
                            Serial = item.Nome,
                            IDBase = item.Id
                        });
                    }
                    retVal.Allegati = allegati.ToArray();
                }
            }

            return retVal;
        }
    }
}
