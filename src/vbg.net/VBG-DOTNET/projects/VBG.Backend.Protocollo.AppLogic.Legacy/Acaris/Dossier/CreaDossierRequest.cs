using Init.SIGePro.Protocollo.AcarisObjectServicePort;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Dossier
{
    internal class CreaDossierRequest
    {
        public int AnniConservazione { get; internal set; }
        public int AnniConservazioneGenerale { get; internal set; }
        public string CodiceDossier { get; internal set; }
        public string Descrizione { get; internal set; }
        public IdAOOType IdAOO { get; internal set; }
        public string IdentificativoUtente { get; internal set; }
        public IdNodoType IdNodo { get; internal set; }
        public IdStrutturaType IdStruttura { get; internal set; }
        public PrincipalIdType PrincipalId { get; internal set; }
        public ObjectIdType RepositoryId { get; internal set; }
        public ObjectIdType SerieDossierId { get; internal set; }
        public string ParolaChiave { get; internal set; }
    }
}
