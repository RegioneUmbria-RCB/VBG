using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.Modena
{
    public class OpzioniSalvataggioLocalizzazioniModena
    {
        public readonly int IdStradarioDefault;
        public readonly string IdCatastoDefault;
        public readonly string NomeCatastoDefault;
        public readonly int IdCampoParticelleManualiPresenti;

        public OpzioniSalvataggioLocalizzazioniModena(int idStradarioDefault, string idCatastoDefault, string nomeCatastoDefault, int idCampoParticelleManualiPresenti)
        {
            IdStradarioDefault = idStradarioDefault;
            IdCatastoDefault = idCatastoDefault;
            NomeCatastoDefault = nomeCatastoDefault;
            IdCampoParticelleManualiPresenti = idCampoParticelleManualiPresenti;
        }


    }
}
