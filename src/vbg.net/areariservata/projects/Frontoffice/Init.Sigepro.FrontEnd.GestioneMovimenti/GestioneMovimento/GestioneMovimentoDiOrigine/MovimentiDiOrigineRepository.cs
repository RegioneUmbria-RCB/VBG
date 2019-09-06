using AutoMapper;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine
{
    public class MovimentiDiOrigineRepository : IMovimentiDiOrigineRepository
    {
        MovimentiBackofficeServiceCreator _serviceCreator;

        public MovimentiDiOrigineRepository(MovimentiBackofficeServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        public MovimentoDiOrigine GetById(MovimentoDaEffettuare movimentoDaeffettuare)
        {
            var movimentoDiOrigine = GetById(movimentoDaeffettuare.IdMovimentoDiOrigine);
            var movimentoDaEffettuare = GetById(movimentoDaeffettuare.Id);

            if (movimentoDaEffettuare.PubblicaSchede)
            {
                movimentoDiOrigine.SchedeDinamiche = movimentoDaEffettuare.SchedeDinamiche;
            }

            return movimentoDiOrigine;
        }

        public MovimentoDiOrigine GetByIdHackUsaSoloPerCreazioneMovimento(int idMovimento)
        {
            return GetById(idMovimento);
        }

        private MovimentoDiOrigine GetById(int idMovimento)
        {
            using (var svc = this._serviceCreator.CreateClient())
            {
                return Mapper.Map<DatiMovimentoDaEffettuareDto, MovimentoDiOrigine>(svc.Service.GetMovimento(svc.Token, idMovimento.ToString()));
            }
        }

    }
}
