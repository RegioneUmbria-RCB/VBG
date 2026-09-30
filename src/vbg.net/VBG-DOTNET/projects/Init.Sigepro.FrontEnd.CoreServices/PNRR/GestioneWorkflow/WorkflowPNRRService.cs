using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WsInterventi;
using VBG.Shared.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.CoreServices.PNRR.GestioneWorkflow
{
    public class WorkflowPNRRService : IWorkflowPNRRService
    {
        public class WorkflowSection : IWorkflowSection
        {
            public WorkflowSection(string titolo, string descrizione, bool nascondiMenuNavigazione)
            {
                this.Titolo = titolo;
                this.Descrizione = descrizione;
                this.NascondiMenuNavigazione = nascondiMenuNavigazione;
            }
            public string Titolo { get; } = "";
            public string Descrizione { get; }
            public bool NascondiMenuNavigazione { get; }
            public IEnumerable<WorkflowStep> Steps { get; init; } = Enumerable.Empty<WorkflowStep>();
        }

        public class Workflow : IWorkflowInstance
        {
            private readonly WorkflowSection[] _sections = Array.Empty<WorkflowSection>();

            public Workflow(WorkflowV3Dto wf)
            {
                this._sections = wf.Sezioni.Select(section => new WorkflowSection(section.Titolo, section.Descrizione, section.NascondiMenuNavigazione)
                {
                    Steps = section.Steps.Select(step => new WorkflowStep
                    {
                        Titolo = step.Titolo,
                        Descrizione = step.Descrizione,
                        StepType = this.MapStepType(step.Control),
                        NascondiSuRiepilogo = step.NascondiSuRiepilogo,
                        Properties = step.Properties?.Select(prop => new WorkflowStep.StepProperty
                        {
                            Name = prop.Nome,
                            Value = prop.Valore
                        })?.ToArray() ?? Array.Empty<WorkflowStep.StepProperty>()
                    }).Where(x => x.StepType != StepTypeEnum.Discard)
                }).ToArray();
            }

            private StepTypeEnum MapStepType(string control)
            {
                var stepMap = new Dictionary<string, StepTypeEnum>
                {
                    {"~/reserved/inserimentoistanza/benvenuto.aspx", StepTypeEnum.Discard},
                    {"~/reserved/inserimentoistanza/gestioneprivacy.aspx" , StepTypeEnum.InformativaPrivacy},
                    {"~/reserved/inserimentoistanza/gestioneinterventi.aspx" , StepTypeEnum.Discard},
                    {"~/reserved/inserimentoistanza/gestioneinterventiateco.aspx" , StepTypeEnum.Discard},
                    {"~/reserved/inserimentoistanza/gestioneendo.aspx" , StepTypeEnum.Discard},
                    {"~/reserved/inserimentoistanza/gestioneendov2.aspx" , StepTypeEnum.Discard},
                    {"~/reserved/inserimentoistanza/gestioneendoverificascia.aspx" , StepTypeEnum.Discard},
                    {"~/reserved/inserimentoistanza/gestioneanagrafiche.aspx" , StepTypeEnum.Anagrafiche},
                    {"~/reserved/inserimentoistanza/gestioneanagrafichesemplificata.aspx" , StepTypeEnum.Anagrafiche},
                    {"~/reserved/inserimentoistanza/gestionedatidinamici.aspx" , StepTypeEnum.ListaSchedeDinamiche},
                    {"~/reserved/inserimentoistanza/gestionesottoscriventi.aspx" , StepTypeEnum.Sottoscrittori},
                    {"~/reserved/inserimentoistanza/gestionedelegaatrasmettere.aspx" , StepTypeEnum.DelegaATrasmettere},
                    {"~/reserved/inserimentoistanza/gestionedomicilioelettronico.aspx" , StepTypeEnum.DomicilioElettronico},
                    {"~/reserved/inserimentoistanza/gestioneendopresenti.aspx" , StepTypeEnum.Discard},
                    {"~/reserved/inserimentoistanza/gestioneendopresenti_allegati.aspx" , StepTypeEnum.Discard},
                    {"~/reserved/inserimentoistanza/testo-libero.aspx" , StepTypeEnum.TestoLibero},
                    {"~/reserved/inserimentoistanza/gestioneinformativa.aspx" , StepTypeEnum.Informativa},
                    {"~/reserved/inserimentoistanza/gestioneallegatiintervento.aspx" , StepTypeEnum.AllegatiIntervento},
                    {"~/reserved/inserimentoistanza/gestioneallegatidatidinamici.aspx" , StepTypeEnum.AllegatiSchedeDinamiche},
                    {"~/reserved/inserimentoistanza/gestioneallegatiendo.aspx" , StepTypeEnum.AllegatiEndo},
                    {"~/reserved/inserimentoistanza/gestionestradario.aspx" , StepTypeEnum.Localizzazioni},
                    {"~/reserved/inserimentoistanza/gestionedaticatastali.aspx" , StepTypeEnum.Discard},
                    {"~/reserved/inserimentoistanza/gestionelocalizzazioni.aspx" , StepTypeEnum.Localizzazioni},
                    {"~/reserved/inserimentoistanza/gestionelocalizzazionisit.aspx" , StepTypeEnum.LocalizzazioniSIT},
                    {"~/reserved/inserimentoistanza/datiistanzace.aspx" , StepTypeEnum.DatiIstanza},
                    {"~/reserved/inserimentoistanza/datiistanza.aspx" , StepTypeEnum.DatiIstanza},
                    {"~/reserved/inserimentoistanza/gestioneprocure.aspx" , StepTypeEnum.Procure},
                    {"~/reserved/inserimentoistanza/gestioneoneri.aspx" , StepTypeEnum.Discard},
                    {"~/reserved/inserimentoistanza/gestioneammissibilitaintervento.aspx" , StepTypeEnum.AmmissibilitaIntervento },
                    {"~/reserved/inserimentoistanza/riepilogodomandahtml.aspx" , StepTypeEnum.Discard},
                    {"~/reserved/inserimentoistanza/pagamenti/verificastatopagamentinodopagamenti.aspx", StepTypeEnum.Discard},
                    {"~/reserved/inserimentoistanza/pagamenti/gestionepagamentinodopagamenti.aspx", StepTypeEnum.Discard},
                    {"~/reserved/inserimentoistanza/pagamenti/pagamentonodopagamenti.aspx", StepTypeEnum.Discard},
                    {"~/reserved/inserimentoistanza/gestionefilesexcel.aspx", StepTypeEnum.FilesExcel },
                    {"~/reserved/inserimentoistanza/triesteaccessoatti.aspx", StepTypeEnum.TriesteAccessoAtti },
                    {"~/reserved/inserimentoistanza/gestionetransiti.aspx", StepTypeEnum.GestioneTransiti},
                    {"~/reserved/inserimentoistanza/localizzazioni-modena/gestione-localizzazioni-modena.aspx", StepTypeEnum.LocalizzazioniModena},
                    {"~/reserved/inserimentoistanza/benvenutoldp.aspx", StepTypeEnum.LDPBenvenuto},
                    {"~/reserved/inserimentoistanza/gestioneallegatoldp.aspx", StepTypeEnum.LDPAllegato},
                    {"~/reserved/inserimentoistanza/integrazione-ldp-livorno.aspx",StepTypeEnum.LDPIntegrazioneLivorno},
                    {"~/reserved/inserimentoistanza/gestionelocalizzazionisitmodena.aspx", StepTypeEnum.LocalizzazioniSITModena},
                    {"~/reserved/inserimentoistanza/gestioneverificasoggettifirmatari.aspx", StepTypeEnum.VerificaSoggettiFirmatari}
                };

                if (stepMap.TryGetValue(control.ToLowerInvariant(), out var stepId))
                {
                    return stepId;
                }

                return StepTypeEnum.Unknown;
            }

            public IEnumerable<IWorkflowSection> Sections => this._sections;
        }

        private readonly IInterventiV3Service _interventiService;
        private readonly StepTypeMapperBase _stepTypeMapperBase;
        private readonly IApplicationCache _applicationCache;
        private readonly IAliasResolver _aliasResolver;
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;

        public WorkflowPNRRService(IInterventiV3Service interventiService, StepTypeMapperBase stepTypeMapperBase, IApplicationCache applicationCache, IAliasResolver aliasResolver, ISalvataggioDomandaStrategy salvataggioDomandaStrategy)
        {
            this._interventiService = interventiService;
            this._stepTypeMapperBase = stepTypeMapperBase;
            this._applicationCache = applicationCache;
            this._aliasResolver = aliasResolver;
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
        }

        public async Task<IWorkflowInstance> GetWorkflowByIdInterventoAsync(int idIntervento)
        {
            var cacheKey = $"WorkflowPNRRService.{this._aliasResolver.AliasComune}.{idIntervento}";

            return await this._applicationCache.GetOrAdd(cacheKey, async () =>
            {
                var wf = await this._interventiService.GetWorkflowDomandaOnLineByIdInterventoAsync(idIntervento);
                return new Workflow(wf);
            });
        }

        public async Task<IEnumerable<IWorkflowSection>> GetSezioniAsync(int idIntervento)
        {
            var wf = await this.GetWorkflowByIdInterventoAsync(idIntervento);

            return wf?.Sections ?? Enumerable.Empty<IWorkflowSection>();
        }

        public async Task<StepsFromSectionResponse> GetStepsPerSezioneAsync(int idIntervento, int sectionIndex)
        {
            var wf = await this.GetWorkflowByIdInterventoAsync(idIntervento);

            var totalSectionsCount = wf?.Sections.Count() ?? 0;

            if (wf != null && totalSectionsCount > sectionIndex)
            {
                var sezione = wf.Sections.ElementAt(sectionIndex);

                var steps = sezione.Steps.Select((x, idx) =>
                {
                    var mapping = this._stepTypeMapperBase.MapStep(x);

                    return new DescrittoreStep
                    {
                        Id = idx,
                        Titolo = x.Titolo,
                        Descrizione = x.Descrizione,
                        Type = mapping.Type,
                        Properties = mapping.Properties
                    };
                }).ToArray();

                return new StepsFromSectionResponse(sezione.Titolo, sezione.Descrizione, sezione.NascondiMenuNavigazione, steps, totalSectionsCount);
            }

            return new StepsFromSectionResponse("", "", false, Enumerable.Empty<DescrittoreStep>(), totalSectionsCount);
        }

        public async Task<IEnumerable<DescrittoreStep>> GetStepsPerRiepilogoAsync(int idIntervento)
        {
            var wf = await this.GetWorkflowByIdInterventoAsync(idIntervento);

            var steps = wf.Sections.SelectMany(s => s.Steps.Where(x => !x.NascondiSuRiepilogo));

            return steps.Select((x, idx) =>
            {
                var mapping = this._stepTypeMapperBase.MapStep(x);

                return new DescrittoreStep
                {
                    Id = idx,
                    Titolo = x.Titolo,
                    Descrizione = x.Descrizione,
                    Type = mapping.Type,
                    Properties = mapping.Properties
                };
            }).ToArray();
        }

        public async Task<IWorkflowInstance?> GetWorkflowByIdDomandaAsync(int idDomanda)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

            var idIntervento = domanda.ReadInterface?.AltriDati?.Intervento?.Codice;

            if (idIntervento.HasValue)
            {
                return await this.GetWorkflowByIdInterventoAsync(idIntervento.Value);
            }

            return null;
        }
    }
}
