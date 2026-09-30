using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Dossier;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Folder;
using System.Configuration;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using System.Collections.Generic;
using System.Linq;
using System;
using Init.SIGePro.Manager.Logic.GestioneContesti;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Fascicolazione
{
    public class FascicolazioneSuDossierService : IFascicolazioneService
    {
        private readonly ProtocolloLogs _logger;
        private readonly DossierService _dossierService;
        private readonly FolderService _folderService;
        private readonly IFolderTypeResolver _resolver;
        public static readonly string Name = "FascicolazioneSuDossierService";

        public FascicolazioneSuDossierService(IProtocolloSerializer serializer, ProtocolloLogs logger, IFolderTypeResolver resolver)
        {
            this._logger = logger;
            this._resolver = resolver;
            this._dossierService = new DossierService(serializer, this._logger, resolver);
            this._folderService = new FolderService(serializer, this._logger, resolver);
        }

        public IdFolder Fascicola(FascicolaRequest request)
        {
            this._logger.Info("Inizio fascicolazione su dossier");
            //1. Verifico la presenza della serie di dossier e ne recupero il codice
            var idSerieDossier = this._dossierService.RecuperaIdAcarisSerieDossierDaCodice(request.Configurazione.SerieDossier);

            //2. Ricerco il dossier sulla serie trovata per il codice azienda passato
            var idDossier = this._dossierService.RicercaDossierPerCodice(idSerieDossier, request.CodiceDossier);

            //3. Se il dossier non esiste deve essere creato
            if (string.IsNullOrEmpty(idDossier))
            {
                idDossier = this._dossierService.CreaDossier(new CreaDossierRequest
                {
                    AnniConservazione = request.Configurazione.AnniConservazioneCorrente,
                    AnniConservazioneGenerale = request.Configurazione.AnniConservazioneGenerale,
                    CodiceDossier = request.CodiceDossier,
                    Descrizione = this.CalcolaDescrizioneDossier(request.TemplateDescrizioneDossier, request.DatiContestoDossier),
                    IdAOO = new Init.SIGePro.Protocollo.AcarisObjectServicePort.IdAOOType { value = request.Configurazione.IdAoo.Id },
                    IdentificativoUtente = request.IdentificativoUtente,
                    IdNodo = new Init.SIGePro.Protocollo.AcarisObjectServicePort.IdNodoType { value = request.Configurazione.IdNodo.Id },
                    IdStruttura = new Init.SIGePro.Protocollo.AcarisObjectServicePort.IdStrutturaType { value = request.Configurazione.IdStruttura.Id },
                    ParolaChiave = this.CalcolaParolaChiave(request.DatiContestoDossier),
                    PrincipalId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.PrincipalIdType { value = request.Configurazione.PrincipalID.IdAcaris },
                    RepositoryId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.ObjectIdType { value = request.Configurazione.RepositoryID.IdAcaris },
                    SerieDossierId = new Init.SIGePro.Protocollo.AcarisObjectServicePort.ObjectIdType { value = idSerieDossier }
                });
            }

            //5. Aggiungo l'id del dossier alla lista dei parametri disponibili
            this._resolver.IdDossier = idDossier;

            //5. Ricerco il fascicolo per descrizione all'interno del dossier trovato
            var idFolder = request.Configurazione.RicercaFascicoloPerDescrizione
                            ? this._folderService.GetFolderPerDescrizioneEDossier(this._resolver.TypeId, idDossier, this._resolver.OggettoFascicolo)
                            : this._folderService.GetFolderPerCodiceEDossier(this._resolver.TypeId, idDossier, this._resolver.Numero);

            //6. Se il fascicolo non esiste, creo il fascicolo
            if (idFolder == null)
            {
                //4. Creo il fascicolo
                var folderResponse = this._folderService.CreaFolder(this._resolver.IdDossier);
                idFolder = new IdFolder(folderResponse.objectId.value);
            }
            this._logger.Info("Fine fascicolazione su dossier");
            return idFolder;
        }

        private string CalcolaDescrizioneDossier(string template, Dictionary<string, List<Dyn2Dato>> datiContesto)
        {
            if (string.IsNullOrEmpty(template))
            {
                throw new ConfigurationErrorsException("Errore, non è stato configurato il template della descrizione del dossier");
            }
            if (datiContesto == null || datiContesto.Count == 0)
            {
                throw new ConfigurationErrorsException("Errore, impossibile calcolare la descrizione per il dossier");
            }

            this._logger.Info($"datiContesto.ContainsKey(Contesti.Soggetto): {datiContesto.ContainsKey(Contesti.Soggetto)}");

            return new TemplateContestiResolver().SostituisciTemplate(template, datiContesto);
        }

        private string CalcolaParolaChiave(Dictionary<string, List<Dyn2Dato>> datiContesto)
        {
            if (datiContesto == null || datiContesto.Count == 0)
            {
                throw new ConfigurationErrorsException("Errore, impossibile calcolare la parola chiave per il nuovo dossier");
            }

            //1. se la partita iva della sede è presente utilizzo quella
            if (datiContesto.ContainsKey(Contesti.PartitaIva) && datiContesto[Contesti.PartitaIva].Any(x => !String.IsNullOrEmpty(x.Valore)))
            {
                return datiContesto[Contesti.PartitaIva].First(x => !String.IsNullOrEmpty(x.Valore)).Valore;
            }

            //1. se il codice fiscale della sede è presente utilizzo quello
            if (datiContesto.ContainsKey(Contesti.CodiceFiscale) && datiContesto[Contesti.CodiceFiscale].Any(x => !String.IsNullOrEmpty(x.Valore)))
            {
                return datiContesto[Contesti.CodiceFiscale].First(x => !String.IsNullOrEmpty(x.Valore)).Valore;
            }

            return null;
        }
    }
}
