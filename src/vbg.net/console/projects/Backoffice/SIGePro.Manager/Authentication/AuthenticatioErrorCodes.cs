using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Authentication
{
    public struct AuthenticatioErrorCodes
    {
        public const int NO_ERR_CODE = 0;
        public const int ERR_DATABASE = 58000;
        public const int ERR_USER_NOT_EXIST = 58001;
        public const int ERR_PASSWORD_NOT_VALID = 58002;
        public const int ERR_INSERT_USER = 58003;
        public const int ERR_UPDATE_PASSWORD = 58004;
        public const int ERR_INVALID_TOKEN = 58005;
        public const int ERR_UTENTI_MULTIPLI = 58006;
        public const int ERR_CFPIVA_NONCORRETTO = 58007;
        public const int ERR_EXTRACT_USER = 58008;
    }
}
