using System;
using System.Linq;
using System.Text;
using System.Threading;

public class PasswordGenerator
{
    // Use ThreadLocal to provide a unique Random instance per thread
    private static readonly ThreadLocal<Random> _random = new ThreadLocal<Random>(() => new Random());


    // Define the character sets for different requirements
    private const string UppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private const string LowercaseChars = "abcdefghijklmnopqrstuvwxyz";
    private const string DigitChars = "0123456789";
    private const string SpecialChars = "@&$_-";

    public static string GeneratePassword(int length = 8)
    {
        if (length < 8)
        {
            throw new ArgumentException("Password length must be at least 8 characters.");
        }

        // Ensure we have at least one of each required character type
        var password = new StringBuilder();
        password.Append(GetRandomChar(UppercaseChars)); // At least one uppercase
        password.Append(GetRandomChar(LowercaseChars)); // At least one lowercase
        password.Append(GetRandomChar(DigitChars));     // At least one digit
        password.Append(GetRandomChar(SpecialChars));   // At least one special character

        // Fill the rest of the password length with random characters from all sets
        string allChars = UppercaseChars + LowercaseChars + DigitChars + SpecialChars;
        while (password.Length < length)
        {
            password.Append(GetRandomChar(allChars));
        }

        // Shuffle the characters to avoid predictable patterns
        return Shuffle(password.ToString());
    }

    // Helper method to get a random character from a string
    private static char GetRandomChar(string chars)
    {
        return chars[_random.Value.Next(chars.Length)];
    }

    // Helper method to shuffle the characters in a string
    private static string Shuffle(string input)
    {
        return new string(input.ToCharArray().OrderBy(_ => _random.Value.Next()).ToArray());
    }
}