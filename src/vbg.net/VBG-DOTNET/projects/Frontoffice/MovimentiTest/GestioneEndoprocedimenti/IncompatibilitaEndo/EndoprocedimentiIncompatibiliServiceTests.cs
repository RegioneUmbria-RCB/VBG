// -----------------------------------------------------------------------
// <copyright file="EndoprocedimentiIncompatibiliServiceTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Xunit;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;
using VBG.Frontend.AppLogic.WsAnagraficheService;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;

namespace MovimentiTest.GestioneEndoprocedimenti.IncompatibilitaEndo
{
    public class EndoprocedimentiIncompatibiliServiceTests
    {
        public class EndoprocedimentiIncompatibiliRepository_Fake : IEndoprocedimentiIncompatibiliRepository
        {
            public IEnumerable<EndoprocedimentoIncompatibileDto> Result;
            public string NaturaBase;

            public IEnumerable<EndoprocedimentoIncompatibileDto> GetEndoprocedimentiIncompatibili(int[] listaIdEndoAttivati)
            {
                return this.Result;
            }

            public string GetNaturaBaseDaidEndoprocedimento(int codiceInventario)
            {
                return NaturaBase;
            }
        }

        EndoprocedimentiIncompatibiliRepository_Fake _repository;
        EndoprocedimentiIncompatibiliService _svc;

        public EndoprocedimentiIncompatibiliServiceTests()
        {
            this._repository = new EndoprocedimentiIncompatibiliRepository_Fake();
            this._svc = new EndoprocedimentiIncompatibiliService(this._repository);
        }

        [Fact]
        public void Quando_esistono_piu_endo_incompatibili_con_un_endo_questi_vengono_raggruppati()
        {
            _repository.Result = new EndoprocedimentoIncompatibileDto[]{
                new EndoprocedimentoIncompatibileDto{CodiceEndoprocedimento = 1 , CodiceEndoprocedimentoIncompatibile = 2},
                new EndoprocedimentoIncompatibileDto{CodiceEndoprocedimento = 1 , CodiceEndoprocedimentoIncompatibile = 3},
            };

            var listaEndoAttivati = new int[] { 1, 2, 3 };

            var result = this._svc.GetEndoprocedimentiIncompatibili(listaEndoAttivati);

            Assert.Single(result);
            Assert.Equal(1, result.ElementAt(0).Endo);
            Assert.Equal(2, result.ElementAt(0).EndoIncompatibili.Count());
            Assert.Equal(2, result.ElementAt(0).EndoIncompatibili.ElementAt(0));
            Assert.Equal(3, result.ElementAt(0).EndoIncompatibili.ElementAt(1));
        }

        [Fact]
        public void Endo_incompatibili_con_endo_diversi_restituiscono_piu_di_un_elemento()
        {
            _repository.Result = new EndoprocedimentoIncompatibileDto[]{
                new EndoprocedimentoIncompatibileDto{CodiceEndoprocedimento = 1 , CodiceEndoprocedimentoIncompatibile = 2},
                new EndoprocedimentoIncompatibileDto{CodiceEndoprocedimento = 3 , CodiceEndoprocedimentoIncompatibile = 4},
            };

            var listaEndoAttivati = new int[] { 1, 2, 3, 4 };

            var result = this._svc.GetEndoprocedimentiIncompatibili(listaEndoAttivati);

            Assert.Equal(2, result.Count());
            Assert.Equal(1, result.ElementAt(0).Endo);
            Assert.Single(result.ElementAt(0).EndoIncompatibili);
            Assert.Equal(2, result.ElementAt(0).EndoIncompatibili.ElementAt(0));

            Assert.Equal(3, result.ElementAt(1).Endo);
            Assert.Single(result.ElementAt(1).EndoIncompatibili);
            Assert.Equal(4, result.ElementAt(1).EndoIncompatibili.ElementAt(0));
        }
    }
}
