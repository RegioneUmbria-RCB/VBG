using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Exceptions;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers.Conversioni
{
    public class DatiMittentiTypeToDatiMittentiXmlType
    {
        private readonly ProtocollazioneRepository _anagraficheRepository;

        public DatiMittentiTypeToDatiMittentiXmlType(ProtocollazioneRepository anagraficheRepository)
        {
            this._anagraficheRepository = anagraficheRepository;
        }

        public DatiMittentiXmlType Converti(AmbitoProtocollazioneEnum ambito, DatiMittentiType datiMittenti, Istanze istanza, VerticalizzazioneProtocolloAttivo verticalizzazioneProtocolloAttivo)
        {
            var result = new DatiMittentiXmlType
            {
                Amministrazione = this.ConvertiAmministrazioni(datiMittenti),
                Anagrafe = this.ConvertiAnagrafiche(ambito, datiMittenti, istanza, verticalizzazioneProtocolloAttivo)
            };

            return result;
        }

        private List<ProtocolloAmministrazioni>? ConvertiAmministrazioni(DatiMittentiType datiMittenti)
        {
            if (datiMittenti.Amministrazione == null)
            {
                return null;
            }

            var result = new List<ProtocolloAmministrazioni>();

            foreach (var amministrazioneRequest in datiMittenti.Amministrazione)
            {
                if (String.IsNullOrEmpty(amministrazioneRequest.Cod))
                {
                    throw new ProtocolloException($"La richiesta contiene un'amministrazione senza codice amministrazione.");
                }

                var amministrazione = this._anagraficheRepository.RepositoryGetAmministrazioneByIdProtocollo(Convert.ToInt32(amministrazioneRequest.Cod));

                if (amministrazione == null)
                {
                    throw new ProtocolloException($"L'amministrazione con codice {amministrazioneRequest.Cod} non è stata trovata.");
                }

                amministrazione.Mezzo = amministrazioneRequest.Mezzo;
                amministrazione.ModalitaTrasmissione = amministrazioneRequest.ModalitaTrasmissione;

                if (!String.IsNullOrEmpty(amministrazioneRequest.Email))
                {
                    amministrazione.PEC = amministrazioneRequest.Email;
                }

                result.Add(amministrazione);
            }

            return result.Any() ? result : null;
        }

        private List<ProtocolloAnagrafe>? ConvertiAnagrafiche(AmbitoProtocollazioneEnum ambito, DatiMittentiType datiMittenti, Istanze istanza, VerticalizzazioneProtocolloAttivo verticalizzazioneProtocolloAttivo)
        {
            var result = new List<ProtocolloAnagrafe>();

            if (datiMittenti.Anagrafe == null)
            {
                return null;
            }

            foreach (var anagrafeRequest in datiMittenti.Anagrafe)
            {
                if (String.IsNullOrEmpty(anagrafeRequest.Cod))
                {
                    throw new ProtocolloException($"La richiesta contiene un'anagrafica senza codice anagrafe.");
                }

                var anagrafe = this._anagraficheRepository.RepositoryGetAnagrafeById(anagrafeRequest.Cod);

                if (anagrafe == null)
                {
                    throw new ProtocolloException($"Anagrafica con codice anagrafe {anagrafeRequest.Cod} non trovato.");
                }

                anagrafe.Mezzo = anagrafeRequest.Mezzo;
                anagrafe.ModalitaTrasmissione = anagrafeRequest.ModalitaTrasmissione;

                anagrafe.PecProtocollazione = String.IsNullOrEmpty(anagrafeRequest.Email) ? anagrafe.PecProtocollazione : anagrafeRequest.Email;
                anagrafe.PecAnagrafica = anagrafe.PecProtocollazione;

                if (ambito == AmbitoProtocollazioneEnum.DA_ISTANZA || ambito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                {
                    if (String.IsNullOrEmpty(anagrafeRequest.Email))
                    {
                        var gestionePec = (ProtocolloAnagrafe.TipoGestionePecEnum)Enum.Parse(typeof(ProtocolloAnagrafe.TipoGestionePecEnum), verticalizzazioneProtocolloAttivo.GestionePec);
                        anagrafe.SetPec(istanza, gestionePec);
                    }
                }

                result.Add(anagrafe);
            }

            return result.Any() ? result : null;
        }
    }
}
