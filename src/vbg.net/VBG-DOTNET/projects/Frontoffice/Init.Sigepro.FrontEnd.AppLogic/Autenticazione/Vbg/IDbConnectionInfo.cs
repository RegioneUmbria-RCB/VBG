using PersonalLib2.Data;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg
{
    internal interface IDbConnectionInfo
    {
        IDatabase CreateDatabase(string token);
        public string IdComune { get; }
        public string Alias { get; }
    }
}