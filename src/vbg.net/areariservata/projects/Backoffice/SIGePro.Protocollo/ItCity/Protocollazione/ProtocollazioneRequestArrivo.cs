using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloItCityService;

namespace Init.SIGePro.Protocollo.ItCity.Protocollazione
{
    public class ProtocollazioneRequestArrivo : ProtocollazioneRequestBase, IProtocollazioneRequest
    {
        IEnumerable<IAnagraficaAmministrazione> _anagrafiche;

        public ProtocollazioneRequestArrivo(IDatiProtocollo datiProtocollo, IEnumerable<IAnagraficaAmministrazione> anagrafiche, int idFascicolo, int? numeroSottoFascicolo) : base(datiProtocollo, idFascicolo, numeroSottoFascicolo)
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
                    Indirizzo = new Indirizzo
                    {
                        Descrizione = x.Indirizzo,
                        Comune = x.ComuneResidenza != null ? x.ComuneResidenza.COMUNE : "",
                        Provincia = x.ComuneResidenza != null ? x.ComuneResidenza.SIGLAPROVINCIA : ""
                    }
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
