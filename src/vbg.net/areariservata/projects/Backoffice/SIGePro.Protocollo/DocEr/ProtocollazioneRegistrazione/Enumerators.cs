using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione
{
    public class Enumerators
    {
        public enum TipiForzaRegistrazione { NonForzareRegistrazione = 0, ForzareRegistrazione = 1 };
        /// <summary>
        /// FD=firmato digitalmente
        /// FE=firmato non digitalmente
        /// F=da inoltrare alla firma
        /// NF Non firmato 
        /// </summary>
        public enum TipiFirma { FD, FE, F, NF };
        public enum TipoFlusso { E, U, I };
        public enum TipoPersona { F, G };
        public enum TipoSesso { M, F };
    }
}
