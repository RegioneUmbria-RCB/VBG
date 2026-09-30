using Init.SIGePro.Protocollo.AcarisOfficialBookServicePort;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Protocollazione.Registrazioni
{
    public class RegistrazioneAPIPartenzaResolver : IRegistrazioneAPIResolver
    {
        private class Constants
        {
            public const bool ForzareSePresenzaInviti = true;
            public const bool ForzareSePresenzaDaInoltrare = true;
            public const bool ForzareSeRegistrazioneSimile = true;
            public const int IdRuoloPerCompetenza = 1;
        }

        private readonly ProtocolloExt _datiProtocollo;
        private readonly ParametriRegoleInfo _parametri;
        public string ClassificazioneID { get; }

        public RegistrazioneAPIPartenzaResolver(ProtocolloExt datiProtocollo, String classificazione)
        {
            this._datiProtocollo = datiProtocollo;
            this._parametri = datiProtocollo.Configurazione;
            this.ClassificazioneID = classificazione;
        }

        public RegistrazioneAPI ResolveRegistrazione()
        {
            return new RegistrazionePartenza
            {
                tipoRegistrazione = enumTipoAPI.Partenza,
                infoCreazione = new InfoCreazioneRegistrazione
                {
                    forzareSePresenzaInviti = Constants.ForzareSePresenzaInviti,
                    forzareSePresenzaDaInoltrare = Constants.ForzareSePresenzaDaInoltrare,
                    forzareSeRegistrazioneSimile = Constants.ForzareSeRegistrazioneSimile,
                    protocollante = new IdentificazioneProtocollante
                    {
                        nodoId = new ObjectIdType { value = this._parametri.IdNodo.IdAcaris },
                        strutturaId = new ObjectIdType { value = this._parametri.IdStruttura.IdAcaris }
                    },
                    oggetto = this._datiProtocollo.DatiProtocollo.Oggetto,
                    mittenteInterno = this.ListaMittDestToListMittenteInterno(this._datiProtocollo.DatiProtocollo.Mittenti).ToArray(),
                    destinatarioEsterno = this.ListaMittDestToListDestinatarioEsterno(this._datiProtocollo.DatiProtocollo.Destinatari).ToArray(),
                    destinatarioInterno = this.ListaMittDestToListDestinatarioInterno(this._datiProtocollo.DatiProtocollo.Destinatari)?.ToArray(),
                }
            };
        }

        private List<MittenteInterno> ListaMittDestToListMittenteInterno(ListaMittDest mittenti)
        {
            if (mittenti == null)
            {
                throw new Exception("Nessun mittente presente per la protocollazione in partenza");
            }

            var elenco = new List<MittenteInterno>();

            elenco.AddRange(mittenti.Amministrazione?.Select(x => new MittenteInterno
            {
                corrispondente = new InfoCreazioneCorrispondente
                {
                    denominazione = x.AMMINISTRAZIONE,
                    infoSoggettoAssociato = new RiferimentoSoggettoEsistente { soggettoId = new ObjectIdType { value = this._parametri.IdNodo.IdAcaris }, tipologia = enumTipologiaSoggettoAssociato.Nodo }
                    // tipologiaCorrispondente = enumTipologiaCorrispondente.PA,
                    // tipologiaCorrispondenteSpecified = true
                }
            }));

            elenco.AddRange(mittenti.Anagrafe?.Select(x => new MittenteInterno
            {
                corrispondente = new InfoCreazioneCorrispondente
                {
                    cognome = x.NOMINATIVO,
                    nome = x.NOME
                }
            }));

            return elenco;
        }

        private List<DestinatarioInterno> ListaMittDestToListDestinatarioInterno(ListaMittDest destinatari)
        {
            if (destinatari == null)
            {
                throw new Exception("Nessun destinatario presente per la protocollazione in partenza");
            }

            var client = new BackofficeWSClient(this._parametri.BackOfficePortUrl, this._parametri.AccessToken);
            var amministrazioniInterne = destinatari.Amministrazione?.Where(x => !String.IsNullOrEmpty(x.PROT_RUOLO));

            if (amministrazioniInterne == null || !amministrazioniInterne.Any())
            {
                return null;
            }

            return amministrazioniInterne.Select(x => new DestinatarioInterno
            {
                corrispondente = new InfoCreazioneCorrispondente
                {
                    infoSoggettoAssociato = new RiferimentoSoggettoEsistente
                    {
                        tipologia = enumTipologiaSoggettoAssociato.Nodo,
                        soggettoId = new ObjectIdType { value = new IdNodo(this._datiProtocollo.Serialier, client, this._parametri.RepositoryID, this._parametri.PrincipalID, Convert.ToInt32(x.PROT_RUOLO)).IdAcaris }
                    },
                    denominazione = x.AMMINISTRAZIONE
                },
                idRuoloCorrispondente = String.IsNullOrEmpty(x.ModalitaTrasmissione) ? Constants.IdRuoloPerCompetenza : Convert.ToInt32(x.ModalitaTrasmissione)
            }).ToList();
        }

        private List<DestinatarioEsterno> ListaMittDestToListDestinatarioEsterno(ListaMittDest destinatari)
        {
            if (destinatari == null)
            {
                throw new Exception("Nessun destinatario presente per la protocollazione in partenza");
            }
            var client = new BackofficeWSClient(this._parametri.BackOfficePortUrl, this._parametri.AccessToken);

            var destinatariEsterni = new List<DestinatarioEsterno>();

            destinatariEsterni.AddRange(destinatari.Amministrazione?.Where(x => String.IsNullOrEmpty(x.PROT_RUOLO)).Select(x => new DestinatarioEsterno
            {
                corrispondente = new InfoCreazioneCorrispondente
                {
                    denominazione = String.IsNullOrEmpty(x.AMMINISTRAZIONE) ? null : x.AMMINISTRAZIONE,
                    PIVA = String.IsNullOrEmpty(x.PARTITAIVA) ? null : x.PARTITAIVA,
                    codiceIPAPA = String.IsNullOrEmpty(x.CodiceIPA) ? null : x.CodiceIPA,
                    tipologiaCorrispondente = enumTipologiaCorrispondente.PA,
                    tipologiaCorrispondenteSpecified = !String.IsNullOrEmpty(x.CodiceIPA),
                    indirizzoTelematico = (String.IsNullOrEmpty(x.CodiceIPA) && !String.IsNullOrEmpty(x.PEC))
                        ? new IndirizzoTelematicoType { indirizzo = x.PEC }
                        : null,
                    indirizzoTelematicoPA = (!String.IsNullOrEmpty(x.CodiceIPA) && !String.IsNullOrEmpty(x.PEC))
                        ? new IndirizzoTelematicoType { indirizzo = x.PEC }
                        : null,
                },
                idRuoloCorrispondente = String.IsNullOrEmpty(x.ModalitaTrasmissione) ? Constants.IdRuoloPerCompetenza : Convert.ToInt32(x.ModalitaTrasmissione)
            }));

            destinatariEsterni.AddRange(destinatari.Anagrafe?.Select(x => new DestinatarioEsterno
            {
                corrispondente = new InfoCreazioneCorrispondente
                {
                    denominazione = x.TIPOANAGRAFE == "F" ? null : x.NOMINATIVO,
                    cognome = x.TIPOANAGRAFE == "G" ? null : x.NOMINATIVO,
                    nome = String.IsNullOrEmpty(x.NOME) ? null : x.NOME,
                    codiceFiscale = this.RecuperaCF(x.TIPOANAGRAFE, x.CODICEFISCALE),
                    PIVA = this.RecuperaPIVA(x.TIPOANAGRAFE, x.PARTITAIVA, x.CODICEFISCALE),
                    tipologiaCorrispondente = x.TIPOANAGRAFE == "F" ? enumTipologiaCorrispondente.PF : enumTipologiaCorrispondente.PG,
                    tipologiaCorrispondenteSpecified = true,
                    indirizzoTelematico = !String.IsNullOrEmpty(x.PecProtocollazione)
                        ? new IndirizzoTelematicoType { indirizzo = x.PecProtocollazione }
                        : null
                },
                idRuoloCorrispondente = String.IsNullOrEmpty(x.ModalitaTrasmissione) ? Constants.IdRuoloPerCompetenza : Convert.ToInt32(x.ModalitaTrasmissione)
            }));

            return destinatariEsterni;
        }

        private string RecuperaCF(string tipoAnagrafe, string codiceFiscale)
        {
            if (tipoAnagrafe == "F")
            {
                return codiceFiscale;
            }

            if (String.IsNullOrEmpty(codiceFiscale))
            {
                return null;
            }

            return int.TryParse(codiceFiscale, out _) ? null : codiceFiscale;
        }

        private string RecuperaPIVA(string tipoAnagrafe, string partitaIVA, string codiceFiscale)
        {
            if (tipoAnagrafe == "F")
            {
                return null;
            }

            if (!String.IsNullOrEmpty(partitaIVA))
            {
                return partitaIVA;
            }

            if (String.IsNullOrEmpty(codiceFiscale))
            {
                return null;
            }

            return int.TryParse(codiceFiscale, out _) ? codiceFiscale : null;
        }
    }
}
