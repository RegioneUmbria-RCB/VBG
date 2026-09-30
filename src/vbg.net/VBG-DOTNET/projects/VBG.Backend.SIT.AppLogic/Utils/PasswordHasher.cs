//using System.Security.Cryptography;
//using System.Text;

//namespace VBG.Backend.SIT.AppLogic.Utils
//{
//    public static class PasswordHasher
//    {
//        public static string HashPasswordSHA1(string password)
//        {
//            using (SHA1 sha1 = SHA1.Create())
//            {
//                // Convert the input string to a byte array
//                byte[] passwordBytes = Encoding.UTF8.GetBytes(password);

//                // Compute the hash
//                byte[] hashBytes = sha1.ComputeHash(passwordBytes);

//                // Convert the byte array to a hex string
//                StringBuilder hashString = new StringBuilder();
//                foreach (byte b in hashBytes)
//                {
//                    hashString.Append(b.ToString("x2")); // Convert each byte to a hexadecimal value
//                }

//                return hashString.ToString();
//            }
//        }
//    }
//}
