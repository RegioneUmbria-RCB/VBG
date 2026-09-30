using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.AreeUsoPubblicoLivorno
{
    public class AggiornaDatiOccupazioneCommand
    {
        public class CampiIntervallo
        {
            public readonly int IdCampoData;
            public readonly int IdCampoOra;

            public CampiIntervallo(int idCampoData, int idCampoOra)
            {
                this.IdCampoData = idCampoData;
                this.IdCampoOra = idCampoOra;
            }
        }

        public readonly int IdDomanda;
        public readonly CampiIntervallo DataInizio;
        public readonly CampiIntervallo DataFine;
        public readonly int IdCampoDescrizioneOccupazione;
        public readonly int IdCampoFrequenzaOccupazione;

        public AggiornaDatiOccupazioneCommand(int idDomanda, CampiIntervallo dataInizio, CampiIntervallo dataFine, int campoDescrizioneOccupazione, int idCampoFrequenzaOccupazione)
        {
            this.IdDomanda = idDomanda;
            this.DataInizio = dataInizio;
            this.DataFine = dataFine;
            this.IdCampoDescrizioneOccupazione = campoDescrizioneOccupazione;
            this.IdCampoFrequenzaOccupazione = idCampoFrequenzaOccupazione;
        }
    }
}
