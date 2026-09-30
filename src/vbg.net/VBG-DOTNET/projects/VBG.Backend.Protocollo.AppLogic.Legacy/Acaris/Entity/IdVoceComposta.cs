using System;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity
{
    public class IdVoceComposta : IIDAcaris
    {
        private class Constants 
        {
            public static char Separatore = Convert.ToChar(".");
        }

        public string Voce { get; }

        public string IdAcaris { get; }

        public IdVoceComposta(IProtocolloSerializer serializer, ObjectWSClient client, RepositoryId repositoryId, PrincipalId principalId, string voce, int idTitolario )
        {
            var voci = voce.Split(Constants.Separatore).Select(x => Convert.ToInt32(x));
            int? padre = null;
            int? titolario = idTitolario;

            string lIdAcaris = null;
            foreach (var v in voci)
            {
                var idVoce = new IdVoce(serializer, client, repositoryId, principalId, v, titolario, padre);
                lIdAcaris = idVoce.IdAcaris;
                padre = idVoce.DbKey;
                titolario = null;
            }

            this.Voce = voce;
            this.IdAcaris = lIdAcaris;
        }

        public override string ToString()
        {
            return $"IdVoceComposta: [voce={this.Voce} idAcaris={this.IdAcaris}]";
        }
    }
}
