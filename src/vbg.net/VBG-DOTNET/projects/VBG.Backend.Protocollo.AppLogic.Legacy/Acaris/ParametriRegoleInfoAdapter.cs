using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Metadati;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizazioni.Legacy;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris
{
    public class ParametriRegoleInfoAdapter
    {
        private static class Constants
        {
            public const int _conservazioneIllimitata = 99;
        }

        private readonly VerticalizzazioneProtocolloAcaris _vert;
        private readonly string _codiceFiscale;
        private readonly int _idAoo;
        private readonly int _idNodo;
        private readonly int _idStruttura;
        private readonly IProtocolloSerializer _serializer;
        private readonly IEnumerable<MetadatoType> _metadati;

        public ParametriRegoleInfoAdapter(IProtocolloSerializer serializer, String operatore, string idComuneAlias, string software, string codiceComune, int idAoo, int idStruttura, int idNodo, IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._serializer = serializer;
            this._metadati = null;

            this._vert = verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAcaris>(idComuneAlias, software, codiceComune);

            this._codiceFiscale = operatore;

            this._idAoo = idAoo;
            this._idStruttura = idStruttura;
            this._idNodo = idNodo;
        }

        public ParametriRegoleInfoAdapter(IProtocolloSerializer serializer, String operatore, string idComuneAlias, string software, string codiceComune, IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._serializer = serializer;
            this._metadati = null;

            this._vert = verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAcaris>(idComuneAlias, software, codiceComune);

            this._codiceFiscale = operatore;
        }

        public ParametriRegoleInfoAdapter(IProtocolloSerializer serializer, IEnumerable<MetadatoType> metadati, int codiceAmministrazione, ProtocolloBase protocollo, IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._serializer = serializer;
            this._metadati = metadati;

            var resolveDatiProtocollazione = protocollo.DatiProtocollo;

            this._vert = verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAcaris>(resolveDatiProtocollazione.IdComuneAlias, resolveDatiProtocollazione.Software, resolveDatiProtocollazione.CodiceComune);

            this._codiceFiscale = protocollo.Operatore;
            this._idAoo = this._vert.IdAOO.Value;

            var amministrazione = new AmministrazioniProtocolloMgr(resolveDatiProtocollazione.Db).GetById(resolveDatiProtocollazione.IdComune, codiceAmministrazione, resolveDatiProtocollazione.Software, resolveDatiProtocollazione.CodiceComune);
            this._idStruttura = Convert.ToInt32(amministrazione.ProtUo);
            this._idNodo = Convert.ToInt32(amministrazione.ProtRuolo);
        }

        public ParametriRegoleInfo Adatta()
        {
            if (!this._vert.Attiva)
                throw new ConfigurationErrorsException($"La verticalizzazione {this._vert.NomeVerticalizzazione} non è attiva");
            try
            {
                var accessToken = "";
                if (!string.IsNullOrEmpty(this._vert.UrlAutenticazione))
                {
                    accessToken = new AuthenticationRestClient(this._serializer, this._vert.UrlAutenticazione, this._vert.ConsumerKey, this._vert.ConsumerSecret).GetAuthToken();
                }

                var repositoryClient = new RepositoryWsClient(this._vert.UrlRepositoryServices, accessToken);
                var backofficeWsClient = new BackofficeWSClient(this._vert.UrlBackofficeServices, accessToken);

                //Verifico l'override dei parametri sui metadati dell'albero se presente il codice istanza

                var configurazione = new ParametriRegoleInfo
                {
                    AccessToken = accessToken,
                    AnniConservazioneCorrente =
                    (this._metadati?.Any(x => x.Chiave == MetadatiConstants.AnniConservazioneCorrente)) != true
                        ? this._vert.AnniConservazioneCorrente ?? Constants._conservazioneIllimitata
                        : Convert.ToInt32(this._metadati.First(x => x.Chiave == MetadatiConstants.AnniConservazioneCorrente).Valore),
                    AnniConservazioneGenerale =
                    (this._metadati?.Any(x => x.Chiave == MetadatiConstants.AnniConservazioneGenerale)) != true
                        ? this._vert.AnniConservazioneGenerale ?? Constants._conservazioneIllimitata
                        : Convert.ToInt32(this._metadati.First(x => x.Chiave == MetadatiConstants.AnniConservazioneGenerale).Valore),
                    AnnotaAllegatoPrincipale = this._vert.AnnotaAllegatoPrincipale,
                    AnnotaAllegatoSecondario = this._vert.AnnotaAllegatoSecondario,
                    AppKey = this._vert.AppKey,
                    ApplicativoAlimentante = this._vert.ApplicativoAlimentante,
                    BackOfficePortUrl = this._vert.UrlBackofficeServices,
                    CodiceDossierLength = this._vert.CodiceDossierLength,
                    CodiceDossierMax = this._vert.CodiceDossierMax,
                    CodiceDossierMin = this._vert.CodiceDossierMin,
                    CodiceFiscale = this._codiceFiscale,
                    DescrizioneFascicolo = this._vert.DescrizioneFascicolo,
                    DocumentPortUrl = this._vert.UrlDocumentServices,
                    GestioneSediAbilitata = this._vert.GestioneSedi,
                    RicercaFascicoloPerDescrizione = this._vert.RicercaFascicoloPerDescrizione,
                    IdGradoVitalita = this._vert.IdGradoVitalita.Value,
                    InviaCopiaCortesia = this._vert.InviaCopiaCortesia,
                    IdTitolario = this._vert.IdTitolario.Value,
                    ManagementPortUrl = this._vert.UrlManagementServices,
                    NavigationPortUrl = this._vert.UrlNavigationServices,
                    OfficialBookPortUrl = this._vert.UrlOfficialBookServices,
                    ObjectPortUrl = this._vert.UrlObjectServices,
                    RelationshipsPortUrl = this._vert.UrlRelationshipServices,
                    RepositoryID = new RepositoryId(this._serializer, repositoryClient, this._vert.Repository),
                    SerieDossier =
                    (this._metadati?.Any(x => x.Chiave == MetadatiConstants.SerieDossier)) != true
                        ? this._vert.SerieDossier
                        : this._metadati.First(x => x.Chiave == MetadatiConstants.SerieDossier).Valore,
                    SerieFascicoli =
                    (this._metadati?.Any(x => x.Chiave == MetadatiConstants.SerieFascicoli)) != true
                        ? this._vert.SerieFascicoli
                        : this._metadati.First(x => x.Chiave == MetadatiConstants.SerieFascicoli).Valore,
                    TemplateDescrizioneDossier = this._vert.TemplateDescrizioneDossier,
                    TemplateNumeroFascicolo = (this._metadati?.Any(x => x.Chiave == MetadatiConstants.TemplateNumeroFascicolo)) != true
                        ? this._vert.TemplateNumeroFascicolo
                        : this._metadati.First(x => x.Chiave == MetadatiConstants.TemplateNumeroFascicolo).Valore,
                    TipoFascicolazione = this._vert.TipoFascicolazione,
                    TipoFascicolo = this._vert.TipoFascicolo,
                    GestioneSottofascicoloAbilitata = this._vert.GestisciSottofascicolo,
                    DescrizioneSottofascicolo = this._vert.DescrizioneSottofascicolo,
                    ValorizzaSoggettoFascicolo = this._vert.ValorizzaSoggettoFascicolo

                };

                try
                {
                    configurazione.PrincipalID = new PrincipalId(this._serializer, backofficeWsClient, configurazione.RepositoryID, this._codiceFiscale, this._vert.IdAOO.Value, this._idStruttura, this._idNodo, this._vert.AppKey);
                }
                catch(Exception)
                {
                    return null;
                }

                configurazione.IdAoo = new IdAoo(this._serializer, backofficeWsClient, configurazione.RepositoryID, configurazione.PrincipalID, this._idAoo);
                configurazione.IdStruttura = new IdStruttura(this._serializer, backofficeWsClient, configurazione.RepositoryID, configurazione.PrincipalID, this._idStruttura);
                configurazione.IdNodo = new IdNodo(this._serializer, backofficeWsClient, configurazione.RepositoryID, configurazione.PrincipalID, this._idNodo);

                return configurazione;
            }
            catch (Exception ex)
            {
                throw new ConfigurationErrorsException($"RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE {this._vert.NomeVerticalizzazione} FALLITO, {ex.Message}", ex);
            }
        }
    }
}
