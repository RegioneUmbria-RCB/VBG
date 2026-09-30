using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.MittentiDestinatari.GestioneAnagrafiche
{
    public class AnagraficheBase
    {
        protected InsielVerticalizzazioniConfiguration _vert;
        protected ProtocolloLogs _logs;
        public AnagraficheBase(ProtocolloLogs logs, InsielVerticalizzazioniConfiguration vert)
        {
            this._vert = vert;
            this._logs = logs;
        }

        protected void AggiornaAnagrafica(IAnagraficaAmministrazione anagrafica, ProtocolloService srv, string nominativo, Anagrafica responseLeggi)
        {
            this._logs.Info($"INIZIO AGGIORNAMENTO ANAGRAFICA CODICE (PROTOCOLLO): {responseLeggi.CodiceAnagrafica}, DESCRIZIONE (PROTOCOLLO): {responseLeggi.DescrizioneAnagrafica}");
            this._logs.Info($"CONFIGURAZIONE AGGIORNAMENTO: {this._vert.TipoAggiornamentoAnagrafica}");

            if (this._vert.TipoAggiornamentoAnagrafica == TipoGestioneAnagraficaEnum.TipoAggiornamento.NO_AGGIORNAMENTO)
            {
                return;
            }

            this._logs.Info($"PEC ANAGRAFICA DA AGGIORNARE: {anagrafica.Pec}");

            if (String.IsNullOrEmpty(anagrafica.Pec))
            {
                return;
            }

            var numeroEmailPresenti = responseLeggi.EmailList.Count;

            this._logs.Info($"NUMERO PEC PRESENTI SU ANAGRAFICA PROTOCOLLO: {numeroEmailPresenti}");

            if (this._vert.TipoAggiornamentoAnagrafica == TipoGestioneAnagraficaEnum.TipoAggiornamento.AGGIORNA_SE_PEC_VUOTA)
            {
                if (numeroEmailPresenti > 0)
                {
                    return;
                }
            }

            if (responseLeggi.EmailList.Where(x => x.Email == anagrafica.Pec && x.Tipo == TipoEmailAnagrafica.pec && x.Principale).Count() == 0)
            {
                var requestAggiorna = new AggiornamentoAnagraficaRequest
                {
                    IdAnagrafica = new IdAnagrafica { DescrizioneAnagrafica = responseLeggi.DescrizioneAnagrafica },
                    DatiAnagrafica = new Anagrafica
                    {
                        EmailList = GetMailList(anagrafica)
                    }
                };

                this._logs.Info($"AGGIORNAMENTO DELL'ANAGRAFICA {responseLeggi.DescrizioneAnagrafica} CON PEC {anagrafica.Pec}");
                srv.AggiornaAnagrafica(requestAggiorna);
            }
        }

        protected void InserisciAnagraficaDefault(IAnagraficaAmministrazione anagrafica, ProtocolloService srv, string nominativo)
        {
            var request = new NuovaAnagraficaRequest
            {
                DatiAnagrafica = new Anagrafica
                {
                    DescrizioneAnagrafica = nominativo,
                    Denominaz = nominativo,
                    Nome = anagrafica.Nome.Replace("  ", " ").Trim(),
                    Cognome = anagrafica.Cognome.Replace("  ", " ").Trim(),
                    CodTipoAna = CodiceTipoAnagrafica.esterno,
                    Disattivata = false,
                    EmailList = GetMailList(anagrafica)
                }
            };

            if (!String.IsNullOrEmpty(anagrafica.PartitaIva) && anagrafica.PartitaIva.Length == 11)
            {
                request.DatiAnagrafica.Piva = anagrafica.PartitaIva;
            }

            if (!String.IsNullOrEmpty(anagrafica.CodiceFiscale) && anagrafica.CodiceFiscale.Length == 16)
            {
                request.DatiAnagrafica.Codfis = anagrafica.CodiceFiscale;
            }

            if (!String.IsNullOrEmpty(anagrafica.Indirizzo))
            {
                request.DatiAnagrafica.Indirizzo = anagrafica.Indirizzo;
            }

            if (!this._vert.DisabilitaValidazioneCapIta && !String.IsNullOrEmpty(anagrafica.Cap))
            {
                int result;
                string trimmedCap = anagrafica.Cap.Trim();
                bool isParsable = int.TryParse(trimmedCap, out result);

                if (!isParsable)
                {
                    throw new Exception($"IL VALORE DEL CAP {trimmedCap}, RELATIVO ALL'ANAGRAFICA {anagrafica.NomeCognome}, NON HA UN FORMATO CORRETTO");
                }

                if (trimmedCap.Length < 4)
                {
                    throw new Exception($"IL VALORE {trimmedCap} RELATIVO AL CAP DI RESIDENZA DELL'ANAGRAFICA {anagrafica.NomeCognome} E' COMPOSTO DA MENO DI 4 CARATTERI");
                }

                request.DatiAnagrafica.Cap = trimmedCap;
            }


            if (!String.IsNullOrEmpty(anagrafica.Sesso))
            {
                request.DatiAnagrafica.Sesso = EnumConverter.ConvertToSessoEnum(anagrafica.Sesso);
            }

            this._logs.Info($"INIZIO INSERIMENTO ANAGRAFICA.  Denominazione: {anagrafica.Denominazione}, CodiceFiscale: {anagrafica.CodiceFiscale}, PartitaIva: {anagrafica.PartitaIva}");

            srv.InserisciAnagrafica(request);
        }

        protected string CercaInserisciAggiornaAnagrafica(IAnagraficaAmministrazione anagrafica, ProtocolloService srv, string nominativo)
        {
            string nome = string.Empty;
            string cognome = string.Empty;

            if (!string.IsNullOrEmpty(anagrafica.Nome))
                nome = anagrafica.Nome.Replace("  ", " ").Trim();

            if (!string.IsNullOrEmpty(anagrafica.Cognome))
                cognome = anagrafica.Cognome.Replace("  ", " ").Trim();

            var request = new PredisponiAnagraficaRequest
            {
                Anagrafica = new AnagraficaObj
                {
                    Denominazione = nominativo,
                    CodiceFiscale = anagrafica.CodiceFiscale,
                    PartitaIva = anagrafica.PartitaIva,
                    Indirizzo = anagrafica.Indirizzo,
                    EmailList = GetMailList(anagrafica)
                },
            };

            if (!String.IsNullOrEmpty(nome) && !string.IsNullOrEmpty(cognome))
            {
                request.Anagrafica.Nome = nome;
                request.Anagrafica.Cognome = cognome;
            }

            this._logs.InfoFormat("valore di anagrafica.Sesso {0} //DEBUG", anagrafica.Sesso);
            var sessoEnum = anagrafica.Sesso?.ConvertToSessoEnum();
            if (sessoEnum.HasValue)
            {
                request.Anagrafica.Sesso = sessoEnum;
            }


            this._logs.Info($"INIZIO PredisponiAnagrafica. Ricerca effettuata con Denominazione: {nominativo}, CodiceFiscale: {anagrafica.CodiceFiscale}, PartitaIva: {anagrafica.PartitaIva}, Nome: {nome}, Cognome: {cognome}");

            var response = srv.PredisponiAnagrafica(request);

            if (response.AnagraficheTrovate == 0)
                this._logs.Info($"Creata l'anagrafica con Codice: {response.Anagrafica.CodiceAnagrafica}, Descrizione: {response.Anagrafica.DescrizioneAnagrafica}, CF: {response.Anagrafica.Codfis}, PIVA: {response.Anagrafica.Piva}");
            else if (response.AnagraficheTrovate == 1)
                this._logs.Info($"Trovata/Aggiornata l'anagrafica con Codice: {response.Anagrafica.CodiceAnagrafica}, Descrizione: {response.Anagrafica.DescrizioneAnagrafica}, CF: {response.Anagrafica.Codfis}, PIVA: {response.Anagrafica.Piva}");
            else
                this._logs.Info($"Sono state trovate {response.AnagraficheTrovate} anagrafiche.. in teoria questo non è possibile perchè il protocollo restituisce sempre una sola anagrafica ");

            return response.Anagrafica.DescrizioneAnagrafica;
        }

        private List<EmailAnagrafica> GetMailList(IAnagraficaAmministrazione anagrafica)
        {
            var mailList = new List<EmailAnagrafica>();
            bool hasPEC = false;

            if (!string.IsNullOrEmpty(anagrafica.Pec))
            {
                mailList.Add(new EmailAnagrafica()
                {
                    Email = anagrafica.Pec,
                    Tipo = TipoEmailAnagrafica.pec,
                    Principale = true
                });

                hasPEC = true;
            }

            if (!string.IsNullOrEmpty(anagrafica.Email))
            {
                mailList.Add(new EmailAnagrafica()
                {
                    Email = anagrafica.Email,
                    Tipo = TipoEmailAnagrafica.peo,
                    Principale = !hasPEC
                });
            }

            if (mailList.Count > 0)
                return mailList;
            else
                return null;
        }
    }
}
