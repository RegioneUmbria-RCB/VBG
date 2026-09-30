using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma
{
    public class CredentialsInfo
    {
        public string Username { get; private set; }
        public string Password { get; private set; }
        public string CodiceEnte { get; private set; }
        public string CredentialInfo { get; private set; }
        public string Token { get; set; }

        public CredentialsInfo(string username, string password, string codiceEnte, string token)
        {
            this.Username = username;
            this.Password = password;
            this.CodiceEnte = codiceEnte;
            this.CredentialInfo = Convert.ToBase64String(Encoding.UTF8.GetBytes($"{username}:{password}"));
            this.Token = token;
        }
    }
}
