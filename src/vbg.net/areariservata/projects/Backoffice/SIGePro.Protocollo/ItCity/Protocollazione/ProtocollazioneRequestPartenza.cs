using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloItCityService;

namespace Init.SIGePro.Protocollo.ItCity.Protocollazione
{
    public class ProtocollazioneRequestPartenza: ProtocollazioneRequestBase, IProtocollazioneRequest
    {
        IEnumerable<IAnagraficaAmministrazione> _anagrafiche;

        public ProtocollazioneRequestPartenza(IDatiProtocollo datiProtocollo, IEnumerable<IAnagraficaAmministrazione> anagrafiche, int idFascicolo, int? numeroSottoFascicolo) : base(datiProtocollo, idFascicolo, numeroSottoFascicolo)
        {
            this._anagrafiche = anagrafiche;
        }

        public RecapitoInterno MittenteInternoInfo
        {
            get
            {
                return new RecapitoInterno
                {
                    IdUnitaOperativa = Convert.ToInt32(base.DatiProtocollo.Uo)
                };
                //return null;
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
                return null;
            }
        }

        public DestinatarioEsterno[] DestinatariEsterniInfo
        {
            get
            {
                return this._anagrafiche.Select((x,i) => new DestinatarioEsterno
                {
                    Anagrafica = new Anagrafica
                    {
                        CodiceFiscalePartitaIva = x.CodiceFiscalePartitaIva,
                        Cognome = x.Tipo == ProtocolloConstants.COD_PERSONAFISICA ? x.Cognome : "",
                        Nome = x.Tipo == ProtocolloConstants.COD_PERSONAFISICA ? x.Nome : "",
                        RagioneSociale = x.Tipo == ProtocolloConstants.COD_PERSONAGIURIDICA ? x.Denominazione : ""
                    },
                    FlagPersonaDitta = x.Tipo == ProtocolloConstants.COD_PERSONAFISICA ? FlagPersonaDitta.P : FlagPersonaDitta.D,
                    Indirizzo = new Indirizzo
                    {
                        Descrizione = x.Indirizzo,
                        Comune = x.ComuneResidenza != null ? x.ComuneResidenza.COMUNE : "",
                        Provincia = x.ComuneResidenza != null ? x.ComuneResidenza.SIGLAPROVINCIA : ""
                    },
                    Originale = ( i == 0 ),
                }).ToArray();
            }
        }
    }
}
