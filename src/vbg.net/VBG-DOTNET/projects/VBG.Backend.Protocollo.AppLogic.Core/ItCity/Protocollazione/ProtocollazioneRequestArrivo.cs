using ItCityService;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Protocollazione
{
    public class ProtocollazioneRequestArrivo : ProtocollazioneRequestBase, IProtocollazioneRequest
    {
        IEnumerable<IAnagraficaAmministrazione> _anagrafiche;

        public ProtocollazioneRequestArrivo(ParametriRegoleInfo parametri, IDatiProtocollo datiProtocollo, IEnumerable<IAnagraficaAmministrazione> anagrafiche, int idFascicolo, int? numeroSottoFascicolo) : base(parametri, datiProtocollo, idFascicolo, numeroSottoFascicolo)
        {
            this._anagrafiche = anagrafiche;
        }

        public RecapitoInterno MittenteInternoInfo
        {
            get
            {
                return null;
            }
        }

        public RecapitoEsterno[] MittentiEsterniInfo
        {
            get
            {
                return this._anagrafiche.Select(x => new RecapitoEsterno
                {
                    FlagPersonaDitta = x.Tipo == ProtocolloConstants.COD_PERSONAFISICA ? FlagPersonaDitta.P : FlagPersonaDitta.D,
                    Anagrafica = new Anagrafica
                    {
                        CodiceFiscalePartitaIva = x.CodiceFiscalePartitaIva,
                        Cognome = x.Tipo == ProtocolloConstants.COD_PERSONAFISICA ? x.Cognome : "",
                        Nome = x.Tipo == ProtocolloConstants.COD_PERSONAFISICA ? x.Nome : "",
                        RagioneSociale = x.Tipo == ProtocolloConstants.COD_PERSONAGIURIDICA ? x.Denominazione : ""
                    },
                    Indirizzo = GetIndirizzoAmministrazione(x)
                }).ToArray();
            }
        }

        public DestinatarioInterno[] DestinatariInterniInfo
        {
            get
            {
                return new DestinatarioInterno[] 
                {
                    new DestinatarioInterno
                    {
                        AnnoFacicolo = 0,
                        CopiaConoscenza = false,
                        IdIndice = 0,
                        NumeroFascicolo = 0,
                        NumeroSottoFascicolo = 0,
                        Recapito = new RecapitoInterno
                        {
                            IdUnitaOperativa = Convert.ToInt32(base.DatiProtocollo.Uo)
                        },
                        Originale = true
                    }
                };
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
