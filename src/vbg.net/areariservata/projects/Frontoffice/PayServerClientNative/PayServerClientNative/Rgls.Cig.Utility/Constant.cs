using System;

namespace Rgls.Cig.Utility
{
	public class Constant
	{
		public const string SICUREZZA_DBSECTION = "Sicurezza";

		public const string EMISSIONE_DBSECTION = "Emissione";

		public const string LOG_DBSECTION = "Log";

		public const int INVALID_INT = -2147483648;

		public const int NOT_CASE_SENSITIVE = -2;

		public const string INVALID_STRING = "__OSA__";

		public const int INVALID_YEARDATE = 1753;

		public const int RULE_DEFAULT = 0;

		public const int RESULT_SUCCESS = 0;

		public const int FATAL_ERROR = 15728640;

		public const int BAD_PARAMETER = 256;

		public const int WRONG_PARAMETER = 512;

		public const int METHOD_FAIL = 2048;

		public const int NO_DATA = 3840;

		public const int AUTHID_PWD = 1;

		public const int DB_OPEN_ERROR = 83886081;

		public const int DB_TRANS_ERROR = 83886083;

		public const int DB_UPDATE_ERROR = 83886084;

		public const int DB_SELECT_ERROR = 83886085;

		public const int DB_INSERT_ERROR = 83886086;

		public const int TRANS_ABORT = 1;

		public const int TRANS_COMMIT = 0;

		public const int USER_STATUS_DISABLED = 0;

		public const int USER_STATUS_ENABLED = 1;

		public const int USER_STATUS_BLOCKED = 2;

		public const int AUTHIDONLINE = 1;

		public const int AUTHIDBATCH = 2;

		public const int AUTHIDOTOKENUSB = 3;

		public const int AUTHIDCIE = 4;

		public const int RULEONLINE = 1;

		public const int RULEBATCH = 2;

		public const string PWD_INVALID_DOMAIN = "0";

		public const int PWD_PASSWORD_STATUS_ENABLED = 1;

		public const int PWD_PASSTHROUGH_FIRST_TIME = 1;

		public const int PWD_MAIN_LEVEL = 0;

		public const int PWD_ALWAYS_VERIFIED = 0;

		public const int PWD_ZERO_ALLOWED = -1;

		public const int PWD_LOCK_FORWARDED = 1;

		public const int PWD_NOTREFERENCED = -1;

		public const int PWD_CHANGEABLE = 1;

		public const int PWD_CHANGE_PWD_FIRST_TIME = 1;

		public const int PWD_NOT_VERIFIED = -1;

		public const int PWD_VERIFIED = 1;

		public const int PWD_DHH_FALSE = 0;

		public const int PWD_USERS = 0;

		public const int PWD_PASSWORDS = 1;

		public const int PWD_GENERALRULES = 3;

		public const int PWD_CRYPT_ERR = -2147220990;

		public const int PWD_BAD_USER = 67109121;

		public const int PWD_BAD_PASSWORD = 67109123;

		public const int PWD_BAD_DOMAIN = 67109122;

		public const int PWD_WRONG_PARAMETER = 67109376;

		public const int PWD_WRONG_USERNAME = 67109377;

		public const int PWD_WRONG_PWDLEVEL = 67109378;

		public const int PWD_WRONG_RULE = 67109379;

		public const int PWD_WRONG_SECID = 67109380;

		public const int PWD_WRONG_TABLE = 67109381;

		public const int PWD_ALREADY_USER = 67109889;

		public const int PWD_METHOD_FAIL = 67110912;

		public const int PWD_USER_DISABLED = 67110913;

		public const int PWD_USER_BLOCKED = 67110914;

		public const int PWD_USER_FORWARD_BLOCKED = 67110915;

		public const int PWD_EXPIRED_PASSWORD = 67110916;

		public const int PWD_PASSWORD_FAILED = 67110917;

		public const int PWD_PASSWORD_BLOCKED = 67110918;

		public const int PWD_RENEW_PASSWORD = 67110919;

		public const int PWD_PASSTHROUGH_FAILED = 67110920;

		public const int PWD_INVALID_MIN_CHAR = 67112961;

		public const int PWD_INVALID_MIN_ALPHACHAR = 67112962;

		public const int PWD_INVALID_MIN_UCHAR = 67112963;

		public const int PWD_INVALID_MIN_LCHAR = 67112964;

		public const int PWD_INVALID_PUNCT_CHAR = 67112965;

		public const int PWD_INVALID_MIN_NUM = 67112966;

		public const int PWD_INVALID_MAX_CHAR = 67112967;

		public const int PWD_MAX_CONSECUTIVE_CHAR = 67112969;

		public const int PWD_NOT_CHANGEABLE = 67112970;

		public const int PWD_PASSWORD_USED = 67112971;

		public const int PWD_PASSWORD_PRESENT = 67112972;

		public const int PWD_FIRSTACCES_CHANGE = 67112973;
	}
}
