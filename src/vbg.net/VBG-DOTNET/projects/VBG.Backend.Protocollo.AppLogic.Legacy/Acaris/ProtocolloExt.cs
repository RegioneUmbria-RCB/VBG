using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Fascicolazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Metadati;
using PersonalLib2.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared;
using System.Collections.Generic;
using System;
using System.Linq;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.Metadati;
using Init.SIGePro.Manager;
using SIGePro.Manager.VerticalizzazioniBase;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris
{
    public class ProtocolloExt
    {
        public DataBase Db { get; }
        public ProtocolloLogs Logger { get; }
        public string Token { get; }
        public string IdComuneAlias { get; }
        public string Idcomune { get; }
        public string CodiceComune { get; }
        public string Software { get; }
        public IProtocolloSerializer Serialier { get; }
        public ParametriRegoleInfo Configurazione { get; }
        public DatiProtocolloIn DatiProtocollo { get; }
        public int? CodiceIstanza { get; internal set; }
        public string NumeroFascicolo { get; }
        public string DescrizioneLavori { get; }
        public int? CodiceMovimento { get; internal set; }
        public List<String> Soggetti { get; }
        public string InterventoIstanza { get; }
        public string Operatore { get; }
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public ProtocolloExt(IProtocolloSerializer serializer, ProtocolloLogs logger, DatiProtocolloIn datiProtocollo, ProtocolloBase protocollo, IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this.Serialier = serializer;
            this.Logger = logger;
            this.Token = protocollo.DatiProtocollo.Token;
            this.IdComuneAlias = protocollo.DatiProtocollo.IdComuneAlias;
            this.Idcomune = protocollo.DatiProtocollo.IdComune;
            this.CodiceComune = protocollo.DatiProtocollo.CodiceComune;
            this.Software = protocollo.DatiProtocollo.Software;
            this.Db = protocollo.DatiProtocollo.Db;
            this.DatiProtocollo = datiProtocollo;
            this.Operatore = protocollo.Operatore;
            this._verticalizzazioniFactory = verticalizzazioniFactory;

            if (!String.IsNullOrEmpty(protocollo.DatiProtocollo.CodiceIstanza)) {
                this.CodiceIstanza = Convert.ToInt32(protocollo.DatiProtocollo.CodiceIstanza);
            }
            logger.DebugFormat($"Codice istanza {this.CodiceIstanza}");

            this.DescrizioneLavori =  protocollo.DatiProtocollo.Istanza?.LAVORI;
            logger.DebugFormat($"DescrizioneLavori {this.DescrizioneLavori}");

            if (!String.IsNullOrEmpty(protocollo.DatiProtocollo.CodiceMovimento))
            {
                this.CodiceMovimento = Convert.ToInt32(protocollo.DatiProtocollo.CodiceMovimento);
            }
            logger.DebugFormat($"Codice movimento {this.CodiceMovimento}");

            this.Soggetti = new List<string>();
            if (datiProtocollo.Mittenti.Anagrafe?.Count > 0)
            {
                this.Soggetti.AddRange(datiProtocollo.Mittenti.Anagrafe?.Select(x => x.GetNomeCompleto()));
            }
            if (datiProtocollo.Mittenti.Amministrazione?.Count > 0)
            {
                this.Soggetti.AddRange(datiProtocollo.Mittenti.Amministrazione?.Select(x => x.AMMINISTRAZIONE ));
            }
            logger.DebugFormat($"Soggetti {String.Join(" - ", this.Soggetti)}");

            this.InterventoIstanza = protocollo.DatiProtocollo.Istanza?.Intervento.DescrizioneCompleta;
            logger.DebugFormat($"InterventoIstanza {this.InterventoIstanza}");

            var codiceAmministrazione = (datiProtocollo.Flusso == "A") ? datiProtocollo.Destinatari.Amministrazione[0].CODICEAMMINISTRAZIONE : datiProtocollo.Mittenti.Amministrazione[0].CODICEAMMINISTRAZIONE;

            var metadatiProtocollo = this.RecuperaMetadatiProtocollo(datiProtocollo);

            

            this.Configurazione = new ParametriRegoleInfoAdapter(serializer, metadatiProtocollo, Convert.ToInt32(codiceAmministrazione), protocollo, this._verticalizzazioniFactory).Adatta();

            this.NumeroFascicolo = new NumeroFascicoloResolver(protocollo.DatiProtocollo, datiProtocollo.Destinatari.Anagrafe?.FirstOrDefault()).Resolve(this.Configurazione);
            logger.DebugFormat($"Numero fascicolo {this.NumeroFascicolo}");

            logger.DebugFormat($"AnniConservazioneCorrente {this.Configurazione.AnniConservazioneCorrente}");
            logger.DebugFormat($"AnniConservazioneGenerale {this.Configurazione.AnniConservazioneGenerale}");
            logger.DebugFormat($"AppKey {this.Configurazione.AppKey}");
            logger.DebugFormat($"BackOfficePortUrl {this.Configurazione.BackOfficePortUrl}");
            logger.DebugFormat($"CodiceFiscale {this.Configurazione.CodiceFiscale}");
            logger.DebugFormat($"IdAOO {this.Configurazione.IdAoo}");
            logger.DebugFormat($"IdGradoVitalita {this.Configurazione.IdGradoVitalita}");
            logger.DebugFormat($"IdNodo {this.Configurazione.IdNodo}");
            logger.DebugFormat($"IdStruttura {this.Configurazione.IdStruttura}");
            logger.DebugFormat($"ObjectPortUrl {this.Configurazione.ObjectPortUrl}");
            logger.DebugFormat($"OfficialBookPortUrl {this.Configurazione.OfficialBookPortUrl}");
            logger.DebugFormat($"RepositoryID {this.Configurazione.RepositoryID}");
            logger.DebugFormat($"SerieFascicoli {this.Configurazione.SerieFascicoli}");
        }

        public ProtocolloExt(IProtocolloSerializer serializer, ProtocolloLogs logger, ResolveDatiProtocollazioneService resolver, string operatore, IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this.Serialier = serializer;
            this.Logger = logger;
            this.Token = resolver.Token;
            this.IdComuneAlias = resolver.IdComuneAlias;
            this.Idcomune = resolver.IdComune;
            this.CodiceComune = resolver.CodiceComune;
            this.Software = resolver.Software;
            this.Db = resolver.Db;
            this.Operatore = operatore;
            this._verticalizzazioniFactory = verticalizzazioniFactory;

            if (!String.IsNullOrEmpty(resolver.CodiceIstanza))
            {
                this.CodiceIstanza = Convert.ToInt32(resolver.CodiceIstanza);
            }
            logger.DebugFormat($"Codice istanza {this.CodiceIstanza}");

            this.NumeroFascicolo = resolver.NumeroIstanza;
            logger.DebugFormat($"Numero fascicolo {this.NumeroFascicolo}");

            if (resolver.Istanza != null)
            {
                this.DescrizioneLavori = resolver.Istanza.LAVORI;
                logger.DebugFormat($"DescrizioneLavori {this.DescrizioneLavori}");

                this.InterventoIstanza = resolver.Istanza.Intervento.DescrizioneCompleta;
                logger.DebugFormat($"InterventoIstanza {this.InterventoIstanza}");
            }

            if (!String.IsNullOrEmpty(resolver.CodiceMovimento))
            {
                this.CodiceMovimento = Convert.ToInt32(resolver.CodiceMovimento);
            }
            logger.DebugFormat($"Codice movimento {this.CodiceMovimento}");

            this.Soggetti = new List<string>();
            logger.DebugFormat($"Soggetti {String.Join(" - ", this.Soggetti)}");

            this.Configurazione = new ParametriRegoleInfoAdapter(serializer, this.Operatore, resolver.IdComuneAlias, resolver.Software, resolver.CodiceComune, this._verticalizzazioniFactory).Adatta();

            logger.DebugFormat($"AnniConservazioneCorrente {this.Configurazione.AnniConservazioneCorrente}");
            logger.DebugFormat($"AnniConservazioneGenerale {this.Configurazione.AnniConservazioneGenerale}");
            logger.DebugFormat($"AppKey {this.Configurazione.AppKey}");
            logger.DebugFormat($"BackOfficePortUrl {this.Configurazione.BackOfficePortUrl}");
            logger.DebugFormat($"CodiceFiscale {this.Configurazione.CodiceFiscale}");
            logger.DebugFormat($"IdAOO {this.Configurazione.IdAoo}");
            logger.DebugFormat($"IdGradoVitalita {this.Configurazione.IdGradoVitalita}");
            logger.DebugFormat($"IdNodo {this.Configurazione.IdNodo}");
            logger.DebugFormat($"IdStruttura {this.Configurazione.IdStruttura}");
            logger.DebugFormat($"ObjectPortUrl {this.Configurazione.ObjectPortUrl}");
            logger.DebugFormat($"OfficialBookPortUrl {this.Configurazione.OfficialBookPortUrl}");
            logger.DebugFormat($"RepositoryID {this.Configurazione.RepositoryID}");
        }

        private IEnumerable<MetadatoType> RecuperaMetadatiProtocollo(DatiProtocolloIn dati)
        {
            var metadati = dati.RecuperaMetadati().ToList();
            if (this.CodiceIstanza.HasValue)
            {
                var codiceIntervento = Convert.ToInt32(new IstanzeMgr(this.Db).GetById(this.Idcomune, this.CodiceIstanza.Value).CODICEINTERVENTOPROC);
                var metadatiAlbero = new MetadatiService(this.Db).RecuperaMetadati(this.Idcomune, codiceIntervento);
                if (metadatiAlbero?.Any() == true)
                {
                    ImpostaMetadato(metadati, MetadatiConstants.TemplateNumeroFascicolo, metadatiAlbero, "PROTOCOLLO_ACARIS");
                    ImpostaMetadato(metadati, MetadatiConstants.SerieDossier, metadatiAlbero, "PROTOCOLLO_ACARIS");
                    ImpostaMetadato(metadati, MetadatiConstants.SerieFascicoli, metadatiAlbero, "PROTOCOLLO_ACARIS");
                    ImpostaMetadato(metadati, MetadatiConstants.AnniConservazioneGenerale, metadatiAlbero, "PROTOCOLLO_ACARIS");
                    ImpostaMetadato(metadati, MetadatiConstants.AnniConservazioneCorrente, metadatiAlbero, "PROTOCOLLO_ACARIS");
                    ImpostaMetadato(metadati, MetadatiConstants.DescrizioneFascicolo, metadatiAlbero, "PROTOCOLLO_ACARIS");
                }
            }

            return metadati;
        }

        private void ImpostaMetadato(List<MetadatoType> metadatiPresenti, String chiaveMetadato, IEnumerable<AlberoProcMetadati> metadatiConfigurati, string nomeVerticalizzazione)
        {
            var chiaveMetadatoConfigurato = $"{nomeVerticalizzazione}.{chiaveMetadato}";

            if (metadatiPresenti.Find(x => x.Chiave == chiaveMetadato) == null)
            {
                var valoreMetadatoIntervento = metadatiConfigurati.FirstOrDefault(x => x.Chiave == chiaveMetadatoConfigurato)?.Valore;
                if (!String.IsNullOrEmpty(valoreMetadatoIntervento))
                {
                    metadatiPresenti.Add(new MetadatoType
                    {
                        Chiave = chiaveMetadato,
                        Valore = valoreMetadatoIntervento
                    });
                }
            }
        }
    }
}
