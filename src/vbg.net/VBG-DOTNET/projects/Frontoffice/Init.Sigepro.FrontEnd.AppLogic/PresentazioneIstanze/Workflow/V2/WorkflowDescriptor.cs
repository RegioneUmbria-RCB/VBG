using Init.Sigepro.FrontEnd.AppLogic.Utils;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.V2
{
    public enum WorkflowSteps
    {
        Undefined,
        Benvenuto,
        GestionePrivacy,
        GestioneInterventi,
        GestioneInterventiAteco,
        GestioneEndoOld,
        GestioneEndo,
        GestioneAnagrafiche,
        GestioneAnagraficheSemplificata,
        GestioneDatiDinamici,
        GestioneSottoscriventi,
        GestioneDelegaATrasmettere,
        GestioneDomicilioElettronico,
        GestioneEndoPresenti,
        GestioneEndoPresentiAllegati,
        TestoLibero,
        GestioneInformativa,
        GestioneAllegatiIntervento,
        GestioneAllegatiDatiDinamici,
        GestioneAllegatiEndo,
        GestioneStradario,
        GestioneDatiCatastali,
        GestioneLocalizzazioni,
        GestioneLocalizzazioniSit,
        DatiIstanzaCe,
        DatiIstanza,
        GestioneProcure,
        GestioneOneri,
        GestioneAmmissibilitaIntervento,
        RiepilogoDomanda,
        VerificaStatoPagamenti,
        GestionePagamento,
        Pagamento,
        GestioneFilesExcel,
        GestioneAccessoAttiTrieste,
        GestioneTransiti,
        GestioneLocalizzazioniModena,
        BenvenutoLdp,
        GestioneAllegatoLdp,
        IntegrazioneLdpLivorno,
        GestioneLocalizzazioniSitModena,
        GestioneVerificaSoggettiFirmatari,
        GestioneEndoVerificaScia,
        GestioneLocalizzazioniCartografico,
        SsuGestioneProcedimenti,
        SsuGestioneAllegati,
        SsuGestioneSchede,
        SsuGestioneOneri,
        SsuGestionePagamenti,
        SsuRiepilogoDomanda,
        SsuGestioneAllegatiDatiDinamici,
        SsuVerificaDomanda,
        SsuGestioneAnagrafiche
    }

    public static class WorkflowDescriptor
    {
        public class StepDescriptor
        {
            public string Titolo { get; set; } = "";
            public string Descrizione { get; set; } = "";
            public string FrameworkPath { get; set; } = "";
            public string CorePath { get; set; } = "";
            public bool Deprecato { get; set; } = false; // Indica se il passo è obsoleto e non dovrebbe essere più utilizzato
            public bool IsMergePoint { get; set; } = false; // Indica se il passo è un merge point nella procedura di workflow
        }

        private readonly static Dictionary<WorkflowSteps, StepDescriptor> Steps = new Dictionary<WorkflowSteps, StepDescriptor>
        {
            {WorkflowSteps.Benvenuto, new StepDescriptor{ Titolo = "Benvenuto", Descrizione = "Pagina di benvenuto del workflow", FrameworkPath = "~/reserved/inserimentoistanza/benvenuto.aspx", CorePath = "benvenuto" } },
            {WorkflowSteps.GestionePrivacy, new StepDescriptor{ Titolo = "Gestione Privacy", Descrizione = "Pagina per la gestione della privacy", FrameworkPath = "~/reserved/inserimentoistanza/gestioneprivacy.aspx", CorePath = "gestione-privacy" } },
            {WorkflowSteps.GestioneInterventi, new StepDescriptor{ IsMergePoint = true, Titolo = "Gestione Interventi", Descrizione = "Pagina per la gestione degli interventi", FrameworkPath = "~/reserved/inserimentoistanza/gestioneinterventi.aspx", CorePath = "gestione-interventi" } },
            {WorkflowSteps.GestioneInterventiAteco, new StepDescriptor{ IsMergePoint = true, Titolo = "Gestione Interventi ATECO", Descrizione = "Pagina per la gestione degli interventi ATECO", FrameworkPath = "~/reserved/inserimentoistanza/gestioneinterventiateco.aspx", CorePath = "gestione-interventi" } },
            {WorkflowSteps.GestioneEndoOld, new StepDescriptor{ Deprecato=true,  Titolo = "Gestione Endo (deprecato)",Descrizione = "Pagina per la gestione degli endo (deprecato)", FrameworkPath = "~/reserved/inserimentoistanza/gestioneendo.aspx", CorePath = "gestione-endo" } },
            {WorkflowSteps.GestioneEndo, new StepDescriptor{ Titolo = "Gestione Endo", Descrizione = "Pagina per la gestione degli endo", FrameworkPath = "~/reserved/inserimentoistanza/gestioneendov2.aspx", CorePath = "gestione-endo" } },
            {WorkflowSteps.GestioneEndoVerificaScia, new StepDescriptor{ Titolo = "Verifica Procedura", Descrizione = "Verifica procedura della domanda in base agli endoprocedimenti selezionati", FrameworkPath = "~/reserved/inserimentoistanza/gestioneendoverificascia.aspx", CorePath = "gestione-endo-verifica-scia" } },
            {WorkflowSteps.GestioneAnagrafiche, new StepDescriptor{ Titolo = "Gestione Anagrafiche", Descrizione = "Pagina per la gestione delle anagrafiche", FrameworkPath = "~/reserved/inserimentoistanza/gestioneanagrafiche.aspx", CorePath = "gestione-anagrafiche" } },
            {WorkflowSteps.GestioneAnagraficheSemplificata, new StepDescriptor{ Deprecato=true, Titolo = "Gestione Anagrafiche Semplificata", Descrizione = "Pagina per la gestione delle anagrafiche semplificate", FrameworkPath = "~/reserved/inserimentoistanza/gestioneanagrafichesemplificata.aspx", CorePath = "gestione-anagrafiche" } },
            {WorkflowSteps.GestioneDatiDinamici, new StepDescriptor{ Titolo = "Gestione Dati Dinamici", Descrizione = "Pagina per la gestione dei dati dinamici", FrameworkPath = "~/reserved/inserimentoistanza/gestionedatidinamici.aspx", CorePath = "gestione-dati-dinamici" } },
            {WorkflowSteps.GestioneSottoscriventi, new StepDescriptor{ Titolo = "Gestione Sottoscriventi", Descrizione = "Pagina per la gestione dei sottoscriventi", FrameworkPath = "~/reserved/inserimentoistanza/gestionesottoscriventi.aspx", CorePath = "gestione-sottoscriventi" } },
            {WorkflowSteps.GestioneDelegaATrasmettere, new StepDescriptor{ Titolo = "Gestione Delega a Trasmettere", Descrizione = "Pagina per la gestione della delega a trasmettere", FrameworkPath = "~/reserved/inserimentoistanza/gestionedelegaatrasmettere.aspx", CorePath = "gestione-delega-a-trasmettere" } },
            {WorkflowSteps.GestioneDomicilioElettronico, new StepDescriptor{ Titolo = "Gestione Domicilio Elettronico", Descrizione = "Pagina per la gestione del domicilio elettronico", FrameworkPath = "~/reserved/inserimentoistanza/gestionedomicilioelettronico.aspx", CorePath = "gestione-domicilio-elettronico" } },
            {WorkflowSteps.GestioneEndoPresenti, new StepDescriptor{ Titolo = "Gestione Endo Presenti", Descrizione = "Pagina per la gestione degli endo presenti", FrameworkPath = "~/reserved/inserimentoistanza/gestioneendopresenti.aspx", CorePath = "gestione-endo-presenti" } },
            {WorkflowSteps.GestioneEndoPresentiAllegati, new StepDescriptor{ Deprecato=true, Titolo = "Gestione Allegati Endo Presenti", Descrizione = "Pagina per la gestione degli allegati degli endo presenti", FrameworkPath = "~/reserved/inserimentoistanza/gestioneendopresenti_allegati.aspx", CorePath = "null" } },
            {WorkflowSteps.TestoLibero, new StepDescriptor{ Titolo = "Testo Libero", Descrizione = "Pagina per l'inserimento di testo libero", FrameworkPath = "~/reserved/inserimentoistanza/testo-libero.aspx", CorePath = "testo-libero" } },
            {WorkflowSteps.GestioneInformativa, new StepDescriptor{ Titolo = "Gestione Informativa", Descrizione = "Pagina per la gestione dell'informativa", FrameworkPath = "~/reserved/inserimentoistanza/gestioneinformativa.aspx", CorePath = "gestione-informativa" } },
            {WorkflowSteps.GestioneAllegatiIntervento, new StepDescriptor{ Titolo = "Gestione Allegati Intervento", Descrizione = "Pagina per la gestione degli allegati dell'intervento", FrameworkPath = "~/reserved/inserimentoistanza/gestioneallegatiintervento.aspx", CorePath = "allegati-intervento" } },
            {WorkflowSteps.GestioneAllegatiDatiDinamici, new StepDescriptor{ Titolo = "Gestione Allegati Dati Dinamici", Descrizione = "Pagina per la gestione degli allegati dei dati dinamici", FrameworkPath = "~/reserved/inserimentoistanza/gestioneallegatidatidinamici.aspx", CorePath = "allegati-dati-dinamici" } },
            {WorkflowSteps.GestioneAllegatiEndo, new StepDescriptor{ Titolo = "Gestione Allegati Endo", Descrizione = "Pagina per la gestione degli allegati degli endo", FrameworkPath = "~/reserved/inserimentoistanza/gestioneallegatiendo.aspx", CorePath = "allegati-endo" } },
            {WorkflowSteps.GestioneStradario, new StepDescriptor{ Deprecato=true, Titolo = "Gestione Stradario", Descrizione = "Pagina per la gestione dello stradario", FrameworkPath = "~/reserved/inserimentoistanza/gestionestradario.aspx", CorePath = "gestione-localizzazioni" } },
            {WorkflowSteps.GestioneDatiCatastali, new StepDescriptor{  Deprecato=true, Titolo = "Gestione Dati Catastali", Descrizione = "Pagina per la gestione dei dati catastali", FrameworkPath = "~/reserved/inserimentoistanza/gestionedaticatastali.aspx", CorePath = "null" } },
            {WorkflowSteps.GestioneLocalizzazioni, new StepDescriptor{ Titolo = "Gestione Localizzazioni", Descrizione = "Pagina per la gestione delle localizzazioni", FrameworkPath = "~/reserved/inserimentoistanza/gestionelocalizzazioni.aspx", CorePath = "gestione-localizzazioni" } },
            {WorkflowSteps.GestioneLocalizzazioniSit, new StepDescriptor{ Titolo = "Gestione Localizzazioni SIT", Descrizione = "Pagina per la gestione delle localizzazioni SIT", FrameworkPath = "~/reserved/inserimentoistanza/gestionelocalizzazionisit.aspx", CorePath = "gestione-localizzazioni-sit" } },
            {WorkflowSteps.DatiIstanzaCe, new StepDescriptor{ Titolo = "Dati Istanza CE", Descrizione = "Pagina per la gestione dei dati dell'istanza CE", FrameworkPath = "~/reserved/inserimentoistanza/datiistanzace.aspx", CorePath = "dati-istanza-ce" } },
            {WorkflowSteps.DatiIstanza, new StepDescriptor{ Deprecato=true,  Titolo = "Dati Istanza", Descrizione = "Pagina per la gestione dei dati dell'istanza", FrameworkPath = "~/reserved/inserimentoistanza/datiistanza.aspx", CorePath = "dati-istanza-ce" } },
            {WorkflowSteps.GestioneProcure, new StepDescriptor{ Titolo = "Gestione Procure", Descrizione = "Pagina per la gestione delle procure", FrameworkPath = "~/reserved/inserimentoistanza/gestioneprocure.aspx", CorePath = "gestione-procure" } },
            {WorkflowSteps.GestioneOneri, new StepDescriptor{ Titolo = "Gestione Oneri", Descrizione = "Pagina per la gestione degli oneri", FrameworkPath = "~/reserved/inserimentoistanza/gestioneoneri.aspx", CorePath = "gestione-oneri" } },
            {WorkflowSteps.GestioneAmmissibilitaIntervento, new StepDescriptor{ Titolo = "Gestione Ammissibilità Intervento", Descrizione = "Pagina per la gestione dell'ammissibilità dell'intervento", FrameworkPath = "~/reserved/inserimentoistanza/gestioneammissibilitaintervento.aspx", CorePath = "gestione-ammissibilita-intervento" } },
            {WorkflowSteps.RiepilogoDomanda, new StepDescriptor{ Titolo = "Riepilogo Domanda", Descrizione = "Pagina per il riepilogo della domanda", FrameworkPath = "~/reserved/inserimentoistanza/riepilogodomandahtml.aspx", CorePath = "riepilogo-domanda" } },
            {WorkflowSteps.VerificaStatoPagamenti, new StepDescriptor{ Titolo = "Verifica Stato Pagamenti", Descrizione = "Pagina per la verifica dello stato dei pagamenti", FrameworkPath = "~/reserved/inserimentoistanza/pagamenti/verificastatopagamentinodopagamenti.aspx", CorePath = "pagamenti/verifica-stato-pagamenti" } },
            {WorkflowSteps.GestionePagamento, new StepDescriptor{ Titolo = "Gestione Pagamento", Descrizione = "Pagina per la gestione dei pagamenti", FrameworkPath = "~/reserved/inserimentoistanza/pagamenti/gestionepagamentinodopagamenti.aspx", CorePath = "pagamenti/gestione-pagamenti" } },
            {WorkflowSteps.Pagamento, new StepDescriptor{ Titolo = "Pagamento", Descrizione = "Pagina per il pagamento", FrameworkPath = "~/reserved/inserimentoistanza/pagamenti/pagamentonodopagamenti.aspx", CorePath = "pagamenti/pagamento" } },
            {WorkflowSteps.GestioneFilesExcel, new StepDescriptor{ Titolo = "Gestione Files Excel", Descrizione = "Pagina per la gestione dei files Excel", FrameworkPath = "~/reserved/inserimentoistanza/gestionefilesexcel.aspx", CorePath = "gestione-files-excel" } },
            {WorkflowSteps.GestioneAccessoAttiTrieste, new StepDescriptor{ Titolo = "Gestione Accesso Atti Trieste", Descrizione = "Pagina per la gestione dell'accesso agli atti a Trieste", FrameworkPath = "~/reserved/inserimentoistanza/triesteaccessoatti.aspx", CorePath = "gestione-accesso-atti-trieste" } },
            {WorkflowSteps.GestioneTransiti, new StepDescriptor{ Titolo = "Gestione Transiti", Descrizione = "Pagina per la gestione dei transiti", FrameworkPath = "~/reserved/inserimentoistanza/gestionetransiti.aspx", CorePath = "gestione-transiti" } },
            {WorkflowSteps.GestioneLocalizzazioniModena, new StepDescriptor{ Titolo = "Gestione Localizzazioni Modena", Descrizione = "Pagina per la gestione delle localizzazioni a Modena", FrameworkPath = "~/reserved/inserimentoistanza/localizzazioni-modena/gestione-localizzazioni-modena.aspx", CorePath = "gestione-localizzazioni-modena" } },
            {WorkflowSteps.BenvenutoLdp, new StepDescriptor{ Titolo = "Benvenuto LDP", Descrizione = "Pagina di benvenuto del workflow LDP", FrameworkPath = "~/reserved/inserimentoistanza/benvenutoldp.aspx", CorePath = "benvenuto-ldp" } },
            {WorkflowSteps.GestioneAllegatoLdp, new StepDescriptor{ Titolo = "Gestione Allegato LDP", Descrizione = "Pagina per la gestione degli allegati LDP", FrameworkPath = "~/reserved/inserimentoistanza/gestioneallegatoldp.aspx", CorePath = "allegato-ldp" } },
            {WorkflowSteps.IntegrazioneLdpLivorno, new StepDescriptor{ Titolo = "Integrazione LDP Livorno", Descrizione = "Pagina per l'integrazione LDP a Livorno", FrameworkPath = "~/reserved/inserimentoistanza/integrazione-ldp-livorno.aspx", CorePath = "integrazione-ldp-livorno" } },
            {WorkflowSteps.GestioneLocalizzazioniSitModena, new StepDescriptor{ Titolo = "Gestione Localizzazioni SIT Modena", Descrizione = "Pagina per la gestione delle localizzazioni SIT a Modena", FrameworkPath = "~/reserved/inserimentoistanza/gestionelocalizzazionisitmodena.aspx", CorePath = "gestione-localizzazioni-sit-modena" } },
            {WorkflowSteps.GestioneVerificaSoggettiFirmatari, new StepDescriptor{ Titolo = "Gestione Verifica Soggetti Firmatari", Descrizione = "Pagina per la verifica dei soggetti firmatari", FrameworkPath = "~/reserved/inserimentoistanza/gestioneverificasoggettifirmatari.aspx", CorePath = "gestione-verifica-soggetti-firmatari" } },
            {WorkflowSteps.GestioneLocalizzazioniCartografico, new StepDescriptor{ Titolo = "Gestione Localizzazioni su Cartografico", Descrizione = "Pagina per la gestione delle localizzazioni in modalità integrata con un cartografico", FrameworkPath = "~/reserved/inserimentoistanza/gestionelocalizzazionicartografico.aspx", CorePath = "gestione-localizzazioni-cartografico"  } },
            {WorkflowSteps.SsuGestioneProcedimenti, new StepDescriptor{ Titolo = "SSU Gestione Procedimenti", Descrizione = "Pagina per la gestione dei procedimenti SSU", FrameworkPath = "~/reserved/inserimentoistanza/404.aspx", CorePath = "ssu/gestione-procedimenti" }   },
            {WorkflowSteps.SsuGestioneAllegati, new StepDescriptor{ Titolo = "SSU Gestione Allegati", Descrizione = "Pagina per la gestione degli allegati SSU", FrameworkPath = "~/reserved/inserimentoistanza/405.aspx", CorePath = "ssu/gestione-allegati" }   },
            {WorkflowSteps.SsuGestioneSchede, new StepDescriptor{ Titolo = "SSU Gestione Schede", Descrizione = "Gestione delle schede dinamiche in modalità SSU", FrameworkPath = "~/reserved/inserimentoistanza/406.aspx", CorePath = "ssu/gestione-schede" }   },
            {WorkflowSteps.SsuGestioneOneri, new StepDescriptor{ Titolo = "SSU Gestione Oneri", Descrizione = "Gestione degli oneri in modalità SSU", FrameworkPath = "~/reserved/inserimentoistanza/407.aspx", CorePath = "ssu/gestione-oneri" }   },
            {WorkflowSteps.SsuGestionePagamenti, new StepDescriptor{ Titolo = "SSU Gestione Pagamenti", Descrizione = "Gestione dei pagamenti in modalità SSU", FrameworkPath = "~/reserved/inserimentoistanza/408.aspx", CorePath = "ssu/gestione-pagamenti" }   },
            {WorkflowSteps.SsuRiepilogoDomanda, new StepDescriptor{ Titolo = "SSU Riepilogo Domanda", Descrizione = "Pagina per il riepilogo della domanda in modalità SSU", FrameworkPath = "~/reserved/inserimentoistanza/409.aspx", CorePath = "ssu/riepilogo-domanda" }   },
            {WorkflowSteps.SsuGestioneAllegatiDatiDinamici, new StepDescriptor{ Titolo = "SSU Gestione Allegati Dati Dinamici", Descrizione = "Pagina per la gestione degli allegati dei dati dinamici in modalità SSU", FrameworkPath = "~/reserved/inserimentoistanza/410.aspx", CorePath = "ssu/allegati-dati-dinamici" }   },
            {WorkflowSteps.SsuVerificaDomanda, new StepDescriptor{ Titolo = "SSU Verifica Domanda", Descrizione = "Pagina per la verifica della domanda in modalità SSU", FrameworkPath = "~/reserved/inserimentoistanza/411.aspx", CorePath = "ssu/verifica-domanda" }   },
            {WorkflowSteps.SsuGestioneAnagrafiche, new StepDescriptor{ Titolo = "SSU Gestione Anagrafiche", Descrizione = "Pagina per la gestione delle anagrafiche in modalità SSU", FrameworkPath = "~/reserved/inserimentoistanza/412.aspx", CorePath = "ssu/gestione-anagrafiche" }   }
        };

        public readonly static Dictionary<string, WorkflowSteps> _frameworkToStepIdMap = new Dictionary<string, WorkflowSteps>();
        public readonly static Dictionary<string, WorkflowSteps> _coreToStepIdMap = new Dictionary<string, WorkflowSteps>();

        static WorkflowDescriptor()
        {
            foreach (var key in Steps.Keys)
            {
                _frameworkToStepIdMap.Add(Steps[key].FrameworkPath, key);

                if (!_coreToStepIdMap.ContainsKey(Steps[key].CorePath))
                {
                    _coreToStepIdMap.Add(Steps[key].CorePath, key);
                }
            }
        }

        public static WorkflowSteps GetStepIdByFrameworkPath(string frameworkPath)
        {
            if (_frameworkToStepIdMap.TryGetValue(frameworkPath.ToLower(), out var stepId))
            {
                return stepId;
            }
            return WorkflowSteps.Undefined;
        }

        public static WorkflowSteps? GetStepIdByCorePath(string corePath)
        {
            if (_coreToStepIdMap.TryGetValue(corePath.ToLower(), out var stepId))
            {
                return stepId;
            }
            return WorkflowSteps.Undefined;
        }

        public static bool IsMergePoint(WorkflowSteps stepId)
        {
            if (Steps.TryGetValue(stepId, out var stepDescriptor))
            {
                return stepDescriptor.IsMergePoint;
            }
            return false;
        }

        public static string GetStepUrlById(WorkflowSteps? identifier)
        {
            var step = Steps[identifier ?? WorkflowSteps.Undefined];

            return (RuntimePlatform.Current == RuntimePlatformEnum.NetCore) ?
                $"inserimento-istanza/{step.CorePath}" :
                step.FrameworkPath;
        }

        public static StepDescriptor GetStepDescriptor(WorkflowSteps stepId)
        {
            if (Steps.TryGetValue(stepId, out var stepDescriptor))
            {
                return stepDescriptor;
            }
            throw new KeyNotFoundException($"Step {stepId} non trovato nel workflow.");
        }

        public static IEnumerable<WorkflowSteps> GetStepDisponibili()
        {
            return Steps.Keys;
        }
    }
}
