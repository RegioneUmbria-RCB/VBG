using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneWorkflowDomandaOnLine
{
    [DataContract]
    public class WorkflowStepPropertyV3
    {
        [DataMember]
        public string Nome { get; set; }
        [DataMember]
        public string Valore { get; set; }
    }

    [DataContract]
    public class StepWorkflowV3
    {
        [DataMember]
        public string Titolo { get; set; }
        [DataMember]
        public string Descrizione { get; set; }
        [DataMember]
        public string Control { get; set; }
        [DataMember]
        public bool NascondiSuRiepilogo { get; set; }
        [DataMember]
        public List<WorkflowStepPropertyV3> Properties { get; set; } = new List<WorkflowStepPropertyV3>();
    }


    [DataContract]
    public class SezioneWorkflowV3
    {
        [DataMember]
        public string Id { get; set; }
        [DataMember]
        public string Titolo { get; set; } = "";
        [DataMember]
        public string Descrizione { get; set; } = "";
        [DataMember]
        public bool NascondiMenuNavigazione { get; set; } = false;
        [DataMember]
        public List<StepWorkflowV3> Steps { get; set; } = new List<StepWorkflowV3>();
    }

    [DataContract]
    public class WorkflowV3Dto
    {
        [DataMember]
        public List<SezioneWorkflowV3> Sezioni { get; set; } = new List<SezioneWorkflowV3>();

        public WorkflowV3Dto()
        {

        }

        public WorkflowV3Dto(WorkflowStepsCollection xmlDeserializzato)
        {
            Dictionary<string, SezioneWorkflowV3> sezioni = new Dictionary<string, SezioneWorkflowV3>();
            SezioneWorkflowV3 elementiSenzaSezione = new SezioneWorkflowV3
            {
                Id = "-1",
                Titolo = "Steps senza una sezione configurata",
            };

            if (xmlDeserializzato.Sections?.Sections != null)
            {
                foreach (var s in xmlDeserializzato.Sections.Sections)
                {
                    sezioni.Add(s.Id, new SezioneWorkflowV3
                    {
                        Id = s.Id,
                        Titolo = s.Title,
                        Descrizione = s.Description,
                        NascondiMenuNavigazione = s.NascondiMenNavigazione
                    });
                }
            }

            foreach (var step in xmlDeserializzato.Steps)
            {
                if (!sezioni.TryGetValue(step.IdSezione ?? "-1", out var tmp))
                {
                    tmp = elementiSenzaSezione;
                }

                tmp.Steps.Add(new StepWorkflowV3
                {
                    Titolo = step.Title,
                    Descrizione = step.Description,
                    Control = step.Control,
                    NascondiSuRiepilogo = step.NascondiSuRiepilogo,
                    Properties = step.ControlProperties?.Select(x => new WorkflowStepPropertyV3
                    {
                        Nome = x.Name,
                        Valore = x.Value
                    }).ToList()
                });
            }

            if (elementiSenzaSezione.Steps.Count > 0)
            {
                if (sezioni.Count == 0)
                {
                    sezioni.Add(elementiSenzaSezione.Id, elementiSenzaSezione);
                }
                else
                {
                    sezioni.Last().Value.Steps.AddRange(elementiSenzaSezione.Steps);
                }
            }

            this.Sezioni = sezioni.Values.ToList();
        }
    }
}