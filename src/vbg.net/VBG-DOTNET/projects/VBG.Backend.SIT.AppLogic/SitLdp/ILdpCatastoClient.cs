namespace VBG.Backend.SIT.AppLogic.SitLdp
{
    internal interface ILdpCatastoClient
    {
        System.Collections.Generic.IEnumerable<string> GetListaFogli(string sezione);
        System.Collections.Generic.IEnumerable<string> GetListaParticelle(string sezione, string foglio);
        System.Collections.Generic.IEnumerable<string> GetListaSezioni();
    }
}
