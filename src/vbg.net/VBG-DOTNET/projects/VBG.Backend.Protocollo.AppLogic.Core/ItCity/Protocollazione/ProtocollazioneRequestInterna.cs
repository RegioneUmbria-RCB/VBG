using ItCityService;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Protocollazione
{
    public class ProtocollazioneRequestInterna : ProtocollazioneRequestBase, IProtocollazioneRequest
    {
        public ProtocollazioneRequestInterna( ParametriRegoleInfo parametri, IDatiProtocollo datiProtocollo, int idFascicolo, int? numeroSottoFascicolo) : base(parametri, datiProtocollo, idFascicolo, numeroSottoFascicolo)
        {

        }

        public RecapitoInterno MittenteInternoInfo
        {
            get
            {
                return new RecapitoInterno { IdUnitaOperativa = Convert.ToInt32(base.DatiProtocollo.Uo) };
            }
        }

        public RecapitoEsterno[] MittentiEsterniInfo
        {
            get
            {
                return null;
            }
        }

        public DestinatarioInterno[] DestinatariInterniInfo
        {
            get
            {
                return this.DatiProtocollo.AmministrazioniProtocollo.Select((x,i) => new DestinatarioInterno
                {
                    Originale = ( i == 0 ),
                    Recapito = new RecapitoInterno
                    {
                        IdUnitaOperativa = Convert.ToInt32(x.PROT_UO)
                    }
                }).ToArray();
            }
        }

        public DestinatarioEsterno[] DestinatariEsterniInfo
        {
            get
            {
                return null;
            }
        }
    }
}
