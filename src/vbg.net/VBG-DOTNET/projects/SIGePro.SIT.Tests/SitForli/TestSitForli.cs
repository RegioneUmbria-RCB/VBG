using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.SIT.AppLogic.Data;
using VBG.Backend.SIT.AppLogic.Forli;
using Xunit;

namespace VBG.Backend.SIT.Tests.IntegrazioneSitForli
{
    public class TestSitForli
    {
        //public class SitForliUnderTest : SitForli
        //{
        //    public override void SetupVerticalizzazione(IVerticalizzazioniFactory verticalizzazioniFactory)
        //    {
        //        this._catasto = new ForliCatasto(TestSitForli.CnStringCatasto);
        //        this._toponomastica = new ForliToponomastica(TestSitForli.CnStringToponomastica);
        //    }
        //}
        //private const string CnStringCatasto = "Data Source=(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=10.56.5.71)(PORT=1521)))(CONNECT_DATA=(SID=orcl)(SERVER=DEDICATED)));User Id=INIT;Password=tini;";
        //private const string CnStringToponomastica = "Data Source=(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=10.56.5.70)(PORT=1521)))(CONNECT_DATA=(SID=orcl)(SERVER=DEDICATED)));User Id=INIT;Password=tini;";
        //private readonly SitForli _sit;

        //public TestSitForli()
        //{
        //    this._sit = new SitForliUnderTest();
        //    this._sit.InizializzaParametriSigepro("E256", "E256", "TT", null);

        //    this._sit.SetupVerticalizzazione();
        //}

        //[Fact]
        //public void ListaVie()
        //{
        //    var vie = this._sit.GetListaVie(FiltroRicercaListaVie.Tutte, null);
        //}

        //[Fact]
        //public void ListaVie_disabilitate()
        //{
        //    var vie = this._sit.GetListaVie(FiltroRicercaListaVie.Cessata, null);
        //}

        //[Fact]
        //public void ListaVie_abilitate()
        //{
        //    var vie = this._sit.GetListaVie(FiltroRicercaListaVie.Attiva, null);
        //}

        //[Fact]
        //public void ListaCivici()
        //{
        //    var codVia = "8217";

        //    this._sit.DataSit = new Sit
        //    {
        //        CodVia = codVia
        //    };

        //    var ret = this._sit.ElencoCivici();

        //    Assert.True(ret.ReturnValue);
        //    Assert.Equal(5, ret.DataCollection.Count);
        //}

        //[Fact]
        //public void ValidaCivico()
        //{
        //    var codVia = "8217";
        //    var civico = "2";

        //    this._sit.DataSit = new Sit
        //    {
        //        CodVia = codVia,
        //        Civico = civico
        //    };

        //    var ret = this._sit.CivicoValidazione();

        //    Assert.True(ret.ReturnValue);
        //    Assert.Equal("0082170002        ", this._sit.DataSit.CodCivico);
        //}

        //[Fact]
        //public void ValidaCivico_civicoEsistenteConEsponentiMultipli()
        //{
        //    var codVia = "303";
        //    var civico = "2";

        //    this._sit.DataSit = new Sit
        //    {
        //        CodVia = codVia,
        //        Civico = civico
        //    };

        //    var ret = this._sit.CivicoValidazione();

        //    Assert.True(ret.ReturnValue);
        //    Assert.Equal("", this._sit.DataSit.CodCivico);
        //}

        //[Fact]
        //public void ListaEsponenti()
        //{
        //    var codVia = "303";
        //    var civico = "2";

        //    this._sit.DataSit = new Sit
        //    {
        //        CodVia = codVia,
        //        Civico = civico
        //    };

        //    var ret = this._sit.ElencoEsponenti();
        //    Assert.True(ret.ReturnValue);
        //    Assert.Equal(4, ret.DataCollection.Count);
        //}

        //[Fact]
        //public void ValidaEsponente_esponenteValorizzato()
        //{
        //    var codVia = "303";
        //    var civico = "2";
        //    var esponente = "A";

        //    this._sit.DataSit = new Sit
        //    {
        //        CodVia = codVia,
        //        Civico = civico,
        //        Esponente = esponente
        //    };

        //    var ret = this._sit.EsponenteValidazione();
        //    Assert.True(ret.ReturnValue);
        //    Assert.Equal("0003030002A       ", this._sit.DataSit.CodCivico);
        //}

        //[Fact]
        //public void ValidaEsponente_esponenteVuoto()
        //{
        //    var codVia = "303";
        //    var civico = "2";
        //    var esponente = "";

        //    this._sit.DataSit = new Sit
        //    {
        //        CodVia = codVia,
        //        Civico = civico,
        //        Esponente = esponente
        //    };

        //    var ret = this._sit.EsponenteValidazione();
        //    Assert.True(ret.ReturnValue);
        //    Assert.Equal("0003030002        ", this._sit.DataSit.CodCivico);
        //}

        //[Fact]
        //public void ValidaEsponente_esponenteNonValido()
        //{
        //    var codVia = "303";
        //    var civico = "2";
        //    var esponente = "Z";

        //    this._sit.DataSit = new Sit
        //    {
        //        CodVia = codVia,
        //        Civico = civico,
        //        Esponente = esponente
        //    };

        //    var ret = this._sit.EsponenteValidazione();
        //    Assert.False(ret.ReturnValue);
        //}

        //[Fact]
        //public void ListaInterni()
        //{
        //    var codVia = "303";
        //    var civico = "2";
        //    var esponente = "B";

        //    this._sit.DataSit = new Sit
        //    {
        //        CodVia = codVia,
        //        Civico = civico,
        //        Esponente = esponente
        //    };

        //    var ret = this._sit.ElencoInterni();

        //    Assert.True(ret.ReturnValue);
        //    Assert.Equal(6, ret.DataCollection.Count);
        //}

        //[Fact]
        //public void ListaInterni_noEsponente_noInterniTrovati()
        //{
        //    var codVia = "303";
        //    var civico = "2";
        //    var esponente = "";

        //    this._sit.DataSit = new Sit
        //    {
        //        CodVia = codVia,
        //        Civico = civico,
        //        Esponente = esponente
        //    };

        //    var ret = this._sit.ElencoInterni();

        //    Assert.False(ret.ReturnValue);
        //    Assert.Empty(ret.DataCollection);
        //}

        //[Fact]
        //public void ListaInterni_noEsponente_variInterniTrovati()
        //{
        //    var codVia = "303";
        //    var civico = "8";
        //    var esponente = "";

        //    this._sit.DataSit = new Sit
        //    {
        //        CodVia = codVia,
        //        Civico = civico,
        //        Esponente = esponente
        //    };

        //    var ret = this._sit.ElencoInterni();

        //    Assert.True(ret.ReturnValue);
        //    Assert.Equal(3, ret.DataCollection.Count);
        //}

        //[Fact]
        //public void ValidaInterno_internoTrovato()
        //{
        //    var codVia = "303";
        //    var civico = "8";
        //    var esponente = "";
        //    var interno = "1";

        //    this._sit.DataSit = new Sit
        //    {
        //        CodVia = codVia,
        //        Civico = civico,
        //        Esponente = esponente,
        //        Interno = interno
        //    };

        //    var ret = this._sit.InternoValidazione();

        //    Assert.True(ret.ReturnValue);
        //    Assert.Equal("0003030008        ", this._sit.DataSit.CodCivico);
        //}

        //[Fact]
        //public void ValidaInterno_internoNonTrovato()
        //{
        //    var codVia = "303";
        //    var civico = "8";
        //    var esponente = "";
        //    var interno = "888";

        //    this._sit.DataSit = new Sit
        //    {
        //        CodVia = codVia,
        //        Civico = civico,
        //        Esponente = esponente,
        //        Interno = interno
        //    };

        //    var ret = this._sit.InternoValidazione();

        //    Assert.False(ret.ReturnValue);
        //}

        //[Fact]
        //public void ValidaInterno_civicoNonPassato()
        //{
        //    var codVia = "303";
        //    var civico = "";
        //    var esponente = "";
        //    var interno = "888";

        //    this._sit.DataSit = new Sit
        //    {
        //        CodVia = codVia,
        //        Civico = civico,
        //        Esponente = esponente,
        //        Interno = interno
        //    };

        //    Assert.Throws<SitValidationException>(() => this._sit.InternoValidazione());
        //}

        //[Fact]
        //public void ListaFogli()
        //{
        //    this._sit.DataSit = new Sit();

        //    var ret = this._sit.ElencoFogli();

        //    Assert.True(ret.ReturnValue);
        //    Assert.Equal(295, ret.DataCollection.Count);
        //}

        //[Fact]
        //public void ValidaFoglio()
        //{
        //    this._sit.DataSit = new Sit
        //    {
        //        Foglio = "1"
        //    };

        //    var ret = this._sit.FoglioValidazione();

        //    Assert.True(ret.ReturnValue);
        //}


        //[Fact]
        //public void ListaParticelle()
        //{
        //    this._sit.DataSit = new Sit
        //    {
        //        Foglio = "1"
        //    };

        //    var ret = this._sit.ElencoParticelle();

        //    Assert.True(ret.ReturnValue);
        //    Assert.Equal(310, ret.DataCollection.Count);
        //}

        //[Fact]
        //public void ValidaParticella()
        //{
        //    this._sit.DataSit = new Sit
        //    {
        //        Foglio = "1",
        //        Particella = "1"
        //    };

        //    var ret = this._sit.ParticellaValidazione();

        //    Assert.True(ret.ReturnValue);
        //}

        //[Fact]
        //public void ListaSub()
        //{
        //    this._sit.DataSit = new Sit
        //    {
        //        Foglio = "1",
        //        Particella = "14"
        //    };

        //    var ret = this._sit.ElencoSub();

        //    Assert.True(ret.ReturnValue);
        //    Assert.Equal(5, ret.DataCollection.Count);
        //}

        //[Fact]
        //public void ValidaSub_valido()
        //{
        //    this._sit.DataSit = new Sit
        //    {
        //        Foglio = "1",
        //        Particella = "14",
        //        Sub = "5"
        //    };

        //    var ret = this._sit.SubValidazione();

        //    Assert.True(ret.ReturnValue);
        //}


        //[Fact]
        //public void ValidaSub_nonValido()
        //{
        //    this._sit.DataSit = new Sit
        //    {
        //        Foglio = "1",
        //        Particella = "14",
        //        Sub = "100"
        //    };

        //    var ret = this._sit.SubValidazione();

        //    Assert.False(ret.ReturnValue);
        //}
    }
}
