using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity
{
    public class IdFolder : IIDAcaris
    {
        public string IdAcaris { get; }

        public IdFolder(string idAcarisFolder)
        {
            this.IdAcaris = idAcarisFolder;
        }

        public override string ToString()
        {
            return $"IdFolder: [idAcaris={this.IdAcaris}]";
        }
    }
}
