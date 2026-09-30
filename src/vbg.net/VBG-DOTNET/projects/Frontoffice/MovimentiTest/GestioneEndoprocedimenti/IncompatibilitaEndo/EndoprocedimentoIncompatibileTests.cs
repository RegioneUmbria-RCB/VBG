// -----------------------------------------------------------------------
// <copyright file="EndoprocedimentoIncompatibileTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;
using Xunit;

namespace MovimentiTest.GestioneEndoprocedimenti.IncompatibilitaEndo
{
    public class EndoprocedimentoIncompatibileTests
    {
        [Fact]
        public void Verifica_della_stringa_restituita_da_ToString_con_un_solo_endo_incompatibile()
        {
            var nomeEndo = "Endo1";
            var nomeEndoIncomp = "Endo2";
            var expected = "L'endoprocedimento \"" + nomeEndo + "\" non è compatibile con l'endoprocedimento \"" + nomeEndoIncomp + "\"";
            var arrEndoIncomp = new string[] { nomeEndoIncomp };

            var ei = new EndoprocedimentoIncompatibile(nomeEndo, arrEndoIncomp);

            Assert.Equal(expected, ei.ToString());
        }

        [Fact]
        public void Verifica_della_stringa_restituita_da_ToString_con_piu_di_un_endo_incompatibile()
        {
            var nomeEndo = "Endo1";
            var nomeEndoIncomp1 = "Endo2";
            var nomeEndoIncomp2 = "Endo2";
            var expected = "L'endoprocedimento \"" + nomeEndo + "\" non è compatibile con i seguenti endoprocedeimenti: \"" + nomeEndoIncomp1 + "\", \"" + nomeEndoIncomp2 + "\"";
            var arrEndoIncomp = new string[] { nomeEndoIncomp1, nomeEndoIncomp2 };

            var ei = new EndoprocedimentoIncompatibile(nomeEndo, arrEndoIncomp);

            Assert.Equal(expected, ei.ToString());
        }
    }
}
