using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.MIP
{
    public static class CodiciErroreMIP
    {
        public const int LEN_COMPONENTE = 3;
        public const int CK_TIMEABS = 1;
        public const int CK_TIMEREL = 2;
        public const int CK_EXPIRED = -100;
        public const int CK_BADIPADDR = -101;
        public const int CK_BADPWDLEV = -102;
        public const int PS2S_HASHERROR = -1;
        public const int PS2S_HASHNOTFOUND = -2;
        public const int PS2S_COMPERROR = -3;
        public const int PS2S_TIMEELAPSED = -4;
        public const int PS2S_DATAERROR = -5;
        public const int PS2S_XMLERROR = -6;
        public const int PS2S_DATEERROR = -7;
        public const int PS2S_CREATEHASHERROR = -8;
        public const int PS2S_HTTPCONNECTION = -9;
        public const int PS2S_RIDNOTFOUND = -10;
        public const int PS2S_RIDUSED = -11;
        public const int PS2S_TIDNOTFOUND = -12;
        public const int PS2S_TIDUSED = -13;
        public const int S2S_CS_STATOOK = 0;
        public const int S2S_CS_STATOUSED = 1;
        public const int S2S_CS_STATOKO = 2;
        public const int S2S_PROTOCOLVERSION_1 = 1;
        public const int S2S_PROTOCOLVERSION_2 = 2;
        public const int S2S_KT_CLEAR = 1;
    }
}
