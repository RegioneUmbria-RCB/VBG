using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine
{
    public interface IMovimentiDiOrigineRepository
    {
        MovimentoDiOrigine GetById(MovimentoDaEffettuare movimentoDaeffettuare);
        MovimentoDiOrigine GetByIdHackUsaSoloPerCreazioneMovimento(int id);
    }
}
